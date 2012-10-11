package com.bin.delegate;

import java.util.HashMap;

public interface IEventHandler {
	public void addEvent(Object object,String methodName,Object[] args);
	public HashMap<String,Object> notifyX() throws Exception;
}
