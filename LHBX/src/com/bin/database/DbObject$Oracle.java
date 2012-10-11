package com.bin.database;

import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import oracle.jdbc.rowset.OracleCachedRowSet;

import com.bin.log.Log;
import com.bin.object.BinList;
import com.bin.object.BinMap;

public class DbObject$Oracle extends DbObject {
	public DbObject$Oracle(){
		super();
	}
	
	public DbObject$Oracle(String dbtype,String Driverstr,String jdbcstr,String username,String userpwd ){	
		super(dbtype,Driverstr,jdbcstr,username,userpwd);
	}
	
	
	public OracleCachedRowSet QueryOracleSQL(String sql){
		ResultSet rs=null;
		OracleCachedRowSet crs=null;
		Connection conn=null;
		Statement st=null;
		try {
			conn=this.getConnetion();
			if(conn!=null){
				Log.getLogger();
				Log.setDebug(sql);
				st=conn.createStatement();
				crs=new OracleCachedRowSet();
				rs=st.executeQuery(sql);
				crs.populate(rs);
				rs.close();				
				rs=null;
				conn.close();
				conn=null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}		
		return crs;
	}
	
	public BinMap selectMap(String tablename, BinMap para,
			String wherestr) {
		BinMap rsLm=new BinMap();
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("select ");
	    sqlSb.append(dbutil.getSelectField(para));
	    sqlSb.append(" from ");	
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	 
	    OracleCachedRowSet crs=this.QueryOracleSQL(sqlSb.toString());
	    para=dbutil.getNoExKey(para);
	    try {
			if(crs.next()){
				for(int i=0;i<para.size();i++){
					if(crs.getObject(para.getKey(i).toString())!=null){
						if("clob".equals(para.getValue(i).toString())){
							java.sql.Clob clob =crs.getClob(para.getKey(i).toString());
							Reader inStream = clob.getCharacterStream();
							char[] c = new char[(int) clob.length()];
							try {
								inStream.read(c);
								//data是读出并需要返回的数据，类型是String
								String data = new String(c);
								inStream.close();
								rsLm.put(para.getKey(i).toString(), data);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}finally{
								
							}
						}else{
							rsLm.put(para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
						}
						
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
	
	
	public BinList selectList(String tablename, BinMap para,
			String wherestr) {
		BinList rsList=new BinList();
		if(para.size()==0)para=this.getTableCols(tablename);
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("select ");
	    sqlSb.append(dbutil.getSelectField(para));
	    sqlSb.append(" from ");	
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);		    
	    OracleCachedRowSet crs=this.QueryOracleSQL(sqlSb.toString());
	    para=dbutil.getNoExKey(para);
	    int n=0;
	    try {
			while(crs.next()){
				for(int i=0;i<para.size();i++){
					if(crs.getObject(para.getKey(i).toString())!=null){
						if("clob".equals(para.getValue(i).toString())){
							java.sql.Clob clob =crs.getClob(para.getKey(i).toString());
							Reader inStream = clob.getCharacterStream();
							char[] c = new char[(int) clob.length()];
							try {
								inStream.read(c);
								//data是读出并需要返回的数据，类型是String
								String data = new String(c);
								inStream.close();
								rsList.put(n,para.getKey(i).toString(), data);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}finally{
								
							}
						}else{
							rsList.put(n,para.getKey(i).toString(), crs.getObject(para.getKey(i).toString()));
						}
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
	
	
	public BinMap selectListMap(String tablename, BinMap para,
			String wherestr, int currentpage, int pagesize) {
		BinMap rsLm=new BinMap();
		int count=0;
		BinList rsList=new BinList();
	    StringBuffer sqlSb=new StringBuffer();
	    sqlSb.append("select ");
	    sqlSb.append(dbutil.getSelectField(para));
	    sqlSb.append(" from ");	
	    sqlSb.append(tablename);
	    sqlSb.append(" where ");
	    sqlSb.append(wherestr);	 
	    OracleCachedRowSet crs=this.QueryOracleSQL(sqlSb.toString());
	    para=dbutil.getNoExKey(para);	    
	    int beginnum=(currentpage-1)*pagesize;
        int endnum=currentpage*pagesize;
        int num=0;
        int n=0;
	    try {	
	    	count=crs.getFetchSize();
	    	while(crs.next()){
	    		if(beginnum<=num&&num<endnum ){		    		
					for(int i=0;i<para.size();i++){
						if(crs.getObject(para.getKey(i).toString())!=null){
							if("clob".equals(para.getValue(i).toString())){
								java.sql.Clob clob =crs.getClob(para.getKey(i).toString());
								Reader inStream = clob.getCharacterStream();
								char[] c = new char[(int) clob.length()];
								try {
									inStream.read(c);
									//data是读出并需要返回的数据，类型是String
									String data = new String(c);
									inStream.close();
									rsList.put(n,para.getKey(i).toString(), data);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}finally{
									
								}
							}
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

	public Boolean update(String tablename, BinMap para, String wherestr) {
	    boolean rsFlag=false;
	    BinMap nopara=dbutil.getNoExKey(para);
	    if(nopara.containsValue("clob"))
	    	rsFlag=this.RunUpdateClob(tablename, para, wherestr);
	    else
	    	rsFlag=super.update(tablename, para, wherestr);
		return rsFlag;
	}
	
	private Boolean RunUpdateClob(String tablename, BinMap para, String wherestr){
		boolean rsFlag=false;			
		String sql="select "+dbutil.getSelectField(para)+" from "+tablename+" where "+wherestr+" for update" ;
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			conn=this.getConnetion();
			if(conn!=null){
				conn.setAutoCommit(false);
				st=conn.createStatement();
				rs=st.executeQuery(sql);
				Writer outStream=null;
				if (rs.next())
				{
					
					BinMap nokeypara=dbutil.getNoExPara(para);
					
					for(int i=0;i<nokeypara.size();i++){
						//得到java.sql.Clob对象后强制转换为oracle.sql.CLOB
						oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(nokeypara.getKey(i).toString());
						outStream = clob.getCharacterOutputStream();
						
						//data是传入的字符串，定义：String data
						char[] c = nokeypara.getValue(i).toString().toCharArray();
						outStream.write(c, 0, c.length);
					}
				}
				outStream.flush();
				outStream.close();
				
				rs.close();				
				rs=null;
				conn.commit();
				conn.close();
				conn=null;
				rsFlag=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}	
		return rsFlag;
	}
	
	public BinMap getTableCols(String tablename)
	{
		BinMap rslm=new BinMap();
		String sql="select column_name,data_type,data_length,data_precision,data_scale from user_tab_columns where table_name='"+tablename.toUpperCase()+"'";
		OracleCachedRowSet crs=this.QueryOracleSQL(sql);
		try {
			while(crs.next()){
				if("DATE".equals(crs.getString("DATA_TYPE")))				
					rslm.put("to_char("+crs.getString("COLUMN_NAME")+",'yyyy-mm-dd hh24:mi:ss') as "+crs.getString("COLUMN_NAME"), "");
				else
					rslm.put(crs.getString("COLUMN_NAME"), "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rslm;
		
	}
}
