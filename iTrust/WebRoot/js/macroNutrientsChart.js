var chart = null;
var allValid = true;
var totalCalories;
function graphIt() {
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
	allValid = true;

	var weightElement = document.getElementById('weight');
	var ageElement = document.getElementById("age");
	var heightElement = document.getElementById("height");

	var errorWeight = document.getElementById('errorTextWeight');
	var errorHeight = document.getElementById('errorTextHeight');
	var errorAge = document.getElementById('errorTextAge');

	weightElement.style.border = "1px solid #DDDDDD";
	heightElement.style.border = "1px solid #DDDDDD";
	ageElement.style.border = "1px solid #DDDDDD";

	errorWeight.style.display = "none";
	errorHeight.style.display = "none";
	errorAge.style.display = "none";

	//var weightRegex = /^(0*([1-9][0-9]?|[1-6][0-9]{2}|7[0-8][0-9]|790))/;
	if (weightElement.value === "" || weightElement.value.length > 3
			|| !$.isNumeric(weightElement.value)
			|| parseInt(weightElement.value) <= 0
			|| parseInt(weightElement.value) > 790) {
		weightElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
		errorWeight.style.display = "block";
		errorWeight.innerHTML = "Enter value in range 1-790";
		allValid = false;
	}

	//var heightRegex = /^(0*([1-9][0-9]?|1[0-9]{2}|2[0-6][0-9]|27[0-3]))/;
	if (heightElement.value === "" || heightElement.value.length > 3
			|| !$.isNumeric(heightElement.value)
			|| parseInt(heightElement.value) <= 0
			|| parseInt(heightElement.value) > 273) {
		heightElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
		errorHeight.style.display = "block";
		errorHeight.innerHTML = "Enter value in range: 1-273";
		allValid = false;
	}

	//var ageRegex = /^(0*([1-9][0-9]?|1[0-2][0-9]|130))/;

	if (ageElement.value === "" || ageElement.value.length > 3
			|| !$.isNumeric(ageElement.value)
			|| parseInt(ageElement.value) <= 0
			|| parseInt(ageElement.value) > 130) {
		ageElement.style.border = "1px solid rgba(255, 37, 14, 0.7)";
		errorAge.style.display = "block";
		errorAge.innerHTML = "Enter value in range: 1-130";
		allValid = false;
	}

	if (!allValid)
		return;

	var weight = parseFloat(weightElement.value);
	var weightunit = document.getElementById('weightUnit').value;
	var weightInPounds = weight;
	var weightInKg = weight;
	if (weightunit === 'kg') {
		weightInPounds = weight * 2.20462;
	} else {
		weightInKg = weight * 0.453592;
	}

	var height = parseFloat(heightElement.value);
	var heightunit = document.getElementById('heightUnit').value;
	var heightInCms = height;
	var heightInInches = height;
	if (heightunit === 'in') {
		heightInCms = height * 2.54;
	} else {
		heightInInches = height * 0.393701;
	}

	var age = parseInt(document.getElementById("age").value);

	var gender = document.macroForm.gender.value;
	console.log(gender);

	var goal = document.macroForm.mode.value;

	var bmr = (10 * weightInKg) + (6.25 * heightInCms) - (5 * age);

	if (gender === 'male')
		bmr += 5;
	else
		bmr -= 161;

	var activityLevel = document.macroForm.activityLevel.value;

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

	if (goal == 'losemode')
		totalCalories -= 500;
	else if (goal == 'gainmode')
		totalCalories += 400;

	var totalProtein = .825 * weightInPounds;
	var proteinCalories = totalProtein * 4;
	var fatCalories = (totalCalories * .25);
	var totalFat = fatCalories / 9;
	var carbCalories = totalCalories - proteinCalories - fatCalories;
	var totalCarbs = carbCalories / 4;

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

	var ctx = document.getElementById("chart-area").getContext("2d");
	var options = {
		animateRotate : true,
		animationEasing : "noBounce",
		animationSteps : 30
//		onAnimationComplete: function () {
//			var ctx = $('#chart-area').get(0).getContext("2d");
//		    var canvasWidthvar = $('#chart-area').width();
//		    var canvasHeight = $('#chart-area').height();
//		    var constant = 700;
//		    var fontsize = (canvasHeight/constant).toFixed(2);
//		    //ctx.font="2.8em Verdana";
//		    ctx.font=fontsize +"em Verdana";
//		    ctx.textBaseline="middle"; 
//		    var calories = "Total Calories: " + parseInt(totalCalories);
//		    var textWidth = ctx.measureText(calories).width;
//		    
//		     var txtPosx = Math.round((canvasWidthvar - textWidth)/2);
//		      ctx.fillText(calories, txtPosx, canvasHeight/2);
//		}
	};

	if (chart == null) {
		chart = new Chart(ctx).Pie(pieData, options);
	} else {
		chart.segments[0].value = parseInt(totalProtein);
		chart.segments[1].value = parseInt(totalFat);
		chart.segments[2].value = parseInt(totalCarbs);
		chart.update();
	}
	legendPie(document.getElementById('legendDiv'), pieData);
}

function radioChange() {
	if (chart !== null) {
		graphIt();
	}
}

function legendPie(parent, data) {
   while(parent.hasChildNodes()){
	   parent.removeChild(parent.lastChild);
   }
	
   var ul = document.createElement('ul');
   parent.appendChild(ul);
   for(var i = 0; i < data.length; i++){
	   var item = data[i];
	   var li = document.createElement('li');
	   var imageUrl = "/iTrust/image/sq_" + item.color.substring(1) + ".png";
	   li.style.listStyleImage = "url(" + "\'" + imageUrl + "\'" + ")";
	   //li.style.fontSize = "300%";
	   var label = item.label.substring(0 , item.label.indexOf('(') - 1) + ": " + item.value + " gms";
	   li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:black; gravity:left\">"+label+"</span></p>";
	   ul.appendChild(li);
   }
   var calorieDisplay = document.createElement('div');
   calorieDisplay.style.fontSize = "18px";
   calorieDisplay.marginLeft = "30px";
   
   calorieDisplay.innerHTML = "Total Calories: " + parseInt(totalCalories);
   parent.appendChild(calorieDisplay);
}