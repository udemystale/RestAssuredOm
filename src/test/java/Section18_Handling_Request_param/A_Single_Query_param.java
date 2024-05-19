package Section18_Handling_Request_param;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class A_Single_Query_param {

	@Test
	void single_query_Param() {
		given().baseUri("https://postman-echo.com/").queryParam("foo1", "book1").log().all().get("get").then().log()
				.all().assertThat().statusCode(200);
	}
}
