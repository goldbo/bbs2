<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>第一体验俱乐部研究员管理平台</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">

<script type="text/javascript">

function toSurveyResult(id){
	window.location = '<%=baseURL%>/statisticsAction.do?action=showSurveyResultTotal&id='+id;
}

</script>
</head>

<body>

<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<html:form action="/statisticsAction.do?action=showStatisticsSurveyList" styleId="vcitySurveyForm"  >
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
    <td width="43%" bgcolor="#f1f9fe" class=" tdl_B">问卷查询：
    <html:text property="sureyName" name="vcitySurveyForm"></html:text>
    &nbsp;<input type="button" class="btn_BS" onClick="vcitySurveyForm.submit();" value=" 查询 "></td>
    <td width="87%" height="40" bgcolor="#f1f9fe" class="tdr_B"></td>
  </tr>
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
          <td dwidth="10%">序号</td>
            <td width="70%">调查问卷</td>
            <td width="20%">操作</td>
          </tr>
        </thead>
        <c:if test="${!empty datas}">
			<c:forEach items="${datas}" var="survey" varStatus="stauts">
	        <tr>
	     		<td align="center">${stauts.count}</td>
	          <td align="center">${survey.sureyName}</td>
	          <td align="center">
			  <input type="button" onClick="toSurveyResult(${survey.sureyId})" class="btn_BS" value="统计结果" />
			 </td>
	        </tr>
        	</c:forEach>
        </c:if>
		<tfoot>
          <tr>
            <td colspan="5">
				<wbase:dispartPage formId="vcitySurveyForm" />
			</td>
          </tr>
        </tfoot>
      </table>
</html:form>
</fieldset>
	
	</td>
  </tr>
</table>

</body>
</html>
