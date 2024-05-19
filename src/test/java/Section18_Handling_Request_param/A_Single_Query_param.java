package Section18_Handling_Request_param;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

public class A_Single_Query_param {

	@Test
	void single_query_Param() {
		given().baseUri("https://postman-echo.com/").queryParam("foo1", "book1").log().all().get("get").then().log()
				.all().assertThat().statusCode(200);
	}
	
	@Test
	void Multi_query_Param() {
		
		HashMap<String,String> queryParam = new  HashMap<String,String>();
		
		queryParam.put("q1", "v1");
		queryParam.put("q2", "v2");
		queryParam.put("q3", "v4");
		given().baseUri("https://postman-echo.com/").
		queryParam("foo1", "book1").
		queryParam("foo12", "book2").
		queryParams(queryParam)
		
		.log().all().get("get").then().log()
				.all().assertThat().statusCode(200);
	}
}
