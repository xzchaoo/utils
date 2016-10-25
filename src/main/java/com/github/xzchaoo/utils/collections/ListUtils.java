package com.github.xzchaoo.utils.collections;

import java.util.List;

/**
 * Created by Administrator on 2016/8/6.
 */
public class ListUtils {
	public static <T> T first(List<T> list) {
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}

	public static <T> T last(List<T> list) {
		return (list == null || list.isEmpty()) ? null : list.get(list.size() - 1);
	}
}
