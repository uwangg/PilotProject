<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setStatus(HttpServletResponse.SC_OK);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>500 에러 페이지</title>
</head>
<body>
<strong>서비스 과정에서 에러가 발생했습니다.</strong>
<br><br>
<a href="${pageContext.request.contextPath}/">홈으로 가기</a>
</body>
</html>