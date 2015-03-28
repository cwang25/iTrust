<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.beans.HealthRecord"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.SuggestionBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.action.SuggestionAction"%>
<%@page import="edu.ncsu.csc.itrust.action.GetFoodDiaryLabelAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean"%>

<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - View Patient Food Diary";
%>

<%@include file="/header.jsp" %>
<itrust:patientNav thisTitle="View Patient Food Diary" />
<%
	// Require a Patient ID first
	String pidString = (String)session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewPatientFoodDiary.jsp");
	   	return;
	}
	ViewFoodDiaryAction action = new ViewFoodDiaryAction(prodDAO, Long.toString(loggedInMID));
	GetFoodDiaryLabelAction labelAction = new GetFoodDiaryLabelAction(prodDAO, loggedInMID);
	SuggestionAction suggAction = new SuggestionAction(prodDAO, loggedInMID);
	SuggestionAction suggPatientAction = new SuggestionAction(prodDAO, Long.parseLong(pidString));
	List<FoodDiaryBean> foodDiaryList = action.getFoodDiaryListByOwnerID(Long.parseLong(pidString));
	session.setAttribute("foodDiaryList", foodDiaryList);
	boolean needToAddSuggestion = (request.getParameter("addNewSuggestion") != null && request.getParameter("addNewSuggestion").equals("true"));
	if (needToAddSuggestion) {
		String date = request.getParameter("date");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    java.util.Date d = sdf.parse(date);  
		String suggTxt = request.getParameter("suggestionText");
		
		if (suggTxt.length() < 1) {
			%>
			<p align="center"style="font-size: 16pt; font-weight: bold;" >Suggestion cannot be empty.</p>
			<%
		} else {
			SuggestionBean newSugg = new SuggestionBean(d, Long.parseLong(pidString), loggedInMID, suggTxt, "true");
			suggAction.addSuggestion(newSugg);
			%>
			<p align="center"style="font-size: 16pt; font-weight: bold;" >Your Suggestion has been added to the patient's Food Diary.</p>
			<%
		}
	}

	if (foodDiaryList != null && foodDiaryList.size() > 0) {
%>	

	<div style="margin-left: 5px;">
	</br>
	<table class="fTable" border=1 align="center">
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

			<%=row+""+((index % 2 == 1)?" class=\"alt\"":"")+">"%>
			<%
				//SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY");
			if(recordFlag.length() > 0 && !sdf.format(b.getDate()).equals(recordFlag)){
				needDailySummary = true;
			}
						
						
			if(needDailySummary){
				FoodDiaryLabelSetBean labelBean = labelAction.getSetFoodDiaryLabel(oldBean.getOwnerID(), new Date(oldBean.getDate().getTime()));
				String label = "";
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				if(labelBean != null)
					label = labelBean.getLabel();
			%>
			<tr>
				<td>
					<%=StringEscapeUtils.escapeHtml("[Daily Summary]")%>
					<button id="toggle<%=index%>" style="border:none; background-color:Transparent"><img id="img<%=index%>" src="/iTrust/image/icons/addSuggestionPlus.png" height="20" width="20"></button>
					<span style="<%= label.length() > 0 ? "border-radius:5px; padding:3px; color:white; background-color:red;" : "" %>"><%=StringEscapeUtils.escapeHtml(label)%></span>
				</td>
				<script language="JavaScript">
				$(document).ready(function(){
					$("#toggle<%=index%>").click(
					function(){
						$("#suggestion<%=index%>").toggle();
					});
				}); 
				</script>
				<td></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfFat())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getMilligramsOfSodium())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfCarbs())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfSugar())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfFiber())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.getGramsOfProtein())%></td>
				<td><%=StringEscapeUtils.escapeHtml("" + dailyTotalCalories)%></td>
			</tr>
			<tr id="suggestion<%=index%>" style="display: none;">
			<form action="viewPatientFoodDiary.jsp"> 
				<td>New Suggestion:</td>
				<td colspan="4">
					<textarea rows="4" cols="50" name="suggestionText" id="suggestionText"></textarea>
					<input name="addNewSuggestion" value="true" type ="hidden" ></input>
					<input name="date" value="<%=sdf.format(oldBean.getDate())%>" type ="hidden" ></input>
				</td>
				<td colspan="2"><button type="submit" id="addNewSuggestion">Submit Suggestion</button></td>
				<%
					String suggestionList = "";
					List<SuggestionBean> suggestionsToShow = suggPatientAction.getSuggestionsByDate(new java.sql.Date(oldBean.getDate().getTime()), Long.parseLong(pidString));
					
					if(suggestionsToShow.size() != 0){
						int suggestionNum = 1;
						for(SuggestionBean sBean : suggestionsToShow){
							suggestionList += "" + suggestionNum + ". " + sBean.getSuggestion() + "\n";
							suggPatientAction.editSuggestion(sBean);
							suggestionNum++;
						}
					}else{
						suggestionList += "No suggestions";
					}
					
				%>
				<td>Patient's Suggestions:</td>
				<td colspan="4"><textarea id="tarea<%=index%>" rows="4" cols="50" readonly><%=StringEscapeUtils.escapeHtml(suggestionList)%></textarea>
				</td>
			</form>
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
			oldBean = b;
			%>
				<td><%= StringEscapeUtils.escapeHtml("" + sdf.format(b.getDate()))%></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getTypeOfMeal().toString()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getNameOfFood()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getNumberOfServings()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getCaloriesPerServing()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getGramsOfFat()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getMilligramsOfSodium()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getGramsOfCarbs()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getGramsOfSugar()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getGramsOfFiber()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.getGramsOfProtein()) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + b.totalCalories()) %></td>
				</tr>
			</tr>
	<%		index ++; %>
	<%	} 
		FoodDiaryLabelSetBean labelBean = labelAction.getSetFoodDiaryLabel(oldBean.getOwnerID(), new Date(oldBean.getDate().getTime()));
		String label = "";
		if(labelBean != null)
			label = labelBean.getLabel();
	%>
			<tr>
				<td>
				<%=StringEscapeUtils.escapeHtml("[Daily Summary]")%>
				<button id="toggle<%=index%>" style="border:none; background-color:Transparent"><img src="/iTrust/image/icons/addSuggestionPlus.png" height="20" width="20"></button>
				<span style="<%= label.length() > 0 ? "border-radius:5px; padding:3px; color:white; background-color:red;" : "" %>"><%=StringEscapeUtils.escapeHtml(label)%></span>
				</td>
				<script language="JavaScript">
				$(document).ready(function(){
					$("#toggle<%=index%>").click(
					function(){
						$("#suggestion<%=index%>").toggle();
					});
				}); 
				</script>
				<td></td>
				<td><%=StringEscapeUtils.escapeHtml("")%></td>
				<td></td>
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
			<tr id="suggestion<%=index%>" style="display: none;"> 
				<form action="viewPatientFoodDiary.jsp?"> 
				<td>New Suggestion:</td>
				<td colspan="4">
					<textarea rows="4" cols="50" name="suggestionText" id="suggestionText2"></textarea>
					<input name="addNewSuggestion" value="true" type ="hidden" ></input>
					<input name="date" value="<%=sdf.format(oldBean.getDate())%>" type ="hidden" ></input>
				</td>
				<td colspan="2"><button type="submit" name="submitMe">Submit Suggestion</button></td>
				<%
					String suggestionList = "";
					List<SuggestionBean> suggestionsToShow = suggPatientAction.getSuggestionsByDate(new java.sql.Date(oldBean.getDate().getTime()), Long.parseLong(pidString));
					
					if(suggestionsToShow.size() != 0){
						int suggestionNum = 1;
						for(SuggestionBean sBean : suggestionsToShow){
							suggestionList += "" + suggestionNum + ". " + sBean.getSuggestion() + "\n";
							suggPatientAction.editSuggestion(sBean);
							suggestionNum++;
						}
					}else{
						suggestionList += "No suggestions";
					}
					
				%>
				<td>Patient's Suggestions:</td>
				<td colspan="4"><textarea id="tarea<%=index%>" rows="4" cols="50" readonly><%=StringEscapeUtils.escapeHtml(suggestionList)%></textarea>
				</td>
			</form>
			</tr>
			</tr>
	</table>
	
	
	</div>
<%	} else { 
		if(action.isNutritionist() && foodDiaryList != null){ %>
	<div>
		<center>The patient has no Food diary</center>
	</div>
<%		}else{%>
	<div>
		<center>1. This function is only available to Nutritionist specialty HCP.</center>
		<center>2. Patient has to set you as his/her Designated Nutritionist in order to view it.</center>
	</div>
	<%  } %>
<%	} %>	
	<br />
</div>

<%@include file="/footer.jsp" %>

