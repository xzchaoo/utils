package com.xzchaoo.utils.concurrent.n;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ESBuilder2Test {
	@Test
	public void test_needOut_false() {
		List<Integer> result = ESBuilder2.<Integer, Integer>create(2)
			.range(0, 10)
			.needOut(false, true)
			.callback(new ESCallback<Integer, Integer>() {
				public Integer process(Integer integer) throws Exception {
					return integer + 1;
				}
			}).build().execute();
		assertNull(result);
	}

	@Test
	public void test_needOut_true() {
		List<Integer> result = ESBuilder2.<Integer, Integer>create(2)
			.range(0, 10)
			.needOut(true, true)
			.callback(new ESCallback<Integer, Integer>() {
				public Integer process(Integer integer) throws Exception {
					return integer + 1;
				}
			}).build().execute();
		assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), result);
	}

	@Test
	public void test_list() {
		final int threads = 2;
		ESCallback<String, String> callback = new ESCallback<String, String>() {
			public String process(String in) throws Exception {
				StringBuilder sb = new StringBuilder(in);
				sb.reverse();
				return sb.toString();
			}
		};
		ESTask<String, String> task = ESBuilder2.<String, String>create(threads)
			.needOut(true, true)
			.logException(true)
			.callback(callback)
			.list(Arrays.asList("abc", "def"))
			.build();

		assertTrue(task.isNeedOut());
		assertTrue(task.isNeedOrder());
		assertTrue(task.isLogException());
		assertNotNull(task.getCallback());
		assertEquals(callback, task.getCallback());

		assertEquals(threads, task.getThreads());
		assertTrue(task instanceof ListTask);
		ListTask<String, String> lt = (ListTask<String, String>) task;
		List<String> result = lt.execute();
		assertEquals(Arrays.asList("cba", "fed"), result);
	}

	@Test
	public void test_range() {
		final int threads = 2;
		final int begin = 0;
		final int end = 100;
		ESCallback<Integer, String> callback = new ESCallback<Integer, String>() {
			public String process(Integer in) throws Exception {
				return Integer.toString(in + 1);
			}
		};
		ESTask<Integer, String> task = ESBuilder2.<Integer, String>create(threads)
			.range(begin, end)
			.needOut(true, true)
			.logException(true)
			.callback(callback)
			.build();
		assertTrue(task.isNeedOut());
		assertTrue(task.isNeedOrder());
		assertTrue(task.isLogException());
		assertNotNull(task.getCallback());
		assertEquals(callback, task.getCallback());

		assertEquals(threads, task.getThreads());
		assertTrue(task instanceof RangeTask);
		RangeTask<String> rt = (RangeTask<String>) task;
		assertEquals(begin, rt.getBegin());
		assertEquals(end, rt.getEnd());

		List<String> result = rt.execute();
		assertNotNull(result);
		for (int i = begin; i < end; ++i) {
			assertEquals(Integer.toString(i + 1), result.get(i - begin));
		}
	}

}
