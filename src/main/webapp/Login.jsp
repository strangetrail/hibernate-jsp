<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<form action = "<%=request.getContextPath()%>/login-user" method = "POST">
         Login: <input type="text" name="login_field"/>
         <br />
         Password: <input type="password" name="password_field"/>
         <input type = "submit" value = "Submit" />
	</form>
</body>
</html>