package com.bin.xml;

import com.bin.object.BinList;
import com.bin.object.BinMap;

public interface IXmlView {
	public String FlageXML(String flage,String msg);
	public String ReturnListXML(BinMap title,BinList list);
	public String getInterXml(String name,BinMap title,BinList list);
}
