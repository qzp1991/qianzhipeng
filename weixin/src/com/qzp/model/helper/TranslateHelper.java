package com.qzp.model.helper;

public class TranslateHelper {

	public static String getTranslateHelper() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("[拥抱]智能翻译使用指南").append("\n\n");  
	    buffer.append("智能翻译为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");  
	    buffer.append("    中 -> 英").append("\n\n");  
	     
	    buffer.append("使用示例：").append("\n");  
	    buffer.append("    翻译我是中国人").append("\n\n");  
	    
	    buffer.append("回复\"任意\"显示主菜单");  
	    return buffer.toString();  
	}  
}
