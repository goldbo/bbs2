  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户端下载</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL%>/inc/style.css" ></link>
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
 #TopMenu, #leftListTop, #leftListBottom, #MListTitle, #listConTop, #listConBottom, #footer, .menu_on, .menu_line, #searchWrap ul{ behavior: url(inc/iepngfix.htc) }
 </style>
<![endif]-->
<style>
#leftList_left{float:left; width:293px;}
#leftList_right{ margin-left:293px;}
#leftListTop{ background:url(images/downlefttop.png) top no-repeat; height:100%; overflow:auto;}
#leftListBottom{height:25px; background:url(images/pngLeftList_bottom.png)}
#listConTop{background:url(images/pnglist_top.png) top no-repeat; height:100%; overflow:hidden;}
#listConTop h2{ font-size:16px; color:#355b84; margin:0px; padding:20px 0px 20px 40px;}
#listConTop span{float:right; width:80px; font-size:12px}
#listCon_right{float:right; width:310px; line-height:21px;}
#listCon_right h1{ font-size:14px; font-weight:bold;}
#listConBottom{background:url(images/pnglist_bottom.png) no-repeat; height:25px;}
#ConList{ padding-left:40px;  padding-top:70px;}
#ConList li{ float:left; width:200px; margin:0px 2px; line-height:25px;}
#ConList li img{border:0px}
#MListTitle{background:url(images/pngLeftListTitle.png) no-repeat center; height:40px; clear:both}
#MListConWrap{padding:8px 0px;}
</style>
<body>
<div id="wrap">
	<div id="TopMenu">
	<!--搜索与登录入口 -->

	<!-- 主菜单 -->
		<div id="menuWrap">
			<ul>
				<li class="menu_off"  onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesIndex';"><img src="<%=baseURL%>/images/icon_home.png" class="pngimg" /><br />首 页</li>
				<li class="menu_line"></li>
				<li class="menu_off"" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&resType=2';"><img src="<%=baseURL%>/images/icon_mobi.png" class="pngimg" /><br />手机模型</li>
				<li class="menu_line"></li>
				<li class="menu_off" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=showVcityResourcesList&resType=1';"><img src="<%=baseURL%>/images/icon_app.png" class="pngimg" /><br />应用中心</li>
				<li class="menu_line"></li>
				<li class="menu_on" onclick="location.href='<%=baseURL %>/vcityResourcesAction.do?action=toDownClient';"><img src="<%=baseURL%>/images/icon_down.png" class="pngimg" /><br />客户端下载</li>
			</ul>
	  </div>
	</div>
	<!-- 内容 -->
	<div id="MListConWrap">
		<!-- 左部 -->
		<div id="leftList_left">
			<div id="leftListTop">
				<div id="ConList">
					<ul>
						<li><span>名称：</span>手机应用体验平台</li>
						<li><span>版本：</span>1.1</li>
						<li><span>环境：</span>Window XP/2003/win7/</li>
						<li><br /><a title="60Mb内存适合初级玩家" href="<%=baseURL %>/vcityResourcesAction.do?action=downClient&downType=1"><img src="images/download_60.gif" /></a></li>
						<li><br /><a onclick="temphandle();"  title="150Mb内存适合中级玩家" href="javascript:;"><img src="images/download_150.gif" /></a><br /></li>
						<li><br /><a  onclick="temphandle();"  title="300Mb内存适合专业级玩家" href="javascript:;"><img src="images/download_300.gif" /></a><br /></li>
					</ul>
				</div>
				<div id="MListTitle"></div>
				<table align="center" width="90%" border="0" cellspacing="0" cellpadding="0" height="100px">
					<tr>
						<td></td>
					</tr>
			  </table>
			</div>
			<div id="leftListBottom"></div>
		</div>
		<!-- 右部 -->
		<div id="leftList_right">
			<div id="listConTop">
					<h2>手机应用体验平台</h2>
				<div id="listCon_right">
				</div>
				<div style=" margin-right:310px; padding-left:80px">
					<div style="text-align:center; width:330px;">
						<img src="images/appCenter.png"  />
					</div>
				</div>	
					<br>
					<br>
				<div id="listCon_right">
				</div>
				<div style=" margin-right:310px; padding-left:30px">
					<div style="text-align:center; width:330px;">
						<img src="images/skinCenter.png"  />
					</div>
				</div>
				<br>
				<br>
				<div id="listCon_right">
				</div>
				<div style=" margin-right:310px; padding-left:150px">
					<div style="text-align:center; width:330px;">
						<img src="images/model.png"  />
					</div>
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
</div>
</body>
  <script type="text/javascript">
  function temphandle(){
  	alert("即将上线,敬请期待！");
  	return false;
  	//<%=baseURL %>/vcityResourcesAction.do?action=downClient
  }
  </script>
</html>
