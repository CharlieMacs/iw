<%@page import="com.xnx3.j2ee.util.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
	<div class="col-lg-12">
		<div class="dataTables_paginate paging_bootstrap pagination">
			<ul>
				<li style="margin-right:30px;border:0px; padding-top:5px;">共${page.allRecordNumber }条，${page.currentPageNumber }/${page.lastPageNumber }页</li>
				<c:if test="${!page.currentFirstPage}">
					<li><a href="${page.firstPage }">首页</a></li>
					<li><a href="${page.upPage }">上一页</a></li>
				</c:if>
				<c:forEach items="${page.upList}" var="a">
					<li><a href="${a.href }">${a.title }</a></li>
				</c:forEach>
				
				<li class="active"><a href="#">${page.currentPageNumber }</a></li>
				
				<c:forEach items="${page.nextList}" var="a">
					<li><a href="${a.href }">${a.title }</a></li>
				</c:forEach>
				<c:if test="${!page.currentLastPage}">
					<li><a href="${page.nextPage }">下一页</a></li>
					<li><a href="${page.lastPage }">尾页</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>