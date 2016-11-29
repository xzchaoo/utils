package com.github.xzchaoo.utils.lang;

/**
 * Created by xzchaoo on 2016/11/12.
 */
public class ObjectUtils {
	/**
	 * 返回第一个非空的元素
	 *
	 * @param args
	 * @param <T>
	 * @return
	 */
	public static <T> T firstNonNull(T... args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; ++i) {
				if (args[i] != null) {
					return args[i];
				}
			}
		}
		return null;
	}
}
