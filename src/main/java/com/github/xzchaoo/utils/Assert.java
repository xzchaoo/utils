package com.github.xzchaoo.utils;

/**
 * Created by xzchaoo on 2016/6/8 0008.
 */
public class Assert {

	public static void notNull(Object obj) {
		notNull(obj, "can not be null.");
	}

	public static void notNull(Object obj, String desc) {
		if (obj == null) throw new NullPointerException(desc);
	}
}
