<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户行为数据</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcityResourceService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
</head>
<body>
<table width="97%" height="95%" align="center" border="0" cellpadding="0" cellspacing="1" class="tableWrap">
	<tr><td valign="top">
	<table width="97%" height="95%" align="center" border="0" cellpadding="0" cellspacing="1" class="tableWrap">
            <tr>
              <td height="25" class="greenFont12 tdl_B"><strong>子任务列表：</strong></td>
            </tr>
            <c:if test="${!empty userSubList }">
            	<c:forEach items="${userSubList }" var="sub" varStatus="st">
            	<c:if test="${sub.subTaskType ==1 }">
		            <tr>
		              <td height="15" class="tdl">${st.index+1 } 、<a href="javascript:;" onClick="showStep('${sub.id }','${sub.subTaskResId }','${sub.userName }','${sub.subTaskType }');">${sub.subTaskDesc }</a></td>
		            </tr>
		           </c:if>
	            </c:forEach>
            </c:if>
            
            <tr>
              <td colspan="2" class="tdc">&nbsp;</td>
            </tr>
          </table>
          </td>
          <td valign="top">
          <table width="97%" height="95%" align="center" border="0" cellpadding="0" cellspacing="1" class="tableWrap">
            <tr>
              <td  height="25" class="greenFont12 tdl_B"><strong>操作步骤</strong></td>
            </tr>
            <tbody id="step">
            <c:if test="${!empty userStepList }">
            	<c:forEach items="${userStepList }" var="step" varStatus="st">
		            <tr>
		              <td height="15" class="tdl">  ${st.index+1 } 、${step }</td>
		            </tr>
	            </c:forEach>
            </c:if>
			</tbody>
			<tfoot>
             <tr>
              <td colspan="2" class="tdc">&nbsp;</td>
            </tr>
            </tfoot>
           
          </table>
          </td>
          </tr>
  </table>
</body>
<script type="text/javascript">
function showStep(id,resid,username,type){
	if(type=='1'){
		vcityResourceService.getUserStep(resid,username,setStepTbody);
	}
	if(type=='2'){
		vcityResourceService.getUserSueryResult(id,resid,username,setResultTbody);
	}
}
function setStepTbody(data){
	var tbody = document.getElementById("step"); 
	while(tbody.hasChildNodes()){
			tbody.removeChild(tbody.lastChild);
	}
	var str = "";
	for(var i=0;i<data.length;i++){
		str += "<tr><td height='15' class='tdl'>"+(i+1)+"、"+data[i]+"</td></tr>";
	}
	$('#step').append(str);
}
</script>
</html>
