<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common/head.jsp">
	<jsp:param name="title" value="阅读站内信"/>
</jsp:include>


		<hr/>
		发信者：${messageVO.senderUser.nickname }
		<br/>
		收信者：${messageVO.recipientUser.nickname }
		<br/>
		信息发送时间：${messageVO.message.time }
		<br/>
		信息内容：${messageVO.content}
		<br/>

<jsp:include page="../common/foot.jsp"></jsp:include> 