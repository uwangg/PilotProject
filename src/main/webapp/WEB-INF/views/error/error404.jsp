<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setStatus(HttpServletResponse.SC_OK);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404 에러</title>
</head>
<body>
<strong>요청한 페이지는 존재하지 않습니다.</strong>
<br><br>
주소를 올바르게 입력했는지 확인하시길 바랍니다.
<br><br>
<a href="${pageContext.request.contextPath}/">홈으로 가기</a>
</body>
</html>