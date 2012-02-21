<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<title>查看问卷结果</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript">
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<div class="tdc" style="border-bottom:1px dashed #CCCCCC; line-height:40px;"><strong class="greenFont14">调查问卷结果</strong><c:if test="${isAnswer }">&nbsp; <span class="redFont12">(未完成)</span></c:if> </div>
<br />
<c:forEach items="${selectquestionList}" var="question" varStatus="questionStauts">
<div style="display:none;"><input type="hidden" name="question(${question.questionId}).questionId" value="${question.questionId}" /> </div>
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">

  <tr>
    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >${question.questionName }</strong></td>
  </tr>
  
  <c:if test="${question.questType eq 1}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input name="question(${question.questionId}).selectitemId" type="radio" disabled="disabled"  value="${item.itemId}" ${empty item.selectFlag?"":"checked" } />&nbsp;${item.itemDesc}</td>
	  </tr>
	  </c:forEach>
  </c:if>
  
  <c:if test="${question.questType eq 2}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
	  <tr>
	    <td height="30"><input name="question(${question.questionId}).selectitemId" type="checkbox" disabled="disabled" value="${item.itemId}" ${empty item.selectFlag?"":"checked" } />&nbsp;${item.itemDesc}</td>
	  </tr>
	  </c:forEach>
  </c:if>
  
</table>
</c:forEach>
<div align="center" style="padding:8px 0px; border-top:1px dashed #CCCCCC">
</div>
</fieldset>
</td></tr>
	<tr id="close1"><td align="center"> 
		<input type="button" name="edit" class="btn_BS" value="关  闭" onclick="finish();" />
	</td></tr><%--
	
	<tr id="close2" style="display: none"><td align="center"> 
		<input type="button" name="edit" class="btn_BS" value="关  闭" onclick="window.close()" />
		<input type="button" name="edit" class="btn_BS" value="返 回" onclick="history.back(-1);" />
	</td></tr>


--%></table>
</body>
<script type="text/javascript">
/*var isShowUserResult = '${isShowUserResult }';
if(isShowUserResult!=''){
	document.getElementById("close1").style.display = 'none';
	document.getElementById("close2").style.display = '';
}*/
var isAnswerSuccess = '${isAnswerSuccess}';
if(isAnswerSuccess!=''){
	alert("感谢您参与本次调查！");
}

function finish(){
	window.close();
}

function getXMLHTTPRequest() {
try {
req = new XMLHttpRequest();
} catch(err1) {
  try {
  req = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (err2) {
    try {
    req = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (err3) {
      req = false;
    }
  }
}
return req;
}
//定义引用事件
var actionhttp = getXMLHTTPRequest();
//传送场景事件
function actionTask(objId) {

	//随机数字，防止页面缓存影响
	var rannum=parseInt(Math.random()*(10000-51+1) + 51);
	var url="http://localhost:8888/bbs/task.jsp?action=doUserExcuteTask"
	var modurl = encodeURI(""+url+"&objId="+objId+"&Reload="+rannum+"&isRenGong=1");
  	actionhttp.open("GET", modurl, true);
 	actionhttp.onreadystatechange = GetRoomResponse;
 	actionhttp.send(null);
}
function GetRoomResponse() {
   if (actionhttp.readyState == 4) {
   if(actionhttp.status == 12029){
   	}
    else if(actionhttp.status == 200) {
       var mytext = actionhttp.responseText;
	   //alert(mytext);
       if(mytext!=""){
		    var sp=mytext.split(",");			
		    if(sp[0]!="0"){
				//alert(sp[0]+"======"+sp[1]);
				 openurl(sp[0],sp[1]);
			}
		    //窗口打开事件openurl(type,url)3D场景type=1，新窗口type=2
	   }
    }
    else
    {
    }
  } else {
  }
}
document.oncontextmenu=function(e){return false;}   //屏蔽鼠标右键
</script>
</html>