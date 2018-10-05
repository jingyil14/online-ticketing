package onlineticketing.datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.Ticket;

public class TicketMapper implements DataMapper{
	
	/**
	 * Create a new ticket for a schedule in database
	 * @param obj  the ticket to be created
	 */
	@Override
	public void insert(DomainObject obj) {
		assert !(obj instanceof Ticket) : "obj is not a ticket object";
		Ticket ticket = (Ticket) obj;
		
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		
		String createTicketString = "INSERT INTO ONLINETICKETING.TICKETS "
				+ "(TICKETID, SOLD, SCHEDULEID, SEATID) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createTicketString);
		
		try {
			createStatement.setString(1, ticket.getId());
			createStatement.setInt(2, ticket.isSold());
			createStatement.setString(3, ticket.getScheduleId());
			createStatement.setInt(4, ticket.getSeatId());
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ticketMap.put(ticket.getId(), ticket);
		
	}
	
	
	/**
	 * Update the information (sold and order ID) of a ticket
	 * @param obj  the ticket object with updated infromation
	 */
	@Override
	public void update(DomainObject obj) {
		assert !(obj instanceof Ticket) : "obj is not a ticket object";
		Ticket ticket = (Ticket) obj;
		
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		
		String updateTicketString = "UPDATE ONLINETICKETING.TICKETS "
				+ "SET SOLD = ?, ORDERID = ? WHERE TICKETID = '" 
				+ ticket.getId() + "'";
		PreparedStatement updateStatement = DBConnection.prepare(updateTicketString);
		
		try {
			updateStatement.setInt(1, ticket.isSold());
			updateStatement.setInt(2, ticket.getOrderId());
			updateStatement.execute();
			System.out.println(updateStatement.toString());
			
			DBConnection.close(updateStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ticketMap.put(ticket.getId(), ticket);
	}
	
	
	/**
	 * Delete a ticket
	 * @param obj  the ticket to be deleted
	 */
	@Override
	public void delete(DomainObject obj) {
		assert !(obj instanceof Ticket) : "obj is not a ticket object";
		Ticket ticket = (Ticket) obj;
		
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		
		String updateTicketString = "DELETE FROM ONLINETICKETING.TICKETS "
				+ "WHERE TICKETID = '" + ticket.getId() + "'";
		PreparedStatement createStatement = DBConnection.prepare(updateTicketString);
		
		try {
			createStatement.execute();
			System.out.println(createStatement.toString());
			
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ticketMap.put(ticket.getId(), null);
	}
	
	/**
	 * Find all tickets for a schedule
	 * @param scheduleId  the input schedule ID
	 * @return a list of all tickets that belong to the schedule with input schedule ID
	 */
	public static ArrayList<Ticket> findTicketsByScheduleId(String scheduleId){
		
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		
		String findTicketsString = "SELECT * FROM ONLINETICKETING.TICKETS "
				+ "WHERE SCHEDULEID = '" + scheduleId + "' ORDER BY SEATID ASC";
		PreparedStatement findStatement = DBConnection.prepare(findTicketsString);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		
		try {
			ResultSet rs = findStatement.executeQuery();
			
			while(rs.next()) {
				Ticket ticket = loadTicket(rs);
				targetTicket = ticketMap.get(ticket.getId());
				if(targetTicket == null) {
					ticketMap.put(ticket.getId(), ticket);
					ticketList.add(ticket);
				} else {
					ticketList.add(targetTicket);
				}
				
			}
			DBConnection.close(findStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		for(Ticket ticket : ticketList) {
			checkLockExpire(ticket);
		}
		
		return ticketList;
	}
	
	public static ArrayList<Ticket> findTicketsByOrderId(int orderId){
		
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		
		String findTicketsString = "SELECT * FROM ONLINETICKETING.TICKETS "
				+ "WHERE ORDERID = " + orderId;
		PreparedStatement findStatement = DBConnection.prepare(findTicketsString);
		ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
		
		try {
			ResultSet rs = findStatement.executeQuery();
			
			while(rs.next()) {
				Ticket ticket = loadTicket(rs);
				targetTicket = ticketMap.get(ticket.getId());
				if(targetTicket == null) {
					ticketMap.put(ticket.getId(), ticket);
					ticketList.add(ticket);
				} else {
					ticketList.add(targetTicket);
				}
				
			}
			DBConnection.close(findStatement);
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		for(Ticket ticket : ticketList) {
			checkLockExpire(ticket);
		}
		
		return ticketList;
	}
	
	public static Ticket findTicketByTicketId(String ticketId) {
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		targetTicket = ticketMap.get(ticketId);
		
		if(targetTicket == null) {
			Ticket ticket = null;
			String findTicketsString = "SELECT * FROM ONLINETICKETING.TICKETS "
					+ "WHERE TICKETID = '" + ticketId + "'";
			PreparedStatement findStatement = DBConnection.prepare(findTicketsString);
			
			try {
				ResultSet rs = findStatement.executeQuery();
				
				while(rs.next()) {
					ticket = loadTicket(rs);
					ticketMap.put(ticket.getId(), ticket);
				}
				DBConnection.close(findStatement);
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			checkLockExpire(ticket);
			return ticket;
		}
		else {
			checkLockExpire(targetTicket);
			return targetTicket;
		}
	}
	
	public static Ticket findTicketByTicketIdWithoutLockCheck(String ticketId) {
		Ticket targetTicket = new Ticket();
		IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
		targetTicket = ticketMap.get(ticketId);
		
		if(targetTicket == null) {
			Ticket ticket = null;
			String findTicketsString = "SELECT * FROM ONLINETICKETING.TICKETS "
					+ "WHERE TICKETID = '" + ticketId + "'";
			PreparedStatement findStatement = DBConnection.prepare(findTicketsString);
			
			try {
				ResultSet rs = findStatement.executeQuery();
				
				while(rs.next()) {
					ticket = loadTicket(rs);
					ticketMap.put(ticket.getId(), ticket);
				}
				DBConnection.close(findStatement);
				rs.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ticket;
		}
		else {
			return targetTicket;
		}
	}
	
	
	/**
	 * Generate a ticket object from a resultset
	 * @param rs  the resultset of a user
	 * @return the ticket object generated by the resultset
	 */
	public static Ticket loadTicket(ResultSet rs) {
		Ticket ticket = null;
		
		try {
			String ticketId = rs.getString("TICKETID");
			boolean isSold = (rs.getInt("SOLD")==0 ? false : true);
			int seatId = rs.getInt("SEATID");
			int orderId = rs.getInt("ORDERID");
			String scheduleId = rs.getString("SCHEDULEID");
			
			ExclusiveWriteManager lockingManager = ExclusiveWriteManager.getInstance();
			boolean isLocked = lockingManager.hasLock(ticketId);
			
			ticket = new Ticket(ticketId, isSold, seatId, orderId, scheduleId, isLocked);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ticket;
	}
	
	public static void checkLockExpire (Ticket ticket) {
		ExclusiveWriteManager lockingManager = ExclusiveWriteManager.getInstance();
		
		if (lockingManager.hasLock(ticket.getId())) {
			if (lockingManager.lockExpire(ticket.getId())) {
				lockingManager.releaseLock(ticket.getId());
			}
		}
	}

}
