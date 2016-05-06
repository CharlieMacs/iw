<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    	<jsp:include page="common/head.jsp">
    		<jsp:param name="title" value="无权访问"/>
    	</jsp:include>  
  </head>
  
  <body>
   无权访问！
    <br/>
    <a href="<%=basePath %>login.do" style="color:blue">登陆</a>
    <br/>
    <a href="<%=basePath %>user/info.do" style="color:blue">个人中心</a>
  </body>
</html>
