package onlineticketing.datatransfer;

import net.sf.json.JSONObject;

public class FilmDTO {
	
	private String filmId;
	private String title;
	private String description;
	private String director;
	private String mainCast;
	private String genre;
	private int runningHour;
	private int runningMin;
	
	public FilmDTO(String filmId, String title, String description, String director, String mainCast, String genre,
			int runningHour, int runningMin) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.description = description;
		this.director = director;
		this.mainCast = mainCast;
		this.genre = genre;
		this.runningHour = runningHour;
		this.runningMin = runningMin;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("film_id", filmId);
		jsonObject.put("film_title", title);
		jsonObject.put("film_description", description);
		jsonObject.put("film_director", director);
		jsonObject.put("film_main_cast", mainCast);
		jsonObject.put("film_genre", genre);
		jsonObject.put("film_running_hour", runningHour);
		jsonObject.put("film_running_min", runningMin);
		return jsonObject;
	}

	public String toJSONString() {
		JSONObject jsonObejct = toJSONObject();
		return jsonObejct.toString();
	}
	
	public static FilmDTO readJSON(JSONObject jsonObject) {
		String title = jsonObject.getString("film_title");
		String director = jsonObject.getString("film_director");
		String mainCast = jsonObject.getString("film_main_cast");
		String genre = jsonObject.getString("film_genre");
		String description = jsonObject.getString("film_description");
		
		String filmId = null;
		if (jsonObject.has("film_id"))
			filmId = jsonObject.getString("film_id");
		
		int runningHour = 0;
		if (jsonObject.has("film_running_hour"))
			runningHour = jsonObject.getInt("film_running_hour");
		
		int runningMin = 0;
		if (jsonObject.has("film_running_min"))
			runningMin = jsonObject.getInt("film_running_min");
		
		FilmDTO filmDTO = new FilmDTO(filmId, title, description, director, mainCast, genre, runningHour, runningMin);
		return filmDTO;
	}
	
	public static FilmDTO readJSONString(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		FilmDTO filmDTO = FilmDTO.readJSON(jsonObject);
		return filmDTO;
		
	}

	public String getFilmId() {
		return filmId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDirector() {
		return director;
	}

	public String getMainCast() {
		return mainCast;
	}

	public String getGenre() {
		return genre;
	}

	public int getRunningHour() {
		return runningHour;
	}

	public int getRunningMin() {
		return runningMin;
	}
	
	
	
}
