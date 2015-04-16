package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryLabelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class RemoveFoodDiaryLabelAction {
	long mid;
	protected FoodDiaryLabelDAO foodDAO;
	protected EventLoggingAction loggingAction;
	
	/**
	 * The constructor
	 * @param factory DAOFactory (you know what it is.)
	 * @param midString The logged in user mid.
	 * @throws ITrustException
	 */
	public RemoveFoodDiaryLabelAction(DAOFactory factory, long mid) throws ITrustException {
		this.mid = mid;
		foodDAO = factory.getFoodDiaryLabelDAO();
		loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Removes a label from a day containing food diary entries
	 * @param bean Bean representing the label to remove
	 * @return The removed label
	 * @throws DBException
	 */
	public FoodDiaryLabelSetBean removeFoodDiaryLabel(FoodDiaryLabelSetBean bean) throws DBException {
		FoodDiaryLabelSetBean ret = foodDAO.removeFoodDiaryLabel(bean);
		loggingAction.logEvent(TransactionType.PATIENT_REMOVE_ATTACHED_LABEL, mid, mid, "");
		return ret;
	}
}
