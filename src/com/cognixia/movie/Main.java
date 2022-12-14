package com.cognixia.movie;

import java.util.Scanner;

import com.cognixia.connection.MovieDAO;
import com.cognixia.exception.BadLoginCredentialsException;
import com.cognixia.exception.MovieNotFoundException;

import java.util.Date;
import java.util.InputMismatchException;

public class Main {
	public static void main(String[] args) throws MovieNotFoundException {

		final int MAXATTEMPTS = 3;
		Scanner input = new Scanner(System.in);
		MovieDAO movieSql = new MovieDAO();
		String username = "";
		String password = "";
		boolean entryStatus = false;
		int logInAttenpts = 0;
		//start point		
				System.out.println("\t Standalone Rating App \t\t"+
						   "\n+=======================================+"+
						   "\n|1.REGISTER		  		|"+
						   "\n|2.LOGIN				|"+
						   "\n|3.EXIT					|"+
						   "\n+=======================================+");
				int switchCase = Integer.parseInt(input.nextLine());

				switch (switchCase) {
				case 1:
					System.out.println("Register");
					System.out.println("Please enter your new user name:");
					username = input.nextLine();
					System.out.println("Please enter your new password:");
					password = input.nextLine();
					movieSql.Register(username, password);
					entryStatus = true;
					break;
				case 2:
					System.out.println("\nLog In");
					while (entryStatus == false) { 
					try {
						System.out.println("Please enter your user name:");
						username = input.nextLine();
						System.out.println("Please enter your password:");
						password = input.nextLine();
					} catch (Exception e) {
						System.out.println("__Please enter a username__\n");
					}

					try {
						if (movieSql.login(username, password) == true) {
							entryStatus = true;
						} else {
							username = "";
							password = "";
							logInAttenpts += 1;
							throw new BadLoginCredentialsException();
						}
					} catch (BadLoginCredentialsException e) {
						System.out.println("\n" + e.getMessage() + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (logInAttenpts >= MAXATTEMPTS) {
						System.out.println("\n\nMAX login attmepts reached\n\nPlease register");
						System.out.println("Please enter your new user name:");
						username = input.nextLine();
						System.out.println("Please enter your new password:");
						password = input.nextLine();
						movieSql.Register(username, password);
						entryStatus = true;

					}
					}
					break;
				case 3:
					System.out.println("\nterminating.");
					entryStatus = false;
					break;
				default:
					System.out.println("\nPlease enter a vaild option\n");
					break;
				}
					//after login. user menu below.
				while (entryStatus == true) {
					System.out.println();
			System.out.println("\t Standalone Rating App \t\t"+
					   "\n+=======================================+"+
					   "\n|1.VIEW MOVIES		  		|"+
					   "\n|2.RATE A MOVIE				|"+
					   "\n|3.EXIT					|"+
					   "\n+=======================================+");

			try {
				int toDo2 = Integer.parseInt(input.nextLine());
				switch (toDo2) {
				case 1:

					System.out.println("Loading all the movie available.");
					movieSql.getAllmovie();
					break;
				case 2:
					Date date = new Date();
					movieSql.getAllmovie();
					System.out.println("select movie from the list.");
					int movieId = Integer.parseInt(input.nextLine());
					System.out.println("\t Standalone Rating App \t\t"+
							   "\n+==========================================+"+
							   "\n|User: "+username +" Date:"+date+" |"+
							   "\n|Selected Movie: "+movieId+		"  	  		   |"+
							   "\n|Select Rating				   |"+
							   "\n|0.Awful		  	           |"+
							   "\n|1.Bad		  			   |"+
							   "\n|2.Disappointing			   |"+
							   "\n|3.Okay					   |"+
							   "\n|4.Good					   |"+
							   "\n|5.Great				   |"+
							   "\n|6.EXIT					   |"+
							   "\n+==========================================+");
					int userId = movieSql.getUserId(username);
					int rating = Integer.parseInt(input.nextLine());
					if (rating < 6) {
						movieSql.rateMovie(userId, movieId, rating);
						System.out.println("Rating has been added.");
					} else {
						System.out.println("invalid input.");
					}
					break;

				case 3:
					System.out.println("\nbye.");
					entryStatus = false;
					break;
				default:
					System.out.println("\nPlease enter a vaild option\n");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nPlease enter a vaild option\n");
				input.next();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		input.close();

	}
}