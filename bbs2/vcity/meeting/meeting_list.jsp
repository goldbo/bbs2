<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>第一体验俱乐部研究员管理平台</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body>
<form action="meeting_forward.jsp" name="frm">
	<input  type="hidden" name="action" id="action" value="pageAppointmentByUserName"/>
    <input name="num" class="input_goto" id="_num_" type="hidden" value="${num==null?1:num  }">
    <input type="hidden" name="appId"/>
    <input type="hidden" name="sendType" value="0"/>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">视频会议列表</strong>&nbsp;&nbsp;&nbsp;&nbsp;<strong class="greenFont14"><a href="<%=baseURL %>/vcityChatRoomAction.do?action=showChatRoomList&isCreate=1">文字会议列表</a></strong>   </legend><br>
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
    <td width="13%" bgcolor="#f1f9fe" class=" tdl_B">&nbsp;</td>
    <td width="87%" height="40" bgcolor="#f1f9fe" class="tdr_B"><input name="Submit22" type="button" class="btn_BS" onClick="addMeeting();" value=" 创建会议"></td>
  </tr>
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="14%">会议主题</td>
            <td width="14%">会议开始时间</td>
            <td width="10%">会议模式</td>
            <td width="5%">主持人</td>
            <td width="5%"> 是否发布</td>
             <td width="5%"> 状态</td>
              <td width="10%"> 操作</td>
          </tr>
        </thead>
         <tbody>
		   <logic:notEmpty name="appList">
			   <logic:iterate id="app" name="appList">
			    <tr>
				    <td align="center" height="25px">${app.appointmentName }</td>
				    <td align="center">${app.appointmentStarttime }</td>
				    <td align="center">
				         <c:if test="${app.modeType == 0 }">内部会议</c:if>
					     <c:if test="${app.modeType == 1 }">指定会议</c:if>
					     <c:if test="${app.modeType == 2 }">公开会议</c:if>
				    </td>
				    <td align="center">${app.userPresider.username }</td>
				    <td align="center">  
				        <c:if test="${app.isPublished == 0}">未发布</c:if>
				        <c:if test="${app.isPublished == 1}">已发布</c:if>
				     </td>
				   <td align="center">
                      <c:if test="${app.isEnd == 1 || nowDate >= app.appointmentEndtime}" var="flg">
                      	<font color='red'>已结束</font>
                      </c:if>
                       <c:if test="${!flg}">
                       	 <c:if test="${app.isEnd == 0 && nowDate<app.appointmentEndtime && nowDate>=app.appointmentStarttime}" var="aa">
                       	   <font color='blue'>正在进行</font>
                       	 </c:if>
                    	 <c:if test="${!aa}">
                       	   <font color='blue'>未展开</font>
                       	 </c:if>
                       </c:if>
					</td>
				   <td align="center">
				     <c:if test="${app.isEnd == 1 || nowDate >= app.appointmentEndtime}" var="flg">
                      	  	<a href="meeting_forward.jsp?action=getAppointmentInfoById&appId=${app.appointmentId }">查看</a>
                      	  	<a href="javaScript:doDelete(${app.appointmentId},1)">删除</a>
                       </c:if>
                       <c:if test="${!flg}">
                    	   <a href="meeting_forward.jsp?action=toUpdateAppointment&appId=${app.appointmentId }"> 修改</a>
                    	   <c:if test="${app.modeType != 0}"><a href="meeting_forward.jsp?action=toKickoutAppointmentMember&appId=${app.appointmentId }"> 会员筛选</a></c:if>
                    	   <a href="javaScript:doIsPublished(${app.appointmentId},0)"> 发布</a>
                    	   <a href="javaScript:doDelete(${app.appointmentId},0)">删除</a>
                       </c:if>
				    </td>
			   </tr>
			   </logic:iterate>
		 </tbody>
		<tfoot>
          <tr class="category">
		      <td colspan="7" >
		          <table width="98%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td align="left">共${page.recordTotal }条记录，当前第${page.currentPage }/${page.pageTotal }页，每页${page.pageSize }条记录  </td>
					    <td align="right" width="200"><a href="javaScript:goPage(1);">首页</a>
					     <a href="javaScript:back(${page.currentPage },${page.pageTotal });">上一页</a> 
					     <a href="javaScript:next(${page.currentPage },${page.pageTotal });">下一页</a>
					     <a href="javaScript:goPage(${page.pageTotal });">尾页</a>&nbsp;&nbsp;&nbsp;</td>
					  </tr>

					</table>
		      </td>
		   </tr>
		   </logic:notEmpty>
	    	<logic:empty name="appList">
		      <tr class="category">
		          <td colspan="7" bgcolor="#EDF9DE">没有找到与你相关的会议</td>
		      </tr>
		    </logic:empty>
		</table>
		<div align="center" style="height:40px;">${msg }</div>
        </tfoot>
      </table>
	  
</fieldset>
	
	</td>
  </tr>
</table>
</form>
</body>
<script type="text/javascript">
    //	主键删除
    function doDelete(id,tvalue){
    	if(confirm("您是否确定要删除该数据吗？请注意,在删除的时候可能会影响到其他数据!")){
    		document.getElementById("action").value="deleteAppointment";
    		frm.appId.value=id;
    		frm.sendType.value=tvalue;
    		frm.submit();
    	}	
    }
    
    //会议发布
    function doIsPublished(id,tvalue){
    	if(confirm("您确定要发布该会议吗？请注意，在发布完会议，邀请者将不能对该会议进行报名!!")){
    		document.getElementById("action").value="isPublishedAppointment";
    		frm.appId.value=id;
    		frm.sendType.value=tvalue;
    		frm.submit();
    	}	
    }
    function addMeeting(){
   		window.location.href='<%=baseURL %>/meeting_forward.jsp?action=toAddAppointment';
    }
	function goPage(page)
	 {
	    frm.num.value=page;
	 	frm.submit(); 
	 }
	 
	 function back(curren,sum){
	    if(curren==1){
	    alert("当前已经是第一页");return;
	    }	 	
	    frm.num.value=curren-1;
	 	frm.submit(); 
	 }
	 
	 function next(curren,sum){
	// window.showModelessDialog('http://localhost:8888/bbs/space/goweb.jsp?weburl=121.htm',window,'dialogHeight:510px;dialogWidth:640px;center:yes;resizable:yes;status:no;scroll:yes;help:no');
	//window.showModelessDialog('http://localhost:8888/bbs/vcity/resource/vresources/forward3D.jsp',window,'dialogHeight:710px;dialogWidth:840px;center:yes;resizable:no;status:no;scroll:yes;help:no');
	    if(curren==sum||sum==0){
	    alert("当前已经是最后一页");return;}
	    frm.num.value=curren+1;
	 	frm.submit(); 
	 }
	 
	 function goshowpage(sum) 
	 {var _value= document.getElementById('_num_').value;
	 if (_isNull_(_value)){alert('您还没有填写页号，请填写！');
	 document.getElementById('_num_').focus();return;
	 }
	 if (_value == 0){alert('页号不允许填写“0”，请修改！');
	 document.getElementById('_num_').focus();return;
	 }
	 if (!_isInt_(_value))
	 {alert('页号仅仅允许填写数字，请修改！');
	 document.getElementById('_num_').focus();return;
	 }
	 if(_value>sum){alert('页号大于总页数，请修改！');
	 document.getElementById('_num_').focus();return; 
	 }
		 frm.submit(); 
	 }
	 function _isNull_(his) {if (his.length == 0) return true;
	  for(var i=0;i<his.length;i++) {if(his.charAt(i)!=" ") break; 
	  }
	  if (i == his.length)return true;return false;}
	  function _isInt_(his) {var ret = true;
	  var flag = true;var NumStr="0123456789";
	  var chr;for (i=0;i<his.length;i++) {chr=his.charAt(i);
	  if (flag && chr == " ") continue;flag = false;
	  if (NumStr.indexOf(chr,0)==-1) {ret = false; break; }
	  }return ret;
	  }
	</script>	
</html>
