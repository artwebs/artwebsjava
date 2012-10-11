package com.bin.ftp.LHBftp4jPasv;


import java.util.Locale;

import com.bin.ftp.FtpModel;
import com.bin.ftp.IAFtpClient;
import com.bin.object.BinList;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

public class Ftp4jPasvClient extends IAFtpClient {
	private FTPClient client = new FTPClient();
	public Ftp4jPasvClient()
	{}
	public Ftp4jPasvClient(FtpModel ftpModel)
	{
		this.ftpModel = ftpModel;
        this.returnClient();
	}
	
    public FtpModel getFtpModel() {
		return ftpModel;
	}

	public void setFtpModel(FtpModel ftpModel) {
		this.ftpModel = ftpModel;
	}

	@Override
	public void returnClient() {
		try {
			client.connect(this.getFtpModel().getHost(), this.getFtpModel().getPort());
			client.login(this.getFtpModel().getUsername(), this.getFtpModel().getPassword(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Integer changeDirectory(String newPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer changeDirectoryUp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean closeConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer createDirectory(String newPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteDirectory(String Path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer downloadFile(String remoteFileName, String localFolderPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentDirectory() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public BinList getFileLHBList(String pattern) {
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

	@Override
	public BinMap getFileProperties(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer isExist(String remotePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer rename(String oldname, String newname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(Integer type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer uploadFile(String localFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer uploadFile(String localFileName, String remoteFolderPath) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ftp4jPasvClient ftp=new Ftp4jPasvClient(new FtpModel("220.165.246.236",21,"out","0871","/"));
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
		
		BinList list=ftp.getFileLHBList("PALMLINK_POLICE_WORKSTAT-*.zip");
		for(int i=0;i<list.size();i++){
			System.out.println((String)list.getValue(i, "modifiedDate"));
		}

	}

}
