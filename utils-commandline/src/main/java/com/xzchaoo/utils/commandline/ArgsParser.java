package com.xzchaoo.utils.commandline;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ArgsParser {
	private static final Pattern SHORT_PATTERN = Pattern.compile("-([a-zA-Z])(=([,\\w]+))?");
	private static final Pattern LONG_PATTERN = Pattern.compile("--([a-zA-Z]+)(=([,\\w]+))?");
	private static final Pattern[] PATTERNS = new Pattern[]{SHORT_PATTERN, LONG_PATTERN};
	public static final ArgsParser INSTANCE = new ArgsParser();

	public Args parse(String[] args) {
		Properties properties = new Properties();
		Properties shortProperties = new Properties();
		Properties longProperties = new Properties();

		for (String arg : args) {
			boolean resolved = false;
			for (Pattern p : PATTERNS) {
				Matcher m = p.matcher(arg);
				if (m.matches()) {
					String name = m.group(1);
					String value = m.group(3);
					if (value == null) {
						value = "true";
					}
					properties.setProperty(name, value);
					if (p == SHORT_PATTERN) {
						shortProperties.setProperty(name, value);
					} else {
						longProperties.setProperty(name, value);
					}
					resolved = true;
					break;
				}
			}
			if (!resolved) {
				throw new IllegalArgumentException("无法解析 " + arg);
			}
		}
		return new Args(properties, shortProperties, longProperties);
	}
}
