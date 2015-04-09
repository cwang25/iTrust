package edu.ncsu.csc.itrust.action;

import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.itrust.action.base.SuggestionBaseAction;
import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SuggestionDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class SuggestionAction extends SuggestionBaseAction {
	
	/**
	 * Constructor
	 * @param factory the DAOFactory
	 * @throws ITrustException
	 */
	
	public SuggestionAction(DAOFactory factory, long loggedInMid)	throws ITrustException {
		super(factory, loggedInMid);
	}
	

	/*
	 *  Need: getSuggestionListByPatientID
	 *  DONT FORGET TO LOG EVENTS:
	 *  - TRANSACTION TYPE 7500: HCP LEFT SUGGESTION
	 *  - 7501: PATIENT VIEWED SUGGESTION
	 *  
	 */
	
	/**
	 * Get all suggestions given a date and a patient id
	 * 
	 * @param date The date
	 * @param patientID The mid of the patient
	 * @return a list of SuggestionBeans containing suggestions
	 * @throws DBException if something goes wrong in the database
	 */
	public List<SuggestionBean> getSuggestionsByDate(Date date, long patientID) throws DBException{
		return suggestionDAO.getSuggestionsByDate(date, patientID);
	}
	
	/**
	 * Add a suggestion to the database
	 * 
	 * @param suggestionBean The suggestion to be added
	 * @throws DBException if something goes wrong int he database
	 */
	public void addSuggestion(SuggestionBean suggestionBean) throws DBException{
		suggestionDAO.insertSuggestion(suggestionBean);
		//log the event
		loggingAction.logEvent(TransactionType.HCP_LEFT_SUGGESTION, loggedInMID, suggestionBean.getPatientID(), "");
	}
	
	/**
	 * Edit a specific suggestion in the database
	 * 
	 * @param suggestionBean The suggestion to be edited
	 * @throws DBException if something goes wrong in the database
	 */
	public void editSuggestion(SuggestionBean suggestionBean) throws DBException{
		suggestionDAO.editSuggestion(suggestionBean);
	}
	/**
	 * Get suggestion by rowid.
	 * @param rowid
	 * @return
	 * @throws DBException
	 */
	public SuggestionBean getSuggetionByRowID(long rowid) throws DBException{
		return suggestionDAO.getSuggetionByID(rowid);
	}
}
