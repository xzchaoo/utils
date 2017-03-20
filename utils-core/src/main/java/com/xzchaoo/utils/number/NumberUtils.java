package com.xzchaoo.utils.number;

/**
 * Created by Administrator on 2016/7/10.
 */
public class NumberUtils {
	public static int between(int value, int min, int max) {
		return value < min ? min : value > max ? max : value;
	}

	public static int max(int n1, int n2, int n3) {
		return Math.max(n1, Math.max(n2, n3));
	}

	public static int max(int n1, int n2, int n3, int n4) {
		return Math.max(n1, max(n2, n3, n4));
	}

	public static int max(int n1, int n2, int n3, int n4, int n5) {
		return Math.max(n1, max(n2, n3, n4, n5));
	}

	public static int divAndCeil(int x, int y) {
		return x / y + (x % y == 0 ? 0 : 1);
	}

	public static long divAndCeil(long x, long y) {
		return x / y + (x % y == 0 ? 0 : 1);
	}

	public static int parseInt(String str, int defaultValue) {
		if (str == null || str.length() == 0) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
