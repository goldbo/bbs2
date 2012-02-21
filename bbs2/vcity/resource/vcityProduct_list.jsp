<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html:html lang="true">
<head>
	<html:base />
	<title>产品体验列表</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
function gethref(url,width,height){
	window.returnValue = "<%=baseURL%>"+url;
	window.close();  
	
}
  

</script>
</head>

<body>
	<table width="100%" height="100%" align="center" border="0"
		cellspacing="0" cellpadding="0">
		<tr>
			<td height="30" valign="top" bgcolor="#f1f7fb">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="10" class="tab_kp_off">
							&nbsp;
						</td>
						<td width="100" height="26" class="tab_kp_on">
							产品管理
						</td>
						
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<table width="98%" align="center" border="1" cellspacing="0"
					cellpadding="0" bordercolor="#A6CCEB"
					style="border-collapse:collapse; margin:8px 0px;">
					<tr>
						<td class="table_thead tdl">
							产品体验列表
						</td>
					</tr>
					<tr>
						<td>
							<html:form action="/vcityResourceAction.do?action=showVictyResourceList"
								styleId="vcityResourceForm">
								<table>
									<tr height="35px">
										
										
										<td style="padding-right:5px" nowrap="nowrap">
											<wbase:powerButton onclick="vcityResourceForm.submit();"
												resNo="postManagement" type="4" value="产品体验" />
										</td>
										
										<td style="padding-right:5px" nowrap="nowrap">
											
										</td>
									</tr>
								</table>
								<table width="100%" border="1" cellspacing="0" cellpadding="0"
									bordercolor="#D0D0D0" style="border-collapse:collapse; ">
									<thead>
										<tr height="25px" align="center">
											<td width="5%" align="center" class="listheadbg">
												
											</td>
											<td>
												NO.
											</td>
											<td>
												资源名称
											</td>
											<td>
												资源类型
											</td>
											
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty datas}">
											<c:forEach items="${datas}" var="resource"
												varStatus="stauts">
												<tr onMouseMove="OM_table(this);"
													onMouseOut="OO_table(this);">
													<td align="center" height="25px">
														<input type="radio" name="selectedRow" value="${resource.id }"	onclick="gethref('/vcityResourceHomeAction.do?action=showVictyResourceList&resid=${resource.id }',800,600)">		
													</td>
													<td align="center">
														${stauts.index+1 }
													</td>
													<td align="center">
														${resource.resName }
													</td>
													<td align="center">
															产品体验
													</td>
												
												</tr>
											</c:forEach>
										
										</c:if>
										
														
										
										<c:if test="${empty datas}">
											<tr>
												<td colspan="7" align="center" bgcolor="#EFF3F7">
													没有找到相应的记录
												</td>
											</tr>
										</c:if>
										<tr ><td colspan="10">
										<wbase:dispartPage formId="vcityResourceForm" />
										</td></tr>
									</tbody>
								</table>
							</html:form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<wbase:showMessage />
</body>
	<script type="text/javascript">
    function copyToClipboard(obj) {   
         if(window.clipboardData) {   
                 window.clipboardData.clearData();   
                 window.clipboardData.setData("Text", obj.href); 
                  alert("复制成功！")  
         } else if(navigator.userAgent.indexOf("Opera") != -1) {   
              window.location = obj.href;   
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
              var copytext = obj.href;   
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
