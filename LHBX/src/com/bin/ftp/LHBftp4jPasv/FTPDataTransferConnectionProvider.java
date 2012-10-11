package com.bin.ftp.LHBftp4jPasv;

import java.net.Socket;

/**
 * @date 2011-8-8
 * @author zhanghaitao
 * @version 1.0
 * 
 */
abstract interface FTPDataTransferConnectionProvider {
	public abstract Socket openDataTransferConnection();

	public abstract void dispose();
}