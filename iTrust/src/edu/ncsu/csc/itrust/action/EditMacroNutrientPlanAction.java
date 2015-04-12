package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.base.MacroNutrientPlanBaseAction;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class EditMacroNutrientPlanAction extends MacroNutrientPlanBaseAction{

	/**
	 * The constructor.
	 * @param factory
	 * @param midString
	 * @throws ITrustException
	 */
	public EditMacroNutrientPlanAction(DAOFactory factory, String midString)
			throws ITrustException {
		super(factory, midString);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Edit macronutrient plan into database.
	 * @param b macronutrientbean to insert.
	 * @return The long id of the new inserted record.
	 * @throws DBException 
	 */
	public int editMacroNutrientPlan(MacroNutrientPlanBean b) throws DBException{		
		int row_update = macroDAO.updateMacroNutrientPlan(b);
		if(!this.isNutritionist)
			loggingAction.logEvent(TransactionType.PATIENT_EDIT_MACRONUTRIENTPLAN, mid, mid, "Patient edited a macronutrient plan.");
		else
			loggingAction.logEvent(TransactionType.HCP_EDIT_MACRONUTRIENTPLAN, mid, b.getOwnerID(), "Nutritionist edited patient's macronutrient plan.");
		return row_update;
	}
	
	/**
	 * Method for Nutritionist role.
	 * Edit  macronutrient plan by inputting string value.
	 * This method will check error for invalid input.
	 * @param oldMacronutrientPlanRowID RowID for the record to update.
	 * @param oID OwnderID (Patient)
	 * @param p Protein
	 * @param f Fat
	 * @param carbs Carbonates
	 * @return Rows that get updated
	 * @throws FormValidationException 
	 * @throws DBException 
	 */
	public int editMacroNutrientPlanByStrForNutritionist(long oldMacronutrientPlanRowID, String oID, String p, String f, String c) throws FormValidationException, DBException{
		//if nutritionist is patient's designated HCP or not.
		boolean isInDeclaredList = false;
		List<PersonnelBean> personnelList = super.getFactory().getPatientDAO().getDeclaredHCPs(Long.parseLong(oID));
		for(PersonnelBean b : personnelList){
			if(b.getMID() == mid){
				isInDeclaredList = true;
				break;
			}
		}
		if(isInDeclaredList)
			return editMacroNutrientPlanByStr(oldMacronutrientPlanRowID, oID, p, f, c);
		else
			return 0;
	}
	
	/**
	 * Edit  macronutrient plan by inputting string value.
	 * This method will check error for invalid input.
	 * @param oldMacronutrientPlanRowID RowID for the record to update.
	 * @param oID OwnderID
	 * @param p Protein
	 * @param f Fat
	 * @param carbs Carbonates
	 * @return Rows that get updated
	 * @throws FormValidationException 
	 */
	public int editMacroNutrientPlanByStr(long oldMacronutrientPlanRowID, String oID, String p, String f, String c) throws FormValidationException{
		MacroNutrientPlanBean b = null;
		int rowUpdate = 0;
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
			b.setRowID(oldMacronutrientPlanRowID);
			try{
				rowUpdate = editMacroNutrientPlan(b);
			}catch (DBException e){
				e.printStackTrace();
			}
		}
		return rowUpdate;
	}
	/**
	 * Method to remove macronutrient plan record from SQL.
	 * The macroNutrientToRemove has to be the one retrieved from database. (The objects in table list.) instead of creating a new one.
	 * Only MacroNutrientPlanBean object from DAO will has valid rowID attribute.
	 * rowID attribute is essential for DAO to decide which record to remove. 
	 * @param macroNutrientToRemove FoodDiaryBean which is going to be removed from database.
	 * @return The MacroNutrientPlanBean that has been removed. 
	 */
	public MacroNutrientPlanBean deleteFoodDiary(MacroNutrientPlanBean macroNutrientToRemove){
		MacroNutrientPlanBean removedMacroNutrientPlanBean = null;
		try {
			removedMacroNutrientPlanBean = macroDAO.removeMacroNutrientPlan(macroNutrientToRemove);
			if(!this.isNutritionist)
				loggingAction.logEvent(TransactionType.PATIENT_REMOVE_MACRONUTRIENTPLAN, mid, mid, "Patient removed a macronutrient plan.");
			else
				loggingAction.logEvent(TransactionType.HCP_REMOVE_MACRONUTRIENTPLAN, mid, macroNutrientToRemove.getOwnerID(), "Nutritionist removed patient's macronutrient plan.");

		} catch (DBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			removedMacroNutrientPlanBean = null;
		}
		return removedMacroNutrientPlanBean;
	}
}
