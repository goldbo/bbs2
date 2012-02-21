<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>第一体验俱乐部研究员管理平台</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body>
<html:form action="/vcityTaskAction.do?action=showTaskList" styleId="vcityTaskForm">
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">任务列表</strong></legend><br>
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
    <td width="13%" bgcolor="#f1f9fe" class=" tdl_B">&nbsp;</td>
    <td width="87%" height="40" bgcolor="#f1f9fe" class="tdr_B"><input name="Submit22" type="button" class="btn_BS" onClick="addtask('/vcityTaskAction.do?action=editTask&todo=create',800,600);" value=" 创建任务 "></td>
  </tr>
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%">
       		  <input type='CheckBox' id='selectedRowAll' name='selectedRowAll' onclick='selectAllGrid(this,"selectedRow")'>&nbsp;<a href="javascript:;" onClick="deleteMore('/vcityTaskAction.do?action=deleteTask');">删除</a></td>
            <td>任务名称</td>
            <td width="15%">任务类型</td>
            <td width="15%">状态</td>
            <td width="25%">操作</td>
          </tr>
        </thead>
          <c:if test="${!empty datas}">
				<c:forEach items="${datas}" var="task" varStatus="stauts">
				<tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
								<td align="center" height="25px">
									<input type='CheckBox' name='selectedRow' value='${task.taskId }'>
								<br></td>
							<td align="center">${task.taskName }</td>
							<c:if test="${task.taskType ==1 }">
							<td align="center"> 自动任务</td></c:if>
							<c:if test="${task.taskType ==2 }">
							<td align="center"> 人工任务</td></c:if>
							<td align="center">${task.taskStatus}</td>
							
							 <td align="center" class="tdc">
							 <c:if test="${task.taskStatus!='已结束'}"><input type="button" value=" 编辑 " class="btn_BS" onClick="updateTask('/vcityTaskAction.do?action=editTask&todo=update&taskid=${task.taskId }',800,600);" /></c:if>
							 <input type="button" value=" 删除 " class="btn_BS" onClick="deleteOne('/vcityTaskAction.do?action=deleteTask&taskid=${task.taskId }');" />
							 <input type="button" value=" 详情 " class="btn_BS" onClick="taskInfo('/vcityTaskAction.do?action=taskInfo&taskId=${task.taskId }',800,600);" />
							  <c:if test="${task.taskType ==2 }"> <c:if test="${task.taskStatus!='已结束'}"><input type="button" value="进入交流" class="btn_BS" onClick="window.open('<%=baseURL%>/chatAction.do?action=toChat&taskId=${task.taskId }');" /></c:if></c:if>
							  </td>
		        </tr>
		       </c:forEach>
		  </c:if>
		<tfoot>
          <tr>
            <td colspan="6"><wbase:dispartPage formId="vcityTaskForm" /></td>
          </tr>
        </tfoot>
      </table>
	  
</fieldset>
	
	</td>
  </tr>
</table>
</html:form>
</body>
<script type="text/javascript">
function addtask(url,width,height){
	//window.showModalDialog("<%=baseURL%>"+url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
	//vcityTaskForm.submit();
	vcityTaskForm.action = "<%=baseURL%>/vcityTaskAction.do?action=editTask&todo=create";
	vcityTaskForm.submit();
}
function deleteMore(url){
	var num = getCheckBoxNum("selectedRow");
	if(num>0){
	 if (!confirm("确定要删除已选择任务？")){
	 	     return false;
	 	  }else{
	 	  	vcityTaskForm.action="<%=baseURL%>"+url;
			vcityTaskForm.submit();
	 	  	return true;
	 	  }
	}else{
		alert("请选择您要删除的任务！");
		return false;
	}
}
function deleteOne(url){
	if (!confirm("确定要删除该任务？")){
		return false;
	} else {
	 	vcityTaskForm.action="<%=baseURL%>"+url;
		vcityTaskForm.submit();
	 	return true;
	}
}
function updateTask(url,width,height){
	vcityTaskForm.action = "<%=baseURL%>"+url;
	vcityTaskForm.submit();
}
function taskInfo(url,width,height){
	//window.showModalDialog("<%=baseURL%>"+url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
	//vcityTaskForm.submit();
	vcityTaskForm.action = "<%=baseURL%>"+url;
	vcityTaskForm.submit();
}
function toChat(url){
	vcityTaskForm.action = "<%=baseURL%>"+url;
	vcityTaskForm.submit();
}

</script>
</html>
