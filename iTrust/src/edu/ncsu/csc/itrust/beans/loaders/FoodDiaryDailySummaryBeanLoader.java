package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryDailySummaryBean;

public class FoodDiaryDailySummaryBeanLoader implements BeanLoader<FoodDiaryDailySummaryBean> {
	/**
	 * loadList simply takes a ResultSet from a query to the Billing table, and
	 * then it creates a list of FoodDiaryBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the FoodDiaryBean in the ResultSet.
	 */
	@Override
	public List<FoodDiaryDailySummaryBean> loadList(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<FoodDiaryDailySummaryBean> result = new ArrayList<FoodDiaryDailySummaryBean>();
		while(rs.next()){
			FoodDiaryDailySummaryBean b = loadSingle(rs);
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
	public FoodDiaryDailySummaryBean loadSingle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		if(rs == null) return null;
		long ownerID = rs.getLong("ownerID");
		Date d = rs.getDate("diaryDate");
		double caloriesPerServing = rs.getDouble("totalCals");
		double gramsOfFat = rs.getDouble("totalFat");
		double milligramsOfSodium = rs.getDouble("totalSodium");
		double gramsOfCarbs = rs.getDouble("totalCarbs");
		double gramsOfSugar = rs.getDouble("totalSugar");
		double gramsOfFiber = rs.getDouble("totalFiber");
		double gramsOfProtein = rs.getDouble("totalProtein");
		FoodDiaryDailySummaryBean result = new FoodDiaryDailySummaryBean(ownerID, d, caloriesPerServing, gramsOfFat,
				milligramsOfSodium, gramsOfCarbs, gramsOfSugar,
				gramsOfFiber, gramsOfProtein);

		return result;
	}

	/**
	 * This is not supported for FoodDiaryDailySummaryBean
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			FoodDiaryDailySummaryBean bean) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("loadParameters for FoodDiaryDateSummaryBeanLoader is not supported");
	}

}
