package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.loaders.SuggestionBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class SuggestionDAO {
	private transient final DAOFactory factory;
	private transient final SuggestionBeanLoader loader;
	/**
	 * Constructor
	 * @param factory
	 */
	public SuggestionDAO(final DAOFactory factory){
		this.factory = factory;
		this.loader = new SuggestionBeanLoader();
	}
	
	/* Methods needed:
	 *	insertSuggestion()
	 *	getSuggestionByMID()
	 *  anything else?? 
	 */

}