package onlineticketing.datatransfer;

import java.time.LocalDateTime;

import net.sf.json.JSONObject;

public class ScheduleDTO {
	
	private String scheduleId;
	private int filmId;
	private int screeningRoomId;
	private float price;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	

	public ScheduleDTO(String scheduleId, int filmId, int screeningRoomId, float price, LocalDateTime startTime,
		LocalDateTime endTime) {
		super();
		this.scheduleId = scheduleId;
		this.filmId = filmId;
		this.screeningRoomId = screeningRoomId;
		this.price = price;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("schedule_id", scheduleId);
		jsonObject.put("film_id", filmId);
		jsonObject.put("screening_room_id", screeningRoomId);
		jsonObject.put("price", price);
		jsonObject.put("start_time", startTime);
		jsonObject.put("end_time", endTime);
		
		return jsonObject;
	}

	public String toJSONString() {
		JSONObject jsonObejct = toJSONObject();
		return jsonObejct.toString();
	}
	
	public static ScheduleDTO readJSON(JSONObject jsonObject) {
		
		String scheduleId = null;
		if (jsonObject.has("schedule_id"))
			scheduleId = jsonObject.getString("schedule_id");
		
		int filmId = -1;
		if (jsonObject.has("film_id"))
			filmId = jsonObject.getInt("film_id");
		
		int screeningRoomId = -1;
		if (jsonObject.has("screening_room_id"))
			screeningRoomId = jsonObject.getInt("screening_room_id");
		
		float price = Float.parseFloat(jsonObject.getString("price"));
		int startDay = jsonObject.getInt("start_day");
		int startMonth = jsonObject.getInt("start_month");
		int startYear = jsonObject.getInt("start_year");
		int startHour = jsonObject.getInt("start_hour");
		int startMin = jsonObject.getInt("start_min");
		int endDay = jsonObject.getInt("end_day");
		int endMonth = jsonObject.getInt("end_month");
		int endYear = jsonObject.getInt("end_year");
		int endHour = jsonObject.getInt("end_hour");
		int endMin = jsonObject.getInt("end_min");
		LocalDateTime startTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMin);
		LocalDateTime endTime = LocalDateTime.of(endYear, endMonth, endDay, endHour, endMin);
		
		ScheduleDTO scheduleDTO = new ScheduleDTO(scheduleId, filmId, 
				screeningRoomId, price, startTime, endTime);
		
		return scheduleDTO;
	}
	
	public static ScheduleDTO readJSONString(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		ScheduleDTO scheduleDTO = ScheduleDTO.readJSON(jsonObject);
		return scheduleDTO;
		
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public int getFilmId() {
		return filmId;
	}

	public int getScreeningRoomId() {
		return screeningRoomId;
	}

	public float getPrice() {
		return price;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}
	
	
	
}
