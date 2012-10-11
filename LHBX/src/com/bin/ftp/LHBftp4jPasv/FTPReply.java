package com.bin.ftp.LHBftp4jPasv;

/**
 * @date 2011-8-7
 * @author zhanghaitao
 * @version 1.0
 * 
 */
public class FTPReply {
	private int code = 0;
	private String[] messages;

	FTPReply(int code, String[] messages) {
		this.code = code;
		this.messages = messages;
	}

	public int getCode() {
		return this.code;
	}

	public boolean isSuccessCode() {
		int aux = this.code - 200;
		return (aux >= 0) && (aux < 100);
	}

	public String[] getMessages() {
		return this.messages;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append(" [code=");
		buffer.append(this.code);
		buffer.append(", message=");
		for (int i = 0; i < this.messages.length; i++) {
			if (i > 0) {
				buffer.append(" ");
			}
			buffer.append(this.messages[i]);
		}
		buffer.append("]");
		return buffer.toString();
	}
}
