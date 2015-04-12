package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.WeightLogBean;
import edu.ncsu.csc.itrust.beans.loaders.WeightLogBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Database Access Object for weightlog
 * @author Miles
 *
 */
public class WeightLogDAO {

	private transient final DAOFactory factory;
	private transient final WeightLogBeanLoader loader;
	
	/**
	 * constructor
	 * @param factory DAOFactory 
	 */
	public WeightLogDAO(final DAOFactory factory) {
		this.factory = factory;
		loader = new WeightLogBeanLoader();
	}
	
	/**
	 * Inserts a WeightLogBean into the database
	 * @param weightlog The bean to insert
	 * @return rowid of inserted record
	 * @throws DBException
	 */
	public long insertWeightLog(WeightLogBean weightlog) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO fooddiarytable (mid, logdate, weight, chest, waist, upperarm, forearm, thigh, calves, neck)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps = loader.loadParameters(ps, weightlog);
			ps.executeUpdate();
			lastInsertID = DBUtil.getLastInsert(conn);
		} catch (SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
		return lastInsertID;
	}
	
	/**
	 * Retrieves a list of every weight log a user has
	 * @param mid MID of the user
	 * @return List of WeightLogBeans
	 * @throws DBException
	 */
	public List<WeightLogBean> getWeightLogListByMID(long mid) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<WeightLogBean> beanList;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM weightlog WHERE mid=? ORDER BY logdate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			beanList = loader.loadList(rs);
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
		return beanList;
	}
}
