package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.HtmlEncoder;
import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MacroNutrientProfileDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class MacroNutrientProfileBaseAction {
	/**
	 * The database access object factory to associate this with a runtime context.
	 */
	private DAOFactory factory;
	protected MacroNutrientProfileDAO macroDAO;
	protected EventLoggingAction loggingAction;
	/**
	 * Flag to show if the user is Nutritionist.
	 */
	protected boolean isNutritionist = false;
	private String nutritionistSpecialty = "Nutritionist";

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
	public MacroNutrientProfileBaseAction(DAOFactory factory, String midString)throws ITrustException {
		this.factory = factory;
		this.mid = checkOwnerID(midString);
		macroDAO = factory.getMacroNutrientProfileDAO();
		loggingAction = new EventLoggingAction(factory);
		isNutritionist = checkIfNutritionist(midString);

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
	public long getLoggedInID(){
		return mid;
	}
	public boolean isNutritionist(){
		return this.isNutritionist;
	}
	protected DAOFactory getFactory(){
		return factory;
	}
	/**
	 * The method to check if the logged in user is Nutritionist.
	 * @param input Logged in mid.
	 * @return true of false if the user is Nutritionist or not.
	 * @throws ITrustException 
	 */
	private boolean checkIfNutritionist(String input) throws ITrustException {
		try {
			long mid = Long.valueOf(input);
			PersonnelBean t = factory.getPersonnelDAO().getPersonnel(mid);
			if(t != null){
				String nutritionist = nutritionistSpecialty.toUpperCase();
				String personnelSpecialty = t.getSpecialty().toUpperCase();
				return nutritionist.equals(personnelSpecialty);
			}else{
				return false;
			}
		} catch (NumberFormatException e) {
			throw new ITrustException("User ID is not a number: " + HtmlEncoder.encode(input));
		}
	}
	
}
