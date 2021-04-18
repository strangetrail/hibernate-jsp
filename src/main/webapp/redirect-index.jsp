<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Redirecting to index</title>
</head>
<body>
<center>
      <h1>Page Redirection</h1>
      </center>
      <%
         // New location to be redirected
         String site = new String(request.getContextPath() + "/index.jsp");
         response.setStatus(response.SC_MOVED_TEMPORARILY);
         response.setHeader("Location", site); 
      %>
</body>
</html>