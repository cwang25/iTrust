package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.beans.loaders.SuggestionBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class SuggestionDAO {
	private transient final DAOFactory factory;
	private transient final SuggestionBeanLoader loader;
	/**
	 * Constructor
	 * @param factory
	 */
	public SuggestionDAO(final DAOFactory factory){
		this.factory = factory;
		this.loader = new SuggestionBeanLoader();
	}
	
	public long insertSuggestion(SuggestionBean suggestion) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO suggestions (suggDate, patientID, hcpID, sugg) "
					+ "VALUES (?,?,?,?)");
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
	
	public SuggestionBean getSuggestionByHCPID(long hcpID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		SuggestionBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM suggestions WHERE hcpID = ?");
			ps.setLong(1, hcpID);
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

	public SuggestionBean getSuggestionByPatientID(long hcpID) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		SuggestionBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM suggestions WHERE patientID = ?");
			ps.setLong(1, hcpID);
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
	
	/* Methods needed:
	 *	insertSuggestion()
	 *	getSuggestionByMID()
	 *  anything else?? 
	 */
}
