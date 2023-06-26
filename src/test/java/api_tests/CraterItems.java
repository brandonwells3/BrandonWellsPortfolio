package api_tests;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.BrowserUtils;
import utilities.DataReader;

public class CraterItems {
	
	String token;
	Response response;
	BrowserUtils utils;
	int itemID;
	String itemName;
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://crater.primetech-apps.com";
	}
	
	@Test
	public void login() {
		String endpoint = "/api/v1/auth/login";
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("username", DataReader.getProperty("username"));
		requestBody.put("password", DataReader.getProperty("password"));
		requestBody.put("device_name", DataReader.getProperty("device_name"));
		
		response = RestAssured.given()
		.contentType("application/json")
		.accept("application/json")
		.body(requestBody)
		.post(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		token = response.jsonPath().getString("token");
	}
	@Test (dependsOnMethods = "login")
	public void createItem() {
		String endpoint = "/api/v1/items";
		utils = new BrowserUtils();
		itemName = "FIFA "+utils.randomNumber();
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", itemName);
		requestBody.put("price", 6000);
		requestBody.put("unit_id", 11);
		requestBody.put("description", "soccer game");
		
		response = RestAssured.given()
		.contentType("application/json")
		.accept("application/json")
		.auth().oauth2("Bearer "+token)
		.body(requestBody)
		.post(endpoint)
		.thenReturn();
		
		response.then().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("data.name", Matchers.equalTo(itemName))
		.and().assertThat().body("data.price", Matchers.equalTo(6000))
		.and().assertThat().body("data.unit_id", Matchers.equalTo(11))
		.and().assertThat().body("data.description", Matchers.equalTo("soccer game"));
		
		itemID = response.jsonPath().getInt("data.id");
	}
	@Test (dependsOnMethods = "createItem")
	public void getItem() {
		String endpoint = "/api/v1/items/"+itemID;
		
		response = RestAssured.given()
		.contentType("application/json")
		.accept("application/json")
		.auth().oauth2("Bearer "+token)
		.get(endpoint)
		.thenReturn();
		
		response.then().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("data.name", Matchers.equalTo(itemName))
		.and().assertThat().body("data.price", Matchers.equalTo(6000))
		.and().assertThat().body("data.unit_id", Matchers.equalTo(11))
		.and().assertThat().body("data.description", Matchers.equalTo("soccer game"));
	}
	@Test (dependsOnMethods = "getItem")
	public void updateItem() {
		String endpoint = "/api/v1/items/"+itemID;
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", itemName);
		requestBody.put("price", 7000);
		requestBody.put("unit_id", 11);
		requestBody.put("description", "soccer game");
		
		response = RestAssured.given()
		.contentType("application/json")
		.accept("application/json")
		.auth().oauth2("Bearer "+token)
		.body(requestBody)
		.put(endpoint)
		.thenReturn();
		
		response.then().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("data.name", Matchers.equalTo(itemName))
		.and().assertThat().body("data.price", Matchers.equalTo(7000))
		.and().assertThat().body("data.unit_id", Matchers.equalTo(11))
		.and().assertThat().body("data.description", Matchers.equalTo("soccer game"));
	}
	@Test (dependsOnMethods = "updateItem")
	public void deleteItem() {
		String endpoint = "/api/v1/items/delete";
		String ID = String.valueOf(itemID);
		String[] itemIds = {ID};
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("ids", itemIds);
		
		RestAssured.given()
		.contentType("application/json")
		.accept("application/json")
		.auth().oauth2("Bearer "+token)
		.body(requestBody)
		.post(endpoint)
		.then().statusCode(200)
		.contentType("application/json")
		.body("success", Matchers.equalTo(true));
	}
}
