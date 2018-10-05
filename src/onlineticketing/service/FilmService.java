package onlineticketing.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

import onlineticketing.datasource.ExclusiveWriteManager;
import onlineticketing.datasource.FilmMapper;
import onlineticketing.datasource.IdentityMap;
import onlineticketing.domain.Film;
import onlineticketing.domain.Schedule;
import onlineticketing.domain.Ticket;
import onlineticketing.onlineticketing.Params;
import onlineticketing.onlineticketing.Session;

public class FilmService {

	/**
	 * Create a new film
	 * @param title  the title of the film
	 * @param description  the description of a film
	 * @param director  the director of the film
	 * @param mainCast the main cast of the film
	 * @param genre  the genre of the film
	 * @param hour  the hour of running time
	 * @param minute  the minute of running time
	 */
	public void createFilm(String title, String description, String director,
			String mainCast, String genre, int hour, int minute)
	{
		Duration runningTime = Duration.ofHours(hour);
		runningTime = runningTime.plusMinutes(minute);
		
		int filmId = getNextFilmId();
		
		Film film = new Film(filmId, title, description, director, mainCast, 
				genre, runningTime);
		FilmMapper filmMapper = new FilmMapper();
		filmMapper.insert(film);
	}
	
	/**
	 * Generate a random int from 0 to 999999999 as the id for the new film,
	 * generate the new id until it does not already exist as a film id
	 * @return the next film id
	 */
	private int getNextFilmId() {
		FilmMapper.findAllFilms();
		
		Film targetFilm = new Film();
		IdentityMap<Film> filmMap = IdentityMap.getInstance(targetFilm);
		
		Random random = new Random();
		int filmId = random.nextInt(Params.MAX_FILM_ID);
		
		while(filmMap.get(Integer.toString(filmId))!=null)
			filmId = random.nextInt(Params.MAX_FILM_ID);
		
		return filmId;
	}
	
	/**
	 * View all films in the database
	 * @return  the list of all the films
	 */
	public ArrayList<Film> viewAllFilms() {
		ArrayList<Film> filmList = FilmMapper.findAllFilms();
		return filmList;
	}
	
	/**
	 * Find all the information of a film (title, description, running time, 
	 * director, main cast, genre, schedules)
	 * @param id  the input film id
	 * @return  the film object with input film id
	 */
	public Film findFilmByFilmId(String id) {
		Film film = FilmMapper.findFilmById(Integer.parseInt(id));
		if(film == null)
			System.err.println("Cannot find the film with film ID " + id);
		
		return film;
	}
	
	/**
	 * Edit the basic information of a film (title, description, 
	 * running time, director, main cast, genre)
	 * @param filmId  the film id of the film to be edited
	 * @param title  the new title
	 * @param description  the new description
	 * @param director  the new director
	 * @param mainCast  the new main cast
	 * @param genre  the new genre
	 */
	public void editFilm(String filmId, String title, String description, 
			String director, String mainCast, String genre) {
		
		Film film = FilmMapper.findFilmById(Integer.parseInt(filmId));
		FilmMapper filmMapper = new FilmMapper();
		
		if (film == null)
			System.err.println("The film being editted does not exist");
		else
			film.setTitle(title);
			film.setDescription(description);
			film.setDirector(director);
			film.setDirector(director);
			film.setMainCast(mainCast);
			film.setGenre(genre);
			filmMapper.update(film);

	}
	
	/**
	 * Delete a film
	 * @param filmId  the film id of the film to be deleted
	 */
	public boolean deleteFilm(String filmId) {
		
		FilmMapper filmMapper = new FilmMapper();
		Film film = FilmMapper.findFilmById(Integer.parseInt(filmId));
		if (film == null) {
			System.err.println("The film being deleted does not exist");
			return false;
		}	
		else {
			//acquire lock for all the tickets of the film
			ExclusiveWriteManager lockingManager = ExclusiveWriteManager.getInstance();
			int userId = Session.getInstance().getUserid();
			boolean acquireLock = true;
			
			ArrayList<Schedule> scheduleList = film.getSchedule();
			ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
			for (Schedule schedule : scheduleList) {
				ticketList.addAll(schedule.getTickets());
			}
			
			for (Ticket ticket : ticketList) {
				if(!lockingManager.acquireLock(ticket.getId(), userId))
					acquireLock = false;
			}
			
			if(!acquireLock) {
				for (Ticket ticket : ticketList) {
					lockingManager.releaseLock(ticket.getId(), userId);
				}
				return false;
			}
			
			filmMapper.delete(film);
			return true;
		}
	}
	
}
