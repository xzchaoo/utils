package com.github.xzchaoo.utils.buffer;

import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */
public interface BufferFlusher<T> {
	void flush(List<T> buffer);
}
