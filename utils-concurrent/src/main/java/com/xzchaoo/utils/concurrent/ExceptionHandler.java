package com.xzchaoo.utils.concurrent;

/**
 * Created by Administrator on 2016/10/23.
 */
public interface ExceptionHandler<IN, OUT> {
	OUT onException(IN index, TaskContext tc, ESContext ctx);
}
