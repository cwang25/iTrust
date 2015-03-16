package edu.ncsu.csc.itrust.beans;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.ncsu.csc.itrust.enums.FlagValue;

/**
 * FlagsBean represents a flag.
 *
 */
public class FlagsBean {
	private long fid;
	private long mid;
	private long pregId;
	private FlagValue flagValue;
	private boolean flagged;
	
	public static final int FHR_MAX_TRIGGER = 170;
	public static final int FHR_MIN_TRIGGER = 105;
	public static final int SYS_BP_MAX_TRIGGER = 140;
	public static final int DIA_BP_MAX_TRIGGER = 90;
	// Chi-Han
	// Change all to unmodifiable list collection object.
	// final array will only make reference immutable but not the data within it.
	// So changed it for security issues.
	//public static final double WeightGainBMIBounds[] = {18.5, 24.9, 29.9,999};
	public static final List<Double> WeightGainBMIBounds = Collections.unmodifiableList(Arrays.asList(18.5d, 24.9d, 29.9d,999d));
	//public static final double WeightGainBMIMin[] = {28,25,15,11};
	public static final List<Double> WeightGainBMIMin = Collections.unmodifiableList(Arrays.asList(28d,25d,15d,11d));
	//public static final double WeightGainBMIMax[] = {40,35,25,20};
	public static final List<Double> WeightGainBMIMax = Collections.unmodifiableList(Arrays.asList(40d,35d,25d,20d));
	//public static final double WeightGainTwinsBMIMin[] = {37,37,31,25};
	public static final List<Double> WeightGainTwinsBMIMin = Collections.unmodifiableList(Arrays.asList(37d,37d,31d,25d));
	//public static final double WeightGainTwinsBMIMax[] = {54,54,50,42};
	public static final List<Double> WeightGainTwinsBMIMax = Collections.unmodifiableList(Arrays.asList(54d,54d,50d,42d));
	
	/**
	 * Returns the FID
	 * @return
	 */
	public long getFid() {
		return fid;
	}
	
	/**
	 * Sets the FID
	 * @param fid
	 */
	public void setFid(long fid) {
		this.fid = fid;
	}
	
	/**
	 * Returns the MID
	 * @return
	 */
	public long getMid() {
		return mid;
	}
	
	/**
	 * Sets the MID
	 * @param mid
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}
	
	/**
	 * Returns the flag enum value
	 * @return
	 */
	public FlagValue getFlagValue() {
		return flagValue;
	}
	
	/**
	 * Sets the flag enum value
	 * @param flagValue
	 */
	public void setFlagValue(FlagValue flagValue) {
		this.flagValue = flagValue;
	}
	
	/**
	 * Returns true if the flag is set
	 * @return
	 */
	public boolean isFlagged() {
		return flagged;
	}
	
	/**
	 * Sets the flag boolean value
	 * @param flagged
	 */
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	
	/**
	 * Returns the pregnancy ID
	 * @return
	 */
	public long getPregId() {
		return pregId;
	}
	
	/**
	 * Sets the pregnancy ID
	 * @param pregId
	 */
	public void setPregId(long pregId) {
		this.pregId = pregId;
	}
	
}
