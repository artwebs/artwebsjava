package com.bin.ftp.LHBftp4j;

/** 
* FTP操作类型 
* 
* @author leizhimin 2009-11-30 11:16:59 
*/ 
public enum FTPOptType { 
        UP("上传"), 
        DOWN("下载"), 
        LIST("浏览"), 
        DELFILE("删除文件"), 
        DELFOD("删除文件夹"), 
        RENAME("上传"); 

        private String optname; 

        FTPOptType(String optname) { 
                this.optname = optname; 
        } 

        public String getOptname() { 
                return optname; 
        } 
}