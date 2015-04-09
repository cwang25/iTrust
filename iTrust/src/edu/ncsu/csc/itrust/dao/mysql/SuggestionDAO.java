package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.beans.loaders.SuggestionBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class SuggestionDAO {
	/** Factory object */
	private transient final DAOFactory factory;
	/** A Loader for loading parameters */
	private transient final SuggestionBeanLoader loader;
	
	/**
	 * Constructor
	 * @param factory
	 */
	public SuggestionDAO(final DAOFactory factory){
		this.factory = factory;
		this.loader = new SuggestionBeanLoader();
	}
	
	/**
	 * Insert a new suggestion in the database
	 * @param suggestion The bean containing the suggestion
	 * @return lastInsertID
	 * @throws DBException if something goes wrong in the database
	 */
	public long insertSuggestion(SuggestionBean suggestion) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO suggestions (suggDate, patientID, hcpID, sugg, isNew) "
					+ "VALUES (?,?,?,?, ?)");
			ps = loader.loadParameters(ps, suggestion);
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
	 * Get all suggestions for a specific date
	 * @param date The date
	 * @param patientID the mid of the patient
	 * @return list a list of SuggestionBeans
	 * @throws DBException if something goes wrong in the database
	 */
	public List<SuggestionBean> getSuggestionsByDate(Date date, long patientID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<SuggestionBean> list = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM suggestions WHERE patientId = ? AND suggDate = ?");
			ps.setLong(1, patientID);
			ps.setDate(2, date);
			ResultSet rs = ps.executeQuery();
			list = loader.loadList(rs);
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
		return list;
	}
	
	/**
	 * Edit a suggestion already in the database
	 * @param suggestion The SuggestionBean to be edited
	 * @throws DBException if something goes wrong in the database
	 */
	public void editSuggestion(SuggestionBean suggestion) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE suggestions SET suggDate=?, patientID=?, hcpID=?, sugg=?, isNew=? WHERE rowID=?");
			ps = loader.loadEditParameters(ps, suggestion);
			ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
	}
	/**
	 * Get suggestion by rowID
	 * @param rowid
	 * @return SuggestionBean
	 * @throws DBException
	 */
	public SuggestionBean getSuggetionByID(long rowid) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		SuggestionBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM suggestions WHERE rowID = ?");
			ps.setLong(1, rowid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bean = loader.loadSingle(rs);
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
		return bean;
		
	}
}
