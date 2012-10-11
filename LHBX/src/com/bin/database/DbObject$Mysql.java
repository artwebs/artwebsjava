package com.bin.database;

public class DbObject$Mysql extends DbObject {
	public DbObject$Mysql(){
		super();
	}
	
	public DbObject$Mysql(String dbtype,String Driverstr,String jdbcstr,String username,String userpwd ){	
		super(dbtype,Driverstr,jdbcstr,username,userpwd);
	}
}
