package edu.ncsu.csc.itrust.beans;

public class MacroNutrientProfileBean {
	/**
	 * Enum gender
	 * So, we can limit the input gender to be either
	 * male or female
	 * @author Chi-Han
	 *
	 */
	public enum Gender {
		/**
		 * Male
		 */
		MALE("male"),
		/**
		 * Female
		 */
		FEMALE("female");
		private final String genderStr;
		private Gender(String s) {
			genderStr = s;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return genderStr;
		}
	}; 
	/**
	 * Enum goals
	 * So, we can limit the input goals to be either
	 * lose weight, maintain weight, or gain weight.
	 * @author Chi-Han
	 *
	 */
	public enum Goal {
		/**
		 * Lose weight
		 */
		LOSE_WEIGHT("lose_weight"),
		/**
		 * Maintain weight
		 */
		MAINTAIN_WEIGHT("maintain_weight"),
		/**
		 * Gain weight
		 */
		GAIN_WEIGHT("gain_weight");
		private final String goalStr;
		private Goal(String s) {
			 goalStr = s;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return  goalStr;
		}
	}; 
	/**
	 * Enum goals
	 * So, we can limit the input goals to be either
	 * lose weight, maintain weight, or gain weight.
	 * @author Chi-Han
	 *
	 */
	public enum Activity {
		/**
		 * Sedntary
		 */
		SEDENTARY("sedentary"),
		/**
		 * Lightly active
		 */
		LIGHTLY_ACTIVE("lightly_active"),
		/**
		 * Moderately active
		 */
		MODERATELY_ACTIVE("moderately_active"),
		/**
		 * Very active
		 */
		VERY_ACTIVE("very_active"),
		/**
		 * Extremely active
		 */
		EXTREMELY_ACTIVE("extremely_active");
		private final String activityStr;
		private Activity(String s) {
			activityStr = s;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return  activityStr;
		}
	}; 
	/**
	 * Unique id from SQL database
	 * -1: No row ID (when create a new record before storing into database.)
	 */
	private long rowID = -1;
	private Gender gender;
	private int age;
	/**
	 * The weight in kg (kilogram).
	 */
	private double weight;
	/**
	 * The height in cm (centimeter).
	 */
	private double height;
	private Goal goal;
	private Activity act;
	/**
	 * The associated macronutrientplan rowID.
	 * Has to be the one from macronutrientplan SQL table.
	 */
	private long macroPlanID = -1;
	/**
	 * Constructor of the MacroNutrientProfileBean
	 * @param g Gender
	 * @param a Age
	 * @param w Weight in kg
	 * @param h Height in cm
	 * @param gl Goal
	 * @param at Activity
	 * @param mcrID Associate macronutrientplan's ID
	 */
	public MacroNutrientProfileBean(Gender g, int a, double w, double h, Goal gl, Activity at, long mcrID) {
		// TODO Auto-generated constructor stub
		this.gender = g;
		this.age = a;
		this.weight = w;
		this.height = h;
		this.goal = gl;
		this.act = at;
		this.macroPlanID = mcrID;
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
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
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
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * @return the goal
	 */
	public Goal getGoal() {
		return goal;
	}
	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}
	/**
	 * @return the act
	 */
	public Activity getAct() {
		return act;
	}
	/**
	 * @param act the act to set
	 */
	public void setAct(Activity act) {
		this.act = act;
	}
	/**
	 * @return the macroPlanID
	 */
	public long getMacroPlanID() {
		return macroPlanID;
	}
	/**
	 * @param macroPlanID the macroPlanID to set
	 */
	public void setMacroPlanID(long macroPlanID) {
		this.macroPlanID = macroPlanID;
	}
	
	
}
