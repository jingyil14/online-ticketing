package onlineticketing.domain;

import onlineticketing.datasource.SeatMapper;
import onlineticketing.onlineticketing.Params;

public class Ticket extends DomainObject {
	
	private boolean isSold;
	private int seatId;
	private int screeningRoomId;
	private String seatNumber;
	private int orderId;
	private String scheduleId;
	private boolean isLocked;
	
	public Ticket() {
		
	}
	
	public Ticket(String ticketId, int seatId, String scheduleId) {
		super();
		this.id = ticketId;
		this.isSold = false;
		this.seatId = seatId;
		this.scheduleId = scheduleId;
		this.isLocked = false;
		
		Seat seat = SeatMapper.findSeatById(seatId);
		this.seatNumber = seat.getSeatNumber();
	}
	
	public Ticket(String ticketId, boolean isSold, int seatId, int orderId, String scheduleId, boolean isLocked) {
		super();
		this.id = ticketId;
		this.isSold = isSold;
		this.seatId = seatId;
		this.orderId = orderId;
		this.scheduleId = scheduleId;
		this.isLocked = isLocked;
		
		Seat seat = SeatMapper.findSeatById(seatId);
		this.seatNumber = seat.getSeatNumber();
	}
	
	public int isSold() {
		return (isSold ? 1 : 0 );
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	public int getSeatId() {
		return seatId;
	}

	public int getScreeningRoomId() {
		return screeningRoomId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public int getStatus() {
		int status = Params.TICKET_AVAILABLE;
		if(this.isSold)
			status = Params.TICKET_SOLD;
		else if (this.isLocked)
			status = Params.TICKET_LOCKED;
		
		return status;
	}
}
