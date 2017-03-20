package com.xzchaoo.utils.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 基于阻塞队列的消费者
 * Created by zcxu on 2017/3/9.
 */
public abstract class SmartQueueConsumer<T> extends SmartThreadRunner {
	private BlockingQueue<T> queue;

	private long pollInterval = 1000;

	public SmartQueueConsumer() {
		this(1000, true, 1000);
	}

	public SmartQueueConsumer(long pollInterval, boolean interruptWhenStop, long joinThreadMills) {
		super(interruptWhenStop, joinThreadMills);
		this.pollInterval = pollInterval;
	}

	protected void doRun() {
		while (canRun()) {
			try {
				T t = queue.poll(pollInterval, TimeUnit.MILLISECONDS);
				if (t != null) {
					process(t);
				} else if (isStopCalled()) {
					log.info("队列为空, stopping==true 退出执行");
					break;
				}
			} catch (InterruptedException e) {
				interrupted.set(true);
				break;
			}
		}
		if (interrupted.get()) {
			log.info("线程被中断 提前退出");
			Thread.currentThread().interrupt();
			if (!isStopCalled()) {
				stop();
			}
		}
	}

	/**
	 * 创建阻塞队列
	 *
	 * @return
	 */
	protected BlockingQueue<T> setupQueue() {
		return new LinkedBlockingQueue<T>();
	}

	protected boolean canRun() {
		//不考虑stopping
		//它会清理 interrupted 标志
		if (Thread.interrupted()) {
			Thread.currentThread().interrupt();
			interrupted.set(true);
			return false;
		}
		return !interrupted.get();
	}

	/**
	 * 处理每个元素
	 *
	 * @param t
	 */
	protected abstract void process(T t);

	private final AtomicBoolean inited = new AtomicBoolean(false);

	public void init() {
		if (!inited.compareAndSet(false, true)) {
			return;
		}
		//先初始化队列
		queue = setupQueue();
		if (queue == null) {
			throw new IllegalStateException("队列不可为空");
		}
	}

	protected void doStart() {
		init();
		super.doStart();
	}

	/**
	 * 元素入队
	 *
	 * @param t
	 */
	public boolean add(T t) {
		checkStop();
		return queue.add(t);
	}

	/**
	 * 元素入队
	 *
	 * @param t
	 */
	public void put(T t) throws InterruptedException {
		checkStop();
		queue.put(t);
	}

	/**
	 * 元素入队
	 *
	 * @param t
	 */
	public boolean offer(T t) {
		checkStop();
		return queue.offer(t);
	}

	public boolean offer(T t, long timeout, TimeUnit unit) throws InterruptedException {
		checkStop();
		return queue.offer(t, timeout, unit);
	}

	public BlockingQueue<T> getQueue() {
		return queue;
	}

}
