package com.xzchaoo.utils;

/**
 * Created by xzchaoo on 2016/6/7 0007.
 */
public class SleepUtils {
	public static void sleepQuietly(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void sleep(String tag, long millis) {
		sleep(tag, millis, true);
	}

	public static void sleep(String tag, long millis, boolean print) {
		if (print) System.out.println(String.format("[%s] 睡觉%.2f秒", tag, millis / 1000.0f));
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
