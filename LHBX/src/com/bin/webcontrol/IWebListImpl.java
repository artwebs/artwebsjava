package com.bin.webcontrol;


import com.bin.object.BinList;

public class IWebListImpl implements IWebList {

	private IWebList list=new WebList();
	
	public IWebListImpl(){
		
	}
	public void setModyText(String modyText) {
		list.setModyText(modyText);
	}
	public void setDelText(String delText) {
		list.setDelText(delText);
	}
	public void setAddText(String addText) {
		list.setAddText(addText);
	}
	public void setAllother(boolean allother) {
		list.setAllother(allother);
	}
	public void setAllMethodText(String allMethodText) {
		list.setAllMethodText(allMethodText);
	}
	public IWebListImpl(IWebList ilist){
		this.list=ilist;
	}
	
	public String Display() {		
		return list.Display();
	}

	public void setAdd(boolean add) {
		list.setAdd(add);
	}

	public void setBindData(Object obj, String methodName) {
		list.setBindData(obj,methodName);
	}

	public void setCurrentPage(int currentPage) {
		list.setCurrentPage(currentPage);
		
	}

	public void setDel(boolean del) {
		list.setDel(del);
		
	}

	public void setForm(String form) {
		list.setForm(form);
		
	}

	public void setInList(BinList inList) {
		list.setInList(inList);
		
	}

	public void setKeyFied(String keyFied) {
		list.setKeyFied(keyFied);
		
	}

	public void setMethodText(String methodText) {
		list.setMethodText(methodText);		
	}

	public void setMethodUrl(String methodUrl) {
		list.setMethodUrl(methodUrl);		
	}

	public void setMody(boolean mody) {
		list.setMody(mody);		
		
	}

	public void setOther(boolean other) {
		list.setOther(other);		
	}

	public void setPageSize(int pageSize) {
		list.setPageSize(pageSize);	
	}

	public void setUrl(String url) {
		list.setUrl(url);	
	}

}
