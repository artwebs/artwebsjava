package com.bin.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bin.log.Log;

public class LHBJob {
	private static List<Trigger> triggerList=new ArrayList<Trigger>();
	private static List<JobDetail> jobList=new ArrayList<JobDetail>();
	private static  Scheduler scheduler = null;   
	
	public static void setScheduleJob(JobDetail job,Trigger trigger){
		jobList.add(job);
		triggerList.add(trigger);		
	}
	
	public static void setQuartz(){
		try {
			scheduler= StdSchedulerFactory.getDefaultScheduler();
			for(int i=0;i<jobList.size();i++){
				if(i<triggerList.size()){
					scheduler.scheduleJob(jobList.get(i),triggerList.get(i));
				}else{
					Log.getLogger();
					Log.setError("计划缺少触发器");
					break;
				}
			}
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public static void shutup(){
		setQuartz();
		if(scheduler!=null){
			try {
				scheduler.start();
				scheduler=null;
			} catch (SchedulerException e) {
				Log.getLogger();
				Log.setError("计划启动失败！");
			}finally{
				
			}
		}else{
			Log.getLogger();
			Log.setError("计划停止时，计划为空");
		}
	}
	
	public static void shutdown(){
		if(scheduler!=null){
			try {
				scheduler.shutdown(true);
				scheduler=null;
			} catch (SchedulerException e) {
				Log.getLogger();
				Log.setError("计划停止失败！");
				e.printStackTrace();
			}finally{
				
			}
		}else{
			Log.getLogger();
			Log.setError("计划停止时，计划为空");
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobDetail job=new JobDetail("job1", "group1", HelloJob.class);
		Trigger trigger = new SimpleTrigger("trigger1", "group1", new Date(),null,SimpleTrigger.REPEAT_INDEFINITELY,2L * 1000L);
		
//		JobDetail job=new JobDetail("job1", "group1", HelloJob.class);
//		Trigger trigger=null;
//		try {
//			trigger = new  CronTrigger("CronTrigger", null,  "10,20,30 * * * * ?");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		LHBJob.setScheduleJob(job, trigger);
		LHBJob.shutup();

	}

}
