<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<!-- validate -->
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#writeForm').validate({
		// 규칙
		rules: {
			title: {
				required: true,
			}
		},
		// 규칙 실패시 출력될 메세지
		messages: {
			title: {
				required: "제목을 입력해주세요"
			}
		}
	});
});
</script>
</head>	
<body>
	<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
	
	<form action="/pilot-project/board?a=write" method="post" enctype="multipart/form-data" id="writeForm"
		style="width: 650px; margin: auto; margin-top: 80px; border: 1px double; border-radius: 10px; border-color: #dcdcdc; padding: 30px;">
<!-- 		<input type="hidden" name="a" value="write"> -->
		<div class="panel-title text-center" style="padding: 10px;">
			<h2 class="title">글쓰기</h2>
			<hr />
		</div>

		<!-- 제목 입력 -->
		<div class="form-group">
			<label for="exampleTextarea">제목</label> 
			<input class="form-control" type="text" id="title" name="title">
		</div>
		
		<!-- 내용 입력 -->
		<div class="form-group">
			<label for="exampleTextarea">내용</label>
			<textarea class="form-control" id="content" rows="15" name="content"></textarea>
		</div>
		
		<!-- 이미지 첨부 -->
		<div class="form-group">
			<label for="exampleInputFile">이미지 첨부</label> 
			<input type="file" class="form-control-file" id="exampleInputFile"
				aria-describedby="fileHelp" name="image_path"> 
				<small id="fileHelp" class="form-text text-muted">This is some
				placeholder block-level help text for the above input. It's a bit
				lighter and easily wraps to a new line.</small>
		</div>

		<div class="text-center">
			<button type="submit" class="btn btn-primary"
				style="padding-left: 30px; padding-right: 30px; margin: 5px">확인</button>
			<a href="/pilot-project/main" class="btn btn-primary"
				style="padding-left: 30px; padding-right: 30px; margin: 5px">취소</a>
		</div>
	</form>
</body>
</html>