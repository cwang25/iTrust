package edu.ncsu.csc.itrust.beans.loaders;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryDailySummaryBean;

public class FoodDiaryDailySummaryBeanLoaderTest {

	  private IMocksControl ctrl;
	  private ResultSet rs;
	  private FoodDiaryDailySummaryBeanLoader load;
	  private FoodDiaryDailySummaryBean bean;
	  private static final double EPSILON = 0.1;
	    
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new FoodDiaryDailySummaryBeanLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<FoodDiaryDailySummaryBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}

	@Test
	public void testLoadSingle() throws SQLException {
		long time = System.currentTimeMillis();
		Date d = new Date(time);
		expect(rs.getLong("ownerID")).andReturn(500L).once();
		expect(rs.getDate("diaryDate")).andReturn(d).once();
		expect(rs.getDouble("totalCals")).andReturn(120.0).once();
		expect(rs.getDouble("totalFat")).andReturn(10.0).once();
		expect(rs.getDouble("totalSodium")).andReturn(500.0).once();
		expect(rs.getDouble("totalCarbs")).andReturn(25.0).once();
		expect(rs.getDouble("totalSugar")).andReturn(12.0).once();
		expect(rs.getDouble("totalFiber")).andReturn(8.0).once();
		expect(rs.getDouble("totalProtein")).andReturn(4.0).once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals(120, bean.getCaloriesPerServing(), EPSILON);
		assertEquals(10, bean.getGramsOfFat(), EPSILON);
		try{
			assertEquals("Pizza", bean.getNameOfFood());
			fail();
		}catch (Exception e){
			
		}
	}

	@Test
	public void testLoadParameters() {
		try{
			load.loadParameters(null, null);
			fail();
		} catch(Exception e) {
			
		}
	}

}
