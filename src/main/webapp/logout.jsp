<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="mainstyles.css">
<title>Insert title here</title>
</head>
<body>
<div>A you sure you want to logout?</div>
<form class="inliner" action="<%= request.getContextPath()%>/redirect-index.jsp">
	<input type="submit" value="No">
</form>
<form class="inliner" action = "<%= request.getContextPath()%>/logout-user" method="POST">
	<input type="submit" value="Yes"/>
</form>
</body>
</html>