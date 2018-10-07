package onlineticketing.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

import onlineticketing.datasource.TicketMapper;

public class Order extends DomainObject{
	
	private float payment;
	private LocalDateTime createTime;
	private int status;
	private int customerId;
	private String ticketInformation;
	private ArrayList<Ticket> tickets;
	private boolean ticketLoaded = false;
	
	public Order() {
		
	}
	
	public Order(int customerId, ArrayList<Ticket> tickets) {
		this.customerId = customerId;
		this.tickets = tickets;
		this.ticketLoaded = false;
	}

	public Order(float payment, LocalDateTime createTime, int status, int customerId, 
			String ticketInformation) {
		super();
		this.payment = payment;
		this.createTime = createTime;
		this.status = status;
		this.customerId = customerId;
		this.ticketInformation = ticketInformation;
		this.ticketLoaded = false;
	}
	
	public Order(int orderid, float payment, LocalDateTime createTime, int status, 
			int customerId, String ticketInformation) {
		super();
		this.id = Integer.toString(orderid);
		this.payment = payment;
		this.createTime = createTime;
		this.status = status;
		this.customerId = customerId;
		this.ticketInformation = ticketInformation;
		this.ticketLoaded = false;
	}
	
	public Order(int orderid, float payment, LocalDateTime createTime, int status, 
			int customerId, String ticketInformation, ArrayList<Ticket> tickets) {
		super();
		this.id = Integer.toString(orderid);
		this.payment = payment;
		this.createTime = createTime;
		this.status = status;
		this.customerId = customerId;
		this.ticketInformation = ticketInformation;
		this.tickets = tickets;
		this.ticketLoaded = true;
	}
	
	public int getOrderId() {
		return Integer.parseInt(id);
	}

	public float getPayment() {
		return payment;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getTicketInformation() {
		return ticketInformation;
	}

	public ArrayList<Ticket> getTickets() {
		if(!ticketLoaded) {
			int orderId = Integer.parseInt(this.id);
			this.tickets = TicketMapper.findTicketsByOrderId(orderId);
			this.ticketLoaded = true;
		} else {
			System.out.println("tickets loaded");
			TicketMapper.checkLockExpire(tickets.get(0));
		}
		return tickets;
	}

}
