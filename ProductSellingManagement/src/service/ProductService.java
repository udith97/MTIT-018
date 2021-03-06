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

import model.ProductServlet;

@Path("/Product") 
public class ProductService {

ProductServlet productObj = new ProductServlet(); 
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readProduct() 
	{ 
		return productObj.readProduct(); 
	} 	

	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertProduct(
		 @FormParam("username") String username, 
		 @FormParam("title") String title, 
		 @FormParam("price") String price, 
		 @FormParam("description") String description,
		 @FormParam("telephoneNo") String telephoneNo)
		 
	{ 
		String output = productObj.insertProduct(username, title, price, description, telephoneNo); 
		return output; 
	}	
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateProduct(String productData) 
	{ 
		//Convert the input string to a JSON object 
		 JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject(); 
		//Read the values from the JSON object
		 String productId = productObject.get("productId").getAsString(); 
		 String username = productObject.get("username").getAsString(); 
		 String title = productObject.get("title").getAsString(); 
		 String price = productObject.get("price").getAsString(); 
		 String description = productObject.get("description").getAsString();
		 String telephoneNo = productObject.get("telephoneNo").getAsString(); 

		 String output = productObj.updateProduct(productId, username, title, price, description, telephoneNo); 
		 return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteProduct(String productData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(productData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String productId = doc.select("productId").text(); 
		 String output = productObj.deleteProduct(productId); 
		 return output; 
	}
}
