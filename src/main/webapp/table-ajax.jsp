<%@page import="generate.userlist.GenerateUserList"%>
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
	<div name="ajaxTable" class="listindex" id="ajaxTable">
	<%
		TableGenerator tg = new TableGenerator();
		String generatedTable = tg.generateGeneral(0, 5, 100, 0);
		out.print(generatedTable);
	%>
	</div>
	<div class="listindex"><input type="text" style="width: 40px" id="listindex"/></div>
	<script>
         document.getElementById("listindex").addEventListener("keydown",
		   function(event) {
		      if (!event) {
		         var event = window.event;
		      }
		         //event.preventDefault();
		      if (event.keyCode == 13){
		         showpage(document.getElementById("listindex").value);
		      }
		   }, false);
	</script>
	<div class=chatcontainer>
	<a role="button" class="chatselection" onclick="showuserlist()">V</a>
	<%
		GenerateUserList ulGenerator = new GenerateUserList();
		out.print(ulGenerator.generateList());
	%>
	<div id="chatbox" class="chatbox">
				
	</div>
	<input type="text" class="textinput"/>
	<input type="button" class="submitchatbutton" value="send" onclick="send_test()"/>
	</div>
</body>
</html>