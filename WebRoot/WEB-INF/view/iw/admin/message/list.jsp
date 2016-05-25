<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../../../publicPage/adminCommon/head.jsp">
    	<jsp:param name="title" value="信息列表"/>
    </jsp:include>
    <script src="<%=basePath+Global.CACHE_FILE %>Message_state.js"></script>
    
    <script type="text/javascript">
    	//根据站内信id删除站内信
    	function deleteMessage(userId){
    		//要用ajax
    		window.location="<%=basePath %>/admin/message/delete.do?id="+userId;
    	}
    </script>
</head>
<body>

<section id="container" >
<jsp:include page="../../../publicPage/adminCommon/topHeader.jsp"></jsp:include>   
<aside>
    <div id="sidebar" class="nav-collapse">
		<jsp:include page="../../../publicPage/adminCommon/menu.jsp"></jsp:include>          
    </div>
</aside>
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
        <!-- page start-->

        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a data-toggle="tab" href="">站内信管理</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;">
                       <form method="get">
                       		<input type="hidden" name="orderBy" value="<%=request.getParameter("orderBy") %>" />
	                        <span style="float:left;line-height:34px;margin-left:10px;">发信用户ID：</span>
	                        <div class="input-group m-bot15 " style="width: 16%;float: left;"> 
	                            <input type="text" name="senderid" class="form-control" value="<%=request.getParameter("senderid")==null? "":request.getParameter("senderid")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">收信用户ID：</span>
	                        <div class="input-group m-bot15 " style="width: 16%;float: left;">
	                            <input type="text" name="recipientid" class="form-control" value="<%=request.getParameter("recipientid")==null? "":request.getParameter("recipientid")  %>">
	                        </div>
	                        <div class="input-group m-bot15 " style="width: 100px; float: left;">
	                            <span class="input-group-btn">
	                            <input class="btn btn-success" type="submit" value="搜索">
	                            <i class="fa fa-search"></i>
	                            </span>
	                        </div>
                        </form>     
                        
                    </div>
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
                                    <th class="numeric">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                
                                <c:forEach items="${list}" var="message">
                                	<tr>
	                                    <td>${message['id'] }</td>
	                                    <td><a href="?senderid=${message['senderid'] }">${message['self_nickname'] }(ID:${message['senderid'] })</a></td>
	                                    <td><a href="?recipientid=${message['recipientid'] }">${message['other_nickname'] }(ID:${message['recipientid'] })</a></td>
	                                    <td class="numeric">${message['content'] }</td>
	                                    <td class="numeric"><x:time linuxTime="${message['time'] }"></x:time></td>
	                                    <td class="numeric">
	                                    	<script type="text/javascript">document.write(state['${message['state'] }']);</script>
	                                    </td>
	                                    <td class="numeric">
	                                    	<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" href="" onclick="deleteMessage(${message['id'] });">
	                                    		<i class="fa fa-trash-o"></i>
	                                    	</button>
	                                    </td>
	                                </tr>
                                </c:forEach>
                               
                                </tbody>
                            </table>
                        </section>
                        <!-- 通用分页跳转 -->
                        <jsp:include page="../../../publicPage/adminCommon/page.jsp">
                        	<jsp:param name="page" value="${page }"/>
                        </jsp:include>
                    </div>
                </section>
                
            </div>
        </div>
        <!-- page end-->
        </section>
    </section>
    <!--main content end-->

</section>
<!--right sidebar start-->


<!--right sidebar end-->
<!-- Placed js at the end of the document so the pages load faster -->

<jsp:include page="../../../publicPage/adminCommon/footImport.jsp"></jsp:include>
</body>
</html>
