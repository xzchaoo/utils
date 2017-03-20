package com.xzchaoo.utils.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/10/22.
 */
public class NESUtils {
	private static <T> List<T> arrayToList(Object[] array) {
		List<T> list = new ArrayList<T>(array.length);
		for (Object obj : array) {
			list.add((T) obj);
		}
		return list;
	}

	public static <OUT, IN> List<OUT> es3(List<IN> list, final ESContext ctx, final ESCallback<IN, OUT> ecb, final ExceptionHandler<IN, OUT> exceptionHandler) {
		final Object lock = new Object();
		final int[] indexArray = new int[]{0};
		final Object[] ret;
		if (ctx.isNeedReturnValue()) {
			ret = new Object[list.size()];
		} else {
			ret = null;
		}
		final Iterator<IN> iterator = list.iterator();

		es1(new ESContext(ctx.es, ctx.threads, null, 0, false), new ESCallback<Integer, Void>() {
			//@Override
			public Void run(Integer oldIndex, TaskContext oldTc, ESContext oldCtx) throws Exception {
				IN u = null;
				int index;
				while (true) {
					synchronized (lock) {
						index = indexArray[0]++;
						if (iterator.hasNext()) {
							u = iterator.next();
						} else {
							break;
						}
					}
					if (u == null) {
						break;
					}
					TaskContext tc = new TaskContext();
					OUT value = null;
					try {
						value = ecb.run(u, tc, ctx);
					} catch (Exception e) {
						tc.lastException = e;
						value = exceptionHandler != null ? exceptionHandler.onException(u, tc, ctx) : null;
					}
					if (ctx.isNeedReturnValue()) {
						ret[index] = value;
					}
				}
				return null;
			}
		}, null);
		return ctx.isNeedReturnValue() ? (List<OUT>)arrayToList(ret) : null;
	}

	public static <OUT> List<OUT> es2(final int beg, final int end, final ESContext ctx, final ESCallback<Integer, OUT> ecb, final ExceptionHandler<Integer, OUT> exceptionHandler) {
		final AtomicInteger counter = new AtomicInteger(beg);

		final Object[] ret;
		if (ctx.isNeedReturnValue()) {
			ret = new Object[end - beg];
		} else {
			ret = null;
		}
		es1(new ESContext(ctx.es, ctx.threads, null, 0, false), new ESCallback<Integer, Void>() {
			//@Override
			public Void run(Integer oldIndex, TaskContext oldTc, ESContext oldCtx) throws Exception {
				int index = counter.getAndIncrement();
				while (index < end) {
					OUT value;
					TaskContext tc = new TaskContext();
					while (true) {
						try {
							value = ecb.run(index, tc, ctx);
							break;
						} catch (Exception e) {
							tc.lastException = e;
							if (tc.retry < ctx.maxRetry) {
								++tc.retry;
							} else {
								value = exceptionHandler != null ? exceptionHandler.onException(index, tc, ctx) : null;
								break;
							}
						}
					}
					if (ctx.isNeedReturnValue()) {
						ret[index - beg] = value;
					}
					index = counter.getAndIncrement();
				}
				return null;
			}
		}, null);
		if (ctx.isNeedReturnValue()) {
			List<OUT> list = new ArrayList<OUT>(ret.length);
			for (Object obj : ret) {
				list.add((OUT) obj);
			}
			return list;
		}
		return null;
	}

	public static <OUT> List<OUT> es1(final ESContext ctx, final ESCallback<Integer, OUT> ecb, final ExceptionHandler<Integer, OUT> exceptionHandler) {
		List<Future<OUT>> futureList = new ArrayList<Future<OUT>>(ctx.threads);
		for (int i = 0; i < ctx.threads; ++i) {
			final int index = i;
			Future<OUT> future = ctx.es.submit(new Callable<OUT>() {
				//@Override
				public OUT call() throws Exception {
					TaskContext tc = new TaskContext();
					while (true) {
						try {
							return ecb.run(index, tc, ctx);
						} catch (Exception e) {
							tc.lastException = e;
							if (tc.retry < ctx.maxRetry) {
								++tc.retry;
							} else {
								return exceptionHandler != null ? exceptionHandler.onException(index, tc, ctx) : null;
							}
						}
					}
				}
			});
			futureList.add(future);
		}
		List<OUT> ret;
		if (ctx.needReturnValue) {
			ret = new ArrayList<OUT>(ctx.threads);
		} else {
			ret = null;
		}
		for (Future<OUT> future : futureList) {
			OUT value;
			try {
				value = future.get();
			} catch (Exception e) {
				value = null;
			}
			if (ctx.needReturnValue) {
				ret.add(value);
			}
		}
		return ret;
	}

}
