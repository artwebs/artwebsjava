package com.bin.ftp;

public interface IFtp {
	public int downFile(String fname,String file);
	public int uploadFile(String dir, String file);
	public Boolean isFileExists(String fname);
}
