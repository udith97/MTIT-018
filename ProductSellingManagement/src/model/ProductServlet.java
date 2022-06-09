package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductServlet {

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

		public String insertProduct(String username, String title, String price, String description, String telephoneNo) 
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
				 	 String query = "INSERT INTO products(`productId`,`username`,`title`,`price`,`description`,`telephoneNo`)" + " VALUES (?, ?, ?, ?, ?, ?)"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
					 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, username);
					 preparedStmt.setString(3, title);
					 preparedStmt.setString(4, price);
					 preparedStmt.setString(5, description);
					 preparedStmt.setString(6, telephoneNo);
		
					 				 
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 String newProduct = readProduct(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while inserting the product.\"}";
				 System.err.println(e.getMessage());
			 } 
		 	return output; 
		 } 

		//Read Products
		 public String readProduct() 
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
				 output = "<table bproduct='1'><tr><th>username</th>"
				 + "<th>title</th>" +
				 "<th>price</th>" + 
				 "<th>Description</th>" +
				 "<th>telephoneNo</th>" +
				 "<th>Update</th><th>Remove</th></tr>"; 
			 
				 
				 String query = "SELECT * FROM products"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
					 String productId = Integer.toString(rs.getInt("productId")); 
					 String username = rs.getString("username"); 
					 String title = rs.getString("title"); 
					 String price = rs.getString("price"); 
					 String description = rs.getString("description"); 	
					 String telephoneNo = rs.getString("telephoneNo"); 
					 
					 // Add into the html table
					 output += "<tr><td>" + username + "</td>"; 
					 output += "<td>" + title + "</td>"; 
					 output += "<td>" + price + "</td>"; 
					 output += "<td>" + description + "</td>"; 		
					 output += "<td>" + telephoneNo + "</td>"; 	
					 
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" 
					 + productId + "'>" + "</td></tr>";
				 } 
				 	 con.close(); 
				 	 // Complete the html table
				 	 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while reading the product"; 
				 System.err.println(e.getMessage()); 
			 } 
		 	 return output; 
		 } 
				
		//Update Products
		public String updateProduct(String productId, String username, String title, String price, String description, String telephoneNo)
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
				 String query = "UPDATE products SET username=? , title=? , price=? , description=? , telephoneNo=? WHERE productId=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, username); 
				 preparedStmt.setString(2, title); 
				 preparedStmt.setString(3, price); 
				 preparedStmt.setString(4, description); 
				 preparedStmt.setString(5, telephoneNo); 
				 preparedStmt.setInt(6, Integer.parseInt(productId)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 String newProduct = readProduct(); output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the product.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 	return output; 
			 } 
		
			//Delete Products
			 public String deleteProduct(String productId) 
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
				 String query = "DELETE FROM products WHERE productId=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(productId)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 String newProduct = readProduct(); output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}";
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\": \"Error while deleting the product.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		} 
}
