<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>社区访问统计</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css"></head>
<script type="text/javascript" src="<%=baseURL%>/common/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/Highcharts/js/highcharts.js"></script>
<script type="text/javascript">
var category = ${dateList};
		var data = ${IPList};
		var chart;
		$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						defaultSeriesType: 'line',
						marginRight: 130,
						marginBottom: 50
					},
					title: {
						text: '社区访问统计',
						x: -20 //center
					},
					
					xAxis: {
						categories: category
					},
					yAxis: {
					 allowDecimals: false,
						title: {
							text: '访问IP数 (个)'
						},
						plotLines: [{
							value: 0,
							width: 1,
							color: '#808080'
						}]
					},
					tooltip: {
						formatter: function() {
				                return '<b>'+ this.series.name +'</b><br/>'+
								this.x +': '+ this.y +'个';
						}
					},
					legend: {
						layout: 'vertical',
						align: 'right',
						verticalAlign: 'top',
						x: -10,
						y: 100,
						borderWidth: 0
					},
					series: [{
						name: 'IP数',
						data: data
					}]
				});
				
				
			});
</script>
<body>
<html:form  action="statisticsAction.do?action=showAccessTotal"  >
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">访问统计分析</strong></legend><br>
		<table width="668" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td>
			<td>访问时间：<input type="text" name="beginTime" value="${beginTime }" id="beginTime" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')" class="blueborderText" style="width:147px">
          ~
          <input type="text" name="endTime" id="endTime" value="${ endTime}" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')" class="blueborderText" style="width:148px">
        &nbsp;&nbsp;
        <input type="submit" name="submit" value="查询" >
        <input type="button" name="Submit2" value="返回" onClick="location.href='vcity/statistics/statisticsIndex.jsp';">			</td>
		  </tr>
		</table>

      <br>
		<div align="center" id="container"></div>		
		<br>
		<br>
		<br>
        <table align="center" width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable">
          <tr>
            <td height="50" align="center" bgcolor="#f4fbff" class="tdl_B"><strong>日期</strong></td>
            <td width="20%" align="center" bgcolor="#f4fbff">IP数</td>
            <td width="20%" align="center" bgcolor="#f4fbff">页面浏览数</td>
          </tr>
          <c:forEach items="${accessTotalList }" var="access">
          <tr>
            <td height="50" class="tdl_B">${access[0] }</td>
            <td align="center">${access[1] }</td>
             <td align="center">${access[2] }</td>
          </tr>
         </c:forEach>
        </table>
		
	  </fieldset>
	
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
