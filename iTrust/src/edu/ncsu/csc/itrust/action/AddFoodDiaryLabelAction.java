package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryLabelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class AddFoodDiaryLabelAction {
	
	long mid;
	protected FoodDiaryLabelDAO foodDAO;
	protected EventLoggingAction loggingAction;
	
	/**
	 * The constructor
	 * @param factory DAOFactory (you know what it is.)
	 * @param midString The logged in user mid.
	 * @throws ITrustException
	 */
	public AddFoodDiaryLabelAction(DAOFactory factory, long mid) throws ITrustException {
		this.mid = mid;
		foodDAO = factory.getFoodDiaryLabelDAO();
		loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Adds a label to choose from to the database. Labels can only contain letters and numbers.
	 * @param b Bean representing the label to be added
	 * @return The row id of the label in the database
	 * @throws FormValidationException
	 * @throws DBException
	 */
	public long addFoodDiaryLabel(FoodDiaryLabelBean b) throws FormValidationException, DBException {		
		for(char c : b.getLabel().toCharArray()){
			if(!Character.isLetterOrDigit(c)) {
				throw new FormValidationException();
			}
		}
		
		long ret = foodDAO.insertFoodDiaryLabel(b);
		loggingAction.logEvent(TransactionType.PATIENT_ADD_LABEL, mid, mid, "");
		
		return ret;
	}
}
