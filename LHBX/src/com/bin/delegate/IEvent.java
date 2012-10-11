package com.bin.delegate;

public interface IEvent {
	public void setObject(Object object);
	public void setMethodName(String methodName);
	public void setParams(Object[] params);
	public Class[] getParamTypes();
	public Object getObject();
	public String getMethodName();
	public Object[] getParams();
	public Object invoke()throws Exception;
}
