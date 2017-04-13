<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 댓글란 -->

<!-- 댓글 폼 -->
<div class="commentform-group">
	<div class="commentform-default">
	<form role="form" method="post" action="${pageContext.request.contextPath}/board">
		<input type="hidden" name="a" value="commentwrite">
		<input type="hidden" name="a" value="${postVo.id }">
		<input type="hidden" name="depth" value="0">
		<div class="form-group">
			<label for="comment">댓글입력란:</label>
			<textarea class="form-control" rows="3" id="comment" name="content"
				placeholder="댓글을 입력해주세요"></textarea>
		</div>
			<button type="submit" class="btn btn-primary pull-right">확인</button>
		<div class="clearfix"></div>
	</form>
	</div>
</div>