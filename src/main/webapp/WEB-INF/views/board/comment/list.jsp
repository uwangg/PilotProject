<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
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

<label for="accordion">댓글:</label>
<div class="comment-group" id="accordion">

	<c:forEach items="${pagination.elemList }" var="commentEntity" varStatus="status">
		<c:choose>
			<c:when test="${commentEntity.deleteFlag == true }">
				<c:import url="/WEB-INF/views/board/comment/deleted_comment.jsp">
					<c:param name="depth" value="${commentEntity.depth }"/>
				</c:import>
			</c:when>
			<c:otherwise>
		<div class="panel comment-default"
			style="margin-left:${commentEntity.depth*20}px">
			<div class="comment-heading">
				<h4 class="comment-title">
				<c:if test="${commentEntity.depth>0 }"><span class="glyphicon glyphicon-hand-right"></span></c:if>
					${commentEntity.userName } <small>${commentEntity.createTime }</small> <small
						class="pull-right"> 
						
						<c:if test="${sessionScope.authUser != null }">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapse${commentEntity.id }"> 답글 </a>
						</c:if>
						<c:if test="${sessionScope.authUser.id == commentEntity.userId }">
						<a class="commentModify" id="${commentEntity.id }">수정</a>
						<a href="${pageContext.request.contextPath}/board/${postEntity.id}/commentdelete/${commentEntity.id}">삭제</a>
						
						</c:if>
					</small>
				</h4>
				<p class="commentContent" id="commentContent${commentEntity.id }">
					<c:if test="${commentEntity.depth>0 }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
					${commentEntity.content }</p>
				<div class="modifyForm" id="modifyForm${commentEntity.id }">
					<form role="form" style="margin: 10px" method="post" action="${pageContext.request.contextPath}/board/${postEntity.id}/commentmodify/${commentEntity.id}">
						<div class="form-group">
							<textarea rows="2" class="form-control" ng-model="user.comment" name="content">${commentEntity.content }</textarea>
						</div>
						<div>
							<button type="submit" class="btn btn-success btn-sm">수정완료</button>
						</div>
						<div class="clearfix"></div>
					</form>
				</div>
			</div>
			<div id="collapse${commentEntity.id }" class="comment-collapse collapse">
				<hr/>
				<div class="panel-body">
					<form role="form" method="post" action="${pageContext.request.contextPath}/board/${postEntity.id}/commentwrite">
					<input type="hidden" name="depth" value="${commentEntity.depth+1 }">
					<input type="hidden" name="thread" value="${commentEntity.thread-1 }">
						<div class="form-group">
							<label for="comment">답글:</label>
							<textarea rows="3" class="form-control" id="comment" name="content"
								placeholder="답글을 입력해주세요" ng-model="user.comment"></textarea>
						</div>
						<button type="submit" class="pull-right btn btn-success btn-sm">확인</button>
					</form>
				</div>
			</div>
		</div>
			</c:otherwise>
		</c:choose>

	</c:forEach>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<script>
	$(document).ready(function() {
		$(".modifyForm").hide();
		
		$(".commentModify").click(function() {
			var no = $(this).attr("id");
			var commentContent = "#commentContent" + no;
			var modifyForm = "#modifyForm" + no;
			$(commentContent).toggle();
			$(modifyForm).toggle();
		});
	});
</script>