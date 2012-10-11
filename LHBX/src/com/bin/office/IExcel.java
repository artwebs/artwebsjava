package com.bin.office;

import com.bin.object.BinList;

public interface IExcel {
	public String getFileName();
	public void setFileName(String fileName);
	public Boolean readExecelFile(int sheet);
	public Boolean readExecelFile(String fName,int sheet);
	public Boolean saveExecelFile(String fName);
	public Boolean setHSSFCellValue(BinList inlist);
}
