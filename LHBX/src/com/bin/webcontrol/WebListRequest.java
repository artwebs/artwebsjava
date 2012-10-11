package com.bin.webcontrol;

import javax.servlet.http.HttpServletRequest;

import com.bin.delegate.IEvent;
import com.bin.delegate.IEventImpl;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

public class WebListRequest {
	private IEvent event=new IEventImpl();
	public String DealRequest(Object obj,HttpServletRequest request){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String rs="";
		BinMap para=Utils.getMapByRequest(request);		
		event.setObject(obj);
		event.setMethodName(para.getValue("method").toString());
		para.remove("method");
		Object[] objpara=new Object[para.size()];
		for(int i=0;i<para.size();i++){
			objpara[i]=para.getValue(i);
		}
		event.setParams(objpara);
		try {
			rs=(String)event.invoke();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;		
	}

	public String DealRequest(Object obj,String methodName,BinMap para){
		String rs="";
	
		event.setObject(obj);
		event.setMethodName(methodName);
		Object[] objpara=new Object[para.size()];
		for(int i=0;i<para.size();i++){
			objpara[i]=para.getValue(i);
		}
		event.setParams(objpara);
		try {
			rs=(String)event.invoke();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;		
	}
}
