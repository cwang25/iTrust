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

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean.Activity;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean.Gender;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean.Goal;

public class MacroNutrientProfileBeanLoaderTest {
	private IMocksControl ctrl;
	private ResultSet rs;
	private MacroNutrientProfileBeanLoader load;
	private MacroNutrientProfileBean bean;
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new MacroNutrientProfileBeanLoader();
		bean = new MacroNutrientProfileBean(MacroNutrientProfileBean.Gender.MALE, 22, 80, 168, 
					MacroNutrientProfileBean.Goal.LOSE_WEIGHT, MacroNutrientProfileBean.Activity.SEDENTARY, 1L);
	}

	@Test
	public void testLoadList() throws SQLException {
		List<MacroNutrientProfileBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}

	@Test
	public void testLoadSingle() throws SQLException {
		//Mock rs return data.
		expect(rs.getLong("rowID")).andReturn(1L).once();
		expect(rs.getString("gender")).andReturn("male").once();
		expect(rs.getInt("age")).andReturn(22).once();
		expect(rs.getDouble("weight")).andReturn(80.0).once();
		expect(rs.getDouble("height")).andReturn(168.0).once();
		expect(rs.getString("goals")).andReturn("lose_weight").once();
		expect(rs.getString("activity")).andReturn("sedentary").once();
		expect(rs.getLong("macroplanID")).andReturn(2L).once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals(22 , bean.getAge());
		assertEquals("lose_weight", bean.getGoal().toString());
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
