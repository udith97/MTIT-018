package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OfferServlet {

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

		public String insertOffer(String userName, String offerTitle, String description, String telephoneNo, String userEmail) 
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
				 	 String query = "INSERT INTO offer(`offerID`,`userName`,`offerTitle`,`description`,`telephoneNo`,`userEmail`)" + " VALUES (?, ?, ?, ?, ?, ?)"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
					 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, userName);
					 preparedStmt.setString(3, offerTitle);
					 preparedStmt.setString(4, description);
					 preparedStmt.setString(5, telephoneNo);
					 preparedStmt.setString(6, userEmail);
		
					 				 
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 String newOffer = readOffer(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newOffer + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while inserting the offer.\"}";
				 System.err.println(e.getMessage());
			 } 
		 	return output; 
		 } 

		//Read Offers
		 public String readOffer() 
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
				 output = "<table boffer='1'><tr><th>userName</th>"
				 + "<th>offerTitle</th>" +
				 "<th>description</th>" + 
				 "<th>telephoneNo</th>" +
				 "<th>userEmail</th>" +
				 "<th>Update</th><th>Remove</th></tr>"; 
			 
				 
				 String query = "SELECT * FROM offer"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
					 String offerID = Integer.toString(rs.getInt("offerID")); 
					 String userName = rs.getString("userName"); 
					 String offerTitle = rs.getString("offerTitle"); 
					 String description = rs.getString("description"); 
					 String telephoneNo = rs.getString("telephoneNo"); 	
					 String userEmail = rs.getString("userEmail"); 
					 
					 // Add into the html table
					 output += "<tr><td>" + userName + "</td>"; 
					 output += "<td>" + offerTitle + "</td>"; 
					 output += "<td>" + description + "</td>"; 
					 output += "<td>" + telephoneNo + "</td>"; 		
					 output += "<td>" + userEmail + "</td>"; 	
					 
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" 
					 + offerID + "'>" + "</td></tr>";
				 } 
				 	 con.close(); 
				 	 // Complete the html table
				 	 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while reading the offer"; 
				 System.err.println(e.getMessage()); 
			 } 
		 	 return output; 
		 } 
				
		//Update Offers
		public String updateOffer(String offerID, String userName, String offerTitle, String description, String telephoneNo, String userEmail)
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
				 String query = "UPDATE offer SET userName=? , offerTitle=? , description=? , telephoneNo=? , userEmail=? WHERE offerID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, userName); 
				 preparedStmt.setString(2, offerTitle); 
				 preparedStmt.setString(3, description); 
				 preparedStmt.setString(4, telephoneNo); 
				 preparedStmt.setString(5, userEmail); 
				 preparedStmt.setInt(6, Integer.parseInt(offerID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 String newOffer = readOffer(); output = "{\"status\":\"success\", \"data\": \"" + newOffer + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the offer.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 	return output; 
			 } 
		
			//Delete Offers
			 public String deleteOffer(String offerID) 
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
				 String query = "DELETE FROM offer WHERE offerID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(offerID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 String newOffer = readOffer(); output = "{\"status\":\"success\", \"data\": \"" + newOffer + "\"}";
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while deleting the offer.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		} 
}

