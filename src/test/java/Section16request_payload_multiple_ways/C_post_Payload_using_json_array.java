package Section16request_payload_multiple_ways;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.protocols.Input.Builder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class C_post_Payload_using_json_array {
	public static String BASE_URL = "https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io/";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";

	// RequestSpecification requestSpecification;

	@BeforeClass
	void beforeClass() {

		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(BASE_URL);
		builder.addHeader("x-mock-match-request-body", "true");

		// checke the png as there error to default encoding two opsting

		// 1 set utf-8 encoding as false
//		builder.setConfig(config.encoderConfig(
//				EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
		
		
				//OR
		//set contentype as below in both the rest api code and example as well
		 
		
		builder.setContentType("application/json;charset=UTF-8");
		builder.addHeader("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2);
		builder.log(LogDetail.ALL);
		RestAssured.requestSpecification = builder.build();

		System.out.println("---------Log response-----------");
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(RESP_200).expectContentType(ContentType.JSON).log(LogDetail.ALL);

		// Needes to used RestAssured.responseSpecification

		RestAssured.responseSpecification = responseSpecBuilder.build();
		System.out.println("--------------------");

	}

	@Test
	void validate_post_req_payload_using_json_array() {
		HashMap<String, String> obj5003 = new HashMap<String, String>();
		obj5003.put("id", "5003");
		obj5003.put("type", "Chocolate");

		HashMap<String, String> obj5004 = new HashMap<String, String>();
		obj5004.put("id", "5004");
		obj5004.put("type", "Maple");

		ArrayList<HashMap<String, String>> jsonArray = new ArrayList<HashMap<String, String>>();
		jsonArray.add(obj5003);
		jsonArray.add(obj5004);

		// To avoid serilizatiopn issue i.e covert hasmap to json needs to use jackson
		// databind lib

		given().body(jsonArray)

				.when().post("post1")

				.then().log().all()

				.assertThat()

				.body("msg", equalTo("succeful"));

						

	}

	//@Test
	void validate_post_req_non_bdd_style() {
		File file = new File(".//src//main//resources//createWorkspace.json");
		Response response = with().body(file).post(WORKSPACE);

		// here are asserssions make sure to use toString
		assertThat(response.path("workspace.name").toString(), equalTo("My First worksspace21"));

		assertThat(response.path("workspace.id").toString(), matchesPattern("^[a-z0-9-]{36}$"));

	}

}
