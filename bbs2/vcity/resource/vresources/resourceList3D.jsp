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
#leftList_right{ margin-left:10px;}
#listConTop{background:url(images/pnglist_to.png) top no-repeat; height:100%; overflow:auto;} 
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
#listPro li{ float:left; width:190px; height:250px; margin:3px;}
#listPro h4{ font-size:12px; margin:0px; padding:4px 0px; text-align:center}
#listPro p{ margin:0px; padding:0px; text-align:center}
.listImg{border:1px #b6b6b6 solid; height:170px; background-color:#FFFFFF}
#page_wrap{clear:both; height:30px; float:right; margin-right:80px; _margin-right:10px;}
#page_wrap a{ float:left; border:1px #b6b6b6 solid; padding:4px 6px; margin-right:2px; background-color:#FFFFFF;}
#page_wrap a:hover{COLOR: #278fd7; TEXT-DECORATION: none; font-style: normal; border:1px #278fd7 solid; padding:3px 6px; margin-right:2px;background-color:#eaf6ff;}
#page_wrap h4{float:left; border:1px #278fd7 solid; padding:4px 6px; margin:0px 2px 0px 0px; background-color:#246592; color:#ffffff; font-weight:bold}
#page_wrap input{float:left;width:23px;height:22px; padding:0px; border-bottom:1px solid #93a8a9;  border-top:1px solid #ffffff;line-height:30px; border-right:1px solid #ffffff; border-left:1px solid #ffffff;}
#page_wrap span{float:left; padding:7px 6px; margin:0px -60px 0px -3px; font-weight:10px}
a.imagehref{color:#FFFFFF;}
</style>

</head>

<body>
<div id="wrapss">
	<html:form action="/vcityResourcesAction.do?action=showResourceList3D" styleId="vcityResourceForm" method="post" >
	<input type="hidden" name="resType" value="${vcityResourceForm.resType }" />


		<!-- 右部 -->
		<div id="leftList_right">
			<div id="listConTop"><span id="typename"></span>
				<div id="listPro">
					<ul>
					<c:if test="${!empty datas}">
					<c:forEach items="${datas}" var="vcityResource" varStatus="stauts"  >
						<li>
							<p class="listImg"><a class="imagehref"  target="_blank" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" ><img border="0"  src="<%=path %>/${vcityResource.fullImage  }"   onload="javascript:DrawImage(this,185,170)"/></a></p>
							<h4> ${vcityResource.resName } </h4>
							<p><a target="_blank" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${vcityResource.id }&resType=${vcityResourceForm.resType }" ><img src="images/btn_1.jpg" border="0" align="absmiddle" /></a>&nbsp;
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
		</div>
	</div>
	</html:form>
</div>
</body>
</html>

