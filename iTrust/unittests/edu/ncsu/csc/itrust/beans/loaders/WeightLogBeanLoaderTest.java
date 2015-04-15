package edu.ncsu.csc.itrust.beans.loaders;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.WeightLogBean;
import junit.framework.TestCase;

public class WeightLogBeanLoaderTest extends TestCase {
	
	private IMocksControl ctrl;
	private ResultSet rs;
	private WeightLogBeanLoader load;
	private WeightLogBean bean;
	    
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new WeightLogBeanLoader();
		bean = new WeightLogBean(1, Date.valueOf("2015-04-14"), 1, 1, 1, 1, 1, 1, 1, 1);
	}
	
	@Test
	public void testLoadList() throws SQLException {
		List<WeightLogBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	}
	
	@Test
	public void testLoadSingle() throws SQLException {
		expect(rs.getLong("mid")).andReturn(1L).once();
		expect(rs.getDate("logdate")).andReturn(Date.valueOf("2015-04-14")).once();
		expect(rs.getDouble("weight")).andReturn(1.0).once();
		expect(rs.getDouble("chest")).andReturn(1.0).once();
		expect(rs.getDouble("waist")).andReturn(1.0).once();
		expect(rs.getDouble("upperarm")).andReturn(1.0).once();
		expect(rs.getDouble("forearm")).andReturn(1.0).once();
		expect(rs.getDouble("thigh")).andReturn(1.0).once();
		expect(rs.getDouble("calves")).andReturn(1.0).once();
		expect(rs.getDouble("neck")).andReturn(1.0).once();
		ctrl.replay();
		bean = load.loadSingle(rs);
		assertEquals(1L, bean.getMid());
		assertEquals(Date.valueOf("2015-04-14"), bean.getDate());
		assertEquals(1.0, bean.getWeight());
		assertEquals(1.0, bean.getChest());
		assertEquals(1.0, bean.getWaist());
		assertEquals(1.0, bean.getUpperarm());
		assertEquals(1.0, bean.getForearm());
		assertEquals(1.0, bean.getThigh());
		assertEquals(1.0, bean.getCalves());
		assertEquals(1.0, bean.getNeck());
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
