package com.bin.database;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public interface IDbObject {
	public void setDbtype(String dbtype);
	public void setDriverstr(String driverstr);
	public void setJdbcstr(String jdbcstr);
	public void setUsername(String username);
	public void setUserpwd(String userpwd);
	public Boolean exec(String sql);
	public Boolean insert(String tablename,BinMap para);
	public Boolean update(String tablename,BinMap para,String wherestr);
	public Boolean delete(String tablename,String wherestr);
	public BinMap selectMap(String tablename,BinMap para,String wherestr);
	public BinMap selectMap(String tablename,BinMap para);
	/**
	 * 无记录总数等列表查询
	 * @param tablename
	 * @param para
	 * @param wherestr
	 * @return LHBList
	 */
	public BinList selectList(String tablename,BinMap para,String wherestr);
	public BinList selectList(String tablename,BinMap para);
	/**
	 * 包含了记录总数等列表查询
	 * @param tablename
	 * @param para
	 * @param wherestr
	 * @return  {[Recordcount int 录总数][RecordContent LHBList 录总数]}
	 */ 
	public BinMap selectListMap(String tablename,BinMap para,String wherestr,int currentpage,int pagesize);
	/**
	 * 包含了记录总数等列表查询
	 * @param tablename
	 * @param para
	 * @param wherestr
	 * @return  {[Recordcount int 录总数][RecordContent LHBList 录总数]}
	 */ 
	public BinMap selectListMap(String tablename,BinMap para,int currentpage,int pagesize);

	public BinMap runProcMap(String procName,BinMap inpara,BinMap outpara);
	public BinList runProcList(String procName,BinMap inpara,BinMap outpara);
	public BinList runProcListMap(String procName,BinMap inpara,BinMap outpara);
}
