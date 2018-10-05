package onlineticketing.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import onlineticketing.datatransfer.OrderServiceBean;
import onlineticketing.onlineticketing.Params;
import onlineticketing.service.CustomerService;

/**
 * Servlet implementation class PaymentControllerServlet
 */
@WebServlet("/PaymentControllerServlet")
public class PaymentControllerServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentControllerServlet() {
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
			String target = Params.PAYMENT_URL;
			authorisation(target, request, response);
		} else if (action.equals("getInfo")) {
			getInfo(request, response);
		} else if (action.equals("pay")) {
			pay(request, response);
		} else if (action.equals("cancel")) {
			cancel(request, response);
		}
	}
	
	/**
	 * Get the general information of a order from the system 
	 * according to the order id in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void getInfo(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		OrderServiceBean orderServiceBean = new OrderServiceBean();
		String json = orderServiceBean.getOrderByOrderId(id);
		
		response.getWriter().write(json);
		response.flushBuffer();
	}
	
	/**
	 * Pay a order according to the order id in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void pay(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		CustomerService customerService = new CustomerService();
		customerService.payOrder(id);
		
		response.getWriter().write("success");
		response.flushBuffer();
	}
	
	/**
	 * Cancel a order according to the order id in the passed-in request
	 * 
	 * @param request	the HttpServletRequest
	 * @param response	the HttpServletResponse
	 */
	private void cancel(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		CustomerService customerService = new CustomerService();
		customerService.cancelOrder(id);
		
		response.getWriter().write("success");
		response.flushBuffer();
	}

}
