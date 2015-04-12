package edu.ncsu.csc.itrust.beans;

public class MacroNutrientPlanBean {
	long rowID = -1;
	long ownerID;
	double protein;
	double fat;
	double carbs;
	double totalCal;
	/**
	 * MacroNutrientPlanBean
	 * @param owner  OwnerID (Patient)
	 * @param p Protein in gram
	 * @param f Fat in gram
	 * @param c Carbs in gram
	 * @param t total calories
	 */
	public MacroNutrientPlanBean(long owner, double p, double f, double c, double t) {
		// TODO Auto-generated constructor stub
		this.ownerID = owner;
		this.protein = p;
		this.fat = f;
		this.carbs = c;
		this.totalCal = t;
	}

	
	/**
	 * @return the totalCal
	 */
	public double getTotalCal() {
		return totalCal;
	}


	/**
	 * @param totalCal the totalCal to set
	 */
	public void setTotalCal(double totalCal) {
		this.totalCal = totalCal;
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
	 * @return the ownerID
	 */
	public long getOwnerID() {
		return ownerID;
	}
	/**
	 * @param ownerID the ownerID to set
	 */
	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}
	/**
	 * @return the protein
	 */
	public double getProtein() {
		return protein;
	}
	/**
	 * @param protein the protein to set
	 */
	public void setProtein(double protein) {
		this.protein = protein;
	}
	/**
	 * @return the fat
	 */
	public double getFat() {
		return fat;
	}
	/**
	 * @param fat the fat to set
	 */
	public void setFat(double fat) {
		this.fat = fat;
	}
	/**
	 * @return the carbs
	 */
	public double getCarbs() {
		return carbs;
	}
	/**
	 * @param carbs the carbs to set
	 */
	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}
}
