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

public class Resuest_Loging {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";
	static String res;

	@Test
	void collections_assert() {

		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).
		//log all the request info such header cookies or saybase url
		log().all().
		
		when().get(WORKSPACE).then().
		//print all the response 
		log().all().assertThat().statusCode(RESP_200);
		
				
	}
}