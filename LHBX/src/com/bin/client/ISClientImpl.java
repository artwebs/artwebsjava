package com.bin.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.codehaus.xfire.client.Client;

import com.bin.utils.Utils;

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
			Date date=new Date();
			int year=date.getYear()+1900;
//			System.out.println();
//			System.out.println("=========================="+methodname+"->start===========================");
//			for(int i=0;i<object.length;i++)System.out.println("in"+i+"="+object[i]);
			 URLConnection uc=wsdlURL.openConnection();
			 Client client=new Client(uc.getInputStream(),null);
			 rsObject=client.invoke(methodname, object);
//			 for(int i=0;i<rsObject.length;i++)System.out.println("out"+i+"="+rsObject[0]);
//			 System.out.println("=========================="+methodname+"->end===========================");
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
	public static void main(String[] args) {
//		ISClientImpl client=new ISClientImpl("http://116.52.157.140:8080/roadorginterface/roadorg.wsdl");
//		Object[] object={"123","123","vehvio","ÔÆAT0275","02","","4549","","1"};
//		client.GetClientResult("query", object);
		
		ISClientImpl client=new ISClientImpl("http://127.0.0.1/policeserver/index.php?services.wsdl");
		Object[] object={"hhga","login","desktop","[\"user8\",\"123\"]"};
		Object[] rs=client.GetClientResult("queryjson", object);
		System.out.println(Utils.UnicodeToString(rs[0].toString()));

		
		
	}
}
