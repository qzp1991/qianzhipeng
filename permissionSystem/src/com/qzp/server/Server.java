package com.qzp.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import com.qzp.service.MyService;
import com.qzp.serviceImpl.MyServiceImpl;

public class Server {
	
	/**
	 * 私有化构造方法
	 * 在浏览器中，如若可以访问http://localhost:9000/MyService?wsdl则说明部署成功
	 */
	private Server() {
		MyServiceImpl ms = new MyServiceImpl();
		// 创建WebService服务工厂
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		// 注册WebService接口
		factory.setServiceClass(MyService.class);
		// 发布接口
		factory.setAddress("http://localhost:9000/MyService");
		factory.setServiceBean(ms);
		// 创建WebService
		factory.create();
	};

	public static void main(String[] args) throws InterruptedException {
		
		// 启动服务端
		new Server();
		System.out.println("Server ready...");
		// 休眠一分钟，便于测试
//		Thread.sleep(1000 * 60);
//		System.out.println("Server exit...");
//		System.exit(0);
	}

}
