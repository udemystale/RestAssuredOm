import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

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
	static Response res;

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

}
