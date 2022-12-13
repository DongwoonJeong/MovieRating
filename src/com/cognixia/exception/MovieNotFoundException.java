package com.cognixia.exception;

public class MovieNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public MovieNotFoundException(){
		super("Movie not found.");
	}
}
