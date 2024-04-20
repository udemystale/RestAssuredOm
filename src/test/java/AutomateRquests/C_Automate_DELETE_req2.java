package AutomateRquests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class C_Automate_DELETE_req2 {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";
	String res;
	
	@BeforeMethod
	void post_req() {
		String bodyCon = "{\r\n" + "    \"workspace\": {\r\n" + "        \"name\": \"My First worksspace\",\r\n"
				+ "        \"type\": \"team\",\r\n" + "        \"visibility\": \"personal\"\r\n" + "    }\r\n" + "}";

	 res=	given().body(bodyCon)

				.when().post(WORKSPACE).then().extract().response().path("workspace.id");
	System.out.println(res);

	}

	@org.testng.annotations.BeforeClass
	void beforeClass() {
		// Default req specification
		RequestSpecBuilder reqSpec = new RequestSpecBuilder();
		reqSpec.setBaseUri(BASE_URL);
		reqSpec.addHeader("X-Api-Key", "PMAK" + apiKey_1 + apiKey_2).log(LogDetail.ALL);

		RestAssured.requestSpecification = reqSpec.build();

		// Default response specification

		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder().expectStatusCode(RESP_200)
				.expectContentType(ContentType.JSON).log(LogDetail.ALL);
		RestAssured.responseSpecification = respBuilder.build();
	}

	@Test

	// First way to patch with header
	void validate_DELETE_bdd_style_1() {
		String workspaceId = res;
		given()

				// we can provide the workspace id here this is first way
				.when().delete(WORKSPACE + "/" + workspaceId)

				.then().log().all().assertThat()

				.body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"), "workspace.id", equalTo(workspaceId));

	}

	@Test

	// here we are using via path param
	void validate_patch_bdd_style_2() {
		String workspaceId = res;
		given().pathParam("workspa", workspaceId)

				// we can provide the workspace id here this is first way
				.when().delete("/workspaces/{workspa}")

				.then().log().all().assertThat()

				.body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"), "workspace.id", equalTo(workspaceId));

	}
}
