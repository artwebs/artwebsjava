package com.bin.webcontrol;

import com.bin.delegate.IEvent;
import com.bin.delegate.IEventImpl;
import com.bin.object.BinList;
import com.bin.object.BinMap;

public class WebList implements IWebList {
	private int allRecordCount=0;
	private int currentPage=1;
	private int pageSize=10;
	private BinList inList=new BinList();
	private BinList outList=new BinList();
	private BinMap field=new BinMap();
	private String keyFied;
	private String url;
	private String form;
	private String MethodText="操作";
	private String AllMethodText="操作";
	private String modyText="修改";
	private String delText="删除";
	private String addText="添加";
	private boolean mody=false;
	private boolean del=false;
	private boolean add=false;
	private boolean other=false;
	private boolean allother=false;
	private String id;
	public void setModyText(String modyText) {
		this.modyText = modyText;
	}
	public void setDelText(String delText) {
		this.delText = delText;
	}
	public void setAddText(String addText) {
		this.addText = addText;
	}
	public void setAllother(boolean allother) {
		this.allother = allother;
	}
	public void setAllMethodText(String allMethodText) {
		this.AllMethodText = allMethodText;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private String methodUrl="#";

	private IEvent bindData;
	
	public String getMethodUrl() {
		return methodUrl;
	}

	public void setMethodUrl(String methodUrl) {
		this.methodUrl = methodUrl;
	}

	public void setBindData(IEvent bindData) {// (LHBList inList, int currentPage, int pageSize)
		this.bindData = bindData;
	}
	
	public void setBindData(Object obj,String methodName) {// (LHBList inList, int currentPage, int pageSize)
		IEvent binddata=new IEventImpl(obj,methodName);
		this.bindData = binddata;
	}

  	public int getAllRecordCount() {
		return allRecordCount;
	}

	public void setAllRecordCount(int allRecordCount) {
		this.allRecordCount = allRecordCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public BinList getInList() {
		return inList;
	}

	public void setInList(BinList inList) {
		this.inList = inList;
	}

	public BinMap getField() {
		return field;
	}

	public void setField(BinMap field) {
		this.field = field;
	}

	public String getKeyFied() {
		return keyFied;
	}

	public void setKeyFied(String keyFied) {
		this.keyFied = keyFied;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getMethodText() {
		return MethodText;
	}

	public void setMethodText(String methodText) {
		MethodText = methodText;
	}

	public boolean isMody() {
		return mody;
	}

	public void setMody(boolean mody) {
		this.mody = mody;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}



	private void getFieldByList(BinList list){
		if(field!=null)field.clear();
		for(int i=0;i<list.size();i++){
			field.put(list.getValue(i, "field"), "");
		}
		
	}
	
	public String Display(){
		StringBuffer rsSb=new StringBuffer();
		getFieldByList(inList);
		if(url.indexOf('?')>0){
			if(url.indexOf("&page=")>0){
				url=url.substring(0,url.indexOf("&page"));
				url+="&";
			}else if(url.indexOf("?page=")>0){
				url=url.substring(0,url.indexOf("?page"));
				url+="?";
			}
			else{
				url+="&";
			}
		}else{
			url+="?";
		}
		try {
			Object[] para={this.getField(),this.getCurrentPage(),this.getPageSize()};
	        this.bindData.setParams(para);
	        BinMap temp=new BinMap();
	        temp=(BinMap)this.bindData.invoke();
			setAllRecordCount(Integer.parseInt(temp.getValue(0).toString()));
			outList=(BinList)temp.getValue(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int allPageNum=(int)(this.allRecordCount/this.getPageSize());
		if((this.allRecordCount%this.pageSize)!=0){
			allPageNum=allPageNum+1;
		}
		int step=4;
		int leftnum=1;
		int rightnum=1;
		if(this.getCurrentPage()-step<1){
			leftnum=1;
		}else{
			leftnum=this.getCurrentPage()-step;
		}
		if(this.getCurrentPage()+step>allPageNum){
			rightnum=allPageNum;
		}else{
			rightnum=this.getCurrentPage()+step;
		}
		
		StringBuffer pagesb=new StringBuffer();
		for(int i=leftnum;i<=rightnum;i++){
			if(i==this.getCurrentPage()){
				pagesb.append(" [ ");
				pagesb.append(getCurrentPage());
				pagesb.append(" ]");
			}else{
				pagesb.append(" [<a href=" + url + "page=");
				pagesb.append(i);
				pagesb.append("><font style='color:red'> ");
				pagesb.append(i);
				pagesb.append("</font> </a>]");
			}
			
		}
		if(this.getCurrentPage()>1){
			pagesb.insert(0, " <a href=" + url + "page="+(this.getCurrentPage()-1)+"><font style='color:red'>上一页</font></a> ");

		}
		
		if(this.getCurrentPage()<allPageNum){
			pagesb.append(" <a href="+url+"page=");
			pagesb.append(this.getCurrentPage() + 1);
			pagesb.append(">下一页</a>");
		}
			
        rsSb.append(" <script type=\"text/javascript\">");

        rsSb.append("   var posturl=\""+this.methodUrl+"\";");
        rsSb.append("  var para=\"\";");
        rsSb.append("  function AddBtn(id){");
        rsSb.append("  	    para=\"method=InsertInfo&id=\"+id; ");
        rsSb.append("  	    Callback(posturl,para);    	");
        rsSb.append("  }");
        rsSb.append("  ");
        rsSb.append("  ");
        rsSb.append(" function DelBtn(id){");
        rsSb.append("        para=\"method=DeleteInfo&id=\"+id;");
        rsSb.append("        Callback(posturl,para);");
        rsSb.append("  }");
        rsSb.append("  ");
        rsSb.append(" function UpdateBtn(id){");
        rsSb.append("        para=\"method=UpdateInfo&id=\"+id;");
        rsSb.append("        Callback(posturl,para);");
        rsSb.append("  }");
        rsSb.append(" function OtherBtn(id){");
        rsSb.append("        para=\"method=OtherInfo&id=\"+id;");
        rsSb.append("        Callback(posturl,para);");
        rsSb.append(" }");
        rsSb.append("  ");
        rsSb.append(" function DelMoreBtn(){");
        rsSb.append(" 	    var selected=false;");
        rsSb.append("      	var selectitem='';");
        rsSb.append("      	for(i=0;i<document."+form+"."+keyFied+".length;i++){");
        rsSb.append("      		if(document."+form+"."+keyFied+"[i].checked)");
        rsSb.append("      		{");
        rsSb.append("      			if(!selected)selected=true;");
        rsSb.append("      			selectitem+=\"'\"+document."+form+"." + keyFied + "[i].value+\"'\"+',';");
        rsSb.append("      		}");
        rsSb.append("      	}");
        rsSb.append("      ");
        rsSb.append("		if(!selected){");
        rsSb.append("			alert('请选择要删除的内容！');");
        rsSb.append("			return false;");
        rsSb.append("		}else{");
        rsSb.append("		    selectitem=selectitem.substring(0,selectitem.length-1);");
        rsSb.append("		    para=\"method=DeleteMoreInfo&id=\"+selectitem;");
        rsSb.append("		    Callback(posturl,para);");
        rsSb.append("		}");
        rsSb.append(" }");
        rsSb.append(" function OtherMoreBtn(){");
        rsSb.append(" 	    var selected=false;");
        rsSb.append("      	var selectitem='';");
        rsSb.append("      	for(i=0;i<document."+form+"."+keyFied+".length;i++){");
        rsSb.append("      		if(document."+form+"."+keyFied+"[i].checked)");
        rsSb.append("      		{");
        rsSb.append("      			if(!selected)selected=true;");
        rsSb.append("      			selectitem+=\"'\"+document."+form+"." + keyFied + "[i].value+\"'\"+',';");
        rsSb.append("      		}");
        rsSb.append("      	}");
        rsSb.append("      ");
        rsSb.append("		if(!selected){");
        rsSb.append("			alert('请选择要操作的内容！');");
        rsSb.append("			return false;");
        rsSb.append("		}else{");
        rsSb.append("		    selectitem=selectitem.substring(0,selectitem.length-1);");
        rsSb.append("		    para=\"method=OtherMoreInfo&id=\"+selectitem;");
        rsSb.append("		    Callback(posturl,para);");
        rsSb.append("		}");
        rsSb.append(" }");
        rsSb.append(" function SelectAllBtn(){");
        rsSb.append("      	for(i=0;i<document."+this.form+"." + this.keyFied + ".length;i++){");
        rsSb.append("      		document."+this.form+"." + this.keyFied + "[i].checked=true");
        rsSb.append("      	}");
        rsSb.append(" }");
        rsSb.append(" function Callback(url,para)");
        rsSb.append("   {");
        rsSb.append("   	   var rs=\"\";");
        rsSb.append("   	   rs=postRemoteData(url,para);");       
        rsSb.append("   	   	var flage = -1;");
        rsSb.append("		flage = rs.search('<artscript>');");
        rsSb.append("		if (flage == -1) {");
        rsSb.append("			alert(rs);");
        rsSb.append("	        document.location.reload();");
        rsSb.append("		}");
        rsSb.append("		else {");
        rsSb.append("			rs = rs.replace('<artscript>', '');");
        rsSb.append("			rs = rs.replace('<\\/artscript>', '');");
        rsSb.append("			eval(rs);");
        rsSb.append("		}");
        rsSb.append("   }");
        rsSb.append("");
        rsSb.append("	var contentCache = new Object();");
        rsSb.append("	var tempref;");
        rsSb.append("	var timeOut = 250;");
        rsSb.append("	var waitInterval;");
        rsSb.append("	function getXmlhttp(){");
        rsSb.append("	   var http_request;");
        rsSb.append("	   if (window.XMLHttpRequest) {");
        rsSb.append("	       http_request = new XMLHttpRequest();");
        rsSb.append("	     if (http_request.overrideMimeType) {");
        rsSb.append("	       http_request.overrideMimeType(\"text/xml\");");
        rsSb.append("	     }");
        rsSb.append("	   }else {");
        rsSb.append("	   if (window.ActiveXObject){");
        rsSb.append("	    try {");
        rsSb.append("	       http_request = new ActiveXObject(\"Msxml2.XMLHTTP\");");
        rsSb.append("	    }catch(e) {");
        rsSb.append("	    try {");
        rsSb.append("	      http_request = new ActiveXObject(\"Microsoft.XMLHTTP\");");
        rsSb.append("	    }catch(e){}");
        rsSb.append("	   }");
        rsSb.append("	  }");
        rsSb.append("	}");
        rsSb.append("	if (!http_request) {");
        rsSb.append("	window.alert(\"can't create XMLHttpRequest object.\");");
        rsSb.append("	return null;");
        rsSb.append("	}");
        rsSb.append("	return http_request;");
        rsSb.append("	}");
        rsSb.append("");
        rsSb.append("	function GetRemoteDataf(url,para,state)");
        rsSb.append("	{");
        rsSb.append("	    var xmlhttp = getXmlhttp();");
        rsSb.append("	    var aurl;");
        rsSb.append("	    if(state==1){");
        rsSb.append("	      aurl=url+'?s='+(new Date()).toTimeString().substr(0,8)+'&'+para;");
        rsSb.append("	    }else if(state==2){");
        rsSb.append("	      aurl=url+\"?\"+para;");
        rsSb.append("	    }else{");
        rsSb.append("	      aurl=url;");
        rsSb.append("	    }");
        rsSb.append("	    try");
        rsSb.append("	    {  ");
        rsSb.append("	         xmlhttp.open('GET', aurl, false);");
        rsSb.append("	         xmlhttp.send(null);");
        rsSb.append("	         if ( xmlhttp.status == 200 )");
        rsSb.append("	         {");
        rsSb.append("	             return xmlhttp.responseText;");
        rsSb.append("	         }");
        rsSb.append("	         throw ''; ");
        rsSb.append("	    }");
        rsSb.append("	    catch(e)");
        rsSb.append("	    {");
        rsSb.append("	         return '';");
        rsSb.append("	    } ");
        rsSb.append("	}");
        rsSb.append("");
        rsSb.append("	function postRemoteData(url,para)");
        rsSb.append("	{");
        rsSb.append("	    var xmlhttp = getXmlhttp();");
        rsSb.append("	    try");
        rsSb.append("	    {  ");
        rsSb.append("	         xmlhttp.open('POST', url, false);");
        rsSb.append("	         xmlhttp.setRequestHeader(\"Content-Length\",para.length);");
        rsSb.append("	         xmlhttp.setRequestHeader(\"CONTENT-TYPE\",\"application/x-www-form-urlencoded\");");
        rsSb.append("	         xmlhttp.send(para);");
        rsSb.append("	         if ( xmlhttp.status == 200 )");
        rsSb.append("	         {");
        rsSb.append("	             return xmlhttp.responseText;");
        rsSb.append("	         }");
        rsSb.append("	         throw ''; ");
        rsSb.append("	    }");
        rsSb.append("	    catch(e)");
        rsSb.append("	    {");
        rsSb.append("	         return '';");
        rsSb.append("	    } ");
        rsSb.append("	}");
        rsSb.append("</script>");

        
        int colsnum =2;
        rsSb.append("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" class=\"mytbl\">");
        rsSb.append("<tr>");
        if (this.mody || this.del || this.add || this.other ) rsSb.append("  <td style=\" font-weight:700;\">选择</td> ");
        for (int i = 0; i < this.inList.size(); i++)
        {           
            if ((Boolean)this.inList.getValue(i,"display"))
            {
            	rsSb.append("  <td style=\" font-weight:700;\">" +this.inList.getValue(i, "fieldcname") + "</td> ");
                colsnum++;
            }
        }
        if (this.mody || this.del || this.add || this.other) rsSb.append("  <td style=\" font-weight:700;\">操作</td> ");
        rsSb.append("</tr>");
        for (int j = 0; j < outList.size(); j++)
        {
            rsSb.append("<tr>");
            if (this.mody || this.del || this.add || this.other||this.allother) rsSb.append("  <td><input value=\"" + this.outList.getValue(j, keyFied) + "\" name=\"" + keyFied + "\" type=\"checkbox\" /></td>");
            for (int i = 0; i < this.field.size(); i++)
            {
                if ((Boolean)this.inList.getValue(i, "display")) rsSb.append("  <td>" + outList.getValue(j, this.inList.getValue(i, "field")) + "</td>");
            }
            String methodstr = "";
            //methodstr += "<input id=\"Button1\" type=\"button\" value=\"新增记录\" onClick=\"singleAdd" + this.getId() + "Btn('" + singleD.Rows[j][keyfied].ToString() + "');\" class=\"close\" />&nbsp;&nbsp;&nbsp;";
            if (this.add) methodstr += "<input id=\"Button1\" type=\"button\" value=\""+this.addText+"\" onClick=\"AddBtn('" + outList.getValue(j, this.keyFied) + "');\" class=\"close\" />&nbsp;&nbsp;&nbsp;";
            if (this.mody) methodstr += "<input id=\"Button1\" type=\"button\" value=\""+this.modyText+"\" onClick=\"UpdateBtn('" + outList.getValue(j, this.keyFied) + "');\" class=\"close\" />&nbsp;&nbsp;&nbsp;";
            if (this.del) methodstr += "<input id=\"Button1\" type=\"button\" value=\""+this.delText+"\" onClick=\"DelBtn('" + outList.getValue(j, this.keyFied)  + "');\" class=\"close\" />&nbsp;&nbsp;&nbsp;";
            if (this.other) methodstr += "<input id=\"Button1\" type=\"button\" value=\"" + this.MethodText+ "\" onClick=\"OtherBtn('" + outList.getValue(j, this.keyFied) + "');\" class=\"close\" />&nbsp;&nbsp;&nbsp;";
            if(methodstr!="")rsSb.append("  <td>"+methodstr+"</td>");
            rsSb.append("</tr>");
        }
        rsSb.append("<tr>");
        //if (colsnum > 0) colsnum = colsnum - 1; 
        //rsSb.append("  <td colspan=\"" + colsnum.ToString() + "\">" + "<input id=\"Button1\" type=\"button\" style=\"width:120px\" value=\"新增记录\" onClick=\"singleAdd" + this.getId() + "Btn();\" class=\"close\" />&nbsp;&nbsp;&nbsp;<input id=\"Button1\" type=\"button\" style=\"width:60px\" value=\"全 选\" onClick=\"Select" + this.getId() + "AllBtn();\" class=\"close\" /> <input id=\"Button1\" type=\"button\" style=\"width:60px\" value=\"删除所选\" onClick=\"Del" + this.getId() + "AllBtn();\" class=\"close\" />&nbsp;&nbsp;&nbsp;" + string.Format("共{0}页，共{1}记录", allPageNum, _TotalCountRecord) + " " + outputpage.ToString() + "</td>");
        if ( this.del ||this.allother)
        {
            rsSb.append("  <td colspan=\"" + colsnum + "\">" + "<input id=\"Button1\" type=\"button\" style=\"width:60px\" value=\"全 选\" onClick=\"SelectAllBtn();\" class=\"close\" /> ");
            if(this.del)rsSb.append("<input id=\"Button1\" type=\"button\" style=\"width:60px\" value=\"删除所选\" onClick=\"DelMoreBtn();\" class=\"close\" />");
            if(this.allother)rsSb.append("<input id=\"Button1\" type=\"button\" style=\"width:100px\" value=\""+this.AllMethodText+"\" onClick=\"OtherMoreBtn();\" class=\"close\" />");
            rsSb.append("  &nbsp;&nbsp;&nbsp;共"+allPageNum+"页，共"+this.allRecordCount+"记录 " + pagesb.toString() + "</td>");

        }else
        {
            rsSb.append("<td colspan=\"" + colsnum+ "\">共"+allPageNum+"页，共"+this.allRecordCount+"记录 " + pagesb.toString() + "</td>");

        }

        //rsSb.append("  <td></td>");
        rsSb.append("</tr> ");
        rsSb.append("</table>");
		
		return rsSb.toString();
		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		ISqlBase database=new SqlBase("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/medicare_gdq?characterEncoding=GBK","root","sa");		
//		IEvent executeSQL=new IEventImpl(database,"ExecuteSQL"); 
//		IEvent querySQL=new IEventImpl(database,"QuerySQL"); 
//		ISqlData isd=new SqlDataEvent(executeSQL,querySQL); 
//		//isd.RunSelectgetListMap(tablename, para, currentpage, pagesize)
//		
//    	LHBList inpara=new LHBList();
//    	inpara.put(0,"field","BatchNumber");
//    	inpara.put(0,"fieldcname","流水批次号");
//    	inpara.put(0,"display",true);
//    	
//    	IEvent bindData=new IEventImpl();
//    	bindData.setObject(isd);
//    	bindData.setMethodName("RunSelectgetListMap");
//    	
//    	WebList list=new WebList();    	
//    	list.setBindData(bindData);
//    	list.setCurrentPage(1);
//    	list.setKeyFied("BatchNumber");
//    	list.setPageSize(5);
//    	list.setInList(inpara);
//    	list.setUrl("reconciliation.jsp");
//    	
//    	System.out.println(list.Display());
    	
    	
//    	LHBMap lm=new LHBMap();
//		lm.put("BatchNumber", "");
//		LHBMap rslm=new LHBMap();
//		rslm=isd.RunSelectgetListMap("reconcliledata", lm, 2, 4);
//		System.out.println(rslm.getValue(0));
	}

}
