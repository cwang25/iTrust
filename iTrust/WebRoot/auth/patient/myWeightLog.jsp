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

<script src="/iTrust/js/Chart.js"></script>
<style type="text/css">
	.foodDiaryTable td, .foodDiaryTable tr, .foodDiaryTable th {
		border:1px solid #212121;
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
			%><div align="center" style="color:#F44336;"><%= e.getMessage() %></div><%
		}
	} else {
		%>
		<div align="center" style="color:#F44336;"><%= errormsg %></div>
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
		<table id="weightLogTable" class="foodDiaryTable">
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
		
		%> 		
		
		</table>
    	
		<%
	}
	%>		
	<div align="Center">
	<input type="button" style="font-size: 16pt; margin:5px;" value="Add New Entry" id="showNewLogForm"/>
	<%if(weightLogs.size() != 0) {
		%>
		<button onclick='makeChart();' style="font-size: 16pt; margin-bottom:20px;">View Chart</button>
        <br/>
        <div align="center" id="ChartContainer" style="display:none;">
	        <div id="canvas-holder" style="display:table; margin:auto;">
		        <canvas id="chart-area" width="600" height="400" style="float:left;"></canvas>
		        <div id="legendOuterDiv" style="float:left;"><div id="legendDiv"></div></div>
	        </div>
	        <div style="clear:both;"></div>
	        <br/><br/>
	        <div id="canvas-holder2" style="display:table; margin:auto;">
		        <canvas id="chart-area2" width="600" height="400" style="float:left;"></canvas>
		        <div id="legendOuterDiv2" style="float:left;"><div id="secondLegend"></div></div>
	        </div>
	        <input align="center" type="button" style="color: #212121;font-size: 16pt; font-weight: bold; " value="Cancel" onclick="$('#ChartContainer').hide();">
	        
    	</div>
		<%
	}
	
	%>
	
   	</div>
   	
	<form style="display:none;" action="myWeightLog.jsp" method="post" id="weightLogForm" name="weightLogForm" align="center">
		<table class="fTable"  align="center">
			<tr>
			<th>Add New Record</th>
			<th><input type="button" style="color: #212121;font-size: 16pt; font-weight: bold; float: right;" value="Cancel" onclick="$('#weightLogForm').hide();"></th>
			</tr>
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
			style="margin: 10px;font-size: 16pt; font-weight: bold;"
			value="Save">
	</form>
</div>

<script type="text/javascript">
	$('#showNewLogForm').click(function() {
		$('#weightLogForm').show();
		$('#ChartContainer').hide();
	});
</script>

<style type="text/css">
	li {
		list-style: none;
	}
</style>

<script language="JavaScript">
var chart = null;
var chart2 = null;

function makeChart() {
	$('#weightLogForm').hide();
	$('#ChartContainer').show();
	
    var count = $('#weightLogTable tr').length - 1;
    var labels = [];
    var datasets = [];
    var table = document.getElementById("weightLogTable");
    var numCol = table.rows[0].cells.length;

    // Create datapoint for each column
    for (var i = 0; i < numCol; i++) {
        var dataPoint = [];
        for (var k = 1; k <= count; k++) { // Add data points
            dataPoint.push(getCell(table, k, i));
        }
        datasets.push(dataPoint);
    }
    
    // Loop through each row after table headings to populate labels array
    for (var i = 1, row; row = table.rows[i]; i++)
        labels.push(row.cells[0].innerHTML);
    
    var measurements = ["", "Weight", "Chest", "Waist", "Upper Arm", "Forearm", "Thighs", "Calves", "Neck"];
    var weightData = {
    		labels: labels,
    		datasets: [{
                label: "Weight",
                data: datasets[1],
                fillColor: "#CDDC39",
                pointColor: "#CDDC39",
                strokeColor: "#CDDC39"
    		}]
    };
    
    var lineData = {
        labels: labels,
        datasets: [ {
                	   label: "Chest",
                	   data: datasets[2],
                       fillColor: "#8BC34A",
                       pointColor: "#8BC34A",
                       strokeColor: "#8BC34A"
                   },{
                       label: "Waist",
                       data: datasets[3],
                       fillColor: "#F44336",
                       pointColor: "#F44336",
                       strokeColor: "#F44336"

                   },{
                       label: "Upper Arm",
                       data: datasets[4],
                       fillColor: "#FF9800",
                       pointColor: "#FF9800",
                       strokeColor: "#FF9800"
                   },{
                       label: "Forearm",
                       data: datasets[5],
                       fillColor: "#BA68C8",
                       pointColor: "#BA68C8",
                       strokeColor: "#BA68C8"
                   },{
                       label: "Thighs",
                       data: datasets[6],
                       fillColor: "#009688",
                       pointColor: "#009688",
                       strokeColor: "#009688"
                   },{
                       label: "Calves",
                       data: datasets[7],
                       fillColor: "#CDDC39",
                       pointColor: "#CDDC39",
                       strokeColor: "#CDDC39"
                   },{
                       label: "Neck",
                       data: datasets[8],
                       fillColor: "#212121",
                       pointColor: "#212121",
                       strokeColor: "#212121"
                   } ]
    };

    // Context for the canvas element where the chart will be drawn
    var ctx = document.getElementById("chart-area").getContext("2d");
    var ctx2 = document.getElementById("chart-area2").getContext("2d");
    // Specify options for the chart
    var options = {
        //scaleShowGridLines : true,
        bezierCurve: true,
        scaleGridLineColor: "rgba(0,0,0,.05)",
        datasetFill : false,
    };

    // Check to see if chart is being drawn for the first time
    // If yes, make a new chart
    chart = new Chart(ctx).Line(lineData, options);
    chart2 = new Chart(ctx2).Line(weightData, options);
    legendLine(document.getElementById('legendDiv'));
    legendLine2(document.getElementById('secondLegend'));

    
}
    
function getCell(table, row, col) {
    return table.rows[row].cells[col].innerHTML;
}

function legendLine(parent) {
    var measurements = ["", "Chest", "Waist", "Upper Arm", "Forearm", "Thighs", "Calves", "Neck"];
    var colors = ["", "#8BC34A", "#F44336", "#FF9800", "#BA68C8", "#009688", "#CDDC39", "#212121"];
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
    for (var i = 1; i <= 7; i++) {
        // Make a new list element for this data item
        var li = document.createElement('li');
        //var imageUrl = "/iTrust/image/sq_" + item.color.substring(1) + ".png";
        // Set list to use an image for bullets instead of regular bullets
        //li.style.listStyleImage = "url(" + "\'" + imageUrl + "\'" + ")";
        var label = measurements[i];
        li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:white; padding:3px; border-radius:5px; border:solid thin #212121; background-color:"+colors[i]+"\">"
                + label + "</span></p>";
        // Add this data point to the legend
        ul.appendChild(li);
    }
}

function legendLine2(parent) {
	if (parent.hasChildNodes()) {
        parent.removeChild(parent.lastChild);
    }
	var ul = document.createElement('ul');
	parent.appendChild(ul);
	var li = document.createElement('li');
	var label = "Weight";
	li.innerHTML = "<p style=\"font-size:18px;\"><span style =\"color:white; padding:3px; border-radius:5px; border:solid thin #212121; background-color:#CDDC39;\">"+label+"</span></p>";
	ul.appendChild(li);
}
</script>

<%@include file="/footer.jsp"%>