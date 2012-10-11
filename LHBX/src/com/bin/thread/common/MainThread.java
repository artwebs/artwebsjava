package com.bin.thread.common;

import java.util.ArrayList;

import com.bin.quartz.LHBJob;
import com.bin.utils.Utils;


public class MainThread {
	private static String content="";
	private static ArrayList<DaemonThread> dlist=new ArrayList<DaemonThread>();
	private static ArrayList<ThreadPool> plist=new ArrayList<ThreadPool>();
	private static ArrayList<LHBJob> jlist=new ArrayList<LHBJob>();
	public static void start(){
		getObjects();
		for(int i=0;i<dlist.size();i++){
			dlist.get(i).setFlag(true);
			dlist.get(i).start();
		}
		for(int i=0;i<plist.size();i++){
			plist.get(i).startThread();
		}
		for(int i=0;i<jlist.size();i++){
			jlist.get(i).shutup();
		}
		
	}
	public static void stop(){
		for(int i=0;i<dlist.size();i++){
			dlist.get(i).setFlag(false);
			dlist.get(i).stop();
		}
		for(int i=0;i<plist.size();i++){
			plist.get(i).stopThread();
		}
		for(int i=0;i<jlist.size();i++){
			jlist.get(i).shutdown();
		}
	}
	
	private static void getObjects(){
		content=Utils.realFile(Utils.getRootPath()+"WEB-INF/common.xml");
		ArrayList<String> dst=new ArrayList<String>();
		ArrayList<String> pst=new ArrayList<String>();
		ArrayList<String> jst=new ArrayList<String>();
		String dststr=Utils.getMarkString(content, "<daemon>", "</daemon>");
		String pststr=Utils.getMarkString(content, "<pool>", "</pool>");
		String jststr=Utils.getMarkString(content, "<job>", "</job>");
		dst=Utils.getMarkStringList(dststr, "<thread>", "</thread>");
		pst=Utils.getMarkStringList(pststr, "<thread>", "</thread>");
		jst=Utils.getMarkStringList(jststr, "<thread>", "</thread>");
		dlist.clear();
		plist.clear();
		jlist.clear();
		for(int i=0;i<dst.size();i++){
			dlist.add((DaemonThread)getObject(dst.get(i)));			
		}
		for(int i=0;i<pst.size();i++){
			plist.add((ThreadPool)getObject(pst.get(i)));
		}
		for(int i=0;i<jst.size();i++){
			jlist.add((LHBJob)getObject(jst.get(i)));
		}
		
	}
	
	private static Object getObject(String cls) {
		Object single=null;
		Class objclass=null;
		try {
			objclass=Class.forName(cls);
			single=objclass.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}finally{
			
		}
		return single;
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
