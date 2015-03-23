package edu.ncsu.csc.itrust.beans;

import java.util.Date;

public class FoodDiaryBean {
	/**
	 * Enum MealTypes
	 * So, we can limit the input meal type to be either
	 * Breakfast, Lunch, Dinner, or Snack.
	 * @author Chi-Han
	 *
	 */
	public enum MealTypes {
		/**
		 * Breakfast
		 */
		BREAKFAST("Breakfast"),
		/**
		 * Lunch
		 */
		LUNCH("Lunch"), 
		/**
		 * Dinner
		 */
		DINNER("Dinner"), 
		/**
		 * Snack
		 */
		SNACK("Snack");
		private final String typeMealStr;
		private MealTypes(String s) {
			typeMealStr = s;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return typeMealStr;
		}
	};
	/**
	 * A unique id from SQL table.
	 * It is auto increment row id in SQL table, only use for retrieve records from table,
	 * but not inserting record.
	 * 
	 * -1 : No row ID (normally will occur when user create a new food diary instead of retrieving existing record form SQL table.
	 */
	private long rowID = -1;
	private long ownerID;
	private Date date;
	private MealTypes typeOfMeal;
	private String nameOfFood;
	private double numberOfServings;
	private double caloriesPerServing;
	private double gramsOfFat;
	private double milligramsOfSodium;
	private double gramsOfCarbs;
	private double gramsOfSugar;
	private double gramsOfFiber;
	private double gramsOfProtein;
	/**
	 * Constructor of the Food Diary.
	 * @param oID Owner's ID.
	 * @param d Date of the food diary.
	 * @param mealType Type of the meal "Breakfast","Lunch","Dinner","Snack". (FoodDiaryBean.<MealType>).
	 * @param foodName Name of the food.
	 * @param servNum Number of servings.
	 * @param servCal Calories per serving.
	 * @param fatg Grams of fat.
	 * @param sodiummg Milligram of sodium.
	 * @param carbsg Grams of carbs.
	 * @param sugarg Grams of sugar.
	 * @param fiberg Grams of fiber.
	 * @param proteing Grams of protein.
	 */
	public FoodDiaryBean(long oID, Date d, MealTypes mealType, String foodName,
			double servNum, double servCal, double fatg, double sodiummg, double carbsg,
			double sugarg, double fiberg, double proteing) {
		this.ownerID = oID;
		//Make a copy of the date, for security reasons from jenkin's bug finder.
		//So, bad user will not be able to change the data of this object's attributes by bypassing the methods call
		//i.e. keep the data reference and modify the data which indirectly will also change data here if pass in data reference directly.
		Date copyOfDate = new Date();
		copyOfDate.setTime(d.getTime());
		this.date = copyOfDate;
		this.typeOfMeal = mealType;
		this.nameOfFood = foodName;
		this.numberOfServings = servNum;
		this.caloriesPerServing = servCal;
		this.gramsOfFat = fatg;
		this.milligramsOfSodium = sodiummg;
		this.gramsOfCarbs = carbsg;
		this.gramsOfSugar = sugarg;
		this.gramsOfFiber = fiberg;
		this.gramsOfProtein = proteing;
	}
	/**
	 * A blank constructor for FoodDiaryBean.
	 * If use this construct, will need to specify parameters explicitly.
	 * All attribute will be in default value.
	 * In case if needed.
	 */
	public FoodDiaryBean(){
		this.ownerID = -1;
		this.date = new Date();
		this.typeOfMeal = FoodDiaryBean.MealTypes.BREAKFAST;
		this.nameOfFood = "";
		this.numberOfServings = 0.0;
		this.caloriesPerServing = 0.0;
		this.gramsOfFat = 0.0;
		this.milligramsOfSodium = 0.0;
		this.gramsOfCarbs = 0.0;
		this.gramsOfSugar = 0.0;
		this.gramsOfFiber = 0.0;
		this.gramsOfProtein = 0.0;
	}
	/**
	 * Total calories of the food diary.
	 * Basically a number of serving * caloriesPerServing.
	 * @return Total calories of the food diary.
	 */
	public double totalCalories(){
		return numberOfServings * caloriesPerServing;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		//Make a copy of the date, for security reasons from jenkin's bug finder.
		Date returnDate = new Date();
		returnDate.setTime(date.getTime());
		return returnDate;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		//Make a copy of the date, for security reasons from jenkin's bug finder.
		Date copyOfDate = new Date();
		copyOfDate.setTime(date.getTime());
		this.date = copyOfDate;
	}

	/**
	 * @return the typeOfMeal
	 */
	public MealTypes getTypeOfMeal() {
		return typeOfMeal;
	}

	/**
	 * @param typeOfMeal the typeOfMeal to set
	 */
	public void setTypeOfMeal(MealTypes typeOfMeal) {
		this.typeOfMeal = typeOfMeal;
	}

	/**
	 * @return the nameOfFood
	 */
	public String getNameOfFood() {
		return nameOfFood;
	}

	/**
	 * @param nameOfFood the nameOfFood to set
	 */
	public void setNameOfFood(String nameOfFood) {
		this.nameOfFood = nameOfFood;
	}

	/**
	 * @return the numberOfServings
	 */
	public double getNumberOfServings() {
		return numberOfServings;
	}

	/**
	 * @param numberOfServings the numberOfServings to set
	 */
	public void setNumberOfServings(double numberOfServings) {
		this.numberOfServings = numberOfServings;
	}

	/**
	 * @return the caloriesPerServing
	 */
	public double getCaloriesPerServing() {
		return caloriesPerServing;
	}

	/**
	 * @param caloriesPerServing the caloriesPerServing to set
	 */
	public void setCaloriesPerServing(double caloriesPerServing) {
		this.caloriesPerServing = caloriesPerServing;
	}

	/**
	 * @return the gramsOfFat
	 */
	public double getGramsOfFat() {
		return gramsOfFat;
	}

	/**
	 * @param gramsOfFat the gramsOfFat to set
	 */
	public void setGramsOfFat(double gramsOfFat) {
		this.gramsOfFat = gramsOfFat;
	}

	/**
	 * @return the milligramsOfSodium
	 */
	public double getMilligramsOfSodium() {
		return milligramsOfSodium;
	}

	/**
	 * @param milligramsOfSodium the milligramsOfSodium to set
	 */
	public void setMilligramsOfSodium(double milligramsOfSodium) {
		this.milligramsOfSodium = milligramsOfSodium;
	}

	/**
	 * @return the gramsOfCarbs
	 */
	public double getGramsOfCarbs() {
		return gramsOfCarbs;
	}

	/**
	 * @param gramsOfCarbs the gramsOfCarbs to set
	 */
	public void setGramsOfCarbs(double gramsOfCarbs) {
		this.gramsOfCarbs = gramsOfCarbs;
	}

	/**
	 * @return the gramsOfSugar
	 */
	public double getGramsOfSugar() {
		return gramsOfSugar;
	}

	/**
	 * @param gramsOfSugar the gramsOfSugar to set
	 */
	public void setGramsOfSugar(double gramsOfSugar) {
		this.gramsOfSugar = gramsOfSugar;
	}

	/**
	 * @return the gramsOfFiber
	 */
	public double getGramsOfFiber() {
		return gramsOfFiber;
	}

	/**
	 * @param gramsOfFiber the gramsOfFiber to set
	 */
	public void setGramsOfFiber(double gramsOfFiber) {
		this.gramsOfFiber = gramsOfFiber;
	}

	/**
	 * @return the gramsOfProtein
	 */
	public double getGramsOfProtein() {
		return gramsOfProtein;
	}

	/**
	 * @param gramsOfProtein the gramsOfProtein to set
	 */
	public void setGramsOfProtein(double gramsOfProtein) {
		this.gramsOfProtein = gramsOfProtein;
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

	
}
