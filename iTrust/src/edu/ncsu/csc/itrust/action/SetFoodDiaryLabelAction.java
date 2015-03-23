package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryLabelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SetFoodDiaryLabelAction {
	long mid;
	protected FoodDiaryLabelDAO foodDAO;
	protected EventLoggingAction loggingAction;
	private RemoveFoodDiaryLabelAction removeAction;
	
	/**
	 * The constructor
	 * @param factory DAOFactory (you know what it is.)
	 * @param midString The logged in user mid.
	 * @throws ITrustException
	 */
	public SetFoodDiaryLabelAction(DAOFactory factory, long mid) throws ITrustException {
		this.mid = mid;
		foodDAO = factory.getFoodDiaryLabelDAO();
		loggingAction = new EventLoggingAction(factory);
		removeAction = new RemoveFoodDiaryLabelAction(factory, mid);
	}
	
	/**
	 * Sets a label to a food diary date
	 * @param b Bean representing the label to be set
	 * @return The row id of the label set in the database
	 * @throws DBException
	 */
	public long setFoodDiaryLabel(FoodDiaryLabelSetBean b) throws DBException {	
		removeAction.removeFoodDiaryLabel(b); //remove if exists
		long ret = foodDAO.setFoodDiaryLabel(b);
		loggingAction.logEvent(TransactionType.PATIENT_SET_LABEL, mid, mid, "");
		
		return ret;
	}
}
