package onlineticketing.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import onlineticketing.datasource.FilmMapper;
import onlineticketing.datasource.TicketMapper;
import onlineticketing.domain.Customer;
import onlineticketing.domain.Film;
import onlineticketing.domain.Order;
import onlineticketing.domain.Schedule;
import onlineticketing.domain.Ticket;
import onlineticketing.domain.User;

public class test {
	public static void main(String[] args) {
//		CustomerService customerService = new CustomerService();
	
//		User user = customerService.getUserByUsername("user1");
////		System.out.println(user.getClass());
//		System.out.println(user.getId());
//		Customer customer = (Customer)user;
//		System.out.println(customer.getPhoneNumber());
//		ArrayList<Order> orders = customer.getOrders();
//		for (Order order:orders) {
//			System.out.println(order.getId());
//			System.out.println(order.getTicketInformation());
//		}
		
		FilmService filmService = new FilmService();
//		filmService.createFilm("Beauty and Beast", "description", "director", "mainCast", "genre", 2, 1);
		Film film = filmService.findFilmByFilmId("865678731");
		film.setDescription("ddddddddd");
		new FilmMapper().update(film);
		System.out.println(film.getDescription());
		Film film2 = filmService.findFilmByFilmId("865678731");
		System.out.println(film2.getDescription());
//		ScheduleService scheduleService = new ScheduleService();
//		LocalDateTime startTime = LocalDateTime.now();
//		ArrayList<Schedule> schedules = film.getSchedule();
//		Schedule schedule = schedules.get(0);
//		ArrayList<Ticket> tickets = schedule.getTickets();
//		System.out.println("seat id:" +tickets.get(0).getSeatId());
//		System.out.println("seat number:" +tickets.get(0).getSeatNumber());
//		System.out.println(tickets.get(0).getId());
//		System.out.println(tickets.size());
//		scheduleService.createSchedule(865678731 , 1, startTime, startTime, 11.2f);
//		System.out.println(scheduleId);
//		scheduleService.deleteSchedule("97475905S1");
//		scheduleService.saveChanges();
//		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
//		ticketList.add(tickets.get(4));
//		ticketList.add(tickets.get(5));
//		Order order = new Order(1, ticketList);
//		customerService.createOrder(order);
		
//		AuthenticationService authService = new AuthenticationService();
//		boolean result = authService.registerAccount("user3", "password2", 123456);
//		System.out.println(result);
		
//		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
//		Ticket ticket = TicketMapper.findTicketByTicketId(ticketId);
		
		
	}
}
