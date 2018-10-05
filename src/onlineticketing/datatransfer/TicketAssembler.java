package onlineticketing.datatransfer;

import java.util.ArrayList;

import onlineticketing.domain.Ticket;
import onlineticketing.onlineticketing.Params;

public class TicketAssembler {

	public ArrayList<TicketDTO> writeDTO(ArrayList<Ticket> ticketList){
		
		ArrayList<TicketDTO> ticketDTOList = new ArrayList<TicketDTO>();
		
		for(Ticket ticket : ticketList) {
//			int status = Params.TICKET_AVAILABLE;
//			if(ticket.isSold() == 1)
//				status = Params.TICKET_SOLD;
//			else if (ticket.isLocked())
//				status = Params.TICKET_LOCKED;
			int status = ticket.getStatus();
			TicketDTO ticketDTO = new TicketDTO(status, ticket.getId(), ticket.getSeatNumber());
			ticketDTOList.add(ticketDTO);
		}
		
		return ticketDTOList;
	}
	
}
