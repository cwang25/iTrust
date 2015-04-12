package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.WeightLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WeightLogDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Responsible for retrieving weight logs from the database. Communicates with DAO.
 *
 */
public class ViewWeightLogAction {
	
	protected WeightLogDAO weightLogDAO;
	protected EventLoggingAction loggingAction;
	protected long mid;
	protected boolean isPatient;
	
	/**
	 * constructor
	 * @param factory DAOFactory
	 * @param mid MID of user logged in
	 * @throws ITrustException
	 */
	public ViewWeightLogAction(DAOFactory factory, long mid) throws ITrustException {
		this.mid = mid;
		weightLogDAO = factory.getWeightLogDAO();
		loggingAction = new EventLoggingAction(factory);
		isPatient = factory.getPatientDAO().checkPatientExists(mid);
	}
	
	/**
	 * Retrieves list of weight logs given an MID
	 * @param ownerMID User's MID to retrieve logs for
	 * @return List of WeightLogBeans representing the user's weight logs
	 * @throws DBException
	 */
	public List<WeightLogBean> getWeightLogListByMID(long ownerMID) throws DBException {
		List<WeightLogBean> list = weightLogDAO.getWeightLogListByMID(ownerMID);
		
		if(isPatient)
			loggingAction.logEvent(TransactionType.PATIENT_VIEW_WEIGHT_LOG, mid, mid, "Viewed weight logs.");
		else
			loggingAction.logEvent(TransactionType.HCP_VIEW_WEIGHT_LOG, mid, mid, "Viewed weight logs.");
		
		return list;
	}
}
