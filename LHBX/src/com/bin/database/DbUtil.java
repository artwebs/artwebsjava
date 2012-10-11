package com.bin.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bin.LHB;
import com.bin.log.Log;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

public class DbUtil extends LHB {
	public BinMap getNoExKey(BinMap para) {
		BinMap rsLm=Utils.getNoExKey(para);
		return rsLm;
	}
	
	public BinMap getNoExPara(BinMap para) {
		BinMap rsLm=Utils.getNoExPara(para);
		return rsLm;
	}
	
	public String getInsertPart(BinMap para) {
		String rsStr="";
		String fieldpart="";
		String valuepart="";		
		for(int i=0;i<para.size();i++){
			String key=(String)para.getKey(i);
			if(key.indexOf(".")>0){
				String ex=key.substring(key.indexOf(".")+1);
				String noex=key.substring(0,key.lastIndexOf("."));
				fieldpart+=noex+",";
				if(ex.equals("string")){
					valuepart+="'"+para.getValue(i)+"',";
				}else if(ex.equals("clob")){
					valuepart+="empty_clob(),";
				}else{
					valuepart+=para.getValue(i)+",";
				}
								
			}else{
				fieldpart+=key+",";
				valuepart+="'"+para.getValue(i)+"',";
			}
			
		}
		if(fieldpart!=""&&valuepart!=""){
			fieldpart=fieldpart.substring(0,fieldpart.lastIndexOf(','));
			valuepart=valuepart.substring(0,valuepart.lastIndexOf(','));
			rsStr="("+fieldpart+") values ("+valuepart+")";
		}
		return rsStr;
	}


	public String getSelectField(BinMap para) {
		String rsStr="";
		String regex="\\s+as\\s+";
		Pattern p=Pattern.compile(regex,Pattern.UNICODE_CASE);
		for(int i=0;i<para.size();i++){
			String key=(String)para.getKey(i);
			Matcher m=p.matcher(key);
			if(key.indexOf(".")>0&&!m.find()){
				String noex=key.substring(0,key.lastIndexOf("."));
				rsStr+=noex+",";
			}else{
				rsStr+=key+",";
			}
		}
		if(rsStr!=""){
			rsStr=rsStr.substring(0,rsStr.lastIndexOf(','));
		}
		return rsStr;
	}

	public String getUpdatePart(BinMap para) {
		String rsStr="";
		for(int i=0;i<para.size();i++){
			String key=(String)para.getKey(i);
			if(key.indexOf(".")>0){
				String ex=key.substring(key.indexOf(".")+1);
				String noex=key.substring(0,key.lastIndexOf("."));
				rsStr+=noex+"=";
				if(ex.equals("string")){
					rsStr+="'"+para.getValue(i)+"',";
				}else{
					rsStr+=para.getValue(i)+",";
				}
								
			}else{
				rsStr+=key+"=";
				rsStr+="'"+para.getValue(i)+"',";
			}
			
		}
		if(rsStr!=""){
			rsStr=rsStr.substring(0,rsStr.lastIndexOf(','));
		}
		return rsStr;
	}
	
	public CallableStatement getProc(Connection conn,String procName, BinMap inpara, BinMap outpara){
		CallableStatement proc=null;
		try {
			Log.getLogger();
			Log.setInfo(procName);
			proc=conn.prepareCall(this.getProcStr(procName, inpara, outpara));
			int i=1;
			BinMap innoex=this.getNoExKey(inpara);
			for(int j=0;j<innoex.size();j++)
			{
				if("integer".equals(innoex.getValue(j).toString()))
				{
					proc.setInt(i, Integer.parseInt(inpara.getValue(j).toString()));
					
				}else{
					proc.setString(i, inpara.getValue(j).toString());
				}
				Log.setInfo("int="+inpara.getValue(j).toString());
				i++;
			}
			
			BinMap outnoex=this.getNoExKey(outpara);
			for(int j=0;j<outnoex.size();j++)
			{
				if("integer".equals(outnoex.getValue(j).toString()))
				{
					proc.registerOutParameter(i, Types.INTEGER);
					
				}else if("time".equals(outnoex.getValue(j).toString()))
				{
					proc.registerOutParameter(i, Types.TIME);
				}
				else{
					proc.registerOutParameter(i, Types.VARCHAR);
				}
				Log.setInfo("out="+outnoex.getKey(j));
				i++;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proc;
	}
	
	private String getProcStr(String procName, BinMap inpara, BinMap outpara){
		String procstr="{call "+procName+"(";
		int count=inpara.size()+outpara.size();
		for(int i=0;i<count;i++){
			if(i==0)
				procstr+="?";
			else
				procstr+=",?";
		}
		
		procstr+=")}";		
		return procstr;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbUtil isf=new DbUtil();
		BinMap lm=new BinMap();
		lm.put("a.string", "1");
		lm.put("b.nextval as b1", "2");
		lm.put("c", "3");
		lm.put("d", "4");
//		
//		String i=isf.getInsertPart(lm);
//		String u=isf.getUpdatePart(lm);
//		String s=isf.getSelectField(lm);
		BinMap s=isf.getNoExKey(lm);
//		
//		System.out.println(i);
//		System.out.println(u);
//		System.out.println(s);
		System.out.println(s.getItem());

	}

}
