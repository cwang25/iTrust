package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.base.MacroNutrientPlanBaseAction;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
/**
 * A specific view food diary action class.
 * @author Chi-Han
 *
 */
public class ViewMacroNutrientPlanAction extends MacroNutrientPlanBaseAction {
	/**
	 * Constructor 
	 * @param factory DAOFactory
	 * @param midString The logged in mid.
	 * @throws ITrustException ITrustException
	 */
	public ViewMacroNutrientPlanAction(DAOFactory factory, String midString)
			throws ITrustException {
		super(factory, midString);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The method to retrieve food diary list by owner ID.
	 * @param inputID  The owner ID to lookup(only available for Nutritionist (HCP).
	 * @return The result list.
	 * @throws DBException
	 */
	public List<MacroNutrientPlanBean> getMacroNutrientPlanListByOwnerID(long inputID) throws DBException{
		long ownerID = inputID;
		boolean isPatient = super.getFactory().getPatientDAO().checkPatientExists(mid);
		//Get Declared HCP.
		List<PersonnelBean> personnelList = super.getFactory().getPatientDAO().getDeclaredHCPs(inputID);
		//Check if the HCP (Nutritionist) MID is in the patient's declared list.
		//Doesn't matter if login ID is HCP or Patient.
		//If it is patient this is always FALSE plus it will short-circuit the if loop later.
		boolean isInDeclaredList = false;
		for(PersonnelBean b : personnelList){
			if(b.getMID() == mid){
				isInDeclaredList = true;
				break;
			}
		}
		//Try to prevent patient from using some weird hack to see others info.
		if(isPatient){
			ownerID = mid;
		}else if(!super.isNutritionist || !isInDeclaredList){
			return null;
		}
		List<MacroNutrientPlanBean> answer = macroDAO.getMacroNutrientPlanByOwnerID(ownerID);
		if(isPatient)
			loggingAction.logEvent(TransactionType.PATIENT_VIEW_MACRONUTRIENTPLAN, mid, mid, "Patient viewed macronutrient plan.");
		else
			loggingAction.logEvent(TransactionType.HCP_VIEW_MACRONUTRIENTPLAN, mid, inputID, "Nutrionist viewed patients macronutrient plan.");
		return answer;
	}
	/**
	 * Retrieve food diary record by specific record ID.
	 * Currently it doesn't have any transaction type associate it.
	 * @param recordID
	 * @return The result record.
	 * @throws DBException
	 */
	public MacroNutrientPlanBean getMacroNutrientPlanByRowID(long recordID) throws DBException{
		return macroDAO.getMacroNutrientPlanByRowID(recordID);
	}

}
