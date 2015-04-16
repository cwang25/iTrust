package edu.ncsu.csc.itrust.beans;

import java.sql.Date;

public class FoodDiaryLabelSetBean {
	private long rowid = -1;
	private long mid;
	private Date date;
	private String label;
	private long labelReferenceRowID;
	
	/**
	 * Constructor for a new FoodDiaryLabelSetBean that isn't already in the database
	 * @param mid User's mid
	 * @param date Date the label is set to
	 * @param label Label to set
	 */
	public FoodDiaryLabelSetBean(long mid, Date date, String label, long referenceRowID) {
		this(-1, mid, date, label, referenceRowID);
	}
	
	/**
	 * Constructor for a FoodDiaryLabelSetBean that is already in the database
	 * @param rowid Database row id
	 * @param mid User's mid
	 * @param date Date the label is set to
	 * @param label Label to set
	 * @param referenceRowID The reference row id connect to label table.
	 */
	public FoodDiaryLabelSetBean(long rowid, long mid, Date date, String label, long referenceRowID) {
		this.rowid = rowid;
		this.mid = mid;
		this.date = date;
		this.label = label;
		this.labelReferenceRowID = referenceRowID;
	}
	
	/**
	 * @return the rowid
	 */
	public long getRowid() {
		return rowid;
	}
	/**
	 * @param rowid the rowid to set
	 */
	public void setRowid(long rowid) {
		this.rowid = rowid;
	}
	/**
	 * @return the mid
	 */
	public long getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(long mid) {
		this.mid = mid;
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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the labelReferenceRowID
	 */
	public long getLabelReferenceRowID() {
		return labelReferenceRowID;
	}

	/**
	 * @param labelReferenceRowID the labelReferenceRowID to set
	 */
	public void setLabelReferenceRowID(long labelReferenceRowID) {
		this.labelReferenceRowID = labelReferenceRowID;
	}
}
