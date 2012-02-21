<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<title>普通用户任务列表</title>
<link rel="stylesheet" type="text/css" href="inc/style.css">
</head>
<!--两个弹出层js -->
<script type="text/javascript" src="inc/popup.js"></script>
<script>
var dialogclosepath="<%=baseURL%>/vcity/inc/close_off.gif";
function closetask(){
	document.getElementById("goframe").style.display='';
	document.getElementById("list").style.display='none';
	
}
</script>
<body>
<html:form action="/vcityTaskAction.do?action=showTaskList" styleId="vcityTaskForm">
<input type="hidden" name="flag" value="1" />
<table id="list"  align="center" height="100%" width="982" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="180">&nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="border:1px solid #9db3c5">
	
	<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x;  padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">任务列表</strong></legend><br>
      <table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%">序号</td>
            <td>任务名称</td>
            <td width="15%">任务类型</td>
            <td width="15%">任务时间</td>
            <td width="15%">状态</td>
            <td width="25%">操作</td>
          </tr>
        </thead>
        <c:if test="${!empty datas}">
				<c:forEach items="${datas}" var="task" varStatus="stauts">
				<tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
						<td>${stauts.index+1 }</td>
							<td align="center">${task.taskName }</td>
							<c:if test="${task.taskType ==2 }"><td align="center"> 人工任务</td></c:if>
							<c:if test="${task.taskType ==1 }"><td align="center"> 自动任务</td></c:if>
							<td align="center">${task.taskBegin} - ${task.taskEnd}</td>
							<td align="center">${task.taskStatus}</td>
							 <td align="center" class="tdc">
							 <a  href="javascript:;" onclick="ShowIframe('查看任务基本信息','<%=baseURL%>/vcityTaskAction.do?action=taskInfo&taskId=${task.taskId }&info=cust',630,420)">查看</a>
							  <script type="text/javascript">
							  		var state = '${task.taskStatus}';
							  		var type = '${task.taskType }';
							  		if(state!='已结束'){
							  			if(type=='2'){
							  				document.write("<a href=\"<%=baseURL%>/chatAction.do?action=toChat&taskId=${task.taskId }\" target='_blank'>进入交流</a>");
							  			} else {
							  				document.write("<a href='javascript:;' target='_blank'>参与任务</a>");
							  			}
							  		}
							 </script>
							  </td>
		        </tr>
		       </c:forEach>
		  </c:if>
		<tfoot>
          <tr>
            <td colspan="6"><table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
           	 <td colspan="6"><wbase:dispartPage formId="vcityTaskForm" /></td>
         	 </tr>
            </table></td>
          </tr>
        </tfoot>
      </table>
	  <!--场景iframe-->
		<div id="goframe" style="display: none;"><iframe scrolling="auto" frameborder="0" width="100%" height="100%" name="gourl" src="../../virtual/space10086.html"></iframe></div>
		
</fieldset>
	
	</td>
  </tr>
</table>
	
	
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
