package onlineticketing.datatransfer;

import java.time.LocalDateTime;
import java.util.ArrayList;

import onlineticketing.domain.Schedule;

public class ScheduleAssembler {
	
	public ScheduleDTO writeDTO(String jsonStr) {
		return ScheduleDTO.readJSONString(jsonStr);
	}
	
	public ArrayList<ScheduleDTO> writeDTO(ArrayList<Schedule> scheduleList) {
		
		ArrayList<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
		
		for (Schedule schedule : scheduleList) {
			LocalDateTime startTime = schedule.getStartTime();
			LocalDateTime endTime = schedule.getEndTime();
			
			ScheduleDTO scheduleDTO = new ScheduleDTO(schedule.getId(), 
					schedule.getFilmId(), schedule.getScreeningRoomId(), 
					schedule.getPrice(), startTime, endTime);
			
			scheduleDTOList.add(scheduleDTO);
		}
		
		return scheduleDTOList;
	}
}
