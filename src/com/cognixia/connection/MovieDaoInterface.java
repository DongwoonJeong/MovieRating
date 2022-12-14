package com.cognixia.connection;

public interface MovieDaoInterface {

	
	public boolean login(String username, String password); //checks if username is in table then checks if the input password matchs the users password. Returns true if both are found else false
	public void getMovie();
	public void Register(String username, String password); //create new entry for username and password
	public void getAllmovie();
	public void rateMovie(int userId, int movieId, int rating);
}
