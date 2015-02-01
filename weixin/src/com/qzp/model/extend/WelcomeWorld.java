package com.qzp.model.extend;

public class WelcomeWorld {

	public static String getWelcomeWorld() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("感谢您的关注！[玫瑰]").append("\n\n");  
        buffer.append("本平台支持在线人脸识别、实时天气，在线听歌；").append("\n");  
        buffer.append("想创业的可以加我QQ：601982124交流。[握手]").append("\n\n");
        buffer.append("也可以去<a href=\"http://blog.sina.com.cn/u/3494557654\">我的博客</a>");
        buffer.append("或者去<a href=\"http://user.qzone.qq.com/601982124\">我的空间</a>").append("\n\n");
        
        buffer.append("按任意键进入主菜单[强]");  
        return buffer.toString();  
    }  
}
