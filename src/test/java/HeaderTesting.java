import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.Header;

public class HeaderTesting {

	@Test
	void multiple_header_test1() {
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").header("header", "value1")
				.header("x-mock-match-request-headers", "header").when().get("headTest")

				.then().log().all().assertThat().statusCode(200);
		
		System.out.println("=============================================================================");
		
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").header("header", "value2")
		.header("x-mock-match-request-headers", "header").when().get("headTest")

		.then().log().all().assertThat().statusCode(200);
	}
	
	@Test
	void multiple_header_test2() {
		
		//by creating header objectr
		
		Header header1 = new Header("header", "value1");
		Header header2 = new Header("x-mock-match-request-headers", "header");
		Header header3 = new Header("header", "value2");
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").header(header1)
				.header(header2).when().get("headTest")

				.then().log().all().assertThat().statusCode(200);
		
		System.out.println("=============================================================================");
		
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").header(header3)
		.header(header2).when().get("headTest")

		.then().log().all().assertThat().statusCode(200);
	}
}
