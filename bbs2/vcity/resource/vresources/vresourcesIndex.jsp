<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应用体验门户</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/inc/style.css" />
</head>
<!--[if IE 6]>
<script src="inc/DD_belatedPNG_0.0.8a-min.js" mce_src="inc/DD_belatedPNG_0.0.8a-min.js"></script>
<script type="text/javascript">    
 /* EXAMPLE */   
 DD_belatedPNG.fix('.pngimg');   
 /* 将 .png_bg 改成你应用了透明PNG的CSS选择器*/   
 </script> <![endif]-->  
<!--[if lt IE 7]>
 <style type="text/css">
 #TopMenu, #banner, #ConText, #footer, .menu_on, .menu_line, #searchWrap ul{ behavior: url(inc/iepngfix.htc) }
 </style>
<![endif]-->
<script type="text/javascript">
	function addCommon(flag){

			//var id = document.getElementById("").value;
			if(flag=="up"){
			//alert("11");
				vcityResCommonService.saveVcityResCommon(0,'${IP }',1,CallBack1);
			}
			else {
				vcityResCommonService.saveVcityResCommon(0,'${IP }',2,CallBack2);
			}
		}
	function CallBack1(returnValue){
	//alert(returnValue); 
		if(returnValue){
			document.getElementById("dis").style.display="none";
			document.getElementById("notdis1").style.display="block";
		}
	}
	function CallBack2(returnValue){
		if(returnValue){
			document.getElementById("dis").style.display="none";
			document.getElementById("notdis1").style.display="none";
			document.getElementById("notdis2").style.display="block";
		}
	}
	function showModel(moduleFileName,resurl,resid){
	if((moduleFileName==null||moduleFileName=="")&&(resurl==null||resurl=="")){
		ShowAlert("","没有产品模型！",150,100)
		return false;
	}
	if(resurl!=""){
	ShowIframe('产品模型','<%=basePath%>vcity/produceOrder/produceOrder.jsp?file='+resurl+'&resid='+resid,630,630);
	}
	else {
	ShowIframe('产品模型','<%=basePath%>/vcity/show3D/show.jsp?file='+moduleFileName,640,480);
	}
}

function bannerOV(id){
	for(var i=1; i<3; i++){
		document.getElementById('banner_right_'+i).className='banner_right_off';
		document.getElementById('bannerspan_'+i).style.display='none';
	}
	document.getElementById('banner_right_'+id).className='banner_right_on';
	document.getElementById('bannerspan_'+id).style.display='';
}
</script>
<body>
<div id="wrap">
	<div id="TopMenu">
	<!--搜索与登录入口 -->
	<!-- 主菜单 -->
		<div id="menuWrap">
			<ul>
				<li class="menu_on" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesIndex';"><img src="<%=baseURL%>/images/icon_home.png" class="pngimg" /><br />首 页</li>
				<li class="menu_line"></li>
				<li class="menu_off" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&resType=2';"><img src="<%=baseURL%>/images/icon_mobi.png" class="pngimg" /><br />手机模型</li>
				<li class="menu_line"></li>
				<li class="menu_off" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&resType=1';"><img src="<%=baseURL%>/images/icon_app.png" class="pngimg" /><br />应用中心</li>
				<li class="menu_line"></li>
				<li class="menu_off" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=toDownClient';"><img src="<%=baseURL%>/images/icon_down.png" class="pngimg" /><br />客户端下载</li>
			</ul>
		</div>
	</div>
	<!-- 推荐 -->
	<div id="banner" >
		<div id="banner_right" >
			<!--第一行 -->
			<div class="banner_right_on" id="banner_right_1" onmouseover="bannerOV(1)">
				<div class="bannerCon">
						<p><div style="float:right; width:80px; line-height:18px; text-align:right">${recommendAppList[0].createDate }</div>
						<div class="blueTitleFont" style="margin-right:80px;"> <a style="font-size:18px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendAppList[0].id }&resType=${recommendAppList[0].resType }"> ${recommendAppList[0].resName }</a></div></p>
						<p><div class="btn_1" title="点击量">${recommendAppList[0].accessTotal }</div>
						<div class="btn_2">${recommendAppList[0].up }</div>
						<div class="btn_3">${recommendAppList[0].down }</div>
						</p>
						<p><img src="images/icon_2.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendAppList[0].id }&resType=${recommendAppList[0].resType }">详细参数</a>　
						　<img src="images/icon_3.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendAppList[0].id }&resType=${recommendAppList[0].resType }">网友点评</a>　
<%--						　<img src="images/icon_4.gif" width="15" height="15" align="absmiddle" /> <a href="javascript:void(0);" onclick="showModel('${recommendAppList[0].moduleFileName  }','${recommendAppList[0].resURL  }','${recommendAppList[0].id }');">立即体验</a>--%>
						</p>
				</div>
				<div class="bannerPic"><img src="<%=path %>/${recommendAppList[0].fullImage }" onload="javascript:DrawImage(this,110,110)" /></div>
			</div>
			<div class="padding"></div>
			<!--第二行 -->
			<div class="banner_right_off" id="banner_right_2" onmouseover="bannerOV(2)">
				<div class="bannerCon">
						<p><div style="float:right; width:80px; line-height:18px; text-align:right">${recommendSkinList[0].createDate }</div>
						<div class="blueTitleFont" style="margin-right:80px;"><a style="font-size:18px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendSkinList[0].id }&resType=${recommendSkinList[0].resType }"> ${recommendSkinList[0].resName }</a></div></p>
						<p><div class="btn_1" title="点击量">${recommendSkinList[0].accessTotal }</div>
						<div class="btn_2">${recommendSkinList[0].up }</div>
						<div class="btn_3">${recommendSkinList[0].down }</div>
						</p>
						<p><img src="images/icon_2.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendSkinList[0].id }&resType=${recommendSkinList[0].resType }">详细参数</a>　
						　<img src="images/icon_3.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendSkinList[0].id }&resType=${recommendSkinList[0].resType }">网友点评</a>　　
<%--						<img src="images/icon_4.gif" width="15" height="15" align="absmiddle" /> <a href="javascript:void(0);" onclick="showModel('${recommendSkinList[0].moduleFileName  }','${recommendSkinList[0].resURL  }','${recommendSkinList[0].id }');">立即体验</a>--%>
						</p>
				</div>
				<div class="bannerPic"><img src="<%=path %>/${recommendSkinList[0].fullImage }" onload="javascript:DrawImage(this,110,110)" /></div>
			</div>
			
		</div>
		<!--幻灯图 -->
		<span id="bannerspan_1">
		<a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendAppList[0].id }&resType=${recommendAppList[0].resType }"><img src="<%=baseURL %>/${recommendAppList[0].viewRecommendImage }" onload="javascript:DrawImage(this,533,340)" border="0"/></a>
		</span>
		<span id="bannerspan_2" style="display:none;">
		<a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${recommendSkinList[0].id }&resType=${recommendSkinList[0].resType }"><img src="<%=baseURL %>/${recommendSkinList[0].viewRecommendImage }" onload="javascript:DrawImage(this,533,340)" border="0"/></a>
		</span>
	</div>
	<div id="ConText">
		<!-- 热门应用 -->
		<div id="newList">
			<div class="ConText_ConWrap">
			<table width="488" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="200" height="235" rowspan="2" align="center" valign="top"><img src="<%=path %>/${hotAppList[0].fullImage }" onload="javascript:DrawImage(this,150,230)"/></td>
				<td valign="top">
				<strong class="blueTitleFont">&nbsp;&nbsp;&nbsp;<a style="font-size:18px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[0].id }&resType=${hotAppList[0].resType }">${hotAppList[0].resName }</a></strong><br />
				<%--<a href="javascript:void(0);" onclick="showModel('${hotAppList[0].moduleFileName  }','${hotAppList[0].resURL  }','${hotAppList[0].id }');"><img src="images/pngbtn_1.png" border="0"/></a>--%>
				</td>
			  </tr>
			  <tr>
				<td>
				
				<div class="clear"></div>
				<div class="btn_1" title="点击量">${hotAppList[0].accessTotal }</div>
				<div class="btn_2">${hotAppList[0].up }</div>
				<div class="btn_3">${hotAppList[0].down }</div>
				<div class="clear"></div>
				<div style="padding:8px 0">	
				<img src="images/icon_2.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[0].id }">详细参数</a>　　<img src="images/icon_3.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[0].id }">网友点评</a>　　<!-- <img src="images/icon_4.gif" width="15" height="15" align="absmiddle" /> <a href="#" onclick="showModel('${hotAppList[0].moduleFileName  }','${hotAppList[0].resURL  }','${hotAppList[0].id }');">立即体验</a> -->
				</div>
				</td>
			  </tr>
			</table>
			<br />
			<table width="488" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="130" align="center"><img src="<%=path %>/${hotAppList[1].fullImage }" onload="javascript:DrawImage(this,110,110)" /></td>
				<td width="114"><p class="sTitleblue"><strong ><a style="font-size:12px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[1].id }">${hotAppList[1].resName }</a></strong></p>
						<p class="Pcss_s1"><img src="images/icon_5.gif" align="absmiddle" /><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[1].id }&resType=${hotAppList[1].resType }">&nbsp;详细参数</a></p>
<%--						<p class="Pcss_s1"><img src="images/icon_6.gif" align="absmiddle" /><a href="javascript:void(0);" onclick="showModel('${hotAppList[1].moduleFileName  }','${hotAppList[1].resURL  }','${hotAppList[1].id }');">&nbsp;立即体验</a></p>--%>
				</td>
				<td width="130" align="center"><img src="<%=path %>/${hotAppList[2].fullImage }" onload="javascript:DrawImage(this,110,110)" /></td>
				<td>
				<p class="sTitleblue">
				<strong><a style="font-size:12px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[2].id }&resType=${hotAppList[2].resType }">${hotAppList[2].resName }</a></strong></p>
<p class="Pcss_s1"><img src="images/icon_5.gif" align="absmiddle" /><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotAppList[2].id }">&nbsp;详细参数</a></p>
<%--<p class="Pcss_s1"><img src="images/icon_6.gif" align="absmiddle" /><a href="javascript:void(0);" onclick="showModel('${hotAppList[2].moduleFileName  }','${hotAppList[2].resURL  }','${hotAppList[2].id }');">&nbsp;立即体验</a></p>--%>
				</td>
			  </tr>
			</table>
			</div>
		</div>
		
		<!-- 最热门皮肤-->
		<div id="hotList">
			<div class="ConText_ConWrap">
			<table width="488" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="200" height="235" rowspan="2" align="center" valign="top"><img src="<%=path %>/${hotSkinList[0].fullImage }" onload="javascript:DrawImage(this,150,230)"/></td>
				<td valign="top">
				<strong class="blueTitleFont">&nbsp;&nbsp;&nbsp;<a style="font-size:18px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[0].id }&resType=${hotSkinList[0].resType }">${hotSkinList[0].resName }</a></strong><br />
				<%--<a href="javascript:void(0);" onclick="showModel('${hotSkinList[0].moduleFileName  }','${hotSkinList[0].resURL  }','${hotSkinList[0].id }');"><img src="images/pngbtn_1.png" border="0"/></a>--%>
				</td>
			  </tr>
			  <tr>
				<td>
				
					<div class="clear"></div>
					  <div class="btn_1" title="点击量">${hotSkinList[0].accessTotal }</div>
					  <div class="btn_2">${hotSkinList[0].up }</div>
					  <div class="btn_3">${hotSkinList[0].down }</div>
					  <div class="clear"></div>
					  <div style="padding:8px 0">	
							<img src="images/icon_2.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[0].id }&resType=${hotSkinList[0].resType }">&nbsp;详细参数</a>　　<img src="images/icon_3.gif"  align="absmiddle"/> <a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[0].id }">网友点评</a>　　<!-- <img src="images/icon_4.gif" width="15" height="15" align="absmiddle" /> <a href="#" onclick="showModel('${hotSkinList[0].moduleFileName  }','${hotSkinList[0].resURL  }','${hotSkinList[0].id }');">立即体验</a> -->
					  </div>
				</td>
			  </tr>
			</table>
			<br />
			<table width="488" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="130" align="center"><img src="<%=path %>/${hotSkinList[1].fullImage }" onload="javascript:DrawImage(this,110,110)" /></td>
				<td width="114">
						<p class="sTitleblue"><strong><a style="font-size:12px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[1].id }&resType=${hotSkinList[1].resType }">${hotSkinList[1].resName }</a></strong></p>
						<p class="Pcss_s1"><img src="images/icon_5.gif" align="absmiddle" /><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[1].id }&resType=${hotSkinList[1].resType }">&nbsp;详细参数</a></p>
<%--						<p class="Pcss_s1"><img src="images/icon_6.gif" align="absmiddle" /><a href="javascript:void(0);" onclick="showModel('${hotSkinList[1].moduleFileName  }','${hotSkinList[1].resURL  }','${hotSkinList[1].id }');">&nbsp;立即体验</a></p>--%>
				</td>
				<td width="130" align="center"><img src="<%=path %>/${hotSkinList[2].fullImage }" onload="javascript:DrawImage(this,110,110)" /></td>
				<td>
						<p class="sTitleblue"><strong><a style="font-size:12px; color:#0b72c1;" href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[2].id }&resType=${hotSkinList[2].resType }">${hotSkinList[2].resName }</a><br /></strong></p>
						<p class="Pcss_s1"><img src="images/icon_5.gif" align="absmiddle" /><a href="<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourceInfo&id=${hotSkinList[2].id }&resType=${hotSkinList[2].resType }">&nbsp;详细参数</a></p>
<%--						<p class="Pcss_s1"><img src="images/icon_6.gif" align="absmiddle" /><a href="javascript:void(0);" onclick="showModel('${hotSkinList[2].moduleFileName  }','${hotSkinList[2].resURL  }','${hotSkinList[2].id }');">&nbsp;立即体验</a></p>--%>
				</td>
			  </tr>
			</table>
		  </div>
		</div>
		
	</div>
	<!--底部 -->
	<div id="footer">
		<p>Copyright © 2011 china telecom. All rights reserved.<br />
		   版权所有(C) 中国电信集团公司 ICP证号：京ICP备09031924号
		</p>
	</div>
</div>
</body>
</html>
