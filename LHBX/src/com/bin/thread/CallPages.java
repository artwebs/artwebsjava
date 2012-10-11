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
import com.bin.utils.Utils;

public class CallPages{
	private ExecutorService exec;
	private CompletionService<BinMap> serv;
	private int timeout=10;
	private BinMap inlm=new BinMap();
	public CallPages (BinMap inlm){
		this.inlm=inlm;
		exec=Executors.newFixedThreadPool(this.inlm.size());
		serv=new ExecutorCompletionService<BinMap>(exec);
		
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void run() {
		if(serv!=null){
			for(int i=0;i<this.inlm.size();i++){
				CallPageThread thread=new CallPageThread(this.inlm.getKey(i).toString(),this.inlm.getValue(i).toString());
				serv.submit(thread);
			}
		}
	}

	public BinMap getResult(){
		BinMap rslm=new BinMap();
		long time=this.timeout*1000;
//		System.out.println("开始等待线程完成");
		long start=0;
		long end=0;
		for(int i=0;i<this.inlm.size();i++){
			try {				
				start=System.currentTimeMillis();
				Future<BinMap> task=serv.poll(time,TimeUnit.MILLISECONDS);
				end=System.currentTimeMillis();	
//				System.out.println(end-start);
				if(task!=null){
					BinMap obj=new BinMap();
					try {
						obj = task.get();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rslm.put(obj.getKey(0).toString(), obj.getValue(0).toString());
				}
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
	public static void main(String[] args) {
		BinMap inlm=new BinMap();
		inlm.put("url1", "http://localhost:8686/LHBSystem/index.php?a=1");
		inlm.put("url2", "http://localhost:8686/LHBSystem/index.php?a=2");
		inlm.put("url3", "http://localhost:8686/LHBSystem/index.php?a=3");
		CallPages call=new CallPages(inlm);
		call.run();
		BinMap rs=call.getResult();
		System.out.println(rs.getItem());

	}
}

class CallPageThread implements Callable{
	private String name;
	private String url;
	public CallPageThread(String name,String url){
		this.name=name;
		this.url=url;
	}
	public BinMap call() throws Exception {
		String rs=Utils.submitMessage(this.url, "get");
		BinMap rsmap=new BinMap();
		rsmap.put(this.name, rs);
		return rsmap;
	}
	
}
