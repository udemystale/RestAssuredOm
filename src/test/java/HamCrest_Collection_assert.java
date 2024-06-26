import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collections;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class HamCrest_Collection_assert {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";
	static String res;

	@Test
	void collections_assert() {

		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE).then()
				.assertThat().statusCode(RESP_200)

				// Check all elements are in a collection and in a strict order
				.body("workspaces.name", contains("Team Workspace", "My Workspace", "w1", "Test Post workspace"))

				// hasItems() -> Check all elements are in a collection
				.body("workspaces.name", hasItems("My Workspace", "w1", "Test Post workspace"))

				// containsInAnyOrder() -> Check all elements are in a collection and in any
				// order
				.body("workspaces.name",
						containsInAnyOrder("My Workspace", "w1", "Test Post workspace", "Team Workspace"));
	}

	@Test
	void empty_method() {

		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE).then()
				.assertThat().statusCode(RESP_200).
				// is not empty to check collectio0n is not empty

				body("workspaces.name", is(not(empty())))

				// Check if the Array is not empty
				.body("workspaces.name", is(not(emptyArray()))).

				// Check size of a collection
				body("workspaces.name", hasSize(4))

				// Check if every item in a collection starts with specified string
				// in below case we are check evveryItem is not start with my
				.body("workspaces.name", is(not(everyItem(startsWith("my")))));

	}
	
	@Test
	void map_method() {

		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).when().get(WORKSPACE).then()
				.assertThat().statusCode(RESP_200).
				
				//here we need map so we need to use "workspaces[0]"
				// need to check key then use method hasKey()
				body("workspaces[0]", hasKey("name")).
				// need to check value then needs to use hasValue()
				body("workspaces[0]", hasValue("Team Workspace")).
				
				//need to check key -value pair then use hasEntry
				body("workspaces[0]", hasEntry("name","Team Workspace"))
				
				
				//check collection is Empty or not use equalTo(Collections.EMPTY_MAP)
				.body("workspaces[0]",not(equalTo(Collections.EMPTY_MAP))).
				
				
				
				// These below are used only for string purpose
				//allOf() -> Matches if all matchers matches
				//anyOf() -> Matches if any of the matchers matches
				body("workspaces.name[3]",allOf(startsWith("Test Post"),containsString("workspace")))
				.body("workspaces.name[3]",anyOf(startsWith("Test Post"),containsString("workspace")))
				;
				
				

	}

}
