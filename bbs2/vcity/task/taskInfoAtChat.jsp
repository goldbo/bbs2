<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body>
<table width="97%" height="97%" align="center" border="0" cellpadding="0" cellspacing="1" class="tableWrap">
  <tr>
    <td valign="top"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="27%" height="40"><strong class="greenFont14">基本信息</strong></td>
        <td width="73%">&nbsp;</td>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>任务名称：</STRONG></p></td>
        <td>${mainTask.taskName }</td>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>有效期：</STRONG></p></td>
        <td>${mainTask.taskBegin }~${mainTask.taskEnd }</td>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>任务类型：</STRONG></p></td>
        <c:if test="${mainTask.taskType ==1}"><td>自动任务</td></c:if>
        <c:if test="${mainTask.taskType ==2}"><td>人工任务</td></c:if>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>任务积分：</STRONG></p></td>
        <td>${mainTask.credit }分</td>
      </tr>
      <tr>
        <td height="30" align="right"><strong>人员列表：</strong></td>
        <td height="30">${users }</td>
      </tr>
      <tr>
        <td height="30" align="right"><strong>已选择调查问卷：</strong></td>
        <td height="30">${surveyName }</td>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>任务介绍：</STRONG></p></td>
        <td height="30">  ${mainTask.taskIntro } </td>
      </tr>
     
      <tr>
        <td  colspan="2" align="center">&nbsp;</td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
