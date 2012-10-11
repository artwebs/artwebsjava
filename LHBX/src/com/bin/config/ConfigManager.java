package com.bin.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bin.io.IFile;
import com.bin.io.IFileImpl;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

public class ConfigManager {
	private static IFile ifile=new IFileImpl();
	private static String getConfigContent(){
		String rsStr=ifile.readFile(Utils.getRootPath()+"/WEB-INF/config.xml",true,"utf-8");
		return rsStr;		
	}
	private static BinMap getAppSettingsMap(){
		BinMap rslm=new BinMap();
		String rsStr=getConfigContent();
		rsStr=Utils.getMarkString(rsStr, "<appSettings>", "</appSettings>");
		String regex="\\s+<add\\s+key=\"(.*?)\"\\s+value=\"(.*?)\"/>";
	    Pattern p=Pattern.compile(regex,Pattern.UNICODE_CASE);
	    if(rsStr!=null){
			Matcher m=p.matcher(rsStr);
			while(m.find())
			{
				String content=m.group(2).toString();				
				rslm.put(m.group(1).toString(),content);
			}
	    }
	    
		return rslm;		
	}
	public static String getAppSettingsValue(String key){
		String rsStr="";
		BinMap rslm=getAppSettingsMap();
		if(rslm.containsKey(key))
		rsStr=rslm.getValue(key).toString();		
		return rsStr;
	}
	public static void setAppSettingsValue(String key,String value){
		String rsStr=getConfigContent();
		String regex="\\s+<add\\s+key=\""+key+"\"\\s+value=\"(.*?)\"/>";
	    Pattern p=Pattern.compile(regex,Pattern.UNICODE_CASE);
	    if(rsStr!=null){
			Matcher m=p.matcher(rsStr);
			while(m.find())
			{
				String ycontent=m.group(0).toString();	
				String yvalue=m.group(1).toString();	
				String ncontent=ycontent.replace("value=\""+yvalue+"\"", "value=\""+value+"\"");
				rsStr=rsStr.replace(ycontent, ncontent);
			}
			Utils.writeFile(Utils.getRootPath()+"/WEB-INF/config.xml", rsStr, false);
	    }
	    
		
	}
	public static void main(String[] args){
//		ConfigManager.setAppSettingsValue("DB_UserPwd", "ydjw1");
		if("".equals(ConfigManager.getAppSettingsValue("Sms_wsdl1")))
		System.out.println(ConfigManager.getAppSettingsValue("Sms_wsdl1")+"1111");
	}
}
