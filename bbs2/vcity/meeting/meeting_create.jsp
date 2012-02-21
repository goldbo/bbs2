<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>

	<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
	<script type="text/javascript">var base = '<%=baseURL %>';</script>
<script charset="utf-8" src="<%=baseURL %>/vcity/inc/js/kindeditor.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/calendar/WdatePicker.js"></script>
	<script type="text/javascript">
		
			KE.show({
				id : 'desc',
				resizeMode : 0,
				allowPreviewEmoticons : false,
				allowUpload : false,
				items : [
				'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				'|', 'emoticons', 'link']
			}); 
		</script>
    <script type="text/javascript">
       function beforedate(btime,atime){
			btime =btime.replace(/-/g,"/");
			var d1=new Date(btime);
			atime =atime.replace(/-/g,"/");
			var d2=new Date(atime);
			if (d1.getTime()<=d2.getTime())
			{
				return true;
			}
			else
			{
				return false;
			}
	  } 
	  function tosubmit(){
	  	if(subfrm()){
	  		frm.submit();
	  	}
	  }
       function subfrm()
       {
       var frm = document.getElementById("frm");
         if(frm.appName.value=="")
         {
             alert("请输入主题");
             return false;
         }
         if(frm.startTime.value=="")
         {
              alert("请选择开始时间");
              return false;
         }
         if(frm.endTime.value=="")
         {
             alert("请选择结束时间");
             return false;
         }
          if(!beforedate(frm.startTime.value,frm.endTime.value))
	       {
	            alert("结束时间必须大于开始时间");
	            return false;
	       }
         if(frm.zhuchi_user.value=="")
         {
             alert("请选择主持人");
             return false;
         }
         if(frm.modeType[2].checked==false)
         {
	         if(frm.canyu_user==null)
	         {
	             alert("请选择参与人员");
	             return false;
	         }
         }
         KE.sync('desc');
         var abc = document.getElementById('desc').value;
         if(abc==""){
         	alert("请输入简单的会议介绍");
		   return false
         }
         abc= abc.replace(/<[^>].*?>/g,"");//去除标签
		 abc = abc.replace(/&nbsp;/ig,"");//去除&nbsp;
		 abc = abc.replace(/\s+/g,"");//除去空格
         if(abc=="")
         {
		    alert("请输入简单的会议介绍");
		   return false
         } 
         return true
       }
       //e声网数据
       function acceptPara(result)
       {
            //$("canyu_user").value=result;
            var arraynum=result.split(",");
            for(var i=0;i<arraynum.length;i++){
			   if($(arraynum[i])==null)
			   $("num").innerHTML=$("num").innerHTML+"<div style='float:left; width:160px; height:30px; line-height:30px;'><input type='checkbox' name='canyu_user' checked id='"+arraynum[i]+"' value='"+arraynum[i]+"'/>"+arraynum[i]+"</div>";
			}
		  //  $("tipDivc").style.display="none";
          //  $("KuanghiddenDiv").style.display="none";
          parent.ClosePop();
       }
       
  /*	var ab ;
       bkLib.onDomLoaded(function() {
			ab = new nicEditor({fullPanel : true}).panelInstance('desc');
		}); */
   </script>

<%--<body style="overflow:hidden">
	--%><form method="post" action="index_meeting.jsp" name="frm" id="frm">
  <input  type="hidden" name="action" value="addAppointment"/>
  <input name="radomID" id="radomID1" type="hidden" value="<%=Math.random() %>"/>
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
          <td width="32%" height="30" class="tdr"><p><strong>会议主题：</strong></p></td>
          <td width="68%"> <input type="text" name="appName" id="appName" style="width:60%" class="blueborderText">
          <span class="redFont12">*</span></td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>会议模式:</strong></p></td>
          	<td>
		       <input type="radio" value="0" name="modeType" onclick="setCanyu(0,this.checked)">内部会议(<font color="red">直接参与会议,不需报名和审核</font>)<br />
		       <input type="radio" value="1" name="modeType" checked="checked" onclick="setCanyu(1,this.checked)">指定会议(<font color="red">参与者需要通过报名和审核</font>)<br />
		       <input type="radio" value="2" name="modeType" onclick="setCanyu(2,this.checked)">公开会议(<font color="red">无需选择参与人员,参与者需要通过报名和审核</font>)
	    </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>会议开始时间：</strong></p></td>
          <td><input type="text" style="width:60%"  readonly="readonly" name="startTime" id="startTime" onclick="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true,'whyGreen')">
      	</td>
        </tr>
         <tr>
          <td height="30" class="tdr"><p><strong>会议结束时间：</strong></p></td>
          <td><input type="text" style="width:60%" readonly="readonly" name="endTime" id="endTime" onclick="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true,'whyGreen')">
      	</td>
        </tr>
        <tr id="scbtn">
          <td height="30" class="tdr"><p><strong>主持人：</strong></p></td>
          <td><input type="text" name="zhuchi_user" style="width:60%;" readonly="readonly" value="${user.username}"/></td>
        </tr>
      <tr>
          <td height="30" class="tdr"><p><strong>参与人：</strong></p></td>
          <td><div>
          <input type="button" name="Submit3" onClick="parent.ShowIframe('选择人员','<%=baseURL %>/shuaxuan.jsp',700,510);" class="btn_BS"  value="会员筛选">
	        	<%--<input type="button" name="Submit" value="选择参与人员"  class="btn_BS"  onClick="openDiv_c('选择参与人员','shuaxuan.jsp',700,500);" />
	         --%></div>
	         <div id="num" style="overflow: auto; width:60%; height:120px; background-color:#F9F9F9; border:1px solid #666666;">
	         </div>
	       </td>
        </tr>
        <tr>
          <td height="30" class="tdr"><p><strong>会议图片：</strong></p></td>
          <td><nobr><input type="text" name="filepic" style="width:60%;" readonly="readonly"/></nobr>
	       <input type="button" style="width:10%;"  class="btn_BS" value="上传图片" onclick="openDiv_b('上传图片','index_meeting.jsp?action=toUploadFile',500,138);">
	       </td>
         </tr>
         <br>
        <tr>
          <td  height="30" class="tdr"><p><strong>会议介绍：</strong></p></td>
          <td><textarea name="desc" id="desc" style="width:60%; height:200px;" class="blueborderText"></textarea></td>
        </tr>
		<tr>
		<td colspan="2" valign="bottom" class="tdc">
						<input type="button" name="Submit2" value=" 提交 " onclick="tosubmit();"  class="btn_BS"/>
	  	  &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn_BS" value="返回" onClick="javaScript:location.href='index_meeting.jsp?action=pageAppointmentByUserName';"/>&nbsp;&nbsp;${msg } 
&nbsp;	</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</form>
	
<!-- 弹出框 -->

<div style="display:none;position: absolute; width: 100px; height: 100px; z-index: 100000; left: 187px; top: 113px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDivc">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td class="tdl" id="tipTitlec" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><img alt="close"  style=" cursor:pointer;" src="images/conference/btn_window_c.gif" onClick="document.getElementById('tipDivc').style.display='none';document.getElementById('KuanghiddenDiv').style.display='none';"></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="460" name="actionFramec" src=""></iframe></td></tr>
</table>
</div> 
<div style="display:none;position: absolute; width: 100px; height: 100px; z-index: 100000; left: 187px; top: 113px;padding:2px;background-color:#f1f6fa; border:1px  solid #badff9;" id="tipDivb">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr height="23px"><td class="tdl" id="tipTitleb" style="font-weight:bold; font-size:14px;color:#1076be;padding-left:5px">&nbsp;</td><td style="text-align:right;padding-right:5px"><img alt="close"  style=" cursor:pointer;" src="images/conference/btn_window_c.gif" onClick="document.getElementById('tipDivb').style.display='none';document.getElementById('KuanghiddenDiv').style.display='none';"></td></tr>
<tr><td colspan="2" style="padding:4px"><iframe scrolling="auto" frameborder="0" width="100%" height="100" name="actionFrameb" src=""></iframe></td></tr>
</table>
</div>
<div id="KuanghiddenDiv" name="KuanghiddenDiv" style="display:none;position: absolute; z-index: 12;background-color:#8eceff;FILTER: alpha(opacity=60)"></div>

<!-- end 弹出框 -->
<script type="text/javascript">

function openDiv_c(actionTitle,actionUrl,actionWidth,actionHeight)
{
	var left = document.body.clientWidth/2;
	var top = document.body.clientHeight/2;
	document.getElementById('tipDivc').style.top = top - actionHeight/2;
	document.getElementById('tipDivc').style.left = left - actionWidth/2;
	document.getElementById('tipDivc').style.width = actionWidth;
	document.getElementById('tipDivc').style.height = actionHeight;
	document.getElementById("tipDivc").style.display = "";
	document.getElementById('KuanghiddenDiv').style.top = top - actionHeight/2 - 6;
	document.getElementById('KuanghiddenDiv').style.left = left - actionWidth/2 - 6;
	document.getElementById("KuanghiddenDiv").style.display ="";
	document.getElementById("KuanghiddenDiv").style.width= actionWidth+17;
	document.getElementById("KuanghiddenDiv").style.height= actionHeight+17;
	document.getElementById("tipTitlec").innerHTML = actionTitle;
	actionFramec.location.href = actionUrl;
}

function openDiv_b(actionTitle,actionUrl,actionWidth,actionHeight)
{
	var left = document.body.clientWidth/2;
	var top = document.body.clientHeight/2;
	document.getElementById('tipDivb').style.top = top - actionHeight/2;
	document.getElementById('tipDivb').style.left = left - actionWidth/2;
	document.getElementById('tipDivb').style.width = actionWidth;
    document.getElementById('tipDivb').style.height = actionHeight;
	document.getElementById("tipDivb").style.display = "";
	document.getElementById('KuanghiddenDiv').style.top = top - actionHeight/2-6;
	document.getElementById('KuanghiddenDiv').style.left = left - actionWidth/2 - 6;
	document.getElementById("KuanghiddenDiv").style.display ="";
	document.getElementById("KuanghiddenDiv").style.width= actionWidth+17;
	document.getElementById("KuanghiddenDiv").style.height= actionHeight+17;
	document.getElementById("tipTitleb").innerHTML = actionTitle;
	actionFrameb.location.href = actionUrl;
}

//如果选择的是公开会议模式，则参与人员的文本框和按钮变为不可用
function setCanyu(type,flg)
{
  if(type==2)
  {
    //frm.canyu_user.disabled = "true";
    frm.Submit3.disabled = "true";
  }
  else
  {
    // frm.canyu_user.disabled = "";
     frm.Submit3.disabled = "";
  }
}
</script>
