<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="navbar-form navbar-right">
<a href="${pageContext.request.contextPath}/user/modify" style="font-size:30">${sessionScope.authUser.name }</a>님 환영합니다.
<a class="btn btn-success" href="${pageContext.request.contextPath}/user/logout">로그아웃</a>
<%-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/user?action=modifyform">회원수정</a> --%>
<a class="btn btn-success" href="${pageContext.request.contextPath}/user/withdrawal">회원탈퇴</a>
</div>