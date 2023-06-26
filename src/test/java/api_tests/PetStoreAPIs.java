package api_tests;

import static org.testng.Assert.*;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.BrowserUtils;

import static io.restassured.RestAssured.*;

public class PetStoreAPIs {
	
	BrowserUtils utils = new BrowserUtils();
	Response response;
	int petID;
	
	@BeforeTest
	public void setup() {
		baseURI = "https://petstore.swagger.io/v2";
	}
	
	@Test
	public void findPetsByStatus() {
		String endpoint = "/pet/findByStatus";
		
		given().contentType("application/json")
		.accept(ContentType.JSON)
		.when().get(endpoint + "?status=sold")
		.then().statusCode(200)
		.and().contentType("application/json");
	}
	@Test
	public void findPetsByStatus_providingQueryParam() {
		String endpoint = "/pet/findByStatus";
		
		response = given().contentType("application/json")
		.accept(ContentType.JSON)
		.queryParam("status", "sold")
		.when().get(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
	}
	@Test
	public void createAPet() {
		petID = 111 + utils.randomNumber();
		String endpoint = "/pet";
		String requestBody = "{\n"
				+ "  \"id\": "+petID+",\n"
				+ "  \"category\": {\n"
				+ "    \"id\": 21,\n"
				+ "    \"name\": \"Dog\"\n"
				+ "  },\n"
				+ "  \"name\": \"Lufee\",\n"
				+ "  \"photoUrls\": [\n"
				+ "    \"string\"\n"
				+ "  ],\n"
				+ "  \"tags\": [\n"
				+ "    {\n"
				+ "      \"id\": 21989,\n"
				+ "      \"name\": \"M_21999\"\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"status\": \"available\"\n"
				+ "}";
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petID);
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		
		System.out.println(response.jsonPath().get("id").toString());
		System.out.println(response.jsonPath().get("status").toString());
		System.out.println(response.jsonPath().get("tags[0].name").toString());
	}
	@Test
	public void createAPet_with_json_file() {
		String endpoint = "/pet";
		File requestBody = new File("./src/test/resources/json_files/createAPet.json");
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(endpoint)
		.thenReturn();
		
		petID = response.jsonPath().getInt("id");
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petID);
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		
		System.out.println(response.jsonPath().get("id").toString());
		System.out.println(response.jsonPath().get("status").toString());
		System.out.println(response.jsonPath().get("tags[0].name").toString());
	}
	@Test (dependsOnMethods = "createAPet")
	public void findAPetByID() {
		String endpoint = "/pet/" + petID;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint)
		.thenReturn();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petID);
		assertEquals(response.jsonPath().getInt("category.id"), 21);
		assertEquals(response.jsonPath().getString("category.name"), "Dog");
		assertEquals(response.jsonPath().getString("name"), "Lufee");
		assertEquals(response.jsonPath().getString("status"), "available");
	}
	@Test
	public void petNotFound() {
		String endpoint = "/pet/" + 2348572;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint)
		.thenReturn();
		
		assertEquals(response.getStatusCode(), 404);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getString("message"), "Pet not found");
		
		response.prettyPrint();
	}
	@Test (dependsOnMethods = "createAPet")
	public void updatePet() {
		String endpoint = "/pet";
		String requestBody = "{\n"
				+ "  \"id\": "+petID+",\n"
				+ "  \"category\": {\n"
				+ "    \"id\": 21,\n"
				+ "    \"name\": \"Dog\"\n"
				+ "  },\n"
				+ "  \"name\": \"Fido\",\n"
				+ "  \"photoUrls\": [\n"
				+ "    \"string\"\n"
				+ "  ],\n"
				+ "  \"tags\": [\n"
				+ "    {\n"
				+ "      \"id\": 21989,\n"
				+ "      \"name\": \"M_21999\"\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"status\": \"available\"\n"
				+ "}";
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.put(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petID);
		assertEquals(response.jsonPath().getString("name"), "Fido");
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		
		System.out.println(response.jsonPath().get("id").toString());
		System.out.println(response.jsonPath().get("status").toString());
		System.out.println(response.jsonPath().get("tags[0].name").toString());
	}
	@Test (dependsOnMethods = "createAPet")
	public void deleteThePet() {
		String endpoint = "/pet/" + petID;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.delete(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getString("message"), petID + "");
	}
	@Test (dependsOnMethods = "createAPet")
	public void findAPetByID_ChainValidation() {
		String endpoint = "/pet/" + petID;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("id", Matchers.equalTo(petID))
		.and().assertThat().body("category.id", Matchers.equalTo(21))
		.and().assertThat().body("category.name", Matchers.equalTo("Dog"))
		.and().assertThat().body("name", Matchers.equalTo("Lufee"))
		.and().assertThat().body("tags[0].name", Matchers.equalTo("M_21999"))
		.and().assertThat().body("status", Matchers.equalTo("available"));
	}
}
