<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="panel comment-default"
	style="margin-left:${param.depth*20}px">
	<div class="comment-heading">
		<h4 class="comment-title">
			<c:if test="${param.depth>0 }">
				<span class="glyphicon glyphicon-hand-right"></span>
			</c:if>
			삭제된 댓글입니다.
		</h4>
	</div>
</div>