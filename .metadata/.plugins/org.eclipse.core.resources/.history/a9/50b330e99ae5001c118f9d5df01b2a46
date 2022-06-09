package com.gadget_badget.orders.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class OrderServlet {
	
	//Connect to the MySQL DB
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver");  
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget?useTimezone=true&serverTimezone=UTC", "root", "123456789"); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();} 
		 	return con; 
		} 

		public String insertOrder(String order_code, String customer_id, String customer_email, String customer_name, String total_amount, String card_no, String cvv_no) 
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
				 	 String query = "INSERT INTO order_tab(`orderID`,`orderCode`,`customerID`,`customerEmail`,`customerName`,`totalAmount`,`cardNo`,`cvvNo`)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
					 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, order_code);
					 preparedStmt.setString(3, customer_id);
					 preparedStmt.setString(4, customer_email);
					 preparedStmt.setString(5, customer_name);
					 preparedStmt.setDouble(6, Double.parseDouble(total_amount));
					 preparedStmt.setString(7, card_no);
					 preparedStmt.setString(8, cvv_no);
					 				 
					 preparedStmt.execute(); 
					 con.close(); 
					 output = "Inserted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while inserting the order.";
				 System.err.println(e.getMessage());
			 } 
		 	return output; 
		 } 

		//Read Orders
		 public String readOrder() 
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
				 output = "<table border='1'><tr><th>Order Code</th>"
				 + "<th>Customer ID</th>" +
				 "<th>Customer Email</th>" + 
				 "<th>Customer Name</th>" + 
				 "<th>Total Amount</th>" +
				 "<th>Card No</th>" +
				 "<th>CVV No</th>" +
				 "<th>Update</th><th>Remove</th></tr>"; 
			 
				 
				 String query = "SELECT * FROM order_tab"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
					 String orderID = Integer.toString(rs.getInt("orderID")); 
					 String orderCode = rs.getString("orderCode"); 
					 String customerID = rs.getString("customerID"); 
					 String customerEmail = rs.getString("customerEmail"); 
					 String customerName = rs.getString("customerName"); 
					 String totalAmount = Double.toString(rs.getDouble("totalAmount")); 
					 String cardNo = rs.getString("cardNo");
					 String cvvNo = rs.getString("cvvNo");
					 
					 
					 // Add into the html table
					 output += "<tr><td>" + orderCode + "</td>"; 
					 output += "<td>" + customerID + "</td>"; 
					 output += "<td>" + customerEmail + "</td>"; 
					 output += "<td>" + customerName + "</td>"; 
					 output += "<td>" + totalAmount + "</td>"; 
					 output += "<td>" + cardNo + "</td>"; 
					 output += "<td>" + cvvNo + "</td>"; 					 
					 
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-info'></td>"
					 + "<td><form method='post' action='items.jsp'>"
					 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					 + "<input name='orderID' type='hidden' value='" + orderID 
					 + "'>" + "</form></td></tr>"; 
				 } 
				 	 con.close(); 
				 	 // Complete the html table
				 	 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while reading the order"; 
				 System.err.println(e.getMessage()); 
			 } 
		 	 return output; 
		 } 
				
		//Update Orders
		public String updateOrder(String order_id, String order_code, String customer_id, String customer_email, String customer_name , String total_amount , String card_no , String cvv_no)
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
				 String query = "UPDATE order_tab SET orderCode=? , customerID=? , customerEmail=? , customerName=? , totalAmount=? , cardNo=? , cvvNo=?  WHERE orderID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, order_code); 
				 preparedStmt.setString(2, customer_id); 
				 preparedStmt.setString(3, customer_email); 
				 preparedStmt.setString(4, customer_name); 
				 preparedStmt.setDouble(5, Double.parseDouble(total_amount)); 
				 preparedStmt.setString(6, card_no); 
				 preparedStmt.setString(7, cvv_no); 
				 preparedStmt.setInt(8, Integer.parseInt(order_id)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 output = "Updated successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while updating the item."; 
				 System.err.println(e.getMessage()); 
			 } 
			 	return output; 
			 } 
		
		
			//Delete Orders
			 public String deleteOrder(String orderID) 
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
				 String query = "DELETE FROM order_tab WHERE orderID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(orderID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 output = "Deleted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while deleting the order."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
} 