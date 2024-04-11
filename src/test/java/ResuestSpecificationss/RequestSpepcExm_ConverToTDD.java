package ResuestSpecificationss;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestSpepcExm_ConverToTDD {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";

	RequestSpecification requestSpecification;

	@BeforeClass
	void beforeClass() {
		// insted of given we can use with
		// The only difference between with() and given() is syntactical
		requestSpecification = with().baseUri(BASE_URL).header("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2);
	}

	@Test
	void validate_status_code() {
		// here creating ref variable of interface in this we
		// are creating object of class which implemented the interface method

		// RequestSpecification requestSpecification;

		// here extractracting the resuest specification from rquest
		// requestSpecification =given().baseUri(BASE_URL).header("X-Api-Key","PMAK" +
		// apiKey_1 + apiKey_2);

		given().baseUri(BASE_URL).header("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE).then()
				.assertThat().statusCode(RESP_200);

		// so above code can be wriutten as

		// given is overloaded method
		given(requestSpecification).when().get(WORKSPACE).then().assertThat().statusCode(RESP_200);

		// OR using spec

		given().spec(requestSpecification).when().get(WORKSPACE).then().assertThat().statusCode(RESP_200);
	}

	void validate_status_code_tdd() {

		Response response = requestSpecification.get(WORKSPACE);

		assertThat(response.statusCode(), is(equalTo(RESP_200)));

	}

	@org.testng.annotations.Test
	void validate_responce_body() {
		given().spec(requestSpecification).get(WORKSPACE).then().log().all().assertThat().statusCode(RESP_200).

		// using groovy's gpatch

		// here single body with two assertions
				body("workspaces.name", hasItems("Team Workspace", "My Workspace", "w1", "Test Post workspace"),
						"workspaces.name[1]", is(equalTo("My Workspace")))
				.
				// different body
				body("workspaces.id", hasItem("fe759805-408a-4946-8a40-8dbbca2e3236"));

	}

	@Test
	void validate_responce_body_TDD() {

		Response response = requestSpecification.get(WORKSPACE).then().log().all().extract().response();
		assertThat(response.statusCode(), is(equalTo(RESP_200)));

			//for body needs to conver to string
		assertThat(response.path("workspaces.name[1]").toString(), is(equalTo("My Workspace")));
		//assertThat(response.path("workspaces.name").toString(), hasItems("Team Workspace", "My Workspace", "w1", "Test Post workspace"));
		
	}

}
