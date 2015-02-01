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

public class setObjInfo extends HttpServlet {

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

		String objectNo1=request.getParameter("objectNo");
		String objectName1=request.getParameter("objectName");
		String parentNo1=request.getParameter("parentNo");
		String description1=request.getParameter("description");
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("data");
		Element objectNo = root.addElement("objectNo");    
		objectNo.addText(objectNo1);
		Element objectName= root.addElement("objectName");
		objectName.addText(objectName1);
		Element parentNo= root.addElement("parentNo");
		parentNo.addText(parentNo1);
		Element description= root.addElement("description");
		description.addText(description1);
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
