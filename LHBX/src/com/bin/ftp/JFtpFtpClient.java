package com.bin.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import net.sf.jftp.config.Settings;
import net.sf.jftp.net.BasicConnection;
import net.sf.jftp.net.ConnectionHandler;
import net.sf.jftp.net.ConnectionListener;
import net.sf.jftp.net.FtpConnection;
import org.apache.commons.lang.StringUtils; 


public class JFtpFtpClient implements ConnectionListener,IFtp {
	// is the connection established?   
    private boolean isThere = false;   
       
    public static long time = 0;   
       
    // connection pool, not necessary but you should take a look at this class   
    // if you want to use multiple event based ftp transfers.   
    private ConnectionHandler handler = new ConnectionHandler();   
   
    private String host;   
    private int port = 21;   
    private String user;   
    private String passwd;   
    public JFtpFtpClient(String host, String user, String passwd){  
    	this.host = host;  
    	this.user = user;  
    	this.passwd = passwd;  
    }  
    public JFtpFtpClient(String host, int port, String user, String passwd){   
        this.host = host;   
        this.port = port;   
        this.user = user;   
        this.passwd = passwd;   
    }   
       
    //creates a FtpConnection and downloads a file   
    public byte[] downloadToBinary(String file)   
    {   
        
        Settings.bufferSize = 16384;  
        long current = System.currentTimeMillis();    
        FtpConnection con = new FtpConnection(host, port, "/"); 
        con.addConnectionListener(this); 
        con.setConnectionHandler(handler);  
        con.login(user, passwd);    
        while(!isThere)   
        {   
            try { Thread.sleep(10); }   
            catch(Exception ex) { ex.printStackTrace(); }   
        }   
   
        byte[] bytes = null;   
        try{    
            InputStream is =  con.getDownloadInputStream(file);   
            ByteArrayOutputStream bais = new ByteArrayOutputStream();   
            int bit = 0;   
            while((bit = is.read()) != -1){   
                bais.write(bit);   
            }   
            bytes = bais.toByteArray();   
        }catch(Exception e){}   
        time = (System.currentTimeMillis()-current);
        //System.out.println("Download took "+time+"ms.");
        return bytes;   
    }   
    public byte[] downloadToBinary(String file,String changDir)   
    {   
        
        Settings.bufferSize = 16384;  
        long current = System.currentTimeMillis();    
        FtpConnection con = new FtpConnection(host, port, changDir); 
        con.addConnectionListener(this); 
        con.setConnectionHandler(handler);  
        con.login(user, passwd);    
        while(!isThere)   
        {   
            try { Thread.sleep(10); }   
            catch(Exception ex) { ex.printStackTrace(); }   
        }   
   
        byte[] bytes = null;   
        try{    
            InputStream is =  con.getDownloadInputStream(file);   
            ByteArrayOutputStream bais = new ByteArrayOutputStream();   
            int bit = 0;   
            while((bit = is.read()) != -1){   
                bais.write(bit);   
            }   
            bytes = bais.toByteArray();   
        }catch(Exception e){}   
        time = (System.currentTimeMillis()-current);
        //System.out.println("Download took "+time+"ms.");
        return bytes;   
    }   
    
    public int upload(String dir, String file){  
        FtpConnection con = new FtpConnection(host, port, "/"); 
        con.addConnectionListener(this);  
        con.setConnectionHandler(handler); 
        con.login(user, passwd);
        while(!isThere)  
        {  
            try { Thread.sleep(10); }  
            catch(Exception ex) { ex.printStackTrace(); }  
        }  
          
        //make dirs  
        String path = "";  
        String[] paths = StringUtils.split(dir, "/");  
        for(int i = 0; i < paths.length; i++){  
            path += "/" + paths[i];  
            if(!con.chdir(path)){ con.mkdir(path); }  
        }  
        con.chdir(dir);  
        return con.upload(file);  
    } 
    
    
   
// ------------------ needed by ConnectionListener interface -----------------   
   
    // called if the remote directory has changed   
    public void updateRemoteDirectory(BasicConnection con)   
    {   
        //System.out.println("new path is: " + con.getPWD());   
    }   
   
    // called if a connection has been established   
    public void connectionInitialized(BasicConnection con)   
    {   
        isThere = true;   
    }   
    
    // called every few kb by DataConnection during the trnsfer (interval can be changed in Settings)   
    public void updateProgress(String file, String type, long bytes) {}   
   
    // called if connection fails   
    public void connectionFailed(BasicConnection con, String why) {System.out.println("connection failed!");}   
   
    // up- or download has finished   
    public void actionFinished(BasicConnection con) {}   


	public int downFile(String fname, String file) {
		int rsInt=0;
		byte[] bs=this.downloadToBinary(fname);
		File f = new File(file);
        try {
        	if(bs!=null){
				FileOutputStream fos=new FileOutputStream(f);
				fos.write(bs);
				rsInt=1;
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsInt;
	}
	public int downFile(String fname, String file,String changDir) {
		int rsInt=0;
		byte[] bs=this.downloadToBinary(fname,changDir);
		File f = new File(file);
        try {
        	if(bs!=null){
				FileOutputStream fos=new FileOutputStream(f);
				fos.write(bs);
				rsInt=1;
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsInt;
	}

	public int uploadFile(String dir, String file) {
		int rsInt=this.upload(dir, file);
		return rsInt;
	}
	public Boolean  isFileExists(String fname){
		    boolean flag=false;
		    Settings.bufferSize = 16384;    
		    FtpConnection con = new FtpConnection(host, port, "/"); 
	        con.addConnectionListener(this); 
	        con.setConnectionHandler(handler);  
	        con.login(user, passwd);    
	        while(!isThere)   
	        {   
	            try { Thread.sleep(10); }   
	            catch(Exception ex) { ex.printStackTrace(); }   
	        }   
  
	        try{    
	            InputStream is =  con.getDownloadInputStream(fname); 
	            ByteArrayOutputStream bais = new ByteArrayOutputStream();   
	            int bit = 0;   
	            while((bit = is.read()) != -1){   
	                bais.write(bit); 
	                flag=true; 
	                break;
	            } 

	        }catch(Exception e){
	        	
	        }finally{
	        	con.disconnect();
	        }
		
		return flag;
		
	}
	public Boolean  isFileExists(String fname,String changedir){
	    boolean flag=false;
	    Settings.bufferSize = 16384;    
	    FtpConnection con = new FtpConnection(host, port, "/"); 
        con.addConnectionListener(this); 
        con.setConnectionHandler(handler);  
        con.login(user, passwd);    
        while(!isThere)   
        {   
            try { Thread.sleep(10); }   
            catch(Exception ex) { ex.printStackTrace(); }   
        }   

        try{    
            InputStream is =  con.getDownloadInputStream(fname); 
            ByteArrayOutputStream bais = new ByteArrayOutputStream();   
            int bit = 0;   
            while((bit = is.read()) != -1){   
                bais.write(bit); 
                flag=true; 
                break;
            } 

        }catch(Exception e){
        	
        }finally{
        	con.disconnect();
        }
	
        return flag;
	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFtpFtpClient f=new JFtpFtpClient("localhost", 22, "www","www");
		int rs=f.downFile("1111.txt", "D:/temp/ftp/11112.txt");
//        
		//int rs=f.uploadFile("/", "D:/temp/ftp/1111.txt");
//		System.out.println(rs);
		//System.out.println(f.isFileExists("TRAN_MER_888002493390000_20260122.dat"));


	}

}
