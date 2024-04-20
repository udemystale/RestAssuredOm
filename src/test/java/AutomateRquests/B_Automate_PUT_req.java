package AutomateRquests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class B_Automate_PUT_req {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";

	@org.testng.annotations.BeforeClass
	void beforeClass() {
		// Default req specification
		RequestSpecBuilder reqSpec = new RequestSpecBuilder();
		reqSpec.setBaseUri(BASE_URL);
		reqSpec.addHeader("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2).log(LogDetail.ALL);

		RestAssured.requestSpecification = reqSpec.build();

		// Default response specification

		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder().expectStatusCode(RESP_200)
				.expectContentType(ContentType.JSON).log(LogDetail.ALL);
		RestAssured.responseSpecification=	respBuilder.build();
	}
	
	
	@Test
	
	//First way to patch with header
	void validate_patch_bdd_style_1()
	{
		String workspaceId ="801653a4-0731-4832-af4d-6095ab25e8bd";
		String payload ="{\r\n"
				+ "    \"workspace\": {\r\n"
				+ "        \"name\": \"My First TeChnage1\",\r\n"
				+ "        \"type\": \"team\",\r\n"
				+ "        \"visibility\": \"personal\"\r\n"
				+ "    }\r\n"
				+ "}";
		given().body(payload)
		
		
		//we can provide the workspace id here this is first way
		.when().put(WORKSPACE+"/"+workspaceId)
		
		.then().log().all()
		.assertThat()

		.body("workspace.name", equalTo("My First TeChnage1"),
				"workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
				"workspace.id",equalTo(workspaceId)
				);
		
		
		
	}
	
	@Test
	
	//here we are using via path param
	void validate_patch_bdd_style_2()
	{
		String workspaceId ="801653a4-0731-4832-af4d-6095ab25e8bd";
		String payload ="{\r\n"
				+ "    \"workspace\": {\r\n"
				+ "        \"name\": \"My First TeChnage2\",\r\n"
				+ "        \"type\": \"team\",\r\n"
				+ "        \"visibility\": \"personal\"\r\n"
				+ "    }\r\n"
				+ "}";
		given().body(payload).pathParam("workspa",workspaceId)
		
		
		//we can provide the workspace id here this is first way
		.when().put("/workspaces/{workspa}")
		
		.then().log().all()
		.assertThat()

		.body("workspace.name", equalTo("My First TeChnage2"),
				"workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
				"workspace.id",equalTo(workspaceId)
				);
		
		
		
	}
}
