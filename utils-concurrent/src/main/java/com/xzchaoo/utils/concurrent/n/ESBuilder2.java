package com.xzchaoo.utils.concurrent.n;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ESBuilder2<IN, OUT> {
	private int threads;
	private ESMode mode;
	private int begin;
	private int end;
	private boolean needOut;
	private boolean needOrder;
	private boolean logException;
	private int retry = 0;
	private ESCallback<IN, OUT> callback;
	private List<IN> list;

	public static <IN, OUT> ESBuilder2<IN, OUT> create(int threads) {
		ESBuilder2<IN, OUT> esb = new ESBuilder2<IN, OUT>();
		esb.threads = threads;
		return esb;
	}

	public ESTask<IN, OUT> build() {
		if (retry < 0) {
			throw new IllegalStateException("retry不能小于0");
		}
		//基础检查
		if (mode == null) {
			throw new IllegalStateException("必须设置mode");
		}
		if (callback == null) {
			throw new IllegalStateException("没有设置callback");
		}
		switch (mode) {
			case RANGE:
				return (ESTask<IN, OUT>) new RangeTask<OUT>(threads, needOut, needOrder, logException, (ESCallback<Integer, OUT>) callback, retry, begin, end);
			case LIST:
				if (list == null) {
					throw new IllegalStateException("没有设置list");
				}
				return new ListTask<IN, OUT>(threads, needOut, needOrder, logException, callback, retry, list);
			default:
				throw new IllegalStateException();
		}
	}

	public ESBuilder2<IN, OUT> retry(int retry) {
		this.retry = retry;
		return this;
	}

	public ESBuilder2<IN, OUT> needOut(boolean needOut, boolean needOrder) {
		this.needOut = needOut;
		this.needOrder = needOrder;
		return this;
	}

	public ESBuilder2<IN, OUT> logException(boolean logException) {
		this.logException = logException;
		return this;
	}

	public ESBuilder2<IN, OUT> range(int begin, int end) {
		mode = ESMode.RANGE;
		this.begin = begin;
		this.end = end;
		return this;
	}

	public ESBuilder2<IN, OUT> list(List<IN> list) {
		mode = ESMode.LIST;
		this.list = list;
		return this;
	}

	public ESBuilder2<IN, OUT> callback(ESCallback<IN, OUT> callback) {
		this.callback = callback;
		return this;
	}
}
