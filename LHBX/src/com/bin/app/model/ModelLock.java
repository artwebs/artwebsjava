package com.bin.app.model;

import com.bin.object.BinMap;

public class ModelLock extends Model{
    protected String KeyField="";
    protected String lockField="";
    protected String lockUpValue="1";
    protected String LockDownValue="0";
    
    public Boolean lockup(String id){
    	Boolean rsFlag=false;
    	BinMap inlm=new BinMap();
    	inlm.put(lockField, lockUpValue);
    	rsFlag=this.update(inlm, KeyField+"='"+id+"'");
    	return rsFlag;
    }
    public Boolean lockdown(String id){
    	Boolean rsFlag=false;
    	BinMap inlm=new BinMap();
    	inlm.put(lockField, LockDownValue);
    	rsFlag=this.update(inlm, KeyField+"='"+id+"'");
    	return rsFlag;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
