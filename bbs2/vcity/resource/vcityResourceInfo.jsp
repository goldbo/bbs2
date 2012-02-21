<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>3D手机虚拟体验中心</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/vcity/resource/inc/style.css">
</head>
<script type="text/javascript" src="<%=baseURL%>/vcity/resource/inc/popup.js"></script>
<script type="text/javascript"
		src="<%=baseURL%>/dwr/interface/vcityResourceService.js"></script>
	<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
	<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>
<script>
var dialogclosepath="<%=baseURL%>/vcity/resource/inc/close_off.gif";

//异步更新推荐或不推荐
function updateUpDown(resid,upordown){
	var acookie=document.cookie.split("; ");
	//获取单个cookies
	for(var i=0;i<acookie.length;i++){
		var arr=acookie[i].split("=");
		//alert(arr)
		if(resid==arr[0]){
			alert("你已经投票过了！");
			return false;
		}
	}
	vcityResourceService.updateVcityResourceUpOrDown(resid,upordown,
		function(bool){
			if(bool){
				var str = resid + "=" + escape(resid);
				document.cookie = str;
				if(upordown=="up"){
					document.getElementById("dis").style.display="none";
					document.getElementById("notdis1").style.display="block";
				} else {
					document.getElementById("dis").style.display="none";
					document.getElementById("notdis2").style.display="block";
				}
			}
		}
	);
}
</script>
<body>
<html:form action="/vcityResourceHomeAction.do?action=showVictyResourceList" 
	styleId="vcityResourceForm">
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
		
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
		  <tr>
		  <c:forEach items="${datas}" var="resource" varStatus="stauts">
			<td colspan="2">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td height="37"><strong class="blueFont18">${resource.resName }</strong></td>
					<td align="right"><a href="<%=baseURL%>/vcityResourceHomeAction.do?action=showVictyResourceList"><img src="<%=baseURL%>/vcity/resource/images/btn_r.jpg" width="85" height="26" border="0" /></a></td>
				  </tr>
				</table>			</td>
		  </tr>
		  <tr>
			<td width="46%" align="center" style="border:10px solid #e0eeff"><img src="<%=baseURL%>/${resource.resPic }" width="120" height="200" /></td>
			<td valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="title_bg">
				  <tr>
					<td><strong class="blackFont14">　基本参数</strong></td>
					<td align="right" style="padding-right:10px;">&nbsp;</td>
				  </tr>
				</table> 

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				 
				  <tr>
					<td style="line-height:23px; padding:10px 0px 10px 10px">
					 ${resource.resRemark }
					<td rowspan="2" width="165" align="right" valign="bottom"><img src="<%=baseURL%>/vcity/resource/images/pic_11.jpg" /></td>
				  </tr>
				  <tr> <c:if test="${!empty resource.resModel }">
					<td><a href="#" onclick="ShowIframe('产品模型','<%=baseURL%>/vcity/resource/3d/n930/n930.jsp?resmodel=${resource.resModel }',830,630)"><img src="<%=baseURL%>/vcity/resource/images/3dbtn.jpg" width="160" height="48" border="0" /></a></td>
				 </c:if>
				  </tr>
				</table>			</td>
			
		  </tr>
		</table>
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
		  <tr>
			<td width="50%" valign="top">
			
			<!-- -->
			
			 <table align="center" width="99%" border="0" cellspacing="0" cellpadding="0" class="title_bg">
			  <tr>
				<td><strong class="blackFont14">　你认为该产品值得推荐吗？</strong></td>
				<td align="right" style="padding-right:10px;"><a href="#" class="blue12"></a></td>
			  </tr>
			</table>
			  <div style="padding:10px;"><%--
			  	<a href="" id="test" onclick="return getck('${resource.id }','<%=baseURL%>/vcityResourceHomeAction.do?action=updateVcityResourceUporDown&resid=${resource.id }','up')">
			  		推 荐 </a>：<strong id="up">${resource.resUp }票</strong> <br/>
				  <a href="" id="test" onclick="return getck('${resource.id }','<%=baseURL%>/vcityResourceHomeAction.do?action=updateVcityResourceUporDown&resid=${resource.id }','down')">
				  不推荐 </a>： ${resource.resDown }票<br/>
				  
			  --%><span id="dis" ><input type="button" onclick="updateUpDown('${resource.id }','up')"  value="推 荐 "/>
			  		${resource.resUp }票<br/>
				  <input type="button"  onclick="updateUpDown('${resource.id }','down')" value="不推荐"/>
				    ${resource.resDown }票<br/>
			  </span>
			  <span id="notdis1" style="display: none;"><input type="button" onclick="updateUpDown('${resource.id }','up')"  value="推 荐 "/>
			  		${resource.resUp +1}票<br/>
				 <input type="button"  onclick="updateUpDown('${resource.id }','down')" value="不推荐"/>
				  	${resource.resDown }票<br/>
			  </span>
			  <span id="notdis2" style="display: none;"><input type="button" onclick="updateUpDown('${resource.id }','up')"  value="推 荐 "/>
			  		${resource.resUp }票<br/>
				  <input type="button"  onclick="updateUpDown('${resource.id }','down')" value="不推荐"/>
				  	 ${resource.resDown +1 }票<br/>
			  </span>
			  </div>
			<!-- -->
			</td>
			<td valign="top">
			<!-- -->
			<table align="center" width="99%" border="0" cellspacing="0" cellpadding="0" class="title_bg">
          <tr>
            <td><strong class="blackFont14">　产品分享</strong></td>
            <td align="right" style="padding-right:10px;"><a href="#" class="blue12"></a></td>
          </tr>
        </table>
		<div style="padding:10px; line-height:25px">
		把产品分享到：

			<div id="jiathis_style_32x32">
				<a class="jiathis_button_qzone"></a>
				<a class="jiathis_button_tsina"></a>
				<a class="jiathis_button_tqq"></a>
				<a class="jiathis_button_kaixin001"></a>
				<a class="jiathis_button_renren"></a>
				<a href="http://www.jiathis.com/share/" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
			</div>
			<script type="text/javascript" src="http://v1.jiathis.com/code/jia.js" charset="utf-8"></script>
	
			<br />
			<%--<div class="shareto_toolbox">
				<a class="shareto_button_tsina"></a>
				<a class="shareto_button_qq"></a>
				<a class="shareto_button_qzone"></a>
				<a class="shareto_button_kaixin001"></a>
				<a class="shareto_button_renren"></a>
				<a class="shareto_button_douban"></a>
				<span class="shareto_separator">|</span>
				<a href="http://shareto.com.cn/share.html" class="shareto_button_compact">更多</a>
			</div>
				<script type="text/javascript" src="http://s.shareto.com.cn/js/shareto_button.js" charset="utf-8"></script>
		--%></div>
			
			</td>
		  </tr>
		</table>
</c:forEach>
	   
	    
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
<script type="text/javascript">
//js获取cookie
//这个是要刷新页面的推荐与不推荐，暂时没使用了
function getck(resid,url,upordown){
	var acookie=document.cookie.split("; ");
	//获取单个cookies
	for(var i=0;i<acookie.length;i++){
		var arr=acookie[i].split("=");
		//alert(arr)
		if(resid==arr[0]){
			alert("你已经投票过了！");
			return false;
		}
	}

	var e = document.getElementById("test");
	 e.href = url+"&upordown="+upordown;
	
} 



</script>
</body>

</html>
