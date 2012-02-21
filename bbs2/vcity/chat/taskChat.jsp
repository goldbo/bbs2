<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>研究员聊天窗口</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>
<!-- ke js -->
<script type="text/javascript">var base = '<%=baseURL %>';</script>
<!--两个弹出层js -->
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/popup.js"></script>
<script>var dialogclosepath="<%=baseURL%>/vcity/inc/close_off.gif";</script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/chatService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script src="<%=baseURL%>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script charset="utf-8" src="<%=baseURL %>/vcity/inc/js/kindeditor.js"></script>
		<script type="text/javascript">
		
			KE.show({
				id : 'msg',
				resizeMode : 0,
				allowPreviewEmoticons : false,
				allowUpload : false,
				items : [
				'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				'|', 'emoticons', 'link'],
				
				afterCreate : function(id) {
	            KE.event.ctrl(document, 13, function() {
	                KE.util.setData(id);
	                sendMsg();
	            });
	            KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
                KE.util.setData(id);
	              sendMsg();
	            });
	        }
			}); 
		</script>

<body onLoad="test();">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="padding:8px">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="80%">
	
		<table width="99%" height="100%" border="0" cellpadding="0" cellspacing="1" class="tableWrap" style="margin-top:0px">
            <tr>
              <td height="25" colspan="2" class="greenFont12 tdl_B"><strong>${task.taskName }</strong></td>
            </tr>
            <tr>
              <input type="hidden" name="senderID" id="senderID" value="${userInfo.uid }" />
			  <input type="hidden" name="reveiverID" id="reveiverID" value="" />	
              <td height="30" class="tdl">&nbsp;<span id="chatinfo" style="display: none">正在与 <strong class="orangeFont12" id="uName"></strong> 会员交流中：</span></td>
              <td class="tdr"><a href="#" onClick="ShowIframe('查看任务基本信息','<%=baseURL %>/vcityTaskAction.do?action=taskInfo&taskId=${taskid}&flag=1',700,500)">查看任务基本信息</a></td>
            </tr>
            <tr>
            
              <!-- 聊天显示区 -->
              <td  colspan="2" height="100%">
					<div  style="overflow: auto; height: 100%; width: 100%"
						id="chatlistcommon" class="textareastyle">
						<%--<div id="publicArea"></div>
					--%></div>
				</td>
            </tr>
            <tr>
              <td height="30" colspan="2">
			  	<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0">
    
              <tr>

			<td align="right" id="userBehavior" style="display: none">
				<%--<input name="Submit24" type="button" class="btn_BS"
					value="体验模型"
					onClick="ShowIframe('体验模型','<%=baseURL %>/vcityResourceAction.do?action=showResourcesByChat&taskId=${chatForm.taskId}&userName='+escape(encodeURIComponent(document.getElementById('uName').innerHTML)),630,500)">
				--%><input name="Submit23" type="button" class="btn_BS"
					value="调查问卷"
					onClick="ShowIframe('调查问卷','<%=baseURL %>/vcityResourceAction.do?action=showSureyByChat&taskId=${chatForm.taskId}&userName='+escape(encodeURIComponent(document.getElementById('uName').innerHTML)),780,420)">

				<input name="Submit25" type="button" class="btn_BS"
					value="调查结果"
					onClick="showResult();">
				<input name="Submit252" id="btnFinish" type="button" class="btn_BS"
					value="结束交流"
					onClick="finishChat()">
				
					
			</td>
		</tr>
</table>

			  </td>
            </tr>
            <tr>
            <!-- 输入框 -->
				<td height="30" class="tdc" colspan="2">
				<textarea id="msg" name="msg" style="width:100%;height:200px;visibility:hidden;" onkeypress="ctrlEnter(event)"></textarea>
					<%--<div class="textareastyle" style="overflow: auto; width: 100%; height: 96%; padding: 3px; word-wrap: break-word;" contentEditable="true" id="msg"></div>
				--%></td>
			</tr>
            <tr>
              <td height="30" colspan="2" class="tdr">
              	<input name="Submit22" type="button" id="btnSend" class="btn_BS" value=" 发送 " onClick="sendMsg()">
              </td>
            </tr>
      </table>
		
	</td>
    <td>
	
	<table width="99%" height="100%" align="right" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100" height="26" class="tab_kp_on">在线会员</td>
                    <td width="100" align="center"  class="tab_kp_off">&nbsp;</td>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>			  </td>
            </tr>
            <tr>
              <td valign="top">
			  <table width="96%" height="98%" border="0" align="center" cellpadding="0" cellspacing="1" style="margin:8px 0px; border:1px solid #c6d2e3; background:url(<%=baseURL%>/common/images/images/bg.gif) top repeat-x">
                  <tr>
                    <td height="25">&nbsp;</td>
                  </tr>
                  <tr>
                    <td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
						<!-- 用户列表 -->
						<tbody id="usertab" >
						<logic:notEmpty name="userList">
							<logic:iterate id="users" name="userList">
								<tr id="tr_${users[0] }">
									<td width="30" height="23" align="center"><img src="<%=baseURL%>/vcity/images/m_icon${users[2] }.gif" width="16" height="16"></td>
									<td class="blackFont12"><a href="#" onClick="toChat('${users[0]}','${users[1] }','${users[2] }')">${users[1] }</a></td>
									<td width="60" align="center" id="isChatOrOnline_${users[0] }"><c:if test="${users[2] ==1}"> 在线</c:if><c:if test="${users[2] ==0}"> 离线</c:if></td>
									<td width="30" align="center" id="isShow_${users[0] }" onClick="finishChat(${users[0] });">&nbsp; </td>
								  </tr>
							</logic:iterate>
						</logic:notEmpty>
						</tbody>
						  </table>
					
					</td>
                  </tr>
                  <tr><td><%--
                  		 用于动态获取用户列表后显示是否交流中 
                  		--%><logic:notEmpty name="userList">
							<logic:iterate id="users" name="userList">
								<input type="hidden" id="isChating_${users[0] }" value="0"/>
							</logic:iterate>
						</logic:notEmpty>
                  	</td>
                  </tr>
                </table>              </td>
            </tr>
          </table>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

<script type="text/javascript">
 

	var crid = '';
	var taskid = '${taskid}';
	var trs = new Array();
		var beginFlag = 0; 
		var endFlag = 0;
		var cflag = -1;
		var t;

	function toChat(userId,userName,isOnline){  //userId是scenetm表的ID标识
		
		if(isOnline=='0'){
			alert("该用户不在线");
			return false;
		}
		document.getElementById('userBehavior').style.display = '';
		document.getElementById('chatinfo').style.display = '';
		document.getElementById('uName').innerHTML = userName;
		document.getElementById('isChatOrOnline_'+userId).innerHTML = '交流中';
		document.getElementById('isShow_'+userId).innerHTML = '';
		document.getElementById('reveiverID').value = userId;
		document.getElementById('btnSend').onclick = function(){ sendMsg();}
		document.getElementById('btnFinish').onclick = function(){ finishChat(userId);}
		document.getElementById('isChating_'+userId).value = 1;
		
		//初始化聊天显示区
		//initTable();
		
		initDiv();
		
		beginFlag = 0; 
		endFlag = 0;
		
		//获取聊天记录
		
		crid = 'chatIndex'+userId+taskid;
		getChatNowByUsers(crid,1);
	}
	
	function finishChat(userId){
		//结束
		KE.html('msg', '本次交流任务已完成，感谢您的参与!');
			//document.getElementById('msg').innerHTML = "本次交流任务已完成，感谢您的参与!";
			sendMsg();
			crid = 'chatIndex'+userId+taskid;
			setTimeout(function(){chatService.finishCurrentChat(crid,function (num){});},7*1000);
			
		
		//隐藏
		//var userId = document.getElementById('reveiverID').value;
		document.getElementById('userBehavior').style.display = 'none';
		document.getElementById('chatinfo').style.display = 'none';
		document.getElementById('uName').innerHTML = '';
		document.getElementById('isChatOrOnline_'+userId).innerHTML = '在线';
		document.getElementById('isShow_'+userId).innerHTML = '';
		document.getElementById('reveiverID').value = "";
		document.getElementById('btnSend').onclick = '';
		document.getElementById('isChating_'+userId).value = 0;
	}
	
	function sendSub(title,url,height,width){
		
	}
	
	/*****************聊天用到的方法 begin*********************/
		
	/*	function initTable(){
			var Inval = new Array();
			for(var i=0;i<1000;i++){	//初始化
			  	Inval[i] ="<tr style='display:none'><td id=t"+i+"></td></tr>";
			}
			var tabObj=document.getElementById("publicArea");
			var str1=Inval.join("");
			tabObj.innerHTML="<table id='publicAreaTbl'>"+str1+"</table>";
		} */
		
		
		
		function getChatNowByUsers(crid,num){
			//如果为1,停止其它聊天刷新,切换到当前聊天窗口
			if(num==1){
				clearTimeout(t); 
				cflag = -1;
			}
			chatService.getChatMsg(crid,cflag,toShowChatNow);
			t = setTimeout(function(){getChatNowByUsers(crid,0);},3*1000);   //数值与普通用户交流界面要一致
		}
	
	/*
	function toShowChatNow(data){
		var table3 = document.getElementById('publicArea');
		if((data!=null && data!="")){
			cflag = 11;
			beginFlag = endFlag;
			var dlen=data.length;
			var templen = dlen;
			for(var l=0;l<templen;l++){
				if(data[l].charInfo!=null&&data[l].charInfo!=""){
					var tempArray = new Array();
					var tempSpeaker = '我';
					//正常的发言
					if(data[l].chatFrom!='${userInfo.uid}'){
						tempSpeaker = document.getElementById('uName').innerHTML;
					}
					tempArray.push(tempSpeaker);
					tempArray.push(" ：");
					tempArray.push(data[l].charInfo);
					
					if(endFlag>=1000){
						endFlag = 0;
						trs[endFlag]=tempArray.join("");
					}else{
						trs[endFlag]=tempArray.join("");
					}
					endFlag++;
				}
			}
			var trToTable = new Array();
			if(beginFlag<endFlag){
				for(var i=beginFlag;i<beginFlag+templen;i++){
					trToTable.push(trs[i]);
				}
				for(var k=0;k<trToTable.length;k++){
					var td = document.getElementById('t'+(beginFlag+k));
					td.innerHTML = trToTable[k];
					td.parentNode.style.display = '';
				}
				
			}else{
				for(var i=endFlag+200;i<1000;i++){
					trToTable.push(trs[i]);
				}
				for(var j=0;j<endFlag;j++){
					trToTable.push(trs[j]);
				}
				
				trs.length = 0;
				for(var m=0;m<trToTable.length;m++){
					trs.push(trToTable[m]);
				}
				var chatTable1 = document.getElementById("publicAreaTbl");
				for(var n=0;n<1000;n++){
					chatTable1.rows[n].style.display = "none";
				}
				for(var k=0;k<trToTable.length;k++){
					var td = document.getElementById('t'+k);
					td.innerHTML = trToTable[k];
					td.parentNode.style.display = '';
				}
				beginFlag = trToTable.length;
				endFlag = trToTable.length;
			}
		}
	} */
	
	/*****************获取数据测试*********************/	  
	
	function initDiv(){
		var temp = document.getElementById("chatlistcommon");
		while(temp.hasChildNodes()){
			temp.removeChild(temp.lastChild);
		}
			var Inval = new Array();
			for(var i=0;i<1000;i++){	//初始化
			  	Inval[i] ="<p id='p"+i+"'></p>";
			}
			var tabObj=document.getElementById("chatlistcommon");
			var str1=Inval.join("");
			tabObj.innerHTML=str1;
		}
	
	function toShowChatNow(data){
		var table3 = document.getElementById('chatlistcommon');
		if((data!=null && data!="")){
			cflag = 11;
			beginFlag = endFlag;
			var dlen=data.length;
			var templen = dlen;
			for(var l=0;l<templen;l++){
				if(data[l].charInfo!=null&&data[l].charInfo!=""){
					var tempArray = new Array();
					var tempSpeaker = '我';
					//正常的发言
					if(data[l].chatFrom!='${userInfo.uid}'){
						tempSpeaker = document.getElementById('uName').innerHTML;
					}
					tempArray.push(tempSpeaker);
					tempArray.push(" ：");
					tempArray.push(data[l].charInfo);
					if(endFlag>=1000){
						endFlag = 0;
						trs[endFlag]=tempArray.join("");
					}else{
						trs[endFlag]=tempArray.join("");
					}
					endFlag++;
				}
				
			}
			var trToTable = new Array();
			var str = "";
			if(beginFlag<endFlag){
				for(var i=beginFlag;i<beginFlag+templen;i++){
					trToTable.push(trs[i]);
				}
				for(var k=0;k<trToTable.length;k++){
					var td = document.getElementById('p'+(beginFlag+k));
					td.innerHTML = trToTable[k];
				}
					go(endFlag);
			
			}else{
				for(var i=endFlag+200;i<1000;i++){
					trToTable.push(trs[i]);
				}
				for(var j=0;j<endFlag;j++){
					trToTable.push(trs[j]);
				}
				
				trs.length = 0;
				for(var m=0;m<trToTable.length;m++){
					trs.push(trToTable[m]);
				}
				var chatTable1 = document.getElementById("chatlistcommon");
    			var rollText_k=chatTable1.getElementsByTagName("p").length;
				for(var n=0;n<1000;n++){
					 document.getElementById('p'+n).style.display = "none";
				}
				for(var k=0;k<rollText_k;k++){
					var td = document.getElementById('p'+k);
					td.innerHTML = trToTable[k];
				}
				go(endFlag);
				beginFlag = trToTable.length;
				endFlag = trToTable.length;
			}
		}
	}
	
	function   go(n){
		var x = document.getElementById('chatlistcommon');
		x.offsetHeight;
		x.scrollTop=0;
		x.scrollTop=(n-1)*38;
	}
	
	
	/***************** 获取数据测试结束*********************/
	
	//去左空格; 
	function ltrim(s){ 
		return s.replace( /^\s*/, ""); 
	} 
	
	//去右空格; 
	function rtrim(s){ 
		return s.replace( /\s*$/, ""); 
	} 
	
	//去左右空格; 
	function trim(s){ 
		return rtrim(ltrim(s)); 
	}
	
	
	function sendMsg() {
			KE.sync('msg');
		//发言人及接收人
		//var sender = document.getElementById("senderName").value; // 获得发送者名字
		var senderid = document.getElementById("senderID").value;  //发送者ID(Session的ID，非用户表中的ID)
		var receiver = document.getElementById("reveiverID").value;  //获得接受者id
		
		//正常发言的内容
		var msg1 = document.getElementById("msg").value; // 获得消息内容
		msg1 = msg1.replace(/<p>/g,"");
		msg1 = msg1.replace(/<\/p>/g,"<br>");
		
		msg1 = msg1.replace(new RegExp("&nbsp;", 'gm'), " ");
		
		//内容不能为空
		if(trim(msg1)==""&& trim(msg1.replace(new RegExp("<BR>","gm"),""))==""){
			//alert("输入内容不能为空！");
			return false;
		}
		chatService.inputMsgToMap(msg1,senderid,receiver,crid,'${chatForm.taskId}',0);  //保存聊天记录

		KE.html('msg', '');
			
	}

	function sendURL(str){
		var senderid = document.getElementById("senderID").value;  //发送者ID
		var receiver = document.getElementById("reveiverID").value;  //获得接受者id
		
		chatService.inputMsgToMap(str,senderid,receiver,crid,'${chatForm.taskId}',1);
	}

/*****************聊天用到的方法 end*********************/	


	
	/**************DWR获取用户列表  start***************/
	//定时获取在线用户
	 function test(){
	 	getOnlineUser(taskid);
	 }
	function getOnlineUser(taskid){
		chatService.getOnlineList(taskid,showOU);
		setTimeout(function(){ getOnlineUser(taskid);},5*1000);
	}
	
	function showOU(data){
	var tab = document.getElementById("usertab");
		while(tab.hasChildNodes()){
			tab.removeChild(tab.lastChild);
		}
		var str = "";
		for(var i=0;i<data.length;i++){
			 var isChating = document.getElementById('isChating_'+data[i][0]).value;
			var temp = data[i][2];
			var online = "";
			if(temp==1){
				online = "在线";
			}
			if(temp==0){
				online = "离线";
			}
			str += "<tr id='tr_"+data[i][0] +"'>";
			str += "<td width='30' height='23' align='center'><img src='<%=baseURL%>/vcity/images/m_icon"+temp+".gif' width='16' height='16'></td>";
			str += "<td class='blackFont12'><a href='#' onclick=\"toChat('"+data[i][0]+"','"+data[i][1]+"','"+data[i][2]+"')\">"+data[i][1]+"</a></td>";
			if(isChating=='0'){
				str += 	"<td width='60' align='center' id='isChatOrOnline_"+data[i][0]+"'>"+online+"</td>";
				str += " <td width='30' align='center' id='isShow_"+data[i][0]+"' >&nbsp;</td>";
			} else {
				str += 	"<td width='60' align='center' id='isChatOrOnline_"+data[i][0]+"'>交流中</td>";
				str += " <td width='30' align='center' id='isShow_"+data[i][0]+"' >&nbsp;</td>";
			}
			str += "</tr>";
			
		}
		$('#usertab').append(str);
	}	
	/***************DWR获取用户列表  end*******************/
	
	
function survery(name,id){
		var url = "http://www.esaying.cn/wingo/do-survey-dispense.action?investigationId="+id;
		var str = "邀请您参与e声网"+name+", <a href=\""+url+"\" target=\"_blank\">进入调查</a>"; 
		sendURL(str);
	alert("问卷发送发功！");
   ClosePop();
   return true;
}

function showResult(){
	var username = document.getElementById('uName').innerHTML;
	var width = screen.width;
	var height = screen.height;
//	　window.open ('<%=baseURL %>/vcityQuestionresultAction.do?action=showUserSurveyResultList&taskId=${taskid}&userName='+escape(encodeURIComponent(username)), 'newwindow', 'height='+height+', width='+width+', top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes')
	ShowIframe('问卷结果','<%=baseURL %>/vcityQuestionresultAction.do?action=showUserSurveyResultList&taskId=${taskid}&userName='+escape(encodeURIComponent(username)),630,420);
}
</script>
</body>
</html>
