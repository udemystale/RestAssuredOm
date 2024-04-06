import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class GET_TEST {
public static String BASE_URL ="https://api.postman.com";
public static String WORKSPACE ="workspaces";
static int RESP_200 =200;
static String apiKey_1 ="-65e5ed1b2cbf790001869f42-";
static String apiKey_2  ="dfea8a7457bed05a72d9d335de0797c27a";

	@org.testng.annotations.Test
	void Test() {
		System.out.println("Test is good run");

	}
@org.testng.annotations.Test
	void get_response() {

		//pre-requists like base url authoriszation and header etc
		given().baseUri(BASE_URL).header("X-API-Key", "PMAK"+apiKey_1+apiKey_2).
		
		
		//when for after specific action done or server let get patch etc
		
		when().get(WORKSPACE).
		
		
		//then --> validate resopnse form server
		
		then().log().all().assertThat().statusCode(RESP_200);
	}

}
