package com.bin.webcontrol;

import com.bin.object.BinList;

public interface IWebList {
	public void setBindData(Object obj,String methodName);
	public void setCurrentPage(int currentPage);
	public void setPageSize(int pageSize);
	public void setKeyFied(String keyFied);
	public void setInList(BinList inList);
	public void setUrl(String url);
	public void setForm(String form);
	public void setMethodText(String methodText);
	public void setMody(boolean mody);
	public void setDel(boolean del);
	public void setAdd(boolean add);
	public void setOther(boolean other);
	public void setAllother(boolean allother);
	public void setAllMethodText(String allMethodText);
	public void setModyText(String modyText);
	public void setDelText(String delText);
	public void setAddText(String addText);
	public void setMethodUrl(String methodUrl);
	public String Display();
}
