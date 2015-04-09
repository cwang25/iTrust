/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.base.FoodDiaryBaseAction;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryDailySummaryBean;
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
public class ViewFoodDiaryAction extends FoodDiaryBaseAction {

	/**
	 * Constructor.
	 * @param factory DAOFactory
	 * @param midString The logged in mid.
	 * @throws ITrustException
	 */
	public ViewFoodDiaryAction(DAOFactory factory, String midString)
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
	public List<FoodDiaryBean> getFoodDiaryListByOwnerID(long inputID) throws DBException{
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
		List<FoodDiaryBean> answer = foodDAO.getFoodDiaryListByOwnerID(ownerID);
		if(isPatient)
			loggingAction.logEvent(TransactionType.VIEW_FDIARY, mid, mid, "Patient viewed food diary.");
		else
			loggingAction.logEvent(TransactionType.HCP_VIEW_FDIARY, mid, inputID, "Nutrionist viewed patientls view food diary.");
		return answer;
	}
	
	
	/**
	 * The method to retrieve food diary daily summary list by owner ID.
	 * @param inputID  The owner ID to lookup(only available for Nutritionist (HCP).
	 * @return The result list.
	 * @throws DBException
	 */
	public List<FoodDiaryDailySummaryBean> getFoodDiaryDailySummaryListByOwnerID(long inputID) throws DBException{
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
		List<FoodDiaryDailySummaryBean> answer = foodDAO.getFoodDiaryDailySummaryListByOwnerID(ownerID);
		return answer;
	}
	
	/**
	 * Retrieve food diary record by specific record ID.
	 * Currently it doesn't have any transaction type associate it.
	 * @param recordID
	 * @return The result record.
	 * @throws DBException
	 */
	public FoodDiaryBean getFoodDiaryByID(long recordID) throws DBException{
		return foodDAO.getFoodDiaryByID(recordID);
	}
	/**
	 * Method to check if user is nutritionist.
	 * @return
	 */
	public boolean isNutritionist(){
		return super.isNutritionist;
	}
}
