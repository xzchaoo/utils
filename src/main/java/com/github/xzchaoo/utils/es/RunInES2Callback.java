package com.github.xzchaoo.utils.es;

/**
 * Created by Administrator on 2016/7/10.
 */
public interface RunInES2Callback {
	void run(int index) throws Exception;

	boolean onException(int index, Exception e) throws Exception;
}
