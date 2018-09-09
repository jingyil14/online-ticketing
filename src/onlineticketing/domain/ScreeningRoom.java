package onlineticketing.domain;

import java.util.ArrayList;

public class ScreeningRoom {

	int screeningRoomId;
	ArrayList<Seat> seats;
	
	public ScreeningRoom() {
		
	}
	
	public ScreeningRoom(int screeningRoomId, ArrayList<Seat> seats) {
		super();
		this.screeningRoomId = screeningRoomId;
		this.seats = seats;
	}

	public int getScreeningRoomId() {
		return screeningRoomId;
	}

	public void setScreeningRoomId(int screeningRoomId) {
		this.screeningRoomId = screeningRoomId;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	
}
