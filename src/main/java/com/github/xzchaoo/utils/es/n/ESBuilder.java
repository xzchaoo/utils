package com.github.xzchaoo.utils.es.n;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/10/22.
 */
public class ESBuilder<IN, OUT> {
	private int beg, end;
	private List<IN> list;
	private ESCallback<IN, OUT> callback;
	private ExceptionHandler<IN, OUT> exceptionHandler;
	private int type = 1;
	private final ESContext context;
	private boolean needClose = false;

	public ESBuilder(ExecutorService es, int threads) {
		context = new ESContext(es, threads, null, 0, false);
	}

	public ESBuilder(int threads) {
		this(null, threads);
		this.needClose = true;
	}

	public static <IN, OUT> ESBuilder<IN, OUT> create(ExecutorService es, int threads) {
		return new ESBuilder<IN, OUT>(es, threads);
	}

	public static <IN, OUT> ESBuilder<IN, OUT> create(int threads) {
		return new ESBuilder<IN, OUT>(threads);
	}

	public ESBuilder<IN, OUT> range(int beg, int end) {
		this.beg = beg;
		this.end = end;
		type = 2;
		return this;
	}

	public ESBuilder<IN, OUT> list(List<IN> list) {
		this.list = list;
		type = 3;
		return this;
	}


	public ESBuilder<IN, OUT> needReturnValue(boolean needReturnValue) {
		this.context.needReturnValue = needReturnValue;
		return this;
	}

	public ESBuilder<IN, OUT> retry(int retry) {
		this.context.maxRetry = retry;
		return this;
	}

	public ESBuilder<IN, OUT> callback(ESCallback<IN, OUT> callback) {
		this.callback = callback;
		return this;
	}

	public ESBuilder<IN, OUT> callback(ESCallback<IN, OUT> callback, ExceptionHandler<IN, OUT> exceptionHandler) {
		this.callback = callback;
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public ESBuilder<IN, OUT> exception(ExceptionHandler<IN, OUT> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		return this;
	}

	public List<OUT> execute() {
		if (callback != null) {
			if (needClose) {
				context.es = Executors.newFixedThreadPool(context.threads);
			}
			try {
				switch (type) {
					case 1:
						return NESUtils.es1(context, (ESCallback<Integer, OUT>) callback, (ExceptionHandler<Integer, OUT>) exceptionHandler);
					case 2:
						return NESUtils.es2(beg, end, context, (ESCallback<Integer, OUT>) callback, (ExceptionHandler<Integer, OUT>) exceptionHandler);
					case 3:
						return NESUtils.es3(list, context, callback, exceptionHandler);
					default:
						return null;
				}
			} finally {
				if (needClose) {
					context.es.shutdown();
				}
			}
		} else {
			throw new IllegalArgumentException("请提供callback");
		}
	}
}
