package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SuggestionDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SuggestionBaseAction {

	private DAOFactory factory;
	protected SuggestionDAO suggestionDAO;
	protected EventLoggingAction loggingAction;
	
	public SuggestionBaseAction(DAOFactory factory)throws ITrustException {
		this.factory = factory;
		suggestionDAO = factory.getSuggestionDAO();
		loggingAction = new EventLoggingAction(factory);
	}
}
