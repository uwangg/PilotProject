<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<script>
	$(document).ready(function() {
		$("#modify_form").hide();
		$("#comment_modify").click(function() {
			$("#comment_content").toggle();
			$("#modify_form").toggle();
		});
	});
</script>

<label for="accordion">댓글:</label>
<div class="comment-group" id="accordion">
  <div class="panel comment-default">
    <div class="comment-heading">
      <h4 class="comment-title">
      	작성자
      	<small>August 25, 2014 at 9:30 PM</small>
      	<small class="pull-right">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
          	답글
        </a>
        <a id="comment_modify">수정</a>
        <a href="#">삭제</a>
        </small>
      </h4>
      <p id="comment_content">
      	여기는 내용입니다.<br/>댓글입니다.<br/>길게길게길게길게길게길게길게 길게길게길게길게길게길게길게길게길게길게길게길게길게길게길게
      </p>
      <div id="modify_form">
      <form role="form" style="margin:10px">
      	<div class="form-group">

      	<textarea rows="2" class="form-control" id="comment_modify" ng-model="user.comment"></textarea>
      	</div>
      	<div>
      	<button type="submit" class="btn btn-default btn-sm">수정완료</button>
      	</div>
		<div class="clearfix"></div>
		</form>
		</div>
    </div>
    <div id="collapse1" class="comment-collapse collapse">
      <div class="panel-body">
        <form role="form">
				<div class="form-group">
				<label for="comment">답글:</label>
				<textarea rows="3" class="form-control" id="comment"
							placeholder="답글을 입력해주세요" ng-model="user.comment"></textarea>
				</div>
				<button type="submit" class="pull-right btn btn-default btn-sm">확인</button>
			</form>
      </div>
    </div>
  </div>
  
</div>