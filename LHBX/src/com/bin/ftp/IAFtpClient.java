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
	 * �ı䵱ǰ�ļ���
	 * @param newPath ��Ŀ¼
	 * @return 0�ɹ� -1ʧ��
	 */
	public abstract Integer changeDirectory(String newPath);
	/**
	 * ���˵��ϼ�Ŀ¼
	 * @return 0�ɹ� -1ʧ��
	 */
	public abstract Integer changeDirectoryUp();
	/**
	 * �ر�FTP���ӣ��ر�ʱ�������������һ���ر�����
	 * @return �رճɹ������������ѶϿ�����������Ϊnullʱ�򷵻�true��ͨ�����ιرն�ʧ��ʱ�򷵻�false 
	 */
	public abstract boolean closeConnection();
	/**
	 * ����Ŀ¼
	 * @param newPath Ҫ������Ŀ¼����
	 * @return  0�ɹ� -1ʧ��
	 */
	public abstract Integer createDirectory(String newPath);
	/**
	 * ɾ��Ŀ¼
	 * @param Path Ҫɾ��Ŀ¼����
	 * @return  0�ɹ� -1ʧ��
	 */
	public abstract Integer deleteDirectory(String Path) ;
	/**
	 * ɾ���ļ�
	 * @param fileName Ҫɾ�����ļ���
	 * @return 0�ɹ� -1ʧ��
	 */
	public abstract Integer deleteFile(String fileName);
    /**
     * FTP�����ļ�������һ���ļ���,��������ļ��в����ڣ��򴴽���Ҫ��Ŀ¼�ṹ 
     * @param remoteFileName    FTP�ļ�  
     * @param localFolderPath   ��ı���Ŀ¼ 
     * @return 0���سɹ�  -1����ʧ�� -2�����ļ����� -3���ر���ĵط���һ���ļ�
     */
	public abstract Integer downloadFile(String remoteFileName, String localFolderPath);
	/**
	 * ȡ�õ�ǰ�ļ���
	 * @return ��ǰĿ¼
	 */
	public abstract String getCurrentDirectory();
	/**
	 * ����ļ��б�
	 * @param pattern ͨ������ļ� Ϊ""��ȫ���ļ�
	 * @return [{name=SMSOUT00000000000040.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=180}, {name=SMSOUT00000000000041.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=180}, {name=SMSOUT00000000000042.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000043.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000044.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=178}, {name=SMSOUT00000000000046.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000047.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000048.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000049.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=185}, {name=SMSOUT00000000000050.zip, FILE=0, modifiedDate=2010-06-05 01:38:35, size=188}]
	 */
	public abstract BinList getFileLHBList(String pattern);
	/**
	 * ����ļ������ԣ��޸�ʱ�䡢��С��
	 * @param fileName �ļ���
	 * @return [{modifiedDate=2010-06-05 01:35:01}, {size=188}]
	 */
	public abstract BinMap getFileProperties(String fileName);
	/**
	 *  �ж�һ��FTP·���Ƿ���ڣ�������ڷ�������(FTPFile.TYPE_DIRECTORY=1��FTPFile.TYPE_FILE=0��FTPFile.TYPE_LINK=2)
	 * @param remotePath FTP�ļ����ļ���·��
	 * @return ����ʱ�򷵻�����ֵ(�ļ�0���ļ���1������2)���������򷵻�-1 
	 */
	public abstract Integer isExist(String remotePath);

	/**
	 * ���������ļ��л��ļ��� ,�ƶ��ļ�
	 * @param oldname ԭ��������
	 * @param newname �µ�����
	 * @return 0�ɹ� -1ʧ��
	 */
	public abstract Integer rename(String oldname, String newname);
	/**
	 * ���ô�������
	 * @param type ASC��FTPClient.TYPE_TEXTUAL ������ FTPClient.TYPE_BINARY  �Զ�ѡ��(�����ļ�����) FTPClient.TYPE_AUTO
	 */
	public abstract void setType(Integer type);
	/**
	 * FTP�ϴ������ļ���FTP�ĵ�ǰĿ¼�� 
	 * @param localFileName    �����ļ�·�� ���ļ���
	 * @return 0 �ϴ��ɹ�  -1�ϴ�ʧ�� -2�ϴ��ļ������� -3��Ҫ�ϴ���FTP�ļ��Ǹ��ļ���
	 */
	public abstract Integer uploadFile(String localFileName);
	/**
	 * FTP�ϴ������ļ���FTP��һ��Ŀ¼�� 
	 * @param localFileName    �����ļ�·�� ���ļ���
	 * @param remoteFolderPath FTP�ϴ�Ŀ¼
	 * @return 0 �ϴ��ɹ�  -1�ϴ�ʧ�� -2�ϴ��ļ������� -3��Ҫ�ϴ���FTP�ļ��Ǹ��ļ���
	 */
	public abstract Integer uploadFile(String localFileName, String remoteFolderPath);

}
