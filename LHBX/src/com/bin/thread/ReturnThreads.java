package com.bin.thread;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.bin.object.BinMap;

public class ReturnThreads{
	private ExecutorService exec;
	private int num=10;
	private CompletionService<BinMap> serv;
	private int timeout=10;

	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public ReturnThreads (){
		exec=Executors.newFixedThreadPool(num);
		serv=new ExecutorCompletionService<BinMap>(exec);
		
	}
	public ReturnThreads (Integer num){
		this.num=num;
		exec=Executors.newFixedThreadPool(num);
		serv=new ExecutorCompletionService<BinMap>(exec);
	}
	
	public Boolean add(Callable<BinMap> thread) {
		Boolean flag=false;
		if(serv!=null){
			serv.submit(thread);
			flag=true;
		}else{
			System.out.println("线程池已经关闭！");
		}
		return flag;
	}
	
	public BinMap getResult(){
		BinMap rslm=new BinMap();
		long time=5*1000L;
		System.out.println("开始等待线程完成");
		long start=0;
		long end=0;
		for(int i=0;i<6;i++){
			try {				
				start=System.currentTimeMillis();
				Future<BinMap> task=serv.poll(time,TimeUnit.MILLISECONDS);
				end=System.currentTimeMillis();	
				System.out.println(end-start);
				BinMap obj=null;
				if(task!=null)
					try {
						obj=task.get();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.out.println(new Date()+" "+obj);
				time=time-(end-start);
				if(time<0){
					time=0;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("完成");
		exec.shutdown();
		return rslm;
	}

	public Boolean start(){
		Boolean flag=false;
	    try {
	    	exec=Executors.newFixedThreadPool(num);
			serv=new ExecutorCompletionService<BinMap>(exec);
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
