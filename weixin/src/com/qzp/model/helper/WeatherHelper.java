package com.qzp.model.helper;

public class WeatherHelper {

	public static String getWeatherHelper() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("[拥抱]天气预报使用指南").append("\n\n");  
	    buffer.append("天气预报使用指南为用户提供实时的天气查询，目前支持以下城市天气查询：").append("\n");  
	    buffer.append("北京、上海、深圳、南京等地区").append("\n\n");  
	     
	    buffer.append("使用示例：").append("\n");  
	    buffer.append("天气南通").append("\n\n");  
	    
	    buffer.append("回复\"任意\"显示主菜单");  
	    return buffer.toString();  
	}  
}
