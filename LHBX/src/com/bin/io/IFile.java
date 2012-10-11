package com.bin.io;

import com.bin.object.BinList;

public interface IFile {
	public String readFile(String FileName);
	public String writeFile(String FileName,String appandstr,boolean falg);
	public String readFile(String FileName,Boolean isNewLine,String charset);
	public BinList getFilesList(String path);
	public boolean delete(String fileName);
	public  boolean deleteFile(String fileName);
	public boolean deleteDirectory(String dir);
}
