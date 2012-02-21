<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>普通会员聊天窗口</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>
	<script type="text/javascript">var base = '<%=baseURL %>';</script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/chatService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
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

<body onload="initDiv();getChatNowByUsers();online();">
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
              <td  height="30" class="tdl">正在与 <strong class="orangeFont12">研究员</strong> 交流中：</td>
              <td class="tdr">&nbsp;</td>
            </tr>
            <tr>
            <input type="hidden" name="senderID" id="senderID" value="${senderID}" />
			<input type="hidden" name="reveiverID" id="reveiverID" value="${chatForm.recieverID }" />
			<input type="hidden" name="username" id="username" value="${userInfo.username }" />
			<input type="hidden" name="taskid" id="taskid" value="${taskid}" />
            <!-- 聊天显示区 -->
              <td colspan="2" height="100%">
					<div style="overflow: auto; height: 100%; width: 100%"
						id="chatlistcommon" class="textareastyle">
						<%--<div id="publicArea"></div>
					--%></div>
				</td>
            </tr>
            <tr>
              <td height="30" colspan="2">
			  	<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right">&nbsp;</td>
  </tr>
</table>

			  </td>
            </tr>
            <tr>
              <!-- 输入框 -->
				<td height="30" class="tdc" colspan="2">
				<textarea id="msg" name="msg" style="width:100%;height:200px;visibility:hidden;" ></textarea>
				</td>
            </tr>
            <tr>
            <td height="30" colspan="2" class="tdr">
            	<%--<td height="30" colspan="2" class="tdr"><input name="Submit22" type="button" class="btn_BS" value=" 关闭 " onClick="parent.location.href='bbs_rwlist.html';">
              --%>	<input name="Submit22" type="button" id="btnSend" class="btn_BS" value=" 发送 " onClick="sendMsg()">
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
                    <td width="100" height="26" class="tab_kp_on">相关资源</td>
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
                  	<!-- 相关资源显示区 -->
                    <td valign="top" id="URLArea">
                    	
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

	
	/*****************聊天用到的方法 begin*********************/
		var taskid = '${taskid}';
		var username = document.getElementById("username").value; 
	/*	function initTable(){
			var Inval = new Array();
			for(var i=0;i<1000;i++){	//初始化
			  	Inval[i] ="<tr style='display:none'><td id=t"+i+"></td></tr>";
			}
			var tabObj=document.getElementById("publicArea");
			var str1=Inval.join("");
			tabObj.innerHTML="<table id='publicAreaTbl'>"+str1+"</table>";
		} */
		
		
		var trs = new Array();
		var beginFlag = 0; 
		var endFlag = 0;
		var cflag = -1;
		var crid = 'chatIndex${senderID}${taskid}';
		
		function getChatNowByUsers(){
			chatService.getChatMsg(crid,cflag,toShowChatNow);
			setTimeout(function(){getChatNowByUsers();},3*1000);
		}
	
		function online(){
			setOnline(username,taskid);
		}
		function setOnline(username,taskid){
			chatService.setOnlineUser(username,taskid,function(bool){});
			setTimeout(function(){setOnline(username,taskid);},5*1000);
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
						tempSpeaker = "研究员";
					}
					tempArray.push(tempSpeaker);
					tempArray.push(" ：");
					tempArray.push(data[l].charInfo);
					
					if(data[l].isURL==1){
						addToURLArea(data[l].charInfo);
					}
					
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
	}
	 */
		function initDiv(){
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
					if(data[l].chatFrom!='${senderID}'){
						tempSpeaker = "研究员";
					}
					tempArray.push(tempSpeaker);
					tempArray.push(" ：");
					tempArray.push(data[l].charInfo);
					
					if(data[l].isURL==1){
						addToURLArea(data[l].charInfo);
					}
					
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
	
	function   go(n){
		var x = document.getElementById('chatlistcommon');
		x.scrollTop=(n-1)*38;
		
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
			return false;
		}
		chatService.inputMsgToMap(msg1,senderid,receiver,crid,'${chatForm.taskId}',0);  //保存聊天记录
		
		KE.html('msg', '');
			
	}

	function addToURLArea(chatInfo){
		var URLArea = document.getElementById("URLArea");
		URLArea.innerHTML += '<table width="98%" border="0" align="center" cellpadding="0" cellspacing="5" style="border-bottom:1px dashed #CCCCCC">'
						+	'<tr>'
                        + 	'<td height="30"><img src="<%=baseURL%>/vcity/images/icon_doc.gif" width="6" height="6" align="absmiddle">'+chatInfo+'</td>'
                        + 	'</tr>'
                        + 	'</table>';
	}




/*****************聊天用到的方法 end*********************/	
</script>
</body>
</html>
