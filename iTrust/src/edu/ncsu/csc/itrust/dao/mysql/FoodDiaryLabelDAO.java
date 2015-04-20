package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryLabelBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryLabelSetBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class FoodDiaryLabelDAO {
	private transient final DAOFactory factory;
	private transient final FoodDiaryLabelBeanLoader labelLoader;
	private transient final FoodDiaryLabelSetBeanLoader labelSetLoader;
	
	/**
	 * Constructor
	 * @param factory
	 */
	public FoodDiaryLabelDAO(final DAOFactory factory){
		this.factory = factory;
		this.labelLoader = new FoodDiaryLabelBeanLoader();
		this.labelSetLoader = new FoodDiaryLabelSetBeanLoader();
	}
	
	/**
	 * Inserts a new label a user can choose from into the database
	 * @param label The bean representing the label to insert
	 * @return the rowid of the inserted label
	 * @throws DBException
	 */
	public long insertFoodDiaryLabel(FoodDiaryLabelBean label) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO fooddiarylabels (mid, label, colorcode) VALUES (?,?,?)");
			labelLoader.loadParameters(ps, label);
			ps.executeUpdate();
			lastInsertID = DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return lastInsertID;
	}
	
	/**
	 * Sets a label for a day in the user's food diary
	 * @param label The bean representing the label and date to set
	 * @return The rowid of the set label
	 * @throws DBException
	 */
	public long setFoodDiaryLabel(FoodDiaryLabelSetBean label) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO fooddiarysetlabels (mid, diarydate, label,labelrowID) VALUES (?,?,?,?)");
			labelSetLoader.loadParameters(ps, label);
			ps.executeUpdate();
			lastInsertID = DBUtil.getLastInsert(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage()+"123***");
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return lastInsertID;
	}
	/**
	 * Remove labels from the food diary label table.
	 * @param bean FoodDiaryLabelBean to remove
	 * @return The deleted FoodDiaryLabelBean.
	 * @throws DBException
	 */
	public FoodDiaryLabelBean removeLabel(FoodDiaryLabelBean bean) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM fooddiarylabels WHERE rowid=?");
			ps.setLong(1, bean.getRowid());
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return bean;
	}
	
	/**
	 * Gets all the labels a user can choose from
	 * @param mid User's mid
	 * @return A list of all label beans representing labels a user can choose from
	 * @throws DBException
	 */
	public List<FoodDiaryLabelBean> getAllFoodDiaryLabels(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<FoodDiaryLabelBean> labelList;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM fooddiarylabels WHERE mid=? ORDER BY label ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			labelList = labelLoader.loadList(rs);
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return labelList;
	}
	
	/**
	 * Retrieves a set food diary label from the database.
	 * @param mid Patient's mid
	 * @param date Date of the food diary entries 
	 * @return The bean representing the set label for the given mid and date
	 * @throws DBException
	 */
	public FoodDiaryLabelSetBean getSetFoodDiaryLabel(long mid, Date date) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		FoodDiaryLabelSetBean label = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM fooddiarysetlabels WHERE mid=? AND diarydate=?");
			ps.setLong(1, mid);
			ps.setDate(2, date);
			ResultSet rs = ps.executeQuery();
			if(rs.isBeforeFirst())
				label = labelSetLoader.loadSingle(rs);
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return label;
	}
	
	/**
	 * Removes a set label from the database (occurs when user sets a label to "none")
	 * @param bean The bean representing the set label to remove
	 * @return The bean representing the label removed
	 * @throws DBException
	 */
	public FoodDiaryLabelSetBean removeFoodDiaryLabel(FoodDiaryLabelSetBean bean) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM fooddiarysetlabels WHERE rowid=?");
			ps.setLong(1, bean.getRowid());
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return bean;
	}
	/**
	 * FoodDiaryLabelBean get record by long rowID.
	 * @param rowID
	 * @return
	 * @throws DBException
	 */
	public FoodDiaryLabelBean getFoodDiaryLabelByRowID(long rowID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		FoodDiaryLabelBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM fooddiarylabels WHERE rowid=?");
			ps.setLong(1, rowID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bean = labelLoader.loadSingle(rs);
			}
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return bean;
	}
}
