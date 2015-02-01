package com.qzp.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.qzp.model.station.Results;
import com.qzp.model.station.Station;

public class StationSearch {

	private static Logger log = LoggerFactory.getLogger(BaiduMusicService.class); 
    /** 
     * 根据名称和作者搜索车辆
     * @author qzp
     * @Date 2014-7-22 
     * @param city1 出发城市名
     * @param city2 到达城市名
     * @param start_addr 出发点 
     * @param end_addr 终点站 
     * @return lineName 具体路线
     */
	/** 
     * 发起http请求获取返回结果 
     *  
     * @param requestUrl 请求地址 
     * @return 
     */  
    public static String httpRequest(String requestUrl) {  
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
  
            httpUrlConn.setDoOutput(false);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
  
            // 将返回的输入流转换成字符串    很重要将字节流转化为字符流
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine())!= null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
  
        } catch (Exception e) {  
        }  
        return buffer.toString();  
    }  
  
    /** 
     * utf编码 
     *  
     * @param cityNo 
     * @return 
     */  
    public static String urlEncodeUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLEncoder.encode(source,"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    /** 
     * 菜谱查询
     *  
     * @param source 
     * @return 
     */  
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String ResultSearch(String startCity,String endCity,String orginStation,String destination) {  
        //默认线路为空
		List lineNamelist=new ArrayList();
    	String lineName=null;
        // 组装查询地址  
        String requestUrl = "http://api.map.baidu.com/telematics/v3/navigation?origin={出发城市}&destination={到达城市}&origin_region={出发地}&destination_region={目的地}&output=json&ak={AppKey}";  
        // 对参数q的值进行urlEncode utf-8编码  
        requestUrl = requestUrl.replace("{出发城市}", urlEncodeUTF8(startCity)); 
        requestUrl = requestUrl.replace("{到达城市}", urlEncodeUTF8(endCity));
        requestUrl = requestUrl.replace("{出发地}", urlEncodeUTF8(orginStation));
        requestUrl = requestUrl.replace("{目的地}", urlEncodeUTF8(destination));
        requestUrl = requestUrl.replace("{AppKey}", urlEncodeUTF8("00c5bf3cb4430cc9b3fa9bdf82064324")); 
  
        // 查询并解析结果  
        try {  
            // 查询并获取返回结果  
            String json = httpRequest(requestUrl);        
            Station station=JSON.parseObject(json,Station.class);
            List<Results> list=new ArrayList();
            list=station.getDestination().getResults();
            for(int i=0;i<list.size();i++){
            	lineName=list.get(i).getAddress();
            	lineNamelist.add(lineName);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        
        return lineName;  
    }
	public static void main(String[] args) {  
        //食谱查询结果  
    	//System.out.println();
        //log.info(StationSearch.ResultSearch("南京师范大学","中国政法大学","南京","北京"));
    }  
	
	
}
