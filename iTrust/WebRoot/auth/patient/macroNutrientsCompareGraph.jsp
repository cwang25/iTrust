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
<div id="graphSwitchGroup" align="center" >
<table>
<tr>
<td>
	<input type="button" id="cancelCompareGraphBtn" name="cancel" style="color: black;font-size: 16pt; font-weight: bold; float: right;"
					value="Cancel" onclick="hideHiddenForm('hiddenDailyGraph');">
</td>
</tr>
<tr>
<td>
<div class="animated_switch_container" style= "padding-left: 5px;">

		<div  class="animated_switch white">

			<input type="radio" name="animated_switch" id="switch-barChart" onclick="toggleGraph('Bar');">
			<input type="radio" name="animated_switch" id="switch-pieChart" onclick="toggleGraph('Pie');"checked>

			<label for="switch-barChart">Bar</label>
			<label for="switch-pieChart">Pie</label>

			<span class="animated_switch_toggle"></span>

		</div> <!-- end switch -->

	</div> <!-- end container -->
</td>
</tr>
</table>
	
</div>
<div id="compareBarChart" style="display:none;">
<canvas id="compare-chart-bar-area" width="500" height="520"></canvas>
<div id="cocompare-legend-bar-area-expectedOuterDiv" align="center"><table style="border:none"><tr><td id="compare-legend-bar-area"></td></tr></table></div>
</div>
<div id="comparePieChart" style="display:none;">
<table >
	<tr>
	<td >
	<canvas id="compare-chart-area" width="400" height="400"></canvas>
	<div id="compare-legend-areaOuterDiv" align="center"><table style="border:none"><tr><label style="font-size:24px;font-weight: bold;">Actual</label></tr><tr><td id="compare-legend-area"></td></tr></table></div>
	</td>
	<%if(hasExpected){%>
	<td>
	<canvas id="compare-chart-area-expected" width="400" height="400"></canvas>
	<div id="compare-legend-area-expectedOuterDiv" align="center"><table style="border:none"><tr><label style="font-size:24px;font-weight: bold;">Expected</label></tr><tr><td id="compare-legend-area-expected"></td></tr></table></div>
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
	var actualTotalCalories;
	function setActualVal(act_protein, act_fat, act_carbs, act_totalCal){
		data.datasets[0].data[0] = act_protein;
		data.datasets[0].data[1] = act_fat;
		data.datasets[0].data[2] = act_carbs;
		data.datasets[0].data[3] = act_totalCal;
		actualTotalCalories = act_totalCal;
		actualPieData[0].value = act_protein;
		actualPieData[1].value = act_fat;
		actualPieData[2].value = act_carbs;
	}
	function legendForComparePie(parent, data, expected) {
		// Used while updating chart.
		// If a previous legend exists, delete it
		while (parent.hasChildNodes()) {
			parent.removeChild(parent.lastChild);
		}

		// Make a new unordered list
		var ul = document.createElement('ul');
		ul.style.paddingTop = "10px";
		// Add it under the legendDiv element
		parent.appendChild(ul);
		// Using traditional for loop because chrome does not support for each loop
		// Iterate through all data points and add a legend entry
		for (var i = 0; i < data.length; i++) {
			var item = data[i];
			// Make a new list element for this data item
			var li = document.createElement('li');
			var imageUrl = "/iTrust/image/sq_" + item.color.substring(1) + ".png";
			// Set list to use an image for bullets instead of regular bullets
			li.style.listStyleImage = "url(" + "\'" + imageUrl + "\'" + ")";
			var label = item.label.substring(0, item.label.indexOf('(') - 1) + ": "
					+ item.value + " g";
			li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:black; gravity:left\">"
					+ label + "</span></p>";
			// Add this data point to the legend
			ul.appendChild(li);
		}
		// Create a div to show total calorie count
		var calorieDisplay = document.createElement('div');
		// Set its font size to 18px
		calorieDisplay.style.fontSize = "18px";
		calorieDisplay.style.paddingLeft = "15px";
		// Set its content
		var totalCalStr = expected?<%=String.format("%.0f", totalCal)%> : actualTotalCalories.toFixed(0);
		calorieDisplay.innerHTML = "Total Calories: " + totalCalStr;
		// Add it to the legend
		parent.appendChild(calorieDisplay);
	}
	function legendForCompareBar(parent, data) {
		// Used while updating chart.
		// If a previous legend exists, delete it
		while (parent.hasChildNodes()) {
			parent.removeChild(parent.lastChild);
		}

		// Make a new unordered list
		var ul = document.createElement('ul');
		ul.style.paddingTop = "10px";
		// Add it under the legendDiv element
		parent.appendChild(ul);
		// Using traditional for loop because chrome does not support for each loop
		// Iterate through all data points and add a legend entry
		for (var i = 0; i < data.datasets.length; i++) {
			var item = data.datasets[i];
			// Make a new list element for this data item
			var li = document.createElement('li');
			var imageUrl = "/iTrust/image/sq_" + item.color.substring(1) + ".png";
			// Set list to use an image for bullets instead of regular bullets
			li.style.listStyleImage = "url(" + "\'" + imageUrl + "\'" + ")";
			var label = item.label;
			li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:black; gravity:left\">"
					+ label + "</span></p>";
			// Add this data point to the legend
			ul.appendChild(li);
		}
		
	}
	function toggleGraph(type){
		removeBar();
		removePie();
		removeExpectedPie();
		if(type == "Pie"){
			var radiobtn = document.getElementById("switch-pieChart");
			radiobtn.checked = true;
			document.getElementById("compareBarChart").style.display = "none";
			document.getElementById("comparePieChart").style.display = "block";
			graphPie();
			legendForComparePie(document.getElementById('compare-legend-area'), actualPieData, false);
			<%
			if(hasExpected){
			%>
			graphExpectedPie();
			legendForComparePie(document.getElementById('compare-legend-area-expected'), expectedPieData, true);
			<%
			}
			%>
			scrollToDiv("comparePieChart");
		}
		if(type =="Bar"){
			document.getElementById("compareBarChart").style.display = "block";
			document.getElementById("comparePieChart").style.display = "none";
			graphBar();
			legendForCompareBar(document.getElementById('compare-legend-bar-area'), data);
			scrollToDiv("compareBarChart");
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
		            color:"#97BBCD",
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
		            color:"#DCDCDC",
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