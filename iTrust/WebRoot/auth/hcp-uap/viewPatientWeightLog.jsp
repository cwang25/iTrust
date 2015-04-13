<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.WeightLogBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewWeightLogAction"%>

<script src="/iTrust/js/Chart.js"></script>
<script language="JavaScript">
var chart = null;

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
    
    // Loop through each row after table headings
  for (var i = 1, row; row = table.rows[i]; i++) {
        
        var dataPoint = []; // create array of data for first entry
        // Add Data to labels
        labels.push(row.cells[0].innerHTML);
        for (var j = 0, val; val = table.rows[j]; j++) {
       //for (var j = 1, col; col = row.cells[j]; j++) {
            dataPoint.push(val.innerHTML);
        }
       
       datasets.push(dataPoint);
    }
        
   var measurements = ["", "Weight", "Chest", "Waist", "Upper Arm", "Forearm", "Thighs", "Calves", "Neck"];
    var lineData = {
        labels: labels,
        datasets: [ {
                	   label: "Weight",
                	   data: datasets[1]
                   },{
                	   label: "Chest",
                	   data: datasets[2]
                   },{
                       label: "Waist",
                       data: datasets[3]
                   },{
                       label: "Upper Arm",
                       data: datasets[4]
                   },{
                       label: "Forearm",
                       data: datasets[5]
                   },{
                       label: "Thighs",
                       data: datasets[6]
                   },{
                       label: "Calves",
                       data: datasets[7]
                   },{
                       label: "Neck",
                       data: datasets[8]
                   }
                   ]
    };

    // Context for the canvas element where the chart will be drawn
    var ctx = document.getElementById("chart-area").getContext("2d");
    // Specify options for the chart
    var options = {
        scaleShowGridLines : true,
    };

    // Check to see if chart is being drawn for the first time
    // If yes, make a new chart
    if (chart == null) {
        chart = new Chart(ctx).Line(lineData, options);
    }
    //legendLine(document.getElementById('legendDiv'), lineData);
    
}
    
function getCell(table, row, col) {
    return table.rows[row].cells[col].innerHTML;
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
        for (int i = 0 ; i < weightLogList.size(); i++) {
            WeightLogBean b = weightLogList.get(i);
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
        <div id="canvas-holder">
        <canvas id="chart-area" width="400" height="400"></canvas>
        <div id="legendOuterDiv" align="center"><table style="border:none"><tr><td id="legendDiv"></td></tr></table></div>
        </div>
    
    	<%
    } else {
    	%>
    	<div><center>The patient has no Weight Log Entries</center></div>
    	<%
    }
    
    
    %>
    
<%@include file="/footer.jsp" %>
