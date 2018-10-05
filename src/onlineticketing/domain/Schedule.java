package onlineticketing.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

import onlineticketing.datasource.IdentityMap;
import onlineticketing.datasource.ScreeningRoomMapper;
import onlineticketing.datasource.TicketMapper;
import onlineticketing.datasource.UnitOfWork;

public class Schedule extends DomainObject{

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ScreeningRoom screeningRoom;
	private float price;
	private int filmId;
	private ArrayList<Ticket> tickets;
	private boolean ticketLoaded;

	public Schedule(){
		
	}
	
	public Schedule(String scheduleId, LocalDateTime startTime, LocalDateTime endTime, float price) {
		super();
		this.id = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
	}
	
	public Schedule(String scheduleId, int screeningRoomId, LocalDateTime startTime,
			LocalDateTime endTime,  float price, int filmId) {
		super();
		this.id = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.filmId = filmId;
		this.screeningRoom = ScreeningRoomMapper.findScreeningRoomById(screeningRoomId);
	}
	
	public Schedule(String scheduleId, int screeningRoomId, int filmId, LocalDateTime startTime, 
			LocalDateTime endTime, float price) {
		super();
		this.id = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.filmId = filmId;
		this.screeningRoom = ScreeningRoomMapper.findScreeningRoomById(screeningRoomId);
		UnitOfWork.getCurrent().registerNew(this);
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		UnitOfWork.getCurrent().registerDirty(this);
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		UnitOfWork.getCurrent().registerDirty(this);
	}

	public int getScreeningRoomId() {
		return screeningRoom.getScreeningRoomId();
	}

	public ScreeningRoom getScreeningRoom() {
		return screeningRoom;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
		UnitOfWork.getCurrent().registerDirty(this);
	}

	public ArrayList<Ticket> getTickets() {
		if(!ticketLoaded) {
			this.tickets = TicketMapper.findTicketsByScheduleId(id);
			this.ticketLoaded = true;
		} else {
			Ticket targetTicket = new Ticket();
			IdentityMap<Ticket> ticketMap = IdentityMap.getInstance(targetTicket);
			
			for (Ticket ticket : tickets) {
				ticket = ticketMap.get(ticket.getId());
			}
		}
		
		return tickets;
	}

	public int getFilmId() {
		return filmId;
	}
	
}
