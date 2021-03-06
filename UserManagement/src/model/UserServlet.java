package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserServlet {

	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver");  
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/database", "root", ""); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();} 
		 	return con; 
		} 

		public String insertUser(String username, String password, String published_date, String published_time) 
		 { 
			 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for inserting."; 
				 } 
			 	 	 // create a prepared statement
				 	 String query = "INSERT INTO user(`iduser`,`username`,`password`,`published_date`,`published_time`)" + " VALUES (?, ?, ?, ?, ?)"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
					 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, username);
					 preparedStmt.setString(3, password);
					 preparedStmt.setString(4, published_date);
					 preparedStmt.setString(5, published_time);		
					 				 
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 String newUser = readUser(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}";
				 System.err.println(e.getMessage());
			 } 
		 	return output; 
		 } 

		//Read Users
		 public String readUser() 
		 { 
			 String output = ""; 

			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for reading."; 
				 } 
				 
				 // Prepare the html table to be displayed
				 output = "<table buser='1'><tr><th>username</th>"
				 + "<th>password</th>" +
				 "<th>published_date</th>" + 
				 "<th>published_time</th>" +
				 "<th>Update</th><th>Remove</th></tr>"; 
			 
				 
				 String query = "SELECT * FROM user"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
					 String iduser = Integer.toString(rs.getInt("iduser")); 
					 String username  = rs.getString("username"); 
					 String password = rs.getString("password"); 
					 String published_date = rs.getString("published_date"); 
					 String published_time = rs.getString("published_time"); 	
					 
					 // Add into the html table
					 output += "<tr><td>" + username + "</td>"; 
					 output += "<td>" + password + "</td>"; 
					 output += "<td>" + published_date + "</td>"; 
					 output += "<td>" + published_time + "</td>"; 		
					 
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" 
					 + iduser + "'>" + "</td></tr>";
				 } 
				 	 con.close(); 
				 	 // Complete the html table
				 	 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while reading the user"; 
				 System.err.println(e.getMessage()); 
			 } 
		 	 return output; 
		 } 
				
		//Update Users
		public String updateUser(String iduser, String username, String password, String published_date, String published_time)
		{ 
			 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for updating."; 
				 } 
				 
				 // create a prepared statement
				 String query = "UPDATE user SET username=? , password=? , published_date=? , published_time=? WHERE iduser=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, username); 
				 preparedStmt.setString(2, password); 
				 preparedStmt.setString(3, published_date); 
				 preparedStmt.setString(4, published_time); 
				 preparedStmt.setInt(5, Integer.parseInt(iduser)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 String newUser = readUser(); output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 	return output; 
			 } 
		
			//Delete Users
			 public String deleteUser(String iduser) 
			 { 
				 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for deleting."; 
			 } 
			 
			 	 // create a prepared statement
				 String query = "DELETE FROM user WHERE iduser=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(iduser)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 String newUser = readUser(); output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		} 
}

