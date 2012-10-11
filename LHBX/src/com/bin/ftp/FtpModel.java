package com.bin.ftp;

public class FtpModel {
    private String username="anonymous";  
    private String password="anonymous";  
    private String host;   
    private int port=21;   
    private String remoteDir="/";
    private boolean pasv=true;
    private boolean sslsocket=false;
    

    public boolean isPasv() {
		return pasv;
	}
	public void setPasv(boolean pasv) {
		this.pasv = pasv;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getRemoteDir() {
		return remoteDir;
	}
	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}
	
	
	public boolean isSslsocket() {
		return sslsocket;
	}
	public void setSslsocket(boolean sslsocket) {
		this.sslsocket = sslsocket;
	}
	public FtpModel(String host,int port) {   
        this.host = host;   
        this.port = port;     
    }
	public FtpModel(String host,int port,String remoteDir) {   
        this.host = host;   
        this.port = port;   
        this.remoteDir = remoteDir;   
    } 
    public FtpModel(String host,String username, String password) {   
        this.username = username;   
        this.password = password;   
        this.host = host;     
    }  
    public FtpModel(String host,String username, String password, String remoteDir) {   
        this.username = username;   
        this.password = password;   
        this.host = host;     
        this.remoteDir = remoteDir;   
    }   
    public FtpModel(String host,int port,String username, String password) {   
        this.username = username;   
        this.password = password;   
        this.host = host;   
        this.port = port;   
    } 
    public FtpModel(String host,int port,String username, String password, String remoteDir) {   
        this.username = username;   
        this.password = password;   
        this.host = host;   
        this.port = port;   
        this.remoteDir = remoteDir;   
    } 
    
    public FtpModel(String host,int port,String username, String password, String remoteDir,boolean pasv) {   
        this.username = username;   
        this.password = password;   
        this.host = host;   
        this.port = port;   
        this.remoteDir = remoteDir;   
        this.pasv=pasv;
    } 
    
    public FtpModel(String host,int port,String username, String password, String remoteDir,boolean pasv,boolean sslsocket) {   
        this.username = username;   
        this.password = password;   
        this.host = host;   
        this.port = port;   
        this.remoteDir = remoteDir;   
        this.pasv=pasv;
        this.sslsocket=sslsocket;
    } 


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
