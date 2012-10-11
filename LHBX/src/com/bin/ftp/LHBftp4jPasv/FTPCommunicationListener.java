package com.bin.ftp.LHBftp4jPasv;

/**
 * @date 2011-8-7
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public abstract interface FTPCommunicationListener {
	public abstract void sent(String paramString);

	public abstract void received(String paramString);
}