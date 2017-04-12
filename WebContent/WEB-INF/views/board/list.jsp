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
				<td class="text-center"><a href="${pageContext.request.contextPath }/board?a=view&id=${vo.id}">${vo.title }</a></td>
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
			<a class="btn btn-primary pull-right" href="${pageContext.request.contextPath}/board?a=writeform"
				style="padding:">글쓰기</a>
		</c:when>
	</c:choose>

	<div class="text-center">
		<ul class="pagination">
			<c:if test="${begin > 1 }">
				<li><a href="${pageContext.request.contextPath}/main?begin=${begin-pageNumUnit}&currentPageNum=${currentPageNum-1}">&laquo;</a></li>
			</c:if>
			
			<c:forEach begin="${begin }" end="${end }" step="1" var="count" >
				<c:choose>
					<c:when test="${currentPageNum == count }">
						<li class="active"><a>${count }</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/main?
								currentPageNum=${count}&begin=${begin}">${count }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
<!-- 			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li class="active"><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li> -->
			<c:if test="${end < totalPageNum }">
				<li><a href="${pageContext.request.contextPath}/main?begin=${begin+pageNumUnit}&currentPageNum=${begin+pageNumUnit}">&raquo;</a></li>
			</c:if>
		</ul>
	</div>
</div>
