package onlineticketing.domain;

import java.util.ArrayList;

import onlineticketing.datasource.IdentityMap;
import onlineticketing.datasource.OrderMapper;

public class Customer extends User{
	private ArrayList<Order> orders = new ArrayList<Order>();
	private boolean orderLoaded;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(int userId, String userName, String password, int phoneNumber, 
			boolean permission) {
		super(userId, userName, password, phoneNumber, permission);
		this.orderLoaded = false;
	}

	public ArrayList<Order> getOrders() {
		if(!this.orderLoaded) {
			this.orders = OrderMapper.findAllOrdersByCustomerId(this.id);
			orderLoaded = true;
		} else {
			Order targetOrder = new Order();
			IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
			
			for (Order order : orders) {
				order = orderMap.get(order.getId());
				OrderMapper.checkLockExpire(order);
			}
		}
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
}
