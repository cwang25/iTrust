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
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryDailySummaryBean" %>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean"%>

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
	List<FoodDiaryDailySummaryBean> foodDiaryDailySummaryList = action.getFoodDiaryDailySummaryListByOwnerID(Long.parseLong(pidString));
	session.setAttribute("foodDiaryList", foodDiaryList);
	
	SimpleDateFormat diaryDateFormat = new SimpleDateFormat("MM/dd/yyyy");
 	String mode = request.getParameter("operationMode");
	boolean needToAddSuggestion = (request.getParameter("addNewSuggestion") != null && request.getParameter("addNewSuggestion").equals("true"));
 	boolean showCompareGraph = mode!=null&&mode.equals("showCompareGraph");
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
		foodDiaryList.add(null);
%>	
<div id="HiddenHelperFields">
<!-- operationMode: allow jsp to know what to do for different mode-->
<input name="operationMode" id="operationMode" value = <%=(mode!=null &&mode.equals("to_edit"))?"edit":"none"%> type="hidden">
</div>
	<div align="center">
		<span>Filter entries by label: </span>
		<select id="labelFilter" value="any">
			<option value="any">any</option>
			<%
				List<FoodDiaryLabelBean> labelsList = labelAction.getAllFoodDiaryLabels(Long.parseLong(pidString));
				for(FoodDiaryLabelBean lb : labelsList) {
					String labeltxt = StringEscapeUtils.escapeHtml(lb.getLabel());
					%><option value="<%=labeltxt %>"><%=labeltxt %></option><%	
				}								
			%>
		</select>
	</div>
	<br/>
	<div align="center">
		<span>Filter entries by date</span><br/>
		Filter by range: <input id="dateRangeCheckbox" type="checkbox"/><br/>
		<input type="text" placeholder="MM/dd/yyyy" id="dateRangeFrom"/>&nbsp;<input type="text" placeholder="MM/dd/yyyy" id="dateRangeTo" style="display:none;" /><br/>
		<input type="button" id="dateFilterSubmit" value="Filter"/><input type="button" id="dateFilterClear" value="Show All"/><br/>
		<span style="color:red;" id="dateFilterErrorMsg"></span>
	</div>
	<div style="margin-left: 5px;">
	</br>
	<table class="foodDiaryTable" border=1 align="center">
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
		//Use to keep on daily total calories.
		double dailyTotalCalories = 0;
		FoodDiaryBean oldBean = null; // keeps track of the previous bean
		boolean needDailySummary = false;
		int dailySummaryIndex = 0;
		String label1="";
		for(int i = 0 ; i < foodDiaryList.size() ; i++){
			FoodDiaryBean b = foodDiaryList.get(i);
			if(b != null){
				if(recordFlag.length() > 0 && !sdf.format(b.getDate()).equals(recordFlag)){
					needDailySummary = true;
				}
				recordFlag = sdf.format(b.getDate());
			}
			if(b==null|| needDailySummary){
				needDailySummary = false;
				FoodDiaryDailySummaryBean totalBeanTmp = foodDiaryDailySummaryList.get(dailySummaryIndex);
				dailySummaryIndex++;
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				FoodDiaryLabelSetBean labelBean = labelAction.getSetFoodDiaryLabel(oldBean.getOwnerID(), new Date(oldBean.getDate().getTime()));
				String label = "";
				if(labelBean != null)
					label = labelBean.getLabel();
			%>
			<tr class="diaryLabelRow <%= label %>" data-diarydate="<%= oldBean != null ? diaryDateFormat.format(oldBean.getDate()) : "" %>">
				<td>
					<b><%=StringEscapeUtils.escapeHtml("Daily Summary")%></b><br/>
					<button id="toggle<%=index%>" style="border:none; background-color:Transparent"><img id="img<%=index%>" src="/iTrust/image/icons/addSuggestionPlus.png" height="20" width="20"></button>
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
				<td><%=StringEscapeUtils.escapeHtml("" + totalBeanTmp.totalCalories())%></td>
				<td>
				<span style="<%= label.length() > 0 ? "border-radius:5px; padding:3px; color:black; background-color:yellow;font-weight: bold;" : "" %>"><%=StringEscapeUtils.escapeHtml(label)%></span>
				<%= label.length() > 0 ? "<br/><br/>" : "" %>
				<button style="margin-top: 5px" class="button" id="viewMacroNutrientGraph" onclick="switchHiddenForm('hiddenDailyGraph','showCompareGraph');setActualVal(<%=totalBeanTmp.getGramsOfProtein()%>,<%=totalBeanTmp.getGramsOfFat()%>,<%=totalBeanTmp.getGramsOfCarbs() %>,<%=totalBeanTmp.totalCalories()%>);toggleGraph('Pie');">View Graph</button> 
				</td>
			</tr>
			<tr id="suggestion<%=index%>" style="display: none;">
			<form action="viewPatientFoodDiary.jsp" method="POST"> 
				<td>New Suggestion:</td>
				<td colspan="4">
					<textarea rows="4" style="width: 100%; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box;resize: none;" name="suggestionText" id="suggestionText<%=index%>"></textarea>
					<input name="addNewSuggestion" value="true" type ="hidden" ></input>
					<input name="date" value="<%=sdf.format(oldBean.getDate())%>" type ="hidden" ></input>
				</td>
				<td colspan="2">
				<button type="submit" id="addNewSuggestion<%=index%>">Submit Suggestion</button>
				
				</td>
				<%
					String suggestionList = "";
					List<SuggestionBean> suggestionsToShow = suggPatientAction.getSuggestionsByDate(new java.sql.Date(oldBean.getDate().getTime()), Long.parseLong(pidString));
					if(suggestionsToShow.size() > 0){
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
				<td colspan="4">
				<%
				for(SuggestionBean bitem: suggestionsToShow){
					%>
					<textarea id="suggestionBeanText<%=bitem.getRowID()%>" style="display: none"><%=bitem.getSuggestion()%></textarea>
					<%
				}
				%>
				
				<button type="button" id="updateSuggestion<%=index%>"  style="color:gray;" disabled>Update</button>
				<script type="text/javascript">
					$("#updateSuggestion<%=index%>").click(function(){
						
						 $.post("FoodDiarySuggestionUpdateServlet",
						 {
							loggedInMID: <%=loggedInMID%>,
							suggestionRowID: document.getElementById("savedSuggestionList<%=index%>").value,
							newText: document.getElementById("tarea<%=index%>").value
						 },
						function(data, status){
							if(data === "Success"){
								alert("Your changes have been saved! :)");
						 		var nText = document.getElementById("tarea<%=index%>").value;
						 		console.log(nText);
						 		var rowID =  document.getElementById("savedSuggestionList<%=index%>").value;
						 		console.log(rowID);
						 		//update text record on client side
								document.getElementById("suggestionBeanText"+rowID.toString()).value = nText;
								//update list title
								document.getElementById("textTitleList"+rowID.toString()).text = nText.length > 25 ? nText.substring(0, 25) : nText;
								//Disable the update button
								$('#updateSuggestion<%=index%>').attr('disabled','disabled');
								$('#updateSuggestion<%=index %>').css('color', 'gray');
							}else{
								alert("Something went wrong :(");
							}
						});
						
					});
				</script>
				<select style="margin:5px" id="savedSuggestionList<%=index%>" onchange="$('#updateSuggestion<%=index%>').attr('disabled','disabled');$('#updateSuggestion<%=index %>').css('color', 'gray');updateSuggestionText(document.getElementById('suggestionBeanText'+(this.value).toString()).value,'tarea<%=index%>');">
				<%
					for(SuggestionBean bitem : suggestionsToShow){
						%>
							<option id="textTitleList<%=bitem.getRowID()%>" value=<%=bitem.getRowID() %>  style="width:50px;" ><%=bitem.getSuggestion().length() > 25 ? bitem.getSuggestion().substring(0, 25) : bitem.getSuggestion() %></option>
						<%
					}
				%>
				</select>
				<button type="button" id="removeSuggestion<%=index%>"  style="float: right;" >Delete</button>
				<script type="text/javascript">

					var suggestionToShowListSizeForReadonly = <%=suggestionsToShow == null ? 0: suggestionsToShow.size()%>;
					$("#removeSuggestion<%=index%>").click(function(){
						if(confirm('Are you sure you want to delete the food diary suggestion?')){
							$.post("FoodDiarySuggestionRemovalServlet",
							 {
								loggedInMID: <%=loggedInMID%>,
								suggestionRowID: document.getElementById("savedSuggestionList<%=index%>").value,
								newText: document.getElementById("tarea<%=index%>").value
							 },
							function(data, status){
								if(data === "Success"){
									alert("Suggestion has been removed! ");						
							 		var rowID =  document.getElementById("savedSuggestionList<%=index%>").value;
							 		console.log(rowID);
							 		//update text record on client side
							 		var suggTextRemove = document.getElementById("suggestionBeanText"+rowID.toString());
							 		suggTextRemove.parentNode.removeChild(suggTextRemove);
									//update list title
									var element = document.getElementById("textTitleList"+rowID.toString());
									element.parentNode.removeChild(element);
									var selector = document.getElementById("savedSuggestionList<%=index%>");
									console.log(suggestionToShowListSizeForReadonly+" list-index");
									suggestionToShowListSizeForReadonly = suggestionToShowListSizeForReadonly - 1;
									if(suggestionToShowListSizeForReadonly < 1){
										document.getElementById("tarea<%=index%>").value = "There is no suggestion.";
										console.log("changed text: "+ <%=index%>);
										document.getElementById("tarea<%=index%>").onkeyup = null;
										$('#updateSuggestion<%=index%>').attr('disabled','disabled');
										$('#updateSuggestion<%=index %>').css('color', 'gray');
										$('#removeSuggestion<%=index%>').attr('disabled','disabled');
										$('#removeSuggestion<%=index%>').css('color', 'gray');
										document.getElementById("tarea<%=index%>").readOnly = true ;
									}else{
										updateSuggestionText(document.getElementById('suggestionBeanText'+(selector.value).toString()).value,'tarea<%=index%>');
									}

								}else{
									alert("Something went wrong :(");
								}
							});
						}
					});
				</script>
				<textarea style="width: 100%; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box;resize: none;" onkeyup="$('#updateSuggestion<%=index%>').removeAttr('disabled');$('#updateSuggestion<%=index %>').css('color', 'black');" id="tarea<%=index%>" rows="4"  ><%=suggestionsToShow != null &&suggestionsToShow.size()> 0 ?suggestionsToShow.get(0).getSuggestion():"There is no suggestion."%></textarea>
				<%
				if(suggestionsToShow == null ||suggestionsToShow.size() < 1){
				%>
				<script>
				document.getElementById("tarea<%=index%>").onkeyup = null;
				$('#updateSuggestion<%=index%>').attr('disabled','disabled');
				$('#updateSuggestion<%=index %>').css('color', 'gray');
				$('#removeSuggestion<%=index%>').attr('disabled','disabled');
				$('#removeSuggestion<%=index%>').css('color', 'gray');
				document.getElementById("tarea<%=index%>").readOnly = true ;
				</script>
				<%
				}
				%>
				</td>
				<td></td>
			</form>
			</tr>
			<%
			}
			if(b != null){
				String row = "<tr data-diarydate='" + (b != null ? diaryDateFormat.format(b.getDate()) : "") + "'";
%>

				<%=row+""+((index % 2 == 1)?" class=\"alt\"":"")+">"%>
				<%
					//SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YYYY");
				if(recordFlag.length() > 0 && !sdf.format(b.getDate()).equals(recordFlag)){
					needDailySummary = true;
				}
							
				recordFlag = sdf.format(b.getDate());
				oldBean = b;
				
				FoodDiaryLabelSetBean labelBean = labelAction.getSetFoodDiaryLabel(oldBean.getOwnerID(), new Date(oldBean.getDate().getTime()));
				String label = "";
				if(labelBean != null)
					label = labelBean.getLabel();
				%>
				<tr class="diaryLabelRow <%= label %>" data-diarydate="<%= b != null ? diaryDateFormat.format(b.getDate()) : "" %>">
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
					<td></td>
					</tr>
				</tr>
		<%		index ++; 
			}
		}
		
%>		

	</table>
	<div align="center" style="margin:10px;">
	<button align="center" type='button' onclick="switchHiddenForm('hiddenMacro','showGraph'); preGraph();">Macro Calculator</button>
	</div>
	</div>
	</br>
	<div id="hiddenMacro"  style="display: none">
	<%@include file="/auth/patient/macroNutrientsChart.jsp"%>
	</div>
	<!-- Hidden compare daily summary diagram -->
	<div align="center" id="hiddenDailyGraph" style="display:none">
	<%@include file="/auth/patient/macroNutrientsCompareGraph.jsp" %>
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

<script type="text/javascript">
	function switchHiddenForm(divID, operationMode){
		document.getElementById("hiddenMacro").style.display = "none";
		document.getElementById("hiddenDailyGraph").style.display = "none";
		setOperationMode(operationMode);
		showHiddenForm(divID);
	}
	function showHiddenForm(divID) {
		document.getElementById(divID).style.display = "block";
		scrollToDiv(divID);
	}
	function setOperationMode(operation){
		document.getElementById("operationMode").value = operation;
	}
	function scrollToDiv(divID){
		$('html, body').animate({
			scrollTop: $("#" + divID).offset().top - 100
		}, 250);
	}
	function hideHiddenForm(divID){
		document.getElementById(divID).style.display = "none";
	}
	$('#labelFilter').change(function() {
		$('[data-diarydate]').show();
		$('#dateRangeTo').val('');
		$('#dateRangeFrom').val('');
		var label = $(this).val();
		if(label == "any") {
			$('.diaryLabelRow').show();
		} else {
			$('.diaryLabelRow').hide();
			var selector = '.'+label;
			$(selector).show();
		}
	});

	$('#dateRangeCheckbox').click(function() {
		if(this.checked)
			$('#dateRangeTo').show();
		else
			$('#dateRangeTo').hide();
	});
	
	$('#dateFilterClear').click(function() {
		$('[data-diarydate]').show();
		$('#dateRangeTo').val('');
		$('#dateRangeFrom').val('');
		$('#labelFilter').val('any');
	});
	
	$('#dateFilterSubmit').click(function() {
		$('#labelFilter').val('any');
		$('[data-diarydate]').show();
		var goodDate = isDate($('#dateRangeFrom').val()); //validate from date
		var dateRange = $('#dateRangeCheckbox')[0].checked;
		if(dateRange)
			goodDate = isDate($('#dateRangeTo').val()); //validate to date if we're in a date range
		
		//doesn't pass validation, show error message
		//otherwise continue with filtering
		if(!goodDate) {
			$('#dateFilterErrorMsg').html('Invalid date. Please make sure your dates are valid and in MM/dd/yyyy format.');
		} else {
			$('#dateFilterErrorMsg').html(''); //clear error message
			var dateFrom = (new Date($('#dateRangeFrom').val())).getTime();
			//if user wants to filter by range
			if(dateRange) {
				var dateTo = (new Date($('#dateRangeTo').val())).getTime();				
				//swap them if dateFrom is after dateTo
				if(dateFrom > dateTo) {
					var tmpdate = dateFrom;
					dateFrom = dateTo;
					dateTo = tmpdate;
				}
			}
			
			//show/hide each applicable element
			$('[data-diarydate]').each(function() {
				var thisdate = (new Date($(this).attr('data-diarydate'))).getTime();
				//single date, must be equal
				if(!dateRange) {
					if(thisdate == dateFrom)
						$(this).show();
					else
						$(this).hide();
				} else { //date range, must be in range
					if(thisdate >= dateFrom && thisdate <= dateTo)
						$(this).show();
					else
						$(this).hide();
				}
			});
			
		}
	});
	
	//validates MM/dd/yyyy format
	//taken from http://www.jquerybyexample.net/2011/12/validate-date-using-jquery.html
	function isDate(txtDate)
	{
	    var currVal = txtDate;
	    if(currVal == '')
	        return false;
	    
	    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/; //Declare Regex
	    var dtArray = currVal.match(rxDatePattern); // is format OK?
	    
	    if (dtArray == null) 
	        return false;
	    
	    //Checks for mm/dd/yyyy format.
	    dtMonth = dtArray[1];
	    dtDay= dtArray[3];
	    dtYear = dtArray[5];        
	    
	    if (dtMonth < 1 || dtMonth > 12) 
	        return false;
	    else if (dtDay < 1 || dtDay> 31) 
	        return false;
	    else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) 
	        return false;
	    else if (dtMonth == 2) 
	    {
	        var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	        if (dtDay> 29 || (dtDay ==29 && !isleap)) 
	                return false;
	    }
	    return true;
	}
	function updateSuggestionText(content, id){
		var textarea = document.getElementById(id);
		textarea.value = content;
	}
</script>

<%@include file="/footer.jsp" %>

