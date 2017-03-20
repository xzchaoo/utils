package com.xzchaoo.utils.concurrent;

/**
 * Created by Administrator on 2016/10/22.
 */
public interface ESCallback<IN, OUT> {
	OUT run(IN index, TaskContext tc, ESContext ctx) throws Exception;
}
