package com.com.github.xzchaoo.utils.buffer;

import com.github.xzchaoo.utils.buffer.BufferFlusher;
import com.github.xzchaoo.utils.buffer.SyncBuffer;
import com.github.xzchaoo.utils.es.n.ESBuilder;
import com.github.xzchaoo.utils.es.n.ESCallback;
import com.github.xzchaoo.utils.es.n.ESContext;
import com.github.xzchaoo.utils.es.n.TaskContext;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2016/11/12.
 */
public class TestSyncBuffer {
	@Test
	public void test_1() {
		final StringBuilder sb = new StringBuilder();
		SyncBuffer<Integer> b = new SyncBuffer<Integer>(10, new BufferFlusher<Integer>() {
			public void flush(List<Integer> buffer) {
				for (Integer i : buffer) {
					sb.append(i);
				}
			}
		});
		for (int i = 0; i < 10; ++i) {
			b.add(i);
		}
		assertEquals("0123456789", sb.toString());
	}

	@Test
	public void test_2() {
		final ConcurrentHashMap<Integer, Boolean> set = new ConcurrentHashMap<Integer, Boolean>();
		final AtomicInteger flushCount = new AtomicInteger();
		final SyncBuffer<Integer> b = new SyncBuffer<Integer>(100, new BufferFlusher<Integer>() {
			public void flush(List<Integer> buffer) {
				flushCount.incrementAndGet();
				for (Integer i : buffer) {
					set.put(i, Boolean.TRUE);
				}
			}
		});
		ESBuilder.<Integer, Void>create(4).range(0, 10001).callback(new ESCallback<Integer, Void>() {
			public Void run(Integer index, TaskContext tc, ESContext ctx) throws Exception {
				b.add(index);
				return null;
			}
		}).execute();
		b.flush(true);

		assertEquals(10001, set.size());
		assertEquals(101, flushCount.get());
	}
}
