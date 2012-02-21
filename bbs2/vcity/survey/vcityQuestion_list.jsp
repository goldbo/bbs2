<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript">
	function goBack(){
		window.location = "<%=baseURL%>/vcitySurveyAction.do?action=showVcitySureyList";
	}
	function editVcitySurvey(id){
		location.href = '<%=baseURL%>/vcitySurveyAction.do?action=editVcitySurey&todo=update&id='+id;
		location.submit();
	}
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<table align="center" width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL%>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL%>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" class="b_white">1、填写调查名称</td>
    <td width="14"><img src="<%=baseURL%>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" class="b_white">2、添加问题</td>
	<td width="14"><img src="<%=baseURL%>/vcity/images/dh_bleft.jpg" height="43"></td>
	<td width="200" class="b_blue">3、调查问卷预览</td>
	<td width="14"><img src="<%=baseURL%>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
    <td background="<%=baseURL%>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL%>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>
<br />
<div class="tdc" style="border-bottom:1px dashed #CCCCCC; line-height:40px;"><strong class="greenFont14">${vcitySurey.sureyName}</strong></div>
<br />
<c:forEach items="${vcitySurey.vcityQuestion}" var="question" varStatus="questionStauts">
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >${question.questionName }</strong></td>
  </tr>
  
  <c:if test="${question.questType eq 1}">
  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
  <tr>
    <td height="30"><input name="question${question.questionId}" type="radio" value="" />&nbsp;${item.itemDesc}</td>
  </tr>
  </c:forEach>
  
  </c:if>
    <c:if test="${question.questType eq 2}">
  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
  <tr>
    <td height="30"><input name="question${question.questionId}" type="checkbox" value="" />&nbsp;${item.itemDesc}</td>
  </tr>
  </c:forEach>
  </c:if>
  
</table>
</c:forEach>
<div align="center" style="padding:8px 0px; border-top:1px dashed #CCCCCC">
<c:if test="${show eq 3}"></c:if>
<c:if test="${empty show }">
<input type="button" class="btn_BS" value="修改问卷" onClick="editVcitySurvey(${vcitySurey.sureyId})">
</c:if>
&nbsp;&nbsp;<input type="button" class="btn_BS" onclick="goBack()" value=" 关闭 " />
</div>
</fieldset>
</td></tr></table>
</body>
</html>