package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;

public class FoodDiaryLabelBeanLoader implements BeanLoader<FoodDiaryLabelBean> {

	/**
	 * loadList simply takes a ResultSet from a query to the fooddiarylabels table, and
	 * then it creates a list of FoodDiaryBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the FoodDiaryBean in the ResultSet.
	 */
	@Override
	public List<FoodDiaryLabelBean> loadList(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<FoodDiaryLabelBean> result = new ArrayList<FoodDiaryLabelBean>();
		while(rs.next()){
			FoodDiaryLabelBean b = loadSingle(rs);
			result.add(b);
		}
		return result;
	}
	/**
	 * loadSingle does the heavy lifting for the loadList method.
	 * Preconditions: The ResultSet must already be on an actual entry in the set.
	 * @param rs The result set that the FoodDiaryBean is being loaded from.
	 * @return The FoodDiaryBean that represents the entry that is currently in the set.
	 */
	@Override
	public FoodDiaryLabelBean loadSingle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		if(rs == null) return null;
		long rowid = rs.getLong("rowid");
		long mid = rs.getLong("mid");
		String label = rs.getString("label");
		String colorCode  = rs.getString("colorcode");
		return new FoodDiaryLabelBean(rowid, mid, label, colorCode);
	}

	/**
	 * loadParameters is used to insert the values of a bean into a pepared statement.
	 * This version of loadParameters assumes that the values in the prepared statement are in
	 * the same order as in the createTables.sql file for fooddiarytable.
	 * @param ps The prepared statement that the bean will be loaded into.
	 * @param bean The FoodDiaryLabelBean that will be loaded into the prepared statement.
	 * @return The PreparedStatement that was passed in.
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, FoodDiaryLabelBean bean) throws SQLException {
		int i = 1;
		ps.setLong(i++, bean.getMid());
		ps.setString(i++, bean.getLabel());
		ps.setString(i++, bean.getColorCode());
		if(bean.getRowid() > -1){
			ps.setLong(i++, bean.getRowid());
		}
		return ps;		
	}

}
