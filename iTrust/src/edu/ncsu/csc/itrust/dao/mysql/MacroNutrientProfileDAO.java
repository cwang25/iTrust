package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.beans.loaders.MacroNutrientProfileBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class MacroNutrientProfileDAO {
	private transient final DAOFactory factory;
	private transient final MacroNutrientProfileBeanLoader loader;
	/**
	 * Constructor
	 * @param factory
	 */
	public MacroNutrientProfileDAO(final DAOFactory factory) {
		this.factory = factory;
		this.loader = new MacroNutrientProfileBeanLoader();
	}
	
	public long insertMacroNutrientProfile(MacroNutrientProfileBean bean) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO macronutrientprofile (gender, age, weight, height, goals, activity, macroplanID)"
					+ "VALUES (?,?,?,?,?,?,?)");
			ps = loader.loadParameters(ps, bean);
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
	 * The method to get a list of food diary for particular owner (patient).
	 * @param ownerID  The owner (patient) id.
	 * @return The list of the food diary.
	 * @throws DBException
	 */
	public List<MacroNutrientProfileBean> getProfileListByMacroNutrientPlanID(long macroPlanID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<MacroNutrientProfileBean> profileList;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM macronutrientprofile WHERE macroplanID=?");
			ps.setLong(1, macroPlanID);
			ResultSet rs = ps.executeQuery();
			profileList = loader.loadList(rs);
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
		return profileList;
	}
	
	/**
	 * To retrieve MacroNutrientProfileBean record by searching specific rowID.
	 * @param rowID The rowID of the food diary record.
	 * @return FoodDiaryBean The object of food diary.
	 * @throws DBException
	 */
	public MacroNutrientProfileBean getMacroNutrientProfileByID(long rowID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		MacroNutrientProfileBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM macronutrientprofile WHERE rowID = ?");
			ps.setLong(1, rowID);
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
	
	/**
	 * The method to MacroNutrientProfileBean record in SQL table.
	 * @param macroProfile The existing MacroNutrientProfile object with new data to update. (need the target's rowID, otherwise this method will return 0 immediately.)
	 * @return The number of row impacted. 
	 * @throws DBException
	 */
	public int updateMacroNutrientProfile(MacroNutrientProfileBean macroProfile) throws DBException{
		if(macroProfile.getRowID() < 0){
			return 0;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		int row_update = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE macronutrientprofile SET gender=?, age=?, weight=?, height=?, goals=?, activity=?, macroplanID=? WHERE rowID =?");
			ps = loader.loadParameters(ps, macroProfile);
			row_update = ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
		return row_update;
	}
	
	/**
	 * The method to remove macronutrient profile record from SQL table by given rowID.
	 * @param MacroNutrientProfile The existing MacroNutrientProfile that will be removed from SQL.
	 * @return The MacroNutrientProfileBean that is removed.
	 * @throws DBException 
	 */
	public MacroNutrientProfileBean removeMacroNutrientProfile(MacroNutrientProfileBean macroNutrientProfile) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		MacroNutrientProfileBean macroNutrientProfileToRemove = macroNutrientProfile;
		int row_update = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM macronutrientprofile WHERE rowID =?");
			ps.setLong(1, macroNutrientProfileToRemove.getRowID());
			row_update = ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
		if(row_update > 0)return macroNutrientProfileToRemove;
		return null;
	}
}
