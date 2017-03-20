package com.xzchaoo.utils.concurrent.n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017/3/20.
 */
public abstract class ESTask<IN, OUT> {
	private static final Logger log = LoggerFactory.getLogger(ESTask.class);

	protected final int threads;
	protected final boolean needOut;
	protected final boolean needOrder;
	protected final boolean logException;
	protected final ESCallback<IN, OUT> callback;
	protected final int maxRetry;

	public ESTask(
		int threads, boolean needOut, boolean needOrder, boolean logException,
		ESCallback<IN, OUT> callback,
		int maxRetry
	) {
		this.threads = threads;
		this.needOut = needOut;
		this.needOrder = needOrder;
		this.logException = logException;
		this.callback = callback;
		this.maxRetry = maxRetry;
		this.futureList = new ArrayList<Future<?>>(threads);
		this.output = createOutput();
	}

	protected abstract List<OUT> execute(ExecutorService es);

	public List<OUT> execute() {
		ExecutorService es = Executors.newFixedThreadPool(threads);
		try {
			return execute(es);
		} finally {
			es.shutdownNow();
		}
	}

	public int getThreads() {
		return threads;
	}

	public boolean isNeedOut() {
		return needOut;
	}

	public boolean isNeedOrder() {
		return needOrder;
	}

	public boolean isLogException() {
		return logException;
	}

	public ESCallback<IN, OUT> getCallback() {
		return callback;
	}

	public abstract ESMode getMode();

	protected final ESOutput<OUT> output;

	protected final List<Future<?>> futureList;

	protected void waitAllFuture() {
		for (Future<?> f : futureList) {
			try {
				f.get();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	protected void process(int index, IN in) {
		int count = 0;
		while (count <= maxRetry) {
			++count;
			try {
				OUT out = callback.process(in);
				if (needOut) {
					output.add(index, out);
				}
				return;
			} catch (Exception e) {
				if (logException) {
					log.error("未处理的异常", e);
				}
			}
		}
		if (needOut) {
			output.add(index, null);
		}
	}

	protected ESOutput<OUT> createOutput() {
		if (needOut) {
			return new SimpleESOutput<OUT>(needOrder);
		} else {
			return NoOpESOutput.INSTANCE;
		}
	}
}
