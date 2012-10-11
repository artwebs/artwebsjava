package com.bin.webcontrol;

import javax.servlet.http.HttpServletRequest;

import com.bin.object.BinList;

public interface IWebListMethod {
	public BinList getParameter();
	public IWebList getWebList(int currpage);
	public IWebList getWebList(HttpServletRequest request);
	public String InsertInfo(String id);
	public String DeleteInfo(String id);
	public String UpdateInfo(String id);
	public String OtherInfo(String id);
	public String DeleteMoreInfo(String id);
	public String OtherMoreInfo(String id);
}
