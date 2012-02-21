<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择体验产品</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body>
<html:form action="vcityResourceAction.do" method="post">
<input type="hidden" name="taskId" value="${taskId }" />
<table width="100%" height="100%" align="right" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100" height="26" id="id1" class="tab_kp_on" onClick="location.href='<%=baseURL %>/vcityResourceAction.do?action=showResourcesByChat&taskId=${taskId}&userName=${vcityResourceForm.userName}';OCTab(1);">预设体验产品</td>
                    <td width="100" align="center" id="id2"  class="tab_kp_off"  onClick="location.href='<%=baseURL %>/vcityResourceAction.do?action=showResourcesByChat&taskId=${taskId}&other=1&userName=${vcityResourceForm.userName}';OCTab(2);">其它体验产品</td>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>
			  </td>
            </tr>
            <tr>
              <td valign="top">
              
              <!--  -->
			  <table width="98%" height="89%" border="0" align="center" cellpadding="0" cellspacing="1" background="images/bg.gif" style="margin:8px 0px; border:1px solid #c6d2e3">
                  <logic:notEmpty name="resourceList">
                  	<logic:iterate id="resourceInfo" name="resourceList" indexId="st">
                  		<bean:define id="inx" value="${st%3}"></bean:define>
                  		<c:if test="${inx==0}">
                  			<tr>
                  		</c:if>
                  		
							<td width="33%" height="25" align="center">
								<div align="center">
									<img src="<%=baseURL %>/${resourceInfo.resImage }"  onload="javascript:DrawImage(this,110,110)">
								</div>
								<input type="checkbox" name="selectedRow" value="${resourceInfo.id }%%${resourceInfo.resName }" />${resourceInfo.resName }
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).sceneTask.taskId" value="${vcityResourceForm.taskId}"/>
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).subTaskURL" value="${resourceInfo.resURL}"/>
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).subTaskType" value="1"/>
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).subTaskResId" value="${resourceInfo.id}"/>
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).userName" value="${vcityResourceForm.userName}"/>
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).subTaskDesc" value="体验${resourceInfo.resName }产品"/>
								<html:hidden name="vcityResourceForm" property="value(${resourceInfo.id }).subTaskState" value="0"/>
								<input type="hidden" name="userName" value="${vcityResourceForm.userName}"/>
								<input type="hidden" name="taskId" value="${vcityResourceForm.taskId}"/>
							</td>
						<c:if test="${inx==2}">
							</tr>
						</c:if>	
                  	</logic:iterate>
                  </logic:notEmpty>
                  
                </table>
				<div align="center" style=" line-height:30px;">
				<html:hidden name="vcityResourceForm" property="taskId"/>
				<html:hidden name="vcityResourceForm" property="userName"/>
		    <input name="Submit2322" type="button" class="btn_BS" value=" 选择 " onclick="saveChoose()"/>
&nbsp;&nbsp;
<input name="Submit2332" type="button" class="btn_BS" value=" 取消 " onclick="parent.ClosePop()"/>
		  </div>
              </td>
            </tr>
          </table>
	</html:form>
</body>
<script>
function OM_table(obj){
	obj.className='table_on';
}
function OO_table(obj){
	obj.className='table_off';
}
var flag = '${flag}'; 
if(flag ==2){
	document.getElementById('id'+flag).className='tab_kp_on';
	document.getElementById('id1').className='tab_kp_off';
}
if(flag ==1){
	document.getElementById('id'+flag).className='tab_kp_on';
	document.getElementById('id2').className='tab_kp_off';
} 


	function saveChoose(){
		var str = false;
		var chses = document.getElementsByName("selectedRow");
		if(chses && chses.length>0){
			for(var i=0;i<chses.length;i++){
				var choose = chses[i];
				if(choose.checked){
					str = true;
				}
			}
		}
		if(!str){
			alert("请选择产品！");
		}else{
			vcityResourceForm.action = "vcityResourceAction.do?action=saveSubTask";
			vcityResourceForm.submit();
		}
	}

	
	if('${isSuc}'!=''){
		if('${isSuc}'=='true'){
			var str = '${str}';
			var s = str.split("%%");
			for(var i=0;i<s.length;i++){
				parent.sendURL(s[i]);	
			}
			alert("保存成功！");
			parent.ClosePop();
		}else{
			alert("保存失败！");
		}
	} 
	

</script>
</html>
