package org.xzc.utils.es;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by Administrator on 2016/7/10.
 */
public class ESUtils {
	public static <T> void runInES3(final Iterable<T> iterable, int threads, final RunInES3Callback<T> callback) throws InterruptedException {
		final Object lock = new Object();
		final Iterator<T> iterator = iterable.iterator();
		runInES1(threads, new RunInES1Callback() {
			public void run(int index) throws Exception {
				T lastT = null;
				while (true) {
					T t = lastT;
					if (t == null) {
						synchronized (lock) {
							if (iterator.hasNext()) {
								t = iterator.next();
							} else {
								break;
							}
						}
					}
					try {
						callback.run(t);
						lastT = null;
					} catch (Exception e) {
						try {
							if (callback.onException(t, e)) {
								lastT = t;
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
	}

	public static void runInES2(final int beg, final int end, final int threads, final RunInES2Callback callback) throws InterruptedException {
		runInES1(threads, new RunInES1Callback() {
			public void run(int index) throws Exception {
				for (int i = beg + index; i < end; i += threads) {
					try {
						callback.run(i);
					} catch (Exception e) {
						try {
							if (callback.onException(i, e)) {
								i -= threads;
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
	}

	public static void runInES1(int threads, final RunInES1Callback callback) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(threads);
		for (int i = 0; i < threads; ++i) {
			final int index = i;
			es.submit(new Callable<Void>() {
				public Void call() throws Exception {
					callback.run(index);
					return null;
				}
			});
		}
		es.shutdown();
		es.awaitTermination(1, TimeUnit.DAYS);
	}
}
