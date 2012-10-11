package com.bin.xml;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public class IXmlViewImpl implements IXmlView {
	public String FlageXML(String flage,String msg){
		String rs=null;
		StringBuffer rssb=new StringBuffer();
		rssb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		rssb.append("<root>");
		rssb.append("<count>0</count>");
		rssb.append("<return>"+flage+"</return>");
		rssb.append("<returnflag>"+msg+"</returnflag>");
		rssb.append("</root>");
		rs=rssb.toString();
		return rs;
	}

	public String ReturnListXML(BinMap title,BinList list) {
		StringBuffer rsSb=new StringBuffer();
		rsSb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		rsSb.append("<root>");
		rsSb.append("<count>"+list.size()+"</count>");
		rsSb.append("<return>00</return>");
		rsSb.append("<value>");
		for(int i=0;i<list.size();i++){
			rsSb.append("<row>");
			for(int j=0;j<title.size();j++){
				rsSb.append("<"+title.getValue(j)+">");
				rsSb.append(list.getValue(i, title.getKey(j).toString()));
				rsSb.append("</"+title.getValue(j)+">");
			}
			rsSb.append("</row>");
		}
		rsSb.append("</value>");
		rsSb.append("</root>");
		rsSb.append("");
		return rsSb.toString();
	}
	
	public String getInterXml(String name,BinMap title,BinList list){
		StringBuffer rsSb=new StringBuffer();
		StringBuffer rscnameSb=new StringBuffer();
		StringBuffer rsnameSb=new StringBuffer();
		StringBuffer dataSb=new StringBuffer();
		String flag="00";
		for(int i=0;i<title.size();i++){
			if(i>0)flag="";
			rscnameSb.append("                        <Data>"+flag+"</Data>\r\n");
			rsnameSb.append("                        <Data>"+title.getValue(i)+"</Data>\r\n");
		}
		
		for(int i=0;i<list.size();i++){
			dataSb.append("                    <Row>\r\n");
			for(int j=0;j<title.size();j++){
				if(list.containsKey(title.getKey(j))){
					dataSb.append("                        <Data>"+list.getValue(i, title.getKey(j))+"</Data>\r\n");
				}else{
					dataSb.append("                        <Data></Data>\r\n");
				}
			}			
			dataSb.append("                    </Row>\r\n");
		}
		rsSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
		rsSb.append("<root>\r\n");
		rsSb.append("    <value>\r\n");	
		rsSb.append("                    <Row>\r\n");
		rsSb.append(rscnameSb.toString());
		rsSb.append("                    </Row>\r\n");
		rsSb.append("                    <Row>\r\n");
		rsSb.append(rsnameSb.toString());
		rsSb.append("                    </Row>\r\n");
		rsSb.append(dataSb.toString());
		rsSb.append("                </value>\r\n");
		rsSb.append("</root>\r\n");
		return rsSb.toString();
	}

}
