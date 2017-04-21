<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="navbar-form navbar-right">
${sessionScope.authUser.name }님 환영합니다.
<a class="btn btn-primary" href="${pageContext.request.contextPath}/user?a=logout">로그아웃</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/user?a=modifyform">회원수정</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/user?a=withdrawalform">회원탈퇴</a>
</div>