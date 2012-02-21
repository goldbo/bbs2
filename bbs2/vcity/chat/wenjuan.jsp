<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择调查问卷</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcityQuestionService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
</head>

<body>
<html:form action="vcityResourceAction.do" method="post" styleId="vcityResourceForm" >
<input type="hidden" id="taskId" name="taskId" value="${taskId }" />
	<table width="98%" height="400" align="center" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3; margin-top:8px;">
            <tr>
              <td height="30" valign="top" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100"  height="26" id="local1" class="tab_kp_on" onclick="changeToLocal();">本地预设问卷</td>
                    <td width="100" id="td2" align="center"   class="tab_kp_off" onclick="changeToE();">e声网问卷</td>
                    <c:if test="${! empty tempSurveyList }"> <td width="150" id="td3" align="center"   class="tab_kp_off" onclick="changeToTemp();">本次任务临时调查问卷</td></c:if>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>			  </td>
            </tr>
			<tr id="tr1"><td>
<table width="98%" height="320" align="center" border="0" cellpadding="0" cellspacing="0" class="tableWrap">
            <tr>
              <td width="28%" height="40" class="greenFont12 tdl_B"><strong>调查问卷名称</strong></td>
              <td width="28%" class="tdr">&nbsp;</td>
				
              <td class="tdr"><input name="Submit23" type="button" id="local2" onclick="changeTo2();" class="btn_BS" value="本地其它调查" /></td>
            </tr>
            <tr>
              <td height="25" class="tdl_B"><strong>问卷名称：</strong></td>
              <td><strong>问卷标题名称：</strong></td>
              <td class="tdl_B"><strong>已选标题</strong>：</td>
            </tr>
            <tr>
              <td class="tdl" valign="top">
              <!-- 问卷名称 -->
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
              	<logic:notEmpty name="sureyList">
              		<logic:iterate id="surey" name="sureyList">
              			<tr>
			              <td height="28" id="sureyTD" class="tdl">
			              <input name="radiobutton" type="radio" value="${surey.sureyId }" onclick="changeBGColor(this)"/>
						  ${surey.sureyName }
						  </td>
			            </tr>
              		</logic:iterate>
              	</logic:notEmpty>
              </table>
              </td>
              
			  <td valign="top" bgcolor="#DCF1FA">
			  
			<!--  问卷标题名称 -->
			  <table  width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:8px">
               	<tbody id="tab_sTitle"></tbody>
                	
                
                <tr>
                  <td height="40" class="tdl"><a href="#" onclick="chooseAll(true)">全选</a>                    &nbsp;&nbsp;
                    <a href="#" onclick="chooseAll(false)">取消</a> </td>
                </tr>
              </table>
			  </td>
			  
			  
			  
			  <!-- 已选题目 -->
              <td rowspan="8" valign="top" class="tdl_B">
              	<ul class="HListCon" id="checkedItems">
	                  
              	</ul>              
              </td>
            </tr>
            
          </table>
		  <div align="center" style=" line-height:30px;">
		  <html:hidden name="vcityResourceForm" property="taskId"/>
		  <html:hidden name="vcityResourceForm" property="userName"/>
		  <input type="hidden" id="title" name="title" />
		    <input name="Submit2322" type="button" class="btn_BS" value=" 直接发送 " onclick="saveItems()"/>
		     <input name="Submit2322" type="button" class="btn_BS" value=" 保存并发送 " onclick="openTitle();"/>
&nbsp;&nbsp;
<input name="Submit2332" type="button" class="btn_BS" value=" 取消 " onclick="parent.ClosePop()"/>
		  </div>
		  </td>
		  </tr>
		   <tr style="display: none"  id="tr2" ><td valign="top" style="padding-top:5px;" >
            <table id="table2" width="97%" align="center" border="0"   cellspacing="0" cellpadding="0"  >
				<tr><td>
            	<iframe id="iframe" width="100%" height="450px;" src="shuaxuan.jsp?type=type" ></iframe>
            	</td></tr>
            	 </table>
             </td></tr>
             
               <tr style="display: none"  id="tr3" ><td valign="top" style="padding-top:5px;" >
            <table id="table3" width="97%" align="center" border="0"   cellspacing="0" cellpadding="0"  >
				<tr>
					<td height="25" class="tdl_B" ><strong>临时问卷名称：</strong></td>
					<td>&nbsp;</td>
				</tr>
				<br/>
				
            	<c:forEach items="${tempSurveyList }" var="temp" varStatus="st">
            		<tr>
            			<td height="28" align="center">
            				${temp.title } <input id="temp_${st.index }" type="hidden" value="${temp.questionIds }"  />
            			</td>
            			<td align="left"><input type="button" class="btn_BS" value="发 送" onclick="sendTemp(${st.index });" /> </td>
            		</tr>
            	</c:forEach>
            	
            	 </table>
             </td></tr>
		  </table>
<!-- 弹出框2 -->
<div style="display:none;position: absolute; width: 200px; height: 100px; z-index: 100000; right: 87px; top: 53px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv2">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td class="tdl" id="tipTitle2" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><span><a href="javascript:;" onClick="document.getElementById('tipDiv2').style.display='none';document.getElementById('KuanghiddenDiv2').style.display='none'">关闭</a></span></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="100" name="actionFrame2" src=""></iframe></td></tr>
</table>
</div>
<div id="KuanghiddenDiv2"  style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>

<!-- end 弹出框2 -->
</html:form>	  
</body>
<script type="text/javascript">

	
var flag = '${flag}'; 
var taskId = document.getElementById("taskId").value;
if(flag ==2){
	document.getElementById('local2').value='预设问卷';
	var url = "<%=baseURL %>/vcityResourceAction.do?action=showSureyByChat&taskId="+taskId;
	document.getElementById('local2').onclick = changeTo1;
	document.getElementById('local1').innerHTML='其它问卷';
}
if(flag ==1){
	document.getElementById('local1').innerHTML='预设问卷';
	document.getElementById('local2').value='其它问卷';
	var url = "<%=baseURL %>/vcityResourceAction.do?action=showSureyByChat&other=1&taskId="+taskId;
	document.getElementById('local2').onclick = changeTo2;
}
if('${isSuc}'!=''){

		if('${isSuc}'=='true'){
			var url = "<%=baseURL %>/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=manualTask";
			var ids = '${questionIds}';
			var subtaskid = '${subTaskId}';
			url += "&ids="+ids;
			url += "&task="+subtaskid;
			var str = "邀请您参与问卷调查， <a href=\""+url+"\" target=\"_blank\">进入调查</a>"; 
			if('${isSaveTemp}'=='true'){
				alert("保存并成功发送了！");
			}else {
				alert("发送成功！");
			}
			parent.sendURL(str);
			parent.ClosePop();
		}else{
			alert("保存失败！");
		}
	}
	//选择调查问卷时改变背景色，并查找问卷下的题目
	function changeBGColor(obj){
		var sureyTD = document.getElementsByName("sureyTD");
		if(sureyTD){
			for(var i=0;i<sureyTD.length;i++){
				sureyTD[i].bgColor = "";
			}
		}
		obj.parentNode.bgColor = "#DCF1FA";
		//查找问卷下的题目
		getSureyTitle(obj.value);
	}
	
	function getSureyTitle(sureyId){
		vcityQuestionService.findVcityQuestionBySureyId(sureyId,callBack);
	}
	
	function callBack(data){
		var tabSTitle = document.getElementById("tab_sTitle");
		while(tabSTitle.childNodes.length>0){
			tabSTitle.deleteRow(-1);
		}
		
		if(data!=null){
		var str = "";
			for(var i=0;i<data.length;i++){
				//var newRow = tabSTitle.insertRow();    
			    //var oCell = newRow.insertCell();
			   // oCell.innerHTML = '<input type="checkbox" name="checkbox" onclick="addToCheckItems(this)" value="'+data[i].questionId+'" /><font id="title'+data[i].questionId+'">'+data[i].questionName+'</font>';    
				//str += "<tr><td>";
				//str += "<input type='checkbox' name='checkbox' onclick='addToCheckItems(this)' value='"+data[i].questionId+"' /><font id='title"+data[i].questionId+"'>"+data[i].questionName+"</font>";
				//str += "</td></tr>";
				var newRow = document.createElement("tr");
				var oCell = document.createElement("td");
				oCell.innerHTML = '<input type="checkbox" name="checkbox" onclick="addToCheckItems(this)" value="'+data[i].questionId+'" /><font id="title'+data[i].questionId+'">'+data[i].questionName+'</font>';    
				newRow.appendChild(oCell);
				tabSTitle.appendChild(newRow);
			}
				//$('#tabSTitle').append(str);
		}
		
	}
	
	//全选
	function chooseAll(chooseFlag){
		//全选
		if(chooseFlag){
			var surveyTitles = document.getElementsByName("checkbox");
			if(surveyTitles){
				for(var i=0;i<surveyTitles.length;i++){
					surveyTitles[i].checked = true;
					addLi(surveyTitles[i].value);
				}
			}
		}else{	//取消
			var surveyTitles = document.getElementsByName("checkbox");
			if(surveyTitles){
				for(var i=0;i<surveyTitles.length;i++){
					surveyTitles[i].checked = false;
				}
			}
		}	
	}
	
	//选择题目
	function addToCheckItems(obj){
		if(obj.checked){
			addLi(obj.value);
		}
	}
	//在已选标题添加记录
	function addLi(titleId){
		var checkedItems = document.getElementById("checkedItems");
		var temp = document.getElementById("li"+titleId);
		if(temp==null){
			var li= document.createElement("li");
		  	li.id = 'li'+titleId;
		  	li.innerHTML='<input name="chkTitle" type="checkbox" value="'+titleId+'" checked="checked" onclick="removeLi('+titleId+')"/>'+document.getElementById("title"+titleId).innerHTML;
			checkedItems.appendChild(li);
		}
	}
	
	function removeLi(titleId){
		var checkedItems = document.getElementById("checkedItems");
		var remli = document.getElementById('li'+titleId);
		checkedItems.removeChild(remli);
	}
	
	//保存
	function saveItems(){
		var selectItems = "";
		var chkTitle = document.getElementsByName("chkTitle");
		if(chkTitle){
			for(var i=0;i<chkTitle.length;i++){
				if(chkTitle[i].checked){
					selectItems += chkTitle[i].value+",";
				}
			}
		}
		if(selectItems!=""){		
			selectItems = selectItems.substring(0,selectItems.length-1);
			vcityResourceForm.action = "vcityResourceAction.do?action=saveSubTaskByQuestion&questionIds="+selectItems;
			vcityResourceForm.submit();		
		}
	}
	
function changeTo1(){
	vcityResourceForm.action =  "vcityResourceAction.do?action=showSureyByChat";
		vcityResourceForm.submit();
 
}
function changeTo2(){
	vcityResourceForm.action =  "vcityResourceAction.do?action=showSureyByChat&other=1";
	vcityResourceForm.submit();
 
}
function changeToE(){
	document.getElementById("local1").className = "tab_kp_off";
	document.getElementById("td3").className = "tab_kp_off";
	document.getElementById("td2").className = "tab_kp_on";
	document.getElementById("tr1").style.display = 'none';
	document.getElementById("tr2").style.display = '';
	document.getElementById("tr3").style.display = 'none';

}
function changeToLocal(){
	document.getElementById("td2").className = "tab_kp_off";
	document.getElementById("td3").className = "tab_kp_off";
	document.getElementById("local1").className = "tab_kp_on";
	document.getElementById("tr2").style.display = 'none';
	document.getElementById("tr1").style.display = '';
	document.getElementById("tr3").style.display = 'none';
}
function changeToTemp(){
	document.getElementById("td2").className = "tab_kp_off";
	document.getElementById("local1").className = "tab_kp_off";
	document.getElementById("td3").className = "tab_kp_on";
	document.getElementById("tr2").style.display = 'none';
	document.getElementById("tr1").style.display = 'none';
	document.getElementById("tr3").style.display = '';
}
/*接收e声网问卷 */
function survery(name,id){
	parent.survery(name,id);
}


function openDiv2(actionTitle,actionUrl,actionWidth,actionHeight)
{
	var left = document.body.clientWidth/2;
	var top = document.body.clientHeight/2;
	document.getElementById('tipDiv2').style.top = top -  parseInt(actionHeight/2);
	document.getElementById('tipDiv2').style.left = left -  parseInt(actionWidth/2);
	document.getElementById('tipDiv2').style.width = parseInt(actionWidth);
	document.getElementById('tipDiv2').style.height = parseInt(actionHeight);
	document.getElementById("tipDiv2").style.display = ""; 
	document.getElementById('KuanghiddenDiv2').style.top = top -  parseInt(actionHeight/2) - 6;
	document.getElementById('KuanghiddenDiv2').style.left = left -  parseInt(actionWidth/2) - 6;
	document.getElementById("KuanghiddenDiv2").style.display ="";
	document.getElementById("KuanghiddenDiv2").style.width= parseInt(actionWidth) + 17;
	document.getElementById("KuanghiddenDiv2").style.height= parseInt(actionHeight)+17;
	document.getElementById("tipTitle2").innerHTML = actionTitle;
	actionFrame2.location.href = actionUrl;
}
function colseDiv2(){
	document.getElementById('tipDiv2').style.display='none';
	document.getElementById('KuanghiddenDiv2').style.display='none'
}

function openTitle(){
	var selectItems = "";
		var chkTitle = document.getElementsByName("chkTitle");
		if(chkTitle){
			for(var i=0;i<chkTitle.length;i++){
				if(chkTitle[i].checked){
					selectItems += chkTitle[i].value+",";
				}
			}
		}
		if(selectItems!=""){	
			openDiv2('输入问卷标题','<%=baseURL %>/vcity/chat/addTempSurvey.jsp',500,138);	
		}
}

/* 保存临时问卷和子任务 */
function saveTempSurvey(title){
	document.getElementById("title").value = title;	
	colseDiv2();
	saveItems();	
	return true;
}

/* 发送已经保存的临时问卷 */
function sendTemp(id){
	var questionIds = document.getElementById("temp_"+id).value;
	vcityResourceForm.action = "vcityResourceAction.do?action=saveSubTaskByQuestion&questionIds="+questionIds;
	vcityResourceForm.submit();
}
</script>

</html>
