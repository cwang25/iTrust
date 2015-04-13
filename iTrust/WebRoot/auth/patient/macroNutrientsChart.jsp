<%@page import="edu.ncsu.csc.itrust.action.ViewMacroNutrientPlanAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddMacroNutrientPlanAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMacroNutrientPlanAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddMacroNutrientProfileAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMacroNutrientProfileAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMacroNutrientProfileAction" %> 
<%@page import="edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean" %>
<%@page import="java.util.List"%>

<script src="/iTrust/js/macroNutrientsChart.js"></script>
<%
	//Make instances for actions
	ViewMacroNutrientPlanAction viewPlanAction = new ViewMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
	AddMacroNutrientPlanAction addPlanAction = new AddMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
	EditMacroNutrientPlanAction editPlanAction = new EditMacroNutrientPlanAction(prodDAO, loggedInMID.toString());
	AddMacroNutrientProfileAction addProfileAction = new AddMacroNutrientProfileAction(prodDAO, loggedInMID.toString());
	EditMacroNutrientProfileAction editProfileAction = new EditMacroNutrientProfileAction(prodDAO, loggedInMID.toString());
	ViewMacroNutrientProfileAction viewProfileAction = new ViewMacroNutrientProfileAction(prodDAO, loggedInMID.toString());
	//Use targetMID to store the target ID in order to make this jsp also work in Nutritionist HCP perspective.
	long targetMID = -1;
	if(viewPlanAction.isNutritionist()){
		targetMID = Long.parseLong((String)session.getAttribute("pid"));
	}else{
		targetMID = loggedInMID;
	}
	//Get the planlist, if there exists one
	List<MacroNutrientPlanBean> planBeanList = viewPlanAction.getMacroNutrientPlanListByOwnerID(targetMID);
	//Get the plan bean, if there exists one
	MacroNutrientPlanBean planBean = planBeanList.size() > 0 ? planBeanList.get(0) : null;

	//Check to see if record exists
	boolean noRecord = planBean == null;
	//Make instances for profile list and profile bean
	List<MacroNutrientProfileBean> profileList;
	MacroNutrientProfileBean profileBean = null;
	//If there is a record
	if(!noRecord){
		//Initialize previously defined profile list and bean
		profileList = viewProfileAction.getMacroNutrientProfileListByPlanID(planBean.getRowID());
		profileBean = profileList.get(0);
	}

	//Get parameters if form was submitted
	String gender = request.getParameter("gender") != null ? request.getParameter("gender") : "";
	String age = request.getParameter("age") != null ? request.getParameter("age") : "";
	String weight = request.getParameter("weight") != null ? request.getParameter("weight") : "";
	String height = request.getParameter("height") != null ? request.getParameter("height") : "";
	String weightUnit = request.getParameter("wunit") != null ? request.getParameter("wunit") : "";
	String heightUnit = request.getParameter("hunit") != null ? request.getParameter("hunit") : "";
	if(!"".equals(weight) && !"".equals(weightUnit) && weightUnit.equals("lbs")){
		double wt = Double.parseDouble(weight) * 0.453592;
		weight = String.format("%.2f", wt); 
	}
	if(!"".equals(height) && !"".equals(heightUnit) && heightUnit.equals("in")){
		double ht = Double.parseDouble(height) * 2.54;
		height = String.format("%.2f", ht); 
	}
	String goal = request.getParameter("mode") != null ? request.getParameter("mode") : "";
	String activityLevel = request.getParameter("activityLevel") != null ? request.getParameter("activityLevel") : "";
	String protein = request.getParameter("protein") != null ? request.getParameter("protein") : "";
	String fat = request.getParameter("fat") != null ? request.getParameter("fat") : "";
	String carbs = request.getParameter("carbs") != null ? request.getParameter("carbs") : "";
	String calories = request.getParameter("calories") != null ? request.getParameter("calories") : "";
	String operationMode = request.getParameter("opMode") != null ? request.getParameter("opMode") : "";

	//If user clicked Save
	if(operationMode.equals("save")){
		long profId = -1;
		if(noRecord){
			profId = addPlanAction.addMacroNutrientPlanByStr(Long.toString(targetMID), protein, fat, carbs, calories);
			addProfileAction.addMacroNutrientPlanByStr(gender, age, weight, height, goal, activityLevel, "" + profId);
		}else{
			editProfileAction.editMacroNutrientProfileByStr(profileBean.getRowID(), gender, age, weight, height, goal, activityLevel, ""+planBean.getRowID());
			editPlanAction.editMacroNutrientPlanByStr(planBean.getRowID(), Long.toString(targetMID), protein, fat, carbs, calories);
		}
		//Used when user does not have a record and saves for the first time
		long IDForUpdate = profId > -1?profId : planBean.getRowID();
		//Update profileBean to view newly submitted values
		profileBean = viewProfileAction.getMacroNutrientProfileListByPlanID(IDForUpdate).get(0);
		//User now has a record
		noRecord = false;
	}
	
%>
<script src="/iTrust/js/Chart.js"></script>
	
<table style="border:none" align="center">
	<tr>
		<td>
			<form action="<%=request.getRequestURI() %>" method="POST" id="macroForm" name="macroForm" align="left">
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
									<tr><td><input onclick="radioChange();" type="radio" id="male" name="gender" value="male" <%=profileBean!=null && profileBean.getGender().toString().equalsIgnoreCase("male") ? "checked" : ""%>> Male</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" id="female" name="gender" value="female" <%=profileBean != null&&profileBean.getGender().toString().equalsIgnoreCase("female") ? "checked" : ""%>> Female</td></tr>
								</table>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Age:</td>
							<td><input name="age"
								value="<%=profileBean != null ? profileBean.getAge() : ""%>"
								id="age"
								type="text" placeholder="Age"
								style="width:50px">
							<div id="errorTextAge" style="display:none; color:red">Hello</div>
							</td>
						<tr>
						<tr align="left">
							<td class="subHeaderVertical">Weight:</td>
							<td><input name="weight"
								value="<%=profileBean != null ? profileBean.getWeight() : ""%>"
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
								value="<%=profileBean != null ? profileBean.getHeight() : ""%>"
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
									<tr><td><input onclick="radioChange();" type="radio" name="mode" id="losemode" value="lose_weight" <%=profileBean != null&&profileBean.getGoal().toString().equals("lose_weight") ? "checked" : ""%>> Lose weight</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="mode" id="maintainmode" value="maintain_weight" <%=profileBean != null&&profileBean.getGoal().toString().equals("maintain_weight") ? "checked" : ""%>> Maintain weight</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="mode" id="gainmode" value="gain_weight" <%=profileBean != null&&profileBean.getGoal().toString().equals("gain_weight") ? "checked" : ""%>> Gain weight</td></tr>
								</table>
							</td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Activity: </td>
							<td>	
								<table border=0>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="sedentary" value="sedentary" <%=profileBean != null&&profileBean.getAct().toString().equals("sedentary") ? "checked" : ""%>> Sedentary</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="light" value="lightly_active" <%=profileBean != null&&profileBean.getAct().toString().equals("lightly_active") ? "checked" : ""%>> Lightly active</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="moderate" value="moderately_active" <%=profileBean != null&&profileBean.getAct().toString().equals("moderately_active") ? "checked" : ""%>> Moderately active</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="high" value="very_active" <%=profileBean != null&&profileBean.getAct().toString().equals("very_active") ? "checked" : ""%>> Very Active</td></tr>
									<tr><td><input onclick="radioChange();" type="radio" name="activityLevel" id="extreme" value="extremely_active" <%=profileBean != null&&profileBean.getAct().toString().equals("extremely_active") ? "checked" : ""%>> Extremely active</td></tr>
								</table>
							</td>
						</tr>
					</table>
					<input id="protein" name="protein" style="display: none" value="">
					<input id="fat" name="fat" style="display: none" value="">
					<input id="carbs" name="carbs" style="display: none" value="">
					<input id="calories" name="calories" style="display: none" value="">
					<input type="submit" id="submitForm" style="display:none" value="">
					<input id="opMode" name="opMode" style="display: none" value="">
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
<script language="JavaScript">
function preGraph(){
	<%
	if(!noRecord) %>graphIt();<%
	%>
	
}
</script>
