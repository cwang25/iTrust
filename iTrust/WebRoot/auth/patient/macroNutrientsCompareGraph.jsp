<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Long"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryDailySummaryBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMacroNutrientPlanAction"%>

<%@page import="java.util.Calendar"%>
<%
	ViewMacroNutrientPlanAction viewMacroNutrientPlanAction = new ViewMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
	double proteinGram = 0;
	double fatGram = 0;
	double carbsGram = 0;
	double totalCal = 0;
	boolean hasExpected = true;
	if(viewMacroNutrientPlanAction.isNutritionist()){
		String pidStringForCompare = (String)session.getAttribute("pid");
		List<MacroNutrientPlanBean> list = viewMacroNutrientPlanAction.getMacroNutrientPlanListByOwnerID(Long.parseLong(pidStringForCompare));
		if(list != null && list.size()>0){
			MacroNutrientPlanBean b = list.get(0);
			proteinGram = b.getProtein();
			fatGram = b.getFat();
			carbsGram = b.getCarbs();
			totalCal = b.getTotalCal();	
		}else{
			hasExpected = false;
		}
	}else{
		List<MacroNutrientPlanBean> list = viewMacroNutrientPlanAction.getMacroNutrientPlanListByOwnerID(loggedInMID);
		if(list != null && list.size()>0){
			MacroNutrientPlanBean b = list.get(0);
			proteinGram = b.getProtein();
			fatGram = b.getFat();
			carbsGram = b.getCarbs();
			totalCal = b.getTotalCal();	
		}else{
			hasExpected = false;
		}
	}
%>
<script src="/iTrust/js/Chart.js"></script>
<div id="macroNutrientsGraph" >
<div id="graphSwitchGroup" style="margin-left:70px;" >
	<button id="pieChartSwitch" onclick="toggleGraph('Pie');">Pie Chart</button>
	<button id="barChartSwitch" onclick="toggleGraph('Bar');">Bar Chart</button>
</div>
<div id="compareBarChart" style="display:none;">
<canvas id="compare-chart-bar-area" width="400" height="400"></canvas>
<div id="compare-legend-bar-area"></div>
</div>
<div id="comparePieChart" style="display:none;">
<table >
	<tr>
	<td >
	<canvas id="compare-chart-area" width="400" height="400"></canvas>
	<div id="compare-legend-area"></div>
	</td>
	<%if(hasExpected){%>
	<td>
	<canvas id="compare-chart-area-expected" width="400" height="400"></canvas>
	<div id="compare-legend-area-expected"></div>
	</td>
	<%} %>
	</tr>

</table>
</div>
</div>
<script language="JavaScript">
	var pieChart;
	var expectedPieChart;
	var barChart;
	function setActualVal(act_protein, act_fat, act_carbs, act_totalCal){
		data.datasets[0].data[0] = act_protein;
		data.datasets[0].data[1] = act_fat;
		data.datasets[0].data[2] = act_carbs;
		data.datasets[0].data[3] = act_totalCal;
		actualPieData[0].value = act_protein;
		actualPieData[1].value = act_fat;
		actualPieData[2].value = act_carbs;
	}
	function toggleGraph(type){
		removeBar();
		removePie();
		removeExpectedPie();
		if(type == "Pie"){
			document.getElementById("compareBarChart").style.display = "none";
			document.getElementById("comparePieChart").style.display = "block";
			graphPie();
			<%
			if(hasExpected){
			%>
			graphExpectedPie();
			<%
			}
			%>
		}
		if(type =="Bar"){
			document.getElementById("compareBarChart").style.display = "block";
			document.getElementById("comparePieChart").style.display = "none";
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
		var ctx = document.getElementById("compare-chart-area-expected").getContext("2d");
		expectedPieChart = new Chart(ctx).Pie(expectedPieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		document.getElementById("compare-legend-area-expected").innerHTML = expectedPieChart.generateLegend();
	}
	function graphPie(){
		var ctx = document.getElementById("compare-chart-area").getContext("2d");
		pieChart = new Chart(ctx).Pie(actualPieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		document.getElementById("compare-legend-area").innerHTML = pieChart.generateLegend();
	}
	function graphBar(){
		var ctx = document.getElementById("compare-chart-bar-area").getContext("2d");
		barChart= new Chart(ctx).Bar(data, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		document.getElementById("compare-legend-bar-area").innerHTML  =barChart.generateLegend();

	}
	var data = {
		    labels: ["Protein", "Fat", "Carbs", "Total Calories"],
		    datasets: [
		        {
		            label: "Actual Macronutrients",
		            fillColor: "rgba(151,187,205,0.5)",
		            strokeColor: "rgba(151,187,205,0.8)",
		            highlightFill: "rgba(151,187,205,0.75)",
		            highlightStroke: "rgba(151,187,205,1)",
		            data: [0, 0, 0, 0]
		        }
		        <%
		     if(hasExpected){
		        %>
		        ,{
		            label: "Expected Macronutrients",
		            fillColor: "rgba(220,220,220,0.5)",
		            strokeColor: "rgba(220,220,220,0.8)",
		            highlightFill: "rgba(220,220,220,0.75)",
		            highlightStroke: "rgba(220,220,220,1)",
		            data: [<%=proteinGram%>, <%=fatGram%>, <%=carbsGram%>, <%=totalCal%>]
		        }
		        <%
		     }
		        %>
		    ]
	};
	var actualPieData = [
				{
					value: 0,
					color:"#F44330",
					highlight: "#FF5A5E",
					label: "Protein (gms)"
				},
				{
					value: 0,
					color: "#4CAF50",
					highlight: "#81C784",
					label: "Fat (gms)"
				},
				{
					value: 0,
					color: "#FDB45C",
					highlight: "#FFC870",
					label: "Carbs (gms)"
				}
	];
	var expectedPieData = [
					{
						value: <%=proteinGram%>,
						color:"#F44330",
						highlight: "#FF5A5E",
						label: "Protein (gms)"
					},
					{
						value: <%=fatGram%>,
						color: "#4CAF50",
						highlight: "#81C784",
						label: "Fat (gms)"
					},
					{
						value: <%=carbsGram%>,
						color: "#FDB45C",
						highlight: "#FFC870",
						label: "Carbs (gms)"
					}
		];
</script>