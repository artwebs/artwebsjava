package com.bin.zip;
import java.io.*;
import org.apache.tools.zip.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class ApacheZip implements IZip {
	
	public int doFileZip(String fileName,String saveZipFileName,boolean isOnlyFileName){
		 try {
	            byte b[] = new byte[512];	            
	            //压缩文件的保存路径
	            String zipFile = saveZipFileName;	            
	            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
	            //使用输出流检查
	            CheckedOutputStream cs = new CheckedOutputStream(fileOutputStream, new CRC32());
	            //声明输出zip流
	            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(cs));
	               InputStream in = new FileInputStream(fileName);
	               fileName = ((String)(fileName)).replace(File.separatorChar,'/');
	               if(!isOnlyFileName)fileName=fileName.substring(fileName.lastIndexOf("/")+1);
	               fileName = fileName.substring(fileName.indexOf("/")+1);
	               ZipEntry e = new ZipEntry(fileName);
	               out.putNextEntry(e);
	               int len = 0;
	               while((len = in.read(b)) != -1) {
	                   out.write(b,0,len);
	                 }
	               out.closeEntry();

	            out.close();  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return 0;		
	}
	/**
	 * 压缩文件夹 
	 * @param filePath 文件件路径
	 * @param saveZipFileName 压缩成文件名（包含路径）
	 * @param isOnlyFileName 是否保留要压缩文件的路径在压缩包里
	 * @return
	 */
	public int doZip(String filePath,String saveZipFileName,boolean isOnlyFileName){
		 try {
	            byte b[] = new byte[512];
	            
	            //压缩文件的保存路径
	            String zipFile = saveZipFileName;
	            
	            //压缩文件目录
	            String filepath = filePath;
	            
	            List fileList = allFile(filepath);
	            
	            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
	            //使用输出流检查
	            CheckedOutputStream cs = new CheckedOutputStream(fileOutputStream, new CRC32());
	            //声明输出zip流
	            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
	                    cs));
	            
	            for (int i = 0; i < fileList.size(); i++) {
	               InputStream in = new FileInputStream((String)fileList.get(i));
	               String fileName = ((String)(fileList.get(i))).replace(File.separatorChar,'/');
	               if(!isOnlyFileName)fileName=fileName.substring(fileName.lastIndexOf("/")+1);
	               fileName = fileName.substring(fileName.indexOf("/")+1);
	               ZipEntry e = new ZipEntry(fileName);
	               out.putNextEntry(e);
	               int len = 0;
	               while((len = in.read(b)) != -1) {
	                   out.write(b,0,len);
	                 }
	               out.closeEntry();
	            }
	            out.close();  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return 0;
	}
	
	
	   private List allFile(String path)
	    {    
		    List list=new ArrayList();
	        File file = new File(path);
	        String[] array = null;
	        String sTemp = "";
	                

	        if(file.isDirectory())
	        {
	        }else{
	            return null;
	        }
	        array= file.list();
	        if(array.length>0)
	        {
	            for(int i=0;i<array.length;i++)
	            {
	                sTemp = path+array[i];
	                file = new File(sTemp);
	                if(file.isDirectory())
	                {
	                    allFile(sTemp+"/");

	                }else{
	                    list.add(sTemp);
	                }
	            }
	        }else{
	            return null;
	        }

	        return list;
	    } 

	   public int unZip(String zipFileName,String outPath){
		   try {
	            String outputDirectory = outPath;
	            File localFolder=new File(outPath);
	            if (!localFolder.exists())localFolder.mkdirs(); 
	            ZipFile zipFile = new ZipFile(zipFileName);
	            Enumeration e = zipFile.getEntries();
	            ZipEntry zipEntry = null;
	            createDirectory(outputDirectory, "");
	            while (e.hasMoreElements()) {
	                zipEntry = (ZipEntry) e.nextElement();
	                if (zipEntry.isDirectory()) {
	                    String name = zipEntry.getName();
	                    name = name.substring(0, name.length() - 1);
	                    File f = new File(outputDirectory + File.separator + name);
	                    f.mkdir();
	                } else {
	                    String fileName = zipEntry.getName();
	                    fileName = fileName.replace("\\", "/");
	                    if (fileName.indexOf("/") != -1) {
	                        createDirectory(outputDirectory, fileName.substring(0,fileName.lastIndexOf("/")));
	                    }

	                    File f = new File(outputDirectory
	                            + zipEntry.getName());

	                    f.createNewFile();
	                    InputStream in = zipFile.getInputStream(zipEntry);
	                    FileOutputStream out = new FileOutputStream(f);

	                    byte[] by = new byte[1024];
	                    int c;
	                    while ((c = in.read(by)) != -1) {
	                        out.write(by, 0, c);
	                    }
	                    out.close();
	                    in.close();
	                }
	            }
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	            ex.printStackTrace();
	        }

		return 0;		   
	   }
	   
	   private static void createDirectory(String directory, String subDirectory) {
	        String dir[];
	        File fl = new File(directory);
	        try {
	            if (subDirectory == "" && fl.exists() != true)
	                fl.mkdir();
	            else if (subDirectory != "") {
	                dir = subDirectory.replace('\\', '/').split("/");
	                for (int i = 0; i < dir.length; i++) {
	                    File subFile = new File(directory + File.separator + dir[i]);
	                    if (subFile.exists() == false)
	                        subFile.mkdir();
	                    directory += File.separator + dir[i];
	                }
	            }
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	    }	   
	   
	   //测试AntZip类
    public static void main(String[] args)throws Exception{

    	ApacheZip zip = new ApacheZip();
//    	zip.doFileZip("D:/temp/ftp/1111.txt","D:/temp/ftp/EquipmentMessage.zip",true);
    	//zip.unZip("D:/temp/ftp/ftp.zip", "D:/temp/ftp/2/");
    	zip.doZip("E:/javaProject/swapservice/WebRoot/SendFiles/00000000000000000000/", "E:/javaProject/swapservice/WebRoot/SendFiles/Police00000000000000000000Swap.zip", false);
    	
    }

}
