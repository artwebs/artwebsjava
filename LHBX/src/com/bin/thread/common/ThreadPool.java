package com.bin.thread.common;

import com.bin.thread.IThreadPool;
import com.bin.thread.IThreadPoolFixedImpl;

public class ThreadPool {
	private static IThreadPool tp=new IThreadPoolFixedImpl(30);

	public static void setThread(Runnable thread){
		tp.add(thread);
	}
	public static void setThread(Thread thread,int second){
		tp=new IThreadPoolFixedImpl(second);
		tp.add(thread);
	}
	public static void startThread(){	
		tp.start();
	}
	public static void stopThread(){
		tp.stop();
	}
}
