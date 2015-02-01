package com.qzp.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.jws.WebService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.qzp.dao.DepartDao;
import com.qzp.dao.GrantDao;
import com.qzp.dao.ObjectDao;
import com.qzp.dao.OperatorDao;
import com.qzp.dao.PermissionDao;
import com.qzp.dao.RoleDao;
import com.qzp.dao.RoleToUserDao;
import com.qzp.dao.UserDao;
import com.qzp.service.MyService;


@WebService 
public class MyServiceImpl implements MyService {

	//测试的webservice
	public String sayHi(String name) {    
		System.out.println("sayHello is called by " + name);    
		return "Hello" + name;    
	 }

	//登陆验证
	public String loginService(String xmlPara) throws DocumentException{
			SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element userNo=root.element("userNo");
			Element userPwd=root.element("userPwd");
			String result=null;
			/**
			 * 登陆验证，用户名和密码都正确，则返回用户信息和所在部门
			 */
			if("100".equals(userNo.getText())&&"111111".equals(userPwd.getTextTrim())){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("登陆成功");				
				Element userName=root2.addElement("userName");
				userName.addText(userNo.getText());				
				Element departName=root2.addElement("departName");
				departName.addText("自助部");
				//将doucment文档转化为xml格式				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0001");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("登陆失败");				
				Element userName=root2.addElement("userName");
				userName.addText(userNo.getText());
				Element departName=root2.addElement("departName");
				departName.addText("无");								
				result=document2.asXML();
				return result;
			}	
		}
	    
	 //带数据库的登陆验证    本项目的核心所在
	 public String userService(String xmlPara) throws Exception {
	    	String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			
			Element userNo=root.element("userNo");
			Element userPwd=root.element("userPwd");
			System.out.println(userNo.getText());
			UserDao userDao=new UserDao();
			ObjectDao objDao=new ObjectDao();
			int i=0;
			int count=objDao.objListCount(userNo.getText());
			if(userDao.userCount(userNo.getTextTrim(),userPwd.getTextTrim())>0){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("登陆成功");				
				Element userName=root2.addElement("userName");
				userName.addText(userNo.getText());				
				Element departName=root2.addElement("departName");
				departName.addText(userDao.query(userNo.getText()));
				Element authSet=root2.addElement("authSet");
				for(i=0;i<count;i++){
					Element objNo = authSet.addElement("objNo");    
					objNo.addText(objDao.objList(userNo.getText()).get(i).getObjectNo()+"");							
					Element objName = authSet.addElement("objName");     
					objName.addText(objDao.objList(userNo.getText()).get(i).getObjectName());
					Element description=authSet.addElement("description");
					description.addText(objDao.objList(userNo.getText()).get(i).getDescription());
				  	}
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0001");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("登陆失败");				
				Element userName=root2.addElement("userName");
				userName.addText(userNo.getText());				
				Element departName=root2.addElement("departName");
				departName.addText("无");			
				result=document2.asXML();
				return result;
			}
	    
		}

	    //修改密码操作
		public String updatePassword(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element userNo=root.element("userNo");
			Element userPwd=root.element("userPwd");
			Element newUserPwd=root.element("newUserPwd");
			UserDao userDao=new UserDao();			
			if(userDao.userCount(userNo.getText(),userPwd.getText())==1&&userDao.update(userNo.getText(),newUserPwd.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改密码成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改密码失败");				
				result=document2.asXML();
				return result;
			}
		}

		//查询机构
		public String queryOrgTree(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element operation=root.element("operation");			
			DepartDao departDao=new DepartDao();
			int i=0;
			int count=departDao.departCount();
			if(operation.getText().equals("机构查询")){			  
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element departNo = root2.addElement("departNo");    
				departNo.addText(departDao.departList().get(i).getDepartNo()+"");							
				Element departName = root2.addElement("departName");     
				departName.addText(departDao.departList().get(i).getDepartName()+"");
			    }
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element departNo = root2.addElement("departNo");    
				departNo.addText("0002");							
				Element departName = root2.addElement("departName");     
				departName.addText("没有查询到机构");				
				result=document2.asXML();
				return result;
			}
		}

		//修改机构信息
		public String setOrgInfo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();			
			Element departNo=root.element("departNo");
			Element departId=root.element("departId");
			Element departName=root.element("departName");
			Element departParentNo=root.element("departParentNo");
			DepartDao departDao=new DepartDao();			
			if(departDao.update(departNo.getText(),departId.getText(),departName.getText(),departParentNo.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");  
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改机构成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0001");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改机构信息失败");				
				result=document2.asXML();
				return result;
			}
		}

		//查询员工信息
		public String queryUserInfo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element queryType=root.element("queryType");
			Element queryInfo=root.element("queryInfo");			
			UserDao userDao=new UserDao();
			if(userDao.userListCount(queryType.getText(),queryInfo.getText())>0){
				System.out.println(userDao.userListCount(queryType.getText(), queryInfo.getText()));
				int i;
				int count=userDao.userListCount(queryType.getText(),queryInfo.getText());
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element userNo = root2.addElement("userNo");    
				userNo.addText(userDao.userList(queryType.getText(), queryInfo.getText()).get(i).getNo()+"");							
				Element userId = root2.addElement("userId");     
				userId.addText(userDao.userList(queryType.getText(), queryInfo.getText()).get(i).getId());
				Element userName=root2.addElement("userName");
				userName.addText(userDao.userList(queryType.getText(), queryInfo.getText()).get(i).getName());
				Element userPwd=root2.addElement("userPwd");
				userPwd.addText(userDao.userList(queryType.getText(), queryInfo.getText()).get(i).getPassword());
				Element userDepartNo=root2.addElement("userDepartNo");
				userDepartNo.addText(userDao.userList(queryType.getText(), queryInfo.getText()).get(i).getDepartNo()+"");
			    }
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0002");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("查询失败");				
				result=document2.asXML();
				return result;
			}
		}

		//修改用户信息
		public String setUserInfo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element userNo=root.element("userNo");
			Element userId=root.element("userId");
			Element userName=root.element("userName");
			Element userDepartNo=root.element("userDepartNo");
			UserDao userDao=new UserDao();			
			if(userDao.updateUserInfo(userNo.getText(),userId.getText(),userName.getText(),userDepartNo.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改员工信息成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改员工信息失败");				
				result=document2.asXML();
				return result;
			}
		}

		//查询对象表
		public String queryObject(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element operation=root.element("operation");		
			ObjectDao objectDao=new ObjectDao();
			if(objectDao.objectListCount()>0&&operation.getText().equals("对象表查询")){
				int i;
				int count=objectDao.objectListCount();
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element objNo = root2.addElement("objNo");    
				objNo.addText(objectDao.objectList().get(i).getObjectNo()+"");							
				Element objName = root2.addElement("objName");     
				objName.addText(objectDao.objectList().get(i).getObjectName());
				Element parentNo=root2.addElement("parentNo");
				parentNo.addText(objectDao.objectList().get(i).getParentNo()+"");
				Element description=root2.addElement("description");
				description.addText(objectDao.objectList().get(i).getDescription());
			  	}
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0002");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("查询对象表失败");				
				result=document2.asXML();
				return result;
			}
		}


		//查询操作表
		public String queryOperator(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element operation=root.element("operation");	
			OperatorDao operatorDao=new OperatorDao();
			if(operatorDao.operatorListCount()>0&&operation.getText().equals("操作表查询")){
				int i;
				int count=operatorDao.operatorListCount();
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element userNo = root2.addElement("operatorNo");    
				userNo.addText(operatorDao.operatorList().get(i).getOperatorNo()+"");							
				Element userId = root2.addElement("operatorName");     
				userId.addText(operatorDao.operatorList().get(i).getOperatorName());
			    }
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0002");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("查询操作表失败");				
				result=document2.asXML();
				return result;
			}
		}

		//查询权限表
		public String queryPermission(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element permission=root.element("operation");		
			PermissionDao permissionDao=new PermissionDao();
			if(permissionDao.permissionListCount()>0&&permission.getText().equals("查询权限表")){
				int i;
				int count=permissionDao.permissionListCount();
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element permissionNo = root2.addElement("permissionNo");    
				permissionNo.addText(permissionDao.permissionList().get(i).getPermissionNo()+"");							
				Element permissionName = root2.addElement("permissionName");     
				permissionName.addText(permissionDao.permissionList().get(i).getPermissionName());
				Element objectNo = root2.addElement("objectNo");    
				objectNo.addText(permissionDao.permissionList().get(i).getObjectNo()+"");	
				Element operatorNo = root2.addElement("operatorNo");    
				operatorNo.addText(permissionDao.permissionList().get(i).getOperatorNo()+"");	
			    }
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0002");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("查询权限表失败");				
				result=document2.asXML();
				return result;
			}
		}

		//修改对象表
		public String setObjInfo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element obj=root.element("obj");
			Element objectNo=obj.element("objectNo");
			Element objectName=obj.element("objectName");
			Element parentNo=obj.element("parentNo");
			Element description=obj.element("description");
			
			ObjectDao objectDao=new ObjectDao();		
			if(objectDao.updateObject(objectNo.getText(),objectName.getText(),parentNo.getText(),description.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改对象表成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改对象表失败");				
				result=document2.asXML();
				return result;
			}
		}

		//修改操作表
		public String setOptInfo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element operator=root.element("operator");
			Element operatorNo=operator.element("operatorNo");
			Element operatorName=operator.element("operatorName");
			
			OperatorDao operatorDao=new OperatorDao();		
			if(operatorDao.updateOperator(operatorNo.getText(),operatorName.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改操作表成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改操作表失败");				
				result=document2.asXML();
				return result;
			}
		}

		//修改权限表
		public String setPermission(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element permission=root.element("permission");
			Element permissionNo=permission.element("permissionNo");
			Element permissionName=permission.element("permissionName");
			Element objectNo=permission.element("objectNo");
			Element operatorNo=permission.element("operatorNo");
			
			PermissionDao permissionDao=new PermissionDao();		
			if(permissionDao.updatePermission(permissionNo.getText(), permissionName.getText(), objectNo.getText(), operatorNo.getText())==1&&permission.getText().equals("permission")){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改权限表成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改权限表失败");				
				result=document2.asXML();
				return result;
			}
		}

		//查询角色
		public String queryRole(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element operation=root.element("operation");	
			RoleDao roleDao=new RoleDao();
			if(roleDao.roleListCount()>0&&operation.getText().equals("角色查询")){
				int i;
				int count=roleDao.roleListCount();
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element roleNo = root2.addElement("roleNo");    
				roleNo.addText(roleDao.roleList().get(i).getRoleNo()+"");							
				Element roleName = root2.addElement("roleName");     
				roleName.addText(roleDao.roleList().get(i).getRoleName());
				Element roleParentNo = root2.addElement("roleParentNo");    
				roleParentNo.addText(roleDao.roleList().get(i).getROLEPARENTNO()+"");		
			    }
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0002");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("角色查询失败");				
				result=document2.asXML();
				return result;
			}
		}

		//设置角色表
		public String setRoleInfo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element roleInfo=root.element("roleInfo");
			Element roleNo=roleInfo.element("roleNo");
			Element roleName=roleInfo.element("roleName");
			Element roleParentNo=roleInfo.element("roleParentNo");
			
			
			RoleDao roleDao=new RoleDao();		
			if(roleDao.updateRole(roleNo.getText(), roleName.getText(), roleParentNo.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改角色表成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("修改角色表失败");				
				result=document2.asXML();
				return result;
			}
		}	
		
		//授权查询
		public String queryGrant(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element operation=root.element("operation");
			GrantDao grantDao=new GrantDao();
			if(grantDao.grantListCount()>0&&operation.getText().equals("角色授权查询")){
				int i;
				int count=grantDao.grantListCount();
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");
			  	for(i=0;i<count;i++){
				Element roleNo = root2.addElement("roleNo");    
				roleNo.addText(grantDao.grantList().get(i).getRoleNo()+"");							
				Element roleName = root2.addElement("roleName");     
				roleName.addText(grantDao.grantList().get(i).getPermissionNo()+"");  					
			    }
				result=document2.asXML();			  
				return result;
			}
			else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0002");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("角色授权查询失败");				
				result=document2.asXML();
				return result;
			}
		}

		//给角色授权
		public String setRoleGrant(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			Element grantInfo=root.element("grantInfo");
			Element roleNo=grantInfo.element("roleNo");
			Element permissonNo=grantInfo.element("permissonNo");	
			GrantDao grantDao=new GrantDao();	
			if(grantDao.updateGrant(roleNo.getText(),permissonNo.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("角色授权成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("角色授权失败");				
				result=document2.asXML();
				return result;
			}
		}

		//查询角色为A的用户
		public String queryRoleByUserNo(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			Document document2 = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			  } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			 }
			Element root = document.getRootElement();		
			Element roleNo=root.element("roleNo");
			UserDao userDao=new UserDao();
			if(userDao.queryRoleByUserNoListCount(roleNo.getText())==1){								
				document2 = DocumentHelper.createDocument();
			  	Element root2 = document2.addElement("data");			  	
				Element userId = root2.addElement("userId");    
				userId.addText(userDao.queryRoleByUserNo(roleNo.getText()).get(0).getId());							
				Element userName = root2.addElement("userName");     
				userName.addText(userDao.queryRoleByUserNo(roleNo.getText()).get(0).getName()); 
				Element userNo = root2.addElement("userNo");     
				userNo.addText(userDao.queryRoleByUserNo(roleNo.getText()).get(0).getNo()+""); 
				Element RoleNo = root2.addElement("RoleNo");     
				RoleNo.addText(roleNo.getText()); 			    
				result=document2.asXML();			  
				return result;
			}else{
				document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("查询角色下的用户信息失败");				
				result=document2.asXML();
				return result;
			}
         }
		
		//人员授权
		public String setRoleToUser(String xmlPara) throws Exception {
			String result=null;
	    	SAXReader reader = new SAXReader();
			Document document = null;
			try {
				//将xml信息解析为doucument对象
				document = reader.read(new ByteArrayInputStream(xmlPara.getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();		
			Element userName=root.element("userName");
			Element roleName=root.element("roleName");
			RoleToUserDao rtu=new RoleToUserDao();
			if(rtu.updateRoleToUser(userName.getText(), roleName.getText())==1){
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0000");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("人员授权成功");				
				result=document2.asXML();
				return result;
			}
			else{
				Document document2 = DocumentHelper.createDocument();
				Element root2 = document2.addElement("data");    
				Element rtnCode = root2.addElement("rtnCode");    
				rtnCode.addText("0004");							
				Element rtnMsg = root2.addElement("rtnMsg");     
				rtnMsg.addText("人员授权失败");				
				result=document2.asXML();
				return result;
			}
		}


			
}
