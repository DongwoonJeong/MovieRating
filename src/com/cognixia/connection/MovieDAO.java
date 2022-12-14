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

	public int getUserId(String username) {

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
	public void getMovie() {

		try {
			PreparedStatement pstmt = connection.prepareStatement("    SELECT  movie.movie_id, movie.movie_name as Name,  AVG(rating_id) as Average_RATING, COUNT(instance_id) as Number_Rating\r\n"
					+ "from Movie\r\n"
					+ "	left JOIN Movie_instance USING ( movie_id )\r\n"
					+ "        GROUP BY movie_id\r\n"
					+ "    order by movie_id;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				String name = rs.getString("movie_name");

				System.out.printf("%1s.%-20s%n",id,name);

			}
		} catch (SQLException e) {
			System.out.println("");
		}

	}
	
	/*("SELECT movie_id, movie_name,  AVG(rating_id), COUNT(instance_id)"
					+ "from Movie"
					+ "inner JOIN Movie_instance USING(movie_id)"
					+ "GROUP BY movie_id"
					+ "order by movie_id;");*/
	@Override
	public void getAllmovie() {

		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT  movie_id , movie_name,  AVG(rating_id), COUNT(instance_id)"
					+ "from Movie"
					+ "	LEFT JOIN Movie_instance USING (movie_id)"
					+ "    GROUP BY movie_id"
					+ "    order by movie_id;");

			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("\t Standalone Rating App \t\t"+
					   "\n+===================================================+");
			System.out.printf("%-20s%-16s%1s%n","Movie","Average Rating","Number of Ratings");
			while (rs.next()) {
				int id = rs.getInt("movie_id");
				String name = rs.getString("movie_name");
				double avg = rs.getDouble("AVG(rating_id)");
				int count = rs.getInt("COUNT(instance_id)");		   
				System.out.printf("%1s.%-20s %-16s%1s%n",id,name,avg,count);


			}System.out.println("\n+===================================================+");
		} catch (SQLException e) {
			System.out.println("service not availble at this time.");
		}

	}

	@Override
	public void rateMovie(int userId, int movieId, int rating) {
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("INSERT INTO Movie_instance(instance_id, user_id, movie_id, rating_id)" + "value(NULL,?,?,?)");
			
			pstmt.setInt(1, userId);
			pstmt.setInt(2, movieId);
			pstmt.setInt(3, rating);
			pstmt.executeUpdate();
			

		} catch (Exception e) {
			System.out.println("Something went wrong, cannot create new account");
		}
		
	}
	
	
	

}