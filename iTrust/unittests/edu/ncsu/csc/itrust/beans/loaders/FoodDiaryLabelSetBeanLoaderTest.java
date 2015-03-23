package edu.ncsu.csc.itrust.beans.loaders;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;
import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;

public class FoodDiaryLabelSetBeanLoaderTest {
	private IMocksControl ctrl;
	private ResultSet rs;
	private FoodDiaryLabelSetBeanLoader load;
	private FoodDiaryLabelSetBean bean;
	    
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new FoodDiaryLabelSetBeanLoader();
		bean = new FoodDiaryLabelSetBean(730, Date.valueOf("2012-09-02"), "Test");
	}
	
	@Test
	public void testLoadList() throws SQLException {
		List<FoodDiaryLabelSetBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	/*@Test
	public void testLoadSingle() throws SQLException {
		expect(rs.getLong("rowid")).andReturn(-1L).once();
		expect(rs.getLong("mid")).andReturn(730L).once();
		expect(rs.getDate("diarydate")).andReturn(Date.valueOf("2012-09-02")).once();
		expect(rs.getString("label")).andReturn("Test").once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals("Test", bean.getLabel());
		assertEquals(-1L, bean.getRowid());
		assertEquals(Date.valueOf("2012-09-02"), bean.getDate());
		assertEquals(730L, bean.getMid());
	}*/

	@Test
	public void testLoadParameters() {
		try{
			load.loadParameters(null, null);
			fail();
		} catch(Exception e) {
		}
	}
}
