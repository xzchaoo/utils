package com.xzchaoo.utils.es;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface RunInES4Callback {
	void run(int index, AtomicBoolean stop) throws Exception;

	boolean onException(int index, AtomicBoolean stop, Exception e) throws Exception;
}
