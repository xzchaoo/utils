package com.github.xzchaoo.utils.es;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/6.
 */
public class ESOperator {
	private final ExecutorService executorService;

	public ESOperator(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public ESOperator untilFinished(long timeout, TimeUnit unit) throws InterruptedException {
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.DAYS);
		return this;
	}

	public ESOperator untilFinished() throws InterruptedException {
		return untilFinished(1, TimeUnit.DAYS);
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}
}
