package com.bin.ftp.LHBftp4j;

/** 
* FTP�������� 
* 
* @author leizhimin 2009-11-30 11:16:59 
*/ 
public enum FTPOptType { 
        UP("�ϴ�"), 
        DOWN("����"), 
        LIST("���"), 
        DELFILE("ɾ���ļ�"), 
        DELFOD("ɾ���ļ���"), 
        RENAME("�ϴ�"); 

        private String optname; 

        FTPOptType(String optname) { 
                this.optname = optname; 
        } 

        public String getOptname() { 
                return optname; 
        } 
}