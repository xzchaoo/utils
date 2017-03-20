package com.xzchaoo.utils.concurrent.n;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public interface ESOutput<OUT> {
	void add(int index, OUT out);

	List<OUT> getList();
}
