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
<%@page import="edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMacroNutrientPlanAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddMacroNutrientPlanAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMacroNutrientPlanAction"%>

<%@page import="java.util.Calendar"%>
<!--  -->

<%
	AddMacroNutrientPlanAction addMacroNutrientPlanAction = new AddMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
	EditMacroNutrientPlanAction editMacroNutrientPlanAction = new EditMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
	//ViewMacroNutrientPlanAction viewMacroNutrientPlanAction = new ViewMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
%>
<script src="/iTrust/js/Chart.js"></script>
<script src="/iTrust/js/macroNutrientsChart.js"></script>
	
<table style="border:none" align="center">
	<tr>
		<td>
			<form action="macroNutrientsChart.jsp" method="POST" id="macroForm" name="macroForm" align="left">
					<table class="fTable" align="left">
						<tr>
							<th id="form_top_banner" colspan=1>Calculate Macronutrients</th>
							<th>
							<input type="button" name="cancel" style="color: black;font-size: 16pt; font-weight: bold; float: right;"
						value="Cancel" onclick="hideHiddenForm('hiddenMacro')" align="left">
							</th>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Gender:</td>
							<td>
								<table border=0>
									<tr><td><input onclick="radioChange();" type="radio" name="gender" value="male" checked> Male</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="gender" value="female"> Female</td></tr>
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
									<tr><td><input onclick="radioChange();" type="radio" name="mode" id="losemode" value="losemode"> Lose weight</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="mode" id="maintainmode" value="maintainmode" checked> Maintain weight</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="mode" id="gainmode" value="gainmode"> Gain weight</td></tr>
								</table>
							</td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Activity: </td>
							<td>	
								<table border=0>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="sedentary" value="sedentary"> Sedentary</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="light" value="light"> Lightly active</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="moderate" value="moderate" checked> Moderately active</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="high" value="high"> Very Active</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="extreme" value="extreme"> Extremely active</td></tr>
								</table>
							</td>
						</tr>
					</table>
					<input type="submit" id="submitForm" style="display:none">
			</form>
		</td>
		<td>
			<div id="canvas-holder">
				<canvas id="chart-area" width="400" height="400"></canvas>
				<div id="legendOuterDiv" align="center"><table style="border:none"><tr><td id="legendDiv"></td></tr></table></div>
			</div>
		</td>
	</tr>
</table>
<div id="buttons" align="center">
	<button onclick="graphIt();" id="preview" name="preview"
			style="font-size: 16pt">Preview</button>
	<button onclick="saveForm();" id="saveMacroForm" name="saveMacroForm"
			style="font-size: 16pt" disabled>Save</button> 
</div>