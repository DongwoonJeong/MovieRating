package com.cognixia.connection;

public interface MovieDaoInterface {

	
	public boolean login(String username, String password);
	public void Register(String username, String password);
	public void getAllmovie(); //print all the movies in the database, with average rating, total number of ratings.
	public void rateMovie(int userId, int movieId, int rating); // the user can rate the movie.
}
