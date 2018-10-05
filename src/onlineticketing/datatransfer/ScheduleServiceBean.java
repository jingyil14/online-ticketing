package onlineticketing.datatransfer;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onlineticketing.domain.Film;
import onlineticketing.service.FilmService;

public class ScheduleServiceBean {
	
	public ScheduleDTO postSchedule(String jsonStr) {
		return new ScheduleAssembler().writeDTO(jsonStr);
	}
	
	public JSONArray getScheduleJsonArray(ArrayList<ScheduleDTO> scheduleDTOList) {
		JSONArray jsonArray = new JSONArray();
		for(ScheduleDTO scheduleDTO : scheduleDTOList) {
			JSONObject jsonObject = scheduleDTO.toJSONObject();
			jsonArray.add(jsonObject);
		}
		return jsonArray;
		
	}
	
	public String getScheduleJson(String filmId) {
		Film film = new FilmService().findFilmByFilmId(filmId);
		ArrayList<ScheduleDTO> scheduleDTOList = new ScheduleAssembler().writeDTO(film.getSchedule());
		return getScheduleJsonArray(scheduleDTOList).toString();
	}
}
