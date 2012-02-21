<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>应用体验门户</title>

<link rel="stylesheet" type="text/css" href="<%=baseURL%>/inc/style.css"></link>

</head>
<script type="text/javascript" src="<%=baseURL%>/common/js/popup.js"></script>
<!--[if IE 6]>
<script src="<%=baseURL%>/inc/DD_belatedPNG_0.0.8a-min.js" mce_src="<%=baseURL%>/inc/DD_belatedPNG_0.0.8a-min.js"></script>
<script type="text/javascript">    
 /* EXAMPLE */   
 DD_belatedPNG.fix('.pngimg');   
 /* 将 .png_bg 改成你应用了透明PNG的CSS选择器*/   
 </script> <![endif]-->  
<!--[if lt IE 7]>
 <style type="text/css">
 #TopMenu, #leftListTop, #leftListBottom, #MListTitle, #listConTop, #listConBottom, #footer, .menu_on, .menu_line, #searchWrap ul{ behavior: url(<%=baseURL%>/inc/iepngfix.htc) }
 </style>
<![endif]-->
<style> 
#leftList_right{margin-left:10px overflow:auto;}
#leftListBottom{height:25px; background:url(<%=baseURL%>/images/pngLeftList_bottom.png)}
#listConTop{background:url(<%=baseURL%>/images/pnglist_top.png) left top no-repeat;margin-left:10px;margin-right:10px; height:100%; overflow:auto;}
#listConTop h2{ font-size:16px; color:#355b84; margin:0px; padding:20px 0px 20px 70px;}
#listConTop span.titlespan{float:right; width:250px; font-size:12px;}
#listCon_right{float:right; width:200px; line-height:21px;height:100%;margin-right:200px}
#listCon_right h1{ font-size:14px; font-weight:bold;}
#listConBottom{background:url(<%=baseURL%>/images/pnglist_bottom.png) no-repeat; height:25px;}
#ConList{ padding-left:40px;  padding-top:70px;}
#ConList li{ float:left; width:120px; margin:0px 2px; line-height:25px;}
#MListTitle{background:url(<%=baseURL%>/images/pngLeftListTitle.png) no-repeat center; height:40px; clear:both}
#MListConWrap{padding:8px 0px;}
#dis a, #dis2 a, #notdis2 a, #notdis1 a{ cursor:pointer}
.spanfont14{font-size:14px; color:#666666}

</style>
<script type="text/javascript" src="<%=baseURL%>/vcity/resource/inc/popup.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/interface/vcityResCommonService.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=baseURL%>/dwr/engine.js"></script>

<script type="text/javascript">

	function addCommon(flag){
	var userName = '${jsprun_userss }';
	var resid = ${resource.id};
	var num=document.getElementById("cookieflag").value;
	var acookie=document.cookie.split("; ");
	//获取单个cookies
	for(var i=0;i<acookie.length;i++){
		var arr=acookie[i].split("=");
	if(resid==arr[0]){
			alert("你已经投票过了！");
			return false;
		}
		
	}
			if(flag=="up"){
				//document.getElementById("cookieflag").value=${resource.id+1};
				vcityResCommonService.saveVcityResCommon(${resource.id},'${IP }',userName,1,CallBack1);
			}
			else {
			//	document.getElementById("cookieflag").value=${resource.id+1};
				vcityResCommonService.saveVcityResCommon(${resource.id},'${IP }',userName,2,CallBack2);
			}
		}
	function CallBack1(returnValue){
		if(returnValue){
		resid = document.getElementById("cookieflag").value;
				var str = resid + "=" + escape(resid);
				document.cookie = str;
			document.getElementById("dis").style.display="none";
			document.getElementById("notdis1").style.display="block";
		}
	}
	function CallBack2(returnValue){
		if(returnValue){
		resid = document.getElementById("cookieflag").value;
			var str = resid + "=" + escape(resid);
				document.cookie = str;
			document.getElementById("dis2").style.display="none";
			document.getElementById("notdis2").style.display="block";
		}
	}

function saveCom(){
	document.getElementById("todo1").value='saveCommon';
	var comment = document.getElementById("comment").value;
	comment = comment.replace(/<\//g,"");
	comment = comment.replace(/</g,"");
	document.getElementById("comment").value = comment;
	var vcityResourceForm = document.getElementById("vcityResourceForm");
	vcityResourceForm.action = '<%=baseURL%>/vcityResourcesAction.do?action=showResourceInfo3D&id=${resource.id }&resType=${resource.resType }';
	vcityResourceForm.submit();
	
}
</script>
<body>
<html:form action="/vcityResourcesAction.do?action=showResourceInfo3D&id=${resource.id }" styleId="vcityResourceForm" method="post" >
<input id="cookieflag" type="hidden" value="${resource.id}" ></input>
<input name="todo" id="todo1"  type="hidden" value=""/>
<input name="radomID" id="radomID1" type="hidden" value="<%=Math.random() %>"/>
<div id="wrapss">
	
	<!-- 内容 -->
	<div id="MListConWrap">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	
			<!-- 右部 -->
			<td valign="top">
				<div id="leftList_right">
			<div id="listConTop"  >
				<h2><span class="titlespan"><img src="images/icon_7.gif" align="absmiddle" />&nbsp;&nbsp;<a href="<%=baseURL %>/vcityResourcesAction.do?action=showResourceList3D&resType=${resource.resType }">返回</a></span>
				${resource.resName }</h2>
				<div id="listCon_right">
				<h1>基本参数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h1>
				<br />
				 ${resource.resDesc }<br />
 
				 <br /><br />
				</div>
				<div style=" margin-right:450px; padding-left:20px">
					<div style="text-align:center; width:330px; height:270px; background-color:#FFFFFF; border:8px solid #a3ccf6">
						<p><img src="<%=baseURL %>/${resource.fullImage }" onload="javascript:DrawImage(this,200,254)" /></p>
					</div>
				</div>
				<div class="clear"></div>
				<!-- 投票 -->
				<table width="643" align="center" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td  width="50%" height="80" style="line-height:30px;">
					
					产品投票：
					<div class="clear"></div>
					 <span id="dis">
			  			<a href="javascript:void(0)" onclick="addCommon('up')"><div class="btn_2">${resource.up }</div></a>
					  </span>
					   <span id="notdis1" style="display: none;">
							<a href="javascript:void(0)" onclick=""><div class="btn_2">${resource.up+1 }</div></a>
					  </span>
					  <span id="dis2">
							<a href="javascript:void(0)" onclick="addCommon('down')"><div class="btn_3">${resource.down }</div></a>
					  </span>
		
					  <span id="notdis2" style="display: none;">
							<a href="javascript:void(0)" onclick=""><div class="btn_3">${resource.down+1 }</div></a>
					  </span>
					</td>
					<td style="line-height:30px;">
					<!-- 分享-->
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
					</td>
				  </tr>
				</table>
				<br />
			<!-- 点评 -->
			<table width="669" align="left" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px  dashed #dadada;background:url('images/K_top.png') top ">
			  <tr>
				<td height="31"><strong style="font-size:14px">&nbsp;&nbsp;&nbsp;网友点评</strong></td>
				<td width="13%" align="right" style="color:#999999">共${total }条点评&nbsp;&nbsp;</td>
			  </tr>
			  <tr>
				<td colspan="2" style="background:url('images/K_bg.png')">
				<table width="95%" align="center" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px dashed #CCCCCC">
				<c:if test="${!empty commonList}">
				<c:forEach items="${commonList}" var="common" varStatus="stauts" >
<br />
					  <tr>
						<td width="30" align="center"><img src="images/PL_icon.jpg"/></td>
						<td width="40" align="center" ><span class="spanfont14">网友</span></td>
						<td>
						<span style="color:#3a699d; font-size:14px">
						<c:if test="${!empty common.userName}">
						${common.userName }
						</c:if>
						<c:if test="${empty common.userName}">
						${common.ip }
						</c:if>
						</span>
						<span class="spanfont14">在&nbsp;&nbsp;${common.createTime }&nbsp;&nbsp;发表了</span>
						</td>
					  </tr>
					  <tr>
						<td>&nbsp;</td>
						<td align="right"><strong>内容：</strong></td>
						<td style="padding:6px 0 10px 0; color:#666666 ">${common.common } </td>
					  </tr>
					
				</c:forEach>
				</c:if>
				</table>
				<br />
				<div style="padding-left:30px;">
				<!--翻页 -->
				<wbase:dispartPageDemo formId="vcityResourceForm" />
				<br />
				<!-- end 翻页 -->

				<html:textarea property="flag" styleId="comment" rows="5" cols="50" name="vcityResourceForm"></html:textarea><br />
			 <input name="test" type="button" value="发表评论" onclick="saveCom()" /> 
			 	</div>
				</td>
			  </tr>
			</table>
			
			<!-- end -->
		  </div>
		  <div id="listConBottom"></div>
		</div>
			</td>
		  </tr>
		</table>

		
	</div>
</div>
</html:form>
</body>
		
</html>
<script type="text/javascript"><%--
window.onload = function() {
document.getElementById("listConTop").style.height=document.getElementById("leftListTop").scrollHeight+"px";
}
--%></script>