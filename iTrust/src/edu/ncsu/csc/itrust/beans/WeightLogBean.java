package edu.ncsu.csc.itrust.beans;

import java.sql.Date;

/**
 * A bean for storing information about a single weight loss/gain log
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class WeightLogBean {
	private long mid;
	private Date date;
	private double weight;
	private double chest;
	private double waist;
	private double upperarm;
	private double forearm;
	private double thigh;
	private double calves;
	private double neck;
	
	/**
	 * Constructor for a WeightLog bean
	 * @param mid MID that the log belongs to
	 * @param date SQL date of the log
	 * @param weight User's weight in lbs
	 * @param chest User's chest measurement in inches
	 * @param waist User's waist measurement in inches
	 * @param upperarm User's upper arm measurement in inches
	 * @param forearm User's forearm measurement in inches
	 * @param thigh User's thigh measurement in inches
	 * @param calves User's calf measurement in inches
	 * @param neck User's neck measurement in inches
	 */
	public WeightLogBean(long mid, Date date, double weight, double chest, double waist, double upperarm, double forearm, double thigh, double calves, double neck) {
		this.mid = mid;
		this.date = date;
		this.weight = weight;
		this.chest = chest;
		this.waist = waist;
		this.upperarm = upperarm;
		this.forearm = forearm;
		this.thigh = thigh;
		this.calves = calves;
		this.neck = neck;
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
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the chest
	 */
	public double getChest() {
		return chest;
	}
	/**
	 * @param chest the chest to set
	 */
	public void setChest(double chest) {
		this.chest = chest;
	}
	/**
	 * @return the waist
	 */
	public double getWaist() {
		return waist;
	}
	/**
	 * @param waist the waist to set
	 */
	public void setWaist(double waist) {
		this.waist = waist;
	}
	/**
	 * @return the upperarm
	 */
	public double getUpperarm() {
		return upperarm;
	}
	/**
	 * @param upperarm the upperarm to set
	 */
	public void setUpperarm(double upperarm) {
		this.upperarm = upperarm;
	}
	/**
	 * @return the forearm
	 */
	public double getForearm() {
		return forearm;
	}
	/**
	 * @param forearm the forearm to set
	 */
	public void setForearm(double forearm) {
		this.forearm = forearm;
	}
	/**
	 * @return the thigh
	 */
	public double getThigh() {
		return thigh;
	}
	/**
	 * @param thigh the thigh to set
	 */
	public void setThigh(double thigh) {
		this.thigh = thigh;
	}
	/**
	 * @return the calves
	 */
	public double getCalves() {
		return calves;
	}
	/**
	 * @param calves the calves to set
	 */
	public void setCalves(double calves) {
		this.calves = calves;
	}
	/**
	 * @return the neck
	 */
	public double getNeck() {
		return neck;
	}
	/**
	 * @param neck the neck to set
	 */
	public void setNeck(double neck) {
		this.neck = neck;
	}
}
