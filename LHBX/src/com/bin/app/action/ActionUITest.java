package com.bin.app.action;

import junit.framework.TestCase;


public class ActionUITest extends TestCase {
	public void testUI()
	{
		ActionUI ui=new ActionUI();
		ui.initWebLayout();
		ui.appendScriptContent("alert('1111');");
		String rs=ui.webReponse();
		System.out.println(rs);
	}
}
