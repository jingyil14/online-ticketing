package onlineticketing.datatransfer;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import onlineticketing.domain.Film;
import onlineticketing.service.FilmService;

public class FilmServiceBean {
	
	public FilmDTO getFilm(String id) {
		Film film = new FilmService().findFilmByFilmId(id);
		return new FilmAssembler().writeDTO(film);
	}
	
	public String getFilmJson(String id) {
		FilmDTO filmDTO = getFilm(id);
		return filmDTO.toJSONString();
	}
	
	public JSONArray getALlFilmJsonArray(ArrayList<FilmDTO> filmDTOList) {
		JSONArray jsonArray = new JSONArray();
		
		for (FilmDTO filmDTO : filmDTOList) {
			jsonArray.add(filmDTO.toJSONObject());
		}
		
		return jsonArray;
	}
	
	public String getAllFilmsJson() {
		ArrayList<Film> filmList = new FilmService().viewAllFilms();
		ArrayList<FilmDTO> filmDTOList = new FilmAssembler().writeDTO(filmList);
		return getALlFilmJsonArray(filmDTOList).toString();
		
	}
	
	public FilmDTO postFilmJson(String jsonStr) {
		FilmDTO filmDTO = new FilmAssembler().writeDTO(jsonStr);
		return filmDTO;
	}
}
