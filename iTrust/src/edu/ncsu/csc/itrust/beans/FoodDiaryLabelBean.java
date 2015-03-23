package edu.ncsu.csc.itrust.beans;

public class FoodDiaryLabelBean {
	private long rowid = -1;
	private long mid;
	private String label;
	
	/**
	 * Constructor for a brand new FoodDiaryLabelBean
	 * @param mid User's MID
	 * @param label Label for food diary
	 */
	public FoodDiaryLabelBean(long mid, String label) {
		this(-1, mid, label);
	}
	
	/**
	 * Constructor for a FoodDiaryLabelBean that already exists in the database and has a rowid
	 * @param rowid database row id
	 * @param mid User's mid
	 * @param label Label for food diary
	 */
	public FoodDiaryLabelBean(long rowid, long mid, String label) {
		this.rowid = rowid;
		this.mid = mid;
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
