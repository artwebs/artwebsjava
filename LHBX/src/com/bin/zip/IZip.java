package com.bin.zip;

public interface IZip {
	public int doFileZip(String fileName,String saveZipFileName,boolean isOnlyFileName);
	public int doZip(String filePath,String saveZipFileName,boolean isOnlyFileName);
	public int unZip(String zipFileName,String outPath);
}
