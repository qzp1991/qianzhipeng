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

public class function extends HttpServlet {

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
		try {
		String function=request.getParameter("function");
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("data");
		Element operation = root.addElement("operation");    
		operation.addText(function);
		String xmlPara = document.asXML();
		// 创建WebService客户端代理工厂
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		// 注册WebService接口
		factory.setServiceClass(MyService.class);
		// 设置WebService地址
		factory.setAddress("http://localhost:9000/MyService");
		MyService service = (MyService) factory.create();
		String result=null;
		System.out.println(function);
		if(function.equals("角色授权查询")){
			result=service.queryGrant(xmlPara);
		}
		else if(function.equals("对象表查询")){
			result=service.queryObject(xmlPara);
		}
		else if(function.equals("操作表查询")){
			result=service.queryOperator(xmlPara);
		}
		else if(function.equals("机构查询")){
			result=service.queryOrgTree(xmlPara);
		}
		else if(function.equals("查询权限表")){
			result=service.queryPermission(xmlPara);
		}
		else if(function.equals("角色查询")){
			result=service.queryRole(xmlPara);
		}
		request.setAttribute("result",result);
		//System.out.println(result);
		request.getRequestDispatcher("result/functionResult.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


