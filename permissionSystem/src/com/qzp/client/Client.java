package com.qzp.client;

import java.io.File;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import com.qzp.service.MyService;

public class Client {

	private Client() {
	};
	
	//测试程序
	//放入不同的报文以测试不同的功能模块
	//测试时需要更改待输入的xml文件(第一行)，和待调用的webservice方法(最后一行)
	public static void main(String[] args) throws Exception {
		Document document = new SAXReader().read(new File("resource/setRoleToUser.xml"));
		String xmlPara = document.asXML();
		// 创建WebService客户端代理工厂
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		// 注册WebService接口
		factory.setServiceClass(MyService.class);
		// 设置WebService地址
		factory.setAddress("http://localhost:9000/MyService");
		MyService service = (MyService) factory.create();
		System.out.println(service.setRoleToUser(xmlPara));
	}	
}

