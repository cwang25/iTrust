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
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Weight:</td>
							<td><input name="weight"
								value=""
								type="text" placeholder="weight"
								id="weight"
								style="width:50px"
								pattern="[1-9][0-9]?|[12][0-9]{2}|3[0-5][0-9]|360"
								>
							<select id="weightUnit" name="wunit">
								<option value="kg">kg</option>
								<option value="lbs">lbs</option>
							</select>
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
						<tr align="left">
							<td class="subHeaderVertical">Activity: </td>
							<td>	
								<table border=0>
									<tr><td><input type="radio" name="activityLevel" id="sedentary" value="sedentary"> Sedentary</td></tr>
									<tr><td><input type="radio" name="activityLevel" id="light" value="light"> Lightly active</td></tr>
									<tr><td><input type="radio" name="activityLevel" id="moderate" value="moderate" checked> Moderately active</td></tr>
									<tr><td><input type="radio" name="activityLevel" id="high" value="high"> Very Active</td></tr>
									<tr><td><input type="radio" name="activityLevel" id="extreme" value="extreme"> Extremely active</td></tr>
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
		
		var weightElement = document.getElementById('weight');
		var weightRegex = /^([0-9])/i;
		weightElement.style.borderColor= "initial";
		if(weightElement.value !== ""){
			if(weightElement.value.match(weightRegex) == null){
				weightElement.style.borderColor="red";
			}
			
		}
		var weight = parseInt(weightElement.value);
		var weightunit = document.getElementById('weightUnit').value;
		var weightInPounds = weight;
		var weightInKg = weight;
		if(weightunit === 'kg'){
			weightInPounds = weight * 2.20462;
		}else{
			weightInKg = weight * 0.453592;
		}
		
		var height = parseInt(document.getElementById('height').value);
		var heightunit = document.getElementById('heightUnit').value;
		if(heightunit === 'in'){
			height = height * 2.54;
		}
		
		var age = parseInt(document.getElementById("age").value);
		
		var gender = document.macroForm.gender.value;
		console.log(gender);
		
		var bmr = (10 * weightInKg) + (6.25 * height) - (5 * age);
		
		if(gender === 'male') bmr += 5;
		else bmr -= 161;
		
		var activityLevel = document.macroForm.activityLevel.value;
		
		var totalCalories;
		if(activityLevel === "sedentary") totalCalories = bmr * 1.2;
		else if(activityLevel === "light") totalCalories = bmr * 1.35;
		else if(activityLevel === "moderate") totalCalories = bmr * 1.55;
		else if(activityLevel === "high") totalCalories = bmr * 1.75;
		else totalCalories = bmr * 2.1;
		
		var totalProtein = .825 * weightInPounds;
		var proteinCalories = totalProtein * 4;
		var fatCalories = (totalCalories * .25);
		var totalFat = fatCalories/9;
		var carbCalories = totalCalories - proteinCalories - fatCalories;
		var totalCarbs = carbCalories/4;
		
		var pieData = [
						{
							value: totalProtein,
							color:"#F7464A",
							highlight: "#FF5A5E",
							label: "Grams Protein"
						},
						{
							value: totalFat,
							color: "green",
							highlight: "#5AD3D1",
							label: "Grams Fat"
						},
						{
							value: totalCarbs,
							color: "#FDB45C",
							highlight: "#FFC870",
							label: "Grams Carbs"
						}

					];

			var ctx = document.getElementById("chart-area").getContext("2d");
			if(window.myPie !== null)
				window.myPie.update();
			else
				window.myPie = new Chart(ctx).Pie(pieData, {animateRotate: true, animationEasing: "noBounce", animationSteps:30});
	}
</script>