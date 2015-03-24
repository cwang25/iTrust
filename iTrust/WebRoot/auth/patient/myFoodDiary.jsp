<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.lang.Long"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean"%>
<%@page import="edu.ncsu.csc.itrust.action.GetFoodDiaryLabelAction"%>
<%@page import="edu.ncsu.csc.itrust.action.AddFoodDiaryLabelAction"%>
<%@page import="edu.ncsu.csc.itrust.action.SetFoodDiaryLabelAction"%>
<%@page import="edu.ncsu.csc.itrust.action.RemoveFoodDiaryLabelAction"%>
<%@page import="java.util.Calendar"%>
<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View My Food Diary";
%>

<%@include file="/header.jsp"%>

<div align=center>
	<h2>My Food Diary</h2>
	<%
		//The fields to create foodDiary bean if user add new food diary.
		AddFoodDiaryAction addAction = new AddFoodDiaryAction(prodDAO, loggedInMID.toString());
		EditFoodDiaryAction editAction = new EditFoodDiaryAction(prodDAO, loggedInMID.toString());
		GetFoodDiaryLabelAction labelGetAction = new GetFoodDiaryLabelAction(prodDAO, loggedInMID);
		AddFoodDiaryLabelAction labelAddAction = new AddFoodDiaryLabelAction(prodDAO, loggedInMID);
		SetFoodDiaryLabelAction labelSetAction = new SetFoodDiaryLabelAction(prodDAO, loggedInMID);
		RemoveFoodDiaryLabelAction labelRemoveAction = new RemoveFoodDiaryLabelAction(prodDAO, loggedInMID);
		SuggestionAction suggestionAction = new SuggestionAction(prodDAO, loggedInMID);
		
		boolean dataAllCorrect = true;
	  	String dateStr = request.getParameter("date") != null ? request.getParameter("date") : "";
	 	String typeOfMeal = request.getParameter("typeOfMeal") != null ? request.getParameter("typeOfMeal") : "";
	 	String nameOfFood = request.getParameter("nameOfFood") != null ? request.getParameter("nameOfFood") : "";
		String numberOfServings = request.getParameter("numberOfServings") != null ? request.getParameter("numberOfServings") : "";
		String caloriesPerServing = request.getParameter("caloriesPerServing")!= null ? request.getParameter("caloriesPerServing") : "";
		String gramsOfFat = request.getParameter("gramsOfFat")!= null ? request.getParameter("gramsOfFat") : "";
		String milligramsOfSodium = request.getParameter("milligramsOfSodium")!= null ? request.getParameter("milligramsOfSodium") : "";
		String gramsOfCarbs = request.getParameter("gramsOfCarbs")!= null ? request.getParameter("gramsOfCarbs") : "";
		String gramsOfSugar = request.getParameter("gramsOfSugar")!= null ? request.getParameter("gramsOfSugar") : "";
		String gramsOfFiber = request.getParameter("gramsOfFiber")!= null ? request.getParameter("gramsOfFiber") : "";
		String gramsOfProtein = request.getParameter("gramsOfProtein")!= null ? request.getParameter("gramsOfProtein") : ""; 
		String newLabelName = request.getParameter("newLabelName") != null ? request.getParameter("newLabelName") : "";
		String labelDate = request.getParameter("labelDate") != null ? request.getParameter("labelDate") : "";
		String changedLabelName = request.getParameter("changedLabelName") != null ? request.getParameter("changedLabelName") : "";
	 	String mode = request.getParameter("operationMode");
	 	String selectedIndex = request.getParameter("selectedIndex");
	 	long selectedRowID = request.getParameter("row_id_to_edit")!=null?Long.parseLong(request.getParameter("row_id_to_edit")):-1;
	 	boolean toEdit = mode!=null&&mode.equals("to_edit");
	 	boolean toDelete = mode!=null&&mode.equals("delete");
	 	boolean undo = mode!=null&&mode.equals("undo");
	 	boolean addLabel = mode!=null&&mode.equals("addLabel");
	 	boolean setLabel = mode!=null&&mode.equals("setLabel");
	 	//out.println(mode);
/*  	String dateStr = "";
		String typeOfMeal = "";
		String nameOfFood = "";
		String numberOfServings = "";
		String caloriesPerServing = "";
		String gramsOfFat = "";
		String milligramsOfSodium = "";
		String gramsOfCarbs = "";
		String gramsOfSugar = "";
		String gramsOfFiber = "";
		String gramsOfProtein = "";  
	 */	if( ((dateStr != null && dateStr.length() > 0) || 
			(typeOfMeal != null && typeOfMeal.length() > 0) || 
			(nameOfFood != null && nameOfFood.length() > 0) ||
			(numberOfServings != null && numberOfServings.length() > 0) ||
			(caloriesPerServing != null && caloriesPerServing.length() > 0) || 
			(gramsOfFat != null && gramsOfFat.length() > 0) ||
			(milligramsOfSodium!=null && milligramsOfSodium.length() > 0) ||
			(gramsOfCarbs!=null&&gramsOfCarbs.length() > 0) ||
			(gramsOfSugar!=null&&gramsOfSugar.length() > 0) || 
			(gramsOfFiber!=null&&gramsOfFiber.length() > 0) || 
			(gramsOfProtein!=null&&gramsOfProtein.length() > 0)) &&
			(mode!=null&&!mode.equals("to_edit"))&&
			(mode!=null&&!mode.equals("delete"))&&
			(mode!=null&&!mode.equals("undo"))){
			try {
				if(mode.equals("edit")){
					if(selectedIndex != null){
						editAction.editStrFoodDiary(selectedRowID, dateStr, typeOfMeal, nameOfFood, numberOfServings, caloriesPerServing, gramsOfFat, milligramsOfSodium, gramsOfCarbs, gramsOfSugar, gramsOfFiber, gramsOfProtein);
						%>
						<p align="center"style="font-size: 16pt; font-weight: bold;" >Your Food Diary has been updated.</p>
						<%
					}
				}else{
					addAction.addStrFoodDiary(dateStr, typeOfMeal, nameOfFood, numberOfServings, caloriesPerServing, gramsOfFat, milligramsOfSodium, gramsOfCarbs, gramsOfSugar, gramsOfFiber, gramsOfProtein);
				}
				selectedRowID = -1;
				selectedIndex = null;
				dateStr = null;
				typeOfMeal = null;	
				nameOfFood = null;
				numberOfServings = null;
				caloriesPerServing = null;
				gramsOfFat = null;
				milligramsOfSodium = null;
				gramsOfCarbs = null;
				gramsOfSugar = null;
				gramsOfFiber = null;
				gramsOfProtein = null;
			} catch (FormValidationException e){
				String errorStr = "";
				mode = "to_edit";
				for(String tempS : e.getErrorList()) errorStr += tempS;
	%>
	<span class="iTrustError"><%=errorStr%></span>
	<%
				dataAllCorrect = false;
			}
		}else if(mode!=null&&mode.equals("to_edit")){
			if(selectedIndex != null){
				int index = Integer.parseInt(selectedIndex);
				List<FoodDiaryBean> foodDiaryList = (List<FoodDiaryBean>)session.getAttribute("foodDiaryList");
				FoodDiaryBean b = foodDiaryList.get(index);
				selectedRowID = b.getRowID();
				SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY");
				dateStr = "" + sdf.format(b.getDate());
				typeOfMeal = "" + b.getTypeOfMeal().toString();	
				nameOfFood = "" + b.getNameOfFood();
				numberOfServings = "" + b.getNumberOfServings();
				caloriesPerServing = "" + b.getCaloriesPerServing();
				gramsOfFat = "" + b.getGramsOfFat();
				milligramsOfSodium = "" + b.getMilligramsOfSodium();
				gramsOfCarbs = "" + b.getGramsOfCarbs();
				gramsOfSugar = "" + b.getGramsOfSugar();
				gramsOfFiber = "" + b.getGramsOfFiber();
				gramsOfProtein = "" + b.getGramsOfProtein();
			}
		}else if(toDelete){
			if(selectedIndex != null){
				int index = Integer.parseInt(selectedIndex);
				List<FoodDiaryBean> foodDiaryList = (List<FoodDiaryBean>)session.getAttribute("foodDiaryList");
				FoodDiaryBean beanToDelete = foodDiaryList.get(index);
				FoodDiaryBean deletedBean = editAction.deleteFoodDiary(beanToDelete);
				if(deletedBean != null){
					session.setAttribute("deletedFoodDiary", deletedBean);
					%>
					<p align="center"style="font-size: 16pt; font-weight: bold;" >Your Food Diary has been deleted.  <button name="undoBtn" style="margin: 2px;" type='button' onclick="undo()" style="width:100;height:100"> Undo</button></p>
					<%
					
				}
				
			}
		}else if(undo){
			FoodDiaryBean b = (FoodDiaryBean) session.getAttribute("deletedFoodDiary");
			addAction.addFoodDiary(b);
		} else if(addLabel) {
			try {
				FoodDiaryLabelBean b = new FoodDiaryLabelBean(loggedInMID, newLabelName);
				labelAddAction.addFoodDiaryLabel(b);
				%>
				<p align="center"style="font-size: 16pt; font-weight: bold;" >Label has been added.</p>
				<%
			} catch (FormValidationException e) {
				%>
				<span class="iTrustError">Label name may only contain letters and numbers.</span>
				<%
				dataAllCorrect = false;
			}
		} else if(setLabel) {
			FoodDiaryLabelSetBean b = labelGetAction.getSetFoodDiaryLabel(loggedInMID, java.sql.Date.valueOf(labelDate));
			if(changedLabelName.equals("none") && b != null) {
				labelRemoveAction.removeFoodDiaryLabel(b);
			} else {
				if(b == null)
					b = new FoodDiaryLabelSetBean(loggedInMID, java.sql.Date.valueOf(labelDate), changedLabelName);
				b.setLabel(changedLabelName);
				labelSetAction.setFoodDiaryLabel(b);
			}
			%>
			<p align="center"style="font-size: 16pt; font-weight: bold;" >Label has been set.</p>
			<%
		}

		
		//-----created food diary list---
	 	ViewFoodDiaryAction action = new ViewFoodDiaryAction(prodDAO, loggedInMID.toString());
	 	List<FoodDiaryBean> foodDiaryList = action.getFoodDiaryListByOwnerID(loggedInMID);
	 	session.setAttribute("foodDiaryList", foodDiaryList);
		
		if (foodDiaryList.size() > 0) {
	%>
	<div style="margin-left: 5px;">
		<table id="fTable" class="fTable" border=1 align="center">
			<tr>
				<th>Date</th>
				<th>Type of meal</th>
				<th>Name of food</th>
				<th>Number of servings</th>
				<th>Calories per serving</th>
				<th>Grams of fat</th>
				<th>Milligrams of sodium</th>
				<th>Grams of carbs</th>
				<th>Grams of sugar</th>
				<th>Grams of fiber</th>
				<th>Grams of protein</th>
				<th>Total calories</th>
				<th>Option</th>
			</tr>
			<%
				int index = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY");
				String recordFlag = "";
				//Use to keep on track of daily total nutritions info.
				FoodDiaryBean totalBeanTmp = new FoodDiaryBean();
				//Use to keep on daily total calories.
				double dailyTotalCalories = 0;
				FoodDiaryBean oldBean = null; // keeps track of the previous bean
				for(FoodDiaryBean b : foodDiaryList) { 		
					boolean needDailySummary = false;
					String row = "<tr";
			%>

				<%=row+""+((index%2 == 1)?" class=\"alt\"":"")+">"%>
			<%
				//SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY");
						if(recordFlag.length() > 0 && !sdf.format(b.getDate()).equals(recordFlag)){
							needDailySummary = true;
						}
						
						
						if(needDailySummary){
							FoodDiaryLabelSetBean labelBean = labelGetAction.getSetFoodDiaryLabel(oldBean.getOwnerID(), new java.sql.Date(oldBean.getDate().getTime()));
							String label = "";
							if(labelBean != null)
								label = labelBean.getLabel();
			%>
			<tr>
				<td><%=StringEscapeUtils.escapeHtml("[Daily Summary]")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfFat())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getMilligramsOfSodium())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfCarbs())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfSugar())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfFiber())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfProtein())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + dailyTotalCalories)%></td>
				<td>
					<span style="color:red"><%=StringEscapeUtils.escapeHtml(label)%></span>
					<select value="<%=label %>">
						<option value="none">none</option>
						<%
							List<FoodDiaryLabelBean> labelList = labelGetAction.getAllFoodDiaryLabels(loggedInMID);
							for(FoodDiaryLabelBean lb : labelList) {
								String labelText = StringEscapeUtils.escapeHtml(lb.getLabel());
								%><option value="<%=labelText %>"><%=labelText %></option><%	
							}								
						%>
					</select>
					<button class="changeLabelBtn" data-date="<%=(new java.sql.Date(oldBean.getDate().getTime())).toString() %>">Change Label</button>
				</td>
			</tr>
			<%
				totalBeanTmp = new FoodDiaryBean();
							dailyTotalCalories = 0;
						}
						
						totalBeanTmp.setGramsOfFat(totalBeanTmp.getGramsOfFat() + b.getGramsOfFat());
						totalBeanTmp.setMilligramsOfSodium(totalBeanTmp.getMilligramsOfSodium() + b.getMilligramsOfSodium());
						totalBeanTmp.setGramsOfCarbs(totalBeanTmp.getGramsOfCarbs() + b.getGramsOfCarbs());
						totalBeanTmp.setGramsOfSugar(totalBeanTmp.getGramsOfSugar() + b.getGramsOfSugar());
						totalBeanTmp.setGramsOfFiber(totalBeanTmp.getGramsOfFiber() + b.getGramsOfFiber());
						totalBeanTmp.setGramsOfProtein(totalBeanTmp.getGramsOfProtein() + b.getGramsOfProtein());
						dailyTotalCalories += b.totalCalories();
						recordFlag = sdf.format(b.getDate());
			%>
			<td><%=StringEscapeUtils.escapeHtml("" + sdf.format(b.getDate()))%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getTypeOfMeal().toString())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getNameOfFood())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getNumberOfServings())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getCaloriesPerServing())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getGramsOfFat())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getMilligramsOfSodium())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getGramsOfCarbs())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getGramsOfSugar())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getGramsOfFiber())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.getGramsOfProtein())%></td>
			<td><%=StringEscapeUtils.escapeHtml("" + b.totalCalories())%></td>
			<td>
			<button name="editBtn" style="margin: 2px;" type='button' onclick="showHiddenEditFoodDiaryForm('HiddenForm', <%=index %>)" style="width:100;height:100">
			Edit</button>
			
			<button name="deleteBtn" style="margin: 2px;" type='button' onclick="runDeleteRecord(<%=index %>)" style="width:100;height:100">
			Delete</button>
			</td>
			</tr>
			<%
				oldBean = b;
				index ++;
			%>
			<%
				}
				
				FoodDiaryLabelSetBean labelBean = labelGetAction.getSetFoodDiaryLabel(oldBean.getOwnerID(), new java.sql.Date(oldBean.getDate().getTime()));
				String label = "";
				if(labelBean != null)
					label = labelBean.getLabel();
			%>

			<tr>
				<td><%=StringEscapeUtils.escapeHtml("[Daily Summary]")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml(""
						+ totalBeanTmp.getGramsOfFat())%></td>
				<td><%=StringEscapeUtils.escapeHtml(""
						+ totalBeanTmp.getMilligramsOfSodium())%></td>
				<td><%=StringEscapeUtils.escapeHtml(""
						+ totalBeanTmp.getGramsOfCarbs())%></td>
				<td><%=StringEscapeUtils.escapeHtml(""
						+ totalBeanTmp.getGramsOfSugar())%></td>
				<td><%=StringEscapeUtils.escapeHtml(""
						+ totalBeanTmp.getGramsOfFiber())%></td>
				<td><%=StringEscapeUtils.escapeHtml(""
						+ totalBeanTmp.getGramsOfProtein())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + dailyTotalCalories)%></td>
				<td>
					<span style="color:red"><%=StringEscapeUtils.escapeHtml(label)%></span>
					<select value="<%=label %>">
						<option value="none">none</option>
						<%
							List<FoodDiaryLabelBean> labelList = labelGetAction.getAllFoodDiaryLabels(loggedInMID);
							for(FoodDiaryLabelBean lb : labelList) {
								String labelText = StringEscapeUtils.escapeHtml(lb.getLabel());
								%><option value="<%=labelText %>"><%=labelText %></option><%	
							}								
						%>
					</select>
					<button class="changeLabelBtn" data-date="<%=(new java.sql.Date(oldBean.getDate().getTime())).toString() %>">Change Label</button>
				</td>
			</tr>
		</table>


	</div>
	<%
		} else {
	%>
	<div>
		<i>You have no Food diary record.</i>
	</div>
	<%
		}
	%>
	<div>
		</br>
		<button type='button' onclick="showHiddenAddNewFoodDiaryForm('HiddenForm');$('#newLabelForm').hide();">Add new food diary</button>
		<button type='button' onclick="hideHiddenForm('HiddenForm');$('#newLabelForm').show();">Create new label</button>
	</div>	
	<br />
	<div id="HiddenForm" name="Hiddenform"
		style="display: <%=!dataAllCorrect&&toEdit? "block" : "none"%>">
		<form action="myFoodDiary.jsp" method="post" id="edit" align="center">
			<td valign=top>
				<div class="row">
					<table class="fTable" align="center">
						<tr>
							<th id="form_top_banner" colspan=1><%=request.getParameter("form_top_banner_tag")!=null?request.getParameter("form_top_banner_tag"):"New Food Diary" %></th>
							<th>
							<input type="button" name="cancel" style="color: black;font-size: 16pt; font-weight: bold; float: right;"
						value="Cancel" onclick="hideHiddenForm('HiddenForm')" align="left">
							</th>
							
						
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Date:</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY");
								Date today = new Date();
							%>
							<td><input name="date"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? sdf.format(today) : dateStr)%>"
								type="text" placeholder="mm/dd/yyyy"> <input
								type="button" value="Select Date"
								onclick="displayDatePicker('date');"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Type of meal:</td>
							<td><select id="typeOfMeal" name="typeOfMeal">
									<option value="BREAKFAST"
										<%if(typeOfMeal!= null && typeOfMeal.equals("BREAKFAST"))%>
										selected <%;%>>Breakfast</option>
									<option value="LUNCH"
										<%if(typeOfMeal!= null && typeOfMeal.equals("LUNCH"))%>
										selected <%;%>>Lunch</option>
									<option value="DINNER"
										<%if(typeOfMeal!= null && typeOfMeal.equals("DINNER"))%>
										selected <%;%>>Dinner</option>
									<option value="SNACK"
										<%if(typeOfMeal!= null && typeOfMeal.equals("SNACK"))%>
										selected <%;%>>Snack</option>
							</select></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Name of food:</td>
							<td><input name="nameOfFood" id="nameOfFood"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : nameOfFood)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Number of servings:</td>
							<td><input name="numberOfServings" id="numberOfServings"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : numberOfServings)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Calories per serving:</td>
							<td><input name="caloriesPerServing" id="caloriesPerServing"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : caloriesPerServing)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Grams of fat:</td>
							<td><input name="gramsOfFat" id="gramsOfFat"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : gramsOfFat)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Milligrams of sodium:</td>
							<td><input name="milligramsOfSodium" id="milligramsOfSodium"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : milligramsOfSodium)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Grams of carbs:</td>
							<td><input name="gramsOfCarbs" id="gramsOfCarbs"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : gramsOfCarbs)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Grams of sugar:</td>
							<td><input name="gramsOfSugar" id="gramsOfSugar"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : gramsOfSugar)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Grams of fiber:</td>
							<td><input name="gramsOfFiber" id="gramsOfFiber"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : gramsOfFiber)%>"
								type="text"></td>
						</tr>
						<tr align="left">
							<td class="subHeaderVertical">Grams of protein:</td>
							<td><input name="gramsOfProtein" id="gramsOfProtein"
								value="<%=StringEscapeUtils.escapeHtml(dataAllCorrect&&!toEdit ? "" : gramsOfProtein)%>"
								type="text"></td>
						</tr>
					</table>
					<br />
					<input type="submit" id="saveBtn" name="action"
						style="font-size: 16pt; font-weight: bold;"
						value="Save">
			</td>
			<div id="HiddenHelperFields">
			<!-- operationMode: none, to_edit, edit, new, delete -->
				<input name="form_top_banner_tag" id="form_top_banner_tag" value="New Food Diary"type="hidden">
				<input name="operationMode" id="operationMode" value = <%=(mode!=null &&mode.equals("to_edit"))?"edit":"none"%> type="hidden">
				<input name="selectedIndex" id="selectedIndex" value=<%=selectedIndex!=null?selectedIndex:-1 %> type="hidden">
				<input name="row_id_to_edit" id="row_id_to_edit" value=<%=selectedRowID > -1 ? selectedRowID :-1 %> type="hidden">
			</div>
		</form>
	</div>
	<br/>
</div>

<div id="newLabelForm" name="newLabelForm" style="display: <%=!dataAllCorrect&&addLabel? "block" : "none"%>">
	<form action="myFoodDiary.jsp" method="post" id="newLabel" align="center">
		<table class="fTable" align="center">
			<tr>
				<th>New Label</th>
				<th><input type="button" style="color: black;font-size: 16pt; font-weight: bold; float: right;" value="Cancel" onclick="$('#newLabelForm').hide();"></th>
			</tr>
			<tr>
				<td class="subHeaderVertical">Label Name:</td>
				<td><input type="text" name="newLabelName" id="newLabelName" value="<%=StringEscapeUtils.escapeHtml(!dataAllCorrect&&addLabel ? newLabelName : "")%>"></td>
			</tr>
		</table>
		<br/>
		<input name="operationMode" value="addLabel" type="hidden">
	
		<input type="submit" id="saveBtn" name="action"	style="font-size: 16pt; font-weight: bold;"	value="Save">
	</form>
</div>

<div id="hiddenLabelChangeForm" name="hiddenLabelChangeForm" style="display:none;">
	<form action="myFoodDiary.jsp" method="post" id="hiddenLabelChange" align="center">
		<input type="hidden" id="labelDate" name="labelDate">
		<input type="hidden" id="changedLabelName" name="changedLabelName">
		<input type="hidden" name="operationMode" value="setLabel">
		<input type="submit" class="hiddenSubmitBtn">
	</form>
</div>

<script language="JavaScript">
	function showHiddenForm(divID) {
		document.getElementById(divID).style.display = "block";
	}
	function showHiddenAddNewFoodDiaryForm(divID){
		document.getElementById("form_top_banner_tag").value = "New Food Diary";
		document.getElementById("form_top_banner").innerHTML = "New Food Diary";
		document.getElementById("operationMode").value = "new";
		document.getElementById("selectedIndex").value = "-1";
		document.getElementById("row_id_to_edit").value = "-1";
		document.getElementById("nameOfFood").value = "";
		document.getElementById("numberOfServings").value = "";
		document.getElementById("caloriesPerServing").value = "";
		document.getElementById("gramsOfFat").value = "";
		document.getElementById("milligramsOfSodium").value = "";
		document.getElementById("gramsOfCarbs").value = "";
		document.getElementById("gramsOfSugar").value = "";
		document.getElementById("gramsOfFiber").value = "";
		document.getElementById("gramsOfProtein").value = "";

		
		showHiddenForm(divID);		
	}
	function showHiddenEditFoodDiaryForm(divID, index){
		document.getElementById("operationMode").value = "to_edit";
		document.getElementById("selectedIndex").value = ""+index;
		//alert(index);
		document.getElementById("form_top_banner_tag").value = "Edit Food Diary";
		document.getElementById("saveBtn").click();
	}
	function hideHiddenForm(divID){
		document.getElementById(divID).style.display = "none";
	}
	function runDeleteRecord(index){
		if(confirm('Are you sure you want to delete the food diary record?')){
			document.getElementById("operationMode").value = "delete";
			document.getElementById("selectedIndex").value = ""+index;
			
			document.getElementById("saveBtn").click();

		}
	}
	function undo(){
		document.getElementById("operationMode").value = "undo";
		document.getElementById("saveBtn").click();
	}
	
	$('.changeLabelBtn').click(function() {
		var me = $(this);
		var form = $('#hiddenLabelChangeForm');
		form.find('#labelDate').val(me.attr('data-date'));
		form.find('#changedLabelName').val(me.siblings('select').val());
		form.find('.hiddenSubmitBtn').click();
	});
/* 	function scrollToBottom() {
		var objDiv = document.getElementById("bottomLine");
		objDiv.scrollTop = objDiv.scrollHeight;
	}; */
	
</script>
<%@include file="/footer.jsp"%>
