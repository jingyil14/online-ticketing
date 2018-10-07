package onlineticketing.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.datatransfer.CustomerServiceBean;
import onlineticketing.onlineticketing.Params;

/**
 * Servlet implementation class ViewAccountServlet
 */
@WebServlet("/ViewAccountControllerServlet")
public class ViewAccountControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAccountControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		CustomerServiceBean customerServiceBean = 
				new CustomerServiceBean();
		String json = customerServiceBean.getAllCustomerJson();
		
		response.getWriter().write(json);
		response.flushBuffer();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("logout")) {
			logout(request, response);
		} else if (action.equals("authorisation")) {
			String target = Params.VIEW_ACCOUNT_URL;
			authorisation(target, request, response);
		}
	}

}
