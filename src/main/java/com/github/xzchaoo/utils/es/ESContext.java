package com.github.xzchaoo.utils.es;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2016/8/6.
 */
public class ESContext {
	final ExecutorService executorService;
	final AtomicBoolean stop = new AtomicBoolean(false);

	public ESContext(ExecutorService executorService) {
		this.executorService = executorService;
	}


	public boolean isStopped() {
		return stop.get();
	}

	public void stop() {
		stop.set(true);
	}

}
