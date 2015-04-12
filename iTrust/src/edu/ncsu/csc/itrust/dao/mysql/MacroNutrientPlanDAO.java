package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryDailySummaryBeanLoader;
import edu.ncsu.csc.itrust.beans.loaders.MacroNutrientPlanBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class MacroNutrientPlanDAO {
	private transient final DAOFactory factory;
	private transient final MacroNutrientPlanBeanLoader loader;

	public MacroNutrientPlanDAO(final DAOFactory factory) {
		this.factory = factory;
		this.loader = new MacroNutrientPlanBeanLoader();
	}
	
	public long insertMacroNutrientPlan(MacroNutrientPlanBean b) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO macronutrientplan (ownerID, protein, fat, carbs)"
					+ "VALUES (?,?,?,?)");
			ps = loader.loadParameters(ps, b);
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
	public List<MacroNutrientPlanBean> getMacroNutrientPlanByOwnerID(long ownerID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<MacroNutrientPlanBean> fDList;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM macronutrientplan WHERE ownerID=?");
			ps.setLong(1, ownerID);
			ResultSet rs = ps.executeQuery();
			fDList = loader.loadList(rs);
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		}finally{
			DBUtil.closeConnection(conn, ps);
		}
		return fDList;
	}
	/**
	 * To retrieve food diary record by searching specific MacroNutrientPlan rowID.
	 * @param diaryID The rowID of the MacroNutrientPlan
	 * @return FoodDiaryBean The object of food diary.
	 * @throws DBException
	 */
	public MacroNutrientPlanBean getMacroNutrientPlanByRowID(long rowID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		MacroNutrientPlanBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM macronutrientplan WHERE rowID = ?");
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
	 * The method to update macronutirentplan record in SQL table.
	 * @param foodDiary The existing macronutirentplan object with new data to update. (need the target's rowID, otherwise this method will return 0 immediately.)
	 * @return The number of row impacted. 
	 * @throws DBException
	 */
	public int updateMacroNutrientPlan(MacroNutrientPlanBean bean) throws DBException{
		if(bean.getRowID() < 0){
			return 0;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		int row_update = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE macronutrientplan SET ownerID=?, protein=?, fat=?, carbs=? WHERE rowID =?");
			ps = loader.loadParameters(ps, bean);
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
	 * The method to remove food diary record from SQL table by given rowID.
	 * @param foodDiary The existing foodDiary that will be removed from SQL.
	 * @return The FoodDiaryBean that is removed.
	 * @throws DBException 
	 */
	public MacroNutrientPlanBean removeMacroNutrientPlan(MacroNutrientPlanBean bean) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		MacroNutrientPlanBean beanToRemove = bean;
		int row_update = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM macronutrientplan WHERE rowID =?");
			ps.setLong(1, beanToRemove.getRowID());
			row_update = ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
		if(row_update > 0)return beanToRemove;
		return null;
	}
}
