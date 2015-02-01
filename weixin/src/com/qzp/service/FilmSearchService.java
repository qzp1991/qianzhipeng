package com.qzp.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.qzp.model.MovieInform.Film;
import com.qzp.model.MovieInform.Movie;

public class FilmSearchService {

	//private static Logger log = LoggerFactory.getLogger(RecipesSearchService.class);
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
     * 电影查询
     *  
     * @param source 
     * @return 
     */  
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Movie ResultSearch(String city) {  
        
		Movie movie=null;
        // 组装查询地址  
        String requestUrl = "http://api.map.baidu.com/telematics/v3/movie?qt=hot_movie&location={城市}&output=json&ak=00c5bf3cb4430cc9b3fa9bdf82064324";  
        // 对参数q的值进行urlEncode utf-8编码  
        requestUrl = requestUrl.replace("{城市}", urlEncodeUTF8(city)); 
        requestUrl = requestUrl.replace("{AppKey}", urlEncodeUTF8("cafe1ec7b4ec32a39bd3f35b9db654ea"));         
            
        // 查询并解析结果  
        try {  
            // 查询并获取返回结果  
            String json = httpRequest(requestUrl);        
            Film film=JSON.parseObject(json,Film.class);            
            List<Movie> movieList=new ArrayList();
            movieList=film.getResult().getMovie(); 
            Movie s=movieList.get(0);
            System.out.println(s);
            for(int i=0;i<movieList.size();i++){
            	movie=movieList.get(i);
            }        	
        } catch (Exception e) {  
            e.printStackTrace();  
        }        
        return movie;  
    }
	
	public static void main(String[] args) {  
        
		//电影查询结果      	
		Movie movie=new Movie();
        movie=FilmSearchService.ResultSearch("南京");
		
        System.out.println("电影的名称是："+movie.getMovie_name()+";电影的演员是"+movie.getMovie_starring());
    }  
}
