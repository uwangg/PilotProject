<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
.error {
	color: red;
}
</style>
<title>Insert title here</title>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- Website Font style -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
</head>
<body>

	<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
<!-- 	<div style="width:400px;margin:auto;margin-top:100px;
				border:1px solid;border-radius:10px;
				border-color:#dcdcdc;padding:60px;padding-top:40px" 
		class="container"> -->
	<div class="container">
	<div class="row">
	<div class="col-lg-4"></div>
	<div class="col-lg-4" style="margin-top: 6%; border:1px solid;border-radius:10px;
				border-color:#dcdcdc;padding:2%">
			<div class="main" style="padding:6%">
				<div class="panel-heading">
	               <div class="panel-title text-center">
	               		<h1 class="title">회원가입</h1>
	               		<hr />
	               	</div>
	            </div> 
				<div class="main-login main-center">
					<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/user/join" id="joinForm">
						<!-- 닉네임 폼 -->
						<div class="form-group">
							<label for="name" class="cols-sm-2 control-label">닉네임</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
									<input type="text" class="form-control" name="name" id="name"  placeholder="김은진" value="${userVo.name}"/>
								</div>
							</div>
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label for="email" class="cols-sm-2 control-label">이메일</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
									<input type="email" class="form-control" name="email" id="email"  placeholder="example@example.com" value="${userVo.email}"/>
								</div>
							</div>
						</div>

						<!-- 패스워드 폼 -->
						<div class="form-group">
							<label for="passwd" class="cols-sm-2 control-label">패스워드</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
									<input type="password" class="form-control" name="password" id="passwd"  placeholder="6자리이상"/>
								</div>
							</div>
						</div>

						<!-- 패스워드 확인 폼 -->
						<div class="form-group" style="margin-bottom:40px;">
							<label for="confirm" class="cols-sm-2 control-label">패스워드 확인</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
									<input type="password" class="form-control" name="confirm" id="confirm"  placeholder="비밀번호확인"/>
								</div>
							</div>
						</div>

						<div class="form-group ">
							<button type="submit" class="btn btn-success btn-lg btn-block login-button">등록하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-lg-4"></div>
		</div>
		</div>

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<!-- validate -->
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$('#joinForm').validate({
		// validation이 끝난 이후 submit 직전할 것
 		submitHandler: function() {
			var f = confirm("회원가입을 완료하시겠습니까?");
			if(f) {
				return true;
			} else {
				return false;
			}
		},
		// 규칙
		rules: {
//			name: {
//				required: true,
//				remote: "user2?action=checkname"
//			},
//			email: {
//				required: true,
//				email: true,
//				remote: "user2?action=checkemail"
//			},
			password: {
				required: true,
				minlength: 6
			},
			confirm: {
			    equalTo: "#passwd",
				required: true,
				minlength: 6
//				equalTo: "#passwd"
			}
		},
		// 규칙 실패시 출력될 메세지
		messages: {
//			name: {
//				required: "필수 입력사항 입니다",
//				remote: "존재하는 닉네임 입니다"
//			},
//			email: {
//				required: "필수 입력사항 입니다",
//				email: "이메일 규칙에 어긋납니다",
//				remote: "존재하는 이메일 입니다"
//			},
			password: {
				required: "필수 입력사항 입니다",
				minlength: "최소 {0}글자 이상이어야 합니다"
			},
			confirm: {
                equalTo: "비밀번호가 일치하지 않습니다",
				required: "필수 입력사항 입니다",
				minlength: "최소 {0}글자 이상이어야 합니다"
			}
		}
/* 		valid: function(form) {
			form.submit();
		} */
	});
});
</script>
</body>
</html>