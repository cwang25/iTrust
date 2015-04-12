package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.MacroNutrientPlanBaseAction;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class AddMacroNutrientPlanAction extends MacroNutrientPlanBaseAction {
	/**
	 * The constructor.
	 * @param factory
	 * @param midString
	 * @throws ITrustException
	 */
	public AddMacroNutrientPlanAction(DAOFactory factory, String midString)
			throws ITrustException {
		super(factory, midString);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Add food diary into database.
	 * @param b MacroNutrientPlanBean to isnert.
	 * @return The long id of the new inserted record.
	 * @throws DBException
	 */
	public long addMacroNutrientPlan(MacroNutrientPlanBean b)throws DBException{
		b.setRowID( -1);
		long newid = macroDAO.insertMacroNutrientPlan(b);
		loggingAction.logEvent(TransactionType.PATIENT_ADD_MACRONUTRIENTPLAN, mid, mid, "Patient created macronutrient plan.");
		return newid;
	}
	/**
	 * Add macronutrient plan by inputting string value.
	 * This method will check error for invalid input.
	 * @param oID
	 * @param p
	 * @param f
	 * @param carbs
	 * @return
	 * @throws FormValidationException 
	 */
	public long addMacroNutrientPlanByStr(String oID, String p, String f, String c) throws FormValidationException{
		MacroNutrientPlanBean b = null;
		long rowID = -1;
		long ownerID = -1;
		long protein = -1;
		long fat = -1;
		long carbs = -1;
		String errorMsg="";
		boolean passed = true;
		try {
			ownerID = Long.parseLong(oID);
			if(ownerID <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Missing ownerID";
			passed = false;
		}
		try {
			protein = Long.parseLong(p);
			if(protein <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for protein.";
			passed = false;
		}
		try {
			fat = Long.parseLong(f);
			if(ownerID <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for fat.";
			passed = false;
		}
		try {
			carbs = Long.parseLong(c);
			if(ownerID <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for carbs.";
			passed = false;
		}
		if(!passed){
			throw new FormValidationException(errorMsg);
		}else{
			b = new MacroNutrientPlanBean(ownerID, protein, fat, carbs);
			try{
				rowID = addMacroNutrientPlan(b);
			}catch (DBException e){
				e.printStackTrace();
			}
		}
		return rowID;
	}

}
