<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="width: 1400px; margin: auto; margin-top: 80px">
	<div style="margin-bottom: 30px; text-align: center;">
		<h1 class="title">게시판</h1>
	</div>
	<hr />

	<table class="table table-striped"
		style="margin-top: 50px; margin-bottom: 50px; width: 1000px; margin-left: auto; margin-right: auto">
		<thead>
			<tr>
				<th class="text-center" style="width:7%">번호</th>
				<th class="text-center" style="width:56%">제목</th>
				<th class="text-center" style="width:12%">작성자</th>
				<th class="text-center" style="width:18%">날짜</th>
				<th class="text-center" style="width:7%">조회수</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${postList }" var="vo" varStatus="status">
			<tr>
				<td class="text-center">${vo.id}</td>
				<td class="text-center">${vo.title}</td>
				<td class="text-center">${vo.user_name}</td>
				<td class="text-center">${vo.create_time}</td>
				<td class="text-center">${vo.hit}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<hr />

	<c:choose>
		<c:when test="${sessionScope.authUser != null }">
			<a class="btn btn-primary pull-right" href="writeform.jsp"
				style="padding:">글쓰기</a>
		</c:when>
	</c:choose>

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
</div>
