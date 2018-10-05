package onlineticketing.datatransfer;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import onlineticketing.domain.Schedule;
import onlineticketing.domain.Ticket;
import onlineticketing.service.ScheduleService;

public class TicketServiceBean {
	
	public JSONArray getAllTicketJSONArray(ArrayList<TicketDTO> ticketDTOList) {
		JSONArray jsonArray = new JSONArray();
		
		for (TicketDTO ticketDTO : ticketDTOList) {
			jsonArray.add(ticketDTO.toJSONObject());
		}
		
		return jsonArray;
	}
	
	public String getAllTicketsByScheduleId(String scheduleId) {
		Schedule schedule = new ScheduleService().getScheduleByScheduleId(scheduleId);
		ArrayList<Ticket> ticketList = schedule.getTickets();
		JSONArray jsonArray = getAllTicketJSONArray(new TicketAssembler().writeDTO(ticketList));
		return jsonArray.toString();
	}
}
