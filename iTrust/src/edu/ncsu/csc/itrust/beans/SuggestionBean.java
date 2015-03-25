package edu.ncsu.csc.itrust.beans;

import java.util.Date;

public class SuggestionBean {
	
	private long rowID = -1;
	private Date date;
	private long patientID;
	private long hcpID;
	private String suggestion;
	private String isNew;
	/**
	 * 
	 * @param date The date of the suggestion
	 * @param patientID The patient's ID
	 * @param hcpID The ID of the HCP making suggestion
	 * @param sugg The suggestion of the HCP to the patient
	 */
	public SuggestionBean(Date date, long patientID, long hcpID,String sugg, String isNew) {
		
		//Make a copy of the date, for security reasons from jenkin's bug finder.
		Date copyOfDate = new Date();
		copyOfDate.setTime(date.getTime());
		
		setPatientID(patientID);
		setHcpID(hcpID);
		setDate(copyOfDate);
		setSuggestion(sugg);
		setIsNew(isNew);
	}

	/**
	 * @return the patientID
	 */
	public long getPatientID() {
		return patientID;
	}

	/**
	 * @param patientID the patientID to set
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	/**
	 * @return the hcpID
	 */
	public long getHcpID() {
		return hcpID;
	}

	/**
	 * @param hcpID the hcpID to set
	 */
	public void setHcpID(long hcpID) {
		this.hcpID = hcpID;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the suggestion
	 */
	public String getSuggestion() {
		return suggestion;
	}

	/**
	 * @param suggestion the suggestion to set
	 */
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	/**
	 * @return the rowID
	 */
	public long getRowID() {
		return rowID;
	}

	/**
	 * @param rowID the rowID to set
	 */
	public void setRowID(long rowID) {
		this.rowID = rowID;
	}
	
	/**
	 * @return the rowID
	 */
	public String getIsNew() {
		return isNew;
	}

	/**
	 * @param rowID the rowID to set
	 */
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

}
