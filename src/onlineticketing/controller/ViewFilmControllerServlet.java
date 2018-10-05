package onlineticketing.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import onlineticketing.datatransfer.FilmServiceBean;
import onlineticketing.datatransfer.ScheduleServiceBean;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;
import onlineticketing.onlineticketing.Params;
import onlineticketing.service.FilmService;
import onlineticketing.service.ScheduleService;

/**
 * Servlet implementation class ViewFilmControllerServlet
 */
@WebServlet("/ViewFilmControllerServlet")
public class ViewFilmControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewFilmControllerServlet() {
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
		String action = request.getParameter("action");
		if (action.equals("basic")) {
			getBasicInfo(request, response);
		} else if (action.equals("schedule")) {
			getScheduleInfo(request, response);
		}
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
			String target = Params.VIEW_FILM_URL;
			authorisation(target, request, response);
		}
	}
	
	/**
	 * Get basic information of a film with the id
	 * in the passed-in request.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void getBasicInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String id = request.getParameter("id");
		
		FilmServiceBean filmServiceBean = new FilmServiceBean();
		String filmJson = filmServiceBean.getFilmJson(id);
		
		response.getWriter().write(filmJson);
		response.flushBuffer();
	}
	
	/**
	 * Get schedule information of a film with the id
	 * in the passed-in request.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void getScheduleInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String id = request.getParameter("id");
		ScheduleServiceBean scheduleServiceBean = 
				new ScheduleServiceBean();
		String scheduleJson = 
				scheduleServiceBean.getScheduleJson(id);
		
		response.getWriter().write(scheduleJson);
		response.flushBuffer();
	}

}
