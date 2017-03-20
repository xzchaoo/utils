package com.xzchaoo.utils.concurrent.n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ListTask<IN, OUT> extends ESTask<IN, OUT> {
	private static final Logger log = LoggerFactory.getLogger(ListTask.class);

	private final List<IN> list;

	public ListTask(int threads, boolean needOut, boolean needOrder, boolean logException, ESCallback<IN, OUT> callback, int maxRetry, List<IN> list) {
		super(threads, needOut, needOrder, logException, callback, maxRetry);
		this.list = list;
	}

	protected List<OUT> execute(ExecutorService es) {
		final AtomicInteger counter = new AtomicInteger(0);
		final int size = list.size();
		for (int i = 0; i < threads; ++i) {
			Future<?> f = es.submit(new Runnable() {
				public void run() {
					while (true) {
						final int index = counter.getAndIncrement();
						if (index >= size) {
							return;
						}
						process(index, list.get(index));
					}
				}
			});
			futureList.add(f);
		}
		waitAllFuture();
		return output.getList();
	}

	public ESMode getMode() {
		return ESMode.LIST;
	}
}
