package service;

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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.OfferServlet;

@Path("/Offer") 
public class OfferService {

OfferServlet offerObj = new OfferServlet(); 
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOffer() 
	{ 
		return offerObj.readOffer(); 
	} 	

	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertOffer(
		 @FormParam("userName") String userName, 
		 @FormParam("offerTitle") String offerTitle, 
		 @FormParam("description") String description, 
		 @FormParam("telephoneNo") String telephoneNo,
		 @FormParam("userEmail") String userEmail)
		 
	{ 
		String output = offerObj.insertOffer(userName, offerTitle, description, telephoneNo, userEmail); 
		return output; 
	}	
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOffer(String offerData) 
	{ 
		//Convert the input string to a JSON object 
		 JsonObject offerObject = new JsonParser().parse(offerData).getAsJsonObject(); 
		//Read the values from the JSON object
		 String offerID  = offerObject.get("offerID").getAsString(); 
		 String userName = offerObject.get("userName").getAsString(); 
		 String offerTitle = offerObject.get("offerTitle").getAsString(); 
		 String description = offerObject.get("description").getAsString(); 
		 String telephoneNo = offerObject.get("telephoneNo").getAsString();
		 String userEmail = offerObject.get("userEmail").getAsString(); 

		 String output = offerObj.updateOffer(offerID, userName, offerTitle, description, telephoneNo, userEmail); 
		 return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteOffer(String offerData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(offerData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String offerID = doc.select("offerID").text(); 
		 String output = offerObj.deleteOffer(offerID); 
		 return output; 
	}
}

