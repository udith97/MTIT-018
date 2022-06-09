package com.gadget_badget.orders.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import com.gadget_badget.orders.model.OrderServlet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Orders") 
public class OrderService 
{	
	OrderServlet orderObj = new OrderServlet(); 
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOrder() 
	{ 
		return orderObj.readOrder(); 
	} 	

	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertOrder(
		 @FormParam("orderCode") String orderCode, 
		 @FormParam("customerID") String customerID, 
		 @FormParam("customerEmail") String customerEmail, 
		 @FormParam("customerName") String customerName,
		 @FormParam("totalAmount") String totalAmount,
		 @FormParam("cardNo") String cardNo,
		 @FormParam("cvvNo") String cvvNo)
		 
	{ 
		String output = orderObj.insertOrder(orderCode, customerID, customerEmail, customerName, totalAmount, cardNo, cvvNo); 
		return output; 
	}	
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOrder(String orderData) 
	{ 
		//Convert the input string to a JSON object 
		 JsonObject orderObject = new JsonParser().parse(orderData).getAsJsonObject(); 
		//Read the values from the JSON object
		 String orderID = orderObject.get("orderID").getAsString(); 
		 String orderCode = orderObject.get("orderCode").getAsString(); 
		 String customerID = orderObject.get("customerID").getAsString(); 
		 String customerEmail = orderObject.get("customerEmail").getAsString(); 
		 String customerName = orderObject.get("customerName").getAsString(); 
		 String totalAmount = orderObject.get("totalAmount").getAsString(); 
		 String cardNo = orderObject.get("cardNo").getAsString(); 
		 String cvvNo = orderObject.get("cvvNo").getAsString(); 
		 
		 String output = orderObj.updateOrder(orderID, orderCode, customerID, customerEmail, customerName, totalAmount, cardNo, cvvNo); 
		 return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteOrder(String orderData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(orderData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String orderID = doc.select("orderID").text(); 
		 String output = orderObj.deleteOrder(orderID); 
		 return output; 
	}
}