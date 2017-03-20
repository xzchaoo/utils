package com.xzchaoo.utils.thread;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by zcxu on 2017/3/9.
 */
public class SmartConsumerTest {
	public static class MySmartQueueConsumer extends SmartQueueConsumer<String> {
		private long processMills;

		public MySmartQueueConsumer(long pollInterval, boolean interruptWhenStop, long joinThreadMills, long processMills) {
			super(pollInterval, interruptWhenStop, joinThreadMills);
			this.processMills = processMills;
		}

		protected void process(String s) {
			System.out.println(s);
			try {
				Thread.sleep(processMills);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}


	@Test
	public void test_SmartQueueConsumer_1() throws InterruptedException {
		MySmartQueueConsumer msc = new MySmartQueueConsumer(
			100,
			true,
			600,
			200
		);
		msc.init();
		final int size = 10;
		BlockingQueue<String> queue = msc.getQueue();
		for (int i = 0; i < size; ++i) {
			msc.add("a" + i);
		}
		assertEquals(size, queue.size());
		assertFalse(msc.isThreadRunning());
		//启动
		msc.start();

		//等到线程启动
		while (!msc.isThreadRunning()) {
			Thread.sleep(1);
		}
		Thread.sleep(620);
		assertTrue(msc.isThreadRunning());
		//此时已经处理了4个元素了 并且线程正在sleep
		assertEquals(size-4, queue.size());
		System.out.println("before stop");
		msc.stop();

		//调用stop之后
		assertFalse(msc.isRunning());
		assertTrue(msc.isStopCalled());

		assertTrue(msc.isInterrupted());
		assertFalse(msc.isThreadRunning());

		System.out.println("after stop");
	}

	@Test
	public void test_SmartQueueConsumer_2() throws InterruptedException {
		MySmartQueueConsumer msc = new MySmartQueueConsumer(
			100,
			false,
			300,
			100
		);
		msc.init();
		BlockingQueue<String> queue = msc.getQueue();
		final int size = 10;
		for (int i = 0; i < size; ++i) {
			msc.add("a" + i);
		}
		assertEquals(size, queue.size());
		assertFalse(msc.isThreadRunning());
		//启动
		msc.start();

		//等到线程启动
		while (!msc.isThreadRunning()) {
			Thread.sleep(1);
		}
		Thread.sleep(320);
		assertTrue(msc.isThreadRunning());
		//此时已经处理了4个元素了 并且线程正在sleep
		assertEquals(size - 4, queue.size());
		System.out.println("before stop");
		msc.stop();

		//调用stop之后
		assertFalse(msc.isRunning());
		assertTrue(msc.isStopCalled());

		assertFalse(msc.isInterrupted());
		assertTrue(msc.isThreadRunning());
		assertEquals(size - 7, queue.size());

		System.out.println("after stop");
		msc.getThread().join();
		assertEquals(0, queue.size());
		assertFalse(msc.isThreadRunning());
	}
}
