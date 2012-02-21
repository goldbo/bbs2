<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcitySureyService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
	<script type="text/javascript">

function saveVcitySurvey(){
	var name=document.getElementById("sureyName").value;
	if(name==null||name==""){
		document.getElementById("msg").style.display="none";
		document.getElementById("msg2").style.display="block";
		//alert("请填写调查名称");
		return false;
	}
	vcitySurveyForm.submit();
	
}
function checkName(name){
		vcitySureyService.findVcitySureyByResName(name,callback);
}
function callback(returnValue){
	if(returnValue){
		document.getElementById("msg2").style.display="none";
		document.getElementById("msg").style.display="block";
		return false;
	}else{
		
	}
}
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<br>

<table align="center" width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL%>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL%>/vcity/images/dh_bleft.jpg" height="43"></td>
    <td width="200" class="b_blue">1、填写调查名称</td>
    <td width="14"><img src="<%=baseURL%>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
    <td width="200" class="b_white">2、添加问题</td>
	<td width="14"><img src="<%=baseURL%>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
	<td width="200" class="b_white">3、调查问卷预览</td>
	<td width="14"><img src="<%=baseURL%>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td background="<%=baseURL%>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL%>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>
<br />
<html:form action="/vcitySurveyAction.do?action=saveVcitySurey" >
<table width="70%" align="center" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="28%" height="40" align="right">调查名称：</td>
    <td>
    <input type="text" name="sureyName" value="${vcitySurey.sureyName}" onblur="checkName(this.value)" style="width:300px;">
    <%--<html:text property="sureyName" name="vcitySurveyForm" value="${vcitySurey.sureyName}" style="width:300px;"></html:text>--%></td>
    <td><span id="msg" style="display: none;font-size: small;color: red;">名称已存在</span><span id="msg2" style="display: none;font-size: small;color: red;">请填写名称</span></td>
  </tr>
  <tr>
    <td align="right">调查描述：</td>
    <td>
   	 <html:textarea property="sureyDesc" name="vcitySurveyForm" value="${vcitySurey.sureyDesc}" onblur="" style="width:300px; height:80px;" >
   	 </html:textarea>
    </td>
  </tr>
</table>

<div align="center" style="padding:8px 0px"><input type="button" class="btn_BS" value="下一步" onClick="saveVcitySurvey()" />
</div>
<html:hidden property="sureyId" name="vcitySurveyForm" value="${sureyId}" />
</html:form>
</fieldset>
</td></tr></table>
</body>
</html>
