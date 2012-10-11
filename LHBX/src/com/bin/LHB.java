package com.bin;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.bin.io.IFileImpl;
import com.bin.method.parse.Base64;
import com.bin.method.parse.CnToSpell;
import com.bin.object.BinMap;
import com.bin.utils.Utils;

public class LHB {
	public String GetEnoughLenStr(int num,String basestr){
		String rs=basestr;
		int baseint=rs.length();
		if(baseint<num){
			for(int i=baseint;i<num;i++)
			{
				rs="0"+rs;
			}
		}
		return rs;
	}
	public String putOffEnoughLenStr(int num,String basestr){
		String rs=basestr;
		int baseint=rs.length();
		if(baseint<num){
			baseint=num-baseint;
			rs=basestr.substring(baseint+1);
		}
		return rs;
	}
	public String UrlEncode(String code, String charset)
	  {
	    String rs = "";
	    try {
	      rs = URLEncoder.encode(code, charset);
	    }
	    catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return rs;
	  }

	  public String UrlDecode(String code, String charset) {
	    String rs = "";
	    try {
	      rs = URLDecoder.decode(code, charset);
	    }
	    catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return rs;
	  }
	  public String getContentCode(String strUrl, String charset)
	  {
	    URL url;
	    try
	    {
	      url = new URL(strUrl);	    
	      URLConnection urlc = url.openConnection();
	      urlc.setConnectTimeout(20000);
	      urlc.setReadTimeout(20000);
	      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), charset));
	      String s = "";
	      StringBuffer sb = new StringBuffer("");
	      while ((s = br.readLine()) != null)
	      {
	        sb.append(s + "\r\n");
	      }
	      br.close();
	      //System.out.println("=="+Method.UrlDecode(strUrl, charset));
	      return sb.toString();
	    }
	    catch (Exception e) {
	      e.printStackTrace(); }
	    return "error";
	  }
	  
	  public String getMarkString(String Str, String LeftMark, String RightMark) {
		    String tmpreturn = Str;
		    if (tmpreturn.indexOf(LeftMark, 0) != -1)
		    {
		      int LeftMarkPoint = tmpreturn.indexOf(LeftMark, 0) + LeftMark.length();
		      int RightMarkPoint = tmpreturn.indexOf(RightMark, LeftMarkPoint);
		      if (RightMarkPoint != -1)
		      {
		        int ValueLength = RightMarkPoint;
		        tmpreturn = tmpreturn.substring(LeftMarkPoint, ValueLength);
		      }
		    }
		    if (tmpreturn == Str)
		    {
		      return "";
		    }

		    return tmpreturn;
		  }

		  public ArrayList getMarkStringList(String Str, String LeftMark, String RightMark)
		  {
		    ArrayList al = new ArrayList();
		    String tempstr = new String(Str);
		    String tempv = "";
		    while (!(getMarkString(tempstr, LeftMark, RightMark).equals(""))) {
		      tempv = getMarkString(tempstr, LeftMark, RightMark);
		      if (tempv.equals("")) break;
		      al.add(new String(tempv));
		      tempstr = tempstr.replace(LeftMark + tempv + RightMark, "");
		    }
		    return al;
		  }
		  public String Base64Encode(String para) {
			    String rs = "";
			    try {
			      rs = Base64.encode(para);
			    }
			    catch (Exception e) {
			      e.printStackTrace();
			    }
			    return rs;
			  }

	public String Base64Decode(String para) {
			    String rs = "";
			    try {
			      rs = Base64.decode(para);
			    }
			    catch (Exception e) {
			      e.printStackTrace();
			    }
			    return rs; 
			    }
	   	  
	   public String systemdate(){
		    String rs="";
			Calendar cal = new GregorianCalendar();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour=cal.get(Calendar.HOUR_OF_DAY);
			int m=cal.get(Calendar.MINUTE);
			int s=cal.get(Calendar.SECOND);
			rs=year+"-"+month+"-"+day+" "+hour+":"+m+":"+s;
			return rs;
	   }

	   /**
	    * 将汉字转化为全拼或首拼
	    * @param instr 中文字符串
	    * @param flag true为首拼  false为全拼
	    * @return
	    */
	   public String getSpell(String instr, boolean flag){
		   String rsstr="";
		   rsstr=CnToSpell.getSpell(instr, flag);
		   return rsstr;
	   }
	   public String getBeforeAfterDate(String date,int n){
		    
			 Calendar cal=Calendar.getInstance();
			 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			 if(date.equals(""))date=df.format(cal.getTime());
			 try {
				cal.setTime(df.parse(date));				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+n);
			date=df.format(cal.getTime());
			return date;		
		}
	   //时间差
	   public Long getDateSub(String first,String second){
		   Long rs=null;
		   Date  df=new Date();
		   Date  dc=new Date();
		   SimpleDateFormat dft=new SimpleDateFormat("yyyy-MM-dd");
		   try {
			   df=dft.parse(first);
			   dc=dft.parse(second);
		   } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   rs=(df.getTime()-dc.getTime())/(1000 * 60 * 60 * 24);	   
		   return rs;		   
	   }
	   /**
	    * 获取当前时间
	    * @param type yyyy MM dd ww hh mm ss
	    * @return
	    */
	   public String getNowStr(String type){
		   	String rsStr=type;
		      Calendar ca = Calendar.getInstance(); 
		      String year = Utils.GetEnoughLenStr(4, ""+ca.get(Calendar.YEAR));//获取年份 
		      String month=Utils.GetEnoughLenStr(2, ""+(ca.get(Calendar.MONTH)+1));//获取月份  
		      String day=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.DATE));//获取日 
		      String minute=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.MINUTE));//分  
		      String hour=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.HOUR_OF_DAY));//小时  
		      String second=Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.SECOND));//秒 
		      String WeekOfYear =Utils.GetEnoughLenStr(2, ""+ca.get(Calendar.DAY_OF_WEEK));  
		      rsStr=rsStr.replace("yyyy", year+"");
		      rsStr=rsStr.replace("MM", month+"");
		      rsStr=rsStr.replace("dd", day+"");
		      rsStr=rsStr.replace("ww", WeekOfYear+"");
		      rsStr=rsStr.replace("hh", hour+"");
		      rsStr=rsStr.replace("mm", minute+"");
		      rsStr=rsStr.replace("ss", second+"");		      
		      return rsStr;
	   }
	   
	   /**
	    * 
	    * @param first
	    * @param second
	    * @param type yyyy-MM-dd
	    * @param time day,hour,min,s
	    * @return
	    */
	   public Long getDateSub(String first,String second,String type,String time){
		   Long rs=null;
		   Date  df=new Date();
		   Date  dc=new Date();
		   SimpleDateFormat dft=new SimpleDateFormat("yyyy-MM-dd");
		   try {
			   df=dft.parse(first);
			   dc=dft.parse(second);
		   } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		   
		    if(time.equals("day"))rs=(df.getTime()-dc.getTime())/(1000 * 60 * 60 * 24);	
		    if(time.equals("hour"))rs=(df.getTime()-dc.getTime())/(1000 * 60 * 60 );	 
		    if(time.equals("min"))rs=(df.getTime()-dc.getTime())/(1000 * 60 );	 
		    if(time.equals("s"))rs=(df.getTime()-dc.getTime())/1000;
		   return rs;		   
	   }
	   /**
	    * 时间格式转换
	    * @param strDate 
	    * @param btype 原来的格式
	    * @param etype 出来的格式
	    * @return
	    */
	   public String convertDateMode(String strDate,String btype,String etype){
		   String rs="";
		   Date date = new Date();
           
           SimpleDateFormat format = new SimpleDateFormat();
           format.applyPattern(btype);
           
           try {
			date=format.parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       format.applyPattern(etype);
	       rs = format.format(date);
		   return rs;
	   }
	   
	   /**
	    * 时间格式转换
	    * @param strDate 
	    * @param btype 原来的格式
	    * @param etype 出来的格式
	    *  Localized （Locale.US);//   默认Locale是中国，所以上面的英文是转换不了的
	    * @return
	    */
	   public String convertDateMode(String strDate,String btype,String etype,Locale local ){
		   String rs="";
		   Date date = new Date();
           
           SimpleDateFormat format = new SimpleDateFormat(btype,local);
           try {
			date=format.parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       format.applyPattern(etype);
	       rs = format.format(date);
		   return rs;
	   }
	
	   
	   /**
	    * 时间格式转换
	    * @param strDate 
	    * @param etype 出来的格式
	    * @return
	    */
	   public String convertDateMode(String strDate,String etype){
		   String rs="";
		   Date date = new Date();
           
           SimpleDateFormat format = new SimpleDateFormat();

	       format.applyPattern(etype);
	       rs = format.format(date);
		   return rs;
	   }
	   public BinMap getMapByRequest(HttpServletRequest request){
		    String charset=request.getParameter("charset");
		    charset=charset==null?"utf-8":charset;
		    BinMap rslm=new BinMap();
		    Enumeration enumer=request.getParameterNames();
			for (Enumeration e = enumer ; e.hasMoreElements() ;) {
		         String thisName=e.nextElement().toString();
		         String thisValue;
				try {
					thisValue = new String((request.getParameter(thisName)).getBytes("ISO-8859-1"), charset);
					rslm.put(thisName, thisValue);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}finally{}	         
		        
			}
			return rslm; 
	   }	   
	   
	  public String getRootPath(){
		    String rsStr="";		   
		    String realPath =this.getClassPath();
		    realPath = realPath.substring(0, realPath.indexOf("/WEB-INF"));
		    realPath=realPath.replace("file:/", "");
		    rsStr=realPath;
		    rsStr=Utils.UrlDecode(rsStr, "gbk");
		   return rsStr;
	  }
	  public String getClassPath(){
		  return this.getClass().getResource("").getPath().toString();
	  }
	  public String realFile(String FileName){
		  return (new IFileImpl()).readFile(FileName);
	  }
	  public String realFile(String FileName,Boolean isNewLine){
		  return (new IFileImpl()).readFile(FileName,isNewLine);
	  }
	  /**
	   * 
	   * @param FileName
	   * @param appandstr
	   * @param falg
	   * @return 返回null为失败，否则返回添加的字符串
	   */
	  public String writeFile(String FileName,String appandstr,boolean falg){
		  return (new IFileImpl()).writeFile(FileName, appandstr, falg);
	  }
	  public String getRandomNumber(){
		  String rs="";
		  Random r=new Random();
		  rs=r.nextInt()+"";
		  return rs;
	  }
	  public String getRandomNumber(int num){
		  String rs="";
		  Random r=new Random();
		  rs=r.nextInt(num)+"";
		  return rs;
	  }
	  
	  public String getGuid(){
		  String rs="";
		  UUID uuid = UUID.randomUUID();
		  rs=uuid.toString();
		  return rs;  
	  }
	  public BinMap getConfigMap(){
		  BinMap rslm=new BinMap();
		  String rsStr=Utils.realFile(Utils.getRootPath()+"/WEB-INF/config.jsp",true);
		 	String regex="\\s+String\\s+(\\w+)=\"(.*)\";";
		    Pattern p=Pattern.compile(regex,Pattern.UNICODE_CASE);
		    if(rsStr!=null){
				Matcher m=p.matcher(rsStr);
				while(m.find())
				{
					String content=m.group(2).toString();				
					rslm.put(m.group(1).toString(),content);
				}
		    }
		  return rslm;
	  }
	  public String submitMessage(String urlAddr,String method){
		  String rsStr="";	
		  method=method.toUpperCase();
		  try {
			URL url = new URL(urlAddr.substring(0,urlAddr.indexOf("?")));   
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);    
			conn.setRequestMethod(method);    
			conn.setUseCaches(false);    
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");    
			conn.setRequestProperty("Content-Length", String.valueOf(urlAddr.substring(urlAddr.indexOf("?")+1).length()));    
			conn.setDoInput(true);    
			conn.connect();    
  
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");    
			out.write(urlAddr.substring(urlAddr.indexOf("?")+1));    
			out.flush();    
			out.close();    
			
		    InputStream is=conn.getInputStream();
		    DataInputStream dis=new DataInputStream(is);
		    byte d[]=new byte[dis.available()];
		    dis.read(d);
		    rsStr=new String(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		  return rsStr;
	  }
	  public String submitMessage(String urlnopara,BinMap params,String method){
		  String rsStr="";	
		  method=method.toUpperCase();
		  StringBuffer psb=new StringBuffer();
		  for(int i=0;i<params.size();i++){
			  psb.append(params.getKey(i));
			  psb.append("=");
			  psb.append(params.getValue(i));
			  psb.append("&");
		  }
		  psb.append("");
		  try {
			URL url = new URL(urlnopara);   
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);    
			conn.setRequestMethod(method);    
			conn.setUseCaches(false);    
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");    
			conn.setRequestProperty("Content-Length", String.valueOf(psb.length()));    
			conn.setDoInput(true);    
			conn.connect();    
  
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");    
			out.write(psb.toString());    
			out.flush();    
			out.close();    
			
		    InputStream is=conn.getInputStream();
		    DataInputStream dis=new DataInputStream(is);
		    byte d[]=new byte[dis.available()];
		    dis.read(d);
		    rsStr=new String(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		  return rsStr;
	  }
	  
	  public void Base64ToPicFile(String InputStr, String FilePath, String Format) throws IOException {
		    InputStream ips = new ByteArrayInputStream(InputStr.getBytes());
		    ByteArrayOutputStream ops = new ByteArrayOutputStream();
		    ByteArrayOutputStream ops1 = new ByteArrayOutputStream();
		    File zoomFile = new File(FilePath);
		    Base64.decode(ips, ops);
		    BufferedImage Bi = ImageIO.read(new ByteArrayInputStream(ops.toByteArray()));
		    ImageIO.write(Bi, Format, zoomFile); 
	  }

	 public String PicFileToBase64(String FilePath, String Format) throws IOException {
		    String rs = "";
		    BufferedImage img = ImageIO.read(new File(FilePath));
		    ByteArrayOutputStream ops = new ByteArrayOutputStream();
		    ImageIO.write(img, Format, ops);
		    InputStream ips = new ByteArrayInputStream(ops.toByteArray());
		    ops = new ByteArrayOutputStream();
		    Base64.encode(ips, ops);
		    return ops.toString();
		  }
	 
	 public void getFixedPic(InputStream infile, OutputStream outfile, int height, int width, String Format) throws IOException
	  {
	    double Ratio = 0D;
	    BufferedImage Bi = ImageIO.read(infile);
	    if ((Bi.getHeight() > height) || (Bi.getWidth() > width)) {
	      if (Bi.getHeight() > Bi.getWidth()) {
	        Ratio = new Integer(height).doubleValue() / Bi.getHeight();
	      }
	      else
	        Ratio = new Integer(width).doubleValue() / Bi.getWidth();

	      Image Itemp = Bi.getScaledInstance(width, height, 4);
	      AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Ratio, Ratio), null);
	      Itemp = op.filter(Bi, null);
	      try {
	        ImageIO.write((BufferedImage)Itemp, Format, outfile);
	      } catch (Exception ex) {
	        System.out.println("######## here error : " + ex);
	      }
	    } else {
	      try {
	        ImageIO.write(Bi, Format, outfile);
	      } catch (Exception ex) {
	        System.out.println("######## here error : " + ex);
	      }
	    }
	  }

	  public String getFixedPic(String InputStr, int height, int width, String Format) throws IOException {
	    String rs = "";
	    InputStream ips = new ByteArrayInputStream(InputStr.getBytes());
	    ByteArrayOutputStream ops = new ByteArrayOutputStream();
	    ByteArrayOutputStream ops1 = new ByteArrayOutputStream();
	    Base64.decode(ips, ops);
	    getFixedPic(new ByteArrayInputStream(ops.toByteArray()), ops1, height, width, Format);
	    InputStream is = new ByteArrayInputStream(ops1.toByteArray());
	    ops = new ByteArrayOutputStream();
	    Base64.encode(is, ops);
	    rs = ops.toString();
	    return rs;
	  }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
