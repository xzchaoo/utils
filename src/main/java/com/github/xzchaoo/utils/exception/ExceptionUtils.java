package com.github.xzchaoo.utils.exception;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ExceptionUtils {
	public static void throwAsRuntimeException(Exception e) {
		if (e instanceof RuntimeException) throw (RuntimeException) e;
		throw new RuntimeException(e);
	}
}
