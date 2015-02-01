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
import com.qzp.model.extend.Weather;

public class WeatherServcie {

	private static Logger log = LoggerFactory.getLogger(BaiduMusicService.class);
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
  
            // 将返回的输入流转换成字符串  
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
    public static String urlEncodeUTF8(String cityNo) {  
        String result = cityNo;  
        try {  
            result = java.net.URLEncoder.encode(cityNo,"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    /** 
     * 天气查询
     *  
     * @param source 
     * @return 
     */  
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List tempSearch(String cityName) {  
        //默认温度为0度    	
    	String temperature1=null;    	 
    	String temperature2=null;  
    	int cityid=0;
    	String weather=null;
    	String cityId=null;
    	List weathList =new ArrayList();
    	if("北京".equals(cityName)){
    		cityid=101010100;
    	}
    	if("苏州".equals(cityName)){
    		cityid=101190401;
    	}
    	if("南京".equals(cityName)){
    		cityid=101190101;
    	}
    	if("南通".equals(cityName)){
    		cityid=101190501;
    	}
    	if("杭州".equals(cityName)){
    		cityid=101210101;
    	}
    	if("天津".equals(cityName)){
    		cityid=101210101;
    	}
    	if("武汉".equals(cityName)){
    		cityid=101210101;
    	}
    	if("深圳".equals(cityName)){
    		cityid=101280601;
    	}
    	if("厦门".equals(cityName)){
    		cityid=101230201;
    	}
    	if("重庆".equals(cityName)){
    		cityid=101040100;
    	}
    	if("上海".equals(cityName)){
    		cityid=101020100;
    	}
    	if(cityid==0){
    		cityId="您输入的城市不在查询范围之内！";
    	}
    	
    	cityId=""+cityid;
        // 组装查询地址  
        String requestUrl = "http://www.weather.com.cn/data/cityinfo/{城市所在的编号}.html";  
        // 对参数q的值进行urlEncode utf-8编码  
        requestUrl = requestUrl.replace("{城市所在的编号}", urlEncodeUTF8(cityId));  
  
        // 查询并解析结果  
        try {  
            // 查询并获取返回结果  
            String json = httpRequest(requestUrl);             
            Weather weath=JSON.parseObject(json, Weather.class);
            temperature1=weath.getWeatherInfo().getTemp1();
            temperature2=weath.getWeatherInfo().getTemp2();
            weather=weath.getWeatherInfo().getWeather();           
            weathList.add(temperature1);
            weathList.add(temperature2);
            weathList.add(weather);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        if (weathList.isEmpty())  
        { log.info("错误");}
        return weathList;  
    }
     
    public static void main(String[] args) {  
        String  temp1=null;
        String  temp2=null;
        String tianqi=null;
        String cityName="北京";
    	// 温度查询结果  
    	
        temp1=(String) WeatherServcie.tempSearch(cityName).get(0);
        temp2=(String) WeatherServcie.tempSearch(cityName).get(1);
        tianqi=(String) WeatherServcie.tempSearch(cityName).get(2);
        //log.info(cityName+"的最低温度："+temp1 +";最高温度"+temp2+";天气情况："+tianqi);
    }  
}
