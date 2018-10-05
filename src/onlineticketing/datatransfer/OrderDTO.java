package onlineticketing.datatransfer;

import java.time.LocalDateTime;
import java.util.ArrayList;

import net.sf.json.JSONObject;
import onlineticketing.domain.Ticket;

public class OrderDTO {
	
	private String orderId;
	private float payment;
	private LocalDateTime createTime;
	private int status;
	private int customerId;
	private String ticketInformation;
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	
	public OrderDTO(String orderId, float payment, LocalDateTime createTime, int status, int customerId, 
			String ticketInformation) {
		super();
		this.orderId = orderId;
		this.payment = payment;
		this.createTime = createTime;
		this.status = status;
		this.customerId = customerId;
		this.ticketInformation = ticketInformation;
	}
	
	public OrderDTO(int customerId, ArrayList<Ticket> tickets) {
		super();
		this.customerId = customerId;
		this.tickets = tickets;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("order_id", orderId);
		jsonObject.put("payment", payment);
		jsonObject.put("create_time", createTime);
		jsonObject.put("status", status);
		jsonObject.put("customer_id", customerId);
		jsonObject.put("ticket_information", ticketInformation);
		
		return jsonObject;
	}

	public int getCustomerId() {
		return customerId;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}
}
