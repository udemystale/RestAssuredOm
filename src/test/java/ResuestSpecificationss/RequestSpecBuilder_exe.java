package ResuestSpecificationss;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.protocols.Input.Builder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilder_exe {
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
		// requestSpecification = with().baseUri(BASE_URL).header("X-Api-Key", "PMAK" +
		// apiKey_1 + apiKey_2);
		// below is method chanining or we can try

		RequestSpecBuilder builder1 = new RequestSpecBuilder().setBaseUri(BASE_URL).addHeader("X-Api-Key",
				"PMAK" + apiKey_1 + apiKey_2).log(LogDetail.ALL);

		
		 RequestSpecBuilder builder = new RequestSpecBuilder();
		 builder.setBaseUri(BASE_URL); builder.addHeader("X-Api-Key", "PMAK" +
		 apiKey_1 + apiKey_2);
		 builder.log(LogDetail.ALL);
		requestSpecification = builder1.build();
	}

	@Test

	void validate_status_code_tdd() {

		//this change as there is bug --> Cannot get property 'assertionClosure' on null object
		Response response = given().spec(requestSpecification)
			//using header you can add multiple header here as well	
				.get(WORKSPACE);

		assertThat(response.statusCode(), is(equalTo(RESP_200)));

	}

	@Test
	void validate_responce_body_TDD() {

		Response response = given().spec(requestSpecification).get(WORKSPACE).then().log().all().extract().response();
		assertThat(response.statusCode(), is(equalTo(RESP_200)));

		// for body needs to conver to string
		assertThat(response.path("workspaces.name[1]").toString(), is(equalTo("My Workspace")));
		// assertThat(response.path("workspaces.name").toString(), hasItems("Team
		// Workspace", "My Workspace", "w1", "Test Post workspace"));

	}

}
