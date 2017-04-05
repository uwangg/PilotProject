<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.zum.db.MySQLConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 		<div class="collapse navbar-collapse"
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

				<button type="submit" class="btn btn-primary">로그인</button>
				<button type="submit" class="btn btn-primary">회원가입</button>
			</form>

		</div> -->
<div class="navbar-form navbar-right">
<%-- <%!
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/pilot";
	String query = "select * from user";
%>
<% 
	try {
		// 1단계 - jdbc 드라이버 Load
		Class.forName("com.mysql.jdbc.Driver");
		
		connection = DriverManager.getConnection(url, "pilot", "pilot");
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
	} catch (ClassNotFoundException e) {
		System.out.println("드라이버를 찾을 수 없습니다." + e);
	} finally {
		try{
			if(resultSet != null) resultSet.close();
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		} catch(Exception e2){
			e2.printStackTrace();
		}
	}
%>
<%=resultSet.getString("name") %>님 환영합니다~ --%>
${sessionScope.authUser.name }님 환영합니다.
<a class="btn btn-primary" href="writeform.jsp" style="padding:">로그아웃</a>
<a class="btn btn-primary" href="writeform.jsp" style="padding:">회원수정</a>
<a class="btn btn-primary" href="writeform.jsp" style="padding:">회원탈퇴</a>
</div>