package com.bin.app.action;

import javax.servlet.http.HttpServletRequest;

import com.bin.config.ConfigManager;
import com.bin.object.BinMap;
import com.bin.utils.Utils;


public class Action {	
	private HttpServletRequest request=null;
	protected BinMap para=new BinMap();
	public Action(){}
	
	public Action(HttpServletRequest request)
	{
		this.setRequest(request);
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.para=Utils.getMapByRequest(request);
		this.request = request;
	}
	
	public final String exec()
	{
		Action mod=null;
		String modName=this.para.getValue("mod").toString();
		String pakeName=ConfigManager.getAppSettingsValue("Package_"+modName);
		String actName=this.para.getValue("act").toString();
		Class objclass=null;
		Object result=null;
		try {
			objclass=Class.forName(pakeName);
			mod=(Action)objclass.newInstance();
			java.lang.reflect.Method method=mod.getClass().getMethod(actName,new Class[]{BinMap.class});
			result=method.invoke(mod,new Object[]{this.para});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		return (String)result;
	}	

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Action a=new Action();
		a.para.put("mod", "client");
		a.para.put("act", "getResult");
		System.out.println(a.exec());
	}

}
