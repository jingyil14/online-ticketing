package onlineticketing.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import onlineticketing.onlineticketing.PermissionsCollection;
import onlineticketing.onlineticketing.Session;

/**
 * Servlet implementation class AuthorisationController
 */
@WebServlet("/AuthorisationController")
public class AuthorisationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorisationController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Check the authorisation of the passed-in request and 
     * estimate whether it can redirect to the target page.
     * 
     * @param target	the url of target page
     * @return			the result
     */
    public Boolean authorisation(String target) 
    				throws IOException, ServletException {
    	Boolean result = null;
    	
    	int permission = Session.getInstance().getPermission();
    	ArrayList<String> permissionCollection = 
    			PermissionsCollection.getPermissionCollection(permission);
    	
    	if (permissionCollection.contains(target)) {
    		result = true;
    	} else {
    		result = false;
    	}
    	
    	return result;
    }

}
