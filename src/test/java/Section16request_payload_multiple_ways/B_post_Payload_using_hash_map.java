package Section16request_payload_multiple_ways;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.protocols.Input.Builder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.internal.net.http.common.Log;

public class B_post_Payload_using_hash_map {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";

	// RequestSpecification requestSpecification;

	@BeforeClass
	void beforeClass() {

		RequestSpecBuilder builder1 = new RequestSpecBuilder().setBaseUri(BASE_URL)
				.addHeader("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2).log(LogDetail.ALL);

		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(BASE_URL);
		builder.addHeader("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2);
		builder.log(LogDetail.ALL);
		RestAssured.requestSpecification = builder1.build();

		System.out.println("---------Log response-----------");
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(RESP_200).expectContentType(ContentType.JSON).log(LogDetail.ALL);

		// Needes to used RestAssured.responseSpecification

		RestAssured.responseSpecification = responseSpecBuilder.build();
		System.out.println("--------------------");

	}

	@Test
	void validate_post_req_payload_using_map() {
		HashMap<String, Object> mainHash = new HashMap<String, Object>();
		
		HashMap<String,String> nestedmap = new HashMap<String,String>();
		
		nestedmap.put("name", "MySecondWS");
		nestedmap.put("type", "team");
		nestedmap.put("visibility", "personal");
		nestedmap.put("description", "Name has been changed");
		mainHash.put("workspace", nestedmap);
		
		//To avoid serilizatiopn issue i.e covert hasmap to json needs to use jackson databind lib
		
		given().body(mainHash)

				.when().post(WORKSPACE)

				.then().log().all()

				.assertThat()

				.body("workspace.name", equalTo("MySecondWS"),

						// https://regex101.com/ you need to work on regular expression
						"workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

	}

	@Test
	void validate_post_req_non_bdd_style() {
		File file = new File(".//src//main//resources//createWorkspace.json");
	Response response=	with().body(file).post(WORKSPACE);
	
	//here are asserssions make sure to use toString
	assertThat(response.path("workspace.name").toString(), equalTo("My First worksspace21"));
	
	assertThat(response.path("workspace.id").toString(), matchesPattern("^[a-z0-9-]{36}$"));
	
	}

	
}
