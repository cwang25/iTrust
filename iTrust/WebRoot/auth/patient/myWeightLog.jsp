<%@page import="edu.ncsu.csc.itrust.action.ViewWeightLogAction"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.lang.Long"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewWeightLogAction" %>
<%@page import="edu.ncsu.csc.itrust.action.AddWeightLogAction" %>
<%@page import="edu.ncsu.csc.itrust.beans.WeightLogBean"%>

<%@page import="java.util.Calendar"%>
<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - View My Weight Log";
%>

<%@include file="/header.jsp"%>

<style type="text/css">
	.foodDiaryTable td, .foodDiaryTable tr, .foodDiaryTable th {
		border:1px solid black;
		border-collapse: collapse;
	}
</style>

<%
ViewWeightLogAction viewAction = new ViewWeightLogAction(prodDAO, loggedInMID);
AddWeightLogAction addAction = new AddWeightLogAction(prodDAO, loggedInMID);

//form was submitted
if(request.getParameter("confirmSubmit") != null) {
	String datestr = request.getParameter("date") != null ? request.getParameter("date") : "";
	String weightstr = request.getParameter("weight") != null ? request.getParameter("weight") : "";
	String cheststr = request.getParameter("chest") != null ? request.getParameter("chest") : "";
	String waiststr = request.getParameter("waist") != null ? request.getParameter("waist") : "";
	String upperarmstr = request.getParameter("upperarm") != null ? request.getParameter("upperarm") : "";
	String forearmstr = request.getParameter("forearm") != null ? request.getParameter("forearm") : "";
	String thighstr = request.getParameter("thigh") != null ? request.getParameter("thigh") : "";
	String calvesstr = request.getParameter("calves") != null ? request.getParameter("calves") : "";
	String neckstr = request.getParameter("neck") != null ? request.getParameter("neck") : "";
	Date date = null;
	double weight = 0;
	double chest = 0;
	double waist = 0;
	double upperarm = 0;
	double forearm = 0;
	double thigh = 0;
	double calves = 0;
	double neck = 0;
	boolean isValid = true;
	String errormsg = "";
	
	try {
		date = new Date((new java.util.Date(datestr)).getTime());
	} catch(Exception e) {
		isValid = false;
		errormsg += "Invalid date format.<br/>";
	}
	try {
		weight = Double.parseDouble(weightstr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Weight must be a number.<br/>";
	}
	try {
		chest = Double.parseDouble(cheststr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Chest must be a number.<br/>";
	}
	try {
		waist = Double.parseDouble(waiststr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Waist must be a number.<br/>";
	}
	try {
		upperarm = Double.parseDouble(upperarmstr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Upper Arm must be a number.<br/>";
	}
	try {
		forearm = Double.parseDouble(forearmstr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Forearm must be a number.<br/>";
	}
	try {
		thigh = Double.parseDouble(thighstr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Thigh must be a number.<br/>";
	}
	try {
		calves = Double.parseDouble(calvesstr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Calves must be a number.<br/>";
	}
	try {
		neck = Double.parseDouble(neckstr);
	} catch(Exception e) {
		isValid = false;
		errormsg += "Neck must be a number.<br/>";
	}
	
	if(isValid) {
		WeightLogBean b = new WeightLogBean(loggedInMID, date, weight, chest, waist, upperarm, forearm, thigh, calves, neck);
		try {
			addAction.addWeightLog(b);
			%><h2 align="center">Log successfully added!</h2><%
		} catch(Exception e) {
			%><div align="center" style="color:red;"><%= e.getMessage() %></div><%
		}
	} else {
		%>
		<div align="center" style="color:red;"><%= errormsg %></div>
		<%
	}
}
%>


<div align="center">
	<h2>My Weight Log</h2>
	
	<%	
	List<WeightLogBean> weightLogs = viewAction.getWeightLogListByMID(loggedInMID);
	
	if(weightLogs.size() == 0) {
		%><div>You have no logs.</div><%
	} else {
		%> 
		<table class="foodDiaryTable">
			<tr>
				<th>Date</th>
				<th>Weight(pounds)</th>
				<th>Chest(inches)</th>
				<th>Waist(inches)</th>
				<th>Upper Arm(inches)</th>
				<th>Forearm(inches)</th>
				<th>Thigh(inches)</th>
				<th>Calves(inches)</th>
				<th>Neck(inches)</th>
			</tr>
		<%
		for(WeightLogBean b : weightLogs) {
			%> 
			<tr>
				<td><%= b.getDate().toString() %></td>
				<td><%= b.getWeight() %></td>
				<td><%= b.getChest() %></td>
				<td><%= b.getWaist() %></td>
				<td><%= b.getUpperarm() %></td>
				<td><%= b.getForearm() %></td>
				<td><%= b.getThigh() %></td>
				<td><%= b.getCalves() %></td>
				<td><%= b.getNeck() %></td>
			</tr>
			<%
		}
	}
	%>	
	</table>
	
	<br/>	
	<input type="button" value="Add Log Entry" id="showNewLogForm"/>
	
	<form style="display:none;" action="myWeightLog.jsp" method="post" id="weightLogForm" name="weightLogForm" align="center">
		<table>
			<tr>
				<td>Date:</td>
				<%
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
					Date today = new Date(Calendar.getInstance().getTimeInMillis());
				%>
				<td><input name="date"
					value="<%= sdf.format(today) %>"
					type="text" placeholder="mm/dd/yyyy"> <input
					type="button" value="Select Date"
					onclick="displayDatePicker('date');"></td>
			</tr>
			<tr>
				<td>Weight:</td>
				<td><input type="text" name="weight"/></td>
			</tr>
			<tr>
				<td>Chest:</td>
				<td><input type="text" name="chest"/></td>
			</tr>
			<tr>
				<td>Waist:</td>
				<td><input type="text" name="waist"/></td>
			</tr>
			<tr>
				<td>Upper Arm:</td>
				<td><input type="text" name="upperarm"/></td>
			</tr>
			<tr>
				<td>Forearm:</td>
				<td><input type="text" name="forearm"/></td>
			</tr>
			<tr>
				<td>Thigh:</td>
				<td><input type="text" name="thigh"/></td>
			</tr>
			<tr>
				<td>Calves:</td>
				<td><input type="text" name="calves"/></td>
			</tr>
			<tr>
				<td>Neck:</td>
				<td><input type="text" name="neck"/></td>
			</tr>
		</table>
		<input type="hidden" name="confirmSubmit" value="true"/>
		<input type="submit" id="saveBtn" name="action"
			style="font-size: 16pt; font-weight: bold;"
			value="Save">
	</form>
</div>

<script type="text/javascript">
	$('#showNewLogForm').click(function() {
		$('#weightLogForm').show();
	});
</script>