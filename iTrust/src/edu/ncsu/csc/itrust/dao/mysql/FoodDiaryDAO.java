/**
 * 
 */
package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * @author Chi-Han
 * This is a database access object for food diary.
 */
public class FoodDiaryDAO {
	private transient final DAOFactory factory;
	private transient final FoodDiaryBeanLoader loader;
	/**
	 * Constructor
	 * @param factory
	 */
	public FoodDiaryDAO(final DAOFactory factory){
		this.factory = factory;
		this.loader = new FoodDiaryBeanLoader();
	}
	/**
	 * The method to insert food diary record into SQL.
	 * @param foodDiary The FoodDiaryBean object which is going to be inserted. 
	 * @return The new rowID of the new inserted record. (May be useful for some operations.)
	 * @throws DBException
	 */
	public long insertFoodDiary(FoodDiaryBean foodDiary) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		long lastInsertID = -1;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO fooddiarytable (ownerID, diaryDate, mealType, foodName, servingsNum, caloriesPerServing, gramsOfFatPerServing, milligramsOfSodiumPerServing, gramsOfCarbsPerServing, gramsOfSugarPerServing, gramsOfFiberPerServing, gramsOfProteinPerServing)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps = loader.loadParameters(ps, foodDiary);
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
	public List<FoodDiaryBean> getFoodDiaryListByOwnerID(long ownerID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		List<FoodDiaryBean> fDList;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM fooddiarytable WHERE ownerID=? ORDER BY diaryDate DESC");
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
	 * To retrieve food diary record by searching specific diaryID.
	 * @param diaryID The rowID of the food diary record.
	 * @return FoodDiaryBean The object of food diary.
	 * @throws DBException
	 */
	public FoodDiaryBean getFoodDiaryByID(long diaryID) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		FoodDiaryBean bean = null;
		try{
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM fooddiarytable WHERE rowID = ?");
			ps.setLong(1, diaryID);
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
	 * The method to update food diary record in SQL table.
	 * @param foodDiary The existing foodDiary object with new data to update. (need the target's rowID, otherwise this method will return 0 immediately.)
	 * @return The number of row impacted. 
	 * @throws DBException
	 */
	public int updateFoodDiary(FoodDiaryBean foodDiary) throws DBException{
		if(foodDiary.getRowID() < 0){
			return 0;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		int row_update = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("UPDATE fooddiarytable SET ownerID=?, diaryDate=?, mealType=?, foodName=?, servingsNum=?, caloriesPerServing=?, gramsOfFatPerServing=?, milligramsOfSodiumPerServing=?, gramsOfCarbsPerServing=?, gramsOfSugarPerServing=?, gramsOfFiberPerServing=?, gramsOfProteinPerServing=? WHERE rowID =?");
			ps = loader.loadParameters(ps, foodDiary);
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
	public FoodDiaryBean removeFoodDiary(FoodDiaryBean foodDiary) throws DBException{
		Connection conn = null;
		PreparedStatement ps = null;
		FoodDiaryBean foodDiaryToRemove = foodDiary;
		int row_update = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM fooddiarytable WHERE rowID =?");
			ps.setLong(1, foodDiaryToRemove.getRowID());
			row_update = ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			throw new DBException(e);
		} finally{
			DBUtil.closeConnection(conn, ps);
		}
		if(row_update > 0)return foodDiaryToRemove;
		return null;
	}
	
	
}
