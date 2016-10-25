package com.github.xzchaoo.utils.es.n;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2016/10/22.
 */
public class ESContext {
	private final AtomicBoolean stopAsked = new AtomicBoolean(false);
	ExecutorService es;
	final int threads;

	Object initParam = null;
	int maxRetry = 0;
	boolean needReturnValue = false;

	public ESContext(ExecutorService es, int threads, Object initParam, int maxRetry, boolean needReturnValue) {
		this.es = es;
		this.threads = threads;
		this.initParam = initParam;
		this.maxRetry = maxRetry;
		this.needReturnValue = needReturnValue;
	}

	public boolean isNeedReturnValue() {
		return needReturnValue;
	}

	public int getMaxRetry() {
		return maxRetry;
	}

	public boolean isStopAsked() {
		return stopAsked.get();
	}

	public Object getInitParam() {
		return initParam;
	}
}
