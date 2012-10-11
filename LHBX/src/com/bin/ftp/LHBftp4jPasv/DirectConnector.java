package com.bin.ftp.LHBftp4jPasv;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @date 2011-8-7
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public class DirectConnector implements FTPConnector {

	public Socket connectForCommunicationChannel(String host, int port)
			throws IOException {
		return new Socket(host, port);
	}

	public Socket connectForDataTransferChannel(String host, int port)
			throws IOException {
		Socket socket = new Socket();
		socket.setReceiveBufferSize(524288);
		socket.setSendBufferSize(524288);
		socket.connect(new InetSocketAddress(host, port));
		return socket;
	}

}
