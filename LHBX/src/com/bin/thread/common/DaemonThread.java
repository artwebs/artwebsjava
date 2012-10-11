package com.bin.thread.common;

public abstract class DaemonThread extends Thread {
	
	private Boolean flag=true;
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public void run(){
		while(flag){			
			try {				
					this.deal();
				} catch (Exception e) {
					
				}finally{
					
				}			
			try{
				this.sleep(2000);
			}catch (Exception e){
				
			}finally{
				
			}
		}
	}
	
	public abstract void  deal();
}
