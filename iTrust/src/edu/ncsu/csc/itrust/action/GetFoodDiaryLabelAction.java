package edu.ncsu.csc.itrust.action;

import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryLabelDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class GetFoodDiaryLabelAction {
	long mid;
	protected FoodDiaryLabelDAO foodDAO;
	protected EventLoggingAction loggingAction;
	
	/**
	 * The constructor
	 * @param factory DAOFactory (you know what it is.)
	 * @param midString The logged in user mid.
	 * @throws ITrustException
	 */
	public GetFoodDiaryLabelAction(DAOFactory factory, long mid) throws ITrustException {
		this.mid = mid;
		foodDAO = factory.getFoodDiaryLabelDAO();
		loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Gets the label for a date in a patient's food diary
	 * @param mid Patient's mid
	 * @param date Date of the diary entries
	 * @return The bean representing the label
	 * @throws DBException
	 */
	public FoodDiaryLabelSetBean getSetFoodDiaryLabel(long mid, Date date) throws DBException {
		FoodDiaryLabelSetBean b = foodDAO.getSetFoodDiaryLabel(mid, date);
		loggingAction.logEvent(TransactionType.PATIENT_GET_LABEL, mid, mid, "");
		return b;
	}
	
	/**
	 * Gets all labels a patient can choose from
	 * @param mid Patient's mid
	 * @return A list of beans representing all the labels they can choose from
	 * @throws DBException
	 */
	public List<FoodDiaryLabelBean> getAllFoodDiaryLabels(long mid) throws DBException {
		List<FoodDiaryLabelBean> list = foodDAO.getAllFoodDiaryLabels(mid);
		loggingAction.logEvent(TransactionType.PATIENT_GET_ALL_LABELS, mid, mid, "");
		return list;
	}
	/**
	 * Get food diary label (not set.)  by rowid.
	 * @param rowID
	 * @return
	 * @throws DBException
	 */
	public FoodDiaryLabelBean getFoodDiaryLabelByRowID(long rowID) throws DBException{
		return foodDAO.getFoodDiaryLabelByRowID(rowID);
	}
}
