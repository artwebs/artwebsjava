package com.bin.socket;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerModel extends Thread {
	private int serverPort=9080;	
	private ServerSocket socket=null;
	private Boolean flag=true;
	public void getInstance(){
		try {
			this.socket=new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public void run(){
		this.getInstance();
		while(flag){
			try {
				this.socket.accept();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
