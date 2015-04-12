<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="edu.ncsu.csc.itrust.beans.WeightLogBean"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewWeightLogAction"%>



<%@include file="/global.jsp" %>

<%
    pageTitle = "iTrust - View Patient Weight Log";
%>

<%@include file="/header.jsp" %>
<itrust:patientNav thisTitle="View Patient Food Diary" />

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
        <table class="weightLogTable" border=1 align="center">
        <tr>
            <th>Date</th>
            <th>Weight</th>
            <th>Chest</th>
            <th>Waist</th>
            <th>Upper Arm</th>
            <th>Forearm</th>
            <th>Thigh</th>
            <th>Calves</th>
            <th>Neck</th>
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
    
    	<%
    } else {
    	%>
    	<div><center>The patient has no Weight Log Entries</center></div>
    	<%
    }
    
    
    %>

<%@include file="/footer.jsp" %>

