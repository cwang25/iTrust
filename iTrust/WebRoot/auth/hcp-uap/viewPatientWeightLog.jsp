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
    
    // Loop through each row after table headings to populate labels array
    for (var i = 1, row; row = table.rows[i]; i++)
        labels.push(row.cells[0].innerHTML);
    
    var measurements = ["", "Weight", "Chest", "Waist", "Upper Arm", "Forearm", "Thighs", "Calves", "Neck"];
    var lineData = {
        labels: labels,
        datasets: [ {
                	   label: "Weight",
                	   data: datasets[1],
                       fillColor: "rgba(220,220,220,0.2)",
                       pointColor: "rgba(220,220,220,1)"
                   },{
                	   label: "Chest",
                	   data: datasets[2],
                	   fillColor: "rgba(101, 156, 239, 0.2)",
                	   pointColor: "rgba(101, 156, 239, 1)"
                   },{
                       label: "Waist",
                       data: datasets[3],
                       fillColor: "rgba(125, 189, 0, 0)",
                       pointColor: "rgba(125, 189, 0, 1)"
                   },{
                       label: "Upper Arm",
                       data: datasets[4],
                       fillColor: "rgba(220, 246, 0, 0)",
                       pointColor: "rgba(220, 246, 0, 1)"
                   },{
                       label: "Forearm",
                       data: datasets[5],
                       fillColor: "rgba(255, 91, 0, 0)",
                       pointColor: "rgba(255, 91, 0, 1)"
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
    // Specify options for the chart
    var options = {
        //scaleShowGridLines : true,
        bezierCurve: true,
        scaleShowLabels: true,
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

function legendLine(parent, data) {
    // Used while updating chart.
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
    alert(data.length);
    for (var i = 0; i < data.length; i++) {
        var item = data[i];
        // Make a new list element for this data item
        var li = document.createElement('li');
        //var imageUrl = "/iTrust/image/sq_" + item.color.substring(1) + ".png";
        // Set list to use an image for bullets instead of regular bullets
        //li.style.listStyleImage = "url(" + "\'" + imageUrl + "\'" + ")";
        var label = item.label.substring(0, item.label.indexOf('(') - 1) + ": "
                + item.value + " gms";
        li.innerHTML = "<p style=\"font-size:18px;\"><span style=\"color:black; gravity:left\">"
                + label + "</span></p>";
        // Add this data point to the legend
        ul.appendChild(li);
    }
    // Create a div to show total calorie count
    var calorieDisplay = document.createElement('div');
    // Set its font size to 18px
    calorieDisplay.style.fontSize = "18px";
    calorieDisplay.marginLeft = "30px";
    // Set its content
    calorieDisplay.innerHTML = "Total Calories: ";
    // Add it to the legend
    parent.appendChild(calorieDisplay);
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
