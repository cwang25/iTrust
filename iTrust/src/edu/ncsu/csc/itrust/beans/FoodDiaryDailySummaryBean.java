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
public class FoodDiaryDailySummaryBean extends FoodDiaryBean {

	/**
	 * The constructor of daily summary food diary. 
	 * Since its daily summary of food diaries, so there is no food name, no serving number, and no meal type.
	 * The servCal will then present as total calories for that day.
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
	public FoodDiaryDailySummaryBean(long oID, Date d, double servCal, double fatg,
			double sodiummg, double carbsg, double sugarg, double fiberg,
			double proteing) {
		super(oID, d, null, null, -1, servCal, fatg, sodiummg,
				carbsg, sugarg, fiberg, proteing);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#getNameOfFood()
	 */
	/**
	 * unsupport method for FoodDiaryDateSummaryBean.
	 * @return
	 */
	@Override
	public String getNameOfFood() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("getNameOfFood for FoodDiaryDateSummaryBean");
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#setNameOfFood(java.lang.String)
	 */
	/**
	 * unsupport method for FoodDiaryDateSummaryBean.
	 * @return
	 */
	@Override
	public void setNameOfFood(String nameOfFood) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("setNameOfFood for FoodDiaryDateSummaryBean");
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#getNumberOfServings()
	 */
	/**
	 * unsupport method for FoodDiaryDateSummaryBean.
	 * @return
	 */
	@Override
	public double getNumberOfServings() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("getNumberOfServings for FoodDiaryDateSummaryBean");
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#setNumberOfServings(double)
	 */
	/**
	 * unsupport method for FoodDiaryDateSummaryBean.
	 * @return
	 */
	@Override
	public void setNumberOfServings(double numberOfServings) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("setNumberOfServings for FoodDiaryDateSummaryBean");
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#getRowID()
	 */
	/**
	 * unsupport method for FoodDiaryDateSummaryBean.
	 * @return
	 */
	@Override
	public long getRowID() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("getRowID for FoodDiaryDateSummaryBean");
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#setRowID(long)
	 */
	/**
	 * unsupport method for FoodDiaryDateSummaryBean.
	 * @return
	 */
	@Override
	public void setRowID(long rowID) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("setRowID for FoodDiaryDateSummaryBean");
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc.itrust.beans.FoodDiaryBean#totalCalories()
	 */
	/**
	 *
	 * FoodDiaryDateSummaryBean will use caloriesPerServing as total calories.
	 * @return
	 */
	@Override
	public double totalCalories() {
		// TODO Auto-generated method stub
		return this.getCaloriesPerServing();
	}

	

}
