<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'setUserInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <form action="setUserInfo" method="post">
  		<p><label>用户编号:</label><input type="text" name="userNo"></p>
  		<p><label>用户名:</label><input type="text" name="userId"></p>
  		<p><label>用户名称:</label><input type="text" name="userName"></p>
  		<p><label>所属部门:</label><input type="text" name="userDepartNo"></p>
		<input type="submit" value="测试"/>
  	</form>
  </body>
</html>
