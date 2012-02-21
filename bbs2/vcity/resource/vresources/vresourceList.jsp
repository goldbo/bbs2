<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应用体验门户</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/inc/style.css" ></link>
<script type="text/javascript" src="<%=baseURL%>/common/js/popup.js"></script>
<!--[if IE 6]>
<script src="<%=path %>/inc/DD_belatedPNG_0.0.8a-min.js" mce_src="<%=path %>/inc/DD_belatedPNG_0.0.8a-min.js"></script>
<script type="text/javascript">    
 /* EXAMPLE */   
 DD_belatedPNG.fix('.pngimg');   
 /* 将 .png_bg 改成你应用了透明PNG的CSS选择器*/   
 </script> <![endif]-->  
<!--[if lt IE 7]>
 <style type="text/css">
 #TopMenu, #leftListTop, #leftListBottom, #MListTitle, #listConTop, #listConBottom, #footer, .menu_on, .menu_line, #searchWrap ul{ behavior: url(<%=path %>/inc/iepngfix.htc) }
 </style>
<![endif]-->


<style>
#leftList_left{float:left; width:293px;}
#leftList_right{ margin-left:293px;}
.leftListTop2{ background:url(images/mobilefttop.png) top no-repeat; height:100%; overflow:auto;}
.leftListTop1{ background:url(images/applefttop.png) top no-repeat; height:100%; overflow:auto;}
#leftListBottom{height:25px; background:url(images/pngLeftList_bottom.png)}
#listConTop{background:url(images/pnglist_top.png) top no-repeat; height:100%; overflow:auto;}
#listConTop h2{ font-size:16px; color:#355b84; margin:0px; padding:20px 0px 20px 40px;}
#listConTop span{float:right; width:80px; font-size:12px}
#listCon_right{float:right; width:310px; line-height:21px;}
#listCon_right h1{ font-size:14px; font-weight:bold;}
#listConBottom{background:url(images/pnglist_bottom.png) no-repeat; height:25px;}
#ConList{ padding-left:40px;  padding-top:70px;}
#ConList li{ float:left; width:100px; margin:0px 2px; line-height:25px;}
#MListTitle{background:url(images/pngLeftListTitle.png) no-repeat center; height:40px; clear:both}
#MListConWrap{padding:8px 0px;}

#listPro{ padding:30px 0px 30px 15px;}
#listPro li{ float:left; width:220px; height:250px; margin:3px;}
#listPro h4{ font-size:12px; margin:0px; padding:4px 0px; text-align:center}
#listPro p{ margin:0px; padding:0px; text-align:center}
.listImg{border:1px #b6b6b6 solid; height:170px; background-color:#FFFFFF}
#page_wrap{clear:both; height:30px; float:right; margin-right:20px; _margin-right:10px;}
#page_wrap a{ float:left; border:1px #b6b6b6 solid; padding:4px 6px; margin-right:2px; background-color:#FFFFFF;}
a.imagehref{color:#FFFFFF;}
#page_wrap a:hover{COLOR: #278fd7; TEXT-DECORATION: none; font-style: normal; border:1px #278fd7 solid; padding:3px 6px; margin-right:2px;background-color:#eaf6ff;}
#page_wrap h4{float:left; border:1px #278fd7 solid; padding:4px 6px; margin:0px 2px 0px 0px; background-color:#246592; color:#ffffff; font-weight:bold}
#page_wrap input{float:left;width:23px;height:22px; padding:0px; border-bottom:1px solid #93a8a9;  border-top:1px solid #ffffff;line-height:30px; border-right:1px solid #ffffff; border-left:1px solid #ffffff;}
#page_wrap span{float:left; padding:7px 6px; margin:0px -60px 0px -3px; font-weight:10px}
</style>

<script type="text/javascript">
function oninfo(id){
	document.getElementById('info').style.display="block";
	document.getElementById('listConTop').style.display="none";
	workspace.location.href = 'vcityResourceAction.do?action=showVcityResourceInfo&vrId='+id;
}
function showModel(moduleFileName,resurl,resid){
	if((moduleFileName==null||moduleFileName=="")&&(resurl==null||resurl=="")){
		ShowAlert("","没有产品模型！",150,100)
		//alert("没有产品模型！");
		return false;
	}
	if(resurl!=""){
	ShowIframe('产品模型','<%=basePath%>vcity/produceOrder/produceOrder.jsp?file='+resurl+'&resid='+resid,630,630);
//	window.open ('<%=basePath%>vcity/produceOrder/produceOrder.jsp?file='+resurl+'&resid='+resid, 'newwindow', 'height=768, width=1024, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
	}
	else {
	ShowIframe('产品模型','<%=request.getContextPath()%>/vcity/show3D/show.jsp?file='+moduleFileName,640,480);
//	window.open ('<%=basePath%>/vcity/show3D/show.jsp?file='+moduleFileName, 'newwindow', 'height=768, width=1024, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
	}
//http://localhost:8888/bbs/vcity/produceOrder/produceOrder.jsp?file=/vcity/produceOrder/n97&resid=4&cTask=110  
//http://localhost:8888/bbs/vcity/show3D/show.jsp?file=xt800   
}
function showtypefont(){
	document.getElementById("BJTitle").style.display="block";
}
</script>

</head>

<body>
<div id="wrap">
	<html:form action="/vcityResourcesAction.do?action=showVcityResourcesList" styleId="vcityResourceForm" method="post" >
	<input type="hidden" name="resType" value="${vcityResourceForm.resType }" />
	<div id="TopMenu">
	<!--搜索与登录入口 -->
	<!-- 主菜单 -->
		<div id="menuWrap">
			<ul>
				<li class="menu_off" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesIndex';"><img src="<%=baseURL%>/images/icon_home.png" class="pngimg" /><br />首 页</li>
				<li class="menu_line"></li>
				<li  <c:if test="${vcityResourceForm.resType==2 }"> class="menu_on"</c:if><c:if test="${vcityResourceForm.resType==1 }"> class="menu_off"</c:if> onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&resType=2';"><img src="<%=baseURL%>/images/icon_mobi.png" class="pngimg" /><br />手机模型</li>
				<li class="menu_line"></li>
				<li  <c:if test="${vcityResourceForm.resType==2 }"> class="menu_off"</c:if><c:if test="${vcityResourceForm.resType==1 }"> class="menu_on"</c:if> onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&resType=1';"><img src="<%=baseURL%>/images/icon_app.png" class="pngimg" /><br />应用中心</li>
				<li class="menu_line"></li>
				<li class="menu_off" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=toDownClient';"><img src="<%=baseURL%>/images/icon_down.png" class="pngimg" /><br />客户端下载</li>
			</ul>
		</div>
	</div>
	<!-- 内容 -->
	<div id="MListConWrap">
		<!-- 左部 -->
		<div id="leftList_left">
		<div id="leftListTop" <c:if test="${vcityResourceForm.resType==2 }"> class="leftListTop2"</c:if><c:if test="${vcityResourceForm.resType==1 }"> class="leftListTop1"</c:if>>
				<div id="ConList">
				<ul>
					<c:if test="${!empty allDatas}">
					<c:forEach items="${allDatas}" var="vcityResource" varStatus="stauts"  >
						<li><a title="${vcityResource.resName }" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" onclick="showtypefont()"><c:if test="${fn:length(vcityResource.resName) > 10}"> ${fn:substring(vcityResource.resName, 0, 10)}... </c:if>
						<c:if test="${fn:length(vcityResource.resName) <= 10}">  ${vcityResource.resName }</c:if> </a></li>
					</c:forEach>
					</c:if>
					</ul>
					<%--<ul>
					<c:if test="${!empty typeList}">
					<c:forEach items="${typeList}" var="resType" varStatus="stauts" end="20">
						<li><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&productType=${resType.id }" onclick="showtypefont()">${resType.resType }</a></li>
					</c:forEach>
					</c:if>
					<li><a href="javascript:void(0)" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList';showtypefont();">全部</a></li>
					</ul>
				--%></div>
				<div id="MListTitle"></div>
				<table align="center" width="90%" border="0" cellspacing="0" cellpadding="0">
				<c:if test="${!empty hotList}">
				<c:forEach items="${hotList}" var="vcityResource" varStatus="stauts"  begin="0" end="4">
				  <tr>
					<td width="60" height="60" align="center" class="border_line">
					<a class="imagehref" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" ><img border="0" src="<%=path %>/${vcityResource.fullImage }"  onload="javascript:DrawImage(this,60,60)" /></a></td>
					<td class="border_line"><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" >&nbsp;&nbsp;&nbsp;&nbsp; ${vcityResource.resName }</a>
					</td>
				  	
				  </tr>
				  
				</c:forEach>
				</c:if>
			  </table>
			</div>
			<div id="leftListBottom"></div>
		</div>
		<!-- 右部 -->
		<div id="leftList_right">
			<div id="listConTop"><span id="typename"></span>
				<div id="listPro">
				<c:if test="${!empty searchType}">
				<div id="BJTitle" ><a  style="font-size:15px; color:#0b72c1;"><strong>&nbsp;&nbsp;${searchType[0].vcityResType.resType}</strong></a> </div>
				<div id="BJTitle2" style="display:none;"><a  style="font-size:15px; color:#0b72c1;"><strong>&nbsp;&nbsp;${datas[0].vcityResType.resType}</strong></a> </div>
				</c:if>
				<c:if test="${!empty searchName}">
				<div id="BJTitle" ><a  style="font-size:15px; color:#0b72c1;"><strong>&nbsp;&nbsp;"${searchName}"搜索结果</strong></a> </div>
				</c:if>
					<ul>
					<c:if test="${!empty datas}">
					<c:forEach items="${datas}" var="vcityResource" varStatus="stauts"  >
						<li>
							<p class="listImg"><a class="imagehref" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" ><img border="0" src="<%=path %>/${vcityResource.fullImage  }"   onload="javascript:DrawImage(this,215,170)"/></a></p>
							<h4> ${vcityResource.resName } </h4>
							<p><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" ><img src="images/btn_1.jpg" border="0" align="absmiddle" /></a>&nbsp;<%--<a href="#" onclick="showModel('${vcityResource.moduleFileName  }','${vcityResource.resURL  }','${vcityResource.id }');"><img src="images/btn_2.jpg" border="0"  align="absmiddle"/></a>--%>
							</p>
						</li>
					</c:forEach>
					</c:if>
					</ul>
				</div>
				<div id="page_wrap">
				<wbase:dispartPageDemo formId="vcityResourceForm" />
				</div>
		  	</div>
			<div id="listConBottom"></div>
		</div>
	</div>
	<div id="footer">
		<p>Copyright © 2011 china telecom. All rights reserved.<br />
		   版权所有(C) 中国电信集团公司 ICP证号：京ICP备09031924号
		</p>
	</div>
	</html:form>
</div>
</body>
<script type="text/javascript">
window.onload = function() {
document.getElementById("leftListTop").style.height="572px";
}
</script>
</html>

