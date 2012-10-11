package com.bin.thread;

public interface IThreadPool {
	public Boolean add(Runnable thread);
	public Boolean start();
	public Boolean stop();
}
