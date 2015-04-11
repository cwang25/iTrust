package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.WeightLogBean;

public class WeightLogBeanLoader implements BeanLoader<WeightLogBean> {
	/**
	 * loadList simply takes a ResultSet from a query to the weightlog table, and
	 * then it creates a list of WeightLogBean from it.
	 * @param rs The ResultSet that is being converted into a list.
	 * @return The list that contains all the WeightLogBean in the ResultSet.
	 */
	@Override
	public List<WeightLogBean> loadList(ResultSet rs) throws SQLException {
		ArrayList<WeightLogBean> result = new ArrayList<WeightLogBean>();
		while(rs.next()){
			WeightLogBean b = loadSingle(rs);
			result.add(b);
		}
		return result;
	}

	/**
	 * loadSingle does the heavy lifting for the loadList method.
	 * Preconditions: The ResultSet must already be on an actual entry in the set.
	 * @param rs The result set that the WeightLogBean is being loaded from.
	 * @return The WeightLogBean that represents the entry that is currently in the set.
	 */
	@Override
	public WeightLogBean loadSingle(ResultSet rs) throws SQLException {
		if(rs == null) return null;
		long ownerID = rs.getLong("mid");
		Date d = rs.getDate("logdate");
		double weight = rs.getDouble("weight");
		double chest = rs.getDouble("chest");
		double waist = rs.getDouble("waist");
		double upperarm = rs.getDouble("upperarm");
		double forearm = rs.getDouble("forearm");
		double thigh = rs.getDouble("thigh");
		double calves = rs.getDouble("calves");
		double neck = rs.getDouble("neck");
		WeightLogBean result = new WeightLogBean(ownerID, d, weight, chest, waist, upperarm, forearm, thigh, calves, neck);

		return result;
	}

	/**
	 * loadParameters is used to insert the values of a bean into a pepared statement.
	 * This version of loadParameters assumes that the values in the prepared statement are in
	 * the same order as in the createTables.sql file for weightlog.
	 * @param ps The prepared statement that the bean will be loaded into.
	 * @param bean The WeightLogBean that will be loaded into the prepared statement.
	 * @return The PreparedStatement that was passed in.
	 */
	@Override
	public PreparedStatement loadParameters(PreparedStatement ps, WeightLogBean bean) throws SQLException {
		int i = 1;
		ps.setLong(i++, bean.getMid());
		ps.setDate(i++, bean.getDate());
		ps.setDouble(i++, bean.getWeight());
		ps.setDouble(i++, bean.getChest());
		ps.setDouble(i++, bean.getWaist());
		ps.setDouble(i++, bean.getUpperarm());
		ps.setDouble(i++, bean.getForearm());
		ps.setDouble(i++, bean.getThigh());
		ps.setDouble(i++, bean.getCalves());
		ps.setDouble(i++, bean.getNeck());

		return ps;
	}

}
