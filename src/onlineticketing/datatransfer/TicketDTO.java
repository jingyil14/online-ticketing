package onlineticketing.datatransfer;

import net.sf.json.JSONObject;

public class TicketDTO {

	private int status;
//	private int seatId;
	private String ticketId;
	private String seatNumber;
	
	public TicketDTO(int status, String ticketId, String seatNumber) {
		super();
		this.status = status;
		this.ticketId = ticketId;
		this.seatNumber = seatNumber;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);
		jsonObject.put("ticket_id", ticketId);
		jsonObject.put("seat_number", seatNumber);
		
		return jsonObject;
	}
	
}
