package onlineticketing.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.datatransfer.FilmServiceBean;
import onlineticketing.onlineticketing.Params;

/**
 * Servlet implementation class HomePageControllerServlet
 */
@WebServlet("/HomePageControllerServlet")
public class HomePageControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePageControllerServlet() {
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
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action.equals("authorisation")) {
			String target = Params.HOME_PAGE_URL;
			authorisation(target, request, response);
		} else if (action.equals("findFilm")) {
			findAllFilm(request, response);
		} else if (action.equals("logout")) {
			logout(request, response);
		}
	}

	/**
	 * Find all film information from the system 
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void findAllFilm(HttpServletRequest request,
			HttpServletResponse response) 
					throws ServletException, IOException{
		FilmServiceBean filmServiceBean = new FilmServiceBean();
		String json = filmServiceBean.getAllFilmsJson();
		
		response.getWriter().write(json);
		response.flushBuffer();
	}
	
}
