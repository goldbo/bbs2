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
<title>3D手机虚拟体验中心</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/inc/style.css"></link>
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

<script type="text/javascript">
function oninfo(id){
	document.getElementById('info').style.display="block";
	document.getElementById('listConTop').style.display="none";
	workspace.location.href = 'vcityResourceAction.do?action=showVcityResourceInfo&vrId='+id;
}

	function showModel(moduleFileName,resurl,resid){
	if((moduleFileName==null||moduleFileName=="")&&(resurl==null||resurl=="")){
		alert("没有产品模型！");
		return false;
	}
	if(resurl!=""){
	ShowIframe('产品模型','<%=basePath%>vcity/produceOrder/produceOrder.jsp?file='+resurl+'&resid='+resid,630,630);
	}
	else {
	ShowIframe('产品模型','<%=basePath%>/vcity/show3D/show.jsp?file='+moduleFileName,640,480);
	}
}
</script>

</head>

<body>
<div id="wrap">
	<html:form action="/vcityResourcesAction.do?action=showVcityResourcesList" styleId="vcityResourceForm" method="post" >
	<div id="TopMenu">
	<!--搜索与登录入口 -->
		<div id="searchWrap">
			<ul>
				<li><strong>搜索：</strong></li>
				<li><html:text property="resName" name="vcityResourceForm" style="height:23px; border-bottom:1px solid #93a8a9;  border-top:1px solid #ffffff;line-height:22px; border-right:1px solid #ffffff; border-left:1px solid #ffffff;line-height:22px;"/></li>
				<li><a><img src="images/se_btn.jpg" align="absmiddle" onclick="vcityResourceForm.submit();"/></a>&nbsp;</li>
			</ul>
			<div id="UserInfo"><img src="images/icon_1.gif" align="absmiddle" />&nbsp;您还没登录？<a href="#" class="orange12">请登录</a>体验更多的服务。</div>
		</div>
	<!-- 主菜单 -->
		<div id="menuWrap">
			<ul>
				<li class="menu_off" onclick="location.href='<%=basePath %>/vcityResourcesAction.do?action=showVcityResourcesIndex';"><img src="images/pngmenu_1.png" class="pngimg" /></li>
				<li class="menu_line"></li>
				<li class="menu_on" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList';"><img src="images/pngmenu_2.png" class="pngimg" /></li>
			</ul>
		</div>
	</div>
	
	<!-- 推荐 -->
	<div id="BJConText">
		<!-- 最新右边 -->
		<div id="newList">
			<div class="BJConText_ConWrap">
				<div id="BJTitleName">${compareresource.vcityResType.resType } ${compareresource.resName }</div>
				<div class="ConWrapLeft2"><img src="<%=path %>/${compareresource.resImage }" onload="javascript:DrawImage(this,153,230)"/></div>
				<div class="ConWrapRight">
					
					<div class="BJConWrapRight_Info">
						<p><strong>基本参数</strong><br />
						${compareresource.resDesc }<br />
						</p>
				  </div>
			  </div>
			  <div class="ConListWrap"></div>
			</div>
		  	
		</div>
		
		<!-- 最热左边 -->
		<div id="hotList">
			<div class="BJConText_ConWrap">
				<div id="BJTitleName">${resource.vcityResType.resType } ${resource.resName }</div>
				<div  class="ConWrapLeft2"><img src="<%=path %>/${resource.resImage }" onload="javascript:DrawImage(this,153,230)"/></div>
				<div class="ConWrapRight">
					<a href="#"></a>
					<div  class="BJConWrapRight_Info">
					  <p><strong>基本参数</strong><br />
						${resource.resDesc }<br />
					 </p>
				  </div>
			  </div>
			  
			  <div class="ConListWrap"></div>
				<div class="clear"></div>
			</div>
		  	
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
</html>

