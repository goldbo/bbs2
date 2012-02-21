<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户任务详情</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="<%=baseURL %>/space/js/excute.js"></script>
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
        <td height="30" align="right"><p><STRONG>任务介绍：</STRONG></p></td>
        <td height="30">          ${mainTask.taskIntro } </td>
      </tr>
      <c:if test="${mainTask.taskType ==1}">
        <tr>
        <td height="30" align="right"><p><STRONG>任务步骤：</STRONG></p></td>
        <td height="30">  
			<c:if test="${ !empty indexList }">
				<c:forEach items="${ indexList }" varStatus="st" var="subTask">
					${st.index+1 }、${subTask.subTaskDesc } </br>
				</c:forEach>
			</c:if>
	      </td>
      </tr>
      </c:if>
      <tr>
        <td height="150" colspan="2" align="center"><input id="test" name="Submit252" type="button" class="btn_BS" value="参与任务" onclick="gotask(${mainTask.taskId });parent.closetask();parent.ClosePop();"></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
<script type="text/javascript">
</script>
</html>
