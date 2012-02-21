<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统计首页</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>
<style>
.listborder{border:1px solid #0099FF}
.listdiv{  border:3px solid #DFF4FD; padding:20px;}
.listdiv ul{ margin:0px; padding:0px;}
.listdiv li{ float:left; padding:5px 20px;}
</style>
<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">统计分析</strong></legend><br>
<h3><strong class="blueFont14">1、产品体验分析</strong></h3>
<div class="listborder">
	<div class="listdiv">
		<ul>
			<li><img src="<%=baseURL %>/images/icon_zj.jpg" width="14" height="17" align="absmiddle">&nbsp;<a href="<%=baseURL %>/statisticsAction.do?action=downloadTotal&resType=1">关注度统计</a></li>
			<li><img src="<%=baseURL %>/images/icon_zj.jpg" width="14" height="17" align="absmiddle">&nbsp;<a href="<%=baseURL %>/statisticsAction.do?action=praiseDegreeTotal&resType=1">好评度统计</a></li>
		</ul>
		<div style=" clear:both"></div>
	</div>
</div>
<br>


<h3><strong class="blueFont14">2、调查问卷分析</strong></h3>
<div class="listborder">
	<div class="listdiv">
		<ul>
			<li><img src="<%=baseURL %>/images/icon_zj.jpg" width="14" height="17" align="absmiddle">&nbsp;<a href="<%=baseURL %>/statisticsAction.do?action=showStatisticsSurveyList">调查问卷统计</a></li>
		</ul>
		<div style=" clear:both"></div>
		
	</div>
</div>
<br>
<h3><strong class="blueFont14">3、登录/访问统计</strong></h3>
<div class="listborder">
	<div class="listdiv">
		<ul>
			<li><img src="<%=baseURL %>/images/icon_zj.jpg" width="14" height="17" align="absmiddle">&nbsp;<a href="<%=baseURL %>/statisticsAction.do?action=showMemberLoginTotal">登录统计分析</a></li>
			<li><img src="<%=baseURL %>/images/icon_zj.jpg" width="14" height="17" align="absmiddle">&nbsp;<a href="<%=baseURL %>/statisticsAction.do?action=showAccessTotal">访问统计分析</a></li>
			<li><img src="<%=baseURL %>/images/icon_zj.jpg" width="14" height="17" align="absmiddle">&nbsp;<a href="<%=baseURL %>/statisticsAction.do?action=showIPTotal">IP访问统计分析</a></li>
		</ul>
		<div style=" clear:both"></div>
	</div>
</div>

</fieldset>
	
	</td>
  </tr>
</table>
</body>
</html>
