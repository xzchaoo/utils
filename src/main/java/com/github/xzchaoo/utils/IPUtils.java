package com.github.xzchaoo.utils;

/**
 * 目前只支持 ipv4
 *
 * @author xzchaoo
 */
public class IPUtils {
	/**
	 * ip 转 int 由于int是32位有符号的 所以当ip >= 128.0.0.1 时候 int就变成负数了
	 *
	 * @param ip
	 * @return
	 */
	public static int ipToInt(String ip) {
		long ret = ipToLong(ip);
		return (int) ret & 0X7FFFFFFF | ((int) ret & 0X80000000);
	}

	public static String intToIP(int ipAsInt) {
		return longToIP((long) ipAsInt & 0X7FFFFFFF | (ipAsInt & 0X80000000));
	}

	/**
	 * 与int版本类似 但是long足够大 所以不用考虑负数问题
	 *
	 * @param ip
	 * @return
	 */
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
