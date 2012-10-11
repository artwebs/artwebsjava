package com.bin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.bin.LHB;
import com.bin.log.Log;
import com.bin.object.BinList;
import com.bin.object.BinMap;
import com.sun.rowset.CachedRowSetImpl;

public abstract class Db extends LHB  implements IDbObject {
	protected Connection conn;
	private String dbtype;
	private String driverstr;
	private String jdbcstr;
	private String username;
	private String userpwd;
	private String key;
	
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public void setDriverstr(String driverstr) {
		this.driverstr = driverstr;
	}

	public void setJdbcstr(String jdbcstr) {
		this.jdbcstr = jdbcstr;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	
	public Db(){}
	public Db(String dbtype,String Driverstr,String jdbcstr,String username,String userpwd ){		
			this.setDbtype(dbtype);
			this.setDriverstr(Driverstr);
			this.setJdbcstr(jdbcstr);
			this.setUsername(username);
			this.setUserpwd(userpwd);				
	}
	

	protected Connection getConnetion(){
		if(driverstr!=null&&jdbcstr!=null&&username!=null&&userpwd!=null){
			try {
				Class.forName(driverstr);
				conn=DriverManager.getConnection(jdbcstr, username, userpwd);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
		}
        
		return conn;
	}

	
	public int ExecuteSQL(String sql) {
		int result=0;
		//System.out.println(sql);
		Log.getLogger();
		Log.setDebug(sql);
		Statement st=null;
		Connection conn=null;
		try {
			conn=this.getConnetion();
			if(conn!=null){
				Log.getLogger();
				Log.setDebug(sql);
				st=conn.createStatement();
				result=st.executeUpdate(sql);
				st.close();
				st=null;
				conn.close();
				conn=null;
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}		
		return result;
	}

	public CachedRowSet QuerySQL(String sql) {
		ResultSet rs=null;
		CachedRowSet crs=null;
		Connection conn=null;
		Statement st=null;
		try {
			conn=this.getConnetion();
			if(conn!=null){
				Log.getLogger();
				Log.setDebug(sql);
				st=conn.createStatement();
				crs=new CachedRowSetImpl();
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
	
	public abstract Boolean exec(String sql);
	public abstract Boolean insert(String tablename,BinMap para);
	public abstract Boolean update(String tablename,BinMap para,String wherestr);
	public abstract Boolean delete(String tablename,String wherestr);
	public abstract BinMap selectMap(String tablename,BinMap para,String wherestr);
	public abstract BinMap selectMap(String tablename,BinMap para);
	/**
	 * 无记录总数等列表查询
	 * @param tablename
	 * @param para
	 * @param wherestr
	 * @return LHBList
	 */
	public abstract BinList selectList(String tablename,BinMap para,String wherestr);
	public abstract BinList selectList(String tablename,BinMap para);
	/**
	 * 包含了记录总数等列表查询
	 * @param tablename
	 * @param para
	 * @param wherestr
	 * @return  {[Recordcount int 录总数][RecordContent LHBList 录总数]}
	 */ 
	public abstract BinMap selectListMap(String tablename,BinMap para,String wherestr,int currentpage,int pagesize);
	/**
	 * 包含了记录总数等列表查询
	 * @param tablename
	 * @param para
	 * @param wherestr
	 * @return  {[Recordcount int 录总数][RecordContent LHBList 录总数]}
	 */ 
	public abstract BinMap selectListMap(String tablename,BinMap para,int currentpage,int pagesize);


	public static void main(String[] args){
//		ISqlBase database=new SqlBase("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/messageinserver?characterEncoding=GBK","root","windows123");		
//		CachedRowSet rs=database.QuerySQL("select * from t_inmessages");
//		System.out.println(rs.size());
		
		
	}
}
