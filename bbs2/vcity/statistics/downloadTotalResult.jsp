<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关注度统计结果</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/Highcharts/js/highcharts.js"></script>
</head>
<script type="text/javascript">
		var nameList = ${nameList};
		var totalList = ${totalList};
		var chart;
		var pieData = new Array();
		for(var i=0;i<totalList.length;i++){

		var percent = totalList[i];
			pieData[i] =  new Array(nameList[i],percent);
		}
		$(document).ready(function() {
				chart = new Highcharts.Chart({
						chart: {
							renderTo: 'container',
							plotBackgroundColor: null,
							plotBorderWidth: null,
							plotShadow: false
						},
						title: {
							text: "关注度统计"
						},
						tooltip: {
							formatter: function() {
								return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                        				 Highcharts.numberFormat(this.y, 0, ',') +' 次)';
							}
						},
						plotOptions: {
							pie: {
								allowPointSelect: true,
								cursor: 'pointer',
								dataLabels: {
									enabled: false
								},
								showInLegend: true
							}
						},
					    series: [{
							type: 'pie',
							name: 'Browser share',
							data: pieData
						}]
					});				
			});
</script>
<body>
<html:form  action="statisticsAction.do?action=downloadTotal"  >
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">关注度统计分析</strong></legend>
      <br>
		<table width="668" align="center" border="0" cellspacing="0" cellpadding="0">
		 <tr>
			<td>选择资源类别：  <select name="resType" id="resType" >
			
				<option value="1" <c:if test="${resType==1 }"> selected="selected"</c:if>  >应用</option>
				<option value="2" <c:if test="${resType==2 }"> selected="selected"</c:if>   >皮肤</option>
			</select> </td>
			<td>下载时间：<input type="text" name="beginTime" value="${beginTime }" id="beginTime" onClick="new WdatePicker(this,'%Y-%M-%D',false,'whyGreen')" class="blueborderText" style="width:147px">
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
