package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.MacroNutrientPlanBaseAction;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
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
	 * @deprecated Not yet complete.
	 * @param oID
	 * @param p
	 * @param f
	 * @param carbs
	 * @return
	 */
	public long addMacroNutrientPlanByStr(String oID, String p, String f, String c){
		long ownerID = -1;
		long protein = -1;
		long fat = -1;
		long carbs = -1;
		return 0;
	}

}
