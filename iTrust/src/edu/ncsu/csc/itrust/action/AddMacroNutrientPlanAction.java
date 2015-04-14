package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.base.MacroNutrientPlanBaseAction;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
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
	 * Add MacroNutrientPlan into database.
	 * @param b MacroNutrientPlanBean to isnert.
	 * @return The long id of the new inserted record.
	 * @throws DBException
	 */
	public long addMacroNutrientPlan(MacroNutrientPlanBean b)throws DBException{
		b.setRowID( -1);
		long newid= -1;
		if(this.isNutritionist){
			boolean isInDeclaredList = false;
			List<PersonnelBean> personnelList = super.getFactory().getPatientDAO()
					.getDeclaredHCPs(b.getOwnerID());
			for (PersonnelBean tb : personnelList) {
				if (tb.getMID() == mid) {
					isInDeclaredList = true;
					break;
				}
			}
			if (isInDeclaredList){
				newid = macroDAO.insertMacroNutrientPlan(b);
				loggingAction.logEvent(TransactionType.HCP_ADD_MACRONUTRIENTPLAN, mid, b.getOwnerID(), "Nutritionist created patient's macronutrient plan.");
			}else{
				return -1;	
			}
				
		}else{
			newid = macroDAO.insertMacroNutrientPlan(b);
			loggingAction.logEvent(TransactionType.PATIENT_ADD_MACRONUTRIENTPLAN, mid, mid, "Patient created macronutrient plan.");

		}
		return newid;
	}
	/**
	 * Method for nutritionist to add macronutrientplan for patient.
	 * Add macronutrient plan by inputting string value.
	 * This method will check error for invalid input.
	 * @param oID OwnerID (Patient)
	 * @param p
	 * @param f
	 * @param carbs
	 * @return The new insert rowID.
	 * @throws FormValidationException 
	 * @throws DBException 
	 * @throws NumberFormatException 
	 */
	public long addMacroNutrientPlanByStrForNutritionist(String oID, String p,
			String f, String c, String t) throws FormValidationException, NumberFormatException, DBException {
		// if nutritionist is patient's designated HCP or not.
		boolean isInDeclaredList = false;
		List<PersonnelBean> personnelList = super.getFactory().getPatientDAO()
				.getDeclaredHCPs(Long.parseLong(oID));
		for (PersonnelBean b : personnelList) {
			if (b.getMID() == mid) {
				isInDeclaredList = true;
				break;
			}
		}
		if (isInDeclaredList)
			return addMacroNutrientPlanByStr( oID,p, f, c, t);
		else
			return -1;
	}
	/**
	 * Add macronutrient plan by inputting string value.
	 * This method will check error for invalid input.
	 * @param oID ownerID
	 * @param p protein
	 * @param f fat
	 * @param carbs carbonates
	 * @param t total calories
	 * @return The new inserted macronutrientplan rowID.
	 * @throws FormValidationException 
	 */
	public long addMacroNutrientPlanByStr(String oID, String p, String f, String c, String t) throws FormValidationException{
		MacroNutrientPlanBean b = null;
		long rowID = -1;
		long ownerID = -1;
		double protein = -1;
		double fat = -1;
		double carbs = -1;
		double totalCal = -1;
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
			protein = Double.parseDouble(p);
			if(protein <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for protein"+p+".";
			passed = false;
		}
		try {
			fat = Double.parseDouble(f);
			if(fat <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for fat.";
			passed = false;
		}
		try {
			carbs = Double.parseDouble(c);
			if(carbs <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for carbs.";
			passed = false;
		}
		try {
			totalCal = Double.parseDouble(t);
			if(totalCal <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			errorMsg += "Need valid value for total calories.";
			passed = false;
		}
		if(!passed){
			throw new FormValidationException(errorMsg);
		}else{
			b = new MacroNutrientPlanBean(ownerID, protein, fat, carbs, totalCal);
			try{
				rowID = addMacroNutrientPlan(b);
			}catch (DBException e){
				e.printStackTrace();
			}
		}
		return rowID;
	}

}
