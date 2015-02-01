package com.qzp.model.extend;

public class Helper {

	public static String getHelper() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("您好，我是智能机器人").append("\n\n");  
        buffer.append("有什么疑问请联系本作者QQ：731478703").append("\n");  
        buffer.append("或者在他的新浪微博发私信<a href=\"http://weibo.com/2489158480/profile?topnav=1&wvr=5&user=1\">@钱志鹏Change</a>").append("\n\n");  
        
        buffer.append("按#号键返回主菜单");  
        return buffer.toString();  
    }  
}
