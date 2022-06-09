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

import model.UserServlet;

@Path("/User")
public class UserService {

	UserServlet userObj = new UserServlet();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readUser() {
		return userObj.readUser();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUser(
			@FormParam("username") String username, 
			@FormParam("password") String password,
			@FormParam("published_date") String published_date,
			@FormParam("published_time") String published_time)

	{
		String output = userObj.insertUser(username, password, published_date, published_time);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String userData) {
		// Convert the input string to a JSON object
		JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
		// Read the values from the JSON object
		String iduser  = userObject.get("iduser").getAsString();
		String username = userObject.get("username").getAsString();
		String password = userObject.get("password").getAsString();
		String published_date = userObject.get("published_date").getAsString();
		String published_time = userObject.get("published_time").getAsString();

		String output = userObj.updateUser(iduser, username, password, published_date, published_time);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(String userData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(userData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String iduser = doc.select("iduser").text();
		String output = userObj.deleteUser(iduser);
		return output;
	}
}
