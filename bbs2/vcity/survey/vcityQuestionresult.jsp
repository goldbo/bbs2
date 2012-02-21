<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>完整调查问卷</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript">
	function createQuestionresult(){
		if(check()){
		vcityQuestionresultForm.submit();
		}
	}
	function check(){
	for(var i=1;i<=${fn:length(vcitySurey.vcityQuestion)};i++){
	var checknum=0;
	var ques =document.getElementById("ques"+i).value;
	var itemlist =document.getElementsByName("question("+ques+").selectitemId");
	for(var j=0;j<itemlist.length;j++){
		if(document.getElementsByName("question("+ques+").selectitemId")[j].checked==true){
			checknum++;
		}
		}
		if(checknum<1){
		alert("请回答完整问题");
		return false;
		}
		}
		return true;
	}
</script>
</head>

<body>
<html:form action="/vcityQuestionresultAction.do?action=saveVcityQuestionresult" >
<input type="hidden" value="${vcitySurey.sureyId }" name="surveyId"  />
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<div class="tdc" style="border-bottom:1px dashed #CCCCCC; line-height:40px;"><strong class="greenFont14">${vcitySurey.sureyName}</strong></div>
<br />
<div style="display:none;"><input type="hidden" name="sureyId" value="${vcitySurey.sureyId}" /></div>
<c:forEach items="${vcitySurey.vcityQuestion}" var="question" varStatus="questionStauts">
<div style="display:none;"><input type="hidden" name="question(${question.questionId}).questionId" value="${question.questionId}" /> </div>
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">
<input type="hidden" id="ques${question.index }" value="${question.questionId}" />
  <tr>
    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >${question.questionName }</strong></td>
  </tr>
  
  <c:if test="${question.questType eq 1}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input id="${item.itemId}" name="question(${question.questionId}).selectitemId" type="radio" value="${item.itemId}" />&nbsp;${item.itemDesc}</td>
	  	
	  </tr>
	  </c:forEach>
  </c:if>
  
  <c:if test="${question.questType eq 2}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input id="${item.itemId}" name="question(${question.questionId}).selectitemId" type="checkbox" value="${item.itemId}" />&nbsp;${item.itemDesc}</td>
	  </tr>
	  </c:forEach>
  </c:if>
  
</table>
</c:forEach>
<div align="center" style="padding:8px 0px; border-top:1px dashed #CCCCCC"><input type="button" class="btn_BS" onclick="createQuestionresult()" value=" 答题 " />
</div>
</fieldset>
</td></tr></table>
</html:form>
</body>
</html>