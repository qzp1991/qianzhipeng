package com.qzp.model.helper;

public class CalculatorHelper {

	public static String getCalculatorHelper() {  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("欢迎使用五险一金计算器").append("\n\n");  
	    buffer.append("本平台可以在线计算您的五险一金,输入你的工资，即可计算").append("\n");  
	      
	     
	    buffer.append("使用示例：").append("\n");  
	    buffer.append("工资：9000").append("\n");  
	    buffer.append("您所需缴纳的五险一金为：1620.0元").append("\n");
	    
	    buffer.append("回复\"任意\"显示主菜单");  
	    return buffer.toString();  
	}  
}
