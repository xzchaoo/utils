package com.xzchaoo.utils.thread;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 生命周期与一个线程的执行进行绑定
 * Created by zcxu on 2017/3/9.
 */
public abstract class SmartThreadRunner extends SmartLifecycleSupport {
	private Thread thread;

	private boolean interruptWhenStop = false;

	private long joinThreadMills = -1;

	/**
	 * 线程的执行是否被打断
	 */
	protected final AtomicBoolean interrupted = new AtomicBoolean(false);

	private volatile boolean threadRunning = false;

	public SmartThreadRunner() {
		this(false, -1);
	}

	public SmartThreadRunner(boolean interruptWhenStop, long joinThreadMills) {
		this.interruptWhenStop = interruptWhenStop;
		this.joinThreadMills = joinThreadMills;
	}

	protected abstract void doRun();

	protected void run0() {
		threadRunning = true;
		try {
			doRun();
		} finally {
			threadRunning = false;
		}
	}

	/**
	 * 创建线程
	 *
	 * @return
	 */
	protected Thread createThread() {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				run0();
			}
		});
	}

	/**
	 * 在线程启动之前
	 *
	 * @param thread
	 */
	protected void beforeThreadStart(Thread thread) {
	}

	protected void doStart() {
		thread = createThread();
		if (thread == null) {
			throw new IllegalStateException("thread不能为null");
		}
		beforeThreadStart(thread);
		//启动线程
		thread.start();
		//threadRunning = true;
	}

	protected void doStop() {
		if (thread == null) {
			return;
		}

		//打断
		if (interruptWhenStop) {
			thread.interrupt();
		}

		//等待
		if (joinThreadMills >= 0) {
			try {
				thread.join(joinThreadMills);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public Thread getThread() {
		return thread;
	}

	public boolean isInterruptWhenStop() {
		return interruptWhenStop;
	}

	public void setInterruptWhenStop(boolean interruptWhenStop) {
		this.interruptWhenStop = interruptWhenStop;
	}

	public long getJoinThreadMills() {
		return joinThreadMills;
	}

	public void setJoinThreadMills(long joinThreadMills) {
		this.joinThreadMills = joinThreadMills;
	}

	public boolean isInterrupted() {
		return interrupted.get();
	}

	public boolean isThreadRunning() {
		return threadRunning;
	}
}
