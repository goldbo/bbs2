<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addTempSurvey.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><td>
    问卷标题：<input type="text" name="title"  >
</td><td>
    <input type="button" onclick="backtitle();" value="确 认">
    <input type="button" onclick="parent.colseDiv2();" value="取 消">
    </td></tr>
    </table>
  </body>
  <script type="text/javascript">
  function backtitle(){
  	var title =  document.getElementById("title").value;
  	parent.saveTempSurvey(title);
  }
  </script>
</html>
