package com.github.xzchaoo.utils;

/**
 * Created by Administrator on 2016/6/17.
 */
public class IPUtils {
	public static int ipToInt(String ip) {
		long ret = ipToLong(ip);
		return (int) ret & 0X7FFFFFFF | ((int) ret & 0X80000000);
	}

	public static String intToIP(int ipAsInt) {
		return longToIP((long) ipAsInt & 0X7FFFFFFF | (ipAsInt & 0X80000000));
	}

	public static long ipToLong(String ip) {
		String[] ss = ip.split("\\.");
		long ret = 0;
		for (int i = 0; i < 4; ++i) {
			int b = Integer.parseInt(ss[i]);
			ret = (ret << 8) | (b & 0XFF);
		}
		return ret;
	}

	public static String longToIP(long ipAsLong) {
		StringBuilder sb = new StringBuilder();
		sb.append((ipAsLong >> 24) & 0XFF);
		sb.append('.');
		sb.append((ipAsLong >> 16) & 0XFF);
		sb.append('.');
		sb.append((ipAsLong >> 8) & 0XFF);
		sb.append('.');
		sb.append((ipAsLong >> 0) & 0XFF);
		return sb.toString();
	}
}
