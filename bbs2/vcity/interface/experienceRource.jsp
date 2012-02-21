<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<title>选择体验产品</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>
<script>
function OM_table(obj){
	obj.className='table_on';
}
function OO_table(obj){
	obj.className='table_off';
}

function getResource(){
	var num = document.getElementsByName("radiobutton");
	for(var i=0;i<num.length;i++){
		if(num[i].checked){
			var str  =   num[i].value;	
		}	
	}
	//window.returnValue = str;
	//window.close();  
	var flag = ${flag};
	var manual = ${manual};
	var s =true;;
	if(manual==1){
		s  = parent.chooseModel(str);
	}
	if(manual==0) {
		s = parent.experience(flag,str);
	}
	if(s){
		parent.ClosePop();
	}
}
  
</script>
<body>
<html:form action="/vcityResourceAction.do?action=showVcityResourceList" styleId="vcityResourceForm">
<input type="hidden" name="flag" value="${flag }"/>
<input type="hidden" name="manual" value="${manual }"/>
<table width="100%" height="100%" align="right" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100" height="26" class="tab_kp_on">产品体验列表</td>
                    <td width="100" align="center"  class="tab_kp_off">&nbsp;</td>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>
			  </td>
            </tr>
            <tr>
              <td valign="top"><br>
              <table width="98%" align="center" border="0" cellspacing="0" cellpadding="0" class="czwrap">
                <tr>
                  <td width="100" class="tdr"><strong>产品体验名称: </strong></td>
                    <td><strong>
                      <input type="text" name="resName" style="width:100%" class="blueborderText">
                      </strong></td>
                    <td width="160">&nbsp;
                      <input name="Submit23222" type="button" class="btn_BS" value=" 查询 " onClick="vcityResourceForm.submit();" />
                     </td>
                </tr>
                </table>
			    <table width="98%" align="center" border="1" cellspacing="0" cellpadding="0" bordercolor="#A6CCEB" style="border-collapse:collapse; margin:8px 0px;">
			      <tr>
			        <td class="table_thead tdl">产品体验</td>
        </tr>
			      <tr>
			        <td><table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#D0D0D0" class="tablelist">
			          <thead>
			            <tr>
			              <td width="5%">&nbsp;</td>
                <td class="tdl_B">角色名称</td>
                </tr>
		              </thead>
		           <c:if test="${!empty datas}">
						<c:forEach items="${datas}" var="resource"	varStatus="stauts">
				          <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
				            <td class="tdc"><input type="radio" name="radiobutton" value="${resource.id }!@${resource.resName }"></td>
				            <td class="tdl_B">${resource.resName }</td>
	              		</tr>
	              		</c:forEach>
	              		</c:if>
			        
			          </table></td>
        </tr>
		        </table>
	            <table width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td>&nbsp;</td>
	                <td><wbase:dispartPage formId="vcityResourceForm" /></td>
                  </tr>
                </table>
	            <div align="center" style=" padding:8px 0px">
	              <input name="Submit2322" type="button" onClick="getResource();" class="btn_BS" value=" 选择 " />
  &nbsp;&nbsp;
  <input name="Submit2332" type="button" class="btn_BS" value=" 取消 " onClick="parent.parent.colseDiv2();" />
                </div>
              </td></tr>
          </table>
   </html:form>
</body>
</html>
