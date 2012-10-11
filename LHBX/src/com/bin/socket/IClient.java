package com.bin.socket;

public interface IClient {
	public void send(String s, String charsetName, boolean flush);
	public String read(int size,String rcharset);
}
