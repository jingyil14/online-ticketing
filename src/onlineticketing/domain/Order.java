package onlineticketing.domain;

import java.util.Date;

public class Order extends DomainObject{
	
	private float payment;
	private Date createTime;
	private int status;
	private int customerId;
	private String ticketInformation;
	
	public Order() {
		
	}

	public Order(float payment, Date createTime, int status, int customerId, 
			String ticketInformation) {
		super();
		this.payment = payment;
		this.createTime = createTime;
		this.status = status;
		this.customerId = customerId;
		this.ticketInformation = ticketInformation;
	}
	
	public Order(int orderid, float payment, Date createTime, int status, 
			int customerId, String ticketInformation) {
		super();
		this.id = Integer.toString(orderid);
		this.payment = payment;
		this.createTime = createTime;
		this.status = status;
		this.customerId = customerId;
		this.ticketInformation = ticketInformation;
	}
	
	public int getOrderId() {
		return Integer.parseInt(id);
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTicketInformation() {
		return ticketInformation;
	}

	public void setTicketInformation(String ticketInformation) {
		this.ticketInformation = ticketInformation;
	}

}
