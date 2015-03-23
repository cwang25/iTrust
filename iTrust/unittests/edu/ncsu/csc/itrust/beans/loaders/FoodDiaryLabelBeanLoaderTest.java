package edu.ncsu.csc.itrust.beans.loaders;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import junit.framework.TestCase;

public class FoodDiaryLabelBeanLoaderTest extends TestCase {
	private IMocksControl ctrl;
	private ResultSet rs;
	private FoodDiaryLabelBeanLoader load;
	private FoodDiaryLabelBean bean;
	    
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new FoodDiaryLabelBeanLoader();
		bean = new FoodDiaryLabelBean(730, "Test");
	}
	
	@Test
	public void testLoadList() throws SQLException {
		List<FoodDiaryLabelBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() throws SQLException {
		expect(rs.getLong("rowid")).andReturn(-1L).once();
		expect(rs.getLong("mid")).andReturn(730L).once();
		expect(rs.getString("label")).andReturn("Test").once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals("Test", bean.getLabel());
		assertEquals(-1L, bean.getRowid());
		assertEquals(730L, bean.getMid());
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
