package com.github.xzchaoo.utils.es;

/**
 * Created by Administrator on 2016/7/10.
 */
public interface RunInES3Callback<T> {
	void run(T t) throws Exception;

	boolean onException(T t, Exception e) throws Exception;
}
