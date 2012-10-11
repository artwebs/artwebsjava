package com.bin.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public class DbObject extends Db {
	protected DbUtil dbutil=new DbUtil();
	public DbObject(){
		super();
	}
	
	public DbObject(String dbtype,String Driverstr,String jdbcstr,String username,String userpwd ){	
		super(dbtype,Driverstr,jdbcstr,username,userpwd);
	}
	
	public Boolean exec(String sql) {
		 boolean rsFlag=false;
		if(this.ExecuteSQL(sql)>0)rsFlag=true;
		return rsFlag;
	}

	public Boolean delete(String tablename, String wherestr) {
	    boolean rsFlag=false;
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("delete from ");
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	    
	    if(this.ExecuteSQL(sqlSb.toString())>0)rsFlag=true;
		return rsFlag;
	}


	public Boolean insert(String tablename, BinMap para) {
	    boolean rsFlag=false;
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("insert into ");
	    sqlSb.append(tablename);
	    sqlSb.append(" ");	
	    sqlSb.append(dbutil.getInsertPart(para));	
	    if(this.ExecuteSQL(sqlSb.toString())>0)rsFlag=true;
		return rsFlag;
	}


	public BinMap selectMap(String tablename, BinMap para,
			String wherestr) {
		BinMap rsLm=new BinMap();
		if(para.size()==0)para=this.getTableCols(tablename);
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("select ");
	    sqlSb.append(dbutil.getSelectField(para));
	    sqlSb.append(" from ");	
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	 
	    CachedRowSet crs=this.QuerySQL(sqlSb.toString());
	    para=dbutil.getNoExKey(para);
	    try {
			if(crs.next()){
				for(int i=0;i<para.size();i++){
					if(crs.getObject(para.getKey(i).toString())!=null){
						rsLm.put(para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
					}else{
						rsLm.put(para.getKey(i).toString(), "");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsLm;
	}


	public BinMap selectMap(String tablename, BinMap para) {
		BinMap rsLm=new BinMap();
		rsLm=this.selectMap(tablename, para, "1=1");
		return rsLm;
	}


	public BinList selectList(String tablename, BinMap para,
			String wherestr) {
		BinList rsList=new BinList();
	    StringBuffer sqlSb=new StringBuffer();
	    if(para.size()==0)para=this.getTableCols(tablename);
	    sqlSb.append("select ");
	    sqlSb.append(dbutil.getSelectField(para));
	    sqlSb.append(" from ");	
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	 
	    CachedRowSet crs=this.QuerySQL(sqlSb.toString());
	    para=dbutil.getNoExKey(para);
	    int n=0;
	    try {
			while(crs.next()){
				for(int i=0;i<para.size();i++){
					if(crs.getObject(para.getKey(i).toString())!=null){
						rsList.put(n, para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
					}else{
						rsList.put(n, para.getKey(i).toString(),"");
					}
					//rsList.put(n, para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
				}
				n++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsList;
	}


	public BinList selectList(String tablename, BinMap para) {
		BinList rsList=new BinList();
	    rsList=this.selectList(tablename, para, "1=1");
		return rsList;
	}


	public BinMap selectListMap(String tablename, BinMap para,
			String wherestr, int currentpage, int pagesize) {
		BinMap rsLm=new BinMap();
		if(para.size()==0)para=this.getTableCols(tablename);
		int count=0;
		BinList rsList=new BinList();
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("select ");
	    sqlSb.append(dbutil.getSelectField(para));
	    sqlSb.append(" from ");	
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	 
	    CachedRowSet crs=this.QuerySQL(sqlSb.toString());
	    para=dbutil.getNoExKey(para);
	    count=crs.size();
	    int beginnum=(currentpage-1)*pagesize;
        int endnum=currentpage*pagesize;
        int num=0;
        int n=0;
	    try {	
	    	while(crs.next()){
	    		if(beginnum<=num&&num<endnum ){		    		
					for(int i=0;i<para.size();i++){
						if(crs.getObject(para.getKey(i).toString())!=null){
							rsList.put(n, para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
						}else{
							rsList.put(n, para.getKey(i).toString(),"");
						}
						//rsList.put(n, para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
					}
					n++;
	    		}
	    		num++;
	    		if(num>=endnum)break;				
	    	}	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rsLm.put("Recordcount", count);
		rsLm.put("RecordContent", rsList);
		return rsLm;
	}


	public BinMap selectListMap(String tablename, BinMap para,
			int currentpage, int pagesize) {
		BinMap rsLm=new BinMap();
		rsLm=this.selectListMap(tablename, para, "1=1", currentpage, pagesize);
		return rsLm;
	}


	public Boolean update(String tablename, BinMap para, String wherestr) {
	    boolean rsFlag=false;
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("update ");
	    sqlSb.append(tablename);
	    sqlSb.append(" set ");	
	    sqlSb.append(dbutil.getUpdatePart(para));	
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	
	    if(this.ExecuteSQL(sqlSb.toString())>0)rsFlag=true;
		return rsFlag;
	}

	public BinMap runProcMap(String procName, BinMap inpara, BinMap outpara) {
		Connection conn=null;
		BinMap rslm=new BinMap();
		try {
			conn=this.getConnetion();
			CallableStatement proc = null;
			if(conn!=null){				
				proc=dbutil.getProc(conn, procName, inpara, outpara);
				proc.execute();
				BinMap para=dbutil.getNoExKey(outpara);
				int j=inpara.size();
				for(int i=0;i<para.size();i++){
					if("integer".equals(para.getValue(i)))
					{
						rslm.put(para.getKey(i), proc.getInt(j+i+1));
					}else 
					{
						
						rslm.put(para.getKey(i), proc.getString(j+i+1));
					}
				}
				
				proc.close();
				proc=null;
				conn.close();
				conn=null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}	
		return rslm;
	}
	
	public BinMap getTableCols(String tablename)
	{
		return new BinMap();
	}

	public BinList runProcList(String procName, BinMap inpara, BinMap outpara) {
		// TODO Auto-generated method stub
		return null;
	}

	public BinList runProcListMap(String procName, BinMap inpara, BinMap outpara) {
		// TODO Auto-generated method stub
		return null;
	}




}
