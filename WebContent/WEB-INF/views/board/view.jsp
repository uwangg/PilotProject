<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("crcn", "\r\n");
	pageContext.setAttribute("br", "<br/>");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<style>
@import url(http://fonts.googleapis.com/earlyaccess/hanna.css);

h1 {
	font-family: 'Hanna';
}
</style>
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-lg-2"></div>
			<!-- Blog Post Content Column -->
			<div class="col-lg-8">

				<!-- 글 제목 -->
				<h1>${vo.title }</h1>

				<!-- 작성자 -->
				<p class="lead">
				<h4>by. ${vo.user_name }</h4>
				</p>

				<hr>

				<!-- Date/Time -->
				<p>
					<span class="glyphicon glyphicon-time"></span> Posted on ${vo.create_time }
				</p>

				<hr>

				<!-- 이미지 -->
				<c:choose>
					<c:when test="${vo.image_path != null }">
						<img class="img-responsive" src="upload/${vo.image_path }"></img>
					</c:when>
					<c:otherwise>
						<img class="img-responsive" src="http://placehold.it/900x300" alt="">
					</c:otherwise>
				</c:choose>

				<hr>

				<!-- 글 내용 -->
				<p class="lead">내용</p>
				<p>${fn:replace(vo.content, crcn, br) }</p>

				<hr>


				<c:import url="/WEB-INF/views/board/comment/writeform.jsp"></c:import>

				<hr>

				<c:import url="/WEB-INF/views/board/comment/list.jsp"></c:import>

				<!-- 페이지네이션 -->
				<div class="text-center">
					<ul class="pagination">
						<li><a href="#">&laquo;</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li class="active"><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&raquo;</a></li>
					</ul>
				</div>
				<hr>

				<!-- 수정 / 삭제 -->
				<div class="text-right">
					<a class="btn btn-primary pull-left" href="${pageContext.request.contextPath}/main" style="padding:">목록보기</a>
					
					<c:choose>
						<c:when test="${sessionScope.authUser.id == vo.user_id }">
							<a class="btn btn-primary" href="${pageContext.request.contextPath}/board?a=modifyform&id=${vo.id }" style="padding:">수정하기</a>
							<a class="btn btn-primary" href="${pageContext.request.contextPath}/board?a=delete&id=${vo.id }&user_id=${vo.user_id}" style="padding:">삭제하기</a>
						</c:when>
					</c:choose>
				</div>
			
				<div style="margin-bottom: 100px;"></div>
			</div>
		</div>



	</div>
	<!-- /.container -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>