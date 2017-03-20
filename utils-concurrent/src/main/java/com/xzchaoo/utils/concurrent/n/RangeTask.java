package com.xzchaoo.utils.concurrent.n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/3/20.
 */
public class RangeTask<OUT> extends ESTask<Integer, OUT> {
	private static final Logger log = LoggerFactory.getLogger(RangeTask.class);

	private final int begin;
	private final int end;

	public RangeTask(
		int threads, boolean needOutput, boolean needOrder, boolean logException, ESCallback<Integer, OUT> callback,
		int maxRetry,
		int begin, int end
	) {
		super(threads, needOutput, needOrder, logException, callback, maxRetry);
		this.begin = begin;
		this.end = end;
	}

	protected List<OUT> execute(ExecutorService es) {
		final AtomicInteger counter = new AtomicInteger(begin);
		for (int i = 0; i < threads; ++i) {
			Future<?> f = es.submit(new Runnable() {
				public void run() {
					while (true) {
						final int index = counter.getAndIncrement();
						if (index >= end) {
							return;
						}
						process(index, index);
					}
				}
			});
			futureList.add(f);
		}
		waitAllFuture();
		return output.getList();
	}

	public ESMode getMode() {
		return ESMode.RANGE;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}
}
