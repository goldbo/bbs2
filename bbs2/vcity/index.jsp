<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" />
<title>第一体验俱乐部研究员管理平台</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css"></head>
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/popup.js"></script>
<script>
var dialogclosepath="inc/close_off.gif";
</script>

<!--[if lt IE 7]>
 <style type="text/css">
 .logotop { behavior: url(inc/iepngfix.htc) }
 </style>
<![endif]-->
<% 
String userName = (String)request.getSession().getAttribute("jsprun_userss");
%>
<script>
/*接收弹出层返回值 -begin */
function localSurvery(num,rv){
	var iframe = document.getElementById('mainFrame');
	if(navigator.appName == 'Microsoft Internet Explorer'){   //如果IE
		return document.frames["mainFrame"].localSurvery(num,rv);
	} else {  
		return iframe.contentWindow.localSurvery(num,rv);
	}
}
function manualSurvey(num,rv){
	var iframe = document.getElementById('mainFrame');
	if(rv!=""){
		var rvs = rv.split("###");
		for(var i=0;i<rvs.length;i++){
			if(navigator.appName == 'Microsoft Internet Explorer'){   //如果IE
		 		document.frames["mainFrame"].manualSurvey(num,rvs[i]);
			} else {
				 iframe.contentWindow.manualSurvey(num,rvs[i]);
			}
		}
	}
	return true;
	
}
function experience(num,rv){
	var iframe = document.getElementById('mainFrame');
	if(navigator.appName == 'Microsoft Internet Explorer'){   //如果IE
		 document.frames["mainFrame"].document.getElementById("subTaskResId"+num).value= Nid;
		 document.frames["mainFrame"].document.getElementById("value("+num+").subTaskDesc").value= "前往："+label;
	} else {  
		iframe.contentDocument.document.getElementById("subTaskResId"+num).value= Nid;
		iframe.contentDocument.document.getElementById("value("+num+").subTaskDesc").value= "前往："+label;
	}
}
function getHostList(Nid,label,num){
	var iframe = document.getElementById('mainFrame');
	if(navigator.appName == 'Microsoft Internet Explorer'){   //如果IE
		return document.frames["mainFrame"].getHostList(Nid,label,num);
	} else {  
		return iframe.contentWindow.getHostList(Nid,label,num);
	}
}
function acceptPara(result){
	var iframe = document.getElementById('mainFrame');
	if(navigator.appName == 'Microsoft Internet Explorer'){   //如果IE
		return document.frames["mainFrame"].acceptPara(result);
	} else {  
		return iframe.contentWindow.acceptPara(result);
	}
}
function survery(name,id){
	var iframe = document.getElementById('mainFrame');
	if(navigator.appName == 'Microsoft Internet Explorer'){   //如果IE
		return document.frames["mainFrame"].survery(name,id);
	} else {  
		return iframe.contentWindow.survery(name,id);
	}
}

function topmenu(mid,url){
	for(var i=1; i<7; i++){
		document.getElementById('tm_id_'+i).className='menu_off';
	}
		document.getElementById('tm_id_'+mid).className='menu_on';
		document.getElementById('mainFrame').src=url;

}


function leftmenu(mid,url){
	for(var i=1; i<7; i++){
		document.getElementById('leftMenu_'+i).className='smenu_off';
	}
		document.getElementById('leftMenu_'+mid).className='smenu_on';
		document.getElementById('mainFrame').src=url;

}
</script>

<!--两个弹出层js -->
<script type="text/javascript" src="<%=baseURL %>/vcity/inc/popup.js"></script>
<script>
var dialogclosepath="<%=baseURL %>/vcity/inc/close_off.gif";
</script>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="69" style="background:url(<%=baseURL %>/vcity/images/top_bg.jpg) no-repeat right;">
		<table width="100%" height="69" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="310" align="right" valign="bottom"><div class="logotop" style="width:300px; height:66px; background:url(<%=baseURL %>/vcity/images/logo.png) no-repeat;">&nbsp;</div></td>
			<td>
				<table width="100%" height="69" border="0" cellpadding="0" cellspacing="0">
				  <tr>
					<td class="tdr">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							<td class="tdr"><img src="<%=baseURL %>/vcity/images/icon_3.jpg" width="16" height="12" align="absmiddle">&nbsp;&nbsp;当前用户： ${jsprun_userss }</td>
							<td></td>
							<c:if test="${! empty jsprun_userss }"> <td width="200" align="center"><img src="images/btn_exit.gif" width="83" height="20" onClick="location.href='<%=baseURL %>/logging.do?action=logout'" class="m_hand" align="absmiddle"></td></c:if>
						  </tr>
						</table>
					</td>
				  </tr>
				  <tr>
					<td valign="bottom">
						<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" id="qt_menu">
							  <tr><%--
								
								<td width="106" class="menu_on" id="tm_id_1" onClick="topmenu(1,'list.html')">任务列表&nbsp;&nbsp;×</td>
								<td width="5">&nbsp;</td>
								<td width="106" class="menu_off" id="tm_id_2" onClick="topmenu(2,'rw_info.html')">天翼手机&nbsp;&nbsp;×</td>
								<td width="5">&nbsp;</td>
								<td width="106" class="menu_off" id="tm_id_3" onClick="topmenu(3,'add_rw.html')">创建任务&nbsp;&nbsp;×</td>
								<td width="5">&nbsp;</td>
								<td width="106" class="menu_off" id="tm_id_4" onClick="topmenu(4,'add_resource.html')">新增资源&nbsp;&nbsp;×</td>
								<td width="5">&nbsp;</td>
								<td width="106" class="menu_off" id="tm_id_5" onClick="topmenu(5,'jiben3.html')">任务详情&nbsp;&nbsp;×</td>
								<td width="5">&nbsp;</td>
								<td width="106" class="menu_off" id="tm_id_6" onClick="topmenu(6,'add_tc.html')">创建调查&nbsp;&nbsp;×</td>
								<td>&nbsp;</td>
						  --%></tr>
							</table>
					</td>
				  </tr>
				</table>
			</td>
		  </tr>
		</table>
	</td>
  </tr>
  <tr>
    <td height="7" background="<%=baseURL %>/vcity/images/top_line.jpg"></td>
  </tr>
  <tr>
    <td>
		
		<!--主框架 -->
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" id="indexid">
		  <tr>
			<td>
			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" id="indexid">
		  <tr>
			<td width="175" height="28" background="<%=baseURL %>/vcity/images/menu_title_bg.jpg">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="30" align="center"><img src="<%=baseURL %>/vcity/images/icon_1.jpg" width="16" height="28"></td>
					<td id="linkName">会议管理</td>
					<td width="50"><img src="<%=baseURL %>/vcity/images/icon_text.jpg" width="41" height="28"></td>
				  </tr>
				</table>			</td>
			<td background="<%=baseURL %>/vcity/images/h_bg.jpg"></td>
			<td rowspan="2"><iframe scrolling="Auto" frameborder="0" width="100%" height="100%" name="mainFrame" id="mainFrame" src="<%=baseURL %>/meeting_forward.jsp?action=pageAppointmentByUserName"></iframe></td>
		  </tr>
		  <tr>
			<td bgcolor="#f7fcfd" valign="top">
				<table width="100%" border="0" cellspacing="5" cellpadding="0" id="smenu_divid_1">
				  <tr>
					<td>&nbsp;</td>
					<td width="126">&nbsp;</td>
				  </tr>
				  <tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_1.jpg" width="28" height="28"></td>
					<td>
						<div class="smenu_on" id="leftMenu_1" onClick="leftmenu(1,'<%=baseURL %>/meeting_forward.jsp?action=pageAppointmentByUserName');">会议管理</div></td>
				  </tr>
				    <tr>
				    <td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_2.jpg" width="28" height="28"></td>
				    <td><div class="smenu_off" id="leftMenu_2" onClick="leftmenu(2,'<%=baseURL %>/vcity/statistics/statisticsIndex.jsp');">统计分析</div></td>
				    </tr>
				    <tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_3.jpg" width="28" height="28"></td>
					<td>
						<div class="smenu_off" id="leftMenu_3" onClick="leftmenu(3,'<%=baseURL %>/vcityResourceAction.do?action=showVcityResourceList');">体验资源管理</div>					</td>
				  </tr>
				  <tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_4.jpg" width="28" height="28"></td>
					<td>
						<div class="smenu_off" id="leftMenu_4" onClick="leftmenu(4,'<%=baseURL %>/vcityTaskAction.do?action=showTaskList');">任务管理</div>					</td>
				  </tr>
				  <tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_5.jpg" width="28" height="28"></td>
					<td>
						<div class="smenu_off" id="leftMenu_5" onClick="leftmenu(5,'<%=baseURL %>/vcitySurveyAction.do?action=showVcitySureyList');">调查问卷</div>					</td>
				  </tr>
				
				
				  <%--<tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_3.jpg" width="28" height="28"></td>
					<td>
						<div class="smenu_off" id="leftMenu_6" onClick="leftmenu(6,'<%=baseURL %>/vcityResTypeAction.do?action=showVcityResTypeList&flag=0');">分类管理 </div></td>
				  </tr>
				  <tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_2.jpg" width="28" height="28"></td>
					<td>
						<div class="smenu_off" id="leftMenu_7" onClick="leftmenu(7,'<%=baseURL %>/vcityResoprateAction.do?action=showVcityResoprateList');">操作码管理</div></td>
				  </tr>
				 
				   --%>

				  <tr>
					<td height="30" align="right"><img src="<%=baseURL %>/vcity/images/menu_icon_2.jpg" width="28" height="28"></td>
					<td>
						<a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesIndex" target="_blank" style="text-decoration: none;"><div class="smenu_off" id="leftMenu_6"  <%--onclick="leftmenu(6,'<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesIndex');" --%> > 应用体验门户</div></a> </td>
				  </tr>
			  </table>
			  
			</td>
			<td width="5" background="<%=baseURL %>/vcity/images/h_bg.jpg"></td>
			</tr>
		</table>
				
			
			</td>
		  </tr>
		</table>
		<!--end 主框架 -->
	</td>
  </tr>
  <tr>
    <td height="25" align="center" background="<%=baseURL %>/vcity/images/bottom_bg.jpg">© Copyright 2010 WINGOTECH All rights reserved.</td>
  </tr>
</table>

<!-- 弹出框 -->
<div style="display:none;position: absolute; width: 650px; height: 418px; z-index: 100000; left: 187px; top: 113px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td class="tdl" id="tipTitle" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><img alt="close" class="m_hand" src="images/btn_window_c.gif" onClick="document.getElementById('tipDiv').style.display='none';document.getElementById('hiddenDiv').style.display='none';document.getElementById('KuanghiddenDiv').style.display='none';"></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="100%" name="actionFrame" src=""></iframe></td></tr>
</table>
</div>
<!-- 层2-->
<div style="display:none;position: absolute; width: 650px; height: 418px; z-index: 100000; left: 187px; top: 113px; background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv2">
<iframe scrolling="auto" frameborder="0" width="100%" height="100%" name="actionFrame2" src=""></iframe>
</div>
<!-- end 层2 -->
<div id="KuanghiddenDiv" name="KuanghiddenDiv" style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>
<div id="hiddenDiv" name="hiddenDiv" style="display:none;width:100%;height:100%;position: absolute; z-index: 1;background-color:#cccccc;top:0;left:0;FILTER: alpha(opacity=50)"></div>
<!-- end 弹出框 -->
</body>
</html>
<script type="text/javascript">
function openDiv(actionTitle,actionUrl,actionWidth,actionHeight)
{
	var left = document.body.clientWidth/2;
	var top = document.body.clientHeight/2;
	document.getElementById('tipDiv').style.top = top - actionHeight/2;
	document.getElementById('tipDiv').style.left = left - actionWidth/2;
	document.getElementById('tipDiv').style.width = actionWidth;
	document.getElementById('tipDiv').style.height = actionHeight;
	document.getElementById("tipDiv").style.display = "";
	document.getElementById("hiddenDiv").style.display = "";
	document.getElementById('KuanghiddenDiv').style.top = top - actionHeight/2 - 6;
	document.getElementById('KuanghiddenDiv').style.left = left - actionWidth/2 - 6;
	document.getElementById("KuanghiddenDiv").style.display ="";
	document.getElementById("KuanghiddenDiv").style.width= actionWidth+12;
	document.getElementById("KuanghiddenDiv").style.height= actionHeight+12;
	document.getElementById("tipTitle").innerHTML = actionTitle;
	actionFrame.location.href = actionUrl;
}

function openHref(url){
	var width = screen.width;
	var height = screen.height;
	window.open(url,"","toolbar=yes,status=no,menubar=yes,location=no,height="+height+",width="+width+",scrollbars=yes,resizable=yes");
}
</script>