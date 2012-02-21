<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html:html lang="true">
<head>
	<html:base />
	<title>体验资源管理</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">

	<script type="text/javascript">
function createResource(){
	vcityResourceForm.action = "<%=baseURL%>/vcityResourceAction.do?action=editVcityResource&todo=create";
	vcityResourceForm.submit();
}

function updateResource(id){
	var selectType = document.getElementById("resType").value;
	vcityResourceForm.action = "<%=baseURL%>/vcityResourceAction.do?action=editVcityResource&todo=update&selectType="+selectType+"&id="+id;
	vcityResourceForm.submit();

}

function resApp(url,width,height){
	window.showModalDialog(url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
}

function deleteAll(url){
	var num = getCheckBoxNum("selectedRow");

	if(num>0){
	 if (!confirm("确定要删除已选择资源？")){
	 	     return false;
	 	  }else{
	 	  	vcityResourceForm.action="<%=baseURL%>"+url;
			vcityResourceForm.submit();
	 	  	return true;
	 	  }
	}else{
		alert("请选择您要删除的资源！");
		return false;
	}
}

function deleteOne(url){

	if (!confirm("确定要删除该资源？")){
		return false;
	} else {
	 	vcityResourceForm.action="<%=baseURL%>"+url;
		vcityResourceForm.submit();
	 	return true;
	}
}

function vcityProduct(url){
	vcityResourceForm.action="<%=baseURL%>"+url;
	vcityResourceForm.submit();
}

function clearname(){
	document.getElementById("resName").value = "";
	document.getElementById("resType").value = "";
	vcityResourceForm.submit();
}

</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:98%;">
      <legend><strong class="greenFont14">体验资源列表</strong></legend><br>
	  <html:form action="/vcityResourceAction.do?action=showVcityResourceList" styleId="vcityResourceForm">
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
    <td width="53%" bgcolor="#f1f9fe" class=" tdl_B">资源名称：<html:text property="resName" styleId="resName"></html:text>&nbsp;&nbsp;
    资源类型： <html:select property="resType" styleId="resType">
					<html:option value="">请选择</html:option>
					<html:option value="1">应用</html:option>
					<html:option value="2">皮肤</html:option>
					</html:select>&nbsp;&nbsp;<input type="button"  class="btn_BS" onClick="vcityResourceForm.submit();" value="查 询" />&nbsp;&nbsp;<input type="button" onClick="clearname();"   class="btn_BS" value="清空条件" /></td>
    <td  height="40" bgcolor="#f1f9fe" class="tdr_B"><input name="Submit22" type="button" class="btn_BS" onClick="createResource();"  value=" 创建资源 "></td>
  </tr>
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%"><input type='CheckBox' id='selectedRowAll' name='selectedRowAll' onclick='selectAllGrid(this,"selectedRow")'>
              <a href="javascript:;" onClick="deleteAll('/vcityResourceAction.do?action=deleteVcityResource&todo=delete');">删除</a></td>
            <td width="8%">NO. </td>
            <td>资源名称 </td>
            <td width="15%">资源类别 </td>
            <td width="30%">操作</td>
          </tr>
        </thead>
		<c:if test="${!empty datas}">
		<c:forEach items="${datas}" var="resource" varStatus="stauts">
        <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
          <td align="center"><input type='CheckBox' name='selectedRow'
															value='${resource.id }'
															onclick='clearAllSelect(this,"selectedRowAll")'></td>
          <td align="center">${stauts.index+1 }</td>
          <td align="center">${resource.resName }</td>
          <td align="center"><c:if test="${resource.resType ==1}">应用</c:if><c:if test="${resource.resType ==2}">皮肤</c:if>  </td>
          <td align="center" class="tdc">
          <input type="button" onClick="updateResource(${resource.id });" class="btn_BS"value="编 辑" />
				<input type="button" onClick="deleteOne('/vcityResourceAction.do?action=deleteVcityResource&todo=delete&id=${resource.id }');" class="btn_BS"value="删 除" /><%--
				<input type="button" onClick="resApp('<%=baseURL %>/vcityResourceAppAction.do?action=doGetResourceUseTree&look=1&resid=${resource.id }',300,400);" class="btn_BS"value="查看应用" />
				<input type="button" onClick="copyToClipboard('');return(false)" class="btn_BS" value="统计查看" /></td>
        --%></tr>
		</c:forEach>
		</c:if>
		<tfoot>
          <tr>
            <td colspan="5"><wbase:dispartPage formId="vcityResourceForm" /></td>
          </tr>
		  <c:if test="${empty datas}">
			<tr>
				<td colspan="7" align="center">
					没有找到相应的记录
				</td>
			</tr>
		</c:if>
        </tfoot>
      </table>
	  </html:form>
</fieldset>
	
	</td>
  </tr>
</table>

	<wbase:showMessage />
</body>
<script type="text/javascript">
function copyToClipboard(url) {   
    if(url==null||url==""){
	    alert("该资源还没有链接地址！");
	    return false;
    }
    if(window.clipboardData) {   
    alert(url);
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
</html:html>
