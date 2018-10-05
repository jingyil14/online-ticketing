package onlineticketing.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
		
//		FilmService filmService = new FilmService();
//		filmService.createFilm("Beauty and Beast", "description", "director", "mainCast", "genre", 2, 1);
//		Film film = filmService.findFilmByFilmId("97475905");
//		ScheduleService scheduleService = new ScheduleService();
//		LocalDateTime startTime = LocalDateTime.now();
//		ArrayList<Schedule> schedules = film.getSchedule();
//		Schedule schedule = schedules.get(0);
//		ArrayList<Ticket> tickets = schedule.getTickets();
//		System.out.println(tickets.get(0).getId());
//		System.out.println(tickets.size());
//		scheduleService.createSchedule(97475905 , 1, startTime, startTime, 11.2f);
//		scheduleService.deleteSchedule("97475905S1");
//		scheduleService.saveChanges();
		
		AuthenticationService authService = new AuthenticationService();
		boolean result = authService.registerAccount("user3", "password2", 123456);
		System.out.println(result);
	}
}
