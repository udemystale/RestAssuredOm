package ResuestSpecificationss;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
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
import io.restassured.specification.ResponseSpecification;
import jdk.internal.net.http.common.Log;

public class ResponseSepcificationBuilder_2 {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";

	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;

	
	@BeforeClass
	void beforeClass() {

		RequestSpecBuilder builder1 = new RequestSpecBuilder().setBaseUri(BASE_URL).addHeader("X-Api-Key",
				"PMAK" + apiKey_1 + apiKey_2).log(LogDetail.ALL);

		
		 RequestSpecBuilder builder = new RequestSpecBuilder();
		 builder.setBaseUri(BASE_URL); builder.addHeader("X-Api-Key", "PMAK" +
		 apiKey_1 + apiKey_2);
		 builder.log(LogDetail.ALL);
		requestSpecification = builder1.build();
		
		//here we are checking for response like status code header etc
		System.out.println("Resp log");
		/*
		 * RestAssured.responseSpecification = RestAssured.expect().
		 * statusCode(RESP_200).log().all();//.contentType(null);
		 */		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(RESP_200).expectContentType(ContentType.JSON).log(LogDetail.ALL);
		responseSpecification =responseSpecBuilder.build();
		System.out.println("--------------------");
				
	}

	@Test

	void validate_status_code_tdd() {

		//this change as there is bug --> Cannot get property 'assertionClosure' on null object
		//no need for response here as just done status code above
		//Response response =
				
				given().spec(requestSpecification)
			//here then method is not overloaded so used spec point to be noted
				.get(WORKSPACE).then().spec(responseSpecification);

		//no need to assert
		//assertThat(response.statusCode(), is(equalTo(RESP_200)));

	}

	@Test
	void validate_responce_body_TDD() {

		Response response = given().spec(requestSpecification).get(WORKSPACE).then().spec(responseSpecification).extract().response();
		
		//No need for the statusCOde assert
		//assertThat(response.statusCode(), is(equalTo(RESP_200)));

		// for body needs to conver to string
		assertThat(response.path("workspaces.name[1]").toString(), is(equalTo("My Workspace")));
		// assertThat(response.path("workspaces.name").toString(), hasItems("Team
		// Workspace", "My Workspace", "w1", "Test Post workspace"));

	}

}
