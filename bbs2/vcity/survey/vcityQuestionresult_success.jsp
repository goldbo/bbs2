<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript">
function goback(){
	location.href="<%=baseURL%>/vcitySurveyAction.do?action=showVcitySureyList";
}
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<div class="tdc" style="border-bottom:1px dashed #CCCCCC; line-height:40px;"><strong class="greenFont14">${vcitySurey.sureyName}</strong></div>
<br />
<c:forEach items="${vcitySurey.vcityQuestion}" var="question" varStatus="questionStauts">
<div style="display:none;"><input type="hidden" name="question(${question.questionId}).questionId" value="${question.questionId}" /> </div>
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >${question.questionName }</strong></td>
  </tr>
  
  <c:if test="${question.questType eq 1}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input name="question(${question.questionId}).selectitemId" type="radio" disabled="disabled"  value="${item.itemId}" ${empty item.selectFlag?"":"checked" } />&nbsp;${item.itemDesc}</td>
	  </tr>
	  </c:forEach>
  </c:if>
  
  <c:if test="${question.questType eq 2}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input name="question(${question.questionId}).selectitemId" type="checkbox" disabled="disabled" value="${item.itemId}" ${empty item.selectFlag?"":"checked" } />&nbsp;${item.itemDesc}</td>
	  </tr>
	  </c:forEach>
  </c:if>
  
</table>
</c:forEach>
<div align="center" style="padding:8px 0px; border-top:1px dashed #CCCCCC">
</div>
</fieldset>
	</td></tr>
	<tr align="center"><td><input type="button" class="btn_BS" onclick="window.close();;" value=" 关 闭 "> </td></tr>
</table>
</body>
</html>