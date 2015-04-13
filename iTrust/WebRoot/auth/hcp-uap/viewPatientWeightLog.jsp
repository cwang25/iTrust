<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.WeightLogBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewWeightLogAction"%>

<script src="/iTrust/js/Chart.js"></script>
<script language="JavaScript">
var chart = null;
var chart2 = null;

function makeChart() {

    /*// Define chart data
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

    ];*/
    
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
                fillColor: "rgba(220,220,220,0.2)",
                pointColor: "rgba(220,220,220,1)"
    		}]
    };
    
    var lineData = {
        labels: labels,
        datasets: [ {
                	   label: "Chest",
                	   data: datasets[2],
                	   fillColor: "rgba(101, 156, 239, 0.1)",
                   },{
                       label: "Waist",
                       data: datasets[3],

                   },{
                       label: "Upper Arm",
                       data: datasets[4],
                   },{
                       label: "Forearm",
                       data: datasets[5],
                   },{
                       label: "Thighs",
                       data: datasets[6]
                   },{
                       label: "Calves",
                       data: datasets[7]
                   },{
                       label: "Neck",
                       data: datasets[8]
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
        li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:black; gravity:left\">"
                + label + "</span></p>";
        // Add this data point to the legend
        ul.appendChild(li);
    }
}

function legendLine2(parent) {
	var ul = document.createElement('ul');
	parent.appendChild(ul);
	var li = document.createElement('li');
	var label = "Weight";
	li.innerHTML = "<p style=\"font-size:18px;\"><span style =\"color:black; gravity:left\">"+label+"</span></p>";
	ul.appendChild(li);
}
</script>

<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Patient Weight Log";
%>

<%@include file="/header.jsp" %>
<itrust:patientNav thisTitle="View Patient Weight Log" />

<%
    // Require a Patient ID first
    String pidString = (String)session.getAttribute("pid");
    if (pidString == null || 1 > pidString.length()) {
        response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewPatientWeightLog.jsp");
        return;
    }
    
    ViewWeightLogAction viewWeightLog = new ViewWeightLogAction(prodDAO, loggedInMID);
    List<WeightLogBean> weightLogList = viewWeightLog.getWeightLogListByMID(Long.parseLong(pidString));

    if (weightLogList != null && weightLogList.size() > 0) {

        %>
        <br/>
        <div style="margin-left: 5px;">
        <table id="weightLogTable" border=1 align="center">
        <tr>
            <th>Date</th>
            <th>Weight (lbs)</th>
            <th>Chest (in)</th>
            <th>Waist (in)</th>
            <th>Upper Arm (in)</th>
            <th>Forearm (in)</th>
            <th>Thighs (in)</th>
            <th>Calves (in)</th>
            <th>Neck (in)</th>
        </tr>
        <%
        for (WeightLogBean b : weightLogList) {
            %>
            <tr>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getDate())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getWeight())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getChest())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getWaist())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getUpperarm())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getForearm())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getThigh())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getCalves())%></td>
            <td><%=StringEscapeUtils.escapeHtml("" + b.getNeck())%></td>
            </tr>
            
            <%
        }
        %>
        </table>
        </div>
        <br/><br/>
        <button onclick='makeChart();' style="font-size: 16pt" align="center">View Chart</button>
        <table><tr><td>
        <div id="canvas-holder">
        <canvas id="chart-area" width="400" height="400"></canvas>
        </td><td>
        <div id="legendOuterDiv" align="center"><table style="border:none"><tr><td id="legendDiv"></td></tr></table></div>
        </div></td></tr><tr><td>
        <div id="canvas-holder2">
        <canvas id="chart-area2" width="400" height="400"></canvas>
        </td><td>
        <div id="legendOuterDiv2" align="center"><table style="border:none"><tr><td id="secondLegend"></td></tr></table></div>
        </div></td></tr>
        </table>
    
    	<%
    } else {
    	%>
    	<div><center>The patient has no Weight Log Entries</center></div>
    	<%
    }
    
    
    %>
    
<%@include file="/footer.jsp" %>
