package onlineticketing.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.onlineticketing.Params;
import onlineticketing.service.AuthenticationService;

/**
 * Servlet implementation class LogInControllerServlet
 */
@WebServlet("/LogInControllerServlet")
public class LogInControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LogInControllerServlet() {
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("login")) {
			login(request, response);
		} else if (action.equals("register")) {
			register(request, response);
		}
	}
	
	/**
	 * Call AuthenticationController to authenticate passed-in 
	 * request and return the permission of the request.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void login(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		AuthenticationController authenticationController = 
				new AuthenticationController();
		int permission = 
				authenticationController.authenticate(request);
		String result = null;
		
		if (permission == Params.ADMIN_PERMISSION) {
			result = "admin";
		} else if (permission == Params.CUSTOMER_PERMISSION) {
			result = "customer";
		} else if (permission == Params.INVALID_PERMISSION){
			result = "error";
		}
		
		response.getWriter().write(result);
		response.flushBuffer();
	}
	
	/**
	 * Call AuthenticationController to register passed-in request.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void register(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int phoneNumber = Integer.parseInt(request.getParameter("phoneNumber"));
		
		AuthenticationService authenticationService = new AuthenticationService();
		boolean result = 
				authenticationService.registerAccount(username, password, phoneNumber);
		
		response.getWriter().write(Boolean.toString(result));
		response.flushBuffer();
	}

}
