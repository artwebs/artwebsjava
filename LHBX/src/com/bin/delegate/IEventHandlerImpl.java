package com.bin.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IEventHandlerImpl implements IEventHandler{
	private List<IEventImpl> objects;
	public IEventHandlerImpl(){
		objects=new ArrayList<IEventImpl>();
	}
	public void addEvent(Object object,String methodName,Object[] args){
		objects.add(new IEventImpl(object,methodName,args));
	}
	public HashMap<String,Object> notifyX() throws Exception{
		HashMap<String,Object> rs=new HashMap<String,Object>();
		for(IEventImpl e:objects){
			rs.put(e.getMethodName(),e.invoke());
		}
		return rs;
	}
}
