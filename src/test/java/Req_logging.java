import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import   io.restassured.config.LogConfig;

public class Req_logging {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";
	static String res;

	@Test
	void req_log_all_only_header() {

		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).
		// to log all the request logg
				log().all().when().get(WORKSPACE).then()

				// to log all the response
				.assertThat().statusCode(RESP_200).log().all();

		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).
		// to log only headers the request logg
				log().headers().when().get(WORKSPACE).then()

				// to log cookies and body the response
				.assertThat().statusCode(RESP_200).log().cookies().log().body();

	}

	@Test
	void log_only_if_error() {

		given().baseUri(BASE_URL).header("X-API-Key", "MAK" + apiKey_1 + apiKey_2).

				log().all().when().get(WORKSPACE).
				// to log the error only if there is error from response
				then().log().ifError().assertThat().statusCode(RESP_200);
	}

	@Test
	void log_ony_if_validation_fails() {

		
		// to log the all when validation fails
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).

				log().ifValidationFails().when().get(WORKSPACE).then().log().ifValidationFails().assertThat().statusCode(200);
	
	//In caase we want to avoid the  ifValidationFails uses 2 times then we can use the config
		
		
		System.out.println("================================================================================================================");
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).

		config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).when().get(WORKSPACE).then().assertThat().statusCode(201);
	}
	
	
	@Test 
	//this is done in order to prevent sensative info no one should see that
	public void blackList_heder() {
		
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).
		config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-API-Key"))).log().all().
		
		when().get(WORKSPACE).
		
		then().assertThat().statusCode(RESP_200);
		
		
		System.out.println("===============TO BLACKLIST MULTIPLE HEADER -----====================");
		
		//TO BLACKLIST MULTIPLE HEADER
		
		Set<String> headers = new HashSet<String>();
		
		headers.add("X-API-Key");
		headers.add("Accept");
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK" + apiKey_1 + apiKey_2).
		config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).log().all().
		
		when().get(WORKSPACE)
		.then().assertThat().statusCode(RESP_200);
		
	}
}
