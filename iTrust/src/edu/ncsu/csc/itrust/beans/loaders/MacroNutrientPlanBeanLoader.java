package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;

public class MacroNutrientPlanBeanLoader implements BeanLoader<MacroNutrientPlanBean> {
	/**
	 * loadList simply takes a ResultSet from a query, and
	 * then it creates a list of MacroNutrientPlanBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the MacroNutrientPlanBean in the ResultSet.
	 */
	@Override
	public List<MacroNutrientPlanBean> loadList(ResultSet rs)
			throws SQLException {
		ArrayList<MacroNutrientPlanBean> result = new ArrayList<MacroNutrientPlanBean>();
		while(rs.next()){
			MacroNutrientPlanBean b = loadSingle(rs);
			result.add(b);
		}
		return result;
	}
	/**
	 * loadSingle does the heavy lifting for the loadList method.
	 * Preconditions: The ResultSet must already be on an actual entry in the set.
	 * @param rs The result set that the MacroNutrientPlanBean is being loaded from.
	 * @return The MacroNutrientPlanBean that represents the entry that is currently in the set.
	 */
	@Override
	public MacroNutrientPlanBean loadSingle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		if(rs==null) return null;
		long rowID = rs.getLong("rowID");
		long ownerID = rs.getLong("ownerID");
		double protein = rs.getDouble("protein");
		double fat = rs.getDouble("fat");
		double carbs = rs.getDouble("carbs");
		double totalCal = rs.getDouble("totalCalories");
		MacroNutrientPlanBean b = new MacroNutrientPlanBean( ownerID, protein, fat, carbs, totalCal);
		b.setRowID(rowID);
		return b;
	}
	/**
	 * loadParameters is used to insert the values of a bean into a pepared statement.
	 * This version of loadParameters assumes that the values in the prepared statement are in
	 * the same order as in the createTables.sql file for macronutrientplan.
	 * The MacroNutrientPlanBean's rowID should be -1, when is doing INSERT to SQL.
	 * Otherwise this method will return a statement for UPDATE if rowID is not -1.
	 * @param ps The prepared statement that the bean will be loaded into.
	 * @param bean The MacroNutrientPlanBean that will be loaded into the prepared statement.
	 * @return The PreparedStatement that was passed in.
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			MacroNutrientPlanBean bean) throws SQLException {
		// TODO Auto-generated method stub
		int i = 1;
		ps.setLong(i++, bean.getOwnerID());
		ps.setDouble(i++, bean.getProtein());
		ps.setDouble(i++, bean.getFat());
		ps.setDouble(i++, bean.getCarbs());
		ps.setDouble(i++, bean.getTotalCal());
		//If the row ID is greater than -1, then meaning the data loadParameters is for UPDATE operation.
		//So, added one row id at the end for WHERE rowID=?.
		//New bean for INSERT should always have -1 rowID.
		if(bean.getRowID() > -1){
			ps.setDouble(i++, bean.getRowID());
		}
		return ps;
	}

}
