package com.bin.app.model;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public class ModelDic extends Model {
	protected String gNameField="GNAME";
	protected String keyField="DICKEY";
	protected String valueField="DICVALUE";
	
	public String getDicVlaue(String gname,String key){
		String rsStr="";
		BinMap inlm=new BinMap();
		inlm.put(valueField, "");
		BinMap mp=this.selectMap(inlm,gNameField+"='"+gname+"' and "+keyField+"='"+key+"'");
		if(mp.size()>0)rsStr=mp.getValue(valueField).toString();
		return rsStr;
		
	}
	
	public String getDicKey(String gname,String value){
		String rsStr="";
		BinMap inlm=new BinMap();
		inlm.put(keyField, "");
		BinMap mp=this.selectMap(inlm,gNameField+"='"+gname+"' and "+valueField+"='"+value+"'");
		if(mp.size()>0)rsStr=mp.getValue(keyField).toString();
		return rsStr;
	}
	
	public BinList getDicList(String gname){
		BinMap inlm=new BinMap();
		inlm.put(keyField, "");
		inlm.put(valueField, "");
		BinList list=this.selectList(inlm, gNameField+"='"+gname+"' ");
		return list;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
