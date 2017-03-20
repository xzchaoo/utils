package com.xzchaoo.utils.concurrent.n;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class NoOpESOutput implements ESOutput {
	public static final NoOpESOutput INSTANCE = new NoOpESOutput();

	public void add(int index, Object o) {
	}

	public List getList() {
		return null;
	}
}
