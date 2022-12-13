package com.cognixia.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.exception.BadLoginCredentialsException;
import com.cognixia.model.Movie;


public class MovieDAO implements MovieDaoInterface {

	private Connection connection = ConnectionManager.getConnection();

	@Override
	public void Register(String username, String password) {
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("Insert Movie_user(user_id, user_name, user_password)" + "value(NULL, ?, ?)");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			System.out.printf("\nWelcome %s\n", username);

		} catch (Exception e) {
			System.out.println("Something went wrong, cannot create new account");
		}
	}

	// done
	@Override
	public boolean login(String username, String inPassword) {
		try {

			PreparedStatement pstmt1 = connection
					.prepareStatement("Select user_name from Movie_user where user_name = ? ");
			PreparedStatement pstmt2 = connection
					.prepareStatement("Select user_password from Movie_user where user_password = ?");
			pstmt1.setString(1, username);
			pstmt2.setString(1, inPassword);
			// pstmt1.setString
			ResultSet rs1 = pstmt1.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();
			boolean exists1 = rs1.next();
			boolean exists2 = rs2.next();

			if (exists1 == true && exists2 == true) {
				System.out.printf("\n\n\nWelcome %s\n", username);
				return true;
			} else if (exists1 == true) {
				throw new BadLoginCredentialsException();
			} else if (exists2 == true) {
				throw new BadLoginCredentialsException();
			}

		} catch (BadLoginCredentialsException e) {
			e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private int getUserId(String username) {

		try {
			PreparedStatement pstmt = connection.prepareStatement("Select user_id from Movie_user where user_name = ?");
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("user_id");

				return id;
			}
		} catch (SQLException e) {
			System.out.println("User: = " + username + " not found.");
		}
		return -1;
	}


	@Override
	public void getMovie(String showTitle) {

		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from Movie where movie_name = ?;");
			pstmt.setString(1, showTitle);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				String name = rs.getString("movie_name");
				Movie movie = new Movie(id, name);
				System.out.println(movie);

			}
		} catch (SQLException e) {
			System.out.println("Movie with title = " + showTitle + " not found.");
		}

	}
	@Override
	public void getAllmovie() {

		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from Movie order by movie_id");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				String name = rs.getString("movie_name");
				Movie movie = new Movie(id, name);
				
				System.out.println(movie);

			}
		} catch (SQLException e) {
			System.out.println("service not availble at this time.");
		}

	}

}