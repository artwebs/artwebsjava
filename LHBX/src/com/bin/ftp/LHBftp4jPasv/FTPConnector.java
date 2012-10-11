package com.bin.ftp.LHBftp4jPasv;

import java.io.IOException;
import java.net.Socket;

/**
 * @date 2011-8-7
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public abstract interface FTPConnector {
	/**
	 * This methods returns an established connection to a remote host, suitable
	 * for a FTP communication channel.
	 * 
	 * @param host
	 *            The remote host name or address.
	 * @param port
	 *            The remote port.
	 * @return The connection with the remote host.
	 * @throws IOException
	 *             If the connection cannot be established.
	 */
	public Socket connectForCommunicationChannel(String host, int port)
			throws IOException;

	/**
	 * This methods returns an established connection to a remote host, suitable
	 * for a FTP data transfer channel.
	 * 
	 * @param host
	 *            The remote host name or address.
	 * @param port
	 *            The remote port.
	 * @return The connection with the remote host.
	 * @throws IOException
	 *             If the connection cannot be established.
	 */
	public Socket connectForDataTransferChannel(String host, int port)
			throws IOException;

}
