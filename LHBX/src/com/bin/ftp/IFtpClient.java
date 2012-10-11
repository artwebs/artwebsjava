package com.bin.ftp;

import com.bin.object.BinList;
import com.bin.object.BinMap;



public interface IFtpClient {
	public FtpModel getFtpModel();
	public void setFtpModel(FtpModel ftpModel);
    public void returnClient();
	public Integer downloadFile(String remoteFileName, String localFolderPath);
	public Integer uploadFile(String localFileName);
	public Integer uploadFile(String localFileName, String remoteFolderPath);
	public Integer isExist(String remotePath);
	public String getCurrentDirectory();
	public Integer changeDirectory(String newPath);
	public Integer changeDirectoryUp();
	public Integer rename(String oldname,String newname);
	public Integer deleteFile(String fileName);
	public Integer createDirectory(String newPath);
	public Integer deleteDirectory(String Path);
	public BinMap getFileProperties(String fileName);
	public BinList getFileLHBList(String pattern);
	public void setType(Integer type);
	public boolean closeConnection();
}
