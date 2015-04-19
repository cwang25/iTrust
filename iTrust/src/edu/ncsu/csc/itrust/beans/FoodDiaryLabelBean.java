package edu.ncsu.csc.itrust.beans;

public class FoodDiaryLabelBean {
	private long rowid = -1;
	private long mid;
	private String label;
	String colorCode ;
	static String DEFAULT_COLOR_CODE = "#99CC00";
	
	/**
	 * Constructor for a brand new FoodDiaryLabelBean
	 * @param mid User's MID
	 * @param label Label for food diary
	 */
	public FoodDiaryLabelBean(long mid, String label) {
		this(-1, mid, label, DEFAULT_COLOR_CODE);
	}
	/**
	 * Constructor for label with given color code.
	 *  @param mid User's MID
	 * @param label Label for food diary
	 * @param code Color Code
	 */
	public FoodDiaryLabelBean(long mid, String label, String code){
		this.rowid = -1;
		this.mid = mid;
		this.label = label;
		this.colorCode = code;
	}
	
	/**
	 * Constructor for a FoodDiaryLabelBean that already exists in the database and has a rowid
	 * @param rowid database row id
	 * @param mid User's mid
	 * @param label Label for food diary
	 * @param code The color code of the label.
	 */
	public FoodDiaryLabelBean(long rowid, long mid, String label, String code) {
		this.rowid = rowid;
		this.mid = mid;
		this.label = label;
		this.colorCode = code;
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
	/**
	 * @return the colorCode
	 */
	public String getColorCode() {
		return colorCode;
	}
	/**
	 * @param colorCode the colorCode to set
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	
}
