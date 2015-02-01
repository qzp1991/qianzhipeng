package com.qzp.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.qzp.service.MyService;

public class setUserInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userNo1=request.getParameter("userNo");
		String userId1=request.getParameter("userId");
		String userName1=request.getParameter("userName");
		String userDepartNo1=request.getParameter("userDepartNo");
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("data");
		Element userNo = root.addElement("userNo");    
		userNo.addText(userNo1);
		Element userId= root.addElement("userId");
		userId.addText(userId1);
		Element userName= root.addElement("userName");
		userName.addText(userName1);
		Element userDepartNo= root.addElement("userDepartNo");
		userDepartNo.addText(userDepartNo1);
		String xmlPara = document.asXML();
		// 创建WebService客户端代理工厂
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		// 注册WebService接口
		factory.setServiceClass(MyService.class);
		// 设置WebService地址
		factory.setAddress("http://localhost:9000/MyService");
		MyService service = (MyService) factory.create();
		String result=null;
		try {
			 result=service.setUserInfo(xmlPara);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("result",result);
		System.out.println(result);
		request.getRequestDispatcher("result/result.jsp").forward(request, response);
	}
}


