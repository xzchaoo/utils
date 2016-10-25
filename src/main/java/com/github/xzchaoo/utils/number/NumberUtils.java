package com.github.xzchaoo.utils.number;

/**
 * Created by Administrator on 2016/7/10.
 */
public class NumberUtils {
	public static int between(int value, int min, int max) {
		return value < min ? min : value > max ? max : value;
	}
}
