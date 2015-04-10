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
<script src="/iTrust/js/Chart.js"></script>
<script language="JavaScript">
	var chart = null;
	function graphIt(){
		//Calculate BMR by Mifflin method
		//men = 10 * weightInKg + 6.25*height in cm - 5*ageinyears + 5
		//women = 10 * weightInKg + 6.25*height in cm - 5*ageinyears - 161
		
		//BMR * activity
		//Sedentary: 1.2
		//Lightly active: 1.35
		//Moderately active: 1.55
		//Very active: 1.75
		//Extremely active: 2.1
		
		//Protein is .825g per pound of bodyweight
		//
		var allValid = true;
		
		
		var weightElement = document.getElementById('weight');
		var ageElement = document.getElementById("age");
		var heightElement = document.getElementById("height");
		
		var errorWeight = document.getElementById('errorTextWeight');
		var errorHeight = document.getElementById('errorTextHeight');
		var errorAge = document.getElementById('errorTextAge');
		
		weightElement.style.border="1px solid #DDDDDD";
		heightElement.style.border="1px solid #DDDDDD";
		ageElement.style.border="1px solid #DDDDDD";
		
		errorWeight.style.display = "none";
		errorHeight.style.display = "none";
		errorAge.style.display = "none";
		
		
		var weightRegex = /^(0*([1-9][0-9]?|[1-6][0-9]{2}|7[0-8][0-9]|790))/;
		if(weightElement.value === "" || weightElement.value.match(weightRegex) == null){
			weightElement.style.border="1px solid rgba(255, 37, 14, 0.7)";
			errorWeight.style.display = "block";
			errorWeight.innerHTML = "Enter value in range: 1-790";
			allValid = false;	
		}
		
		var heightRegex = /^(0*([1-9][0-9]?|1[0-9]{2}|2[0-6][0-9]|27[0-3]))/;
		if(heightElement.value === "" || heightElement.value.match(heightRegex) == null){
			heightElement.style.border="1px solid rgba(255, 37, 14, 0.7)";
			errorHeight.style.display = "block";
			errorHeight.innerHTML = "Enter value in range: 1-273";
			allValid = false;
		}
		
		var ageRegex = /^(0*([1-9][0-9]?|1[0-2][0-9]|130))/;
		
		if(ageElement.value === "" || ageElement.value.match(ageRegex) == null){
			ageElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
			errorAge.style.display = "block";
			errorAge.innerHTML = "Enter value in range: 1-130";
			allValid = false;
		}
		
		if(!allValid) return;
		
		
		var weight = parseFloat(weightElement.value);
		var weightunit = document.getElementById('weightUnit').value;
		var weightInPounds = weight;
		var weightInKg = weight;
		if(weightunit === 'kg'){
			weightInPounds = weight * 2.20462;
		}else{
			weightInKg = weight * 0.453592;
		}
		
		var height = parseFloat(heightElement.value);
		var heightunit = document.getElementById('heightUnit').value;
		var heightInCms = height;
		var heightInInches = height;
		if(heightunit === 'in'){
			heightInCms = height * 2.54;
		}else{
			heightInInches = height * 0.393701;
		}
		
		var age = parseInt(document.getElementById("age").value);
		
		var gender = document.macroForm.gender.value;
		console.log(gender);
		
		var goal = document.macroForm.mode.value;
		
		var bmr = (10 * weightInKg) + (6.25 * heightInCms) - (5 * age);
		
		if(gender === 'male') bmr += 5;
		else bmr -= 161;
		
		var activityLevel = document.macroForm.activityLevel.value;
		
		var totalCalories;
		if(activityLevel === "sedentary") totalCalories = bmr * 1.2;
		else if(activityLevel === "light") totalCalories = bmr * 1.35;
		else if(activityLevel === "moderate") totalCalories = bmr * 1.55;
		else if(activityLevel === "high") totalCalories = bmr * 1.75;
		else totalCalories = bmr * 2.1;
		
		if(goal == 'losemode') totalCalories -= 500;
		else if(goal == 'gainmode') totalCalories += 400;
		
		var totalProtein = .825 * weightInPounds;
		var proteinCalories = totalProtein * 4;
		var fatCalories = (totalCalories * .25);
		var totalFat = fatCalories/9;
		var carbCalories = totalCalories - proteinCalories - fatCalories;
		var totalCarbs = carbCalories/4;
		
		var pieData = [
						{
							value: parseInt(totalProtein),
							color:"#F44330",
							highlight: "#FF5A5E",
							label: "Protein (gms)"
						},
						{
							value: parseInt(totalFat),
							color: "#4CAF50",
							highlight: "#81C784",
							label: "Fat (gms)"
						},
						{
							value: parseInt(totalCarbs),
							color: "#FDB45C",
							highlight: "#FFC870",
							label: "Carbs (gms)"
						}

					];

		var ctx = document.getElementById("chart-area").getContext("2d");
		if(chart == null){
			chart = new Chart(ctx).Pie(pieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
		}
		else{
			chart.segments[0].value = parseInt(totalProtein);
			chart.segments[1].value = parseInt(totalFat);
			chart.segments[2].value = parseInt(totalCarbs);
			chart.update();
		}
	}
</script>



<table style="border:none" align="center">
	<tr>
		<td>
			<form action="myfoodDiary.jsp" method="POST" id="macroForm" name="macroForm" align="left">
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
									<tr><td><input type="radio" name="gender" value="male" checked> Male</td></tr>
									<tr><td><input type="radio" name="gender" value="female"> Female</td></tr>
								</table>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Age:</td>
							<td><input name="age"
								value=""
								id="age"
								type="text" placeholder="Age"
								style="width:50px">
							<div id="errorTextAge" style="display:none; color:red">Hello</div>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Weight:</td>
							<td><input name="weight"
								value=""
								type="text" placeholder="weight"
								id="weight"
								style="width:50px"
								>
							<select id="weightUnit" name="wunit">
								<option value="kg">kg</option>
								<option value="lbs">lbs</option>
							</select>
							<div id="errorTextWeight" style="display:none; color:red">Hello</div>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Height:</td>
							<td><input name="height"
								id="height"
								value=""
								type="text" placeholder="height"
								style="width:50px"
								>
							<select id="heightUnit" name="hunit">
								<option value="cm">cm</option>
								<option value="in">in</option>
							</select>
							<div id="errorTextHeight" style="display:none; color:red">Hello</div>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Goals: </td>
							<td>	
								<table>
									<tr><td><input onclick="graphIt();" type="radio" name="mode" id="losemode" value="losemode"> Lose weight</td></tr>
									<tr><td><input onclick="graphIt();" type="radio" name="mode" id="maintainmode" value="maintainmode" checked> Maintain weight</td></tr>
									<tr><td><input onclick="graphIt();" type="radio" name="mode" id="gainmode" value="gainmode"> Gain weight</td></tr>
								</table>
							</td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Activity: </td>
							<td>	
								<table border=0>
									<tr><td><input onclick="graphIt();" type="radio" name="activityLevel" id="sedentary" value="sedentary"> Sedentary</td></tr>
									<tr><td><input onclick="graphIt();" type="radio" name="activityLevel" id="light" value="light"> Lightly active</td></tr>
									<tr><td><input onclick="graphIt();" type="radio" name="activityLevel" id="moderate" value="moderate" checked> Moderately active</td></tr>
									<tr><td><input onclick="graphIt();" type="radio" name="activityLevel" id="high" value="high"> Very Active</td></tr>
									<tr><td><input onclick="graphIt();" type="radio" name="activityLevel" id="extreme" value="extreme"> Extremely active</td></tr>
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