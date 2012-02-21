<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
</head>

<body>
<html:form action="/vcityQuestionAction.do?action=saveVcityQuestion">
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">

<c:forEach items="${vcitySurey.vcityQuestion}" var="question" varStatus="questionStauts">
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >问题${questionStauts.index+1} :${question.questionName }</strong></td>
	    </tr>
	   <tbody id="theadid${question.questionId}" style="display:none">
		 <tr>
		 	<td>
		 		<table width="98%" align="center" border="0" cellspacing="5" cellpadding="0">
		                <tr>
		                  <td width="20%" height="30" align="right">问题名称：</td>
		                  <td>
		                  	<html:text property="question(${question.questionId}).questionName" name="vcityQuestionForm" style="width:300px;" value="${question.questionName }" />
		                  	</td>
		                </tr>
		                <tr>
		                  <td height="30" align="right">问题类型：</td>
		                  <td>
		                  	<input type="radio" name="question(${question.questionId}).questType" value="1" ${question.questType == 1? "checked":""} />
			                    单选&nbsp;&nbsp;
			                <input type="radio" name="question(${question.questionId}).questType" value="2" ${question.questType == 2 ? "checked":""} />
			                    多选</td>
		                </tr>
		                <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
			                <tr>
			                  <td height="30" align="right">选项${itemStauts.index+1}：</td>
			                  <td>
			                  	<html:text property="question(${question.questionId}).item(${item.itemId}).itemDesc" name="vcityQuestionForm" style="width:300px;" value="${item.itemDesc}" />
			                  	<html:hidden property="question(${question.questionId}).item(${item.itemId}).itemId" name="vcityQuestionForm" value="${item.itemId}" />
			                  	<html:hidden property="question(${question.questionId}).item(${item.itemId}).itemIndex" name="vcityQuestionForm" value="${item.itemIndex}" />
			                  </td>
			                </tr>
		                </c:forEach>
		              
		              </table>
				</td>
		<td>&nbsp;</td>
			</tr>
	</tbody>
	<tfoot id="tbodyid${question.questionId}">
	 <c:if test="${question.questType eq 1}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="stauts">
		  <tr>
		    <td height="30"><input name="1" type="radio" value="" disabled="disabled"/>&nbsp;${item.itemDesc }</td>
		    <td>&nbsp;</td>
		  </tr>
	  </c:forEach>
	  </c:if>
	  <c:if test="${question.questType eq 2}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="stauts">
		  <tr>
		    <td height="30"><input name="1" type="checkbox"value="" disabled="disabled"/>&nbsp;${item.itemDesc }</td>
		    <td>&nbsp;</td>
		  </tr>
	  </c:forEach>
	  </c:if>
	</tfoot>
</table>
</c:forEach>

<hr/>

</fieldset>
</td></tr></table>
</html:form>
</body>
</html>