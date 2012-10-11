package com.bin.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.codehaus.xfire.client.Client;

public class ISClientImpl implements ISClient {
	private static String _wsdl=null;
	public ISClientImpl(String wsdl)
	{
		_wsdl=wsdl;
	}
	public	Object[] GetClientResult(String methodname,Object[] object)
	{
		Object[] rsObject=null;
		 try {
			URL wsdlURL=new URL(_wsdl);		
			System.out.println();
			System.out.println("=========================="+methodname+"->start===========================");
			System.out.println("in="+object[0]);
			 URLConnection uc=wsdlURL.openConnection();
			 Client client=new Client(uc.getInputStream(),null);
			 rsObject=client.invoke(methodname, object);
			 System.out.println("out="+rsObject[0]);
			 System.out.println("=========================="+methodname+"->end===========================");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsObject;	
	}
}
