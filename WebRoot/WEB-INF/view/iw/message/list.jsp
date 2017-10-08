<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="../common/head.jsp">
    	<jsp:param name="title" value="站内信列表"/>
    </jsp:include>
<script src="<%=basePath+Global.CACHE_FILE %>Message_state.js"></script>
</head>
<body>
<article id="container">
	<section id="main">
		<div><a href="<%=basePath %>/message/add.do"  style="color:blue;">新建信息</a></div>
		<div><a href="<%=basePath %>/message/list.do?state=0&box=inbox"  style="color:blue;">收件箱未读消息</a>，<a href="<%=basePath %>/message/list.do?state=1&box=inbox"  style="color:blue;">收件箱已读消息</a>，<a href="<%=basePath %>/message/list.do?state=0&box=outbox"  style="color:blue;">发件箱未读消息</a>，<a href="<%=basePath %>/message/list.do?state=1&box=outbox"  style="color:blue;">发件箱已读消息</a></div>
		<div>信息列表：</div>
		
		<section id="unseen">
	        <table class="table table-bordered table-striped table-condensed">
	            <thead>
	            <tr>
	                <th>编号</th>
	                <th>发信用户</th>
	                <th class="numeric">收信用户</th>
	                <th class="numeric">信息内容</th>
	                <th class="numeric">发信时间</th>
	                <th class="numeric">信息状态</th>
	            </tr>
	            </thead>
	            <tbody>
	            
	            <c:forEach items="${list}" var="message">
	            	<tr onclick="window.location.href='view.do?id=${message.id }';" style="cursor: pointer;">
		                 <td>${message['id'] }</td>
		                 <td>${message['self_nickname'] }(ID:${message['sender'] })</td>
		                 <td>${message['other_nickname'] }(ID:${message['recipientid'] })</td>
		                 <td class="numeric">${message['content'] }</td>
		                 <td class="numeric"><x:time linuxTime="${message['time'] }"></x:time></td>
		                 <td class="numeric">
		                 	<script type="text/javascript">document.write(state['${message['state'] }']);</script>
		                 </td>
		             </tr>
	            </c:forEach>
	           
	            </tbody>
	        </table>
	    </section>
	    <!-- 通用分页跳转 -->
	    <jsp:include page="../common/page.jsp">
	    	<jsp:param name="page" value="${page }"/>
	    </jsp:include>
		
	</section>
	<footer id="footer"></footer>
</article>
</body>
</html>