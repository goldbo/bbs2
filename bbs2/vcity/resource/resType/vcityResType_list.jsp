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
	vcityResTypeForm.submit();
	
}

function updatePost(url,width,height){
	window.showModalDialog("<%=baseURL%>"+url, "", "dialogWidth="+width+"px;dialogHeight="+height+"px;status:no;");
	vcityResTypeForm.submit();

}

function deleteAll(url){
	var num = getCheckBoxNum("selectedRow");

	if(num>0){
	 if (!confirm("该操作会连并删除下属虚拟资源，确定？")){
	 	     return false;
	 	  }else{
	 	  	vcityResTypeForm.action="<%=baseURL%>"+url;
			vcityResTypeForm.submit();
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
	 	vcityResTypeForm.action="<%=baseURL%>"+url;
		vcityResTypeForm.submit();
	 	return true;
	}
}
</script>
</head>

<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:98%;">
      <legend><strong class="greenFont14">虚拟资源分类列表</strong></legend><br>
<html:form action="/vcityResTypeAction.do?action=showVcityResTypeList" styleId="vcityResTypeForm">
<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
  <%--				<td nowrap="nowrap" align="right">--%>
										<%--					是否有效：--%>
										<%--				</td>--%>
										<%--				<td style="padding-right:5px" nowrap="nowrap">--%>
										<%--					<html:select property="isFlag" name="wbasePostForm">--%>
										<%--						<html:option value="3">--%>
										<%--							--全部----%>
										<%--						</html:option>--%>
										<%--						<html:option value="1">--%>
										<%--							是--%>
										<%--						</html:option>--%>
										<%--						<html:option value="0">--%>
										<%--							否--%>
										<%--						</html:option>--%>
										<%--					</html:select>--%>
										<%----%>
										<%--				</td>--%>
    <td width="53%" bgcolor="#f1f9fe" class=" tdl_B">分类名称：<html:text property="resType" name="vcityResTypeForm"></html:text>&nbsp;<input type="button" name="query" class="btn_BS" value="查 询" onClick="vcityResTypeForm.submit();"></td>
    <td height="40" bgcolor="#f1f9fe" class="tdr_B"><input name="Submit22" type="button" class="btn_BS" onClick="createPost('/vcityResTypeAction.do?action=editVcityResType&todo=create',500,250);" value=" 创建分类 "></td>
  </tr>
</table>
<br>

<table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">
        <thead>
          <tr>
            <td width="8%"><input type='CheckBox' id='selectedRowAll'
													name='selectedRowAll'
													onclick='selectAllGrid(this,"selectedRow")'>
              <a href="javascript:;" onClick="deleteAll('/vcityResTypeAction.do?action=deleteVcityResType&todo=delete');">删除</a></td>
            <td width="8%">NO. </td>
            <td>资源分类名称</td>
            <td width="25%">操作</td>
          </tr>
        </thead>
		<c:if test="${!empty datas}">
		<c:forEach items="${datas}" var="resType" varStatus="stauts">
        <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
          <td align="center"><input type='CheckBox' name='selectedRow'
															value='${resType.id }'
															onclick='clearAllSelect(this,"selectedRowAll")'></td>
          <td align="center">${stauts.index+1 }</td>
          <td align="center">${resType.resType }</td>
          <td align="center" class="tdc"><input type="button" name="edit" class="btn_BS" value="编 辑" onClick="updatePost('/vcityResTypeAction.do?action=editVcityResType&todo=update&id=${resType.id }',500,250);" />&nbsp;
														<input type="button" name="del" class="btn_BS" value="删 除" onClick="deleteOne('/vcityResTypeAction.do?action=deleteVcityResType&todo=delete&id=${resType.id }');" /></td>
        </tr>
		</c:forEach>
		</c:if>
			<tfoot>
          <tr>
            <td colspan="5"><wbase:dispartPage formId="vcityResTypeForm" /></td>
          </tr>
        </tfoot>
		<c:if test="${empty datas}">
			<tr>
				<td colspan="7" align="center">
					没有找到相应的记录
				</td>
			</tr>
		</c:if>
      </table>
	  </html:form>
</fieldset>
	
	</td>
  </tr>
</table>
	<wbase:showMessage />
</body>
</html:html>
