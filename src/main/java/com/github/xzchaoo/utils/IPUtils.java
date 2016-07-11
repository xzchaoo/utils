package com.github.xzchaoo.utils;

/**
 * Created by Administrator on 2016/6/17.
 */
public class IPUtils {
	public static int ipToInt(String ip) {
		String[] ss = ip.split("\\.");
		int ret = 0;
		for (int i = 0; i < 4; ++i) {
			int b = Integer.parseInt(ss[i]);
			if (b > 127) b -= 256;
			ret = (ret << 8) | (b & 0xff);
		}
		return ret;
	}

	public static String intToIP(int ipAsInt) {
		StringBuilder sb = new StringBuilder();
		sb.append((256 + ((ipAsInt >> 24) & 0xff)) & 0xff);
		sb.append('.');
		sb.append((256 + ((ipAsInt >> 16) & 0xff)) & 0xff);
		sb.append('.');
		sb.append((256 + ((ipAsInt >> 8) & 0xff)) & 0xff);
		sb.append('.');
		sb.append((256 + ((ipAsInt >> 0) & 0xff)) & 0xff);
		return sb.toString();
	}


	public static void main(String[] args) {
		for (int i = -1062731519; i < -1062731519 + 65536; ++i) {
			System.out.println(intToIP(i));
		}
	}
}
