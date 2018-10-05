package onlineticketing.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.domain.User;
import onlineticketing.onlineticketing.Params;
import onlineticketing.onlineticketing.Session;
import onlineticketing.service.AuthenticationService;
import onlineticketing.service.CustomerService;

/**
 * Servlet implementation class AuthenticationController
 */
@WebServlet("/AuthenticationController")
public class AuthenticationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Get the permission for the passed-in request through
	 * username and id.
	 * If it is a valid user, create a new session,
	 * and return the corresponding permission.
	 * Otherwise, return an error message.
	 * 
	 * @param request	HttpServletRequest
	 * @return			the permission of passed-in request
	 */
	public int authenticate(HttpServletRequest request) 
    				throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int result;
		
		AuthenticationService authenticationService = new AuthenticationService();
		int permission = authenticationService.getPermission(username, password);
		
		if (permission == Params.ADMIN_PERMISSION) {
			System.out.println("Admin");
			int userid = getUserId(username);
			System.out.println("UserId:"+userid);
			Session.getInstance().createSession(request.getSession(), 
					Params.ADMIN_PERMISSION, userid);
			result = Params.ADMIN_PERMISSION;
		} else if (permission == Params.CUSTOMER_PERMISSION) {
			System.out.println("Customer");
			int userid = getUserId(username);
			System.out.println("UserId:"+userid);
			Session.getInstance().createSession(request.getSession(), 
					Params.CUSTOMER_PERMISSION, userid);
			result = Params.CUSTOMER_PERMISSION;
		} else {
			System.out.println("Error");
			result = Params.INVALID_PERMISSION;
		}
		return result;
	}
	
	/**
	 * Get the corresponding user id of the passed-in user name
	 * 
	 * @param username	the passed-in user name
	 */
	private int getUserId(String username) {
		CustomerService customerService = new CustomerService();
		User user = customerService.getUserByUsername(username);
		int userid = Integer.parseInt(user.getId());
		return userid;
	}
	
	/**
	 * Clean the session of the request when user logging out.
	 * 
	 * @param request	HttpServletRequest
	 */
	public void logout(HttpServletRequest request) 
			throws IOException, ServletException {
		Session session = Session.getInstance();
		session.closeSession();
	}
}
