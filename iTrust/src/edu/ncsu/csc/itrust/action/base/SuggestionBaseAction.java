package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.action.EventLoggingAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SuggestionDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SuggestionBaseAction {

	private DAOFactory factory;
	protected SuggestionDAO suggestionDAO;
	protected EventLoggingAction loggingAction;
	protected long loggedInMID;
	
	public SuggestionBaseAction(DAOFactory factory, long mid) throws ITrustException {
		this.factory = factory;
		suggestionDAO = factory.getSuggestionDAO();
		loggingAction = new EventLoggingAction(factory);
		this.loggedInMID = mid;
	}
}
