<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	<form id="signin" class="navbar-form navbar-right" role="form"
		action="${pageContext.request.contextPath}/user">
		<input type="hidden" name="action" value="login">
		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-user"></i></span> <input id="email" type="email"
				class="form-control" name="email" value=""
				placeholder="Email Address">
		</div>

		<div class="input-group">
			<span class="input-group-addon"><i
				class="glyphicon glyphicon-lock"></i></span> <input id="password"
				type="password" class="form-control" name="password" value=""
				placeholder="Password">
		</div>

		<button type="submit" class="btn btn-success">로그인</button>
		<a class="btn btn-success"
			href="${pageContext.request.contextPath}/user/joinform">회원가입</a>
	</form>

</div>
