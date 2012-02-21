<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择调查问卷</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body>
<html:form action="/vcitySurveyAction.do?action=showVcitySureyList" styleId="vcitySurveyForm" >
<input type="hidden" name="flag" value="1" />
<input type="hidden" name="manual" value="${manual }" />
<table width="100%" height="100%" align="right" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #c6d2e3">
            <tr>
              <td height="30" valign="bottom" bgcolor="#f1f7fb">
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10" class="tab_kp_off">&nbsp;</td>
                    <td width="100" id="td1" height="26" class="tab_kp_on"  <c:if test="${manual==0}">onclick="changeToLocal();"</c:if>>本地问卷</td>
                  <c:if test="${manual==0}"> <td width="100" id="td2" align="center"   class="tab_kp_off" onClick="changeToE();">e声网问卷</td></c:if>
                    <td class="tab_kp_off">&nbsp;</td>
                  </tr>
              </table>
			  </td>
            </tr>
          
    <tr id="tr1">
              <td valign="top">
			  <table id="table1" width="98%" align="center" border="0" cellspacing="0" cellpadding="0" class="czwrap">
                <tr>
                  <td width="100" height="40" class="tdr"><strong>调查名称: </strong></td>
                  <td><strong>
                    <input type="text" name="textfield" style="width:100%" class="blueborderText">
                  </strong></td>
                  <td width="160">&nbsp;
                    <input name="Submit23222" type="button" class="btn_BS" value=" 查询 " />
                 </td>
                </tr>
              </table>
			  <table id="table2"  width="98%" align="center" border="1" cellspacing="0" cellpadding="0" bordercolor="#A6CCEB" style="border-collapse:collapse; margin:8px 0px;">
      <tr>
        <td class="table_thead tdl">调查列表</td>
      </tr>
      <tr>
        <td><table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#D0D0D0" class="tablelist">
          <thead>
            <tr>
              <td width="5%">&nbsp;</td>
              <td class="tdl_B">调查名称</td>
              </tr>
          </thead>
           <c:if test="${!empty datas}">
			<c:forEach items="${datas}" var="survey" varStatus="stauts">
          <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
            <td class="tdc">
            
            <input <c:if test="${manual==1}">type="checkbox"</c:if><c:if test="${manual==0}">type="radio"</c:if> name="radiobutton" value="${survey.sureyId}!@${survey.sureyName}"></td>
            <td class="tdl_B">${survey.sureyName}</td>
            </tr>
          </c:forEach>
          </c:if>
        </table></td>
      </tr>
    </table>
	          <table id="table3" width="98%" align="center" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>&nbsp;</td>
                  <td class="tdr"><wbase:dispartPage formId="vcitySurveyForm" /></td>
                </tr>
                <tr><td colspan="2" align="center"> <div align="center" style=" padding:8px 0px">
		    <input name="Submit2322" type="button" class="btn_BS" onClick="getSurvey();" value=" 选择 " />
&nbsp;&nbsp;
<input name="Submit2332" type="button" class="btn_BS" value=" 取消 " onClick="parent.ClosePop();" />
		  </div></td></tr>
              </table>
	         <%--<div align="center" style=" padding:8px 0px">
		    <input name="Submit2322" type="button" class="btn_BS" onClick="getSurvey();" value=" 选择 " />
&nbsp;&nbsp;
<input name="Submit2332" type="button" class="btn_BS" value=" 取消 " onClick="parent.parent.colseDiv2();" />
		  </div>
              --%>
              </td>
   </tr>
            <tr style="display: none"  id="tr4" ><td valign="top" style="padding-top:5px;" >
            <table id="table4" width="97%" align="center" border="0"   cellspacing="0" cellpadding="0"  >
				<tr><td>
            	<iframe id="iframe" width="100%" height="450px;"  ></iframe>
            	</td></tr>
            	 </table>
             </td></tr>
          </table>
</html:form>
<script>
var isManual=${manual};
if(isManual==1){
	var revalues = parent.document.frames["mainFrame"].document.getElementsByName("revalue");
	var num = document.getElementsByName("radiobutton");
	for(var i=0;i<revalues.length;i++){
		var value = revalues[i].value;
		var num = document.getElementsByName("radiobutton");
		for(var j=0;j<num.length;j++){
			if(num[j].value==value){
				num[j].disabled = true;
			}
		}
	}
}


function OM_table(obj){
	obj.className='table_on';
}
function OO_table(obj){
	obj.className='table_off';
}
function getSurvey(){
	var num = document.getElementsByName("radiobutton");
	var str = "";
	for(var i=0;i<num.length;i++){
		if(num[i].checked){
			str  +=   num[i].value+"###";
		}
	}
	str = str.substring(0,str.length-3);
	var flag = ${flag}; 
	var s = true;
	var manual = ${manual};
	if(manual==0){
		s = parent.localSurvery(flag,str);
	}
	if(manual==1){
		s = parent.manualSurvey(flag,str);
	}
	if(s){
		parent.ClosePop();
	}
}
function changeToE(){
	document.getElementById("td1").className = "tab_kp_off";
	document.getElementById("td2").className = "tab_kp_on";
	document.getElementById("tr1").style.display = 'none';
	document.getElementById("tr4").style.display = '';
	document.getElementById("iframe").src = "shuaxuan.jsp?type=type";
	
}
function changeToLocal(){
	document.getElementById("td2").className = "tab_kp_off";
	document.getElementById("td1").className = "tab_kp_on";
	document.getElementById("tr4").style.display = 'none';
	document.getElementById("tr1").style.display = '';
}
function survery(name,id){
	parent.survery(name,id);
}
</script>
</body>
</html>
