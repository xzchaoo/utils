package com.xzchaoo.utils.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 对 Spring 的 SmartLifecycle 提供了基础的支持
 * TODO 现在功能还不完善 先将大部分变量设置成protected
 * Created by zcxu on 2017/3/9.
 */
public abstract class SmartLifecycleSupport {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 是否正在运行
	 */
	private final AtomicBoolean running = new AtomicBoolean(false);
	/**
	 * stop是否已经被调用过了
	 */
	private volatile boolean stopCalled = false;

	protected abstract void doStart();

	public void start() {
		//防止重复启动
		if (running.compareAndSet(false, true)) {
			doStart();
		}
	}

	protected abstract void doStop();

	public void stop() {
		//防止重复停止
		if (running.compareAndSet(true, false)) {
			//shunxu
			stopCalled = true;
			doStop();
		}
	}

	public boolean isRunning() {
		return running.get();
	}

	/**
	 * stop是否已经执行过了
	 *
	 * @return
	 */
	public boolean isStopCalled() {
		return stopCalled;
	}

	/**
	 * 特殊方法 在子类的 doStart 方法里就已经想要停止运行了 (比如初始化失败)
	 */
	protected void stopWhenStarting() {
		running.set(false);
		stopCalled = true;
	}

	protected void checkStop() {
		if (stopCalled) {
			throw new IllegalStateException("该Consumer已经被关闭");
		}
	}
}
