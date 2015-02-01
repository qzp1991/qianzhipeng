package com.qzp.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.qzp.model.extend.TranslateResult;

public class BaiduTranslateService {

	private static Logger log = LoggerFactory.getLogger(BaiduTranslateService.class);
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
            while ((str = bufferedReader.readLine()) != null) {  
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
     * @param source 
     * @return 
     */  
    public static String urlEncodeUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLEncoder.encode(source, "utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    /** 
     * 翻译（中->英 英->中 日->中 ） 
     *  
     * @param source 
     * @return 
     */  
    public static String translate(String source) {  
        //翻译的结果
    	String dst = null;  
  
        // 组装查询地址  
        String requestUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=NREpH5QF3hhMYgD8oABniRGG&q={待翻译的内容}&from=auto&to=auto";  
        // 对参数q的值进行urlEncode utf-8编码  
        requestUrl = requestUrl.replace("{待翻译的内容}", urlEncodeUTF8(source));  
  
        // 查询并解析结果  
        try {  
            // 查询并获取返回结果  
            String json = httpRequest(requestUrl);  
            // 通过Gson工具将json转换成TranslateResult对象  
            TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);  
            // 取出translateResult中的译文  
            dst = translateResult.getTrans_result().get(0).getDst();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        if (null == dst)  
            dst = "翻译系统异常，请稍候尝试！";  
        return dst;  
    }  
  
    public static void main(String[] args) {  
        // 翻译结果：The network really powerful  
    	//log.info(translate("我是谁"));
        //System.out.println();  
    }  
}
