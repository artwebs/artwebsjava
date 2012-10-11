package com.bin.office;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public class IExcelImpl implements IExcel {
	private String fileName;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Boolean readExecelFile(int sheet){
		Boolean flag=false;
	    try {
	    	POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream(fileName));   
			this.wb = new HSSFWorkbook(fs);
			this.sheet=this.wb.getSheetAt(sheet);
			flag=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return flag;
	}
	public Boolean readExecelFile(String fName,int sheet){	
		Boolean flag=false;
	    try {
	    	POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream(fName));   
			this.wb = new HSSFWorkbook(fs);
			this.sheet=this.wb.getSheetAt(sheet);
			flag=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return flag;
	}
	public Boolean saveExecelFile(String fName){
		Boolean flag=false;	  
	    try {
	        FileOutputStream fileOut = new FileOutputStream(fName);   
			wb.write(fileOut);
		    fileOut.close(); 
		    flag=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return flag;
	}
	public Boolean setHSSFCellValue(BinList inlist){
		Boolean flag=false;	
		for(int i=0;i<inlist.size();i++){
			int rownum=Integer.parseInt(inlist.getValue(i, "row").toString());
			int colnum=Integer.parseInt(inlist.getValue(i, "col").toString());
			String cellvalue=inlist.getValue(i, "value").toString();
		    HSSFRow row = this.sheet.getRow(rownum);   
		    HSSFCell cell = row.getCell((short)colnum);   
		    if (cell == null)   
		        cell = row.createCell((short)colnum);   
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);   
		    cell.setCellValue(cellvalue);
		    if(i==inlist.size()-1)flag=true;
		}
		return flag;
	}
	public Boolean setHSSFCellValue(int rownum,BinList inlist){
		Boolean flag=false;	
		for(int i=0;i<inlist.size();i++){
			int colnum=Integer.parseInt(inlist.getValue(i,"col").toString());
			String cellvalue=inlist.getValue(i, "value").toString();
		    HSSFRow row = this.sheet.getRow(rownum);   
		    HSSFCell cell = row.getCell((short)colnum);   
		    if (cell == null)   
		        cell = row.createCell((short)colnum);   
		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);   
		    cell.setCellValue(cellvalue);
		    if(i==inlist.size()-1)flag=true;
		}
		flag=true;
		return flag;
	}
	public Boolean insertRow(int startRow,int rows){
		Boolean flag=false;	
		sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows,true,false);
		startRow = startRow - 1;

		  for (int i = 0; i < rows; i++) {
		
			   HSSFRow sourceRow = null;
			   HSSFRow targetRow = null;
			   HSSFCell sourceCell = null;
			   HSSFCell targetCell = null;
			   short m;
		
			   startRow = startRow + 1;
			   sourceRow = sheet.getRow(startRow);
			   targetRow = sheet.createRow(startRow + 1);
			   targetRow.setHeight(sourceRow.getHeight());
		
			   for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
		
			    sourceCell = sourceRow.getCell(m);
			    targetCell = targetRow.createCell(m);
		
			   // targetCell.setEncoding(sourceCell.get);
			    targetCell.setCellStyle(sourceCell.getCellStyle());
			    targetCell.setCellType(sourceCell.getCellType());
		
			   }
		  }
		  flag=true;
		return flag;
	}
	public static void main(String[] args){
//		try  
//		{   
//		    POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream("D:/temp/office/template.xls"));   
//		    HSSFWorkbook wb = new HSSFWorkbook(fs);   
//		    HSSFSheet sheet = wb.getSheetAt(1);   
//		    HSSFRow row = sheet.getRow(8);   
//		    HSSFCell cell = row.getCell((short)0);   
//		    if (cell == null)   
//		        cell = row.createCell((short)0);   
//		    cell.setCellType(HSSFCell.CELL_TYPE_STRING);   
//		    cell.setCellValue("201005");   
//		    // Êä³öÎÄ¼þ   
//		    FileOutputStream fileOut = new FileOutputStream("D:/temp/office/test.xls");   
//		    wb.write(fileOut);   
//		    fileOut.close();   
//		}catch(Exception e)   
//		{   
//		    e.printStackTrace();   
//		}
		
		IExcelImpl excel=new IExcelImpl();
		excel.setFileName("D:/temp/office/template.xls");
		excel.readExecelFile(1);
		BinList inlt=new BinList();
		inlt.put(false, "row", "8");inlt.put(true, "col", "0");inlt.put(true, "value", "dd");
		inlt.put(false, "row", "8");inlt.put(true, "col", "2");inlt.put(true, "value", "date");
		inlt.put(false, "row", "8");inlt.put(true, "col", "4");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "5");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "6");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "7");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "8");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "10");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "12");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "13");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "14");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "15");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "16");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "18");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "20");inlt.put(true, "value", "50");		
		inlt.put(false, "row", "8");inlt.put(true, "col", "22");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "24");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "25");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "26");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "27");inlt.put(true, "value", "50");
		inlt.put(false, "row", "8");inlt.put(true, "col", "28");inlt.put(true, "value", "50");
		excel.setHSSFCellValue(inlt);		
		excel.insertRow(8, 1);
		excel.setHSSFCellValue(9, inlt);
		excel.saveExecelFile("D:/temp/office/test2.xls");
	}


}
