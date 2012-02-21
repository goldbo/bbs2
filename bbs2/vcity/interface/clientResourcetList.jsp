<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>客户端接口－资源列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  
  <html:form action="/clientInterfaceAction.do?action=resourceList&resType=${vcityResourceForm.resType}" styleId="vcityResourceForm" method="post" >
  <input type="hidden" name="resType" value="${vcityResourceForm.resType} "/>
    <table border="1" cellspacing="0" cellpadding="0" id="rw_list">
        <thead>
          <tr>
           <td>NO. </td>
           <td>资源ID </td>
            <td>资源名称 </td>
            <td>资源类别 </td>
            <td>图片地址 </td>
            <td>下载地址 </td>
          </tr>
        </thead>
		<c:if test="${!empty datas}">
		<c:forEach items="${datas}" var="resource" varStatus="stauts">
        <tr>
          <td>${stauts.index+1 }</td>
          <td>${resource.id }</td>
          <td>${resource.resName }</td>
          <td><c:if test="${resource.resType ==1}">应用</c:if><c:if test="${resource.resType ==2}">皮肤</c:if>  </td>
        <td>${resource.fullImage }</td>
         <td><c:if test="${resource.resType ==1}">${resource.downFileName }</c:if><c:if test="${resource.resType ==2}"><a href="<%=basePath%>clientInterfaceAction.do?action=unZipFileList&dir=${resource.skinDirectory }" >${resource.skinDirectory }</a></c:if>  </td>
        </tr>
		</c:forEach>
		</c:if>
		<tfoot>
          <tr>
            <td colspan="5">
            这是总记录数：<input type="text" name="totalRows" value="${pager.totalRows }" >
            </td>
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
  </body>
</html>
