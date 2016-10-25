package com.github.xzchaoo.utils.buffer;

import com.github.xzchaoo.utils.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的buffer, 用于缓冲数据, 当缓存的数量超过指定的数量的时候就会调用flush
 * Created by Administrator on 2016/6/20.
 */
public abstract class SimpleBuffer<T> {
	private final List<T> buffer;
	private final int mMaxSize;
	private final int printInterval;
	private int count=0;

	public SimpleBuffer(int size, int printInterval) {
		buffer = new ArrayList<T>(size);
		mMaxSize = size;
		this.printInterval = printInterval;
	}

	public synchronized void add(T t) {
		int c = ++count;
		if (c % printInterval == 0) {
			onInterval(c);
		}
		buffer.add(t);
		if (buffer.size() >= mMaxSize)
			flush();
	}

	public synchronized void flush() {
		try {
			doFlush(buffer);
		} catch (Exception e) {
			ExceptionUtils.throwAsRuntimeException(e);
		} finally {
			buffer.clear();
		}
	}

	protected void onInterval(int count) {
	}

	protected abstract void doFlush(List<T> buffer) throws Exception;

}
