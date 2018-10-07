package onlineticketing.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		doPost(request, response);
	}
    
    /**
     * Get the json string of the passed-in request.
     * @param request	HttpServletRequest
     * @return			the json string in request
     */
    protected String getRequestContent(HttpServletRequest request) 
    		throws ServletException, IOException {
		String json = request.getParameter("data");
		
		System.out.println("Request content is:" + json);
		
		return json;
    }
    
    /**
     * Call AuthenticationController to handle logout request.
     * @param request	HttpServletRequest
     */
    protected void logout(HttpServletRequest request,
    		HttpServletResponse response) 
    		throws ServletException, IOException {
    	AuthenticationController authenticationController = 
    			new AuthenticationController();
    	authenticationController.logout(request);
    	response.sendRedirect("index.html");
    }
    
    /**
     * Call AuthorisationController to check the authorisation
     * of the passed-in request.
     * 
     * @param request	HttpServletRequest
     * @param response	HttpServletResponse
     */
    protected void authorisation(String target, HttpServletRequest request,
    		HttpServletResponse response) 
    				throws ServletException, IOException {
    	Boolean result = null;
    	
    	AuthorisationController authorisationController = 
    			new AuthorisationController();
    	result = authorisationController.authorisation(target);
    	
    	System.out.println("Authorisation:"+result.toString());
    	response.getWriter().write(result.toString());
		response.flushBuffer();
    }

}
