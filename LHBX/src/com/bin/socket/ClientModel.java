package com.bin.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientModel implements IClient{
	private Socket socket;
	private DataOutputStream outstream;
	private DataInputStream instream;
	
	public ClientModel(String ip, int port){
		try {
			this.socket=new Socket(ip,port);
		      this.outstream = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
		      this.instream = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(byte[] b, boolean flush) {
		 try {
		      this.outstream.write(b);
		      if (!(flush)) return; 
		      this.outstream.flush();
		 }catch (IOException e) {
		      e.printStackTrace();
		 }
    }

    public void send(String s, String charsetName, boolean flush) {
		try {
		      send(s.getBytes(charsetName), flush);
		}catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		}
	}

	public byte[] read(int size) {
		 byte[] b = new byte[size];
		 try {
		      int len = this.instream.read(b);
		      ByteArrayOutputStream bais = new ByteArrayOutputStream();
		      bais.write(b, 0, len);
		      bais.flush();
		      b = bais.toByteArray();
		      bais.close();
		 }catch (IOException e) {
		      e.printStackTrace();
		 }
		 return b;
    }
	
	public String read(int size,String rcharset){
		byte[] b=this.read(size);
		String rs="";
		try {
			rs=new String(b,rcharset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}

	public String readLn() {
		String rs = "";
		try {
		    rs = this.instream.readLine();
		    }catch (IOException e) {
		      e.printStackTrace();
		}
		return rs;
	}

	public String readStrAll() {
		String rs = "";
		try {
		      String record = "";
		      while ((record = this.instream.readLine()) != null)
		      rs = rs + record + "\r\n";
		}catch (IOException e){
		      e.printStackTrace();
		}
		return rs;
	}

	public void close() {
		try {
		    this.socket.close();
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientModel client=new ClientModel("116.55.248.20",9558);
		client.send("HMV1.00|20100715122323|YXJ0MDA0|MTIzNDU2|km122|8888\r\n", "gbk", true);
		System.out.println(client.read(20000, "gbk"));
	    client.close();

	}

}
