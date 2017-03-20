package com.xzchaoo.utils.concurrent.n;

/**
 * Created by Administrator on 2017/3/20.
 */
public interface ESCallback<IN, OUT> {
	OUT process(IN in) throws Exception;
}
