package com.bin.app.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public class ActionXml extends Action {
	protected Document doc=null; 
	protected Element root =null; 
	protected BinMap defaultArgs=null; 
	public ActionXml(){}
	public ActionXml(HttpServletRequest request)
	{
		super(request);
	}
	
	public void xmlRs(BinMap para,BinMap args)
	{
		this.xmlinit();
		if(!para.containsKey("count"))para.put("count", "0");
		if(!para.containsKey("return"))para.put("return", "00");
		if(!para.containsKey("returnflag"))para.put("returnflag", "返回成功");
		this.createArgsElement(args);
		for(int i=0;i<para.size();i++)
		{			
			this.addElement(para.getKey(i).toString(), para.getValue(i).toString());
		}
	}
	
	public void xmlInfo(BinList rows,BinMap para)
	{
		this.xmlInfo(rows, para,new BinMap());
	}
	
	public void xmlInfo(BinList rows,BinMap para,BinMap args)
	{
		this.xmlinit();
		if(!args.containsKey("count"))args.put("count", rows.size());
		if(!args.containsKey("return"))args.put("return", "00");
		this.createArgsElement(args);
		Element value=this.addElement("value");
		for(int i=0;i<rows.size();i++)
		{
			Element row=value.addElement("row");
			String txt="";			
			HashMap rowdata=rows.getItem(i);
			for(int j=0;j<para.size();j++)
			{
				String key =para.getKey(j).toString();
				txt+="【"+para.getValue(j)+"】"+this.getRowValue(rowdata, key)+"\r\n";
			}
			row.addText(txt);
		}
		
	}
	
	public void xmlSessionInfo(BinMap inarr)
	{
		BinList rows=new BinList();
		BinMap para=new BinMap();
		BinList oirows=new BinList();
		BinMap separa=new BinMap();
		BinMap args=new BinMap();
		if(inarr.containsKey("rows"))rows=(BinList) inarr.getValue("rows");
		if(inarr.containsKey("para"))para=(BinMap) inarr.getValue("para");
		if(inarr.containsKey("oirows"))oirows=(BinList) inarr.getValue("oirows");
		if(inarr.containsKey("separa"))separa=(BinMap) inarr.getValue("separa");
		if(inarr.containsKey("args"))args=(BinMap) inarr.getValue("args");
		this.xmlInfo(rows, para, args);
		if(rows.size()>0&&separa.size()>0&&oirows.size()>0)
		{
			Element session=this.addElement("truevalue");
			HashMap os=oirows.getItem(0);
			HashMap s=rows.getItem(0);
			String regex="\\[(\\w+?)\\]";
			Pattern p=Pattern.compile(regex,Pattern.UNICODE_CASE);
			for(int i=0;i<separa.size();i++)
			{
				String oivalue="";
				String dicvalue="";
				String key=separa.getKey(i).toString();
				String value=separa.getValue(i).toString();
				oivalue=value;
				dicvalue=value;
				if(key.indexOf(".")>0)
				{
					
					String[] keyarr=key.split("\\.");
					Matcher m=p.matcher(value);
					while(m.find())
					{
						String tempvalue=os.get(m.group(i)).toString();
						String tempdicvalue=s.get(m.group(i)).toString();
						if("value".equals(keyarr[1]))
						{
							tempdicvalue=tempvalue;							
						}
						else if("dicvalue".equals(keyarr[1]))
						{
							tempvalue=tempdicvalue;
						}
						else{}
						dicvalue=dicvalue.replace("["+m.group(1)+"]", tempdicvalue);
						oivalue=oivalue.replace("["+m.group(1)+"]", tempvalue);						
					}
				}				
				else
				{
					Matcher m=p.matcher(value);
					while(m.find())
					{
						dicvalue=dicvalue.replace("["+m.group(1)+"]", s.get(m.group(1)).toString());
						oivalue=oivalue.replace("["+m.group(1)+"]", os.get(m.group(1)).toString());
					}
				}
				
				Element rowline=this.addElement("row",null,session);
				this.addElement("name",key,rowline);
				this.addElement("value",oivalue,rowline);
				this.addElement("dicvalue",dicvalue,rowline);	
			}
		}
	}
	
	public void xmlList(BinList rows,BinMap para,BinMap args)
	{
		this.xmlinit();
		if(!args.containsKey("count"))args.put("count", rows.size());
		if(!args.containsKey("return"))args.put("return", "00");
		this.createArgsElement(args);
	}
	
	private void getDefaultArgs()
	{
		if(this.defaultArgs.containsKey("rowsonlyone"))this.defaultArgs.put("rowsonlyone", ""); 
	}
	
	public void createArgsElement(BinMap args)
	{
		for(int i=0;i<args.size();i++)
		{
			this.addElement(args.getKey(i).toString(), args.getValue(i).toString());
		}
	}
	
	public String getRowValue(HashMap row,String key)
	{
		String value=row.containsKey(key)?row.get(key).toString():"";
		return value;
		
	}
	
	public Element addElement(String name)
	{
		
		return this.addElement(name, null);		
	}
	
	public Element addElement(String name,String txt)
	{
		
		return this.addElement(name, txt,this.root);		
	}
	
	public Element addElement(String name,String txt,Element parent)
	{
		Element element=parent.addElement(name);
		if(txt!=null)element.addText(txt);
		return element;		
	}
	
	public List<Element> addElementByName(String name,String txt)
	{
		return this.addElementByName(name, txt, "/root");		
	}
	
	public List<Element> addElementByName(String name,String txt,String path)
	{
		List<Element> list=new ArrayList<Element>();
		Iterator<Element> it=this.queryElement(path);
		Element parent=null;
		while (it.hasNext())
		{
			parent=it.next();
			Element item=parent.addElement(name);			
			item.addText(txt);
			list.add(item);
		}
		return list;		
	}
	
	public Iterator<Element> queryElement(String path)
	{
		@SuppressWarnings("unchecked")
		List<Element> projects=doc.selectNodes(path);	      
		Iterator<Element> it=projects.iterator();
		return it;
		
	}
	public void xmlinit()
	{
		doc= DocumentHelper.createDocument();
		doc.setXMLEncoding("utf-8");
		root= doc.addElement("root");
		if(this.defaultArgs!=null)this.getDefaultArgs();
	}
	
	public void setXml(String xml)
	{
		try {
			doc=DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	public String xmlResponse()
	{
		return this.doc.asXML();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActionXml xml=new ActionXml();
//		xml.xmlRs(new LHBMap());
		
//		LHBList list=new LHBList();
//		LHBMap para=new LHBMap();
//		para.put("name", "姓名");
//		para.put("sex", "性别");
//		list.put(0, "name", "张三");
//		list.put(0, "sex", "男");
//		list.put(1, "name", "李四");
//		list.put(1, "sex", "女");
//		
//		xml.xmlInfo(list, para);
		
//		LHBList list=new LHBList();
//		LHBMap para=new LHBMap();
//		para.put("name", "姓名");
//		para.put("sex", "性别");
//		list.put(0, "name", "张三");
//		list.put(0, "sex", "男");
//		list.put(1, "name", "李四");
//		list.put(1, "sex", "女");
//		LHBList diclist=new LHBList();
//		diclist.put(0, "name", "张三");
//		diclist.put(0, "sex", "1");
//		diclist.put(1, "name", "李四");
//		diclist.put(1, "sex", "2");
//		LHBMap separa=new LHBMap();
//		separa.put("xm", "[name] [sex]");
//		separa.put("xb", "[sex]");
//		
//		LHBMap inarr=new LHBMap();
//		inarr.put("oirows",diclist);
//		inarr.put("rows", list);
//		inarr.put("para", para);
//		inarr.put("separa", separa);
//		xml.xmlSessionInfo(inarr);
		
		System.out.println(xml.xmlResponse());

	}

}
