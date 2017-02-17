package com.xzchaoo.utils.buffer;

import java.util.List;

/**
 * 一个 buffer 的封装, 适用于如下的场景:
 * 1. 当对象积累到1000个的时候, 才进行一次批量的数据库写操作, 以提升性能
 * <p>
 * 子类应该要保证线程安全
 * Created by Administrator on 2016/11/12.
 */
public interface Buffer<T> {
	/**
	 * 添加一个元素到 buffer 里, 如果 buffer 满了 那么将会触发一次flush(true)
	 *
	 * @param t
	 */
	void add(T t);

	/**
	 * 手动触发一次 flush 操作, 因为默认情况下只有当 buffer 满了才会flush
	 *
	 * @param forceClear 即使发生了异常也要将 buffer 清空, 一般 flush 操作应该不要抛出异常
	 */
	void flush(boolean forceClear);

	/**
	 * 清理掉buffer(不会flush)
	 *
	 * @return 当前buffer的内容
	 */
	List<T> clear();
}
