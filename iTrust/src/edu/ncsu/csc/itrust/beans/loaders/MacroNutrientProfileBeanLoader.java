package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;

public class MacroNutrientProfileBeanLoader implements BeanLoader<MacroNutrientProfileBean>{
	/**
	 * loadList simply takes a ResultSet from a queryand
	 * then it creates a list of MacroNutrientProfileBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the MacroNutrientProfileBean in the ResultSet.
	 */
	@Override
	public List<MacroNutrientProfileBean> loadList(ResultSet rs)
			throws SQLException {
		ArrayList<MacroNutrientProfileBean> result = new ArrayList<MacroNutrientProfileBean>();
		while(rs.next()){
			MacroNutrientProfileBean b = loadSingle(rs);
			result.add(b);
		}
		return result;
	}
	/**
	 * loadSingle does the heavy lifting for the loadList method.
	 * Preconditions: The ResultSet must already be on an actual entry in the set.
	 * @param rs The result set that the MacroNutrientProfileBean is being loaded from.
	 * @return The MacroNutrientProfileBean that represents the entry that is currently in the set.
	 */
	@Override
	public MacroNutrientProfileBean loadSingle(ResultSet rs)
			throws SQLException {
		// TODO Auto-generated method stub
		if(rs==null)return null;
		long rowID = rs.getLong("rowID");
		MacroNutrientProfileBean.Gender gender = MacroNutrientProfileBean.Gender.valueOf(rs.getString("gender").toUpperCase());
		int age = rs.getInt("age");
		double weight = rs.getDouble("weight");
		double height = rs.getDouble("height");
		MacroNutrientProfileBean.Goal goal = MacroNutrientProfileBean.Goal.valueOf(rs.getString("goals").toUpperCase());
		MacroNutrientProfileBean.Activity activity = MacroNutrientProfileBean.Activity.valueOf(rs.getString("activity").toUpperCase());
		long macroplanID = rs.getLong("macroplanID");
		MacroNutrientProfileBean result = new MacroNutrientProfileBean(gender, age, weight, height, goal, activity, macroplanID);
		//Set rowID since it is retrieved from database.
		result.setRowID(rowID);
		return result;
	}
	/**
	 * loadParameters is used to insert the values of a bean into a pepared statement.
	 * This version of loadParameters assumes that the values in the prepared statement are in
	 * the same order as in the createTables.sql file for macronutirentprofile.
	 * @param ps The prepared statement that the bean will be loaded into.
	 * @param bean The FoodDiaryBean that will be loaded into the prepared statement.
	 * @return The PreparedStatement that was passed in.
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps,
			MacroNutrientProfileBean bean) throws SQLException {
		// TODO Auto-generated method stub
		int i = 1;
		ps.setString(i++, bean.getGender().toString());
		ps.setInt(i++, bean.getAge());
		ps.setDouble(i++, bean.getWeight());
		ps.setDouble(i++, bean.getHeight());
		ps.setString(i++, bean.getGoal().toString());
		ps.setString(i++, bean.getAct().toString());
		ps.setLong(i++, bean.getMacroPlanID());
		if(bean.getRowID() > -1){
			ps.setLong(i++, bean.getRowID());
		}
		return ps;
	}

}
