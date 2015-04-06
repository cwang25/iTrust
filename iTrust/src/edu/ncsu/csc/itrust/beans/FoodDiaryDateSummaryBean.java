/**
 * 
 */
package edu.ncsu.csc.itrust.beans;

import java.util.Date;

/**
 * This is a date summary food diary.
 * For assisting retrieving records for daily summary of food diary.
 * @author Chi-Han
 *
 */
public class FoodDiaryDateSummaryBean extends FoodDiaryBean {

	/**
	 * The constructor of daily summary food diary. 
	 * @param oID OwnerID
	 * @param d Date
	 * @param servCal
	 * @param fatg
	 * @param sodiummg
	 * @param carbsg
	 * @param sugarg
	 * @param fiberg
	 * @param proteing
	 */
	public FoodDiaryDateSummaryBean(long oID, Date d, MealTypes mealType,
			String foodName, double servNum, double servCal, double fatg,
			double sodiummg, double carbsg, double sugarg, double fiberg,
			double proteing) {
		super(oID, d, mealType, foodName, servNum, servCal, fatg, sodiummg,
				carbsg, sugarg, fiberg, proteing);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public FoodDiaryDateSummaryBean() {
		// TODO Auto-generated constructor stub
	}

}
