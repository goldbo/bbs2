<%@ page language="java" pageEncoding="utf-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>虚拟资源应用树</title>
<link rel="stylesheet" href="<%=baseURL %>/vcity/include/css/stree.css" type="text/css">
<script language="JavaScript" src="include/js/dtree.js"></script>
<link href="images/admincp/admincp.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body style="background-color:#FFFFFF">
<html:form action="/vcityResourceAppAction.do?action=doGetResourceUseTree"  styleId="vcityResourceAppForm">
<input type="hidden" id="parenId" name="parenId" value="${vcityResAppForm.parenId}">
<input type="hidden" id="parenDesc" name="parenDesc" value="${vcityResAppForm.appDesc}">
<input type="hidden" id="resId" name="resId" value="${vcityResAppForm.resId }">
<input type="hidden" id="id" name="id" value="${vcityResAppForm.parenId}">
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">


	  <table width="100%" height="95%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	
	<table width="99%" height="100%" align="right" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="9" class="tab_kp_off">&nbsp;</td>
                    <td width="93" height="26" class="tab_kp_on">手机应用</td>
                    <td width="93" align="center"  class="tab_kp_off">&nbsp;</td>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>			  </td>
            </tr>
            <tr>
              <td valign="top">
			  <table width="96%" height="98%" border="0" align="center" cellpadding="0" cellspacing="1" style="margin:8px 0px; border:1px solid #c6d2e3; background:url(<%=baseURL %>/vcity/images/bg.gif) top repeat-x">
                
                  <tr>
                   <td valign="top" style="padding:20px 10px">

						<script language="javascript">
							var d = new dTree('d');
							${scene};
							document.write(d);
				</script>
					
					</td>
                  </tr>
                </table>
				</td>
            </tr>
          </table>
	
	</td>
    <td width="50%" valign="top">
    <%--<iframe id="baseapplist" src="<%=baseURL %>/vcityResoprateAction.do?action=showVcityResoprateList&flag=1" ></iframe>
    --%><table width="96%" border="0" id="addx" align="center" cellpadding="0" cellspacing="1" style=" border:1px solid #c6d2e3; background:url(images/bg.gif) top repeat-x;">
      <tr>
        <td colspan="2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td height="35" class="tdl">&nbsp;<strong class="greenFont12" id="name"></strong></td>
				<td id="addxj" style="display: none;" width="35%" ><input type="button" value="删除该应用" onClick="deleteResApp();"/></td>
			  </tr>
			</table>
		</td>
      </tr>
      <tr>
        <td valign="top" colspan="2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">

		<%--<tr><td width="5%" align="center" class="listheadbg">
				<input type="CheckBox" id="selectedRowAll" name="selectedRowAll" onClick="selectAllGrid(this,'selectedRow')">
			</td>

		--%><tr><td width="5%" align="center" class="listheadbg"></td>

			<td>NO</td>
			<td>操作名称</td>
		</tr>
           <c:if test="${!empty ResoprateList}">
				<c:forEach items="${ResoprateList}" var="resoprate" varStatus="stauts">
			<tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
				<td align="center" height="25px">
						<%--<input type="CheckBox" id="selectedRow" name="selectedRow" value="${resoprate.resoprateName };--%>
						<input type="hidden" id="opdesc${resoprate.id }" value="${resoprate.resoprateName }" />
						<input type="hidden" id="opcode${resoprate.id }" value="${resoprate.resoCode }" />
				</td>
				<td>${stauts.index+1 }</td>
				<td  align="center">${resoprate.resoprateName }</td>
				<td>	<input type="button" value="添加" onClick="addResApp(${resoprate.id })" /></td>
			</tr>
			</c:forEach>
			</c:if>
        </table></td>
      </tr>
      <tr>
	  <td colspan="8">
		<wbase:dispartPage formId="vcityResourceAppForm" />
	  </td>
	</tr>
    </table>
    </td>
      </tr>

      <tr><td></td><td></td> </tr>
    </table>

</td></tr></table>
</html:form>
<script>




	var parenid = document.getElementById("parenId").value;
	var parenDesc = document.getElementById("parenDesc").value;
	document.getElementById("addxj").style.display= "none";
	document.getElementById("name").innerHTML= "添加 "+parenDesc + " 子应用";
	
function getHostList(parenid,id,resid,desc)
{  	
//	document.getElementById("baseapplist").src = "<%=baseURL %>/vcityResoprateAction.do?action=showVcityResoprateList&flag=1";
//	document.getElementById('baseapplist').contentWindow.document.getElementById("name").innerHTML= "添加 "+desc + " 子应用";
	if(parenid!=0){document.getElementById("addxj").style.display= "";}
	if(parenid==0){document.getElementById("addxj").style.display= "none";}
	document.getElementById("name").innerHTML= "添加 "+desc + " 子应用";
	document.getElementById("parenId").value = id;
	document.getElementById("id").value = id;
	document.getElementById("parenDesc").value = desc;
  document.getElementById("resId").value = resid;


}
/*function addResApp(){
  var checkboxArr=document.getElementsByName("selectedRow");
    var num=0;
    for(var i=0;i<checkboxArr.length;i++)
    {
       if(checkboxArr[i].checked)
       {
           num=num+1;
       }
    }
	if(num>0){	
		if(checkAdd()){
			vcityResourceAppForm.action="<%=baseURL%>/vcityResourceAppAction.do?action=addResApp";
			vcityResourceAppForm.submit();
	 	}
	 }
} */
function addResApp(num){
	var subdesc = escape(encodeURIComponent(document.getElementById("opdesc"+num).value));
	var subcode = document.getElementById("opcode"+num).value; 
	if(checkAdd()){
			vcityResourceAppForm.action="<%=baseURL%>/vcityResourceAppAction.do?action=addResApp&appDesc="+subdesc+"&appCode="+subcode;
			vcityResourceAppForm.submit();
	 	}
	
}
function deleteResApp(){
	vcityResourceAppForm.action="<%=baseURL%>/vcityResourceAppAction.do?action=deleteResApp";
	vcityResourceAppForm.submit();
}

function checkAdd(){
	var pareId = document.getElementById("parenId").value;
	if(pareId==""){
		alert("请选择父节点");
		return false;
	}
	return true;
}

</script>
</body>
</html>
