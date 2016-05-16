<%@page import="com.xnx3.j2ee.Global"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.xnx3.com/java_xnx3/xnx3_tld" prefix="x" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("type", request.getParameter("type")==null? "":request.getParameter("type"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
   	<jsp:include page="../common/head.jsp">
    	<jsp:param name="title" value="验证码历史列表"/>
    </jsp:include>
    <script src="<%=basePath+Global.CACHE_FILE %>SmsLog_used.js"></script>
</head>

<body>

<section id="container" >
<!--header start-->
<jsp:include page="../common/topHeader.jsp"></jsp:include>     
<!--header end-->
<aside>
    <div id="sidebar" class="nav-collapse">
        <!-- sidebar menu start-->
        	<jsp:include page="../common/menu.jsp"></jsp:include>   
		<!-- sidebar menu end-->
    </div>
</aside>
<!--sidebar end-->
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
        <!-- page start-->

        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading">
                        短信验证码管理
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;">
                       <form method="get">
	                        <span style="float:left;line-height:34px;margin-left:10px;">会员ID：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="userid" class="form-control" value="<%=request.getParameter("userid")==null? "":request.getParameter("userid")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">接收手机号：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;">
	                            <input type="text" name="phone" class="form-control" value="<%=request.getParameter("phone")==null? "":request.getParameter("phone")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">使用状态：</span>
	                        <div class="input-group m-bot15 " style="width: 20%;float: left;"> 
	                        	<select name="used" class="form-control">
	                        		<script type="text/javascript">writeSelectAllOptionForused(<%=request.getParameter("used") %>);</script>
	                        	</select>
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
                                    <th>接收手机号</th>
                                    <th class="numeric">验证码</th>
                                    <th class="numeric">是否使用</th>
                                    <th class="numeric">发送时间</th>
                                </tr>
                                </thead>
                                <tbody>
	                                <c:forEach items="${list}" var="smslog">
	                                	<tr>
		                                    <td>${smslog['id'] }</td>
		                                    <td>${smslog['phone'] }</td>
		                                    <td>${smslog['code'] }</td>
		                                    <td><script type="text/javascript">document.write(used[${smslog['used']}]);</script></td>
		                                    <td>
		                                    	<x:time linuxTime="${smslog['addtime'] }"></x:time>
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
<jsp:include page="../common/footImport.jsp"></jsp:include>  

</body>
</html>

