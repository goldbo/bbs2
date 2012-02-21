<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>回答问卷页面</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript">
window.history.forward(1);
	function createQuestionresult(){
		if(check()){
			vcityQuestionresultForm.action="<%=baseURL %>/vcityQuestionresultAction.do?action=saveVcityQuestionresult&todo=manualTask&task=${subtask}";
			vcityQuestionresultForm.submit();
		}
	}
	function check(){
	for(var i=1;i<=${fn:length(questionList)};i++){
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
<html:form action="/vcityQuestionresultAction.do?action=saveVcityQuestionresult">
<input type="hidden" value="${subtype }" name="subtype" />
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<%--<div class="tdc" style="border-bottom:1px dashed #CCCCCC; line-height:40px;"><strong class="greenFont14"></strong></div>
--%><br />
<div style="display:none;"><input type="hidden" name="sureyId" value="${ids}" /></div>
<c:forEach items="${questionList}" var="question" varStatus="questionStauts">
<div style="display:none;"><input type="hidden" name="question(${question.questionId}).questionId" value="${question.questionId}" /> </div>
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">
<input type="hidden" id="ques${questionStauts.count }" value="${question.questionId}" />
  <tr>
    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >${question.questionName }</strong></td>
  </tr>
  
  <c:if test="${question.questType eq 1}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input name="question(${question.questionId}).selectitemId" type="radio" value="${item.itemId}" />&nbsp;${item.itemDesc}</td>
	  </tr>
	  </c:forEach>
  </c:if>
  
  <c:if test="${question.questType eq 2}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input name="question(${question.questionId}).selectitemId" type="checkbox" value="${item.itemId}" />&nbsp;${item.itemDesc}</td>
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