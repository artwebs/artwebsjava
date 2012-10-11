package com.bin.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IThreadPoolFixedImpl implements IThreadPool {
	private ExecutorService exec;
	private int num=10;
	public IThreadPoolFixedImpl (){
		exec=Executors.newFixedThreadPool(num);
	}
	public IThreadPoolFixedImpl (Integer num){
		this.num=num;
		exec=Executors.newFixedThreadPool(num);
	}
	public Boolean add(Runnable thread){
		Boolean flag=false;
		if(exec!=null){
			exec.submit(thread);
			flag=true;
		}else{
			System.out.println("线程池已经关闭！");
		}
		return flag;
	}
	public Boolean start(){
		Boolean flag=false;
	    try {
	    	exec=Executors.newFixedThreadPool(num);
	    	flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			
		}
		
		return flag;
	}
	public Boolean stop(){
		Boolean flag=false;
		try {
			exec.shutdown();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}
}
