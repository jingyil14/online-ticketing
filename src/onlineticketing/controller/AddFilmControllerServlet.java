package onlineticketing.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.datatransfer.FilmDTO;
import onlineticketing.datatransfer.FilmServiceBean;
import onlineticketing.onlineticketing.Params;
import onlineticketing.service.FilmService;

/**
 * Servlet implementation class AddFilmControllerServlet
 */
@WebServlet("/AddFilmControllerServlet")
public class AddFilmControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFilmControllerServlet() {
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
		String action = request.getParameter("action");
		if (action.equals("add")) {
			addFilm(request, response);
		} else if (action.equals("logout")) {
			logout(request, response);
		} else if (action.equals("authorisation")) {
			String target = Params.ADD_FILM_URL;
			authorisation(target, request, response);
		}
	}
	
	/**
	 * Add a film to the system according to the passed-in film info
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void addFilm(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String jsonStr = getRequestContent(request);
		
		FilmServiceBean filmServiceBean = new FilmServiceBean();
		FilmDTO filmDTO = filmServiceBean.postFilmJson(jsonStr);
		
		FilmService filmService = new FilmService();
		filmService.createFilm(filmDTO.getTitle(), 
				filmDTO.getDescription(), filmDTO.getDirector(), 
				filmDTO.getMainCast(), filmDTO.getGenre(), 
				filmDTO.getRunningHour(), filmDTO.getRunningMin());
		response.getWriter().write("success");
		response.flushBuffer();
	}

}
