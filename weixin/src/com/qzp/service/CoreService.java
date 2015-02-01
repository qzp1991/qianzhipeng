package com.qzp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import com.qzp.Util.MessageUtil;
import com.qzp.Util.MySQLUtil;
import com.qzp.model.MovieInform.Movie;
import com.qzp.model.extend.BusSearch;
import com.qzp.model.extend.MainMenu;
import com.qzp.model.extend.WelcomeWorld;
import com.qzp.model.helper.CalculatorHelper;
import com.qzp.model.helper.Helper;
import com.qzp.model.helper.IdentificationHelper;
import com.qzp.model.helper.MusicHelper;
import com.qzp.model.helper.TranslateHelper;
import com.qzp.model.helper.WeatherHelper;
import com.qzp.model.location.BaiduPlace;
import com.qzp.model.location.UserLocation;
import com.qzp.model.resp.Article;
import com.qzp.model.resp.Music;
import com.qzp.model.resp.MusicMessage;
import com.qzp.model.resp.NewsMessage;
import com.qzp.model.resp.TextMessage;

public class CoreService {

	/** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return respMessage
     */  
	
	public static String processRequest(HttpServletRequest request) { 
    	// 返回给微信服务器的xml消息 
        String respMessage = null; 
        // 默认返回的文本消息内容  
        
        try {  
             
            
            /**
             * 调用消息工具类MessageUtil解析微信发来的xml格式的消息,
             * 解析的结果放在HashMap里；
             */
            
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType"); 
           
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName); 
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
            //textMessage.setContent("欢迎访问<a href=\"http://www.guoguang.com.cn\">国光官网</a>!"); 
            // 将文本消息对象转换成xml字符串  
            respMessage = MessageUtil.textMessageToXml(textMessage); 
  
            
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                // 接收用户发送的文本消息内容  
                String content = requestMap.get("Content");  
  
                // 创建图文消息  
                NewsMessage newsMessage = new NewsMessage();  
                newsMessage.setToUserName(fromUserName);  
                newsMessage.setFromUserName(toUserName);  
                newsMessage.setCreateTime(new Date().getTime());  
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                newsMessage.setFuncFlag(0);  
  
                List<Article> articleList = new ArrayList<Article>();  
                // 单图文消息  
                if ("1".equals(content)||"博客".equals(content)) {  
                    Article article = new Article();  
                    article.setTitle("我的博客");  
                    article.setDescription("");  
                    article.setPicUrl("");  
                    article.setUrl("http://blog.sina.com.cn/u/3494557654");  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    //String s="send single message success";
                    //String s1=URLEncoder.encode(s, "utf-8");
                   
                }  
                // 单图文消息---不含图片  
                else if ("2".equals(content)||"QQ".equals(content)) {  
                    Article article = new Article();  
                    article.setTitle("QQ空间");  
                    article.setDescription("");  
                    article.setPicUrl("");  
                    article.setUrl("http://user.qzone.qq.com/601982124");  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
                }  
                // 多图文消息  
                else if ("3".equals(content)||"创业".equals(content)) {  
                	textMessage.setContent("我的QQ:601982124，加我有惊喜，创业有方向");
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage); 
                }  
                // 多图文消息---首条消息不含图片  
                else if ("4".equals(content)||"在线留言".equals(content)) {  
                	textMessage.setContent("我的QQ:601982124，加我有惊喜，创业有方向");
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }  
                // 多图文消息---最后一条消息不含图片  
                
                else if("你的主人是谁".equals(content)||"主人是谁".equals(content)){                	
                	textMessage.setContent("是小鹏，猛戳下面，你就能找到这个臭小子！http://weibo.com/2489158480/profile?topnav=1&wvr=5&user=1");
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                else if("我爱你".equals(content)){                	
                	textMessage.setContent("我也爱你");
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                else if("你是男是女？".equals(content)||"性别".equals(content)||"你是美女吗？".equals(content))
                {                	
                	textMessage.setContent("人家是MM啦");
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }              
                else if(content.startsWith("歌曲")){
                	// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
                    String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");  
                    // 如果歌曲名称为空  
                    if ("".equals(keyWord)) {  
                    	textMessage.setContent("歌曲内容为空"); 
                    	respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else {  
                        String[] kwArr = keyWord.split("@");  
                        // 歌曲名称  
                        String musicTitle = kwArr[0];  
                        // 演唱者默认为空  
                        String musicAuthor = "";  
                        if (2 == kwArr.length)  
                            musicAuthor = kwArr[1];  
  
                        // 搜索音乐  
                        Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);  
                        // 未搜索到音乐  
                        if (null == music) {  
                        	textMessage.setContent("对不起没有找到你所要的歌曲内容"); 
                        	respMessage = MessageUtil.textMessageToXml(textMessage);  
                        } else {  
                            // 音乐消息  
                            MusicMessage musicMessage = new MusicMessage();  
                            musicMessage.setToUserName(fromUserName);  
                            musicMessage.setFromUserName(toUserName);  
                            musicMessage.setCreateTime(new Date().getTime());  
                            musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);  
                            musicMessage.setMusic(music);  
                            respMessage = MessageUtil.musicMessageToXml(musicMessage);  
                         }  
                      }
                   }
                else if ("5".equals(content)){                	
                    textMessage.setContent(MusicHelper.getMusicHelper());
                    // 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                else if("6".equals(content)){
                	textMessage.setContent(IdentificationHelper.getIdentificationHelper());
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                else if("7".equals(content)){
                	textMessage.setContent(TranslateHelper.getTranslateHelper());
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                else if("8".equals(content)){
                	textMessage.setContent(WeatherHelper.getWeatherHelper());
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                
                //显示帮助
                else if("help".equals(content)||"问问".equals(content)
                		||"疑问".equals(content)||"问".equals(content)){
                	textMessage.setContent(Helper.getHelper());
                	// 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                
                //翻译
                else if (content.startsWith("翻译")) {  
                    String keyWord = content.replaceAll("^翻译", "").trim();  
                    if ("".equals(keyWord)) {  
                        textMessage.setContent(TranslateHelper.getTranslateHelper());  
                    } else {  
                        textMessage.setContent(BaiduTranslateService.translate(keyWord));  
                    }  
                    respMessage = MessageUtil.textMessageToXml(textMessage); 
                }
                
                //五险一金计算器
                else if (content.startsWith("工资")) {  
                    String keyWord = content.replaceAll("^工资", "").trim();                                   
                    if ("".equals(keyWord)) {  
                        textMessage.setContent(CalculatorHelper.getCalculatorHelper());  
                    } else {  
                    	String wxyj=MyWebService.searchWxyj(keyWord);
                        textMessage.setContent("您所需要缴纳的五险一金是"+wxyj); 
                       
                    }  
                    respMessage = MessageUtil.textMessageToXml(textMessage); 
                    
                }
                
                //电影查询
                else if (content.startsWith("电影查询")) {  
                    String keyWord = content.replaceAll("^电影查询", "").trim();                                   
                    if ("".equals(keyWord)) {  
                        textMessage.setContent("输入电影查询城市进行查询，如：电影查询南京");  
                    } else {  
                    	Movie movie=FilmSearchService.ResultSearch(keyWord);
                        textMessage.setContent("电影的名称是："+movie.getMovie_name()+";电影的演员是"+movie.getMovie_starring()); 
                        
                    }  
                    respMessage = MessageUtil.textMessageToXml(textMessage);                     
                }
                
                //菜谱查询
                else if (content.startsWith("菜谱")) {  
                    String keyWord = content.replaceAll("^菜谱", "").trim();                                   
                    if ("".equals(keyWord)) {  
                        textMessage.setContent("输入菜谱宫保鸡丁进行查询");  
                    } else {  
                    	String caipu=RecipesSearchService.ResultSearch(keyWord);
                        textMessage.setContent(caipu); 
                        
                    }  
                    respMessage = MessageUtil.textMessageToXml(textMessage); 
                    
                }
                //天气
                else if (content.startsWith("天气")) {  
                    String keyWord = content.replaceAll("^天气", "").trim();  
                    if ("".equals(keyWord)) {  
                        textMessage.setContent(WeatherHelper.getWeatherHelper());  
                    } else {  
                    	String  temp1=null;
                        String  temp2=null;
                        String tianqi=null;
                        String cityName=keyWord;
                    	// 温度及天气查询                   	
                        temp1=(String) WeatherServcie.tempSearch(cityName).get(0);
                        temp2=(String) WeatherServcie.tempSearch(cityName).get(1);
                        tianqi=(String) WeatherServcie.tempSearch(cityName).get(2);
                        textMessage.setContent(cityName+"的最低温度："+temp2 +";最高温度"+temp1+";天气情况："+tianqi);   
                    }  
                    respMessage = MessageUtil.textMessageToXml(textMessage); 
                }
                
                //公交
                else if (content.startsWith("公交查询")) {  
                    String keyWord = content.replaceAll("^公交查询", "").trim();
                    StringTokenizer st = new StringTokenizer(keyWord, ",，");//按照逗号进行截取 
           		 //StringTokenizer st = new StringTokenizer(keyWord);
           		String city=null;String start=null;String end=null;
           		while (st.hasMoreTokens()) {
           	        city=st.nextToken();
           	        start=st.nextToken();
           	        end=st.nextToken();
           	     }
                     textMessage.setContent(city+"从"+start+"开往"+end+"的路线是"+BusSearchService.busSearch(city,start,end));    
                     respMessage = MessageUtil.textMessageToXml(textMessage); 
                    } 
                
               // 周边搜索
			   else if (content.startsWith("附近")) {
					String keyWord = content.replaceAll("附近", "").trim();
					// 获取用户最后一次发送的地理位置
					UserLocation location = MySQLUtil.getLastLocation(request, fromUserName);
					// 未获取到
					if (null == location) {
						textMessage.setContent("输入地理位置进行查询"); 
					} else {
						// 根据转换后（纠偏）的坐标搜索周边POI
						List<BaiduPlace> placeList = BaiduMapService.searchPlace(keyWord, location.getBd09Lng(), location.getBd09Lat());
						// 未搜索到POI
						if (null == placeList || 0 == placeList.size()) {
							textMessage.setContent("您发送的位置附近未搜索到"+keyWord+"信息！");
						} else {
							List<Article> locationarticleList = BaiduMapService.makeArticleList(placeList, location.getBd09Lng(), location.getBd09Lat());
							// 回复图文消息
							NewsMessage locationnewsMessage = new NewsMessage();
							locationnewsMessage.setToUserName(fromUserName);
							locationnewsMessage.setFromUserName(toUserName);
							locationnewsMessage.setCreateTime(new Date().getTime());
							locationnewsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							locationnewsMessage.setArticles(locationarticleList);
							locationnewsMessage.setArticleCount(locationarticleList.size());
							respMessage = MessageUtil.newsMessageToXml(locationnewsMessage);
						  }
					 }
				 } 
                //返回主菜单
                else{
                    textMessage.setContent(MainMenu.getMainMenu());
                    // 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                  }    
               
               }
	
            
            // 图片消息  
            else if (MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {  
            	
            	// 取得图片地址 
            	String picUrl = requestMap.get("PicUrl");
            	// 人脸检测
            	String detectResult = FaceService.detect(picUrl); 
            	textMessage.setContent(detectResult); 
                respMessage = MessageUtil.textMessageToXml(textMessage);
                //String s="send a picture success";
                //String s1=URLEncoder.encode(s, "utf-8");
                //System.out.println(s1);
                
               }
            
            
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
            	// 用户发送的经纬度
				String lng = requestMap.get("Location_Y");
				String lat = requestMap.get("Location_X");
				textMessage.setContent(lng); 
                respMessage = MessageUtil.textMessageToXml(textMessage);
				// 坐标转换后的经纬度
				String bd09Lng = null;
				String bd09Lat = null;
				// 调用接口转换坐标
				UserLocation userLocation = BaiduMapService.convertCoord(lng, lat);
				if (null != userLocation) {
					bd09Lng = userLocation.getBd09Lng();
					bd09Lat = userLocation.getBd09Lat();
				}
				// 保存用户地理位置
				MySQLUtil.saveUserLocation(request, fromUserName, lng, lat, bd09Lng, bd09Lat);

				StringBuffer buffer = new StringBuffer();
				buffer.append("[愉快]").append("成功接收您的位置！").append("\n\n");
				buffer.append("您可以输入搜索关键词获取周边信息了，例如：").append("\n");
				buffer.append("        附近ATM").append("\n");
				buffer.append("        附近KTV").append("\n");
				buffer.append("        附近厕所").append("\n");
				buffer.append("必须以“附近”两个字开头！");
				String respContent = buffer.toString();
				textMessage.setContent(respContent);
                // 文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage);
			  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                //respContent = "您发送的是链接消息！";
                //textMessage.setContent(respContent);
                
                // 文本消息对象转换成xml字符串  
                //respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            
            
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
               // respContent = "您发送的是音频消息！"; 
                
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { 
                	String respContent=null;
                	respContent=WelcomeWorld.getWelcomeWorld();
                    textMessage.setContent(respContent);
                    // 文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
               }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
  
                    if (eventKey.equals("11")) {  
                        //respContent = "天气预报菜单项被点击！";
                        textMessage.setContent(WeatherHelper.getWeatherHelper());
                        respMessage = MessageUtil.textMessageToXml(textMessage); 
                    } else if (eventKey.equals("12")) {  
                    	textMessage.setContent("公交查询菜单项被点击！");
                    	textMessage.setContent(BusSearch.getBusSearchHelper());
                    	respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("13")) {  
                        //respContent = "智能翻译菜单项被点击！"; 
                        textMessage.setContent(TranslateHelper.getTranslateHelper());
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("14")) {  
                        //respContent = "创业交流";
                    	textMessage.setContent("我的QQ:601982124，加我有惊喜，创业有方向");
                    	// 文本消息对象转换成xml字符串  
                        respMessage = MessageUtil.textMessageToXml(textMessage); 
                    } else if (eventKey.equals("21")) {  
                        //respContent = "歌曲点播菜单项被点击！";
                        textMessage.setContent(MusicHelper.getMusicHelper());
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("22")) {  
                    	textMessage.setContent("输入电影查询城市进行查询，如：电影查询南京");
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    	  
                    } else if (eventKey.equals("23")) {  
                        //respContent = "电影查询！"; 
                    	textMessage.setContent(IdentificationHelper.getIdentificationHelper());
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("24")) {  
                        //respContent = "人脸识别菜单项被点击！";
                        textMessage.setContent(IdentificationHelper.getIdentificationHelper());
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("25")) {  
                        //respContent = "聊天唠嗑菜单项被点击！"; 
                    	textMessage.setContent("我们开始聊天吧！");
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    } else if (eventKey.equals("31")) {  
                        //respContent = "系统登录";  
                    } else if (eventKey.equals("32")) {  
                        //respContent = "电影排行榜菜单项被点击！";  
                    } else if (eventKey.equals("33")) {  
                        //respContent = "幽默笑话菜单项被点击！";  
                    }  
                } 
               
            }              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return respMessage; 
        
    }	
}
