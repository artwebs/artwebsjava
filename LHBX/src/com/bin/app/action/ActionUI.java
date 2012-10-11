package com.bin.app.action;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ActionUI extends Action{
	protected Document doc=null; 
	protected Element html =null; 
	protected Element head =null;
	protected Element body =null;
	protected Element north =null;
	protected Element south =null;
	protected Element east =null;
	protected Element west =null;
	protected Element center =null;
	protected Element tabs =null;
	
	protected String scriptContent="";
	
	public void initWebLayout()
	{
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbfactory.newDocumentBuilder();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		 
		doc = builder.newDocument();
		html = doc.createElement("html");
		doc.appendChild(html);
		head = doc.createElement("head");
		body = doc.createElement("body");
		html.appendChild(head);
		html.appendChild(body);		
		html.setAttribute("html", "http://www.w3.org/1999/xhtml");
		
		this.appendLink("./EasyUi/themes/default/easyui.css");
		this.appendLink("./EasyUi/themes/icon.css");
		this.appendScript("./EasyUi/jquery-1.6.min.js");
		this.appendScript("./EasyUi/jquery.easyui.min.js");
		this.appendScript("./EasyUi/locale/easyui-lang-zh_CN.js");
	}
	
	protected void initPartLayout()
	{
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbfactory.newDocumentBuilder();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		 
		doc = builder.newDocument();
	}
	
	protected void appendLink(String src)
	{
		Element link=this.doc.createElement("link");
		this.head.appendChild(link);
		link.setAttribute("rel", "stylesheet");
		link.setAttribute("type", "text/css");
		link.setAttribute("href", src);
	}
	
	protected void appendScript(String src)
	{
		Element link=this.doc.createElement("script");
		this.head.appendChild(link);
		link.setAttribute("language", "stylesheet");
		link.setAttribute("JavaScript", "text/javascript");
		link.setAttribute("src", src);
	}
	
	public void appendScriptContent(String conent)
	{
		this.scriptContent+=conent;
	}
	
	public String webReponse()
	{
		Element script=this.doc.createElement("script");
		script.setTextContent("{"+this.scriptContent+"}");
		this.head.appendChild(script);
		
		
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans;
		try {
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");

			
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			
			trans.transform(source, result);
			
			return sw.toString();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return null;
		
	}
	
	
	public String partReponse()
	{
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans;
		try {
			trans = transfac.newTransformer();
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			
			trans.transform(source, result);
			
			return sw.toString();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return null;
		
	}

}
