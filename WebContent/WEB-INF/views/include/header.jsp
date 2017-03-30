<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Home</a>
		</div>


		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<form id="signin" class="navbar-form navbar-right" role="form">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input id="email"
						type="email" class="form-control" name="email" value=""
						placeholder="Email Address">
				</div>

				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input id="password"
						type="password" class="form-control" name="password" value=""
						placeholder="Password">
				</div>

				<button type="submit" class="btn btn-primary">Login</button>
				<button type="submit" class="btn btn-primary">Register</button>
			</form>

		</div>
	</div>
</nav>