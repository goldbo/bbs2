<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="include/inc/style.css">
	<script type="text/javascript">
	</script>
  </head>

	<body bgcolor="#FFFFFF">
	<form action="logging.jsp" name="login" onsubmit="return subfrm()">
		<input type="hidden" name="formhash" value="${formhash}" />
		<input type="hidden" name="action" value="login_b" />
		<input type="hidden" name="loginsubmit" value="true" />
		<input type="hidden" name="cookietime" value="2592000" />
		<input type="hidden" name="accessing" value="logging" />
		<input type="hidden" name="loginfield" value="username" />
		<input type="hidden" name="referer"
			value="<%=baseURL %>/chatAction.do?action=toChatRoom&id=${id }" />
				 <div  id="msg" style="color: red; padding-bottom: 10px; padding-top: 5px; text-align: center;"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table_3" align="center" style="">
		  <tr height="30">
		    <td width="35%" align="right" height="30px;">用户账号：</td>
		    <td ><input type="text" name="username" id="username" style="width:180px; height: 20px;"/></td>
		  </tr>
		  <tr height="30">
		    <td align="right" height="30px;">用户密码： </td>
		    <td ><input type="password" name="password" id="password" style="width:180px; height: 20px;"/></td>
		  </tr>
		  <tr height="30" >
		    <td colspan="2" align="center" height="30px;"><input type="submit" value="登录" class="btn_BS" />&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"  class="btn_BS"/></td>
		  </tr>
		</table>
	</form>
	
</body>
</html>
