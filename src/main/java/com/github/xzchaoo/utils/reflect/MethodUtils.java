package com.github.xzchaoo.utils.reflect;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/12.
 */
public class MethodUtils {
	public static boolean hasPublicMethod(Class<?> clazz, String method) {
		Method[] ms = clazz.getMethods();
		for (Method m : ms) {
			if (m.getName().equals(method)) {
				return true;
			}
		}
		return false;
	}
}
