<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/common/page/common.jsp"%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调查问卷统计结果</title>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/vcity/inc/style.css">
<script type="text/javascript" src="<%=baseURL%>/common/js/jquery-1.6.2.js"></script>
<script type="text/javascript" src="<%=baseURL%>/vcity/inc/Highcharts/js/highcharts.js"></script>
<%--<script type="text/javascript" src="<%=baseURL%>/vcity/inc/Highcharts/js/modules/exporting.js"></script>
--%></head>
<script>
/*	$(function() {
	
			$( "#tbid_1" ).hide();
			$( "#tbid_2" ).hide();
			$( "#showtb_1" ).click(
				function() {
						$( "#tbid_1" ).show("clip");
						test();
						}
				);
			$( "#tbclose_1" ).click(
				function() {
						$( "#tbid_1" ).hide("clip");
						}
				);			
					
		});	 */
		
	function pieDivShow(num){
		$( "#tbid_"+num ).show("clip");
		showPie(num);
	}	
	
	function pieDivHide(num){
		$( "#tbid_"+num ).hide("clip");
	}	
		var category = ${dateList };
		var data = ${peopleList };
		var chart;
		$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						defaultSeriesType: 'line',
						marginRight: 130,
						marginBottom: 25
					},
					title: {
						text: '参与人数统计',
						x: -20 //center
					},
					
					xAxis: {
						categories: category
					},
					yAxis: {
					 allowDecimals: false,
						title: {
							text: '参与人数 (人)'
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
								this.x +': '+ this.y +'人';
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
						name: '人数',
						data: data
					}]
				});
				
				
			});
			
			/* 饼图 */

			function showPie(num){
			
				var pieData = new Array();
				var pieDiv = "pie_"+num;
				var itemidsValue = $("#itemids_"+num).val();
				var itemids = itemidsValue.split(",");
				
				var allResultCount = document.getElementById("allResultCount_"+num).innerHTML;
				allResultCount = parseInt(allResultCount);
				var questionName = $("#questionName_"+num).val();
				for(var i=0;i<itemids.length;i++){
					var desc = $("#desc_"+itemids[i]).val();
					var count = $("#resultCount_"+itemids[i]).val();
					count = count/allResultCount*100;
					pieData[i] = new Array(desc,parseInt(count));
				}
					chart = new Highcharts.Chart({
						chart: {
							renderTo: pieDiv,
							plotBackgroundColor: null,
							plotBorderWidth: null,
							plotShadow: false
						},
						title: {
							text: questionName
						},
						tooltip: {
							formatter: function() {
								return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
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
			}
				
</script>
<body>
<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="150" valign="top" style="background:url(<%=baseURL %>/vcity/images/tab_topbg.jpg) repeat-x; border:1px solid #9bb5d3; border-top:0px; padding:10px">

<fieldset style="height:100%; padding:10px;">
      <legend><strong class="greenFont14">统计结果</strong></legend><br>
	  
	  	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="40" align="center"><strong class="greenFont18">${vcitySurvey.sureyName }</strong></td>
  </tr>
  <tr>
    <td height="40"><strong class="blueFont14">1、本调查总体统计</strong></td>
  </tr>
  <tr>
    <td>	<div id="container" style="width: 800px; height: 400px; margin: 0 auto"></div></td>
  </tr>
  <tr>
    <td height="40"><strong class="blueFont14">2、本调查结果</strong></td>
  </tr>
  <tr>
    <td align="center">
	
	  <br>
	  
	  <c:forEach items="${vcitySurvey.vcityQuestion }" var="question">
	           	<!-- 用于记录该问题下的选项IDS -->
          <input type="hidden" id="itemids_${question.questionId }" value="" />
          <input type="hidden" id="questionName_${question.questionId }" value="${question.questionName }" />
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="91%" height="30" bgcolor="#DBF2FD" class="tdl_B"><strong class="greenFont14">${question.questionName }</strong></td>
        <td width="9%" align="center" bgcolor="#DBF2FD"><a href="###" class="blue14" id="showtb_${question.questionId }" onclick="pieDivShow(${question.questionId });">查看图表</a></td>
      </tr>
    </table>
    	<div id="tbid_${question.questionId }" style="height:300px;display: none">
			<div id="pie_${question.questionId }" style="width: 800px; height: 250px; margin: 0 auto"></div>
			<a href="###" id="tbclose_${question.questionId }" onclick="pieDivHide(${question.questionId });">关闭</a>
		</div>
      <table align="center" width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#bcd4ef" class="listTable">
        <tr>
          <td height="30" align="center" bgcolor="#f4fbff"><strong>选项</strong></td>
          <td width="10%" align="center" bgcolor="#f4fbff"><strong>数量</strong></td>
          <td width="10%" align="center" bgcolor="#f4fbff"><strong>百分比</strong></td>
          <td width="20%" bgcolor="#f4fbff">&nbsp;</td>
        </tr>
        <c:forEach items="${question.vcityQuestionitem }" var="item" varStatus="st">
        <input type="hidden" id="desc_${item.itemId}" value=" ${item.itemDesc }" />
        <input type="hidden" id="resultCount_${item.itemId}" value="${fn:length(item.vcityQuestionresult) }" />
        <tr>
          <td height="30" class="tdl_B">${st.count }、 ${item.itemDesc }</td>
          <script type="text/javascript">
	          var itemid = '${item.itemId }';
	          var questionId = ${question.questionId};
	          var items = document.getElementById("itemids_"+questionId).value;
	          if(items!="")  document.getElementById("itemids_"+questionId).value = items+","+itemid;
	          else document.getElementById("itemids_"+questionId).value = itemid;
          </script>
          <td align="center" id="r${item.itemId }">${fn:length(item.vcityQuestionresult) }</td>
          <bean:define id="resultTotal" value="${resultTotal+fn:length(item.vcityQuestionresult) }" ></bean:define>
          <td align="center" id="p${item.itemId }"></td>
          <td><div id="image_${item.itemId }" style="height:14px; background:url(<%=baseURL %>/vcity/images/tp_bg_1.jpg); width:30%"></div></td>
        </tr>
       </c:forEach>
       
        <tr>
          <td height="30" bgcolor="#f4fbff" class="tdl_B"><strong>合计</strong></td>
          <td align="center" id="allResultCount_${question.questionId}">${resultTotal }</td>
          <script language="javascript" type="text/javascript">
          var questionId = ${question.questionId};
          var itemsvalue = document.getElementById("itemids_"+questionId).value;
          var resultTotal = '${resultTotal }';
          var items = itemsvalue.split(",");
          for(var i=0;i<items.length;i++){
          	var r = document.getElementById("r"+items[i]).innerHTML;
          	if(resultTotal!=0){
	          	var percent = r/resultTotal*100;
	          	document.getElementById("p"+items[i]).innerHTML =  percent.toFixed([1])+"%";
	          	var imageDiv = document.getElementById("image_"+items[i]);
          		imageDiv.style.width = percent*2;
          	} else {
          		document.getElementById("p"+items[i]).innerHTML =  '0%';
          	}
          	
          }
          </script>
            <bean:define id="resultTotal" value="0" ></bean:define>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <br>
	  </c:forEach>
	  
	  
	  
	  </td>
  </tr>
  <tr><td align="center"><input class="btn_BS"  type="button" value="返   回" onclick="history.go(-1);" /> </td></tr>
</table>



	  
</fieldset>
	
	</td>
  </tr>
</table>
</body>
</html>
