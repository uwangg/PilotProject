<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
.pagination>li>a {
	border: 1px solid #5cb85c;
	color: #5cb85c;
}
.pagination>li.active>a {
	border: 1px solid #5cb85c;
	background: #5cb85c;
	color: #fff;
}
</style>
<div class="container" style="margin-top:5%">
	<div style=" text-align: center;">
		<h1 class="title">게시판</h1>
	</div>
	<hr />

	<table class="table table-striped"
		style="border-collapse: separate; padding:3%">
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
				<td class="text-center">
					<%-- <a href="${pageContext.request.contextPath }/board?action=view&id=${vo.id}&currentPageNum=${currentPageNum }&begin=${begin}"> --%>
					<a href="${pageContext.request.contextPath }/board?action=view&id=${vo.id}">
							${vo.title }</a></td>
				<td class="text-center">${vo.userName}</td>
				<td class="text-center">${vo.createTime}</td>
				<td class="text-center">${vo.hit}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<hr />

	<c:choose>
		<c:when test="${sessionScope.authUser != null }">
			<a class="btn btn-success pull-right" href="${pageContext.request.contextPath}/board?action=writeform"
				style="padding:">글쓰기</a>
		</c:when>
	</c:choose>

	<div class="text-center">
		<ul class="pagination">
			<c:if test="${begin > 1 }">
				<li><a href="${pageContext.request.contextPath}/?begin=${begin-pageNumUnit}&currentPageNum=${currentPageNum-1}">&laquo;</a></li>
			</c:if>
			
			<c:forEach begin="${begin }" end="${end }" step="1" var="count" >
				<c:choose>
					<c:when test="${currentPageNum == count }">
						<li class="active"><a>${count }</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/?
								currentPageNum=${count}&begin=${begin}">${count }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${end < totalPageNum }">
				<li><a href="${pageContext.request.contextPath}/?begin=${begin+pageNumUnit}&currentPageNum=${begin+pageNumUnit}">&raquo;</a></li>
			</c:if>
		</ul>
	</div>
</div>
