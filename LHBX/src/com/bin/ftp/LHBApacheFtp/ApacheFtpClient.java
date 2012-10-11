package com.bin.ftp.LHBApacheFtp;

import com.bin.ftp.FtpModel;
import com.bin.ftp.IAFtpClient;
import com.bin.object.BinList;
import com.bin.object.BinMap;

public class ApacheFtpClient extends IAFtpClient {

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

	@Override
	public BinList getFileLHBList(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BinMap getFileProperties(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FtpModel getFtpModel() {
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
	public void returnClient() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFtpModel(FtpModel ftpModel) {
		// TODO Auto-generated method stub
		
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


}
