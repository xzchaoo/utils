package com.github.xzchaoo.utils.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的计数器
 * Created by Administrator on 2016/11/12.
 */
public class Counter<TAG> {
	private AtomicInteger counter = new AtomicInteger();
	private TAG tag;

	public Counter() {
	}

	public Counter(TAG tag) {
		this.tag = tag;
	}

	public int incrementAndGet() {
		return counter.incrementAndGet();
	}

	public int incrementAndGet(TAG tag) {
		this.tag = tag;
		return counter.incrementAndGet();
	}

	public AtomicInteger getCounter() {
		return counter;
	}

	public TAG getTag() {
		return tag;
	}

	public void setTag(TAG tag) {
		this.tag = tag;
	}
}
