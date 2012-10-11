package com.bin.app.model;

import com.bin.config.ConfigManager;
import com.bin.database.IDbObject;
import com.bin.object.BinList;
import com.bin.object.BinMap;

public class Model implements IDbObject {
	protected IDbObject database;
	protected String tablename;
	protected String dbPR="";
	private String dbtype;
	private String dbdriver;
	private String dburl;
	private String dbuser;
	private String dbpwd;
	
	public static Model model;
	public Model(){
		this.setDbPR("");
	}
	public Model(String dbpr){
		this.setDbPR(dbpr);
	}
	
	public Model(String dataType,String dataDriver,String dataUrl,String dataUser,String dataPwd){
		this.dbtype=dataType;
		this.dbdriver=dataDriver;
		this.dburl=dataUrl;
		this.dbuser=dataUser;
		this.dbpwd=dataPwd;
		this.getInsetance();
	}
	
	public void setDbtype(String dbtype) {
		this.dbtype=dbtype;
		
	}
	public void setDriverstr(String driverstr) {
		this.dbdriver=driverstr;
		
	}
	public void setJdbcstr(String jdbcstr) {
		this.dburl=jdbcstr;
		
	}
	public void setUsername(String username) {
		this.dbuser=username;
		
	}
	public void setUserpwd(String userpwd) {
		this.dbpwd=userpwd;		
	}
	
	

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setDbPR(String dbPR) {
		this.dbPR = dbPR;
		this.dbtype=ConfigManager.getAppSettingsValue(dbPR+"DB_Type").toString();
		this.dbdriver=ConfigManager.getAppSettingsValue(dbPR+"DB_Driver").toString();
		this.dburl=ConfigManager.getAppSettingsValue(dbPR+"DB_Url").toString();
		this.dbuser=ConfigManager.getAppSettingsValue(dbPR+"DB_UserName").toString();
		this.dbpwd=ConfigManager.getAppSettingsValue(dbPR+"DB_UserPwd").toString();
	}
	
	private void getInsetance(){
		Class objclass=null;
		try {
			objclass=Class.forName("com.lhb.database.DbObject$"+this.dbtype);
			this.database=(IDbObject)objclass.newInstance();
		} catch (Exception e) {
			try {
				objclass=Class.forName("com.lhb.database.DbObject");
				this.database=(IDbObject)objclass.newInstance();
			}catch (Exception ex){}finally{}
		}finally{
			this.database.setDbtype(this.dbtype);
			this.database.setDriverstr(this.dbdriver);
			this.database.setJdbcstr(this.dburl);
			this.database.setUsername(this.dbuser);
			this.database.setUserpwd(this.dbpwd);
		}
	}
	
	public Boolean delete(String wherestr) {
		this.getInsetance();
		return database.delete(tablename, wherestr);
	}

	public Boolean insert(BinMap para) {
		this.getInsetance();
		return database.insert(tablename, para);
	}

	public BinList selectList(BinMap para,
			String wherestr) {
		this.getInsetance();
		return database.selectList(tablename, para,wherestr);
	}

	public BinList selectList(BinMap para) {
		this.getInsetance();
		return database.selectList(tablename, para);
	}

	public BinMap selectListMap(BinMap para,
			String wherestr, int currentpage, int pagesize) {
		this.getInsetance();
		return database.selectListMap(tablename, para,wherestr,currentpage,pagesize);
	}

	public BinMap selectListMap( BinMap para,int currentpage, int pagesize) {
		this.getInsetance();
		return database.selectListMap(tablename, para,currentpage,pagesize);
	}

	public BinMap selectMap(BinMap para, String wherestr) {
		this.getInsetance();
		return database.selectMap(tablename,para,wherestr);
	}

	public BinMap selectMap(BinMap para) {
		this.getInsetance();
		return database.selectMap(tablename,para);
	}

	public Boolean update(BinMap para, String wherestr) {
		this.getInsetance();
		return database.update(tablename, para, wherestr);
	}
	public Boolean delete(String tablename, String wherestr) {
		this.getInsetance();
		return database.delete(tablename, wherestr);
	}

	public Boolean insert(String tablename, BinMap para) {
		this.getInsetance();
		return database.insert(tablename, para);
	}

	public BinMap selectMap(String tablename, BinMap para,
			String wherestr) {
		this.getInsetance();
		return database.selectMap(tablename, para,wherestr);
	}

	public BinMap selectMap(String tablename, BinMap para) {
		this.getInsetance();
		return database.selectMap(tablename, para);
	}

	public BinList selectList(String tablename, BinMap para,
			String wherestr) {
		this.getInsetance();
		return database.selectList(tablename, para, wherestr);
	}


	public BinList selectList(String tablename, BinMap para) {
		this.getInsetance();
		return database.selectList(tablename, para);
	}

	public BinMap selectListMap(String tablename, BinMap para,
			String wherestr, int currentpage, int pagesize) {
		this.getInsetance();
		return database.selectListMap(tablename, para, wherestr, currentpage, pagesize);
	}
	
	public BinMap RunSelectgetListMap(String tablename, BinMap para,
			String wherestr, Integer currentpage, Integer pagesize) {
		this.getInsetance();
		return database.selectListMap(tablename, para, wherestr, currentpage, pagesize);
	}



	public BinMap selectListMap(String tablename, BinMap para,
			int currentpage, int pagesize) {

		this.getInsetance();
		return database.selectListMap(tablename, para, currentpage, pagesize);
	}
	
	public BinMap RunSelectgetListMap(String tablename, BinMap para,
			Integer currentpage, Integer pagesize) {

		this.getInsetance();
		return database.selectListMap(tablename, para, currentpage, pagesize);
	}

	public Boolean update(String tablename, BinMap para, String wherestr) {
		this.getInsetance();
		return database.update(tablename, para, wherestr);
	}
	
	public Boolean exec(String sql){
		this.getInsetance();
		return database.exec(sql);
	}
	

	public BinMap runProcMap(String procName, BinMap inpara, BinMap outpara) {
		this.getInsetance();
		return database.runProcMap(procName, inpara, outpara);
	}
	public BinList runProcList(String procName, BinMap inpara, BinMap outpara) {
		this.getInsetance();
		return database.runProcList(procName, inpara, outpara);
	}
	public BinList runProcListMap(String procName, BinMap inpara, BinMap outpara) {
		this.getInsetance();
		return database.runProcListMap(procName, inpara, outpara);
	}
	
	
	public static void main(String[] args){
//		int i=0;
//		while(i<1000){
//			(new test()).start();	
//			i++;
//		}
//		TestClobModel t=new TestClobModel();		
//		System.out.println(t.setClob());
//		System.out.println(t.getQuery().getItem());
//		System.out.println(t.getProcMap().getItem());
//		System.out.println(t.getProcMap1().getItem());
		
		test2model m=new test2model();
		m.getProc();
	}
	
	
}

class TestClobModel extends Model {
	public TestClobModel(){
		super("DIC");
		this.tablename="test";
	}
	
	public BinMap getQuery(){
		BinMap rslm=new BinMap();
		BinMap inlm=new BinMap();
		inlm.put("ID", "");
		inlm.put("TEXT.clob", "123121312321");
		String w="ID='1'";
		rslm=this.selectMap(inlm, w);
		return rslm;
	}
	
	public boolean setClob(){
		boolean flag=false;
		BinMap inlm=new BinMap();
		inlm.put("TEXT.clob", "artwebs");
		String w="ID='1'";
		flag=this.update(this.tablename, inlm, w);
		return flag;
	}
	
	public BinMap getProcMap(){
		BinMap rslm=new BinMap();
		BinMap inpara=new BinMap();
		BinMap outpara=new BinMap();
		inpara.put("a.integer", "6");
		inpara.put("b.integer", "6");
		outpara.put("out.integer", "");
		rslm=this.runProcMap("ABC", inpara, outpara);
		return rslm;
	}
	public BinMap getProcMap1(){
		BinMap rslm=new BinMap();
		BinMap inpara=new BinMap();
		BinMap outpara=new BinMap();
		outpara.put("out.string", "");
		rslm=this.runProcMap("getrecodes", inpara, outpara);
		return rslm;
	}
}

class test extends Thread{
	private testmodel t=new testmodel();
	private test1model t1=new test1model();
	public void run(){
			BinList list=t.getlist();
			BinList list1=t1.getlist();
			System.out.println(list.size());
			System.out.println(list1.size());
		
	}
	

}

class testmodel extends Model{
	public testmodel(){
		super("SMS");
		this.tablename="t_inmessages";
	}
	public BinList getlist(){
		BinMap lm=new BinMap();
		lm.put("messageid", "");
		BinList list=this.selectList(lm);
		return list;
	}
}

class test1model extends Model{
	public test1model(){
		this.tablename="systemdic";
	}
	public BinList getlist(){
		BinMap lm=new BinMap();
		lm.put("Code", "");
		BinList list=this.selectList(lm);
		return list;
	}
}

class test2model extends Model
{
	public test2model(){
		this.tablename="pl_log";
	}
	
	public void getProc()
	{
		BinMap inlm=new BinMap();
		BinMap rslm=new BinMap();
		String code="";
		String message="";
		inlm.put("login_name", "user8");
		inlm.put("logdate", "2011-09-27 15:15:16");
		inlm.put("logsys", "jwt");
		inlm.put("logmod", "login");
		inlm.put("logact", "android");
		inlm.put("urlstr", "http://localhost/policeserver/index.php?mod=user&act=validateUser&login_name=user8&login_pwd=123&imei=357394011595465&imsi=460008323884551");
		rslm.put("code", code);
		rslm.put("message", message);
		rslm=this.runProcMap("insert_log", inlm, rslm);
		System.out.println(rslm.getItem());
	}
}

