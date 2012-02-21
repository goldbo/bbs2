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


<style>
#BJleftList_right{ }
#BJlistConTop{background:url(images/pnglistbg_top.png) top no-repeat; height:100%; overflow:auto;}
#BJlistConTop h2{ font-size:16px; color:#355b84; margin:0px; padding:20px 0px 20px 40px;}
#BJlistConTop span{float:right; width:80px; font-size:12px}
#BJlistCon_right{float:right; width:310px; line-height:21px;}
#BJlistCon_right h1{ font-size:14px; font-weight:bold;}
#BJlistConBottom{background:url(images/pnglistbg_bottom.png) no-repeat; height:25px;}
#ConList{ padding-left:40px;  padding-top:70px;}
#ConList li{ float:left; width:70px; margin:0px 2px; line-height:25px;}
#MListTitle{background:url(images/pngLeftListTitle.png) no-repeat center; height:40px; clear:both}
#MListConWrap{padding:8px 0px;}
#BJTitle{ padding:0px 8px 8px 8px; font-size:18px; font-weight:bold; color:#008000; margin-right:50px;}
#BJTitle a{ font-family:"宋体"; float:right; width:250px; text-align:right;}

#BJlistPro{ padding:30px 0px 30px 15px;}
#BJlistPro li{ float:left; width:220px; height:250px; margin:3px 24px 3px 3px;}
#BJlistPro h4{ font-size:12px; margin:0px; padding:4px 0px; text-align:center}
#BJlistPro p{ margin:0px; padding:0px; text-align:center}
.listImg{border:1px #b6b6b6 solid; height:170px; background-color:#FFFFFF}
#page_wrap{clear:both; height:30px; float:right; margin-right:20px; _margin-right:10px;}
#page_wrap a{ float:left; border:1px #b6b6b6 solid; padding:3px 6px; margin-right:2px; background-color:#FFFFFF;}
#page_wrap a:hover{COLOR: #278fd7; TEXT-DECORATION: none; font-style: normal; border:1px #278fd7 solid; padding:3px 6px; margin-right:2px;background-color:#eaf6ff;}
#page_wrap h4{float:left; border:1px #278fd7 solid; padding:3px 6px; margin:0px 2px 0px 0px; background-color:#246592; color:#ffffff; font-weight:bold}
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
	<html:form action="/vcityResourcesAction.do?action=VcityResourcesCompareList" styleId="vcityResourceForm" method="post" >
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
	<!-- 内容 -->
	<div id="MListConWrap">
		<!-- 上部 -->
		<div id="BJleftList_right">
			<div id="BJlistConTop">
				<div id="BJlistPro">
					<div id="BJTitle"><a href="#" onfocus="this.blur();">> </a>${resource.resName }&nbsp;&nbsp; ${resource.vcityResType.resType }手机对比 </div>
					<ul>
					<c:if test="${!empty typeList}">
					<c:forEach items="${typeList}" var="vcityResource" varStatus="stauts"  >
						<li>
							<p class="listImg"><img src="<%=path %>/${vcityResource.resImage  }"   height="170"/></p>
							<h4>${vcityResource.vcityResType.resType } ${vcityResource.resName } </h4>
							<p><a href="<%=baseURL %>/vcityResourcesAction.do?action=VcityResourcesCompare&resid=${resource.id }&resid2=${vcityResource.id  }"><img src="images/btn_3.jpg" border="0" align="absmiddle" /></a></p>
						</li>
					</c:forEach>
					</c:if>
					</ul>
			  </div>
				<div id="page_wrap">
				</div>
		  	</div>
			<div id="BJlistConBottom"></div>
		</div>
	</div>
	
		<!-- 下部 -->
		<div id="BJleftList_right">
			<div id="BJlistConTop">
				<div id="BJlistPro">
					<div id="BJTitle"><a href="#" onfocus="this.blur();"></a>${resource.resName } &nbsp;&nbsp;手机对比 </div>
					<ul>
					<c:if test="${!empty datas}">
					<c:forEach items="${datas}" var="vcityResource" varStatus="stauts"  >
						<li>
							<p class="listImg"><img src="<%=path %>/${vcityResource.resImage  }"   height="170"/></p>
							<h4>${vcityResource.vcityResType.resType } ${vcityResource.resName } </h4>
							<p><a href="<%=baseURL %>/vcityResourcesAction.do?action=VcityResourcesCompare&resid=${resource.id }&resid2=${vcityResource.id  }"><img src="images/btn_3.jpg" border="0" align="absmiddle" /></a></p>
						</li>
					</c:forEach>
					</c:if>
					</ul>

			  </div>
				<div id="page_wrap">
				<wbase:dispartPageDemo formId="vcityResourceForm" />
				</div>
		  	</div>
			<div id="BJlistConBottom"></div>
		</div>
	
	</html:form>	

	<div id="footer">
		<p>Copyright © 2011 china telecom. All rights reserved.<br />
		   版权所有(C) 中国电信集团公司 ICP证号：京ICP备09031924号
		   
		</p>
	</div>
</div>	

</body>
</html>

