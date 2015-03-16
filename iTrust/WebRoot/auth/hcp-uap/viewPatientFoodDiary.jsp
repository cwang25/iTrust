<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.beans.HealthRecord"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewFoodDiaryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%>

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
	List<FoodDiaryBean> foodDiaryList = action.getFoodDiaryListByOwnerID(Long.parseLong(pidString));
	session.setAttribute("foodDiaryList", foodDiaryList);
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
	<%		index ++; %>
	<%	} %>
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
		<center>This function is only available to Nutritionist specialty HCP.</center>
	</div>
	<%  } %>
<%	} %>	
	<br />
</div>

<%@include file="/footer.jsp" %>

