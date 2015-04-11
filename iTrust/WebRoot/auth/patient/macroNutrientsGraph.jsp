<%@page import="java.util.List"%>
<%@page import="java.lang.Long"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryDailySummaryBean"%>
<%@page %>

<%@page import="java.util.Calendar"%>
<script src="/iTrust/js/Chart.js"></script>
<div id="macroNutrientsGraph" >
<div id="graphSwitchGroup" style="margin-left:70px;" >
	<button id="pieChartSwitch" onclick="toggleGraph('Pie', 0, 0, 0);">Pie Chart</button>
	<button id="barChartSwitch" onclick="toggleGraph('Bar', 0, 0, 0);">Bar Chart</button>
</div>
<table>
	<tr>
	<td>
	<canvas id="chart-area" width="300" height="300"></canvas>
	<div id="legend-area"></div>
	</td>
	<td>
	<canvas id="chart-area-expected" width="300" height="300"></canvas>
	<div id="legend-area-expected"></div>
	</td>
	</tr>

</table>
</div>
<script language="JavaScript">
	var pieChart;
	var expectedPieChart;
	var barChart;
	function toggleGraph(type, act_protein, act_fat, act_carbs){
		removeBar();
		removePie();
		removeExpectedPie();
		if(type == "Pie"){
			graphPie();
			graphExpectedPie();
		}
		if(type =="Bar"){
			graphBar();
		}
			
	}
	function removePie(){
		if(pieChart!= null){
			pieChart.destroy();
		}	
	}
	function removeExpectedPie(){
		if(expectedPieChart != null){
			expectedPieChart.destroy();
		}
	}
	function removeBar(){
		if(barChart != null){
			barChart.destroy();	
		}
	}
	function graphExpectedPie(){
		var ctx = document.getElementById("chart-area-expected").getContext("2d");
		expectedPieChart = new Chart(ctx).Pie(pieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		document.getElementById("legend-area-expected").innerHTML = expectedPieChart.generateLegend();
	}
	function graphPie(){
		var ctx = document.getElementById("chart-area").getContext("2d");
		pieChart = new Chart(ctx).Pie(pieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		document.getElementById("legend-area").innerHTML = pieChart.generateLegend();
	}
	function graphBar(){
		var ctx = document.getElementById("chart-area").getContext("2d");
		barChart= new Chart(ctx).Bar(data, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		document.getElementById("legend-area").innerHTML  =barChart.generateLegend();

	}
	var data = {
		    labels: ["Protein", "Fat", "Carbs", "Total Calories"],
		    datasets: [
		        {
		            label: "Actual Macronutrients",
		            fillColor: "rgba(220,220,220,0.5)",
		            strokeColor: "rgba(220,220,220,0.8)",
		            highlightFill: "rgba(220,220,220,0.75)",
		            highlightStroke: "rgba(220,220,220,1)",
		            data: [0, 0, 0, 0]
		        },
		        {
		            label: "Expected Macronutrients",
		            fillColor: "rgba(151,187,205,0.5)",
		            strokeColor: "rgba(151,187,205,0.8)",
		            highlightFill: "rgba(151,187,205,0.75)",
		            highlightStroke: "rgba(151,187,205,1)",
		            data: [0, 0, 0, 0]
		        }
		    ]
	};
	var pieData = [
				{
					value: 50,
					color:"#F44330",
					highlight: "#FF5A5E",
					label: "Protein (gms)"
				},
				{
					value: 20,
					color: "#4CAF50",
					highlight: "#81C784",
					label: "Fat (gms)"
				},
				{
					value: 90,
					color: "#FDB45C",
					highlight: "#FFC870",
					label: "Carbs (gms)"
				}
	];
</script>