package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SuggestionDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SuggestionBaseAction {

	private DAOFactory factory;
	protected FoodDiaryDAO foodDAO;
	protected EventLoggingAction loggingAction;
	
	public SuggestionBaseAction(DAOFactory factory, String midString)throws ITrustException {
		this.factory = factory;
		//this.mid = checkOwnerID(midString);
		foodDAO = factory.getFoodDiaryDAO();
		loggingAction = new EventLoggingAction(factory);
	}
}
