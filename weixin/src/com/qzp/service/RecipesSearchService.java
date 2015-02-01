package com.qzp.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.qzp.model.extend.Food;

public class RecipesSearchService {

	private static Logger log = LoggerFactory.getLogger(RecipesSearchService.class);
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
    
	public static String ResultSearch(String cai) {  
        //默认温度为0度
    	String caipu=null;
        // 组装查询地址  
        String requestUrl = "http://apis.juhe.cn/cook/query?key={AppKey}&menu={菜谱}&rn=1";  
        // 对参数q的值进行urlEncode utf-8编码  
        requestUrl = requestUrl.replace("{菜谱}", urlEncodeUTF8(cai)); 
        requestUrl = requestUrl.replace("{AppKey}", urlEncodeUTF8("cafe1ec7b4ec32a39bd3f35b9db654ea")); 
  
        // 查询并解析结果  
        try {  
            // 查询并获取返回结果  
            String json = httpRequest(requestUrl);        
            Food food=JSON.parseObject(json, Food.class);
            caipu=food.getResult().getData().get(0).getImtro();     
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        
        return caipu;  
    }
	public static void main(String[] args) {  
        //食谱查询结果  
    	//System.out.println();
        //log.info(RecipesSearchService.ResultSearch("西红柿"));
    }  
}
