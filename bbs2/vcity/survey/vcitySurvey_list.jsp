<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>第一体验俱乐部研究员管理平台</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/popup.js"></script>

	<script type="text/javascript">
function createVcitySurvey(){
	vcitySurveyForm.action = '<%=baseURL%>/vcitySurveyAction.do?action=editVcitySurey&todo=create';
	vcitySurveyForm.submit();
	//window.location.reload(true);
}
function editVcitySurvey(id){
	vcitySurveyForm.action = '<%=baseURL%>/vcitySurveyAction.do?action=editVcitySurey&todo=update&id='+id;
	vcitySurveyForm.submit();
}
function deleteVcitySurey(id){
 	if (!confirm("确定要删除已选择任务？")){
	   	return false;
	} else {
		if(id == undefined ){
			vcitySurveyForm.action = '<%=baseURL%>/vcitySurveyAction.do?action=deleteVcitySurey';
		}
		vcitySurveyForm.action = '<%=baseURL%>/vcitySurveyAction.do?action=deleteVcitySurey&id='+id;
		vcitySurveyForm.submit();
	}
}
function showVcitySurey(id){
	vcitySurveyForm.action = '<%=baseURL%>/vcitySurveyAction.do?action=showVcityQuestionList&id='+id;
	vcitySurveyForm.submit();
}
function doVcitySurey(id){
	window.location = '<%=baseURL%>/vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=create&subtype=1&id='+id;
}

function toSurveyResult(id){
	window.location = '<%=baseURL%>/vcityQuestionresultAction.do?action=showSurveyResult&id='+id;
}

function copyVcitySurey(id) {   
	var url =  '<%=basePath%>vcityQuestionresultAction.do?action=editVcityQuestionresult&todo=create&id='+id;
    if(window.clipboardData) {   
    	if( window.clipboardData.clearData()){ 
	        window.clipboardData.setData("Text", url); 
	         alert("复制成功！");
	     }
     } else if(navigator.userAgent.indexOf("Opera") != -1) {   
            	  window.location = url;   
         	} else if (window.netscape) {   
            	  try {   
                 	  netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");   
            	  } catch (e) {   
                   		alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");   
             	  }  
            	  var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);   
	              if (!clip)   
	                   return;   
	              var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);   
	              if (!trans)   
	                   return;   
	              trans.addDataFlavor('text/unicode');   
	              var str = new Object();   
	              var len = new Object();   
	              var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);   
	              var copytext = url;   
	              str.data = copytext;   
	              trans.setTransferData("text/unicode",str,copytext.length*2);   
	              var clipid = Components.interfaces.nsIClipboard;   
	              if (!clip)   
	                   return false;   
	              clip.setData(trans,null,clipid.kGlobalClipboard);   
	              alert("复制成功！")   
       		  }   
    }  

</script>
</head>

<body>

<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">调查问卷</strong></legend><br>
<html:form action="/vcitySurveyAction.do?action=showVcitySureyList" styleId="vcitySureyForm" >
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
    <td width="43%" bgcolor="#f1f9fe" class=" tdl_B">问卷查询：
    <html:text property="sureyName" name="vcitySurveyForm"></html:text>
    &nbsp;<input type="button" class="btn_BS" onClick="vcitySurveyForm.submit();" value=" 查询 "></td>
    <td width="87%" height="40" bgcolor="#f1f9fe" class="tdr_B">
    <input name="Submit22" type="button" class="btn_BS" onClick="createVcitySurvey()" value=" 创建调查 ">
    </td>
 
  </tr>
  
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%"><input type="checkbox" id='selectedRowAll' name='selectedRowAll' onclick='selectAllGrid(this,"selectedRow")'>
              <a href="javascript:;" onClick="deleteVcitySurey()">删除</a></td>
            <td>调查问卷</td>
            <td width="15%">创建日期</td>
            <td width="35%">操作</td>
          </tr>
        </thead>
        <c:if test="${!empty datas}">
			<c:forEach items="${datas}" var="survey" varStatus="stauts">
	        <tr>
	          <td align="center"><input type="checkbox" name="selectedRow" value="${survey.sureyId}"></td>
	          <td align="center">${survey.sureyName}</td>
	          <td align="center">${survey.ceateTime}</td>

	          <td align="center" class="tdc">
			  <input type="button" onClick="editVcitySurvey(${survey.sureyId})" class="btn_BS" value="编 辑" />
			  <input type="button" onClick="deleteVcitySurey(${survey.sureyId})" class="btn_BS" value="删 除" />
			  <input type="button" onClick="showVcitySurey(${survey.sureyId})" class="btn_BS" value="预 览" />
			  <input type="button" onClick="doVcitySurey(${survey.sureyId})" class="btn_BS" value="作 答" />
			  <input type="button" onClick="copyVcitySurey(${survey.sureyId})" class="btn_BS" value="答题链接" />
			 </td><%--
	          <td align="center" class="tdc"><a href="#" onclick="editVcitySurvey(${survey.sureyId})">编辑</a> | <a href="#" onclick="deleteVcitySurey(${survey.sureyId})">删除</a> |
	           <a href="#" onClick="showVcitySurey(${survey.sureyId})">预览</a> | <a href="#" onClick="doVcitySurey(${survey.sureyId})">作答</a>
	         | <a href="#" onClick="toSurveyResult(${survey.sureyId})">结果</a>  | <a href="#" onClick="copyVcitySurey(${survey.sureyId})">答题链接</a></td>

	       --%>
	        </tr>
        	</c:forEach>
        </c:if>
		<tfoot>
          <tr>
            <td colspan="5">
				<wbase:dispartPage formId="vcitySureyForm" />
			</td>
          </tr>
        </tfoot>
      </table>
</html:form>
</fieldset>
	
	</td>
  </tr>
</table>
</body>
</html>
