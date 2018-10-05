package onlineticketing.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import onlineticketing.datatransfer.FilmServiceBean;
import onlineticketing.domain.Film;
import onlineticketing.onlineticketing.Params;
import onlineticketing.service.FilmService;
import onlineticketing.service.ScheduleService;

/**
 * Servlet implementation class AdminHomeControllerServlet
 */
@WebServlet("/AdminHomeControllerServlet")
public class AdminHomeControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeControllerServlet() {
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
		FilmServiceBean filmServiceBean = new FilmServiceBean();
		String json = filmServiceBean.getAllFilmsJson();
		
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
		if (action.equals("delete")) {
			deleteFilm(request, response);
		} else if (action.equals("logout")) {
			logout(request, response);
		} else if (action.equals("authorisation")) {
			String target = Params.ADMIN_HOME_PAGE_URL;
			authorisation(target, request, response);
		}
	}
	
	/**
	 * Delete a film to the system according to the passed-in film id
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void deleteFilm(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String filmId = request.getParameter("id");
		FilmService filmService = new FilmService();
		filmService.deleteFilm(filmId);
		response.sendRedirect("AdminHomePage.html");
	}

}
