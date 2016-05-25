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
    	<jsp:param name="title" value="支付日志列表"/>
    </jsp:include>
    <script src="<%=basePath+Global.CACHE_FILE %>PayLog_channel.js"></script>
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
        <div class="row">
            <div class="col-sm-12">
                <section class="panel">
                    <header class="panel-heading">
                        支付日志管理
                    </header>
                    <div class="panel-body">
                    <div class="space15"></div>
                    <div class="col-xs-12" style="padding:0;">
                       <form method="get">
	                        <span style="float:left;line-height:34px;margin-left:10px;">会员ID：</span>
	                        <div class="input-group m-bot15 " style="width: 16%;float: left;">
	                            <input type="text" name="userid" class="form-control" value="<%=request.getParameter("userid")==null? "":request.getParameter("userid")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">订单号：</span>
	                        <div class="input-group m-bot15 " style="width: 16%;float: left;">
	                            <input type="text" name="orderno" class="form-control" value="<%=request.getParameter("orderno")==null? "":request.getParameter("orderno")  %>">
	                        </div>
	                        <span style="float:left;line-height:34px;margin-left:10px;">支付方式：</span>
	                        <div class="input-group m-bot15 " style="width: 16%;float: left;"> 
	                        	<select name="channel" class="form-control">
	                        		<script type="text/javascript">writeSelectAllOptionForchannel('<%=request.getParameter("channel") %>');</script>
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
                                    <th>用户昵称</th>
                                    <th class="numeric">支付金额</th>
                                    <th class="numeric">订单号</th>
                                    <th class="numeric">支付方式</th>
                                    <th class="numeric">操作时间</th>
                                </tr>
                                </thead>
                                <tbody>
	                                <c:forEach items="${list}" var="payLog">
	                                	<tr>
		                                    <td>${payLog['id'] }</td>
		                                    <td>${payLog['nickname'] }(ID:${payLog['userid'] })</td>
		                                    <td>${payLog['money'] }</td>
		                                    <td>${payLog['orderno'] }</td>
		                                    <td><script type="text/javascript">document.write(channel['${payLog['channel']}']);</script></td>
		                                    <td>
		                                    	<x:time linuxTime="${payLog['addtime'] }"></x:time>
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
        </section>
    </section>
    <!--main content end-->
</section>

<jsp:include page="../../../publicPage/adminCommon/footImport.jsp"></jsp:include>  
</body>
</html>

