<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>

	<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">

<form method="post" action="index_meeting.jsp" name="frm" id="frm">
    <input  type="hidden" name="action" value="doKickoutAppointmentMember"/>
     <input  type="hidden" name="appId" value="${app.appointmentId }"/>
		<table width="100%" height="100%" align="center" border="0"
			cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
			<tr>
				<td height="30" valign="bottom" bgcolor="#f1f7fb">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20" class="tab_kp_off">&nbsp;
								
							</td>
							<td width="100" height="26" class="tab_kp_on">
								会议参与员筛选
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
          <td width="32%" height="30" class="tdr"><p><strong>会议主题：</strong></p></td>
          <td width="68%">
	      ${app.appointmentName }  </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>会议模式:</strong></p></td>
          <td>
	    	<c:if test="${app.modeType eq 0}">内部会议(<font color="red">直接参与会议,不需报名和审核</font>)</c:if>
	    	<c:if test="${app.modeType eq 1}">指定会议(<font color="red">参与者需要通过报名和审核</font>)</c:if>
	    	<c:if test="${app.modeType eq 2}">公开会议(<font color="red">参与者需要通过报名和审核</font>)</c:if>
	    </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>会议开始时间：</strong></p></td>
          <td><bean:write name="app" property="appointmentStarttime" format="yyyy-MM-dd HH:mm" /></td>
        </tr>
         <tr>
          <td height="30" class="tdr"><p><strong>预期会议结束时间：</strong></p></td>
          <td><bean:write name="app" property="appointmentEndtime" format="yyyy-MM-dd HH:mm"/>
	     </td>
        </tr>
        <tr id="scbtn">
          <td height="30" class="tdr"><p><strong>主持人：</strong></p></td>
          <td>
	        ${app.userPresider.username }</td>
	   </tr>
      <tr>
          <td height="30" class="tdr"><p><strong>参与人：</strong></p></td>
          <td>
	        ${canyu_user }        
	    </td>
        </tr>
        
        <tr>
          <td height="30" class="tdr"><p><strong>会议图片：</strong></p></td>
          <td> <img width="80px;" height="60px;" id="img" src="${app.imagePath }"></td>
         </tr>
           <tr >
	    <td height="30" class="tdr"><p><strong>会议视频：</strong></p></td>
	    <td>
	         <logic:empty name="videoUrlList">暂无视频录制记录 </logic:empty>
	         <logic:notEmpty name="videoUrlList">
	           <logic:iterate id="videoUrl" name="videoUrlList">
	               <a href="http://meeting.esaying.cn:5080/meeting_red5_video/record/${videoUrl}">${videoUrl}</a>
	           </logic:iterate>
	         </logic:notEmpty>
	    </td>
	  </tr>
        <tr>
          <td  height="30" class="tdr"><p><strong>会议介绍：</strong></p></td>
          <td>  ${app.appointmentDescription }</td>
        </tr>
		 <tr>
	    <td height="30">&nbsp;</td>
	    <td>
	  	  <input type="button" class="btn_BS" value="返回" onClick="javaScript:location.href='index_meeting.jsp?action=pageAppointmentByUserName';"/>&nbsp;&nbsp;${msg } </td>
	  </tr>
		</table>
		</td>
		</tr></table>
</form>

	