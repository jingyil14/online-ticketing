package onlineticketing.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.datatransfer.FilmDTO;
import onlineticketing.datatransfer.FilmServiceBean;
import onlineticketing.datatransfer.ScheduleDTO;
import onlineticketing.datatransfer.ScheduleServiceBean;
import onlineticketing.onlineticketing.Params;
import onlineticketing.service.FilmService;
import onlineticketing.service.ScheduleService;

/**
 * Servlet implementation class EditFilmControllerServlet
 */
@WebServlet("/EditFilmControllerServlet")
public class EditFilmControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	ScheduleService scheduleService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditFilmControllerServlet() {
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
		scheduleService = new ScheduleService();
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
		if (action.equals("basic")) {
			postBasicInfo(request, response);
		} else if (action.equals("addSchedule")) {
			addSchedule(request, response);
		} else if (action.equals("deleteSchedule")) {
			deleteSchedule(request, response);
		} else if (action.equals("editSchedule")) {
			editSchedule(request, response);
		} else if (action.equals("saveSchedule")) {
			saveSchedule(request, response);
		} else if (action.equals("logout")) {
			logout(request, response);
		} else if (action.equals("authorisation")) {
			String target = Params.EDIT_FILM_URL;
			authorisation(target, request, response);
		}
	}
	
	/**
	 * Get basic information of a film.
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
	 * Get schedule information of a film.
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
	
	/**
	 * Update basic information of a film.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void postBasicInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String jsonStr = getRequestContent(request);
		
		FilmServiceBean filmServiceBean = new FilmServiceBean();
		FilmDTO filmDTO = filmServiceBean.postFilmJson(jsonStr);
		
		FilmService filmService = new FilmService();
		filmService.editFilm(filmDTO.getFilmId(), 
				filmDTO.getTitle(), filmDTO.getDescription(),
				filmDTO.getDirector(), filmDTO.getMainCast(), 
				filmDTO.getGenre());
		
		response.getWriter().write("success");
		response.flushBuffer();
	}
	
	/**
	 * Add a new schedule to a film.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void addSchedule(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String jsonStr = getRequestContent(request);
		
		ScheduleServiceBean scheduleServiceBean = 
				new ScheduleServiceBean();
		ScheduleDTO scheduleDTO = 
				scheduleServiceBean.postSchedule(jsonStr);
		
		String scheduleId = scheduleService.createSchedule
				(scheduleDTO.getFilmId(), 
				scheduleDTO.getScreeningRoomId(), 
				scheduleDTO.getStartTime(),
				scheduleDTO.getEndTime(), 
				scheduleDTO.getPrice());
		
		response.getWriter().write(scheduleId);
		response.flushBuffer();
	}
	
	/**
	 * Delete a schedule from a film.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void deleteSchedule(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String scheduleId = request.getParameter("id");
		
		System.out.println(scheduleId);
		scheduleService.deleteSchedule(scheduleId);
		
		
		response.getWriter().write("success");
		response.flushBuffer();
	}
	
	/**
	 * Edit a schedule of a film.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void editSchedule(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String jsonStr = getRequestContent(request);
		
		ScheduleServiceBean scheduleServiceBean = 
				new ScheduleServiceBean();
		ScheduleDTO scheduleDTO = 
				scheduleServiceBean.postSchedule(jsonStr);
		
		scheduleService.updateSchedule(scheduleDTO.getScheduleId(), 
				scheduleDTO.getStartTime(), scheduleDTO.getEndTime(), 
				scheduleDTO.getPrice());
		
		response.getWriter().write("success");
		response.flushBuffer();
	}

	/**
	 * Save all changes of schedules in a film.
	 * 
	 * @param request	HttpServletRequest
	 * @param response	HttpServletResponse
	 */
	private void saveSchedule(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String result = null;
		boolean saveSuccess = scheduleService.saveChanges();
		
		if (saveSuccess) {
			result = "success";
		} else {
			result = "fail";
		}
		
		response.getWriter().write(result);
		response.flushBuffer();
	}
}
