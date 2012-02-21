<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html:html lang="true">
<head>
	<html:base />
	<title>虚拟资源管理</title>

<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/inc/style.css" >
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="/bbs/vcity/inc/style.css">

	<script type="text/javascript">
function createPost(url,width,height){
	window.showModalDialog("<%=baseURL%>"+url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
	vcityResoprateForm.submit();
	
}

function updatePost(url,width,height){
	window.showModalDialog("<%=baseURL%>"+url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
	vcityResoprateForm.submit();

}

function deleteAll(url){
	var num = getCheckBoxNum("selectedRow");

	if(num>0){
	 if (!confirm("确定删除？")){
	 	     return false;
	 	  }else{
	 	  	vcityResoprateForm.action="<%=baseURL%>"+url;
			vcityResoprateForm.submit();
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
	 	vcityResoprateForm.action="<%=baseURL%>"+url;
		vcityResoprateForm.submit();
	 	return true;
	}
}
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:98%;">
      <legend><strong class="greenFont14">操作码列表</strong></legend><br>
	  <html:form action="/vcityResoprateAction.do?action=showVcityResoprateList" styleId="vcityResoprateForm">
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">

  <tr>
    <td width="53%" bgcolor="#f1f9fe" class=" tdl_B">名称：<html:text property="resoprateName" name="vcityResoprateForm"></html:text>&nbsp;<input type="button" name="query" class="btn_BS" value="查 询" onClick="vcityResoprateForm.submit();"></td>
    <td height="40" bgcolor="#f1f9fe" class="tdr_B">
	<input type="button" name="add" class="btn_BS" value="新增信息" onClick="createPost('/vcityResoprateAction.do?action=editVcityResoprate&todo=create',500,250);" />
	</td>
  </tr>
</table>
<br>
<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%"><input type='CheckBox' id='selectedRowAll' name='selectedRowAll' onclick='selectAllGrid(this,"selectedRow")'>
              <a href="javascript:;"  onClick="deleteAll('/vcityResoprateAction.do?action=deleteVcityResoprate&todo=delete');" >删除</a></td>
            <td width="8%">NO.</td>
            <td >信息名称</td>
            <td width="15%">操作码</td>
            <td width="15%">操作</td>
          </tr>
        </thead>
		<c:if test="${!empty ResoprateList}">
		 <c:forEach items="${ResoprateList}" var="resoprate" varStatus="stauts">
        <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
          <td align="center"><input type='CheckBox' name='selectedRow' value='${resoprate.id }' onclick='clearAllSelect(this,"selectedRowAll")'></td>
          <td align="center">${stauts.index+1 }</td>
          <td align="center">${resoprate.resoprateName }</td>
          <td align="center">${resoprate.resoCode }</td>
          <td align="center" class="tdc"><input type="button" name="edit" class="btn_BS" value="编 辑" onClick="updatePost('/vcityResoprateAction.do?action=editVcityResoprate&todo=update&id=${resoprate.id }',500,250);"  />&nbsp;
														<input type="button" name="del" class="btn_BS" value="删 除" onClick="deleteOne('/vcityResoprateAction.do?action=deleteVcityResoprate&todo=delete&id=${resoprate.id }');" /></td>
        </tr>
		</c:forEach>
		</c:if>
		
		<tfoot>
          <tr>
            <td colspan="5">
                <wbase:dispartPage formId="vcityResoprateForm" />
			</td>
          </tr>
        </tfoot>
		<c:if test="${empty ResoprateList}">
			<tr>
				<td colspan="7" align="center">
					没有找到相应的记录
				</td>
			</tr>
		</c:if>
      </table>
	  </html:form>
</fieldset>
	<wbase:showMessage />
	</td>
  </tr>
</table>

	<wbase:showMessage />
</body>
</html:html>
