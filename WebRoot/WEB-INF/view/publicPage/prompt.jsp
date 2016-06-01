<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String redirectUrl = request.getAttribute("redirectUrl").toString();
if(redirectUrl.indexOf("http")>-1){
}else if(redirectUrl.indexOf("avascript")>-1){
}else{
	redirectUrl = basePath+redirectUrl;
}

request.setAttribute("success", Global.PROMPT_STATE_SUCCESS);
request.setAttribute("error", Global.PROMPT_STATE_ERROR);
%>
<!DOCTYPE HTML>
<html>
<head>
<body>
<h1 style="text-align: center;">
	<c:choose>
       <c:when test="${state == success}">
              成功
       </c:when>
       <c:when test="${state == error}">
              错误 
       </c:when>
       <c:otherwise>
              ??
       </c:otherwise>
	</c:choose>
</h1>
<h3 style="text-align: center;"><a href="<%=redirectUrl %>" style="color:blue;">${info }</a></h3>
</body>
</html>