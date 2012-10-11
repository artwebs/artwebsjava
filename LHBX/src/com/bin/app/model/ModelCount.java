package com.bin.app.model;

import java.util.Random;

import com.bin.object.BinList;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

public class ModelCount extends Model {
	protected String coutNameField="";
	protected String coutValueField="";
	protected int ran=100;
	public String getNewID(String countName){
		String countvalue="";
		BinMap inlm=new BinMap();
		inlm.put(this.coutNameField, countName);
		inlm.put(this.coutValueField, "0");
		BinList rslist=this.selectList(inlm, this.coutNameField+"='"+countName+"'");
		if(rslist.size()>0){
			countvalue=rslist.getValue(0, this.coutValueField).toString();
		}else{
			this.insert(inlm);
			countvalue="0";
		}
		inlm.clear();
		inlm.put(this.coutValueField+".realstring", this.coutValueField+"+1");
		this.update(inlm, this.coutNameField+"='"+countName+"'");
		return countvalue;		
	}
	public String getNewID(String countName,Integer len){
		return Utils.GetEnoughLenStr(len, this.getNewID(countName));
		
	}
	public String getRanNewID(String countName,Integer len){
		String rs="";
		Random r=new Random();
		String rstr=(r.nextInt(ran*5)+r.nextInt(ran*5-1))%ran+"";
		int rlen=(ran+"").length();
		rs+=Utils.GetEnoughLenStr(len-rlen+1, this.getNewID(countName));
		rs+=Utils.GetEnoughLenStr(rlen-1, rstr);
		return rs;
		
	}
	public String getNewIDNotUpdate(String countName){
		String countvalue="";
		BinMap inlm=new BinMap();
		inlm.put(this.coutNameField, countName);
		inlm.put(this.coutValueField, "0");
		BinList rslist=this.selectList(inlm, this.coutNameField+"='"+countName+"'");
		if(rslist.size()>0){
			countvalue=rslist.getValue(0, this.coutValueField).toString();
		}else{
			this.insert(inlm);
			countvalue="0";
		}	
		return countvalue;	
	}
	public String getNewIDNotUpdate(String countName,Integer len){
		return Utils.GetEnoughLenStr(len, this.getNewIDNotUpdate(countName));
	}
	public Boolean getNewIDToUpdate(String countName){
		Boolean rsFlag=false;
		BinMap inlm=new BinMap();
		inlm.put(this.coutValueField+".realstring", this.coutValueField+"+1");
		if(this.update(inlm, this.coutNameField+"='"+countName+"'"))rsFlag=true;
		return rsFlag;		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ModelCount m=new ModelCount();
		m.tablename="T_SYSCOUT";
		m.coutNameField="COUNTNAME";
		m.coutValueField="COUNTVALUE";
		System.out.println(m.getNewID("MailID"));

	}

}
