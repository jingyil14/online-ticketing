package onlineticketing.datasource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.Order;
import onlineticketing.onlineticketing.Params;

public class OrderMapper implements DataMapper{

	@Override
	public void insert(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Order) : "obj is not a order object";
		Order order = (Order)obj;
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		String createOrderString = "INSERT INTO ONLINETICKETING.ORDERS "
				+ "(ORDERID, STATUS, CREATETIME, PAYMENT, CUSTOMERID, TICKETINFO) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createOrderString);
		
		try {
			createStatement.setInt(1, order.getOrderId());
			createStatement.setInt(2, order.getStatus());
			createStatement.setObject(3, order.getCreateTime());
			createStatement.setFloat(4, order.getPayment());
			createStatement.setInt(5, order.getCustomerId());
			createStatement.setString(6, order.getTicketInformation());
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderMap.put(order.getId(), order);
	}

	@Override
	public void update(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Order) : "obj is not a order object";
		Order order = (Order)obj;
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		String updateOrderString = "UPDATE ONLINETICKETING.ORDERS SET STATUS = ? "
				+ "WHERE ORDERID = " + order.getOrderId();
		PreparedStatement updateStatement = DBConnection.prepare(updateOrderString);
		
		try {
			updateStatement.setInt(1, order.getStatus());
			updateStatement.execute();
			System.out.println(updateStatement.toString());
			
			DBConnection.close(updateStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderMap.put(order.getId(), order);
	}

	@Override
	public void delete(DomainObject obj) {
		// TODO Auto-generated method stub
		assert !(obj instanceof Order) : "obj is not a order object";
		Order order = (Order)obj;
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		String deleteOrderString = "DELETE * FROM ONLINTICKETING.ORDERS "
				+ "WHERE ORDERID = " + order.getOrderId();
		PreparedStatement deleteStatement = DBConnection.prepare(deleteOrderString);
		
		try {
			deleteStatement.execute();
			System.out.println(deleteStatement.toString());
			
			DBConnection.close(deleteStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderMap.put(order.getId(), null);
		
	}
	
	/**
	 * Find all the orders in the database
	 * @return a list of all the Order objects in the database
	 */
	public static ArrayList<Order> findAllOrders(){
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		ArrayList<Order> orderList = new ArrayList<Order>();
		String findAllOrdersString = "SELECT * FROM ONLINETICKETING.ORDERS";
		PreparedStatement findAllStatement = DBConnection.prepare(findAllOrdersString);
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			
			while(rs.next()) {
				Order order = loadOrder(rs);
				
				targetOrder = orderMap.get(order.getId());
				if(targetOrder == null) {
//					order.getTickets();
					checkLockExpire(order);
					orderMap.put(order.getId(), order);
					orderList.add(order);
				} else {
//					targetOrder.getTickets();
					checkLockExpire(targetOrder);
					orderList.add(targetOrder);
				}
					
			}
			DBConnection.close(findAllStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return orderList;
	}
	
	/**
	 * Find all the order of a customer in the database
	 * @return a list of all the Order objects in the database
	 */
	public static ArrayList<Order> findAllOrdersByCustomerId(String customerId){
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		
		ArrayList<Order> orderList = new ArrayList<Order>();
		String findAllOrdersString = "SELECT * FROM ONLINETICKETING.ORDERS "
				+ "WHERE CUSTOMERID = '" + customerId + "'";
		PreparedStatement findAllStatement = DBConnection.prepare(findAllOrdersString);
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			
			while(rs.next()) {
				Order order = loadOrder(rs);
				
				targetOrder = orderMap.get(order.getId());
				if(targetOrder == null) {
					checkLockExpire(order);
					orderMap.put(order.getId(), order);
					orderList.add(order);
				} else {
					checkLockExpire(targetOrder);
					orderList.add(targetOrder);
				}
					
			}
			DBConnection.close(findAllStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return orderList;
	}
	
	public static Order findOrderByOrderId(String orderId){
		
		Order targetOrder = new Order();
		IdentityMap<Order> orderMap = IdentityMap.getInstance(targetOrder);
		targetOrder = orderMap.get(orderId);
		
		if(targetOrder == null) {
			String findOrderString = "SELECT * FROM ONLINETICKETING.ORDERS "
					+ "WHERE ORDERID = " + orderId;
			PreparedStatement findStatement = DBConnection.prepare(findOrderString);
			Order order = null;
			
			try {
				ResultSet rs = findStatement.executeQuery();
				
				while(rs.next()) {
					order = loadOrder(rs);
				}
				DBConnection.close(findStatement);
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			checkLockExpire(order);
			return order;
			
		} else {
			checkLockExpire(targetOrder);
			return targetOrder;
		}
	}
	
	public static void checkLockExpire(Order order) {
		if(order.getStatus()==Params.ORDER_CREATED)
			order.getTickets();
	}
	
	/**
	 * Generate an order object from a resultset
	 * @param rs the resultset of an order
	 * @return the order object generated by the resultset
	 */
	public static Order loadOrder(ResultSet rs) {
		
		Order order = null;
		try {
			int orderId = rs.getInt("ORDERID");
			float payment = rs.getFloat("PAYMENT");
			Timestamp createTimestamp = rs.getTimestamp("CREATETIME");
			LocalDateTime createTime = convertToLocalDateTimeViaSqlTimestamp(new Date(createTimestamp.getTime()));
			int status = rs.getInt("STATUS");
			int customerId = rs.getInt("CUSTOMERID");
			String ticketInformation = rs.getString("TICKETINFO");
			order = new Order(orderId, payment, createTime, status, customerId, ticketInformation);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return order;
	}
	
	/**
	 * Convert Date type to LocalDateTime type
	 * @param dateToConvert  the date to be converted
	 * @return  the converted LocalDateTime
	 */
	public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
	    return new java.sql.Timestamp(
	      dateToConvert.getTime()).toLocalDateTime();
	}
}
