package edu.ncsu.csc.itrust.dao.fooddiary;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class FoodDiaryDAOTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private EvilDAOFactory evil;
	private FoodDiaryDAO evil2;
	private FoodDiaryDAO foodDAO = factory.getFoodDiaryDAO();
	private FoodDiaryBean b1;
	private FoodDiaryBean b2;
	private FoodDiaryBean bad_bean;
	private static final long PATIENT_MID = 500L;
	private static final long PETIENT_MID_2 = 503L;
	private static final long NUTRITIONIST_TEST = 9900000012L;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		evil = new EvilDAOFactory(0);
		evil2 = evil.getFoodDiaryDAO();
		DBBuilder tables = new DBBuilder();
		tables.dropTables();
		tables.createTables();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		Calendar c1 = Calendar.getInstance();
		c1.set(2015, 2, 15);
		b1 = new FoodDiaryBean(500, c1.getTime(),
				FoodDiaryBean.MealTypes.SNACK, "Oreos", 53, 140, 7,
				90, 21, 13, 1, 0);
		b2 = new FoodDiaryBean(500, new Date(),
				FoodDiaryBean.MealTypes.DINNER, "Sushi", 60, 140, 8,
				100, 20, 0, 0, 20);
		bad_bean = new FoodDiaryBean(500, new Date(),
				FoodDiaryBean.MealTypes.DINNER, "Junk", 60, 140, 8,
				100, 20, 0, 0, -1);
	}

	@Test
	public void testInsertFoodDiary() throws DBException {
		foodDAO.insertFoodDiary(b1);
		List<FoodDiaryBean> foodlist = foodDAO.getFoodDiaryListByOwnerID(PATIENT_MID);
		assertEquals(1, foodlist.size());
		foodDAO.insertFoodDiary(b2);
		List<FoodDiaryBean> foodlist2 = foodDAO.getFoodDiaryListByOwnerID(PATIENT_MID);
		assertEquals(2, foodlist2.size());
		try{
			foodDAO.insertFoodDiary(bad_bean);
		}catch(DBException e){
			//The origin of the message comes from FoodDiaryBean Loader.
			assertEquals(e.getSQLException().getMessage(), "Grams of protein has to be postitive.");
		}
	}

	@Test
	public void testGetFoodDiaryListByOwnerID() throws DBException {
		foodDAO.insertFoodDiary(b1);
		foodDAO.insertFoodDiary(b2);
		List<FoodDiaryBean> foodlist = foodDAO.getFoodDiaryListByOwnerID(PATIENT_MID);
		assertEquals(2, foodlist.size());
		List<FoodDiaryBean> emptyFood = foodDAO.getFoodDiaryListByOwnerID(PETIENT_MID_2);
		assertEquals(0, emptyFood.size());		
	}

	@Test
	public void testGetFoodDiaryByID() throws DBException {
		long id = foodDAO.insertFoodDiary(b1);
		FoodDiaryBean b = foodDAO.getFoodDiaryByID(id);
		//Check if the objects have the same data.
		assertEquals(b1.getNameOfFood(), b.getNameOfFood());
		Calendar c1 = Calendar.getInstance();
		c1.setTime(b1.getDate());
		Calendar c2 = Calendar.getInstance();
		c2.setTime(b.getDate());
		assertEquals(c1.get(Calendar.DATE), c2.get(Calendar.DATE));
		assertTrue(b1.getGramsOfCarbs() == b.getGramsOfCarbs());
		assertTrue(b1.getGramsOfProtein() == b.getGramsOfProtein());
	}
	@Test
	public void testUpdateFoodDiary() throws DBException{
		long id = foodDAO.insertFoodDiary(b1);
		FoodDiaryBean b = foodDAO.getFoodDiaryByID(id);
		b.setGramsOfCarbs(b.getGramsOfCarbs()+10);
		b.setGramsOfProtein(b.getGramsOfProtein()+10);
		b.setNumberOfServings(b.getNumberOfServings()+10);
		foodDAO.updateFoodDiary(b);
		FoodDiaryBean newBean = foodDAO.getFoodDiaryByID(id);
		assertTrue(b1.getGramsOfCarbs()+10 - newBean.getGramsOfCarbs() == 0);
		assertTrue(b1.getGramsOfProtein()+10 - newBean.getGramsOfProtein() == 0);
		assertTrue(b1.getNumberOfServings()+10 - newBean.getNumberOfServings() == 0);
	}
	
	@Test
	public void testRemoveFoodDiary() throws DBException{
		for(int i = 0 ; i < 5 ; i++){
			foodDAO.insertFoodDiary(b1);
		}
		List <FoodDiaryBean>list = foodDAO.getFoodDiaryListByOwnerID(500);
		for(FoodDiaryBean b : list){
			foodDAO.removeFoodDiary(b);
		}
		List <FoodDiaryBean> newList = foodDAO.getFoodDiaryListByOwnerID(500);
		assertTrue(newList.size() == 0);
	}
}
