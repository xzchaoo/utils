package com.xzchaoo.utils.commandline;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/20.
 */
public class Options {
	private final Properties properties;

	public Options(Properties properties) {
		this.properties = properties;
	}

	public boolean contains(String name) {
		return properties.containsKey(name);
	}

	public boolean getBoolean(String name) {
		String value = properties.getProperty(name);
		return Boolean.parseBoolean(value);
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}

	public String getString(String name) {
		return properties.getProperty(name);
	}

	public String getString(String name, String defaultValue) {
		return properties.getProperty(name, defaultValue);
	}

	public List<Integer> getIntList(String name) {
		String value = properties.getProperty(name);
		if (value == null) {
			return null;
		}
		String[] values = value.split(",");
		List<Integer> list = new ArrayList<Integer>(values.length);
		for (String s : values) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}

	public List<String> getStringList(String name) {
		String value = properties.getProperty(name);
		if (value == null) {
			return null;
		}
		String[] values = value.split(",");
		List<String> list = new ArrayList<String>(values.length);
		for (String s : values) {
			list.add(s);
		}
		return list;
	}

	public int getInt(String name) {
		String value = properties.getProperty(name);
		return Integer.parseInt(value);
	}

	public int getInt(String name, int defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Integer.parseInt(value);
	}

	@Override
	public String toString() {
		return "Options{" +
			"properties=" + properties +
			'}';
	}

	public Properties getProperties() {
		return properties;
	}

}
