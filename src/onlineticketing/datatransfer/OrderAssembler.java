package onlineticketing.datatransfer;

import java.util.ArrayList;

import onlineticketing.domain.Order;
import onlineticketing.domain.Ticket;

public class OrderAssembler {

	public ArrayList<OrderDTO> writeDTO(ArrayList<Order> orderList){
		
		ArrayList<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		
		for(Order order : orderList) {
			OrderDTO orderDTO = new OrderDTO(order.getId(), order.getPayment(), order.getCreateTime(),
					order.getStatus(), order.getCustomerId(), order.getTicketInformation());
			orderDTOList.add(orderDTO);
		}
		
		return orderDTOList;
	}
	
	public OrderDTO writeDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO(order.getId(), order.getPayment(), order.getCreateTime(),
				order.getStatus(), order.getCustomerId(), order.getTicketInformation());
		return orderDTO;
	}
	
	public OrderDTO writeDTO(int customerId, ArrayList<Ticket> tickets){
		
		OrderDTO orderDTO = new OrderDTO(customerId, tickets);
		return orderDTO;
	}
}
