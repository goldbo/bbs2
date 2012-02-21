<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html:html lang="true">
<head>

	<title>新增体验资源</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="description" content="cate edit">

	<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
	<!-- ke js -->
<script type="text/javascript">var base = '<%=baseURL %>';</script>
	<script src="<%=baseURL%>/common/ckeditor/ckeditor.js" type="text/javascript"></script>
	<script src="<%=baseURL%>/common/js/DateTimePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script charset="utf-8" src="<%=baseURL %>/vcity/inc/js/kindeditor.js"></script>
	<script type="text/javascript">
		
			KE.show({
				id : 'resDesc',
				resizeMode : 0,
				allowPreviewEmoticons : false,
				allowUpload : false,
				items : [
				'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				'|', 'emoticons', 'link']
			}); 
		</script>
<script type="text/javascript">
function FileChange(path){
	if( !path.match( /.unity3d/i ) ){
        alert('资源格式无效！');
        document.getElementById("formFile").value = "";
        return false;   
    }else{
		document.all.uploadimage.width=10;
		document.all.uploadimage.height=10;
		document.all.uploadimage.alt="";
		document.all.uploadimage.src=path;
	}
}


function IsURL(){

 var str_url = document.getElementById("resLink").value;
  var strRegex = "^((https|http|ftp|rtsp|mms)?://)" 
  + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@ 
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184 
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www. 
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名 
        + "[a-z]{2,6})" // first level domain- .com or .museum 
        + "(:[0-9]{1,4})?" // 端口- :80 
        + "((/?)|" // a slash isn't required if there is no file name 
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
        var re=new RegExp(strRegex); 
  //re.test()
        if (re.test(str_url)){
         //  alert("yes"); 
        }else{ 
           alert("请输入合法的链接地址！");
           document.getElementById("resLink").value = "";
        }
    } 
</script>
</head>

<body style="overflow:hidden">
	<html:form action="/vcityResourceAction.do?action=saveVcityResource" styleId="vcityResourceForm" enctype="multipart/form-data" >
		<table width="100%" height="100%" align="center" border="0"
			cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
			<tr>
				<td height="30" valign="bottom" bgcolor="#f1f7fb">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20" class="tab_kp_off">&nbsp;
								
							</td>
							<td width="100" height="26" class="tab_kp_on">
								新增信息
							</td>
							<td class="tab_kp_off">&nbsp;
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="95%" height="97%" border="0" align="center" cellpadding="0"
						cellspacing="1" class="tableWrap">
						<tr>
							<td height="25" class="tdr_B">&nbsp;							</td>
							<td class="greenFont12 tdr_B">&nbsp;							</td>
						</tr>
						<tr>
          <td width="32%" height="30" class="tdr"><p><strong>资源名称：</strong></p></td>
          <td width="68%"> <input type="text" name="resName" id="resName" onChange="ajaxCheckResName();" style=" width:60%" class="blueborderText">
          <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>资源图片：</strong></p></td>
          <td><input type="file" id="imageFile" name="imageFile" style=" width:60%" class="blueborderText" onChange="FileChange(this.value);"></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>资源类型：</strong></p></td>
          <td><input name="resType" type="radio" id="resType1" value="1" checked="checked" >
         应用
            <input type="radio" name="resType" id="resType2" value="2" >
         皮肤 <span class="redFont12">*</span></td>
        </tr>
       
		<tr id="dybtn" style="display:none">
          <td height="30"  align="center" colspan="2"><p><span class="redFont12">正在上传资源,请稍候...</span></p></td>
        </tr>
        <tr id="scbtn">
          <td height="30" class="tdr"><p><strong>资源上传：</strong></p></td>
          <td><input type="file" id="modelFile" name="modelFile" style=" width:60%" class="blueborderText"><%-- onChange="check3Dfile(this.value);">--%>
          <span class="redFont12">*</span></td>
        </tr>
       <%-- <tr>
          <td height="30" class="tdr"><strong>产品分类：</strong></td>
          <td><select name="productType" id="productType">
            <option value="">请选择</option>
            <logic:iterate id="type" name="typeList" scope="request" indexId="index">
	            <option value="${type.id }">
						${type.resType }				</option>
            </logic:iterate>
          </select>
          <span class="redFont12">*</span> </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><strong>产品展示场景：</strong></td>
          <td><input type="text"  name="resScene"  style="width:60%" id="tasksub" class="blueborderText" />
					<input type="hidden" id="tasksubid" name="tasksubid"/>
					<input type="button" id="btn1" onClick="openDiv('选择场景','<%=baseURL %>/scene.do?option=doGetTree',300,430);" class="btn_BS" value="选择场景"/>
          <span class="redFont12">*</span></td>
        </tr>
        --%><tr>
          <td height="30" class="tdr"><p><strong>资源信息描述：</strong></p></td>
          <td><textarea name="resDesc" id="resDesc" style="width:60%; height:200px;" class="blueborderText"></textarea>
            <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>是否推荐：</strong></p></td>
          <td><input name="radiobutton" type="radio" value="radiobutton" onClick="showHidden(1);" >
          是
            <input name="radiobutton" type="radio" value="0" name="isRecommend" onClick="showHidden(2);" checked >
          否&nbsp;&nbsp;&nbsp;
		  <span id="tjdj" style=" display:none">推荐等级：<input type="radio" name="isRecommend" value="5" id="ra5" >5&nbsp;<input type="radio" name="isRecommend" value="4">4&nbsp;<input type="radio" name="isRecommend" value="3">3&nbsp;<input type="radio" name="isRecommend" value="2">2&nbsp;<input type="radio" name="isRecommend" value="1">1</span>		  </td>
        </tr>
        <tr id="recomonFile" style="display:none">
          <td height="30" class="tdr"><p><strong>上传推荐图片：</strong><br>(建议像素530x340内)</p></td>
          <td><input type="file" id="recommendImageFile" name="recommendImageFile" style=" width:60%"  class="blueborderText" onChange="FileChange2(this.value);"></td>
        </tr>
		<tr>
							<td colspan="2" valign="bottom" class="tdc">
								
									    <%--<span id="add" style="display:none"><input type="button" onClick="saveVcityRes('<%=baseURL %>/vcityResourceAction.do?action=saveVcityResource&todo=addresourceuse');" class="btn_BS" value="添加应用">&nbsp;&nbsp;&nbsp;&nbsp;
									    </span>
											--%>
											<input type="button" class="btn_BS" value="完成" 
												onclick="saveVcityRes();">&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" class="btn_BS" value="取  消"
												onclick="baskToList();">	<br>
&nbsp;						</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</html:form>
	
<!-- 弹出框 -->
<div style="display:none;position: absolute; width: 300px; height: 430px; z-index: 100000; right: 287px; top: 13px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td  id="tipTitle" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><span><a onClick="colseDiv()">关闭</a></span></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="100%" name="actionFrame" src="<%=baseURL %>/vcityResourceAction.do?action=editVcityResource&todo=create"></iframe></td></tr>
</table>
</div>
<div id="KuanghiddenDiv" style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>

<!-- end 弹出框 -->	

<!-- 弹出框资源应用树 -->
<div style="display:none;position: absolute; width: 700px; height: 530px; z-index: 100000; right: 87px; top: 53px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDiv2">
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border-bottom:0px">
<tr height="23px"><td  id="tipTitle2" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><span><a onClick="document.getElementById('tipDiv2').style.display='none';document.getElementById('KuanghiddenDiv2').style.display='none'">关闭</a></span></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="463" name="actionFrame2" src=""></iframe></td></tr>
</table>
</div>
<div id="KuanghiddenDiv2"  style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>

<!-- end 弹出框2 -->
<script type="text/javascript">
function saveVcityRes(){
	if(checkForm()){
		document.getElementById("dybtn").style.display='';	
		vcityResourceForm.action='<%=baseURL %>/vcityResourceAction.do?action=saveVcityResource&todo=1';
		vcityResourceForm.submit();
		
	}else{
		return false;
	}
}

function checkForm(){
KE.sync('resDesc');
	var checknull = document.getElementById("resName").value;
	//var productType = document.getElementById("productType").value;
	var imageFile = document.getElementById("imageFile").value;
	var modelFile = document.getElementById("modelFile").value;
	var resDesc = document.getElementById("resDesc").value;
	var  resType = document.getElementsByName("resType");
	var temp = 0;
	for(var i=0;i<resType.length;i++){
		if(resType[i].checked){
			temp = resType[i].value;
		}
	}
	if(checknull==null||checknull==""){
	alert("资源名称不能为空！");
		return false;
	}
	/*if(productType==null||productType==""){
		alert("请选择分类！");
		return false;
	}*/
	if(imageFile==null||imageFile==""){
		alert("请上传图片！");
		return false;
	}
	if(temp==1){
		if(modelFile==null||modelFile==""){
			alert("请上传应用！");
			return false;
		}
	} else {
		if(modelFile==null||modelFile==""){
			alert("请上传皮肤！");
			return false;
		}
	}
	if(resDesc==null||resDesc==""){
		alert("输入资源描述！");
		return false;
	}	
	return true;	
}
 
 function baskToList(){
	window.history.back(1);
 }
 
var XMLHttpReq;
//创建XMLHTTP请求对象
function createXMLHttpRequest(){
	if(window.XMLHttpRequest){
		XMLHttpReq=new XMLHttpRequest();
	} else if(window.ActiveXObject){
		try{
			XMLHttpReq=new ActiveXObject("Msxml2.XMLHTTP");
			
		}catch(e){
			try{
				XMLHttpReq=new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){}
		}
	}
}

//发出异步请求,检查资源名称是否已被占用
function ajaxCheckResName(){

	var url = "<%=baseURL%>/vcityResourceAction.do?action=checkResNameExists";
	createXMLHttpRequest();
	XMLHttpReq.onreadystatechange=processResponse;
	XMLHttpReq.open("POST", url, true);
	XMLHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var strParams;	
	var resName = document.getElementById("resName").value;    //资源名称
	
	strParams ="resName="+resName;
			
	XMLHttpReq.send(strParams);	
	 
}
	
//加载完成后处理响应
function processResponse(){
	
	if(XMLHttpReq.readyState==4){
		if(XMLHttpReq.status==200){
			process();
		} else {
			window.alert("请求指定页面时出错");
		}
	}
}
	
function process(){
	var Datas = XMLHttpReq.responseXML.getElementsByTagName("Data");
	
	var Data_value = Datas[0].getElementsByTagName("value")[0].firstChild.data;
	if(Data_value==1){
		alert("该资源名称已被使用！");
		document.vcityResourceForm.resName.value= "";
		return false;
	} 
	
}

function openDiv(actionTitle,actionUrl,actionWidth,actionHeight)
{
	var left = document.body.clientWidth/2;
	var top = document.body.clientHeight/2;
	document.getElementById('tipDiv').style.top = top -  parseInt(actionHeight/2);
	document.getElementById('tipDiv').style.left = left -  parseInt(actionWidth/2);
	document.getElementById('tipDiv').style.width = parseInt(actionWidth);
	document.getElementById('tipDiv').style.height = parseInt(actionHeight);
	document.getElementById("tipDiv").style.display = "";
	document.getElementById('KuanghiddenDiv').style.top = top -  parseInt(actionHeight/2) - 6;
	document.getElementById('KuanghiddenDiv').style.left = left -  parseInt(actionWidth/2) - 6;
	document.getElementById("KuanghiddenDiv").style.display ="";
	document.getElementById("KuanghiddenDiv").style.width= parseInt(actionWidth) + 17;
	document.getElementById("KuanghiddenDiv").style.height= parseInt(actionHeight)+17;
	document.getElementById("tipTitle").innerHTML = actionTitle;
	actionFrame.location.href = actionUrl;
}
function openDiv2(actionTitle,actionUrl,actionWidth,actionHeight)
{
	var left = document.body.clientWidth/2;
	var top = document.body.clientHeight/2;
	document.getElementById('tipDiv2').style.top = top -  parseInt(actionHeight/2);
	document.getElementById('tipDiv2').style.left = left -  parseInt(actionWidth/2);
	document.getElementById('tipDiv2').style.width = parseInt(actionWidth);
	document.getElementById('tipDiv2').style.height = parseInt(actionHeight);
	document.getElementById("tipDiv2").style.display = "";
	document.getElementById('KuanghiddenDiv2').style.top = top -  parseInt(actionHeight/2) - 6;
	document.getElementById('KuanghiddenDiv2').style.left = left -  parseInt(actionWidth/2) - 6;
	document.getElementById("KuanghiddenDiv2").style.display ="";
	document.getElementById("KuanghiddenDiv2").style.width= parseInt(actionWidth) + 17;
	document.getElementById("KuanghiddenDiv2").style.height= parseInt(actionHeight)+17;
	document.getElementById("tipTitle2").innerHTML = actionTitle;
	vcityResourceForm.action=actionUrl;
	vcityResourceForm.submit();
//	actionFrame2.location.href = actionUrl;
}	

function colseDiv(){
	document.getElementById('tipDiv').style.display='none';
	document.getElementById('KuanghiddenDiv').style.display='none'
}

 function showHidden(num){
 	if(num==1){	
 		document.getElementById('tjdj').style.display='';
 		document.getElementById('ra5').checked = "checked";
 		document.getElementById('recomonFile').style.display='block';
 	} 
 	if(num==2){
 		document.getElementById('tjdj').style.display='none';
 		document.getElementById('ra5').checked = false;
 		document.getElementById('recomonFile').style.display='none';
 	}	
 }
 
 function showTypeHidden(num){
 	if(num==1){
	 	document.getElementById('dybtn').style.display='none';
	 	document.getElementById('scbtn').style.display='';
	 	document.getElementById('add').style.display='none';
	 }
	 if(num==2){
	 	document.getElementById('dybtn').style.display='';
	 	document.getElementById('scbtn').style.display='none';
	 	document.getElementById('add').style.display='';
	 }
 }
 
 function adduse(){
 	saveVcityRes('addresourceuse');
 }
 
 function FileChange(path){
	if( !path.match( /.jpg|.gif|.png|.bmp/i ) ){
	        alert('图片格式无效！');
	        document.getElementById("imageFile").value = "";
	        return false;
	 }
}
 function FileChange2(path){
	if( !path.match( /.jpg|.gif|.png|.bmp/i ) ){
	        alert('图片格式无效！');
	        document.getElementById("recommendImageFile").value = "";
	        return false;
	 }
}
 function check3Dfile(path){
	if( !path.match( /.unity3d|.apk/i ) ){
	        alert('3D文件格式无效！');
	        document.getElementById("modelFile").value = "";
	        return false;
	 }
}
 
</script>

</body>
</html:html>
