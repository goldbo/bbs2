<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>3D手机虚拟体验中心</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/resource/inc/style.css">
</head>
<script type="text/javascript" src="<%=baseURL%>/vcity/resource/inc/popup.js"></script>
<script>
var dialogclosepath="<%=baseURL%>/vcity/resource/inc/close_off.gif";
</script>
<body>
<html:form action="/vcityResourceHomeAction.do?action=showVictyResourceList&flag=1" 
	styleId="vcityResourceForm" >
<table align="center" width="996" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:5px;">
  <tr>
    <td width="40%" height="75" style="background:url(<%=baseURL%>/vcity/resource/images/top_bline.jpg) bottom no-repeat"><img src="<%=baseURL%>/vcity/resource/images/logo.jpg" /></td>
    <td valign="bottom">
  		  <table height="37" width="100%" border="0" cellspacing="0" cellpadding="0" style="background:url(<%=baseURL%>/vcity/resource/images/top_rw.jpg) no-repeat right">
		      <tr>
		        <td><img src="<%=baseURL%>/vcity/resource/images/top_lw.jpg" width="40" height="37" /></td>
		        <td align="right"><strong class="whiteFont14">搜索：</strong></td>
		        <td style="background:url(<%=baseURL%>/vcity/resource/images/menu_sc.jpg) no-repeat center; text-align:center"width="330">
		        <%--<input type="text" name="textfield" style="width:300px; border:0px;" value="" />
		        --%><html:text property="resName" name="vcityResourceForm" style="width:300px; border:0px;"></html:text>
		        </td>
		        <td width="110"><img src="<%=baseURL%>/vcity/resource/images/menu_btn.jpg" width="98" height="25" border="0" onclick="vcityResourceForm.submit()"/></td>
		      </tr>
  		  </table>
  	</td>
  </tr>
</table>
</html:form>


<table align="center" width="996" border="0" cellspacing="0" cellpadding="0">
  	<tr>
   		 <td width="232" bgcolor="#f3f8ff" valign="top">
	<!--品牌 -->
	
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		 		 <tr>
						<td height="47" class="left_title">资源分类</td>
		 		 </tr>
				  <tr>
						<td>
							<div class="pplist">
					              <ul>
					           		 <logic:iterate id="type" name="typeList" scope="request" indexId="index">
					           		 <html:hidden property="resType" name="vcityResourceForm" />
					                		<li><a title="${type.resType }"  href="<%=baseURL%>/vcityResourceHomeAction.do?action=showVictyResourceList&type=${type.id }">${type.resType }</a></li>
					              	 </logic:iterate>            
					              </ul>
		   					 </div>
						</td>
				  </tr>
			</table>

	<!--end 品牌 -->
	<p>&nbsp;</p>
	<!--最新体验 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td height="47" class="left_title">最新体验</td>
				  </tr>
				  <tr>
						<td>
							<table align="center" width="90%" border="0" cellspacing="0" cellpadding="0">
							<c:forEach items="${datas}" var="resource" varStatus="stauts" end="4">
							  <tr>
								<td width="60" height="60" align="center" class="border_line"><img src="<%=baseURL%>/${resource.resPic}" width="23" height="49" /></td>
								<td class="border_line"><a href="<%=baseURL%>/vcityResourceHomeAction.do?action=showVictyResourceList&resid=${resource.id }">
									${resource.resName }</a></td>
							  </tr>
							  </c:forEach>
							  
						  </table>
					</td>
			 	 </tr>
				</table>
	<!--end 最新体验 -->
		</td>
		<td width="5"></td>
  		  <td valign="top">
    			<table width="100%" border="0" cellspacing="15" cellpadding="0">
  	
		  			 <c:if test="${!empty datas}">
		  				<tr>
							<c:forEach items="${datas}" var="resource" varStatus="stauts">
								<c:if test="${stauts.index%4==0}"></tr></c:if>
								<td width="25%" align="center"><img src="<%=baseURL%>/${resource.resPic}" width="79" height="155" ><br />
				        			<br />  
				        			${resource.resName }
										<div style="padding:10px 0px;">
					    				  <a href="<%=baseURL%>/vcityResourceHomeAction.do?action=showVictyResourceList&resid=${resource.id }"><img src="<%=baseURL%>/vcity/resource/images/btn_1.jpg" width="74" height="25" border="0" /></a>&nbsp;&nbsp;
					    				 <c:if test="${!empty resource.resModel }">
					    				 	 <a href="#" onclick="ShowIframe('产品模型','<%=baseURL %>/vcity/resource/3d/n930/n930.jsp?resmodel=${resource.resModel }',830,630)"><img src="<%=baseURL%>/vcity/resource/images/btn_2.jpg" border="0" /></a>	
			    						</c:if>
			    						<%--<c:if test="${empty resource.resModel }">
					    				 	 <img src="<%=baseURL%>/vcity/resource/images/btn_2.jpg" border="0" />
			    						</c:if>
			    						--%></div>	 
		    					 </td>
		    			</c:forEach>
					</tr>
				</c:if>
 	
			</table> 
				<wbase:dispartPage formId="vcityResourceForm" />
				
		</td>
	  </tr>
	 
</table>


<table align="center" width="996" border="0" cellspacing="0" cellpadding="0"  style="border-top:1px solid #cccccc; margin-top:5px;">
  <tr>
    <td align="center" style="line-height:20px;">
	<br />
	Copyright © 2011 china telecom. All rights reserved.<br />  
	版权所有(C) 中国电信集团公司 ICP证号：<a href="http://www.miibeian.gov.cn" target="_blank">京ICP备09031924号</a>	<br />&nbsp;</td>
  </tr>
</table>

</body>
</html>
