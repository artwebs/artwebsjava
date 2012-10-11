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
      * ɾ���ļ��������ǵ����ļ����ļ���    
      * @param   fileName    ��ɾ�����ļ���    
      * @return �ļ�ɾ���ɹ�����true,���򷵻�false    
      */ 
    public boolean delete(String fileName){      
        File file = new File(fileName);      
        if(!file.exists()){      
            Log.getLogger();
            Log.setError("ɾ���ļ�ʧ�ܣ�"+fileName+"�ļ�������");
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
     * ɾ�������ļ�    
     * @param   fileName    ��ɾ���ļ����ļ���    
     * @return �����ļ�ɾ���ɹ�����true,���򷵻�false    
     */     
    public  boolean deleteFile(String fileName){      
        File file = new File(fileName);      
        if(file.isFile() && file.exists()){      
            file.delete();   
            Log.getLogger();
            Log.setDebug("ɾ�������ļ�"+fileName+"�ɹ���");     
            return true;      
        }else{   
        	Log.getLogger();
        	Log.setError("ɾ�������ļ�"+fileName+"ʧ�ܣ�");    
            return false;      
        }      
    }      
          
    /**    
     * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�    
     * @param   dir ��ɾ��Ŀ¼���ļ�·��    
     * @return  Ŀ¼ɾ���ɹ�����true,���򷵻�false    
     */     
    public boolean deleteDirectory(String dir){      
        //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���      
        if(!dir.endsWith(File.separator)){      
            dir = dir+File.separator;      
        }      
        File dirFile = new File(dir);      
        //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�      
        if(!dirFile.exists() || !dirFile.isDirectory()){ 
        	Log.getLogger();
        	Log.setError("ɾ��Ŀ¼ʧ��"+dir+"Ŀ¼�����ڣ�");      
            return false;      
        }      
        boolean flag = true;      
        //ɾ���ļ����µ������ļ�(������Ŀ¼)      
        File[] files = dirFile.listFiles();      
        for(int i=0;i<files.length;i++){      
            //ɾ�����ļ�      
            if(files[i].isFile()){      
                flag = deleteFile(files[i].getAbsolutePath());      
                if(!flag){      
                    break;      
                }      
            }      
            //ɾ����Ŀ¼      
            else{      
                flag = deleteDirectory(files[i].getAbsolutePath());      
                if(!flag){      
                    break;      
                }      
            }      
        }      
              
        if(!flag){ 
        	Log.getLogger();
        	Log.setError("ɾ��Ŀ¼ʧ��");     
            return false;      
        }      
              
        //ɾ����ǰĿ¼      
        if(dirFile.delete()){ 
        	Log.getLogger();
        	Log.setDebug("ɾ��Ŀ¼"+dir+"�ɹ���");      
            return true;      
        }else{      
        	Log.getLogger();
        	Log.setError("ɾ��Ŀ¼"+dir+"ʧ�ܣ�");     
            return false;      
        }      
    } 
	
	public static void main(String[] args){
		IFileImpl file=new IFileImpl();
		//System.out.println(file.readFile("D:/temp/ftp/1112.txt"));
		//System.out.println(file.writeFile("D:/temp/ftp/1112.txt","�Ǻ�111\r\n",false));
		System.out.println(file.getFilesList("E:/javaProject/policework/WebRoot/SendFiles/PALMLINK_POLICE_WORKSTAT-20100901114842/").getItem());

	}
}
