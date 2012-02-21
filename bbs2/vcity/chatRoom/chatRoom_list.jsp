<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html:html lang="true">
<head>
	<html:base />
	<title>体验资源管理</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">

	<script type="text/javascript">
	var isPublish = '${isPublish}';
	if(isPublish=='true'){
		alert("发布成功！");
		window.location.href='<%=baseURL%>/vcityChatRoomAction.do?action=showChatRoomList';		
	}
function createChatRoom(){
	vcityChatRoomForm.action = "<%=baseURL%>/vcityChatRoomAction.do?action=toAddChatRoom";
	vcityChatRoomForm.submit();
}

function updateChatRoom(id){
	vcityChatRoomForm.action = "<%=baseURL%>/vcityChatRoomAction.do?action=toUpdateChatRoom&id="+id;
	vcityChatRoomForm.submit();

}

function deleteAll(){
	var num = getCheckBoxNum("selectedRow");

	if(num>0){
	 if (!confirm("确定要删除已选择会议？")){
	 	     return false;
	 	  }else{
	 	  	vcityChatRoomForm.action="<%=baseURL%>/vcityChatRoomAction.do?action=deleteChatRoom";
			vcityChatRoomForm.submit();
	 	  	return true;
	 	  }
	}else{
		alert("请选择您要删除的资源！");
		return false;
	}
}

function deleteOne(id){

	if (!confirm("确定要删除该会议吗？")){
		return false;
	} else {
	 	vcityChatRoomForm.action="<%=baseURL%>/vcityChatRoomAction.do?action=deleteChatRoom&id="+id;
		vcityChatRoomForm.submit();
	 	return true;
	}
}

function toChatRoom(id){
	//vcityChatRoomForm.action="<%=baseURL%>/chatAction.do?action=toChatRoom&id="+id;
	//vcityChatRoomForm.submit();
	window.open("<%=baseURL%>/chatAction.do?action=toChatRoom&id="+id);
}
function publishChatRoom(id){
	if(confirm('确认发布此会议？发布将会通知参与会员！')){
		vcityChatRoomForm.action="<%=baseURL%>/vcityChatRoomAction.do?action=publishChatRoom&id="+id;
		vcityChatRoomForm.submit();
	}
}
function findDatas(isCreate){
	vcityChatRoomForm.action="<%=baseURL%>/vcityChatRoomAction.do?action=showChatRoomList&isCreate="+isCreate;
	if(isCreate!='${isCreate}'){
		var oNewNode = document.createElement("input");
   	 	oNewNode.setAttribute("type", "hidden");
    	oNewNode.setAttribute("name", "pageMethod");
    	oNewNode.setAttribute("value", "previous");
   		 vcityChatRoomForm.appendChild(oNewNode);
	}
	vcityChatRoomForm.submit();
}
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:98%;">
      <legend><strong class="greenFont14"><a href="<%=baseURL %>/meeting_forward.jsp?action=pageAppointmentByUserName">视频会议列表</a></strong>&nbsp;&nbsp;&nbsp;&nbsp;<strong class="greenFont14">文字会议列表</strong></legend><br>
<html:form action="/vcityChatRoomAction.do?action=showChatRoomList&isCreate=${isCreate }" styleId="vcityChatRoomForm">
	<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr><td width="53%" bgcolor="#f1f9fe" class=" tdl_B">
  	<%--<select name="isCreate" id="isCreate">
  		<option value="" selected="selected">请选择</option>
  		<option value="1" <c:if test="${isCreate==1 }">selected="selected"</c:if>>自己创建</option>
  		<option value="0" <c:if test="${isCreate==0 }">selected="selected"</c:if>>被邀请</option>
  	</select>
 	 <input type="button" onclick="findDatas();" class="btn_BS" value="查 询"  />
  --%>
  <c:if test="${isCreate==1 }"><font color="red"> 我的(${totalMy })</font>&nbsp;｜<a href="javascript:;" onclick="findDatas('');" >全部(${totalAll })</a>&nbsp;｜<a href="javascript:;" onclick="findDatas('0');" >被邀请(${totalOther })</a></c:if>
  <c:if test="${isCreate=='' }"><a href="javascript:;" onclick="findDatas('1');" > 我的(${totalMy })</a>&nbsp;｜<font color="red"> 全部(${totalAll })</font>&nbsp;｜<a href="javascript:;" onclick="findDatas('0');" >被邀请(${totalOther })</a></c:if>
  <c:if test="${isCreate==0 }"><a href="javascript:;" onclick="findDatas('1');" > 我的(${totalMy })</a>&nbsp;｜<a href="javascript:;" onclick="findDatas('');" >全部(${totalAll })</a>&nbsp;｜<font color="red">被邀请(${totalOther })</font></c:if>
  </td>
    <td  height="40" bgcolor="#f1f9fe" class="tdr_B"><input name="Submit22" type="button" class="btn_BS" onClick="createChatRoom();"  value=" 创建会议 "></td>
  </tr>
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%"><input type='CheckBox' id='selectedRowAll' name='selectedRowAll' onclick='selectAllGrid(this,"selectedRow")'>
              <a href="javascript:;" onClick="deleteAll();">删除</a></td>
            <td width="8%">NO. </td>
            <td>名称 </td>
            <td width="15%">创建人员 </td>
            <td width="15%">状态</td>
            <td width="30%">操作</td>
          </tr>
        </thead>
		<c:if test="${!empty datas}">
		<c:forEach items="${datas}" var="room" varStatus="stauts">
        <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
        <c:if test="${room.createdUser == jsprun_userss }"> <td align="center"><input type='CheckBox' name='selectedRow' value=${room.id } onclick='clearAllSelect(this,"selectedRowAll")'></td></c:if> 
        <c:if test="${room.createdUser != jsprun_userss }"><td align="center"><input type='CheckBox' name='xxx' disabled="disabled"></td></c:if>
          <td align="center">${stauts.index+1 }</td>
          <td align="center">${room.roomName }</td>
          <td align="center">${room.createdUser }</td>
          <td align="center"><c:if test="${room.chatStatus ==0}">未开始</c:if><c:if test="${room.chatStatus ==1}">进行中</c:if><c:if test="${room.chatStatus ==2}">已结束</c:if> </td>
          <td align="center" class="tdc">
          <!-- 如果是该研究员创建的会议 -->
          <c:if test="${room.createdUser == jsprun_userss }"> 
          <c:if test="${room.chatStatus !=2}"><input type="button" onClick="updateChatRoom(${room.id });" class="btn_BS"value="编 辑" />
         	<c:if test="${room.isPublish==0 }"><input type="button" onClick="publishChatRoom(${room.id });" class="btn_BS"value="发 布" /></c:if> 
         	<input type="button" onClick="toChatRoom(${room.id });" class="btn_BS"value="进入会议" />
         </c:if>
				<input type="button" onClick="deleteOne(${room.id });" class="btn_BS"value="删 除" />
				</c:if>
				<!-- 如果是被邀请的会议 -->
          <c:if test="${room.createdUser != jsprun_userss }"> 
          <c:if test="${room.chatStatus ==1}"><input type="button" onClick="toChatRoom(${room.id });" class="btn_BS"value="进入会议" /></c:if>
          </c:if>
        </tr>
		</c:forEach>
		</c:if>
		<tfoot>
		<c:if test="${empty datas}">
			<tr>
            <td colspan="6" align="center" >暂时还没有相应记录</td>
          </tr>
       </c:if>
          <tr>
            <td colspan="6"><wbase:dispartPage formId="vcityChatRoomForm" /></td>
          </tr>
        </tfoot>
      </table>
	  </html:form>
</fieldset>
	
	</td>
  </tr>
</table>

	<wbase:showMessage />
</body>

</html:html>
