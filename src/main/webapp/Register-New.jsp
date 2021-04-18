<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
	<form action = "<%=request.getContextPath()%>/register" method = "POST">
         Login: <input type="text" name="login_field"/>
         <br />
         E-mail: <input type="text" name="email_field"/>
         <br />
         First Name: <input type = "text" name = "first_name">
         <br />
         Last Name: <input type = "text" name = "last_name" />
         <br />
         Password: <input type="password" name="pass1" id="pass1"/>
         <br />
         Repeat password: <input type="password" name="pass2" id="pass2"/>
         <input type = "submit" value = "Submit" />
	</form>
</body>
</html>