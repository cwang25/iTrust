/**
 * 
 */
package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.HtmlEncoder;
import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MacroNutrientPlanDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * A food diary base action class for handling basic actions ot food diary.
 * @author Chi-Han
 *
 */
public class MacroNutrientPlanBaseAction {

	/**
	 * The database access object factory to associate this with a runtime context.
	 */
	private DAOFactory factory;
	protected MacroNutrientPlanDAO macroDAO;
	protected EventLoggingAction loggingAction;
	
	/**
	 * Stores the MID of the user associated with this action.
	 */
	protected long mid;
	/**
	 * Constructor
	 * @param factory DAOfactory
	 * @param midString The logged in mid.
	 * @throws ITrustException
	 */
	public MacroNutrientPlanBaseAction(DAOFactory factory, String midString)throws ITrustException {
		this.factory = factory;
		this.mid = checkOwnerID(midString);
		macroDAO = factory.getMacroNutrientPlanDAO();
		loggingAction = new EventLoggingAction(factory);
	}
	
	/**
	 * Asserts whether the input is a valid, existing user's MID.
	 * 
	 * @param input
	 *            The presumed MID
	 * @return The existing user's ID as a long.
	 * @throws ITrustException
	 *             If the user does not exist or there is a DB Problem.
	 */
	private long checkOwnerID(String input) throws ITrustException {
		try {
			long mid = Long.valueOf(input);
			if (factory.getAuthDAO().checkUserExists(mid))
				return mid;
			else
				throw new ITrustException("User does not exist");
		} catch (NumberFormatException e) {
			throw new ITrustException("User ID is not a number: " + HtmlEncoder.encode(input));
		}
	}
	
	/**
	 * Get owner id.
	 * @return
	 */
	public long getOwnderID(){
		return mid;
	}
	
	protected DAOFactory getFactory(){
		return factory;
	}
	
}
