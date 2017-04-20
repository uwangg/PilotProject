<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.zum.db.MySQLConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="navbar-form navbar-right">
${sessionScope.authUser.name }님 환영합니다.
<a class="btn btn-primary" href="${pageContext.request.contextPath}/user?a=logout" style="padding:">로그아웃</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/user?a=modifyform" style="padding:">회원수정</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/user?a=withdrawalform" style="padding:">회원탈퇴</a>
</div>