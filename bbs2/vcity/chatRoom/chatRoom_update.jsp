<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>创建任务</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/popup.js"></script>
<script>
var dialogclosepath="inc/close_off.gif";
</script>

<script type="text/javascript" src="<%=baseURL%>/common/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcityChatRoomService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript">var base = '<%=baseURL %>';</script>
<script charset="utf-8" src="<%=baseURL %>/vcity/inc/js/kindeditor.js"></script>
<script type="text/javascript">
		
			KE.show({
				id : 'describe',
				resizeMode : 0,
				allowPreviewEmoticons : false,
				allowUpload : false,
				items : [
				'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				'|', 'emoticons', 'link']
			}); 
		</script>
</head>
<body>
<html:form action="vcityChatRoomAction.do?action=updateChatRoom"  styleId="vcityChatRoomForm" method="post" >
 <input type="hidden" value="${vcityChatRoomForm.roomName }" id="tempName"　>
 <html:hidden styleId="id" property="id" name="vcityChatRoomForm"/>
  <html:hidden property="createdUser" name="vcityChatRoomForm"/>
<table width="100%" height="100%" align="center" border="0"
			cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
			<tr>
				<td height="30" valign="bottom" bgcolor="#f1f7fb">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20" class="tab_kp_off">&nbsp;
								
							</td>
							<td width="100" height="26" class="tab_kp_on">
								新增信息
							</td>
							<td class="tab_kp_off">&nbsp;
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="95%" height="97%" border="0" align="center" cellpadding="0"
						cellspacing="1" class="tableWrap">
						<tr>
							<td height="25" class="tdr_B">&nbsp;							</td>
							<td class="greenFont12 tdr_B">&nbsp;							</td>
						</tr>
						<tr>
          <td width="32%" height="30" class="tdr"><p><strong>聊天室名称：</strong></p></td>
          <td width="68%"> <input value="${vcityChatRoomForm.roomName }" type="text" name="roomName" id="roomName" onChange="checkNameExist();" style=" width:60%" class="blueborderText">
          <span class="redFont12">*</span>
          </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>有效期：</strong></p></td>
          <td><input type="text" value="${vcityChatRoomForm.beginTime }" name="beginTime" id="beginTime" onClick="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true,'whyGreen')" class="blueborderText" style="width:147px">
          ~
          <input type="text" value="${vcityChatRoomForm.endTime }" name="endTime" id="endTime" onClick="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true,'whyGreen')" class="blueborderText" style="width:148px">
          </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>参与人员：</strong></p></td>
          <td><div id="num" style="overflow: auto; width:60%; height:100px;  border:1px solid #A7CBE5;">
         <c:if test="${not empty vcityChatRoomForm.vcityChatUser}">
					 <c:forEach items="${vcityChatRoomForm.vcityChatUser}" var="user">
							<input type='checkbox' name='checkuser' readonly="readonly" disabled="disabled" checked id='${ user.username}' value=""/>${ user.username}
					   </c:forEach>
					 </c:if> 
         </div>
          <input type="button" name="Submit3" onClick="parent.ShowIframe('选择人员','<%=baseURL %>/shuaxuan.jsp',700,510);" class="btn_BS"  value="会员筛选">
            <span class="redFont12">*</span></td>
        </tr>
       
       <tr>
          <td height="30" class="tdr"><p><strong>聊天室信息描述：</strong></p></td>
          <td><textarea name="describe" id="describe" style="width:60%; height:200px;" class="blueborderText">${vcityChatRoomForm.describe }</textarea>
            <span class="redFont12">*</span></td>
        </tr>
        
		<tr>
			<td colspan="2"  class="tdc">
				<input type="button" name="Submit4" value="保存" onClick="saveChatRoom();" class="btn_BS">&nbsp;	&nbsp;	
				<input onClick="history.back(-1);" type="button" name="Submit42" value=" 取消 " class="btn_BS">
			</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
</html:form>
</body>
<script type="text/javascript">
function checkNameExist(){
 	var roomName = $('#roomName').val();
 	if($.trim(roomName)!=""){
 		
	 	vcityChatRoomService.findChatRoomByName($.trim(roomName),function exist(data){
	 		if(data!=null&&data.id!=$('#id').val()){
	 			alert("该会议名称已经存在,请修改！");
	 			$("#roomName").focus();
	 			$('#roomName').attr("value",$('#tempName').val());	 			
	 		}
	 	});
	 }
}
function saveChatRoom(){
KE.sync('describe');
	vcityChatRoomForm.submit();
}
/*接收推送会员 */
function acceptPara(result){
	var arraynum=result.split(",");
	var checkuser = document.getElementsByName("checkuser");
	for(var i=0;i<arraynum.length;i++){
		var isExist = true;
	   if($(arraynum[i])!=null){
	   		if(!(checkuser.length>0)){
	   			var old = document.getElementById('num').innerHTML;
			   	document.getElementById('num').innerHTML = old+"<div style='float:left; width:100px; height:30px; line-height:30px;'><input type='checkbox' name='checkuser' checked id='"+arraynum[i]+"' value='"+arraynum[i]+"'/>"+arraynum[i]+"</div>";					
	   		} else {
		   		for(var j=0;j<checkuser.length;j++){
		   			if(arraynum[i]==checkuser[j].value){
		   					isExist = false;
				   			break;
					}
				}
				if(isExist){
					var old = document.getElementById('num').innerHTML;
				   	document.getElementById('num').innerHTML = old+"<div style='float:left; width:160px; height:30px; line-height:30px;'><input type='checkbox' name='checkuser' checked id='"+arraynum[i]+"' value='"+arraynum[i]+"'/>"+arraynum[i]+"</div>";					
				}
			}
	   }
	  }
	   parent.ClosePop();
	//document.getElementById('tipDiv2').style.display="none";
	//document.getElementById('KuanghiddenDiv2').style.display="none";
}

</script>
</html>
