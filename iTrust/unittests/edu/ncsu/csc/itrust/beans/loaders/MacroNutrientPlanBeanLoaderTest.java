package edu.ncsu.csc.itrust.beans.loaders;

import static org.easymock.classextension.EasyMock.createControl;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.expect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;

public class MacroNutrientPlanBeanLoaderTest {
	private IMocksControl ctrl;
	private ResultSet rs;
	private MacroNutrientPlanBeanLoader load;
	private MacroNutrientPlanBean bean;
	private static final double EPSILON = 0.1;

	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new MacroNutrientPlanBeanLoader();
		bean = new MacroNutrientPlanBean(0,0,0,0);
	}

	@Test
	public void testLoadList() throws SQLException {
		List<MacroNutrientPlanBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}

	@Test
	public void testLoadSingle() throws SQLException {
		expect(rs.getLong("ownerID")).andReturn(500L).once();
		expect(rs.getLong("rowID")).andReturn(12L).once();
		expect(rs.getDouble("protein")).andReturn(50.0).once();
		expect(rs.getDouble("fat")).andReturn(50.0).once();
		expect(rs.getDouble("carbs")).andReturn(50.0).once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals(bean.getOwnerID(), 500L);
		assertEquals(bean.getRowID(), 12L);
		assertTrue(bean.getProtein()== 50.0);
		
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
