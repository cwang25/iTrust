/*
 * Javascript for handling drawing of macronutrient chart
 * Contains functions to graph chart, draw legend and callback for
 * radio button selection change
 * 
 * @version 1.0
 * @author Team 21 (Spring 2015)
 */

//Variable for chart
var chart = null;

// Variable to check if all input is valid
var allValid = true;

// Variable to keep track of total calories a user needs per day
var totalCalories;

// Function that graphs the daily macronutrient requirement for a user
function graphIt() {

	/*
	 * Algorithm for calculating macronutrients 
	 * 
	 * Calculate BMR by Mifflin method
	 * men = 10 * weightInKg + 6.25 * height in cm - 5 * ageinyears + 5
	 * women = 10 * weightInKg + 6.25 * height in cm - 5 * ageinyears - 161
	 * 
	 * Total Calories (Energy expenditure) = BMR * activity 
	 * Activity levels:
	 *   Sedentary: 1.2 
	 *   Lightly active: 1.35 
	 *   Moderately active: 1.55 
	 *   Very active:
	 *   1.75 Extremely active: 2.1
	 * 
	 * Modify total calories based on goal 
	 * Goals: 
	 *   Lose weight: -500 calories
	 *   Maintain weight: No change 
	 *   Gain weight: +400 calories
	 * 
	 * Protein is .825g per pound of bodyweight 
	 * Fat is 25% of total calories (energy expenditure) 
	 * Remaining calories come from carbohydrates
	 * 
	 * 1g protein = 4 calories 
	 * 1g fat = 9 calories 
	 * 1g carb = 4 calories
	 */

	// Set all valid to true
	allValid = true;

	// Get all the required elements
	var weightElement = document.getElementById('weight');
	var ageElement = document.getElementById("age");
	var heightElement = document.getElementById("height");

	// Get hidden elements in order to show error message, if any
	var errorWeight = document.getElementById('errorTextWeight');
	var errorHeight = document.getElementById('errorTextHeight');
	var errorAge = document.getElementById('errorTextAge');

	// Remove all error highlighting
	weightElement.style.border = "1px solid #DDDDDD";
	heightElement.style.border = "1px solid #DDDDDD";
	ageElement.style.border = "1px solid #DDDDDD";

	// Remove all error messages
	errorWeight.style.display = "none";
	errorHeight.style.display = "none";
	errorAge.style.display = "none";

	// Check to see if weight input is valid and in the range 1 - 790 (Heaviest
	// man ever : 790 pounds)
	if (weightElement.value === "" || weightElement.value.length > 3
			|| !$.isNumeric(weightElement.value)
			|| parseInt(weightElement.value) <= 0
			|| parseInt(weightElement.value) > 790) {
		// If so, highlight the text box and display error message
		weightElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
		errorWeight.style.display = "block";
		errorWeight.innerHTML = "Enter value in range 1-790";
		allValid = false;
	}

	// Check to see if height input is valid and in the range 1 - 273 (Tallest
	// man ever:273cms)
	if (heightElement.value === "" || heightElement.value.length > 3
			|| !$.isNumeric(heightElement.value)
			|| parseInt(heightElement.value) <= 0
			|| parseInt(heightElement.value) > 273) {
		// If so, highlight the text box and display error message
		heightElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
		errorHeight.style.display = "block";
		errorHeight.innerHTML = "Enter value in range: 1-273";
		allValid = false;
	}

	// Check to see if age input is valid and in the range 1 - 130 (Oldest man
	// ever:130 years)
	if (ageElement.value === "" || ageElement.value.length > 3
			|| !$.isNumeric(ageElement.value)
			|| parseInt(ageElement.value) <= 0
			|| parseInt(ageElement.value) > 130) {
		// If so, highlight the text box and display error message
		ageElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
		errorAge.style.display = "block";
		errorAge.innerHTML = "Enter value in range: 1-130";
		allValid = false;
	}

	// If invalid input is found, return without doing anything
	if (!allValid)
		return;

	// All input is correct at this point, so
	// Get the weight
	var weight = parseFloat(weightElement.value);
	var weightunit = document.getElementById('weightUnit').value;
	// Define variables to hold weight both in pounds and kg
	var weightInPounds = weight;
	var weightInKg = weight;
	if (weightunit === 'kg')
		weightInPounds = weight * 2.20462;
	else
		weightInKg = weight * 0.453592;

	// Get the height
	var height = parseFloat(heightElement.value);
	var heightunit = document.getElementById('heightUnit').value;
	var heightInCms = height;
	var heightInInches = height;

	if (heightunit === 'in')
		heightInCms = height * 2.54;
	else
		heightInInches = height * 0.393701;

	// Get the age
	var age = parseInt(document.getElementById("age").value);

	// Get gender
	var gender = document.macroForm.gender.value;

	// Get user's goal
	var goal = document.macroForm.mode.value;

	// Calculate base bmr
	var bmr = (10 * weightInKg) + (6.25 * heightInCms) - (5 * age);

	// Modify bmr based on gender
	if (gender === 'male')
		bmr += 5;
	else
		bmr -= 161;

	// Get the user's activity level
	var activityLevel = document.macroForm.activityLevel.value;

	// Calculate the user's daily energy expenditure based on activity level
	if (activityLevel === "sedentary")
		totalCalories = bmr * 1.2;
	else if (activityLevel === "light")
		totalCalories = bmr * 1.35;
	else if (activityLevel === "moderate")
		totalCalories = bmr * 1.55;
	else if (activityLevel === "high")
		totalCalories = bmr * 1.75;
	else
		totalCalories = bmr * 2.1;

	// Modify totalCalories based on user's goal
	if (goal == 'losemode')
		totalCalories -= 500;
	else if (goal == 'gainmode')
		totalCalories += 400;

	// Calculate macronutrients
	// Protein is .825 times body weight in pounds
	var totalProtein = .825 * weightInPounds;
	var proteinCalories = totalProtein * 4;
	// Fat is 25% of the total energy expenditure
	var fatCalories = (totalCalories * .25);
	var totalFat = fatCalories / 9;
	// Remaining calories come from carbohydrates
	var carbCalories = totalCalories - proteinCalories - fatCalories;
	var totalCarbs = carbCalories / 4;

	// Define chart data
	var pieData = [ {
		value : parseInt(totalProtein),
		color : "#F44330",
		highlight : "#FF5A5E",
		label : "Protein (gms)"
	}, {
		value : parseInt(totalFat),
		color : "#4CAF50",
		highlight : "#81C784",
		label : "Fat (gms)"
	}, {
		value : parseInt(totalCarbs),
		color : "#FDB45C",
		highlight : "#FFC870",
		label : "Carbs (gms)"
	}

	];

	// Context for the canvas element where the chart will be drawn
	var ctx = document.getElementById("chart-area").getContext("2d");
	// Specify options for the chart
	var options = {
		animateRotate : true,
		animationEasing : "noBounce",
		animationSteps : 30
	// onAnimationComplete: function () {
	// var ctx = $('#chart-area').get(0).getContext("2d");
	// var canvasWidthvar = $('#chart-area').width();
	// var canvasHeight = $('#chart-area').height();
	// var constant = 700;
	// var fontsize = (canvasHeight/constant).toFixed(2);
	// //ctx.font="2.8em Verdana";
	// ctx.font=fontsize +"em Verdana";
	// ctx.textBaseline="middle";
	// var calories = "Total Calories: " + parseInt(totalCalories);
	// var textWidth = ctx.measureText(calories).width;
	//		    
	// var txtPosx = Math.round((canvasWidthvar - textWidth)/2);
	// ctx.fillText(calories, txtPosx, canvasHeight/2);
	// }
	};

	// Check to see if chart is being drawn for the first time
	// If yes, make a new chart
	if (chart == null) {
		chart = new Chart(ctx).Pie(pieData, options);
	} else {// Else just modify existing chart
		chart.segments[0].value = parseInt(totalProtein);
		chart.segments[1].value = parseInt(totalFat);
		chart.segments[2].value = parseInt(totalCarbs);
		chart.update();
	}
	// Draw legend for the chart drawn
	legendPie(document.getElementById('legendDiv'), pieData);
}

// If a radio button is clicked, update the chart, if a chart already exists
function radioChange() {
	if (chart !== null) {
		graphIt();
	}
}

// Draws a legend for our pie chart
function legendPie(parent, data) {
	// Used while updating chart.
	// If a previous legend exists, delete it
	while (parent.hasChildNodes()) {
		parent.removeChild(parent.lastChild);
	}

	// Make a new unordered list
	var ul = document.createElement('ul');
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
				+ item.value + " gms";
		li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:black; gravity:left\">"
				+ label + "</span></p>";
		// Add this data point to the legend
		ul.appendChild(li);
	}
	// Create a div to show total calorie count
	var calorieDisplay = document.createElement('div');
	// Set its font size to 18px
	calorieDisplay.style.fontSize = "18px";
	calorieDisplay.marginLeft = "30px";
	// Set its content
	calorieDisplay.innerHTML = "Total Calories: " + parseInt(totalCalories);
	// Add it to the legend
	parent.appendChild(calorieDisplay);
}