package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.base.MacroNutrientProfileBaseAction;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class ViewMacroNutrientProfileAction extends
		MacroNutrientProfileBaseAction {

	public ViewMacroNutrientProfileAction(DAOFactory factory, String midString)
			throws ITrustException {
		super(factory, midString);
	}
	
	/**
	 * The method to retrieve food diary list by owner ID.
	 * @param inputID  The owner ID to lookup(only available for Nutritionist (HCP).
	 * @return The result list.
	 * @throws DBException
	 */
	public List<MacroNutrientProfileBean> getMacroNutrientProfileListByPlanID(long inputID) throws DBException{
		List<MacroNutrientProfileBean> answer = macroDAO.getProfileListByMacroNutrientPlanID(inputID);
		return answer;
	}
	/**
	 * Retrieve food diary record by specific record ID.
	 * Currently it doesn't have any transaction type associate it.
	 * @param recordID
	 * @return The result record.
	 * @throws DBException
	 */
	public MacroNutrientProfileBean getMacroNutrientProfileByRowID(long recordID) throws DBException{
		return macroDAO.getMacroNutrientProfileByID(recordID);
	}

}
