<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>用户调查结果列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">

  </head>
    <script type="text/javascript">
function showUserResult(ids,subtaskid){
	 var username = parent.document.getElementById('uName').innerHTML;
  	username = escape(encodeURIComponent(username));
	window.open('<%=baseURL %>/vcityQuestionresultAction.do?action=showUserSurveyResult&subtaskid='+subtaskid+'&ids='+ids+'&userName='+username);
}
 
  </script>
  <body>
  <table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">用户回答问卷列表</strong></legend><br>
<%--<table align="center" width="98%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a9ccde;">
  <tr>
    <td width="13%" bgcolor="#f1f9fe" class=" tdl_B">&nbsp;</td>
    <td width="87%" height="40" bgcolor="#f1f9fe" class="tdr_B"></td>
  </tr>
</table>
--%>
  
  
    <table align="center" width="98%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable" id="rw_list">

        <thead>
          <tr>
            <td>问卷名称</td>
            <td width="25%">操作</td>
          </tr>
        </thead>
          <c:if test="${!empty userSurveyList}">
				<c:forEach items="${userSurveyList}" var="survey" varStatus="st">
				<tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
					<input type="hidden" value="" id="text_${st.index }" />
							<td align="center">问卷${st.count }</td>
							 <td align="center" class="tdc">
							 <input type="button" value="查看结果" class="btn_BS" onClick="showUserResult('${survey.subTaskResId }','${survey.id }');" />
							 
							  </td>
		        </tr>
		       </c:forEach>
		  </c:if>
		   <c:if test="${empty userSurveyList}">
		   <tr><td colspan="2" align="center"><font color="red"  >该用户还没有需要参与的调查！</font> </td></tr>
		   </c:if>
		<tfoot>
          <tr>
            <td colspan="2"></td>
          </tr>
        </tfoot>
      </table>
      
 </fieldset>
	
	</td>
  </tr>
</table>   
    
  </body>

</html>
