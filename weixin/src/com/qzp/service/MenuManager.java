package com.qzp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qzp.Util.WeixinUtil;
import com.qzp.model.button.AccessToken;
import com.qzp.model.button.Button;
import com.qzp.model.button.CommonButton;
import com.qzp.model.button.ComplexButton;
import com.qzp.model.button.Menu;
import com.qzp.model.button.ViewButton;

/** 
 * 菜单管理器类   用于菜单的创建和管理
 *  
 * @author  qzp
 * @date 2014-7-20 
 */  
public class MenuManager{  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) {  
        // 第三方用户唯一凭证  
        String appId = "wx93bcbb2c61c06445";  
        // 第三方用户唯一凭证密钥  
        String appSecret = "7702a72b0a965e2a9803975ba46b4e43";  
  
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId,appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(),at.getToken());  
  
         // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result); 
        }  
    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("天气预报");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("公交查询");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("智能翻译");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("创业交流");  
        btn14.setType("click");  
        btn14.setKey("14");  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("歌曲点播");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        ViewButton btn22 = new ViewButton();  
        btn22.setName("2048");  
        btn22.setType("view");  
        btn22.setUrl("http://2048.oubk.com/");
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("电影查询");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("人脸识别");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        CommonButton btn25 = new CommonButton();  
        btn25.setName("聊天唠嗑");  
        btn25.setType("click");  
        btn25.setKey("25");  
  
        ViewButton btn31 = new ViewButton();  
        btn31.setName("登陆验证");  
        btn31.setType("view");  
        btn31.setUrl("http://61.132.89.94:4848/html5/MyHtml.html");  
  
        ViewButton btn32 = new ViewButton();  
        btn32.setName("我的博客");  
        btn32.setType("view");  
        btn32.setUrl("http://blog.sina.com.cn/u/3494557654"); 
  
        ViewButton btn33 = new ViewButton();  
        btn33.setName("我的空间");  
        btn33.setType("view");  
        btn33.setUrl("http://user.qzone.qq.com/601982124"); 
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("生活助手");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("休闲驿站");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多指南");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * 公众号目前的菜单结构，每个一级菜单都有二级菜单项
         *  
         * 最多3个一级菜单 
         * 每个一级菜单上最多有5个二级菜单 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[]{mainBtn1,mainBtn2,mainBtn3});  
  
        return menu;
    }  
}  
