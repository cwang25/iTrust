package edu.ncsu.csc.itrust.beans;

import java.sql.Date;

public class FoodDiaryLabelSetBean {
	private long rowid;
	private long mid;
	private Date date;
	private String label;
	
	/**
	 * Constructor for a new FoodDiaryLabelSetBean that isn't already in the database
	 * @param mid User's mid
	 * @param date Date the label is set to
	 * @param label Label to set
	 */
	public FoodDiaryLabelSetBean(long mid, Date date, String label) {
		this(-1, mid, date, label);
	}
	
	/**
	 * Constructor for a FoodDiaryLabelSetBean that is already in the database
	 * @param rowid Database row id
	 * @param mid User's mid
	 * @param date Date the label is set to
	 * @param label Label to set
	 */
	public FoodDiaryLabelSetBean(long rowid, long mid, Date date, String label) {
		this.rowid = rowid;
		this.mid = mid;
		this.date = date;
		this.label = label;
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
}
