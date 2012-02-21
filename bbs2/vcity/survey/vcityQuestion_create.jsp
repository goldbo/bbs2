<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/popup.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcitySureyService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcityQuestionService.js"></script>
<script type="text/javascript">
	var itemIndex = 5;
	function saveVcityQuestion(){
		if(checkForm()){
			vcityQuestionForm.submit();	
		}else{
			return false;
		}
	}
	function resetVcityQuestion(){
		var num = document.getElementById("itemNum").value;
		document.getElementById("questionName").value="";
		for(var i=1;i<=num;i++){
			document.getElementById("value("+i+").itemDesc").value="";
		}
		
	}
	function updateVcityQuestion(questionId){
		vcityQuestionForm.action = 'vcityQuestionAction.do?action=updateVcityQuestion&id='+questionId;
		vcityQuestionForm.submit();
	}
	function downVcityQuestionIndex(questionId){
		vcityQuestionForm.action = 'vcityQuestionAction.do?action=updateVcityQuestionIndex&todo=down&id='+questionId;
		vcityQuestionForm.submit();
	}
	function upVcityQuestionIndex(questionId){
		vcityQuestionForm.action = 'vcityQuestionAction.do?action=updateVcityQuestionIndex&todo=up&id='+questionId;
		vcityQuestionForm.submit();
	}
	function setQuestionTemplate(questionId){
		vcityQuestionForm.action = 'vcityQuestionAction.do?action=setQuestionTemplate&id='+questionId;
		vcityQuestionForm.submit();
	}
	function addTemplateQuestion(questionId){
		$.ajaxSetup({cache:false});
		$.get("vcityQuestionAction.do?action=addTemplateQuestion", {id: questionId }, function(data){
				eval("template = " + data);
				vcityQuestionForm.questionName.value = template.questionName;
				if(template.vcityQuestionitem != null){
				//alert(template.index);
					if(template.index>4){
						for(var i=1; i <= 4; i++ )
						{
						document.forms["vcityQuestionForm"].elements["value("+i+").itemDesc"].value = template.vcityQuestionitem[i-1].itemDesc;
						}
						for(var j=5; j <= template.index; j++){
							var str = "<tr id='item"+j+"'><td height='30' align='right'>选项" + j + "：</td>"
       						str += "<td><input type='text' name='value(" + j + ").itemDesc' value='' style='width:300px;'>";
							str +="&nbsp;<a href='javascript:void(0);' onclick='delItem("+j+")'><img src='<%=baseURL%>/vcity/images/check_error.gif' border='0' align='absmiddle'></a>";
							str += "<input type='hidden' name='value(" + j + ").itemIndex' value='" + j + "'></td></tr>";
							$('#questionInfo').append(str);
							document.forms["vcityQuestionForm"].elements["value("+j+").itemDesc"].value = template.vcityQuestionitem[j-1].itemDesc;
						}
					}else{
						for(var k=1; k <= 4; k++ )
						{
						document.forms["vcityQuestionForm"].elements["value("+k+").itemDesc"].value = template.vcityQuestionitem[k-1].itemDesc;
						}
					}

				}
		  });
	}
	
function delTemplateQuestion(id){
		vcityQuestionService.deleteQuestionTemplate(id,function test(data){
			if(data){
				document.getElementById("template_"+id).style.display = "none";
			}
	});
}
	function completeVcityQuestion(){
	if(${fn:length(vcitySurey.vcityQuestion)}<1){
		ShowAlert("","没有填写任何问题！",180,100);
		return false;
	}
		vcityQuestionForm.action = 'vcityQuestionAction.do?action=showVcityQuestionList';
		vcityQuestionForm.submit();
	}
	function deleteVcityQuestion(questionId){
		if(!confirm('是否删除此问题')){
			return false;
		}
		vcityQuestionForm.action = 'vcityQuestionAction.do?action=deleteVcityQuestion&id='+questionId;
		vcityQuestionForm.submit();
	}
	function addQuestionItem(){
			var index = itemIndex++;
			var num = document.getElementById("itemNum").value;
			document.getElementById("itemNum").value=num+1;
		//alert(	);
		var str = "<tr id='item"+index+"'><td height='30' align='right'>选项" + index + "：</td>"
        str += "<td><input type='text' name='value(" + index + ").itemDesc' value='' style='width:300px;'>";
        str +="&nbsp;<a href='javascript:void(0);' onclick='delItem("+index+")'><img src='<%=baseURL%>/vcity/images/check_error.gif' border='0' align='absmiddle'></a>";
        str += "<input type='hidden' name='value(" + index + ").itemIndex' value='" + index + "'></td></tr>";
        $('#questionInfo').append(str);
	}
	
	/* 修改－－增加选项 */
	function addQuestionItemEdit(questionId,itemsize){
	alert(itemsize);
			var num = document.getElementById("q"+questionId).value;
			num = parseInt(num)+10;
		var str = "<tr id='q"+questionId+"item"+num+"'><td height='30' align='right'>选项" + (itemsize+1) + "：</td>"
        str += "<td><input type='text' name='question("+questionId+").item(" + num + ").itemDesc' value='' style='width:300px;'>";
        str +="&nbsp;<a href='javascript:void(0);' onclick='delQueItem("+questionId+","+num+")'><img src='<%=baseURL%>/vcity/images/check_error.gif' border='0' align='absmiddle'></a>";
        str += "<input type='hidden' name='question("+questionId+").item(" + num + ").itemIndex' value='" + (itemsize+1) + "'></td></tr>";
        $('#tab'+questionId).append(str);
	}
	
	function delQueItem(questionId,id){
		$('#q'+questionId+'item'+id).remove();
	}
	/*  结束  */
	function delItem(id){
		document.getElementById("item"+id).style.display="none";
		document.getElementById("value("+id+").itemDesc").value="";
		document.getElementById("value("+id+").itemIndex").value="";
		//document.getElementById(id).value="";
	}
	
	function delItemOnDatabase(questionId,itemid){
		$('#q'+questionId+'item'+itemid).remove();
		vcitySureyService.deleteItemById(itemid,function back(data){});
	}
	
	function editVcitySurvey(id){
	location.href = '<%=baseURL%>/vcitySurveyAction.do?action=editVcitySurey&todo=update&id='+id;
	location.submit();
	}
	function cancel(id){
		location.href = '<%=baseURL%>/vcitySurveyAction.do?action=deleteVcitySurey&todo=cancel&id='+id;
		location.submit();
	}
function checkForm(){
	var questionName = document.getElementById("questionName").value;
	if($.trim(questionName)==""){
		alert("请填写问卷名称！");
		return false;
	}
	var num = document.getElementById("itemNum").value;
	var flag = false;
	for(var i=1;i<=num;i++){
		var value = document.getElementById("value("+i+").itemDesc").value;
		if($.trim(value)!=""){
			flag = true;
			break;
		}
	}
	if(!flag){
		alert("至少填写一项选项内容！");
		return false;
	}
	return true;
	
}
</script>
</head>

<body>
<html:form action="/vcityQuestionAction.do?action=saveVcityQuestion">
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL%>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">
<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<table align="center" width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL%>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL%>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" class="b_white">1、填写调查名称</td>
    <td width="14"><img src="<%=baseURL%>/vcity/images/dh_bleft.jpg" height="43"></td>
    <td width="200" class="b_blue">2、添加问题</td>
	<td width="14"><img src="<%=baseURL%>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
	<td width="200" class="b_white">3、调查问卷预览</td>
	<td width="14"><img src="<%=baseURL%>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td background="<%=baseURL%>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL%>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>
<br />
<c:forEach items="${vcitySurey.vcityQuestion}" var="question" varStatus="questionStauts">
<table align="center" width="96%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td height="30" class="tdl_B" bgcolor="#DFE8F7"><strong class="grayFont14" >问题${questionStauts.index+1} :${question.questionName }</strong></td>
	    <td width="200" bgcolor="#DFE8F7" class="tdr_B"><a href="#" onClick="upVcityQuestionIndex(${question.questionId})">上移</a> | <a href="#" onClick="downVcityQuestionIndex(${question.questionId})">下移</a> | <a href="#" onClick="document.getElementById('theadid${question.questionId}').style.display='';document.getElementById('tbodyid${question.questionId}').style.display='none';">修改</a> | <a href="#" onClick="deleteVcityQuestion(${question.questionId})">删除</a> | <a href="#" onClick="setQuestionTemplate(${question.questionId})">存为模版</a></td>
	  </tr>
	   <tbody id="theadid${question.questionId}" style="display:none">
		 <tr>
		 	<td>
		 		<table  id="tab${question.questionId}" width="98%" align="center" border="0" cellspacing="5" cellpadding="0">
		                <tr>
		                  <td width="20%" height="30" align="right">问题名称：</td>
		                  <td>
		                  	<html:text property="question(${question.questionId}).questionName" name="vcityQuestionForm" style="width:300px;" value="${question.questionName }" />
		                  	<html:hidden property="question(${question.questionId}).questionId" name="vcityQuestionForm" value="${question.questionId }" />
		                  	<html:hidden property="question(${question.questionId}).isTemplate" name="vcityQuestionForm" value="${question.isTemplate }" />
		                  	<html:hidden property="question(${question.questionId}).questIndex" name="vcityQuestionForm" value="${question.questIndex }" />
		                  	<html:hidden property="question(${question.questionId}).itemLimit" name="vcityQuestionForm" value="${question.itemLimit }" />
		                  	
		                  </td>
		                </tr>
		                <tr>
		                  <td height="30" align="right">问题类型：</td>
		                  <td>
		                  	<input type="radio" name="question(${question.questionId}).questType" value="1" ${question.questType == 1? "checked":""} />
			                    单选&nbsp;&nbsp;
			                <input type="radio" name="question(${question.questionId}).questType" value="2" ${question.questType == 2 ? "checked":""} />
			                    多选</td>
		                </tr>
		                <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="itemStauts">
		                <c:if test="${itemStauts.index==0}"><input type="hidden" value="${item.itemId}" id="q${question.questionId}" /> </c:if>
			                <tr id="q${question.questionId}item${item.itemId}">
			                  <td height="30" align="right">选项${itemStauts.index+1}：</td>
			                  <td>
			                  	<html:text property="question(${question.questionId}).item(${item.itemId}).itemDesc" name="vcityQuestionForm" style="width:300px;" value="${item.itemDesc}" />&nbsp;<a href="javascript:void(0);" onClick="delItemOnDatabase(${question.questionId},${item.itemId})"><img src="<%=baseURL%>/vcity/images/check_error.gif" border="0" align="absmiddle"></a>
			                  	<html:hidden property="question(${question.questionId}).item(${item.itemId}).itemId" name="vcityQuestionForm" value="${item.itemId}" />
			                  	<html:hidden property="question(${question.questionId}).item(${item.itemId}).itemIndex" name="vcityQuestionForm" value="${item.itemIndex}" />
			                  </td>
			                </tr>
			               
		                </c:forEach>
		               
		              </table>
				</td>
		<td></td>
			</tr>
			<tr>
			<td>
			<!-- 修改－增加选项 -->
			<table width="98%" align="center" border="0" cellspacing="5" cellpadding="0">
				 <tr>
		                  <td height="30" align="right">&nbsp;</td>
		                  <td><a href="javascript:void(0)" onclick="addQuestionItemEdit(${question.questionId},${fn:length(question.vcityQuestionitem)})" class="green12"><img src="<%=baseURL%>/vcity/images/icon_6.jpg" width="11" height="11" align="absmiddle" border="0">&nbsp;增加选项</a></td>
		                </tr>
		                <tr>
		                  <td height="30">&nbsp;</td>
		                  <td><input name="Submit" type="button" class="btn_BS" value="保存问题" onClick="updateVcityQuestion(${question.questionId})" />
		                    &nbsp;
		                    <input name="Submit2" type="reset" class="btn_BS" value="重置问题" />
		                    </td>
		                </tr>
		</table>
			</td>
			</tr>
	</tbody>
	<tfoot id="tbodyid${question.questionId}">
	 <c:if test="${question.questType eq 1}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="stauts">
		  <tr>
		    <td height="30"><input name="1" type="radio" value="" disabled="disabled"/>&nbsp;${item.itemDesc }</td>
		    <td>&nbsp;</td>
		  </tr>
	  </c:forEach>
	  </c:if>
	  <c:if test="${question.questType eq 2}">
	  <c:forEach items="${question.vcityQuestionitem}" var="item" varStatus="stauts">
		  <tr>
		    <td height="30"><input name="1" type="checkbox"value="" disabled="disabled"/>&nbsp;${item.itemDesc }</td>
		    <td>&nbsp;</td>
		  </tr>
	  </c:forEach>
	  </c:if>
	</tfoot>
</table>
</c:forEach>

<hr/>

<table align="center" width="96%" height="400" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="99%" height="100%" align="right" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100" height="26" class="tab_kp_on">创建问题</td>
                    <td width="100" align="center"  class="tab_kp_off">&nbsp;</td>
                    <td class="tab_kp_off" style="text-align:right"><input name="Submit22" type="button" class="btn_BS" value="使用问卷模版" onClick="document.getElementById('iconid').style.display='';document.getElementById('mbid').style.display='';">&nbsp;&nbsp;</td>
                  </tr>
              </table>			  </td>
            </tr>
            <tr>
              <td>
			  
			  <table width="98%" align="center" border="0" cellspacing="5" cellpadding="0">
			  <tr>
				  <td colspan="2">
					  <table id="questionInfo" width="100%" align="center" border="0" cellspacing="5" cellpadding="0">
		                <tr>
		                  <td width="20%" height="30" align="right">问题名称：</td>
		                  <td>
		                  	<html:text property="questionName" styleId="questionName" name="vcityQuestionForm" style="width:300px;" value="" />
		                  	<%--<bean:define id="questIndex" name="vcityQuestionForm" property="questIndex"></bean:define>
		                  	--%><html:hidden property="questIndex" name="vcityQuestionForm" value="${fn:length(vcitySurey.vcityQuestion)+1}" />
		                  </td>
		                </tr>
		                <tr>
		                  <td height="30" align="right">问题类型：</td>
		                  <td>
		                  	  <input type="radio" name="questType" value="1" checked="checked" />
		                      单选&nbsp;&nbsp;
		                      <input type="radio" name="questType" value="2" />
		                      多选</td>
		                </tr>
		                
		                <tr id="item1">
		                  <td height="30" align="right">选项1：</td>
		                  <td>
		                  	<html:text property="value(1).itemDesc" name="vcityQuestionForm" style="width:300px;" value="" />&nbsp;<a href="javascript:void(0);" onClick="delItem('1')"><img src="<%=baseURL%>/vcity/images/check_error.gif" border="0" align="absmiddle"></a>
		                  	<html:hidden property="value(1).itemIndex" name="vcityQuestionForm" value="1" />
		                  </td>
		                </tr>
		                <tr  id="item2">
		                  <td height="30" align="right">选项2：</td>
		                  <td>
		                  	<html:text property="value(2).itemDesc" name="vcityQuestionForm" style="width:300px;" value="" />&nbsp;<a href="javascript:void(0);" onClick="delItem('2')"><img src="<%=baseURL%>/vcity/images/check_error.gif" border="0" align="absmiddle"></a>
		                  	<html:hidden property="value(2).itemIndex" name="vcityQuestionForm" value="2" />
		                  </td>
		                </tr>
		                <tr  id="item3">
		                  <td height="30" align="right">选项3：</td>
		                  <td>
		                  	<html:text property="value(3).itemDesc" name="vcityQuestionForm" style="width:300px;" value="" />&nbsp;<a href="javascript:void(0);" onClick="delItem('3')"><img src="<%=baseURL%>/vcity/images/check_error.gif" border="0" align="absmiddle"></a>
		                  	<html:hidden property="value(3).itemIndex" name="vcityQuestionForm" value="3" />
		                  </td>
		                </tr>
		                <tr id="item4">
		                  <td height="30" align="right">选项4：</td>
		                  <td>
		                  	<html:text property="value(4).itemDesc" name="vcityQuestionForm" style="width:300px;" value="" />&nbsp;<a href="javascript:void(0);" onClick="delItem('4')"><img src="<%=baseURL%>/vcity/images/check_error.gif" border="0" align="absmiddle"></a>
		                  	<html:hidden property="value(4).itemIndex" name="vcityQuestionForm" value="4" />
		                 	<input type="hidden" id="itemNum" value="4" />
		                  </td>
		                </tr>
		              </table>
	                </td>
                </tr>
                <tr>
                  <td height="30" align="right" width="20%">&nbsp;</td>
                  <td><a href="javascript:void(0)" class="green12" onClick="addQuestionItem()"><img src="<%=baseURL%>/vcity/images/icon_6.jpg" width="11" height="11" align="absmiddle" border="0">&nbsp;增加选项</a></td>
                </tr>
                <tr>
                  <td height="30">&nbsp;</td>
                  <td><input name="Submit" type="button" class="btn_BS" onClick="saveVcityQuestion()" value="保存问题">
                    &nbsp;
                    <label>
                    <input name="Submit2" type="button" class="btn_BS" onClick="resetVcityQuestion()" value="重置问题">
                    </label></td>
                </tr>
              </table>
              
              
			  </td>
            </tr>
          </table></td>
	<td width="11" align="center" id="iconid" style="display:none"><img src="<%=baseURL%>/vcity/images/icon_7.jpg" width="9" height="106"></td>	  
    <td valign="top" width="300" id="mbid" style="display:none"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100" height="26" class="tab_kp_on">问卷模版</td>
                    <td width="100" align="center"  class="tab_kp_off">&nbsp;</td>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>			  </td>
            </tr>
            <tr>
              <td valign="top">
			  <table width="96%" height="95%" border="0" align="center" cellpadding="0" cellspacing="1" style="margin:8px 0px; border:1px solid #c6d2e3; background:url(<%=baseURL%>/vcity/images/bg.gif) top repeat-x">
                  <tr>
                    <td height="25">&nbsp;</td>
                  </tr>
                  <tr>
                    <td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <c:forEach items="${templateQuesitonList}" var="templateQuesion" varStatus="stauts">
							  <tr id="template_${templateQuesion.questionId }">
								<td width="30" height="23" align="center"><img src="<%=baseURL%>/vcity/images/icon_task.gif" width="12" height="12"></td>
								<td>${templateQuesion.questionName }</td>
							    <td width="80"><a href="javascript:void(0)" onClick="addTemplateQuestion(${templateQuesion.questionId})">添加</a>
							    <a href="javascript:void(0)" onClick="delTemplateQuestion(${templateQuesion.questionId});">删除</a></td>
							  </tr>
						</c:forEach>
					 </table>
					</td>
                  </tr>
                </table>              </td>
            </tr>
          </table></td>
  </tr>
</table>
<div style=" padding:8px 0px; text-align:center"><input type="button" class="btn_BS" value="修改调查名称" onClick="editVcitySurvey(${sureyId})">&nbsp;&nbsp;<input type="button" class="btn_BS" value="完成问卷" onClick="completeVcityQuestion()">&nbsp;&nbsp;
<c:if test="${empty vcitySurey.vcityQuestion}"><input type="button" class="btn_BS" value="取消创建" onClick="cancel(${sureyId})"></c:if>
</div>
</fieldset>
</td></tr></table>
<html:hidden property="surveyId" name="vcityQuestionForm" value="${sureyId}"/>
</html:form>
</body>
</html>