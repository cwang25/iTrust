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

public class FoodDiaryBeanLoaderTest {

	  private IMocksControl ctrl;
	  private ResultSet rs;
	  private FoodDiaryBeanLoader load;
	  private FoodDiaryBean bean;
	  private static final double EPSILON = 0.1;
	    
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new FoodDiaryBeanLoader();
		bean = new FoodDiaryBean();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<FoodDiaryBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}

	@Test
	public void testLoadSingle() throws SQLException {
		long time = System.currentTimeMillis();
		Date d = new Date(time);
		expect(rs.getLong("ownerID")).andReturn(500L).once();
		expect(rs.getDate("diaryDate")).andReturn(d).once();
		expect(rs.getString("foodName")).andReturn("Pizza").once();
		expect(rs.getString("mealType")).andReturn("LUNCH").once();
		expect(rs.getDouble("servingsNum")).andReturn(3.0).once();
		expect(rs.getDouble("caloriesPerServing")).andReturn(120.0).once();
		expect(rs.getDouble("gramsOfFatPerServing")).andReturn(10.0).once();
		expect(rs.getDouble("milligramsOfSodiumPerServing")).andReturn(500.0).once();
		expect(rs.getDouble("gramsOfCarbsPerServing")).andReturn(25.0).once();
		expect(rs.getDouble("gramsOfSugarPerServing")).andReturn(12.0).once();
		expect(rs.getDouble("gramsOfFiberPerServing")).andReturn(8.0).once();
		expect(rs.getDouble("gramsOfProteinPerServing")).andReturn(4.0).once();
		expect(rs.getLong("rowID")).andReturn(5L).once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals("Pizza", bean.getNameOfFood());
		assertEquals(3.0, bean.getNumberOfServings(), EPSILON);
		assertEquals(120, bean.getCaloriesPerServing(), EPSILON);
		assertEquals(10, bean.getGramsOfFat(), EPSILON);
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
