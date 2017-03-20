package com.xzchaoo.utils.buffer;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于 synchronized 关键字的实现
 * Created by Administrator on 2016/11/12.
 */
public class SyncBuffer<T> implements Buffer<T> {
	private List<T> buffer;
	private int flushSize;
	private BufferFlusher<T> flusher;

	public SyncBuffer(int flushSize) {
		this(flushSize, null);
	}

	public SyncBuffer(int flushSize, BufferFlusher<T> flusher) {
		this.flushSize = flushSize;
		this.buffer = new ArrayList<T>(flushSize);
		this.flusher = flusher;
	}

	public synchronized void add(T t) {
		buffer.add(t);
		if (buffer.size() == flushSize) {
			flush(true);
		}
	}

	protected void doFlush(List<T> buffer) {
		if (flusher != null) {
			flusher.flush(buffer);
		}
	}

	public synchronized void flush(boolean forceClear) {
		try {
			doFlush(buffer);
			buffer.clear();
		} catch (RuntimeException e) {
			if (forceClear) {
				buffer.clear();
			}
			throw e;
		}
	}

	public synchronized List<T> clear() {
		List<T> copy = new ArrayList<T>(buffer);
		buffer.clear();
		return copy;
	}
}
