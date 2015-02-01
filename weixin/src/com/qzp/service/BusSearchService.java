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

public class BusSearchService {

	private static Logger log = LoggerFactory.getLogger(BaiduMusicService.class); 
    /** 
     * 根据名称和作者搜索车辆
     * @author qzp
     * @Date 2014-7-22 
     * @param city 城市名 
     * @param start_addr 出发点 
     * @param end_addr 终点站 
     * @return lineName 具体路线
     */
	
	
	public static String busSearch(String city,String start_addr, String end_addr) {  
        // 公交车查询的网站 
        String requestUrl = "http://openapi.aibang.com/bus/transfer?app_key=f41c8afccc586de03a99c86097e98ccb&city={城市}&start_addr={起点}&end_addr={终点}";  
        // 对音乐名称、作者进URL编码  
        requestUrl = requestUrl.replace("{城市}", urlEncodeUTF8(city));
        requestUrl = requestUrl.replace("{起点}", urlEncodeUTF8(start_addr));  
        requestUrl = requestUrl.replace("{终点}", urlEncodeUTF8(end_addr));  
        
  
        // 查询并获取返回结果  
        InputStream inputStream = httpRequest(requestUrl);  
        // 从返回结果中解析出BusInfo 
        String lineName = parseBusInfo(inputStream);   
        return lineName;  
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
     * 解析地址参数 
     *  
     * @param inputStream 地址信息的输入流
     * @return BusInfo 
     */  
    
    
	private static String parseBusInfo(InputStream inputStream) {  
        String text=null;
        try{
        // 使用dom4j解析xml字符串  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        Element element1=root.element("buses"); 
        Element element2=element1.element("bus");
        Element element3=element2.element("segments");
        Element element4=element3.element("segment");
        Element element5=element4.element("line_name");
        text=element5.getText();
        }catch (Exception e) {  
            e.printStackTrace();
            log.info("您输入的地址不存在");
        }         
		return text;  
    }  
  
    // 测试方法  
    public static void main(String[] args) {  
    	//log.info(busSearch("南通","南大街","南通大学"));        
    }  
}
