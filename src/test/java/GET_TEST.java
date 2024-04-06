import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GET_TEST {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";

	@org.testng.annotations.Test
	void Test() {
		System.out.println("Test is good run");

	}

	@org.testng.annotations.Test
	void get_response() {

		// pre-requists like base url authoriszation and header etc
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).

		// when for after specific action done or server let get patch etc

				when().get(WORKSPACE).

				// then --> validate resopnse form server

				then().log().all().assertThat().statusCode(RESP_200);
	}

	@org.testng.annotations.Test
	void validate_responce_body() {
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE).then().log()
				.all().assertThat().statusCode(RESP_200).

				// using groovy's gpatch

				// here single body with two assertions
				body("workspaces.name", hasItems("Team Workspace", "My Workspace", "w1", "Test Post workspace"),
						"workspaces.name[1]", is(equalTo("My Workspace")))
				.
				// different body
				body("workspaces.id", hasItem("fe759805-408a-4946-8a40-8dbbca2e3236"));

	}

	@org.testng.annotations.Test
	void extract_responce() {

		// Response is an abstract class we are assigning the value to it
		Response res;

		res = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE).then()
				.assertThat().statusCode(RESP_200).extract().response();

		// to print response we as using asString not toStriong

		/*
		 * Note --> Heders is not printed here but it's part of response
		 * 
		 */
		System.out.println("Response -->" + res.asString());

		// for pretty string like josn formater
		System.out.println("Response -->" + res.asPrettyString());

	}

	@Test
	void single_vlaue_from_response() {

		System.out.println("First ways");
		Response res = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE)
				.then().assertThat().statusCode(RESP_200).extract().response();

		// rest ausurred by default uese groovey
		System.out.println("Workspace name -->" + res.path("workspaces[0].name"));
		System.out.println(
				"===============================================================================================");

		System.out.println("Second way ways");
		Response res1 = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when()
				.get(WORKSPACE).then().assertThat().statusCode(RESP_200).extract().response();

		// asString should be used
		JsonPath jpath = new JsonPath(res1.asString());
		// base on return type of the value we need to use the method
		System.out.println("WorkSpace name -->" + jpath.getString("workspaces[0].name"));
		// ex for Array
		System.out.println("WorkSpace name -->" + jpath.getList("workspaces.name"));

		System.out.println(
				"===============================================================================================");

		System.out.println("Third way ways");

		Response res2 = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when()
				.get(WORKSPACE).then().assertThat().statusCode(RESP_200).extract().response();
		String ss = JsonPath.from(res2.asString()).getString("workspaces[0].name");

		ArrayList al = (ArrayList) JsonPath.from(res2.asString()).getList("workspaces.name");
		System.out.println(ss);
		System.out.println(al);

		/*
		 * Same can be done like
		 */
		String res3 = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE)
				.then().assertThat().statusCode(RESP_200).extract().response().asString();

		System.out.println(JsonPath.from(res3).getString("workspaces[0].name"));

		System.out.println(
				"===============================================================================================");

		System.out.println("Fourth way ways");

		// using path() method

		String res4 = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE)
				.then().assertThat().statusCode(RESP_200).extract().response().path("workspaces[0].name");
		System.out.println(res4);
	}

	
	@Test
	void hamcrest_assert_on_extracted_response() {
		String name = given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE)
				.then().assertThat().statusCode(RESP_200).extract().response().path("workspaces[0].name");

		// for this u need to import -->import static org.hamcrest.MatcherAssert.assertThat;
		assertThat(name, equalTo("Team Workspace"));
		
		Assert.assertEquals(name, "Team Workspace");

	}
}
