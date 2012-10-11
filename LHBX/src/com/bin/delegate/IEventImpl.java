package com.bin.delegate;

import java.lang.reflect.Method;    
import java.util.Date;    
   
public class IEventImpl implements IEvent {
	
	private Object object;
	private String methodName;
	private Object[] params;
	private Class[] paramTypes;
	public IEventImpl(){
		
	}
	public Class[] getParamTypes() {
		return paramTypes;
	}
	public IEventImpl(Object object,String methodName){
		this.object=object;
		this.methodName=methodName;
	}
	public IEventImpl(Object object,String methodName,Object[] args){
		this.object=object;
		this.methodName=methodName;
		this.params=args;
		contractParamsTypes(args);
	}
	private void contractParamsTypes(Object[] params){
		this.paramTypes=new Class[params.length];
		for(int i=0;i<params.length;i++){
			this.paramTypes[i]=params[i].getClass();
		}
	}
	public Object getObject(){
		return object;
	}

	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
		this.contractParamsTypes(params);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object invoke() throws Exception{
		Method method=object.getClass().getMethod(this.getMethodName(), this.getParamTypes());
		if(method==null){
			return null;
		}
		Object obj=method.invoke(this.getObject(), this.params);
		return obj;
	}

	public static void main(String[] args){
		IEventImpl e=new IEventImpl();
	}
}
