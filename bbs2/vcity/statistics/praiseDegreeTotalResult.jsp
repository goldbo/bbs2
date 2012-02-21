<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>好评度统计</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/Highcharts/js/highcharts.js"></script>
</head>
<script type="text/javascript">
		
			var chart;
			var names = ${nameList};
			var upList = ${upList};
			var downList = ${downList};
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						defaultSeriesType: 'column'
					},
					title: {
						text: '好评度统计'
					},
					
					xAxis: {
						categories:names
					},
					yAxis: {
					 allowDecimals: false,
						min: 0,
						title: {
							text: '评价次数 (次)'
						}
					},
					legend: {
						layout: 'vertical',
						backgroundColor: '#FFFFFF',
						align: 'left',
						verticalAlign: 'top',
						x: 100,
						y: 70,
						floating: true,
						shadow: true
					},
					tooltip: {
						formatter: function() {
							return ''+
								this.x +': '+ this.y +' 次';
						}
					},
					plotOptions: {
						column: {
							pointPadding: 0.2,
							borderWidth: 0
						}
					},
				        series: [{
						name: '好评',
						data: upList
				
					}, {
						name: '差评',
						data: downList
				
					} ]
				});
							
			});
				
		</script>
<body>
<html:form  action="statisticsAction.do?action=praiseDegreeTotal"  >
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">好评度统计分析</strong></legend>
      <br>
		<table width="668" align="center" border="0" cellspacing="0" cellpadding="0">
		 <tr>
			<td>选择资源类别：  <select name="resType" id="resType" >
			
				<option value="1" <c:if test="${resType==1 }"> selected="selected"</c:if>  >应用</option>
				<option value="2" <c:if test="${resType==2 }"> selected="selected"</c:if>   >皮肤</option>
			</select> </td>
			<td>评价时间时间：<input type="text" name="beginTime" value="${beginTime }" id="beginTime" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')" class="blueborderText" style="width:147px">
          ~
          <input type="text" name="endTime" id="endTime" value="${ endTime}" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')" class="blueborderText" style="width:148px">
        &nbsp;&nbsp;
        <input type="submit" name="submit" value="查询" >
        <input type="button" name="Submit2" value="返回" onClick="location.href='vcity/statistics/statisticsIndex.jsp';">			</td>
		  </tr>
		</table>

      <br>
		<div align="center" id="container"></div>
	  </fieldset>
	
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
