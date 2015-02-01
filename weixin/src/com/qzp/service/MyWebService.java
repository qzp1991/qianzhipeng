package com.qzp.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyWebService {

	  private static Logger log = LoggerFactory.getLogger(MyWebService.class); 
	    /** 
	     * 根据名称和作者搜索音乐 
	     * @author qzp
	     * @Date 2014-7-14 
	     * @param musicTitle 音乐名称 
	     * @param musicAuthor 音乐作者 
	     * @return Music 
	     */  
	    public static String searchWxyj(String yourSalary) {  
	    	
	    	//默认的返回值
	    	String y=null;	    	
	        // 百度音乐搜索地址  
	        String requestUrl = "http://book.52itstyle.com/services/MyService/getInsurance?salary={工资}";  
	        // 对音乐名称、作者进URL编码  
	        requestUrl = requestUrl.replace("{工资}", urlEncodeUTF8(yourSalary));  
	        
	        // 查询并获取返回结果  
	        InputStream inputStream = httpRequest(requestUrl);  
	        // 从返回结果中解析出Music  
	         y = parseWxyj(inputStream);  
	  
	      
	         
	        return y;  
	    }  
	  
	    /** 
	     * UTF-8编码 
	     *  
	     * @param source 
	     * @return 
	     */  
	    private static String urlEncodeUTF8(String source) {  
	        String result = source;  
	        try {  
	            result = java.net.URLEncoder.encode(source, "UTF-8");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }  
	  
	    /** 
	     * 发送http请求取得返回的输入流 
	     *  
	     * @param requestUrl 请求地址 
	     * @return InputStream 
	     */  
	    private static InputStream httpRequest(String requestUrl) {  
	        InputStream inputStream = null;  
	        try {  
	            URL url = new URL(requestUrl);  
	            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setRequestMethod("GET");  
	            httpUrlConn.connect();  
	            // 获得返回的输入流  
	            inputStream = httpUrlConn.getInputStream();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return inputStream;  
	    }  
	  
	    /** 
	     * 解析音乐参数 
	     *  
	     * @param inputStream 百度音乐搜索API返回的输入流 
	     * @return Music 
	     */  
	     
	    private static String  parseWxyj(InputStream inputStream) {  
	        String wxyj=null; 
	        try {  
	            // 使用dom4j解析xml字符串  
	            SAXReader reader = new SAXReader();  
	            Document document = reader.read(inputStream);  
	            // 得到xml根元素  
	            Element root = document.getRootElement();  
	            // count表示搜索到的歌曲数  
	            wxyj = root.element("return").getText();  
	            
	            
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return wxyj;  
	    }  
	  
	    // 测试方法  
	    public static void main(String[] args) {  
	        String test = searchWxyj("8900"); 
	        log.info(test);
	        
	    }  
}
