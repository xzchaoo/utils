package com.xzchaoo.utils.es.n;

/**
 * Created by Administrator on 2016/10/23.
 */
public interface ExceptionHandler<IN, OUT> {
	OUT onException(IN index, TaskContext tc, ESContext ctx);
}
