package edu.ncsu.csc.itrust.server;

import java.io.IOException;
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

public class FoodDiarySuggestionNotificationUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loggedInMid = req.getParameter("loggedInMID");
		String suggestionDate = req.getParameter("suggestionDate");
		Date date = new Date(Long.parseLong(suggestionDate));
		try {
			SuggestionAction a = new SuggestionAction(DAOFactory.getProductionInstance(), Long.parseLong(loggedInMid));
			List<SuggestionBean> bList = a.getSuggestionsByDate(date, Long.parseLong(loggedInMid));
			for(SuggestionBean b : bList){
				b.setIsNew("False");
				a.editSuggestion(b);
			}
			
		} catch (ITrustException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
