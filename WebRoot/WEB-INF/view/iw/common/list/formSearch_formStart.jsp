<% /* 列表页面，顶部的搜索框，form开始标签相关内容，包含<form 以及 orderBy 的input标签 */ %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<form class="layui-form toubu_xnx3_search_form" action="" method="get">
	<input type="hidden" name="orderBy" value="<%=request.getParameter("orderBy") %>" />