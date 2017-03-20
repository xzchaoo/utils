package com.xzchaoo.utils.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ESBuilderTest {
	@Test
	public void test_range() {
		final int batch = 2;
		final int beg = 0;
		final int end = 10;
		final ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<Integer>();
		final Set<Long> threadIdSet = new HashSet<Long>();
		List<Integer> result = ESBuilder.<Integer, Integer>create(batch).range(beg, end).callback(new ESCallback<Integer, Integer>() {
			public Integer run(Integer index, TaskContext tc, ESContext ctx) throws Exception {
				threadIdSet.add(Thread.currentThread().getId());
				q.add(index + 1);
				Thread.sleep(1);
				return null;
			}
		}).execute();
		assertNull(result);
		assertEquals(batch, threadIdSet.size());
		List<Integer> list = new ArrayList<Integer>(q.size());
		for (Integer i : q) {
			list.add(i);
		}
		Collections.sort(list);
		assertEquals(end - beg, list.size());
		for (int i = beg; i < end; ++i) {
			assertEquals(i + 1, list.get(i).intValue());
		}
	}


	@Test
	public void test_exception() {
		final int batch = 2;
		final int beg = 0;
		final int end = 10;
		List<Integer> result = ESBuilder.<Integer, Integer>create(batch).range(beg, end).callback(new ESCallback<Integer, Integer>() {
			public Integer run(Integer index, TaskContext tc, ESContext ctx) throws Exception {
				throw new RuntimeException();
			}
		}).execute();
		assertNull(result);
	}
}
