package onlineticketing.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.datasource.ExclusiveWriteManager;
import onlineticketing.datatransfer.FilmServiceBean;
import onlineticketing.datatransfer.OrderDTO;
import onlineticketing.datatransfer.OrderServiceBean;
import onlineticketing.datatransfer.ScheduleServiceBean;
import onlineticketing.datatransfer.TicketServiceBean;
import onlineticketing.domain.Ticket;
import onlineticketing.onlineticketing.Params;
import onlineticketing.onlineticketing.Session;
import onlineticketing.service.CustomerService;

/**
 * Servlet implementation class BuyTicketControllerServlet
 */
@WebServlet("/BuyTicketControllerServlet")
public class BuyTicketControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyTicketControllerServlet() {
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
		if (action.equals("logout")) {
			logout(request, response);
		} else if (action.equals("authorisation")) {
			String target = Params.BUY_TICKET_URL;
			authorisation(target, request, response);
		} else if (action.equals("general")) {
			getGeneralInfo(request, response);
		} else if (action.equals("schedule")) {
			getScheduleInfo(request, response);
		} else if (action.equals("ticket")) {
			getTicketInfo(request, response);
		} else if (action.equals("pay")) {
			createOrder(request, response);
		}
	}
	
	/**
	 * Get the general information of a film from the system 
	 * according to the film id in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void getGeneralInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String filmId = request.getParameter("id");
		
		FilmServiceBean filmServiceBean = 
				new FilmServiceBean();
		String filmJson = 
				filmServiceBean.getFilmJson(filmId);
		
		response.getWriter().write(filmJson);
		response.flushBuffer();
	}
	
	/**
	 * Get the schedule information of a film from the system 
	 * according to the film id in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void getScheduleInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String filmId = request.getParameter("id");
		
		ScheduleServiceBean scheduleServiceBean = 
				new ScheduleServiceBean();
		String scheduleJson = scheduleServiceBean.
				getScheduleJson(filmId);
		
		response.getWriter().write(scheduleJson);
		response.flushBuffer();
	}
	
	/**
	 * Get the ticket information of a film from the system 
	 * according to the film id in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void getTicketInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String scheduleId = request.getParameter("id");
		
		TicketServiceBean ticketServiceBean = 
				new TicketServiceBean();
		String ticketJson = ticketServiceBean.
				getAllTicketsByScheduleId(scheduleId);
		
		response.getWriter().write(ticketJson);
		response.flushBuffer();
	}
	
	/**
	 * Create a new order into the system 
	 * according to the order info in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void createOrder(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String result = null;
		
		int userid = Session.getInstance().getUserid();
		
		String tickets = request.getParameter("tickets");
		String json = "{customer_id:"+userid+",ticket_ids:"+tickets+"}";
		System.out.println(userid);
		System.out.println(json);
		
		OrderServiceBean orderServiceBean = new OrderServiceBean();
		OrderDTO orderDTO = orderServiceBean.createOrder(json);
		
		CustomerService customerService = new CustomerService();
		int orderId = customerService.createOrder(orderDTO);
		result = Integer.toString(orderId);
		
		response.getWriter().write(result);
		response.flushBuffer();
	}

}
