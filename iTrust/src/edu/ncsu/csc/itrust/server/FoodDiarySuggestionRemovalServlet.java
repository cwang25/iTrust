package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ncsu.csc.itrust.action.SearchUsersAction;
import edu.ncsu.csc.itrust.action.SuggestionAction;
import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class FoodDiarySuggestionRemovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	boolean isTesting = false;
	public FoodDiarySuggestionRemovalServlet(){
		isTesting = false;
	}
	
	protected FoodDiarySuggestionRemovalServlet(boolean isTesting){
		this.isTesting = isTesting;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result;
		String loggedInMid = req.getParameter("loggedInMID");
		String suggestionRowID = req.getParameter("suggestionRowID");
		try {
			SuggestionAction a = new SuggestionAction(
					isTesting?TestDAOFactory.getTestInstance():DAOFactory.getProductionInstance(),
					Long.parseLong(loggedInMid));
			SuggestionBean b = a.getSuggetionByRowID(Long.parseLong(suggestionRowID));
			a.removeSuggestion(b);
			result = "Success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Error";
		}
		resp.setContentType("text/plain");
		PrintWriter respW = resp.getWriter();
		respW.write(result.toString());
	}

}
