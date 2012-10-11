package com.bin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.bin.log.Log;
import com.bin.object.BinList;

public class IFileImpl implements IFile{
	public String readFile(String FileName){
		String rsStr="";
		FileReader fr=null;
		BufferedReader in=null;
		try {
			fr=new FileReader(FileName);
			in=new BufferedReader(fr);
			String line="";
			while((line=in.readLine())!=null){
				rsStr+=line+"\r\n";
			}
			rsStr=rsStr.substring(0,rsStr.lastIndexOf("\r\n"));
			in.close();
			fr.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsStr;		
	}
	public String readFile(String FileName,Boolean isNewLine){
		String rsStr="";
		FileReader fr=null;
		BufferedReader in=null;
		try {
			fr=new FileReader(FileName);
			in=new BufferedReader(fr);
			String line="";
			while((line=in.readLine())!=null){
				rsStr+=line;
				if(isNewLine)rsStr+="\r\n";				
			}
			if(isNewLine)rsStr=rsStr.substring(0,rsStr.lastIndexOf("\r\n"));
			in.close();
			fr.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsStr;		
	}
	
	public String readFile(String FileName,Boolean isNewLine,String charset){
		String rsStr="";
		BufferedReader in=null;
		File f=new File(FileName);
		try {
			InputStreamReader read = new InputStreamReader (new FileInputStream(f),charset);   
			in=new BufferedReader(read);
			String line="";
			while((line=in.readLine())!=null){
				rsStr+=line;
				if(isNewLine)rsStr+="\r\n";				
			}
			if(isNewLine)rsStr=rsStr.substring(0,rsStr.lastIndexOf("\r\n"));
			in.close();		
			read.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsStr;		
	}
	public String writeFile(String FileName,String appandstr,boolean falg){
		String rsStr=null;
		FileWriter fw=null;
		BufferedWriter out=null;
		String filePath=FileName.substring(0,FileName.lastIndexOf("/"));
		File fileRoot = new File(filePath);
		if(!fileRoot.isDirectory()){
			fileRoot.mkdirs();
		}
		try {
			fw=new FileWriter(FileName,falg);
			out=new BufferedWriter(fw);
			out.append(appandstr);
			rsStr=appandstr;
			out.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsStr;
	}
	
	public BinList getFilesList(String path){
		BinList list=new BinList();
		File dir=new File(path);
		File[] files = dir.listFiles();
		for(int i=0;i<files.length;i++){
			String filename=files[i].getName();
			if(files[i].isFile())list.put(i, "name", path+filename);
		}
		
		return list;
		
	}
	
	
	 /**    
      * 删除文件，可以是单个文件或文件夹    
      * @param   fileName    待删除的文件名    
      * @return 文件删除成功返回true,否则返回false    
      */ 
    public boolean delete(String fileName){      
        File file = new File(fileName);      
        if(!file.exists()){      
            Log.getLogger();
            Log.setError("删除文件失败："+fileName+"文件不存在");
            return false;      
        }else{      
            if(file.isFile()){  
                return deleteFile(fileName);      
            }else{      
                return deleteDirectory(fileName);      
            }      
        }      
    }      
          
    /**    
     * 删除单个文件    
     * @param   fileName    被删除文件的文件名    
     * @return 单个文件删除成功返回true,否则返回false    
     */     
    public  boolean deleteFile(String fileName){      
        File file = new File(fileName);      
        if(file.isFile() && file.exists()){      
            file.delete();   
            Log.getLogger();
            Log.setDebug("删除单个文件"+fileName+"成功！");     
            return true;      
        }else{   
        	Log.getLogger();
        	Log.setError("删除单个文件"+fileName+"失败！");    
            return false;      
        }      
    }      
          
    /**    
     * 删除目录（文件夹）以及目录下的文件    
     * @param   dir 被删除目录的文件路径    
     * @return  目录删除成功返回true,否则返回false    
     */     
    public boolean deleteDirectory(String dir){      
        //如果dir不以文件分隔符结尾，自动添加文件分隔符      
        if(!dir.endsWith(File.separator)){      
            dir = dir+File.separator;      
        }      
        File dirFile = new File(dir);      
        //如果dir对应的文件不存在，或者不是一个目录，则退出      
        if(!dirFile.exists() || !dirFile.isDirectory()){ 
        	Log.getLogger();
        	Log.setError("删除目录失败"+dir+"目录不存在！");      
            return false;      
        }      
        boolean flag = true;      
        //删除文件夹下的所有文件(包括子目录)      
        File[] files = dirFile.listFiles();      
        for(int i=0;i<files.length;i++){      
            //删除子文件      
            if(files[i].isFile()){      
                flag = deleteFile(files[i].getAbsolutePath());      
                if(!flag){      
                    break;      
                }      
            }      
            //删除子目录      
            else{      
                flag = deleteDirectory(files[i].getAbsolutePath());      
                if(!flag){      
                    break;      
                }      
            }      
        }      
              
        if(!flag){ 
        	Log.getLogger();
        	Log.setError("删除目录失败");     
            return false;      
        }      
              
        //删除当前目录      
        if(dirFile.delete()){ 
        	Log.getLogger();
        	Log.setDebug("删除目录"+dir+"成功！");      
            return true;      
        }else{      
        	Log.getLogger();
        	Log.setError("删除目录"+dir+"失败！");     
            return false;      
        }      
    } 
	
	public static void main(String[] args){
		IFileImpl file=new IFileImpl();
		//System.out.println(file.readFile("D:/temp/ftp/1112.txt"));
		//System.out.println(file.writeFile("D:/temp/ftp/1112.txt","呵呵111\r\n",false));
		System.out.println(file.getFilesList("E:/javaProject/policework/WebRoot/SendFiles/PALMLINK_POLICE_WORKSTAT-20100901114842/").getItem());

	}
}
