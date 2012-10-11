package com.bin.ftp.LHBftp4j;

import java.io.File;
import java.util.Locale;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager; 

import com.bin.ftp.FtpModel;
import com.bin.ftp.IAFtpClient;
import com.bin.object.BinList;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPConnector;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.connectors.DirectConnector;
import it.sauronsoftware.ftp4j.connectors.SOCKS4Connector;
import it.sauronsoftware.ftp4j.connectors.SOCKS5Connector;

public class Ftp4jClient extends IAFtpClient{
	
    protected FTPClient client = new FTPClient(); 

    public FtpModel getFtpModel() {
		return ftpModel;
	}

	public void setFtpModel(FtpModel ftpModel) {
		this.ftpModel = ftpModel;
	}
    public Ftp4jClient() {   
  	  
    }   
	public Ftp4jClient(FtpModel ftpModel) {  
        this.ftpModel = ftpModel;
        this.returnClient();
    }
 
    /**
     * 返回客户端
     */
    public void returnClient(){
       try { 
    	   		if(ftpModel.isSslsocket())
		   	    {
    	   			
		   	    	TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
			    		   public X509Certificate[] getAcceptedIssuers() {
			    		   return null;
			    		   }
			    		   public void checkClientTrusted(X509Certificate[] certs, String authType) {
			    		   }
			    		   public void checkServerTrusted(X509Certificate[] certs, String authType) {
			    		   }
			    		   }
	     	   	    };
	     		   SSLContext sslContext = null;
	     		   try {
		     		   sslContext = SSLContext.getInstance("SSL");
		     		   sslContext.init(null, trustManager, new SecureRandom());
	     		   } catch (Exception e) {
	     			   e.printStackTrace();
	     		   }
	     		   SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
	     		   client.setSSLSocketFactory(sslSocketFactory);
	     		   client.setSecurity(FTPClient.SECURITY_FTP); 

		   	    }
    	   		
    	   		client.setPassive(ftpModel.isPasv());
                client.connect(ftpModel.getHost(),ftpModel.getPort()); 
                client.login(ftpModel.getUsername(), ftpModel.getPassword());
       
          } catch (Exception e) { 
                  System.out.println(e);
          } finally{
        	  
          }
    }
    
    /**
     * FTP下载文件到本地一个文件夹,如果本地文件夹不存在，则创建必要的目录结构 
     * @param remoteFileName    FTP文件  
     * @param localFolderPath   存的本地目录 
     * @return 0下载成功  -1下载失败 -2下载文件存在 -3下载保存的地方是一个文件
     */
	public Integer downloadFile(String remoteFileName, String localFolderPath){
		  Integer rsInt=-1;
		  int x = this.isExist(remoteFileName); 
          FtpListener listener = FtpListener.instance(FTPOptType.DOWN); 
          File localFolder = new File(localFolderPath);
          if (localFolder.isFile()) {
        	      rsInt=-3;
                  System.out.println("所要的下载保存的地方是一个文件，无法保存！"); 
                 
          } else { 
              if (!localFolder.exists()) 
                          localFolder.mkdirs(); 
          
	          if (x == FTPFile.TYPE_FILE) { 
	                  String localfilepath = PathToolkit.formatPath4File(localFolderPath + File.separator + new File(remoteFileName).getName()); 
	                  try { 
	                          if (listener != null) {
	                        	  client.download(remoteFileName, new File(localfilepath), listener); 
	                          }                                 
	                          else {
	                        	  client.download(remoteFileName, new File(localfilepath)); 
	                          }
	                          if(listener.getComplete())rsInt=0;
	                                
	                  } catch (Exception e) { 
	                          throw new FTPRuntimeException(e); 
	                  } 
	          } else { 
	                  rsInt=-2;
	                  System.out.println("所要下载的文件" + remoteFileName + "不存在！");
	          } 	         
          }
		return rsInt;		
	}
	
	/**
	 * FTP上传本地文件到FTP的当前目录下 
	 * @param localFileName    本地文件路径 及文件名
	 * @return 0 上传成功  -1上传失败 -2上传文件不存在 -3所要上传的FTP文件是个文件夹
	 */
	public Integer uploadFile(String localFileName){
		File localfile = new File(localFileName); 
		Integer rsInt=-1;
         FtpListener listener = FtpListener.instance(FTPOptType.UP); 
         try { 
                 if (!localfile.exists()){
                	 rsInt=-2; 
                	 System.out.println("所要上传的FTP文件" + localfile.getPath() + "不存在！"); 
                 }
                 if (!localfile.isFile()){
                	 rsInt=-3; 
                	 System.out.println("所要上传的FTP文件" + localfile.getPath() + "是一个文件夹！"); 
                 }
                 if (listener != null) 
                         client.upload(localfile, listener); 
                 else 
                         client.upload(localfile); 
                 if(listener.getComplete())rsInt=0; 
                 //client.changeDirectory("/"); 
         } catch (Exception e) { 
        	 rsInt=-1; 
         } 
		return rsInt;		
	}
	
	
	/**
	 * FTP上传本地文件到FTP的一个目录下 
	 * @param localFileName    本地文件路径 及文件名
	 * @param remoteFolderPath FTP上传目录
	 * @return 0 上传成功  -1上传失败 -2上传文件不存在 -3所要上传的FTP文件是个文件夹
	 */
	public Integer uploadFile(String localFileName, String remoteFolderPath){
		File localfile = new File(localFileName); 
		Integer rsInt=-1;
		 remoteFolderPath = PathToolkit.formatPath4FTP(remoteFolderPath); 
         FtpListener listener = FtpListener.instance(FTPOptType.UP); 
         try { 
                 client.changeDirectory(remoteFolderPath); 
                 if (!localfile.exists()){
                	 rsInt=-2; 
                	 System.out.println("所要上传的FTP文件" + localfile.getPath() + "不存在！"); 
                 }
                 if (!localfile.isFile()){
                	 rsInt=-3; 
                	 System.out.println("所要上传的FTP文件" + localfile.getPath() + "是一个文件夹！"); 
                 }
                 if (listener != null) 
                         client.upload(localfile, listener); 
                 else 
                         client.upload(localfile); 
                 if(listener.getComplete())rsInt=0; 
                 client.changeDirectory("/"); 
         } catch (Exception e) { 
        	 rsInt=-1; 
         } 
		return rsInt;		
	}


	/**
	 *  判断一个FTP路径是否存在，如果存在返回类型(FTPFile.TYPE_DIRECTORY=1、FTPFile.TYPE_FILE=0、FTPFile.TYPE_LINK=2)
	 * @param remotePath FTP文件或文件夹路径
	 * @return 存在时候返回类型值(文件0，文件夹1，连接2)，不存在则返回-1 
	 */
	
	public Integer isExist(String remotePath){
		Integer rsInt=0;
		remotePath = PathToolkit.formatPath4FTP(remotePath); 
        FTPFile[] list = null; 
        try { 
                list = client.list(remotePath); 
        } catch (Exception e) { 
        	rsInt= -1; 
        } 
        if (list.length > 1) rsInt=FTPFile.TYPE_DIRECTORY; 
        else if (list.length == 1) { 
                FTPFile f = list[0]; 
                if (f.getType() == FTPFile.TYPE_DIRECTORY)rsInt= FTPFile.TYPE_DIRECTORY; 
                //假设推理判断 
                String _path = remotePath + "/" + f.getName(); 
                try { 
                        int y = client.list(_path).length; 
                        if (y == 1)rsInt=FTPFile.TYPE_DIRECTORY; 
                        else rsInt=FTPFile.TYPE_FILE; 
                } catch (Exception e) { 
                	rsInt=FTPFile.TYPE_FILE; 
                } 
        } else { 
                try { 
                        client.changeDirectory(remotePath); 
                        rsInt=FTPFile.TYPE_DIRECTORY; 
                } catch (Exception e) { 
                	rsInt=-1; 
                } 
        } 
		return rsInt;	
	}
	/**
	 * 取得当前文件夹
	 * @return 当前目录
	 */
	public String getCurrentDirectory(){
		String rsStr="";
		try {
			rsStr=client.currentDirectory();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
		return rsStr;		
	}
	
	/**
	 * 改变当前文件夹
	 * @param newPath 新目录
	 * @return 0成功 -1失败
	 */
	public Integer changeDirectory(String newPath){
		Integer rsInt=0;
		try {
			client.changeDirectory(newPath);
		} catch (Exception e) {
			rsInt=-1;
			e.printStackTrace();
		} finally{
			
		}
		return rsInt;		
	}
	/**
	 * 回退到上级目录
	 * @return 0成功 -1失败
	 */
	public Integer changeDirectoryUp(){
		Integer rsInt=0;
		try {
			client.changeDirectoryUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rsInt=-1;
		} finally{
			
		}
		return rsInt;		
	}
	
	/**
	 * 重新命名文件夹或文件名 ,移动文件
	 * @param oldname 原来的名称
	 * @param newname 新的名称
	 * @return 0成功 -1失败
	 */
	
	public Integer rename(String oldname,String newname){
		Integer rsInt=0;
		try {
			client.rename(oldname, newname);
		} catch(Exception e) {
			rsInt=-1;
			e.printStackTrace();
		} finally{
			
		}
		return rsInt;		
	}
	
	/**
	 * 删除文件
	 * @param fileName 要删除的文件名
	 * @return 0成功 -1失败
	 */
	
	public Integer deleteFile(String fileName){
		Integer rsInt=0;
		try {
			client.deleteFile(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rsInt=-1;
			e.printStackTrace();
		} finally{
			
		}
		return rsInt;	
	}
	
	/**
	 * 创建目录
	 * @param newPath 要创建的目录名称
	 * @return  0成功 -1失败
	 */
	public Integer createDirectory(String newPath){
		Integer rsInt=0;
		try {
			client.createDirectory(newPath);
		} catch (Exception e) {
			rsInt=-1;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		return rsInt;	
	}
	/**
	 * 删除目录
	 * @param Path 要删除目录名称
	 * @return  0成功 -1失败
	 */
	public Integer deleteDirectory(String Path){
		Integer rsInt=0;
		try {
			client.deleteDirectory(Path);
		} catch (Exception e) {
			rsInt=-1;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rsInt;	
	}
	
	/**
	 * 获取文件列表
	 * @param pattern 通配浏览文件 为""是全部文件
	 * @return FTPFile
	 */	
	public FTPFile[] getFileList(String pattern){
		FTPFile[] ftplist=null;
		try {
			if(pattern.equals("")){
				ftplist = client.list();
			}else{
				ftplist = client.list(pattern);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return ftplist;
		
	}
	/**
	 * 获得文件的属性（修改时间、大小）
	 * @param fileName 文件名
	 * @return [{modifiedDate=2010-06-05 01:35:01}, {size=188}]
	 */
    public BinMap getFileProperties(String fileName){
    	BinMap rslm=new BinMap();
    	try {
    		
			rslm.put("modifiedDate",Utils.convertDateMode(client.modifiedDate(fileName).toString(), "yyyy-MM-dd hh:mm:ss"));
	    	rslm.put("size",client.fileSize(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

    	return rslm;
    }
	/**
	 * 获得文件列表
	 * @param pattern 通配浏览文件 为""是全部文件
	 * @return [{name=SMSOUT00000000000040.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=180}, {name=SMSOUT00000000000041.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=180}, {name=SMSOUT00000000000042.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000043.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000044.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000046.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000047.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000048.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000049.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000050.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=188}]
	 */
	public BinList getFileLHBList(String pattern){

		BinList  rslist=new BinList();
		FTPFile[] ftplist=this.getFileList(pattern);
		if(ftplist!=null){
			for(int i=0;i<ftplist.length;i++){
				FTPFile file=ftplist[i];
				String filetime=Utils.convertDateMode(file.getModifiedDate().toString(),"EEE MMM dd HH:mm:ss zzz yyyy","yyyy-MM-dd hh:mm:ss",Locale.US);
				rslist.put(i, "name", file.getName());
				rslist.put(i, "FILE", file.getType());
				rslist.put(i, "size", file.getSize());				
				rslist.put(i, "modifiedDate", filetime);
			}
		}
		return rslist;
	}
	/**
	 * 设置传输类型
	 * @param type ASC码FTPClient.TYPE_TEXTUAL 二进制 FTPClient.TYPE_BINARY  自动选择(根据文件内容) FTPClient.TYPE_AUTO
	 */
    public void setType(Integer type){
    	client.setType(type);
    }
    /**
     * SSL 套接字连接
     * @param connector it.sauronsoftware.ftp4j.connectors.SSLConnector  SSL 套接字连接
     */
	public void setConnector(FTPConnector connector){
		client.setConnector(connector);
	}
	/**
	 * 关闭FTP连接，关闭时候像服务器发送一条关闭命令
	 * @return 关闭成功，或者链接已断开，或者链接为null时候返回true，通过两次关闭都失败时候返回false 
	 */
	public boolean closeConnection() { 
        if (client == null) return true; 
        if (client.isConnected()) { 
           try { 
                        client.disconnect(true); 
                        return true; 
                } catch (Exception e) { 
                        try { 
                                client.disconnect(false); 
                        } catch (Exception e1) { 
                                e1.printStackTrace(); 
                                return false; 
                        } 
                } 
        } 
        
        return true; 
	} 
	public void ftptest()
	{
		Ftp4jClient ftp=new Ftp4jClient(new FtpModel("192.168.25.50",21,"out","0871","/",true,true));
		BinList list=ftp.getFileLHBList("Police*Swap.zip");
		for(int i=0;i<list.size();i++){
			System.out.println((String)list.getValue(i, "modifiedDate"));
		}
	}
	public static void main(String[] args){
//		Ftp4jClient ftp=new Ftp4jClient(new FtpModel("192.168.25.50",21,"out","0871","/",true,true));
		//Integer rsInt=-1;
		//rsInt=ftp.isExist("test");
		//rsInt=ftp.downloadFile("SMSOUT00000000000040.zip", "D:/temp/ftp/");
		//rsInt=ftp.uploadFile("D:\\temp\\ftp\\1.txt", "/");
		//System.out.println(ftp.getCurrentDirectory());
		//System.out.println(ftp.changeDirectory("/test"));
		//rsInt=ftp.uploadFile("D:\\temp\\ftp\\1.txt");
		//System.out.println(rsInt);
		//System.out.println(ftp.changeDirectoryUp());
		//System.out.println(ftp.rename("SMSOUT00000000000045.zip", "/test/SMSOUT00000000000045.zip"));
		//System.out.println(ftp.deleteFile("3.txt"));
		//System.out.println(ftp.createDirectory("aaa"));
		//System.out.println(ftp.deleteDirectory("aaa"));
		//System.out.println(ftp.getFileLHBList("*.zip").getItem());
		
		//FTPFile[] files=ftp.getFileList("");
		//System.out.println(files[2]);
		//System.out.println(ftp.getFileProperties("SMSOUT00000000000050.zip").getItem());
		
		//Ftp4jClient ftp=new Ftp4jClient(new FtpModel("211.139.26.236",21,"yibao","yunnan~!#2010yibao","/uploads/ftp"));
		//ftp.downloadFile("TRAN_MER_888002493390000_20100506.dat", "D:/temp/ftp/");
		
//		LHBList list=ftp.getFileLHBList("Police*Swap.zip");
//		for(int i=0;i<list.size();i++){
//			System.out.println((String)list.getValue(i, "modifiedDate"));
//		}
//		Ftp4jClient.ftptest();
	}

}
