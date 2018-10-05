package onlineticketing.datatransfer;

import java.time.Duration;
import java.util.ArrayList;

import onlineticketing.domain.Film;

public class FilmAssembler {
	
	public FilmDTO writeDTO(Film film) {
		Duration runningTime = film.getRunningTime();
		int runningHour = (int) (runningTime.getSeconds() / 3600);
		int runningMin = (int) (runningTime.getSeconds() % 3600 / 60);
		FilmDTO filmDTO = new FilmDTO(film.getId(), film.getTitle(), 
				film.getDescription(), film.getDirector(), film.getMainCast(), 
				film.getGenre(), runningHour, runningMin);
		return filmDTO;
	}
	
	public ArrayList<FilmDTO> writeDTO(ArrayList<Film> filmList){
		ArrayList<FilmDTO> filmDTOList = new ArrayList<FilmDTO>();
		
		for(Film film : filmList) {
			Duration runningTime = film.getRunningTime();
			int runningHour = (int) (runningTime.getSeconds() / 3600);
			int runningMin = (int) (runningTime.getSeconds() % 3600 / 60);
			FilmDTO filmDTO = new FilmDTO(film.getId(), film.getTitle(), 
					film.getDescription(), film.getDirector(), film.getMainCast(), 
					film.getGenre(), runningHour, runningMin);
			
			filmDTOList.add(filmDTO);
		}
		
		return filmDTOList;
	}
	
	public FilmDTO writeDTO(String jsonStr) {
		return FilmDTO.readJSONString(jsonStr);
	}
	
}
