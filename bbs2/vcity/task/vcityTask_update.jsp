<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改任务</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/popup.js"></script>
<script>
var dialogclosepath="inc/close_off.gif";
</script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcityTaskService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
</head>
<body>
<html:form action="/vcityTaskAction.do?action=updateTask" styleId="vcityTaskForm" >
<input type="hidden" id="randValue" name="randValue" value="${indexString }">
<input type="hidden" id="taskId" name="taskId" value="${vcityTaskForm.taskId }">
<input type="hidden" name="createUser" value="${vcityTaskForm.createUser }"/>
<table align="center" id="main" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">修改任务</strong></legend><br>
 <table align="center" id="autoHead" width="100%" border="0" cellspacing="0" cellpadding="0">
 	 <tr>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_blue">1、创建任务
     </td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置任务布骤</td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">3、任务预览</td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>

<table align="center" id="rengongHead" style="display: none;" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
          <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
          <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_blue">1、创建任务</td>
          <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" width="14" height="43"></td><%--
          <td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置体验模型</td>
          <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
          --%><td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置调查问卷</td>
          <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
          <td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">3、任务预览</td>
          <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
          <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
        </tr>
</table>


      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="32%" height="30" class="tdr"><p><strong>任务名称：</strong></p></td>  
          <td width="68%"><input type="text" name="taskName"  onchange="checkNameExist();" value="${vcityTaskForm.taskName }" style=" width:60%" class="blueborderText" id="taskName">
         		<input type="hidden" id="backupName" value="${vcityTaskForm.taskName }">
          <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>有效期：</strong></p></td>
          <td><input type="text" name="taskBegin" id="taskBegin" value="${vcityTaskForm.taskBegin }" style="width:147px"class="blueborderText" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')">
          ~
          <input type="text" name="taskEnd" id="taskEnd" value="${vcityTaskForm.taskEnd }" style="width:148px" class="blueborderText" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')">
        </tr>
             <tr>
          <td height="30" class="tdr"><p><strong>任务类型：</strong></p></td>
          <td><input type="hidden" id="taskType" name="taskType" value=""/>
          <input name="taskType" disabled id="taskType1"  type="radio" value="1" checked>
            自动任务

          <input type="radio" disabled name="taskType" id="taskType2" value="2">
          人工任务 <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>任务积分：</strong></p></td>
          <td><input type="text" id="credit" name="credit" style=" width:60%" class="blueborderText" value="${vcityTaskForm.credit }">
          <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>参与人员：</strong></p></td>
          <td><div id="num" style="overflow: auto; width:50%; height:60px;  border:1px solid #A7CBE5;">
         	  	<c:if test="${not empty userList}">
					 <c:forEach items="${userList}" var="user">
							<input type='checkbox' name='checkuser' readonly="readonly" disabled="disabled" checked id='${ user[1]}' value=""/>${ user[1]}
					   </c:forEach>
					 </c:if> 
         	 </div>
             <input type="button" name="Submit3" onClick="parent.ShowIframe('选择人员','<%=baseURL %>/shuaxuan.jsp',700,510);" class="btn_BS"  value="会员筛选">
            <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>任务介绍：</strong></p></td>
          <td><textarea name="taskIntro" style="width:50%; height:60px;">${vcityTaskForm.taskIntro}</textarea></td>
        </tr>
       <tr>
          <td height="50" colspan="2" align="center"><span id="autoButton">
          <input type="button" name="Submit42" value="下一步" class="btn_BS" onClick="toSub();">
&nbsp;&nbsp;
          </span><span id="rengongButton" style="display: none;"><input type="button"  class="btn_BS" name="Submit42" value="下一步" onClick="toSurvey();"></span><input onClick="returnList();" type="button" name="Submit42" value=" 取消 " class="btn_BS"></td>
        </tr>
      </table>
  
</fieldset>
</td></tr></table>

<table align="center" id="sub" style="display: none;" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">修改任务</strong></legend><br>
	  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_white">1、修改任务
    </td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
    <td width="200" class="b_blue">2、修改任务布骤<!--<br>
  <span class="wFont12">(
      必须设置一项任务内容

      )</span> --></td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">3、任务预览</td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>
    
   <br>
      <table width="100%" border="0" id="childTab" cellspacing="0" cellpadding="0">
        <tr>
          <td width="70" height="30" align="center" class="grayFont14">序号</td>
          <td width="100" align="center" class="grayFont14">任务类型</td>
          <td align="center" class="grayFont14">任务内容</td>
        </tr>
        <c:forEach items="${vcityTaskForm.subTaskList}" var="subTask" varStatus="stauts">
        <tr id="child${subTask.subTaskIndex }">
          <td height="25" align="center"><html:text readonly="true" property="value(${subTask.subTaskIndex }).subTaskIndex" style="width:30px" value="${subTask.subTaskIndex }" styleId="subTaskIndex${subTask.subTaskIndex }"></html:text></td>
          <td align="center">
             <c:if test="${subTask.subTaskType ==1 }">
             <input type="hidden" value="1" name="value(${subTask.subTaskIndex }).subTaskType"/>
             产品体验
            </c:if>
             <c:if test="${subTask.subTaskType ==2 }">
             <input type="hidden" value="2" name="value(${subTask.subTaskIndex }).subTaskType"/>
              调查
            </c:if>
        </td>
          <td><html:text property="value(${subTask.subTaskIndex }).subTaskDesc" readonly="true" value="${subTask.subTaskDesc }"  style="width:100%" styleId="subTaskDesc${subTask.subTaskIndex }"></html:text>
            <input type="hidden" id="subTaskURL${subTask.subTaskIndex }" name="value(${subTask.subTaskIndex }).subTaskURL" value="${subTask.subTaskURL }" />
            <input type="hidden" id="subTaskResId${subTask.subTaskIndex }" name="value(${subTask.subTaskIndex }).subTaskResId" value="${subTask.subTaskResId }" /> 
           </td>
        </tr>
       </c:forEach>
    </table>
      <br>
	<div style=" text-align:center; padding-top:10px;">
	<input type="button" name="Submit42" value="上一步" class="btn_BS" onClick="toMain();">
	&nbsp;&nbsp;
	  <input type="button" name="Submit42" value="下一步" class="btn_BS" onClick="toPreview();">
&nbsp;&nbsp;
<input type="button" name="Submit42" value=" 取消 " class="btn_BS" onClick="returnList();">
	</div>
</fieldset>
</td></tr></table>


<table align="center" id="preview" style="display: none;" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">修改任务</strong></legend><br>
	  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_white">1、修改任务    </td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" class="b_white">2、修改任务布骤</td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_blue">3、任务预览</td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" height="43"></td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>


      
   <br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="33%" height="50" class="tdr"><img src="<%=baseURL %>/vcity/images/ts.jpg" width="46" height="43"></td>
    <td width="67%"><strong class="greenFont18" id="td1">你将创建自动交互任务：天翼手机体验任务。</strong></td>
  </tr>
  <tr>
    <td height="30" align="right" class="grayFont12"><strong>有效期：</strong></td>
    <td class="grayFont12" id="td2" >2011-8-2~2011-9-2</td>
  </tr>
  <tr>
    <td height="30" align="right" class="grayFont12"><strong>任务积分：</strong></td>
    <td class="grayFont12" id="td3">30分</td>
  </tr>
  <tr>
    <td height="30" align="right" class="grayFont12"><strong>新增参与人员：</strong></td>
    <td class="grayFont12" id="td4"></td>
  </tr>
  <tr>
    <td height="30" align="right" valign="top" class="grayFont12"><p><strong>任务步骤：</strong></p></td>
    <td height="50" class="grayFont12" id="td5"><br>
<br>
</td>
  </tr>
</table>

	<div style=" text-align:center; padding-top:10px;">
	  <input type="button" name="Submit42" value="上一步" class="btn_BS" onClick="toSub();">
&nbsp;&nbsp;
<input type="button" name="Submit42" value=" 确认发布 " class="btn_BS" onClick="publish();">
	</div>
</fieldset>
</td></tr></table>

<%--<table align="center" width="100%" id="model" style="display: none;" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">创建任务</strong></legend><br>
	  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_white">1、创建任务</td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_blue">2、配置体验模型<!--<br>
      <span class="wFont12">(
      请选择本次任务的产品模型)</span> --></td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置调查问卷</td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">3、任务预览</td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>


      <br>
	  <table width="100%" id="chooseModel" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7%" height="30" align="center"></td>
    <td width="79%">
      <input type="button" name="Submit5" value="选择模型" class="btn_BS" onClick="parent.ShowIframe('选择体验产品','<%=baseURL%>/vcityResourceAction.do?action=showVcityResourceList&flag=1&manual=1',700,510);">
      </td>
    <td width="14%">&nbsp;</td>
    </tr>
	<tbody id="testModel">
	    <c:forEach items="${vcityTaskForm.initTaskList}" var="init" varStatus="stauts">
	    <c:if test="${init.subTaskType == 1 }">
	 	<tr id='mo${init.id }' >
	   		<td height='30' align='center' bgcolor='#EAF8FD'><input type='checkbox' checked name='modelId' value='${ init.subTaskResId}'></td>
	   		<td bgcolor='#EAF8FD'><input type='hidden'  name='modelName' value='${init.resName }'><p>${init.resName }</p></td>

	    	<td align='center' bgcolor='#EAF8FD'><input type='button' name='Submit' value='查看'  onClick="parent.ShowIframe('用户行为','<%=baseURL%>/vcityResourceAction.do?action=previewResource&resid=${init.subTaskResId }',700,500)">
	   		 <input type='button' name='Submit2' value='删除' onclick='removeModel(${init.id });'></td>

	    	</tr>
	    	</c:if>
	    </c:forEach> 
	</tbody>
</table>
	<div style=" text-align:center; padding-top:10px;">
	  <input type="button" name="Submit42" value="上一步" class="btn_BS" onClick="changeToRenGong();">
&nbsp;&nbsp;
<input type="button" name="Submit42" value="下一步" class="btn_BS" onClick="toSurvey();">
	</div>
</fieldset>
</td></tr></table>


--%><table align="center" width="100%" id="survey" style="display: none;" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">创建任务</strong></legend><br>
	  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_white">1、创建任务</td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td><%--
    <td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置体验模型</td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
	--%><td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_blue">2、配置调查问卷<!--<br>
      <span class="wFont12">(
      请选择本次任务的调查问卷)</span> --></td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">3、任务预览</td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>
      <br>
	  <table width="100%" border="0" id="chooseSurvey" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7%" height="30" align="center"></td>
    <td><input type="button" name="Submit5" value="选择问卷" class="btn_BS" onClick="parent.ShowIframe('选择问卷','<%=baseURL %>/vcitySurveyAction.do?action=showVcitySureyList&flag=1&manual=1',700,580)"></td>
    <td width="14%" align="center">&nbsp;</td>
    <td width="14%">&nbsp;</td>
    </tr>
    <tbody id="testSurvey">
      <c:forEach items="${vcityTaskForm.initTaskList}" var="init" varStatus="stauts">
   		 <c:if test="${init.subTaskType == 2 }">
	<tr id='su${init.id }' >
   		<td height='30' align='center' bgcolor='#EAF8FD'><input type='checkbox' checked name='surveyId' value='${ init.subTaskResId}'></td>
   		<td bgcolor='#EAF8FD'><input type="hidden" name="revalue" value="${ init.subTaskResId}!@${init.resName }">
   		<input type='hidden'  name='surveyName' value='${init.resName }'><p>${init.resName }</p></td>
   		<c:if test="${init.subTaskResId!=0 }"><td align='center' bgcolor='#EAF8FD'>本地问卷</td>
    	<td align='center' bgcolor='#EAF8FD'><input type='button' class="btn_BS" onClick="parent.ShowIframe('查看问卷','<%=baseURL%>/vcitySurveyAction.do?action=previewSurvey&id=${init.subTaskResId }',700,580)" name='Submit' value=' 查看 '>
   		 <input type='button' name='Submit2' value=' 删除 ' class="btn_BS" onclick='removeSurvey(${init.id });'></td>
   		 </c:if> 
   		 <c:if test="${init.subTaskResId==0 }"><td align='center' bgcolor='#EAF8FD'>e声网问卷</td>
    	<td align='center' bgcolor='#EAF8FD'>
   		 <input type='button' name='Submit2' value=' 删除 ' class="btn_BS" onclick='removeSurvey(${init.id });'></td>
   		 </c:if> 
    	</tr>
    	</c:if>
    	</c:forEach>
    	</tbody>
</table>
	<div style=" text-align:center; padding-top:10px;">
	  <input type="button" name="Submit42" value="上一步" class="btn_BS" onClick="changeToRenGong();">
&nbsp;&nbsp;
<input type="button" name="Submit42" value="下一步" class="btn_BS" onClick="toRenGongView();">
	&nbsp;&nbsp;<input type="button" onClick="returnList();" class="btn_BS" name="Submit422" value=" 取消 ">
	</div>
</fieldset>
</td></tr></table>

<table id="rengongview" style="display: none;" align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">创建任务</strong></legend><br>
	  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_left.jpg" width="7" height="43"></td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <td width="200" background="<%=baseURL %>/vcity/images/dh_bbg.jpg" class="b_white">1、创建任务</td>
    <td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
    <%--<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置体验模型</td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_wright.jpg" width="14" height="43"></td>
	--%><td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_white">2、配置调查问卷</td>
	<td width="14"><img src="<%=baseURL %>/vcity/images/dh_bleft.jpg" height="43"></td>
	<td width="200" background="<%=baseURL %>/vcity/images/dh_wbg.jpg" class="b_blue">3、任务预览</td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg"><img src="<%=baseURL %>/vcity/images/dh_bright.jpg" width="14" height="43"></td>
    <td background="<%=baseURL %>/vcity/images/dh_wbg.jpg">&nbsp;</td>
    <td width="7"><img src="<%=baseURL %>/vcity/images/dh_right.jpg" width="7" height="43"></td>
  </tr>
</table>

      <br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="33%" height="50" class="tdr"><img src="<%=baseURL %>/vcity/images/ts.jpg" width="46" height="43"></td>
    <td width="67%"><strong class="greenFont18" id="p1">你将创建人工交互任务：天翼手机体验任务。
</strong></td>
  </tr>
  <tr>
    <td height="30" align="right" class="grayFont12"><strong>任务内容：
</strong></td>
    <td class="grayFont12"><p id="p3"></p></td>
  </tr>
</table>

	<div style=" text-align:center; padding-top:10px;">
	  <input type="button" name="Submit42" value="上一步" class="btn_BS" onClick="toSurvey();">
&nbsp;&nbsp;
<input type="button" name="Submit42" value=" 确认发布 " class="btn_BS" onClick="publish();">
	</div>
</fieldset>
</td></tr></table>

	<!-- 弹出框 -->
<div style="display:none;position: absolute; width: 300px; height: 530px; z-index: 100000; right: 287px; top: 113px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td class="tdl" id="tipTitle" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><span><a href="#" onClick="document.getElementById('tipDiv').style.display='none';document.getElementById('KuanghiddenDiv').style.display='none'">关闭</a></span></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="500" name="actionFrame" src=""></iframe></td></tr>
</table>
</div>
<div id="KuanghiddenDiv" name="KuanghiddenDiv" style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>

<!-- end 弹出框 -->
<!-- 弹出框2 -->
<div style="display:none;position: absolute; width: 700px; height: 530px; z-index: 100000; right: 87px; top: 53px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv2">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td class="tdl" id="tipTitle2" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><span><a href="javascript:;" onClick="document.getElementById('tipDiv2').style.display='none';document.getElementById('KuanghiddenDiv2').style.display='none'">关闭</a></span></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="480" name="actionFrame2" src=""></iframe></td></tr>
</table>
</div>
<div id="KuanghiddenDiv2" name="KuanghiddenDiv2" style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>

<!-- end 弹出框2 -->
</html:form>
</body>
<script type="text/javascript">
function returnList(){
	vcityTaskForm.action = "<%=baseURL%>/vcityTaskAction.do?action=showTaskList";
	 document.getElementById('taskType1').value = "";
	  document.getElementById('taskType2').value = "";
	vcityTaskForm.submit();
}

var type = ${vcityTaskForm.taskType};
if(type==2){
	document.getElementById('taskType2').checked = true;
	document.getElementById('taskType').value = 2;
	changeToRenGong();
} else {
	document.getElementById('taskType').value = 1;
}

function checkNameExist(){
	var taskName = document.getElementById('taskName').value;
	var id = document.getElementById('taskId').value;
	vcityTaskService.checkTaskExistByName(id,taskName,function exist(str){
		if(str=="1"){
			alert("任务名称已经存在！");
			document.getElementById('taskName').value = document.getElementById('backupName').value;
			return false;
		}
	});
}

function toSub(){
	if(checkSubmit()){
		document.getElementById('main').style.display='none';
		document.getElementById('sub').style.display='';
		document.getElementById('preview').style.display='none';
	}
}	

function toMain(){
	document.getElementById('main').style.display='';
	document.getElementById('sub').style.display='none';
	document.getElementById('preview').style.display='none';
}
function toPreview(){
	document.getElementById('main').style.display='none';
	document.getElementById('sub').style.display='none';
	document.getElementById('preview').style.display='';
	 toPreviewValue();
	
}
function toPreviewValue(){
	
	var taskName = document.getElementById('taskName').value;
	var taskBegin = document.getElementById('taskBegin').value;
	var taskEnd = document.getElementById('taskEnd').value;
	var credit = document.getElementById('credit').value;
	var users = document.getElementsByName("checkuser");
	var randValue = document.getElementById('randValue').value;
	var us = "";
	var stepString = "";
	for(var i=0;i<users.length;i++){
		if(users[i].value!=""){
			us += users[i].value+"、";
		}

	}
	if(randValue!=""){
		var valueArr = randValue.split(",");
		for(var i=0;i<valueArr.length;i++){
			var index = document.getElementById("subTaskIndex"+valueArr[i]).value;
			var desc = document.getElementById("subTaskDesc"+valueArr[i]).value;
			stepString += i+1+"、"+desc+"</br>";
		} 
	}
	document.getElementById('td1').innerHTML = "你将创建自动交互任务:"+taskName;
	document.getElementById('td2').innerHTML = taskBegin +"~"+taskEnd;
	document.getElementById('td3').innerHTML = credit;
	document.getElementById('td4').innerHTML = us;
	document.getElementById('td5').innerHTML = stepString;
}
function publish(){
	if(confirm('确认发布此任务？发布将会通知参与会员！')){
		if(checkSubmit()){
			vcityTaskForm.submit();
		}
	}
}
function checkSubmit(){
	var taskName = document.getElementById("taskName").value;
	var taskBegin = document.getElementById("taskBegin").value;
	var taskEnd = document.getElementById("taskEnd").value;
	if(taskName==""){
		alert("任务名称不能为空！");
		return false;
	}
	if(taskBegin>taskEnd){
		alert("任务结束时间不能小于开始时间！");
		return false;
	}
	return true;
}
function changeButton(num){
	var v = document.getElementById("type"+num).value;
	if(v=='1'){
		document.getElementById("bwj"+num).style.display='none';
		document.getElementById("bty"+num).style.display='';
	}if(v=='2'){
		document.getElementById("bty"+num).style.display='none';
		document.getElementById("bwj"+num).style.display='';
	}
}


function changeToAuto(){
	document.getElementById('main').style.display='';
	document.getElementById('autoHead').style.display='';
	document.getElementById('autoButton').style.display='';
	document.getElementById('rengongButton').style.display='none';
	document.getElementById('rengongHead').style.display='none';
}
function changeToRenGong(){
	document.getElementById('main').style.display='';
	document.getElementById('autoHead').style.display='none';
	document.getElementById('autoButton').style.display='none';
	document.getElementById('rengongButton').style.display='';
	document.getElementById('rengongHead').style.display='';

}
/*
function toModel(){
	document.getElementById('main').style.display='none';
	document.getElementById('sub').style.display='none';
	document.getElementById('preview').style.display='none';
	document.getElementById('model').style.display='';
	document.getElementById('survey').style.display='none';
	document.getElementById('rengongview').style.display='none';
} */
function toSurvey(){
	document.getElementById('main').style.display='none';
	document.getElementById('sub').style.display='none';
	document.getElementById('preview').style.display='none';
	//document.getElementById('model').style.display='none';
	document.getElementById('survey').style.display='';
	document.getElementById('rengongview').style.display='none';
}
function toRenGongView(){
	document.getElementById('main').style.display='none';
	document.getElementById('sub').style.display='none';
	document.getElementById('preview').style.display='none';
	//document.getElementById('model').style.display='none';
	document.getElementById('survey').style.display='none';
	document.getElementById('rengongview').style.display='';
	
	var surveyName = document.getElementsByName("surveyName");
	var modelName = document.getElementsByName("modelName");
	var str = "";
	var str1 = "";
	for(var i=0;i<surveyName.length;i++){
		str  +=  surveyName[i].value+",";
	}
	for(var i=0;i<modelName.length;i++){
		str1  +=  modelName[i].value+",";
	}
	var taskName = document.getElementById('taskName').value;
	document.getElementById('p1').innerHTML = "你将创建人工交互任务:"+taskName;
//	document.getElementById('p2').innerHTML = "1、已选择体验模型："+str1; 
	document.getElementById('p3').innerHTML = "1、已选择调查问卷："+str; 
}
function createTaskChild(){
	//x上限，y下限
	var x = 100;
	var y = 4;
	var rand = parseInt(Math.random() * (x - y)+ y);
	
	var randValue = document.getElementById('randValue').value;
	if(randValue==""){
		document.getElementById('randValue').value = rand;
	}
	if(randValue!=""){
		document.getElementById('randValue').value = randValue+","+rand;
	}
	      var str = "<tr id='child"+ rand +"'>";
         str +="<td height='25' align='center'>";
          str +="<td><input type='text' name='value("+ rand +").subTaskIndex' id='subTaskIndex"+ rand +"' style='width:30px' value='' /></td>";
         str +=" <td align='center'>";
           str +=" <select name='value("+ rand +").subTaskType' id='type"+ rand +"' onChange='changeButton("+ rand +");'>";
            str +="  <option value='1'>产品体验</option>";
           str +="   <option value='2' selected>调查</option>";
          str +="  </select>";
       str +=" </td>";
      str +="    <td><input type='text' name='value("+ rand +").subTaskDesc' id='subTaskDesc"+ rand +"' style='width:100%' /></td>";
      str +="    <td align='center'><input type='button' id='bwj"+ rand +"' name='Submit2' value='选择问卷' onClick=\"openDiv2('选择问卷','<%=baseURL %>/vcitySurveyAction.do?action=showVcitySureyList&flag="+ rand +"',700,580)\"><input type='button' style='display:none' id='bty"+ rand +"' name='Submit2' value='选择产品体验'  onClick=\"openDiv2('选择体验产品','<%=baseURL%>/vcityResourceAction.do?action=showVcityResourceList&flag="+ rand +"',700,580);\">";
      str += " <input type='hidden' id='subTaskResId"+ rand +"' name='value("+ rand +").subTaskResId'>";
     str += "<input type='hidden' id='subTaskURL"+ rand +"' name='value("+ rand +").subTaskURL'>";
      str +="    <input type='button' name='Submit22' value='删除' onclick='removeTaskChild("+ rand +");' /></td>";
       str +=" </tr>";  
   
     $('#childTab').append(str);
}

function removeTaskChild(childId){

	var randValue = document.getElementById("randValue").value;
	var valueArr = randValue.split(",");
	var newValue = "";
	for(var i=0;i<valueArr.length;i++){
		if(valueArr[i]!=childId){
			newValue += valueArr[i]+",";
		}
	}
	var v = newValue.substring(0,newValue.length-1);
	 document.getElementById("randValue").value = v
		$('#child'+childId).remove();
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
/*接收e声网问卷 */
function survery(name,id){

   		var surveyNames = document.getElementsByName("surveyName");
		for(var i=0;i<surveyNames.length;i++){
			if(name==surveyNames[i].value){
				alert("已选择该问卷,不必重复选择！");
				return false;
			}
		}
		
		var str =" <tr id='su"+ id +"' >";
   		str +=" <td height='30' align='center' bgcolor='#EAF8FD'><input type='checkbox' checked name='surveyId' value='0'></td>";
   		str +=" <td bgcolor='#EAF8FD'><input type='hidden'  name='surveyName' value='"+ name +"'><p>"+ name +"</p></td>";
   		str += "<td align='center' bgcolor='#EAF8FD'>e声网问卷</td>";
    	str += "<td align='center' bgcolor='#EAF8FD'><input type='button' disabled='disabled'   name='Submit' class='btn_BS'  value=' 查看 '>";
   		 str += " <input type='button' name='Submit2' value=' 删除 ' class='btn_BS' onclick='removeSurvey("+ id +");'></td>";
    	str += "</tr>";
    	
 	 	$('#testSurvey').append(str);	 	
   ClosePop();

}

function localSurvery(num,str){
	if( str!=null){
		var strarray = str.split("!@");
		document.getElementById("subTaskURL"+num).value ="/surveyTest.do?id="+strarray[0];
		document.getElementById("subTaskResId"+num).value =strarray[0];
		document.getElementById("subTaskDesc"+num).value = strarray[1];
	}
	return true;	
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
function colseDiv(){
	document.getElementById('tipDiv').style.display='none';
	document.getElementById('KuanghiddenDiv').style.display='none'
}
function experience(num,rv){
	if( rv!=null){
		var strarray = rv.split("!@");
		document.getElementById("subTaskURL"+num).value ="/modelTest.do?id="+strarray[0];
		document.getElementById("subTaskResId"+num).value =strarray[0];
		document.getElementById("subTaskDesc"+num).value = strarray[1];
	}
	return true;
}
function manualSurvey(num,rv){
//	var   rv   = window.showModalDialog("<%=baseURL%>"+url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
	if( rv!=null){
		var strarray = rv.split("!@");
		var id = strarray[0];
		var name = strarray[1];
		var surveyNames = document.getElementsByName("surveyName");
		
		for(var i=0;i<surveyNames.length;i++){
			if(name==surveyNames[i].value){
				alert("已选择该问卷,不必重复选择！");
				return fasle;
			}
		}
			var str =" <tr id='su"+ id +"' >";
	   		str +=" <td height='30' align='center' bgcolor='#EAF8FD'><input type='checkbox' checked name='surveyId' value='"+ id +"'></td>";
	   		str +=" <td bgcolor='#EAF8FD'><input type=\"hidden\" name=\"revalue\" value='"+rv+"'><input type='hidden'  name='surveyName' value='"+ name +"'><p>"+ name +"</p></td>";
	   		str += "<td align='center' bgcolor='#EAF8FD'>本地问卷</td>";
	    	str += "<td align='center' bgcolor='#EAF8FD'><input type='button' name='Submit' class='btn_BS' onclick=\"parent.ShowIframe('查看问卷','<%=baseURL%>/vcitySurveyAction.do?action=previewSurvey&id="+id+"',700,580)\" value='查看'>";
	   		 str += " <input type='button' class='btn_BS' name='Submit2' value='删除' onclick='removeSurvey("+ id +");'></td>";
	    	str += "</tr>";
	    	
	 	 	$('#testSurvey').append(str);
 	 		
	}
	return true;
}
function chooseModel(rv){

		if( rv!=null){
		var strarray = rv.split("!@");
		var id = strarray[0];
		var name = strarray[1];
		
		var modelNames = document.getElementsByName("modelName");
		for(var i=0;i<modelNames.length;i++){
			if(name==modelNames[i].value){
				alert("已选择该产品体验,不必重复选择！");
				return false;
			} 
		}
			var str =" <tr id='mo"+ id +"' >";
	   		str +=" <td height='30' align='center' bgcolor='#EAF8FD'><input type='checkbox' checked name='modelId' value='"+ id +"'></td>";
	   		str +=" <td bgcolor='#EAF8FD'><input type='hidden'  name='modelName' value='"+ name +"'><p>"+ name +"</p></td>";
	    	str += "<td align='center' bgcolor='#EAF8FD'><input type='button' name='Submit' value='查看' onClick=\"parent.ShowIframe('用户行为','<%=baseURL%>/vcityResourceAction.do?action=previewResource&resid="+ id +"',700,500)\">";
	   		 str += " <input type='button' name='Submit2' value='删除' onclick='removeModel("+ id +");'></td>";
	    	str += "</tr>";
	    	
	 	 $('#testModel').append(str);	
 	 	//document.getElementById('tipDiv2').style.display="none";
		//document.getElementById('KuanghiddenDiv2').style.display="none";
	}
	return true;
}
function removeModel(id){
	$('#mo'+id).remove();
}
function removeSurvey(id){
	$('#su'+id).remove();
}
</script>
</html>
