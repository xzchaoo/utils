package com.xzchaoo.utils.es.n;

/**
 * Created by Administrator on 2016/10/22.
 */
public class TaskContext {
	int retry;
	Exception lastException;

	public int getRetry() {
		return retry;
	}

	public Exception getLastException() {
		return lastException;
	}
}
