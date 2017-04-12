<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- <link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script> -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- <div class="media-group" id="accordion">
	<div class="media-default">
		<div style="margin-bottom:30px">
		<h4 class="media-heading">
			Start Bootstrap <small>August 25, 2014 at 9:30 PM</small> <small
				class="pull-right"> <a data-toggle="collapse"
				data-parent="#accordion" href="#collapseOne"
				style="margin-right: 10px">답글</a> <a href="#"
				style="margin-right: 10px">수정</a> <a href="#"
				style="margin-right: 20px">삭제</a>
			</small>
		</h4>
		<div class="media-body">최신댓글입니다.</div>
		</div>
		<hr/>
		<div id="collapseOne" class="media-collapse collpase in" style="margin-left: 20px; margin-bottom:30px">
			<div class="media-body">
			<div class="col">
				<div class="form-group">
					<label for="comment">답글:</label>
					<textarea rows="3" class="form-control" id="comment"
						placeholder="Enter comment" ng-model="user.comment"></textarea>
				</div>
				<div class="pull-right btn btn-default btn-sm"><a href="#">확인</a></div>
			</div>
			</div>
		</div>
	</div>
</div> -->


<div class="accordion" id="comment-accordion">
	<div class="accordian-group">
	<div class="accordion-heading" style="margin-bottom: 25px">
		<hr/>
		<h4>Start Bootstrap 
			<small>August 25, 2014 at 9:30 PM</small> 
			<small class="pull-right"> 
				<a class="accordion-togle accordion-toggle-styled collapsed" data-toggle="collapse" data-parent="#comment-accordion" href="#collapseOne"
				style="margin-right: 5px">답글</a> 
				<a href="#" style="margin-right: 5px">수정</a> 
				<a href="#" style="margin-right: 20px">삭제</a>
			</small>
		</h4>
			최신댓글입니다.
		<hr />
	</div>
	<div class="accordion-collapse collpase in" id="collapseOne" 
			style="margin-left: 20px; margin-bottom: 60px; margin-top:-10px">
			<div class="accordion-body">
			<form role="form">
				<div class="form-group">
				<label for="comment">답글:</label>
				<textarea rows="3" class="form-control" id="comment"
							placeholder="Enter comment" ng-model="user.comment"></textarea>
				</div>
				<button type="submit" class="pull-right btn btn-default btn-sm">확인</button>
			</form>
			</div>
	</div>
	</div>
	<div class="comment-default" style="margin-bottom: 25px">
		<h4>Start Bootstrap 
			<small>August 25, 2014 at 9:30 PM</small> 
			<small class="pull-right"> 
				<a class="accordion-togle" href="#collapse2" data-toggle="collapse" data-parent="#comment-accordion"
				style="margin-right: 5px">답글</a> 
				<a href="#" style="margin-right: 5px">수정</a> 
				<a href="#" style="margin-right: 20px">삭제</a>
			</small>
		</h4>
			최신댓글입니다.
	</div>
	<hr />
	<div class="comment-collapse collpase" id="collapse2"
			style="margin-left: 20px; margin-bottom: 60px; margin-top:-10px">
			<form role="form">
				<div class="form-group">
				<label for="comment">답글:</label>
				<textarea rows="3" class="form-control" id="comment"
							placeholder="Enter comment" ng-model="user.comment"></textarea>
				</div>
				<button type="submit" class="pull-right btn btn-default btn-sm">확인</button>
			</form>
	</div>
</div>
