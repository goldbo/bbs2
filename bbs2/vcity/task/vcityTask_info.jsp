<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务详情</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
</head>

<body>
<table width="97%" height="97%" align="center" border="0" cellpadding="0" cellspacing="1" class="tableWrap">
  <tr>
    <td valign="top"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="17%" height="40"><strong class="greenFont14">基本信息</strong></td>
        <td width="33%">&nbsp;</td>
        <td width="17%">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>任务名称：</STRONG></p></td>
        <td>${mainTask.taskName }</td>
        <td height="30" align="right"><p><STRONG>任务类型：</STRONG></p></td>
        <c:if test="${mainTask.taskType ==1}"><td>自动任务</td></c:if>
        <c:if test="${mainTask.taskType ==2}"><td>人工任务</td></c:if>
      </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>有效期：</STRONG></p></td>
        <td>${mainTask.taskBegin }~${mainTask.taskEnd }</td>
        <td height="30" align="right"><p><STRONG>任务积分：</STRONG></p></td>
        <td>${mainTask.credit }分</td>
      </tr>
      <c:if test="${mainTask.taskType ==2}">
      <tr>
        <td height="30" align="right"><strong>已选择调查问卷：</strong></td>
        <td height="30" colspan="3">${surveyName }</td>
      </tr>
      </c:if>
	  <tr>
        <td height="30" align="right"><strong>人员列表：</strong></td>
        <td height="30" colspan="3">${users }</td>
        </tr>
      <tr>
        <td height="30" align="right"><p><STRONG>任务介绍：</STRONG></p></td>
        <td height="30" colspan="3">  ${mainTask.taskIntro }</td>
        </tr>
         <c:if test="${mainTask.taskType ==1}">
        <tr>
        <td height="30" align="right"><p><STRONG>任务步骤：</STRONG></p></td>
        <td height="30">  
			<c:if test="${ !empty indexList }">
				<c:forEach items="${ indexList }" varStatus="st" var="sub">
					${st.index+1 }、${sub.subTaskDesc } </br>
				</c:forEach>
			</c:if>
	      </td>
      </tr>
      </c:if>
      <tr>
        <td  colspan="4" align="center"><table width="100%" align="center" border="1" cellspacing="0" cellpadding="0" bordercolor="#A6CCEB" style="border-collapse:collapse; margin:8px 0px;">
      <tr>
        <td class="table_thead tdl">任务详情</td>
      </tr>
      <tr>
        <td><table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#D0D0D0" class="tablelist">
          <thead>
            <tr>
              <td width="15%" class="tdl_B">参与人</td>
              <td class="tdl_B">任务内容</td>
              <td width="20%" class="tdl_B">任务状态</td>
            </tr>
          </thead>
          <c:forEach items="${subTaskList }" var="sub" varStatus="status">
	          <tr onMouseMove="OM_table(this);" onMouseOut="OO_table(this);">
	            <td class="tdl_B">${sub[0]}</td>
	            <td class="tdl_B">
		            <c:forEach items="${sub[1] }" var="subTask" varStatus="st">
		           	 ${st.index+1 }、${subTask.subTaskDesc }
		           	 <c:if test="${subTask.subTaskState ==0 }"> (未开始)</c:if>
		           	 <c:if test="${subTask.subTaskState ==1 }"> (已完成)</c:if>
		           	 <br>	
		           </c:forEach>
	            </td>
	            <td class="tdl_B"><c:if test="${sub[2].taskState==0}">未参与</c:if>
	            <c:if test="${sub[2].taskState==1}">参与中</c:if>
	            <c:if test="${sub[2].taskState==2}">已完成</c:if><br>
	            完成日期：${sub[2].taskTime}</td>
	          </tr>
          </c:forEach>
          
        </table></td>
      </tr>
      
    </table></td>
        </tr>   
    </table></td>
  </tr>
   <tr><td colspan="3" align="center"><input type="button" class="btn_BS"  value="返  回" onclick="history.go(-1);"> </td></tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
