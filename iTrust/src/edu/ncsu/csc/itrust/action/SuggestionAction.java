package edu.ncsu.csc.itrust.action;

import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.itrust.action.base.SuggestionBaseAction;
import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SuggestionDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SuggestionAction extends SuggestionBaseAction {
	
	private SuggestionDAO suggestionDAO;
	
	/**
	 * Constructor
	 * @param factory the DAOFactory
	 * @throws ITrustException
	 */
	
	public SuggestionAction(DAOFactory factory)	throws ITrustException {
		super(factory);
		suggestionDAO = new SuggestionDAO(factory);
	}
	

	/*
	 *  Need: getSuggestionListByPatientID
	 *  DONT FORGET TO LOG EVENTS:
	 *  - TRANSACTION TYPE 7500: HCP LEFT SUGGESTION
	 *  - 7501: PATIENT VIEWED SUGGESTION
	 *  
	 */
	
	public List<SuggestionBean> getSuggestionsByDate(Date date, long patientID) throws DBException{
		return suggestionDAO.getSuggestionsByDate(date, patientID);
	}
	
	public void addSuggestion(SuggestionBean suggestionBean) throws DBException{
		suggestionDAO.insertSuggestion(suggestionBean);
	}
	
	public void editSuggestion(SuggestionBean suggestionBean) throws DBException{
		suggestionDAO.editSuggestion(suggestionBean);
	}
}
