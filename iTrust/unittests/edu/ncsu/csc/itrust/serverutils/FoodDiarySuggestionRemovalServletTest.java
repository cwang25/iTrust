package edu.ncsu.csc.itrust.serverutils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.server.FoodDiarySuggestionRemovalServlet;

public class FoodDiarySuggestionRemovalServletTest {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private PrintWriter out;
	
	private TestDataGenerator gen;
	private Delegator test;
	@Before
	public void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		test = new Delegator();
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws IOException, ServletException {
		Writer t = new StringWriter();
		PrintWriter temp = new PrintWriter(t);
		when(request.getParameter("loggedInMID")).thenReturn("500");
		when(request.getParameter("suggestionRowID")).thenReturn("1");
		when(response.getWriter()).thenReturn(temp);
		test.testDoPost(response, request);
		//System.out.println(t.toString());
		assertTrue(t.toString().contains("Success"));
		
	}
	
	private class Delegator extends FoodDiarySuggestionRemovalServlet{
		private static final long serialVersionUID = 1L;
        
		public Delegator(){
			super(true);
		}
		
		public void testDoPost(HttpServletResponse r, HttpServletRequest req) throws ServletException, IOException{
			super.doPost(req, r);
		}
	}
}
