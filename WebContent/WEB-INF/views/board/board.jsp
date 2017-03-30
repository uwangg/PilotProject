<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="width:1400px;margin:auto;margin-top:80px">
	<div style="margin-bottom:30px;text-align:center;">
	<h2>게시판</h2>
	</div>
	<hr/>

<table class="table table-striped" 
		style="margin-top:50px;margin-bottom:50px;
				width:1000px;margin-left:auto;margin-right:auto">
	<thead>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>날짜</th>
		<th>조회수</th>
	</tr>
	</thead>
	<tbody>
	<%
	for(int i=10 ; i>0 ; i--) {
	%>
		<tr>
		<td><%=i %></td>
		<td>안녕하세요<%=i+1 %></td>
		<td>유저<%=i+1 %></td>
		<td>2017.1.1</td>
		<td><%=i*10 %></td>
	</tr>
	<%
		}
	%>
	</tbody>
</table>
<hr/>

<a class="btn btn-default pull-right" href="writeform.jsp">글쓰기</a>

<div class="text-center">
	<ul class="pagination">
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li><a href="#">3</a></li>
		<li><a href="#">4</a></li>
		<li><a href="#">5</a></li>
	</ul>
</div>
</div>
