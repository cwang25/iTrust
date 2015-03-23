package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.action.base.SuggestionBaseAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SuggestionAction extends SuggestionBaseAction {

	/**
	 * Constructor
	 * @param factory the DAOFactory
	 * @throws ITrustException
	 */
	
	public SuggestionAction(DAOFactory factory)	throws ITrustException {
		super(factory);
	}
	

	/*
	 *  Need: getSuggestionListByPatientID
	 *  DONT FORGET TO LOG EVENTS:
	 *  - TRANSACTION TYPE 7500: HCP LEFT SUGGESTION
	 *  - 7501: PATIENT VIEWED SUGGESTION
	 *  
	 */
	

}
