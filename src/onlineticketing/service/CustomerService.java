package onlineticketing.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;

import onlineticketing.datasource.ExclusiveWriteManager;
import onlineticketing.datasource.FilmMapper;
import onlineticketing.datasource.IdentityMap;
import onlineticketing.datasource.OrderMapper;
import onlineticketing.datasource.ScheduleMapper;
import onlineticketing.datasource.TicketMapper;
import onlineticketing.datasource.UserMapper;
import onlineticketing.datatransfer.OrderDTO;
import onlineticketing.domain.Customer;
import onlineticketing.domain.Film;
import onlineticketing.domain.Order;
import onlineticketing.domain.Schedule;
import onlineticketing.domain.Ticket;
import onlineticketing.domain.User;
import onlineticketing.onlineticketing.Params;
import onlineticketing.onlineticketing.Session;

public class CustomerService {

	/**
	 * View the informaiton of all user in the database
	 * @return  the list of all the user in the database
	 */
	public ArrayList<User> viewAllUserInformation(){
		ArrayList<User> userList = UserMapper.findAllUsers();
		return userList;
	}
	
	public User getUserByUsername(String username) {
		return UserMapper.findUserByUsername(username);
	}
	
	public User getUserByUserId(int userid) {
		return UserMapper.findUserByUserId(userid);
	}
	
	
	/**
	 * View the information of all orders in the database
	 * @return  the list of all the orders in the database
	 */
	public ArrayList<Order> viewAllOrders(){
		ArrayList<Order> orderList = OrderMapper.findAllOrders();
		return orderList;
	}
	
	public Order viewOrder(String orderId) {
		Order order = OrderMapper.findOrderByOrderId(orderId);
		return order;
	}
	
	public int createOrder(OrderDTO orderDTO) {
		
		ExclusiveWriteManager lockingManager = ExclusiveWriteManager.getInstance();
		int userId = Session.getInstance().getUserid();
		boolean acquireLock = true;
		ArrayList<Ticket> tickets = orderDTO.getTickets();
		TicketMapper ticketMapper = new TicketMapper();
		
		for (Ticket ticket : tickets) {
			if (!lockingManager.acquireLock(ticket.getId(), userId))
				acquireLock = false;
		}
		if (!acquireLock) {
			for (Ticket ticket : tickets) {
				lockingManager.releaseLock(ticket.getId(), userId);
			}
			return -1;
		}
		
		LocalDateTime createTime = LocalDateTime.now(ZoneId.of("Australia/Sydney"));
		int status = Params.ORDER_CREATED;
		int orderId = getNextOrderId();
		String ticketInformation = getTicketInformation(orderDTO.getTickets());
		float payment = getPayment(orderDTO.getTickets());
		for (Ticket ticket : tickets) {
			ticket.setOrderId(orderId);
			ticket.setLocked(true);
			ticketMapper.update(ticket);
		}
		
		Order order = new Order(orderId, payment, createTime, status, 
				orderDTO.getCustomerId(), ticketInformation, tickets);
		
//		order.setPayment(payment);
//		order.setCreateTime(createTime);
//		order.setStatus(status);
//		order.setTicketInformation(ticketInformation);
		
		new OrderMapper().insert(order);
		
		User user = UserMapper.findUserByUserId(order.getCustomerId());
		assert !(user instanceof Customer) : "user is not a customer object";
		Customer customer = (Customer)user;
		customer.addOrder(order);
		
		return order.getOrderId();
		
	}
	
	public void payOrder(int orderId) {
		Order order = OrderMapper.findOrderByOrderId(Integer.toString(orderId));
		order.setStatus(Params.ORDER_PAID);
		new OrderMapper().update(order);
		
		TicketMapper ticketMapper = new TicketMapper();
		ArrayList<Ticket> ticketList = order.getTickets();
		ExclusiveWriteManager lockingManager = ExclusiveWriteManager.getInstance();
		int userId = Session.getInstance().getUserid();
		
		for (Ticket ticket : ticketList) {
			ticket.setSold(true);
			ticket.setLocked(false);
			ticketMapper.update(ticket);
			lockingManager.releaseLock(ticket.getId(), userId);
		}
	}
	
	public void cancelOrder(int orderId) {
		Order order = OrderMapper.findOrderByOrderId(Integer.toString(orderId));
		order.setStatus(Params.ORDER_CANCELED);
		new OrderMapper().update(order);
		
		TicketMapper ticketMapper = new TicketMapper();
		ArrayList<Ticket> ticketList = order.getTickets();
		ExclusiveWriteManager lockingManager = ExclusiveWriteManager.getInstance();
		int userId = Session.getInstance().getUserid();
		
		for (Ticket ticket : ticketList) {
			ticket.setLocked(false);
			ticket.setOrderId(-1);
			ticketMapper.update(ticket);
			lockingManager.releaseLock(ticket.getId(), userId);
		}
	}
	
	public float getPayment(ArrayList<Ticket> ticketList) {
//		ArrayList<Ticket> ticketList = order.getTickets();
		
		if(ticketList == null || ticketList.isEmpty())
			return 0;
		
		String scheduleId = ticketList.get(0).getScheduleId();
		Schedule schedule = ScheduleMapper.findScheduleByScheduleId(scheduleId);
		float ticketPrice = schedule.getPrice();
		return (ticketPrice * ticketList.size());
	}
	
	public String getTicketInformation(ArrayList<Ticket> ticketList) {
//		ArrayList<Ticket> ticketList = order.getTickets();
		
		String ticketInformation = "";
		
		if(ticketList != null && !ticketList.isEmpty()) {
			String scheduleId = ticketList.get(0).getScheduleId();
			Schedule schedule = ScheduleMapper.findScheduleByScheduleId(scheduleId);
			int filmId = schedule.getFilmId();
			Film film = FilmMapper.findFilmById(filmId);
			ticketInformation = ticketInformation + film.getTitle() + " " + 
					schedule.getStartTime() + " Screening Room " + schedule.getScreeningRoomId();

			for (Ticket ticket : ticketList) {
				ticketInformation = ticketInformation + " Seat " + ticket.getSeatNumber();
			}
		}
		return ticketInformation;
	}
	
	/**
	 * Generate a random int from 0 to 999999999 as the id for the new order,
	 * generate the new id until it does not already exist as an order id
	 * @return the next order id
	 */
	private int getNextOrderId() {
		OrderMapper.findAllOrders();
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		Random random = new Random();
		int orderId = random.nextInt(Params.MAX_ORDER_ID);
		
		while(orderMap.get(Integer.toString(orderId))!=null)
			orderId = random.nextInt(Params.MAX_ORDER_ID);
		
		return orderId;
	}
	
}
