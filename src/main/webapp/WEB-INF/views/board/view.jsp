<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("crcn", "\r\n");
	pageContext.setAttribute("br", "<br/>");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<style>
@import url(http://fonts.googleapis.com/earlyaccess/hanna.css);

h1 {
	font-family: 'Hanna';
}
</style>
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
				<h1>${postVo.title }</h1>

				<!-- 작성자 -->
				<p class="lead">
				<h4>by. ${postVo.userName }</h4>
				</p>

				<hr>

				<!-- Date/Time -->
				<p>
					<span class="glyphicon glyphicon-time"></span> Posted on ${postVo.createTime }
				</p>


				<!-- 이미지 -->
				<c:if test="${postVo.imagePath != null && postVo.imagePath != \"\"}">
					<hr>
					<img class="img-responsive" src="${pageContext.request.contextPath }/upload/${postVo.imagePath }"/>
				</c:if>
<%-- 				<c:choose>
					<c:when test="${postVo.imagePath != null && postVo.imagePath != \"\"}">
						<img class="img-responsive" src="upload/${postVo.imagePath }"></img>
					</c:when>
					<c:otherwise>
						<img class="img-responsive" src="http://placehold.it/900x300" alt="">
					</c:otherwise>
				</c:choose> --%>


				<!-- 글 내용 -->
				<hr>
 				<c:if test="${postVo.content != \"\"}">
					<label for="content">내용</label>
					<p id="content"><BIG>${fn:replace(postVo.content, crcn, br) }</BIG></p>
				</c:if>
				<%-- <label for="content">내용</label>
				<c:choose>
					<c:when test="${postVo.content == \"\" }">
						<p><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/></p>
					</c:when>
					<c:otherwise>
						<p id="content"><BIG>${fn:replace(postVo.content, crcn, br) }</BIG></p>
					</c:otherwise>
				</c:choose> --%>

				<hr>
				
				<c:if test="${sessionScope.authUser != null }">
				<!-- 댓글 작성폼 -->
				<c:import url="/WEB-INF/views/board/comment/write.jsp"></c:import>
				</c:if>

				<c:import url="/WEB-INF/views/board/comment/list.jsp"></c:import>

				<!-- 페이지네이션 -->
				<div class="text-center">
						<ul class="pagination">
							<c:if test="${pagination.begin > 1 }">
								<li><a href="${pageContext.request.contextPath}/board/${postVo.id }?currentPage=${pagination.begin - 1}">&laquo;</a></li>
							</c:if>
							
							<c:forEach begin="${pagination.begin }" end="${pagination.end }" step="1" var="count" >
								<c:choose>
									<c:when test="${pagination.currentPage == count }">
										<li class="active"><a>${count }</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath}/board/${postVo.id }
												?currentPage=${count}">${count }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagination.isEndPage eq 'false'}">
								<li><a href="${pageContext.request.contextPath}/board/${postVo.id }?currentPage=${pagination.end + 1}">&raquo;</a></li>
							</c:if>
						</ul>
					</div>

				<hr>

				<!-- 수정 / 삭제 -->
				<div class="text-right">
					<a class="btn btn-success pull-left" href="${pageContext.request.contextPath}/" style="padding:">목록보기</a>
					<c:choose>
						<c:when test="${sessionScope.authUser.id == postVo.userId }">
							<a class="btn btn-success" href="${pageContext.request.contextPath}/board/${postVo.id }/modify">수정하기</a>
							<a class="btn btn-success" href="${pageContext.request.contextPath}/board/${postVo.id}/delete">삭제하기</a>
						</c:when>
					</c:choose>
				</div>
			
				<div style="margin-bottom: 100px;"></div>
			</div>
		</div>
	</div>
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</body>
</html>