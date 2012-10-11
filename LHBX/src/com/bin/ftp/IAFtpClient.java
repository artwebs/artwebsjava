package com.bin.ftp;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public abstract class IAFtpClient implements IFtpClient{
	protected FtpModel ftpModel = null;
    public IAFtpClient() {   
    	  
    }   
	public IAFtpClient(FtpModel ftpModel) {   
        this.ftpModel = ftpModel;
        this.returnClient();
    }
	public abstract FtpModel getFtpModel() ;
	public abstract void setFtpModel(FtpModel ftpModel);
	    
	public abstract void returnClient();

	/**
	 * 改变当前文件夹
	 * @param newPath 新目录
	 * @return 0成功 -1失败
	 */
	public abstract Integer changeDirectory(String newPath);
	/**
	 * 回退到上级目录
	 * @return 0成功 -1失败
	 */
	public abstract Integer changeDirectoryUp();
	/**
	 * 关闭FTP连接，关闭时候像服务器发送一条关闭命令
	 * @return 关闭成功，或者链接已断开，或者链接为null时候返回true，通过两次关闭都失败时候返回false 
	 */
	public abstract boolean closeConnection();
	/**
	 * 创建目录
	 * @param newPath 要创建的目录名称
	 * @return  0成功 -1失败
	 */
	public abstract Integer createDirectory(String newPath);
	/**
	 * 删除目录
	 * @param Path 要删除目录名称
	 * @return  0成功 -1失败
	 */
	public abstract Integer deleteDirectory(String Path) ;
	/**
	 * 删除文件
	 * @param fileName 要删除的文件名
	 * @return 0成功 -1失败
	 */
	public abstract Integer deleteFile(String fileName);
    /**
     * FTP下载文件到本地一个文件夹,如果本地文件夹不存在，则创建必要的目录结构 
     * @param remoteFileName    FTP文件  
     * @param localFolderPath   存的本地目录 
     * @return 0下载成功  -1下载失败 -2下载文件存在 -3下载保存的地方是一个文件
     */
	public abstract Integer downloadFile(String remoteFileName, String localFolderPath);
	/**
	 * 取得当前文件夹
	 * @return 当前目录
	 */
	public abstract String getCurrentDirectory();
	/**
	 * 获得文件列表
	 * @param pattern 通配浏览文件 为""是全部文件
	 * @return [{name=SMSOUT00000000000040.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=180}, {name=SMSOUT00000000000041.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=180}, {name=SMSOUT00000000000042.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000043.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000044.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000046.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000047.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000048.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000049.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000050.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=188}]
	 */
	public abstract BinList getFileLHBList(String pattern);
	/**
	 * 获得文件的属性（修改时间、大小）
	 * @param fileName 文件名
	 * @return [{modifiedDate=2010-06-05 01:35:01}, {size=188}]
	 */
	public abstract BinMap getFileProperties(String fileName);
	/**
	 *  判断一个FTP路径是否存在，如果存在返回类型(FTPFile.TYPE_DIRECTORY=1、FTPFile.TYPE_FILE=0、FTPFile.TYPE_LINK=2)
	 * @param remotePath FTP文件或文件夹路径
	 * @return 存在时候返回类型值(文件0，文件夹1，连接2)，不存在则返回-1 
	 */
	public abstract Integer isExist(String remotePath);

	/**
	 * 重新命名文件夹或文件名 ,移动文件
	 * @param oldname 原来的名称
	 * @param newname 新的名称
	 * @return 0成功 -1失败
	 */
	public abstract Integer rename(String oldname, String newname);
	/**
	 * 设置传输类型
	 * @param type ASC码FTPClient.TYPE_TEXTUAL 二进制 FTPClient.TYPE_BINARY  自动选择(根据文件内容) FTPClient.TYPE_AUTO
	 */
	public abstract void setType(Integer type);
	/**
	 * FTP上传本地文件到FTP的当前目录下 
	 * @param localFileName    本地文件路径 及文件名
	 * @return 0 上传成功  -1上传失败 -2上传文件不存在 -3所要上传的FTP文件是个文件夹
	 */
	public abstract Integer uploadFile(String localFileName);
	/**
	 * FTP上传本地文件到FTP的一个目录下 
	 * @param localFileName    本地文件路径 及文件名
	 * @param remoteFolderPath FTP上传目录
	 * @return 0 上传成功  -1上传失败 -2上传文件不存在 -3所要上传的FTP文件是个文件夹
	 */
	public abstract Integer uploadFile(String localFileName, String remoteFolderPath);

}
