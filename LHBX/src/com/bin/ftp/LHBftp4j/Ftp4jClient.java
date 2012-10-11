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
     * ���ؿͻ���
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
     * FTP�����ļ�������һ���ļ���,��������ļ��в����ڣ��򴴽���Ҫ��Ŀ¼�ṹ 
     * @param remoteFileName    FTP�ļ�  
     * @param localFolderPath   ��ı���Ŀ¼ 
     * @return 0���سɹ�  -1����ʧ�� -2�����ļ����� -3���ر���ĵط���һ���ļ�
     */
	public Integer downloadFile(String remoteFileName, String localFolderPath){
		  Integer rsInt=-1;
		  int x = this.isExist(remoteFileName); 
          FtpListener listener = FtpListener.instance(FTPOptType.DOWN); 
          File localFolder = new File(localFolderPath);
          if (localFolder.isFile()) {
        	      rsInt=-3;
                  System.out.println("��Ҫ�����ر���ĵط���һ���ļ����޷����棡"); 
                 
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
	                  System.out.println("��Ҫ���ص��ļ�" + remoteFileName + "�����ڣ�");
	          } 	         
          }
		return rsInt;		
	}
	
	/**
	 * FTP�ϴ������ļ���FTP�ĵ�ǰĿ¼�� 
	 * @param localFileName    �����ļ�·�� ���ļ���
	 * @return 0 �ϴ��ɹ�  -1�ϴ�ʧ�� -2�ϴ��ļ������� -3��Ҫ�ϴ���FTP�ļ��Ǹ��ļ���
	 */
	public Integer uploadFile(String localFileName){
		File localfile = new File(localFileName); 
		Integer rsInt=-1;
         FtpListener listener = FtpListener.instance(FTPOptType.UP); 
         try { 
                 if (!localfile.exists()){
                	 rsInt=-2; 
                	 System.out.println("��Ҫ�ϴ���FTP�ļ�" + localfile.getPath() + "�����ڣ�"); 
                 }
                 if (!localfile.isFile()){
                	 rsInt=-3; 
                	 System.out.println("��Ҫ�ϴ���FTP�ļ�" + localfile.getPath() + "��һ���ļ��У�"); 
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
	 * FTP�ϴ������ļ���FTP��һ��Ŀ¼�� 
	 * @param localFileName    �����ļ�·�� ���ļ���
	 * @param remoteFolderPath FTP�ϴ�Ŀ¼
	 * @return 0 �ϴ��ɹ�  -1�ϴ�ʧ�� -2�ϴ��ļ������� -3��Ҫ�ϴ���FTP�ļ��Ǹ��ļ���
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
                	 System.out.println("��Ҫ�ϴ���FTP�ļ�" + localfile.getPath() + "�����ڣ�"); 
                 }
                 if (!localfile.isFile()){
                	 rsInt=-3; 
                	 System.out.println("��Ҫ�ϴ���FTP�ļ�" + localfile.getPath() + "��һ���ļ��У�"); 
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
	 *  �ж�һ��FTP·���Ƿ���ڣ�������ڷ�������(FTPFile.TYPE_DIRECTORY=1��FTPFile.TYPE_FILE=0��FTPFile.TYPE_LINK=2)
	 * @param remotePath FTP�ļ����ļ���·��
	 * @return ����ʱ�򷵻�����ֵ(�ļ�0���ļ���1������2)���������򷵻�-1 
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
                //���������ж� 
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
	 * ȡ�õ�ǰ�ļ���
	 * @return ��ǰĿ¼
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
	 * �ı䵱ǰ�ļ���
	 * @param newPath ��Ŀ¼
	 * @return 0�ɹ� -1ʧ��
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
	 * ���˵��ϼ�Ŀ¼
	 * @return 0�ɹ� -1ʧ��
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
	 * ���������ļ��л��ļ��� ,�ƶ��ļ�
	 * @param oldname ԭ��������
	 * @param newname �µ�����
	 * @return 0�ɹ� -1ʧ��
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
	 * ɾ���ļ�
	 * @param fileName Ҫɾ�����ļ���
	 * @return 0�ɹ� -1ʧ��
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
	 * ����Ŀ¼
	 * @param newPath Ҫ������Ŀ¼����
	 * @return  0�ɹ� -1ʧ��
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
	 * ɾ��Ŀ¼
	 * @param Path Ҫɾ��Ŀ¼����
	 * @return  0�ɹ� -1ʧ��
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
	 * ��ȡ�ļ��б�
	 * @param pattern ͨ������ļ� Ϊ""��ȫ���ļ�
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
	 * ����ļ������ԣ��޸�ʱ�䡢��С��
	 * @param fileName �ļ���
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
	 * ����ļ��б�
	 * @param pattern ͨ������ļ� Ϊ""��ȫ���ļ�
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
	 * ���ô�������
	 * @param type ASC��FTPClient.TYPE_TEXTUAL ������ FTPClient.TYPE_BINARY  �Զ�ѡ��(�����ļ�����) FTPClient.TYPE_AUTO
	 */
    public void setType(Integer type){
    	client.setType(type);
    }
    /**
     * SSL �׽�������
     * @param connector it.sauronsoftware.ftp4j.connectors.SSLConnector  SSL �׽�������
     */
	public void setConnector(FTPConnector connector){
		client.setConnector(connector);
	}
	/**
	 * �ر�FTP���ӣ��ر�ʱ�������������һ���ر�����
	 * @return �رճɹ������������ѶϿ�����������Ϊnullʱ�򷵻�true��ͨ�����ιرն�ʧ��ʱ�򷵻�false 
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
