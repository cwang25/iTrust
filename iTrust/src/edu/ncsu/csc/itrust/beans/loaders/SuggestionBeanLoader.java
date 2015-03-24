<<<<<<< HEAD
package edu.ncsu.csc.itrust.beans.loaders;

public class SuggestionBeanLoader {

}
=======
package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.SuggestionBean;

/**
 * Suggestion Bean Loader class
 * @author Ben
 *
 */
public class SuggestionBeanLoader {

	/**
	 * loadList simply takes a ResultSet from a query to the suggestions table, and
	 * then it creates a list of SuggestionBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the FoodDiaryBean in the ResultSet.
	 */
	public List<SuggestionBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<SuggestionBean> result = new ArrayList<SuggestionBean>();
		while(rs.next()){
			SuggestionBean b = loadSingle(rs);
			result.add(b);
		}
		return result;
	}
	
	/**
	 * loadSingle does the heavy lifting for the loadList method.
	 * Preconditions: The ResultSet must already be on an actual entry in the set.
	 * @param rs The result set that the SuggestionBean is being loaded from.
	 * @return The SuggestionBean that represents the entry that is currently in the set.
	 */
	public SuggestionBean loadSingle(ResultSet rs) throws SQLException {
		if (rs == null) return null;
		
		Date suggDate = rs.getDate("suggDate");
		long patientID = rs.getLong("patientID");
		long hcpID = rs.getLong("hcpID");
		String sugg = rs.getString("sugg");
		SuggestionBean result = new SuggestionBean(suggDate, patientID, hcpID, sugg);
		
		//Explicitly set rowID to prove that the object is retrieve from SQL.
		result.setRowID(rs.getLong("rowID"));
		
		return result;
	}

	/**
	 * loadParameters is used to insert the values of a bean into a prepared statement.
	 * This version of loadParameters assumes that the values in the prepared statement are in
	 * the same order as in the createTables.sql file for suggestions table.
	 * @param ps The prepared statement that the bean will be loaded into.
	 * @param bean The SuggestionBean that will be loaded into the prepared statement.
	 * @return The PreparedStatement that was passed in.
	 */
	public PreparedStatement loadParameters(PreparedStatement ps,
			SuggestionBean bean) throws SQLException {
		int i = 1;
		ps.setDate(i++, new java.sql.Date(bean.getDate().getTime()));
		ps.setLong(i++, bean.getPatientID());
		ps.setLong(i++, bean.getHcpID());
		ps.setString(i++, bean.getSuggestion());
		
		return ps;
	}
}
>>>>>>> branch 'development' of https://github.ncsu.edu/engr-csc326-spring2015/project-team21.git
