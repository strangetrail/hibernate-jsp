<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, user.session.dao.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="mainstyles.css">
<title>Welcome to my site</title>
</head>
<body>
	<div class="loginbar">
		<% 
			Boolean userRegistered = false;
			Cookie []c = request.getCookies();
			if (c != null) {
				for (Cookie c_item : c) {
					if (c_item.getName().compareTo("session_id") == 0) {
						SessionDAO userSessionDao = new SessionDAO();
						List<UserSession> userSessions = userSessionDao.findUserSession(c_item.getValue());
						if (userSessions.size() > 0) {
							userRegistered = true;
							break;
						}
					}
				}
			}
			if (userRegistered) {
				out.println("<a href=\"logout.jsp\">Logout<a>");
			}
			else {
				out.println("<a href=\"Login.jsp\">Login</a>");
				out.println("<div class=\"inliner\"> | </div>");
				out.println("<a href=\"Register-New.jsp\">Register</a>");
			}
		%>
	</div>
	<a href="table-ajax.jsp">ajax</a>
</body>
</html>