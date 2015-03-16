/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.action.base.FoodDiaryBaseAction;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * @author Chi-Han
 *
 */
public class AddFoodDiaryAction extends FoodDiaryBaseAction {

	/**
	 * The constructor
	 * @param factory DAOFactory (you know what it is.)
	 * @param midString The logged in user mid.
	 * @throws ITrustException
	 */
	public AddFoodDiaryAction(DAOFactory factory, String midString)
			throws ITrustException {
		super(factory, midString);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Add food diary into database.
	 * @param b FoodDiaryBean to insert.
	 * @return The long id of the new inserted record.
	 * @throws DBException 
	 */
	public long addFoodDiary(FoodDiaryBean b) throws DBException{		
		//Sanitize the rowID, since adding food diary is not able to explicitly declare the row id.
		//row id should be 100% created and decided by SQL.
		b.setRowID(-1);
		long newid = foodDAO.insertFoodDiary(b);
		loggingAction.logEvent(TransactionType.PATIENT_CREATE_FDIARY, mid, mid, "Patient created food diary.");
		return newid;
	}
	/**
	 * Add food diary from string input from jsp page.
	 * It will handle the data format and throw appropriate error messages.
	 * @param date
	 * @param type
	 * @param nameOfFood
	 * @param numberOfServings
	 * @param caloriesPerServing
	 * @param gramsOfFat
	 * @param milligramsOfSodium
	 * @param gramsOfCarbs
	 * @param gramsOfSugar
	 * @param gramsOfFiber
	 * @param gramsOfProtein
	 * @return Return the last insert row id, -1 : insertion failed.
	 * @throws FormValidationException 
	 */
	public long addStrFoodDiary(String date, String type, String nameOfFood, String numberOfServings, String caloriesPerServing, String gramsOfFat, String milligramsOfSodium, String gramsOfCarbs, String gramsOfSugar, String gramsOfFiber, String gramsOfProtein) throws FormValidationException {
		Date dateObj = null;
		FoodDiaryBean b = null;
		String errorMsg = "";
		long rowID = -1;
		boolean passed = true;
		double servingsNumber = -1;
		double perServingCalories = -1;
		double fatGrams = -1;
		double sodiumMilligrams = -1;
		double carbsGrams = -1;
		double sugarGrams = -1;
		double fiberGrams = -1;
		double proteinGrams = -1;
		FoodDiaryBean.MealTypes typeMeal = null;
		if(nameOfFood == null || nameOfFood.length() <= 0){
			//Because of the FormValidationException message formatting issues.
			//So addd a <br/> in front of the message.
			errorMsg += "<br/>The name of food cannot be empty.<br/>";
			passed = false;
		}
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateFormat.setLenient(false);
			dateObj = dateFormat.parse(date);
			Date now = new Date();
			if(dateObj.after(now)){
				//The food diary date has to be past or current date.
				throw new IllegalArgumentException();
			}
		} catch (ParseException e){
			errorMsg += "Accepted date format is mm/dd/yyyy.<br/>";
			passed = false;
		} catch (IllegalArgumentException e){
			errorMsg += "Date has to be either past or current date. (No future date.)<br/>";
			passed = false;
		}
		try {
			servingsNumber = Double.parseDouble(numberOfServings);
			if(servingsNumber <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Number of servings has to be a positive number.<br/>";
			passed = false;
		}
		try {
			perServingCalories = Double.parseDouble(caloriesPerServing);
			if(perServingCalories < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Calories per serving has to be 0 or greater.<br/>";
			passed = false;
		}
		try {
			fatGrams = Double.parseDouble(gramsOfFat);
			if(fatGrams < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Grams of fat has to be 0 or greater.<br/>";
			passed = false;
		}
		try {
			sodiumMilligrams = Double.parseDouble(milligramsOfSodium);
			if(sodiumMilligrams < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Milligrams of sodium has to be 0 or greater.<br/>";
			passed = false;
		}
		try {
			carbsGrams = Double.parseDouble(gramsOfCarbs);
			if(carbsGrams < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Grams of carbs has to be 0 or greater.<br/>";
			passed = false;
		}
		try {
			sugarGrams = Double.parseDouble(gramsOfSugar);
			if(sugarGrams < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Grams of sugar has to be 0 or greater.<br/>";
			passed = false;
		}
		try {
			fiberGrams = Double.parseDouble(gramsOfFiber);
			if(fiberGrams < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Grams of fiber has to be 0 or greater.<br/>";
			passed = false;
		}
		try {
			proteinGrams = Double.parseDouble(gramsOfProtein);
			if(proteinGrams < 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Grams of protein has to be 0 or greater.<br/>";
			passed = false;
		}
		try{
			typeMeal =  FoodDiaryBean.MealTypes.valueOf(type.toUpperCase());
		} catch(IllegalArgumentException e){
			errorMsg += "Meal type has to be either Breakfast, Lunch, Dinner or Snack.<br/>";
			passed = false;
		}
		if(!passed){
			throw new FormValidationException(errorMsg);
		}else{
			b = new FoodDiaryBean(super.mid, dateObj,typeMeal, nameOfFood, servingsNumber, perServingCalories, fatGrams, sodiumMilligrams, carbsGrams, sugarGrams, fiberGrams, proteinGrams);
			try {
				rowID = addFoodDiary(b);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rowID;
	}

}
