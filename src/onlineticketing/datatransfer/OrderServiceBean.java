package onlineticketing.datatransfer;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onlineticketing.datasource.TicketMapper;
import onlineticketing.domain.Customer;
import onlineticketing.domain.Order;
import onlineticketing.domain.Ticket;
import onlineticketing.domain.User;
import onlineticketing.service.CustomerService;

public class OrderServiceBean {
	
	public JSONArray getOrderJsonArray(ArrayList<OrderDTO> orderDTOList) {
		JSONArray jsonArray = new JSONArray();
		
		for(OrderDTO orderDTO : orderDTOList) {
			jsonArray.add(orderDTO.toJSONObject());
		}
		
		return jsonArray;
	}

	public String getAllOrdersJson() {
		ArrayList<Order> orderList = new CustomerService().viewAllOrders();
		ArrayList<OrderDTO> orderDTOList = new OrderAssembler().writeDTO(orderList);
		JSONArray jsonArray = getOrderJsonArray(orderDTOList);
		return jsonArray.toString();
	}
	
	public String getAllOrdersByCustomerId(int userid) {
		User user = new CustomerService().getUserByUserId(userid);
		assert !(user instanceof Customer) : "user is not a customer object";
		Customer customer = (Customer)user;
		ArrayList<OrderDTO> orderDTOList = new OrderAssembler().writeDTO(customer.getOrders());
		JSONArray jsonArray = getOrderJsonArray(orderDTOList);
		return jsonArray.toString();
	}
	
	public String getOrderByOrderId(int orderId) {
		Order order = new CustomerService().viewOrder(Integer.toString(orderId));
		OrderDTO orderDTO = new OrderAssembler().writeDTO(order);
		JSONObject jsonObject = orderDTO.toJSONObject();
		return jsonObject.toString();
	}
	
	public OrderDTO createOrder(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		int customerId = jsonObject.getInt("customer_id");
		JSONArray ticketIds = jsonObject.getJSONArray("ticket_ids");
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		
		for(int i = 0; i < ticketIds.size(); i++) {
			String ticketId = ticketIds.getString(i);
			Ticket ticket = TicketMapper.findTicketByTicketId(ticketId);
			ticketList.add(ticket);
		}
		
		return new OrderAssembler().writeDTO(customerId, ticketList);
		
	}
	
}
