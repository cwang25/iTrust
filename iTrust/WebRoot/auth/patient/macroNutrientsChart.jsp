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



<%
//Add java stuff here
%>
<table style="border:none" align="center">
	<tr>
		<td>
			<form action="myfoodDiary.jsp" method="POST" id="macroForm" align="left">
					<table class="fTable" align="left">
						<tr>
							<th id="form_top_banner" colspan=1>Calculate Macronutrients</th>
							<th>
							<input type="button" name="cancel" style="color: black;font-size: 16pt; font-weight: bold; float: right;"
						value="Cancel" onclick="hideHiddenForm('HiddenForm')" align="left">
							</th>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Gender:</td>
							<td>
								<table border=0>
									<tr><td><input type="radio" name="male" value="male" checked> Male</td></tr>
									<tr><td><input type="radio" name="female" value="female"> Female</td></tr>
								</table>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Weight:</td>
							<td><input name="weight"
								value=""
								type="text" placeholder="weight"
								style="width:50px"
								>
							<select id="weightUnit" name="wunit">
								<option value="kg">kg</option>
								<option value="lbs">lbs</option>
							</select>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Height:</td>
							<td><input name="weight"
								value=""
								type="text" placeholder="height"
								style="width:50px"
								>
							<select id="heightUnit" name="hunit">
								<option value="in">in</option>
								<option value="cm">cm</option>
							</select>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Goals: </td>
							<td>	
								<table border=0>
									<tr><td><input type="radio" name="mode" id="losemode" value="losemode" checked> Lose weight</td></tr>
									<tr><td><input type="radio" name="mode" id="maintainmode" value="maintainmode"> Maintain weight</td></tr>
									<tr><td><input type="radio" name="mode" id="gainmode" value="gainmode"> Gain weight</td></tr>
								</table>
							</td>
						</tr>
					</table>
			</form>
		</td>
		<td>
			<div id="canvas-holder" align="right">
				<canvas id="chart-area" width="300" height="300"></canvas>
			</div>
		</td>
	</tr>
</table>

<button onclick="graphIt();" id="preview" name="preview"
			style="font-size: 16pt" align="center">Preview</button>

<script src="/iTrust/js/Chart.js"></script>

<script language="JavaScript">
	function graphIt(){
		
		
		var pieData = [
						{
							value: 300,
							color:"#F7464A",
							highlight: "#FF5A5E",
							label: "Red"
						},
						{
							value: 50,
							color: "#46BFBD",
							highlight: "#5AD3D1",
							label: "Green"
						},
						{
							value: 100,
							color: "#FDB45C",
							highlight: "#FFC870",
							label: "Yellow"
						},
						{
							value: 40,
							color: "#949FB1",
							highlight: "#A8B3C5",
							label: "Grey"
						},
						{
							value: 120,
							color: "#4D5360",
							highlight: "#616774",
							label: "Dark Grey"
						}

					];

			var ctx = document.getElementById("chart-area").getContext("2d");
			window.myPie = new Chart(ctx).Pie(pieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
	}
</script>