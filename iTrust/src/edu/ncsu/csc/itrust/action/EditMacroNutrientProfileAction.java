package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.MacroNutrientProfileBaseAction;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class EditMacroNutrientProfileAction extends
		MacroNutrientProfileBaseAction {

	public EditMacroNutrientProfileAction(DAOFactory factory, String midString)
			throws ITrustException {
		super(factory, midString);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Edit macronutrientprofile in database.
	 * @param b MacroNutrientPlanBean to isnert.
	 * @return The long id of the new inserted record.
	 * @throws DBException
	 */
	public int editMacroNutrientProfile(MacroNutrientProfileBean b)throws DBException{
		int rowUpdate= 0;
		//No need to check if nutitionist is designated hcp, since the ownerID is not in this record plus
		//It requires to know the macronutrientplan first in order to isnert new record to profile, which plan has already checked it..
		if(this.isNutritionist){
			rowUpdate = macroDAO.updateMacroNutrientProfile(b);
			//Didn't plan to add loggingevent since it will get duplicated by the Macronutrientplan related operations.
			//loggingAction.logEvent(TransactionType.HCP_ADD_MACRONUTRIENTPLAN, mid, b.getOwnerID(), "Nutritionist created patient's macronutrient plan.");
		}else{
			rowUpdate = macroDAO.updateMacroNutrientProfile(b);
			//loggingAction.logEvent(TransactionType.PATIENT_ADD_MACRONUTRIENTPLAN, mid, mid, "Patient created macronutrient plan.");

		}
		return rowUpdate;
	}
	
	/**
	 * The method to update mcaroNutrientProfile
	 * @param oldRowID RowID of the profile to udpate.
	 * @param gend Gender
	 * @param a Age
	 * @param w Weight
 	 * @param h Height
	 * @param g Goal
 	 * @param act Activity
	 * @param mcrID MacroNutiretPlan Row ID.
	 * @return Rows get impacted.
	 * @throws FormValidationException
	 */
	public int editMacroNutrientProfileByStr(long oldRowID, String gend, String a, String w, String h, String g, String act, String mcrID) throws FormValidationException{
		MacroNutrientProfileBean b = null;
		MacroNutrientProfileBean.Gender gender = null;
		int rowUpdate = -1;
		int age = -1;
		double weight = -1;
		double height = -1;
		MacroNutrientProfileBean.Goal goal = null;
		MacroNutrientProfileBean.Activity activity = null;
		long macroplanID = -1;
		String errorMsg="";
		boolean passed = true;
		try{
			gender = MacroNutrientProfileBean.Gender.valueOf(gend.toUpperCase());
		}catch(IllegalArgumentException e){
			errorMsg +="Need valid gender input.";
			passed = false;
		}
		try{
			age = Integer.parseInt(a);
		}catch(NumberFormatException e){
			errorMsg += "Need valid age input.";
			passed = false;
		}
		try{
			weight = Double.parseDouble(w);
		}catch(NumberFormatException e){
			errorMsg += "Need valid weight input.";
			passed = false;
		}
		try{
			height = Double.parseDouble(h);
		}catch(NumberFormatException e){
			errorMsg += "Need valid height input.";
			passed = false;
		}
		try{
			goal = MacroNutrientProfileBean.Goal.valueOf(g.toUpperCase());
		}catch(IllegalArgumentException e){
			errorMsg +="Need valid goal input.";
			passed = false;
		}
		try{
			activity = MacroNutrientProfileBean.Activity.valueOf(act.toUpperCase());
		}catch(IllegalArgumentException e){
			errorMsg +="Need valid activity input.";
			passed = false;
		}
		if(!passed){
			throw new FormValidationException(errorMsg);
		}else{
			b = new MacroNutrientProfileBean(gender, age, weight, height, goal, activity, macroplanID);
			b.setRowID(oldRowID);
			try{
				rowUpdate = this.editMacroNutrientProfile(b);
			}catch (DBException e){
				e.printStackTrace();
			}
		}
		return rowUpdate;
	}
	
	/**
	 * Method to remove macronutrient profile record from SQL.
	 * The macroNutrientToRemove has to be the one retrieved from database. (The objects in table list.) instead of creating a new one.
	 * Only MacroNutrientProfileBean object from DAO will has valid rowID attribute.
	 * rowID attribute is essential for DAO to decide which record to remove. 
	 * @param macroNutrientToRemove FoodDiaryBean which is going to be removed from database.
	 * @return The MacroNutrientPlanBean that has been removed. 
	 */
	public MacroNutrientProfileBean deleteMacroNutrientPlan(MacroNutrientProfileBean macroNutrientToRemove){
		MacroNutrientProfileBean removedMacroNutrientPlanBean = null;
		try {
			removedMacroNutrientPlanBean = macroDAO.removeMacroNutrientProfile(macroNutrientToRemove);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			removedMacroNutrientPlanBean = null;
		}
		return removedMacroNutrientPlanBean;
	}

}
