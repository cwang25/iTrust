package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.WeightLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WeightLogDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * Responsible for adding weight logs to the database. Communicates with DAO.
 *
 */
public class AddWeightLogAction {
	
	protected WeightLogDAO weightLogDAO;
	protected EventLoggingAction loggingAction;
	protected long mid;
	
	/**
	 * Constructor
	 * @param factory DAOfactory
	 * @param midString The logged in mid.
	 * @throws ITrustException
	 */
	public AddWeightLogAction(DAOFactory factory, long mid) throws ITrustException {
		this.mid = mid;
		weightLogDAO = factory.getWeightLogDAO();
		loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Adds a WeightLogBean to the database
	 * @param b Bean to add
	 * @return Row id of new entry
	 * @throws DBException
	 * @throws FormValidationException 
	 */
	public long addWeightLog(WeightLogBean b) throws DBException, FormValidationException {
		String errormsg = "";
		
		if(b.getWeight() <= 0)
			errormsg += "Weight must be greater than 0.<br/>";
		if(b.getCalves() <= 0)
			errormsg += "Calf measurement must be greather than 0.<br/>";
		if(b.getChest() <= 0)
			errormsg += "Chest measurement must be greather than 0.<br/>";
		if(b.getForearm() <= 0)
			errormsg += "Forearm measurement must be greather than 0.<br/>";
		if(b.getNeck() <= 0)
			errormsg += "Neck measurement must be greather than 0.<br/>";
		if(b.getThigh() <= 0)
			errormsg += "Thigh measurement must be greather than 0.<br/>";
		if(b.getUpperarm() <= 0)
			errormsg += "Upper arm measurement must be greather than 0.<br/>";
		if(b.getWaist() <= 0)
			errormsg += "Waist measurement must be greather than 0.";
		
		//data invalid, throw exception
		if(errormsg.length() > 0)
			throw new FormValidationException(errormsg);
		
		//data validated, add to database
		long newid = weightLogDAO.insertWeightLog(b);
		loggingAction.logEvent(TransactionType.PATIENT_ADD_WEIGHT_LOG, mid, mid, "Patient added weight log entry.");
		return newid;
	}
}
