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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<!-- validate -->
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#withdrawalForm').validate({
		// validation이 끝난 이후 submit 직전할 것
 		submitHandler: function() {
			var f = confirm("회원탈퇴를 하시겠습니까?");
			if(f) {
				return true;
			} else {
				return false;
			}
		},
		// 규칙
		rules: {
			passwd: {
				required: true,
			}
		},
		// 규칙 실패시 출력될 메세지
		messages: {
			passwd: {
				required: "필수입력사항입니다",
			}
		}
	});
});
</script>
</head>
<body>
	<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
	<div style="width:400px;margin:auto;margin-top:100px;
				border:1px solid;border-radius:10px;
				border-color:#dcdcdc;padding:60px;padding-top:40px" 
		class="container">
			<div class="row main">
				<div class="panel-heading">
	               <div class="panel-title text-center">
	               		<h1 class="title">회원탈퇴</h1>
	               		<hr />
	               	</div>
	            </div> 
				<div class="main-login main-center">
					<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/user" id="withdrawalForm">
						<input type="hidden" name="a" value="withdrawal">

						<!-- 패스워드 폼 -->
						<div class="form-group">
							<label for="password" class="cols-sm-2 control-label">패스워드</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
									<input type="password" class="form-control" name="passwd" id="passwd"  placeholder="패스워드를입력해주세요"/>
								</div>
							</div>
						</div>

						<div class="form-group ">
							<button type="submit" class="btn btn-success btn-lg btn-block login-button">회원탈퇴하기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>