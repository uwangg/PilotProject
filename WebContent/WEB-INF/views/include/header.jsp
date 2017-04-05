<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/main">Home</a>
		</div>

		<c:choose>
			<c:when test="${sessionScope.authUser == null }">
				<c:import url="/WEB-INF/views/user/loginform.jsp"></c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/WEB-INF/views/user/loginsuccess.jsp"></c:import>
			</c:otherwise>
		</c:choose>
		<%-- <c:import url="/WEB-INF/views/user/loginsuccess.jsp"></c:import> --%>
	</div>
</nav>