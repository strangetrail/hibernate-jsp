<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "generate.table.TableGenerator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="mainstyles.css">
<title>jQuery, Ajax and Servlet/JSP integration example</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
<script src="app-ajax.js" type="text/javascript"></script>
</head>
<body>
	<div name="ajaxTable" id="ajaxTable">
	<%
		TableGenerator tg = new TableGenerator();
		String generatedTable = tg.generateGeneral(0, 5, 100, 0);
		out.print(generatedTable);
	%>
	</div>
</body>
</html>