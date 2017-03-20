package com.xzchaoo.utils.commandline;

import java.util.Properties;

/**
 * Created by Administrator on 2017/3/20.
 */
public class Args extends Options {
	private final Options shortOptions;
	private final Options longOptions;

	public Args(Properties options, Properties shortProperties, Properties longProperties) {
		super(options);
		this.shortOptions = new Options(shortProperties);
		this.longOptions = new Options(longProperties);
	}

	public Options shortOptions() {
		return shortOptions;
	}

	public Options longOptions() {
		return longOptions;
	}

	@Override
	public String toString() {
		return "Args{" +
			"shortOptions=" + shortOptions +
			", longOptions=" + longOptions +
			"} " + super.toString();
	}
}
