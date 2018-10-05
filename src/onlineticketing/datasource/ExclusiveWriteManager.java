package onlineticketing.datasource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import onlineticketing.domain.Order;
import onlineticketing.domain.Ticket;
import onlineticketing.onlineticketing.Params;

public class ExclusiveWriteManager {
	
	private static ExclusiveWriteManager exclusiveWriteManager;

	public static ExclusiveWriteManager getInstance() {
		if(exclusiveWriteManager == null)
			exclusiveWriteManager = new ExclusiveWriteManager();
		return exclusiveWriteManager;
	}
	
	public boolean acquireLock (String lockable, int owner) {
		Ticket ticket = TicketMapper.findTicketByTicketId(lockable);
		
		if (ticket.isSold()==1)
			return false;
		if (hasLock(lockable))
			return false;
		
		String createLockString = "INSERT INTO ONLINETICKETING.LOCKS"
				+ "(TICKETID, USERID, DUEDATE) VALUES (?, ?, ?)";
		PreparedStatement createStatement = DBConnection.prepare(createLockString);
		
		try {
			LocalDateTime dueDate = LocalDateTime.now().plusMinutes(Params.LOCK_INTERVAL);
			createStatement.setString(1, lockable);
			createStatement.setInt(2, owner);
			createStatement.setObject(3, dueDate);
			createStatement.execute();
			System.out.println(createStatement.toString());
			DBConnection.close(createStatement);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		ticket.setLocked(true);
		new TicketMapper().update(ticket);
		return true;
	}
	
	public void releaseLock(String lockable, int owner) {
		
		if(hasLock(lockable, owner)) {
			
			Ticket ticket = TicketMapper.findTicketByTicketIdWithoutLockCheck(lockable);
			
			String deleteLockString = "DELETE FROM ONLINETICKETING.LOCKS "
					+ "WHERE TICKETID = '" + lockable + "' AND USERID = " + owner;
			PreparedStatement deleteStatement = DBConnection.prepare(deleteLockString);
			
			try {
				deleteStatement.execute();
				ticket.setLocked(false);
				System.out.println(deleteStatement.toString());
				DBConnection.close(deleteStatement);
				ticket.setLocked(false);
				ticket.setOrderId(-1);
				new TicketMapper().update(ticket);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unexpected error releasing lock on " + lockable);
			}
		} else {
			System.out.println(owner + " has no lock for " + lockable);
		}
		
	}
	
	public void releaseLock(String lockable) {
		
		if(hasLock(lockable)) {
			
			Ticket ticket = TicketMapper.findTicketByTicketIdWithoutLockCheck(lockable);
			
			String deleteLockString = "DELETE FROM ONLINETICKETING.LOCKS "
					+ "WHERE TICKETID = '" + lockable + "'";
			PreparedStatement deleteStatement = DBConnection.prepare(deleteLockString);
			
			try {
				deleteStatement.execute();
				ticket.setLocked(false);
				Order order = OrderMapper.findOrderByOrderId(Integer.toString(ticket.getOrderId()));
				if (order.getStatus() != Params.ORDER_CANCELED) {
					order.setStatus(Params.ORDER_CANCELED);
					new OrderMapper().update(order);
				}
				ticket.setOrderId(-1);
				new TicketMapper().update(ticket);
				System.out.println(deleteStatement.toString());
				DBConnection.close(deleteStatement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unexpected error releasing lock on " + lockable);
			}
		} else {
			System.out.println(" There is no lock for " + lockable);
		}
		
	}
	
	public boolean hasLock(String lockable) {
		String findLockString = "SELECT * FROM ONLINETICKETING.LOCKS WHERE "
				+ "TICKETID = '" + lockable + "'";
		PreparedStatement findAllStatement = DBConnection.prepare(findLockString);
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			if(!rs.next())
				return false;
			else
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		
	}
	
	public boolean hasLock(String lockable, int owner) {
		String findLockString = "SELECT * FROM ONLINETICKETING.LOCKS WHERE "
				+ "TICKETID = '" + lockable + "' AND USERID = " + owner;
		PreparedStatement findAllStatement = DBConnection.prepare(findLockString);
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			if(!rs.next())
				return false;
			else
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		
	}
	
	public boolean lockExpire (String lockable) {
		String findLockString = "SELECT * FROM ONLINETICKETING.LOCKS WHERE "
				+ "TICKETID = '" + lockable + "'";
		PreparedStatement findAllStatement = DBConnection.prepare(findLockString);
		LocalDateTime dueDate = null;
		
		try {
			ResultSet rs = findAllStatement.executeQuery();
			while(rs.next()) {
				Timestamp dueTimestamp = rs.getTimestamp("DUEDATE");
				dueDate = convertToLocalDateTimeViaSqlTimestamp(new Date(dueTimestamp.getTime()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (dueDate == null) {
			System.err.println("There is no lock for " + lockable);
			return true;
		}
		if(LocalDateTime.now().compareTo(dueDate) == -1)
			return false;
		return true;
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
