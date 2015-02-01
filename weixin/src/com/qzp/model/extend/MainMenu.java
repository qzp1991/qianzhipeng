package com.qzp.model.extend;

public class MainMenu {

	public static String getMainMenu() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("您好，我是智能机器人[悠闲]，请回复数字选择服务：").append("\n\n");  
        buffer.append("1  我的博客").append("\n");  
        buffer.append("2  我的空间").append("\n");  
        buffer.append("3  QQ留言").append("\n");
        buffer.append("4  创业交流[憨笑]").append("\n");
        buffer.append("5  在线听歌").append("\n");
        buffer.append("6  人脸识别").append("\n");
        buffer.append("7  在线翻译").append("\n");
        buffer.append("8  天气查询").append("\n\n");
        buffer.append("回复\"help\"显示此帮助菜单");  
        return buffer.toString();  
    }  
}
