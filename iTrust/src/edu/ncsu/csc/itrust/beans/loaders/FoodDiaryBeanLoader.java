package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;

public class FoodDiaryBeanLoader implements BeanLoader<FoodDiaryBean> {
	/**
	 * loadList simply takes a ResultSet from a query to the Billing table, and
	 * then it creates a list of FoodDiaryBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the FoodDiaryBean in the ResultSet.
	 */
	@Override
	public List<FoodDiaryBean> loadList(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<FoodDiaryBean> result = new ArrayList<FoodDiaryBean>();
		while(rs.next()){
			FoodDiaryBean b = loadSingle(rs);
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
	public FoodDiaryBean loadSingle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		if(rs == null) return null;
		long ownerID = rs.getLong("ownerID");
		Date d = rs.getDate("diaryDate");
		FoodDiaryBean.MealTypes mealType = FoodDiaryBean.MealTypes.valueOf(rs.getString("mealType").toUpperCase());
		String nameOfFood = rs.getString("foodName");
		double numberOfServings = rs.getDouble("servingsNum");
		double caloriesPerServing = rs.getDouble("caloriesPerServing");
		double gramsOfFat = rs.getDouble("gramsOfFatPerServing");
		double milligramsOfSodium = rs.getDouble("milligramsOfSodiumPerServing");
		double gramsOfCarbs = rs.getDouble("gramsOfCarbsPerServing");
		double gramsOfSugar = rs.getDouble("gramsOfSugarPerServing");
		double gramsOfFiber = rs.getDouble("gramsOfFiberPerServing");
		double gramsOfProtein = rs.getDouble("gramsOfProteinPerServing");
		FoodDiaryBean result = new FoodDiaryBean(ownerID, d, mealType, nameOfFood,
				numberOfServings, caloriesPerServing, gramsOfFat,
				milligramsOfSodium, gramsOfCarbs, gramsOfSugar,
				gramsOfFiber, gramsOfProtein);
		//Explicitly set rowID to prove that the object is retrieve from SQL.
		result.setRowID(rs.getLong("rowID"));

		return result;
	}

	/**
	 * loadParameters is used to insert the values of a bean into a pepared statement.
	 * This version of loadParameters assumes that the values in the prepared statement are in
	 * the same order as in the createTables.sql file for fooddiarytable.
	 * @param ps The prepared statement that the bean will be loaded into.
	 * @param bean The FoodDiaryBean that will be loaded into the prepared statement.
	 * @return The PreparedStatement that was passed in.
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			FoodDiaryBean bean) throws SQLException {
		// TODO Auto-generated method stub
		int i = 1;
		ps.setLong(i++, bean.getOwnerID());
		ps.setDate(i++, new java.sql.Date(bean.getDate().getTime()));
		ps.setString(i++, bean.getTypeOfMeal().toString());
		ps.setString(i++, bean.getNameOfFood());
		ps.setDouble(i++, bean.getNumberOfServings());
		ps.setDouble(i++, bean.getCaloriesPerServing());
		ps.setDouble(i++, bean.getGramsOfFat());
		ps.setDouble(i++, bean.getMilligramsOfSodium());
		ps.setDouble(i++, bean.getGramsOfCarbs());
		ps.setDouble(i++, bean.getGramsOfSugar());
		ps.setDouble(i++, bean.getGramsOfFiber());
		ps.setDouble(i++, bean.getGramsOfProtein());
		//If the row ID is greater than -1, then meaning the data loadParameters is for UPDATE operation.
		//So, added one row id at the end for WHERE rowID=?.
		//New bean for INSERT should always have -1 rowID.
		if(bean.getRowID() > -1){
			ps.setDouble(i++, bean.getRowID());
		}
		return ps;
		
	}

}
