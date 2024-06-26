import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;

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

		// by creating header objectr

		Header header1 = new Header("header", "value1");
		Header header2 = new Header("x-mock-match-request-headers", "header");
		Header header3 = new Header("header", "value2");
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").header(header1).header(header2)
				.when().get("headTest")

				.then().log().all().assertThat().statusCode(200);

		System.out.println("=============================================================================");

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").header(header3).header(header2)
				.when().get("headTest")

				.then().log().all().assertThat().statusCode(200);
	}

	@Test
	void multiple_header_using_headers() {

		// by creating headers object here is HEADERS OBJECT

		Header header1 = new Header("header", "value1");
		Header header2 = new Header("x-mock-match-request-headers", "header");
		Header header3 = new Header("header", "value2");

		Headers headres1 = new Headers(header1, header2);

		Headers headres2 = new Headers(header3, header2);
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").headers(headres1).when()
				.get("headTest")

				.then().log().all().assertThat().statusCode(200);

		System.out.println("=============================================================================");

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").headers(headres2).when()
				.get("headTest")

				.then().log().all().assertThat().statusCode(200);
	}

	@Test
	void multiple_header_using_mapCollection() {

		// by creating headers object here is HashMap
		HashMap<String, String> HeadersM1 = new HashMap<String, String>();
		HeadersM1.put("header", "value1");
		HeadersM1.put("x-mock-match-request-headers", "header");

		HashMap<String, String> HeadersM2 = new HashMap<String, String>();
		HeadersM1.put("header", "value2");
		HeadersM1.put("x-mock-match-request-headers", "header");

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").headers(HeadersM1).when()
				.get("headTest")

				.then().log().all().assertThat().statusCode(200);

		System.out.println("=============================================================================");

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").headers(HeadersM2).when()
				.get("headTest")

				.then().log().all().assertThat().statusCode(200);
	}

	// headTest/multiValueHeader

	@Test
	void multi_value_Headers() {
		// there two ways to to do this

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io")
				.header("multiValueHeader", "value1", "value2").log().headers().when().get("headTest/multiValueHeader")

				.then().log().headers().assertThat().statusCode(200);

		System.out.println("=============================================================================");
		Header header1 = new Header("multiValueHeader", "value1");
		Header header2 = new Header("multiValueHeader", "value2");

		Headers headres1 = new Headers(header1, header2);

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io").headers(headres1).log().headers()
				.when().get("headTest/multiValueHeader")

				.then().log().all().assertThat().statusCode(200);
	}

	@Test
	void Assert_headers() {
		// Assert he response header

		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io")
				.header("multiValueHeader", "value1", "value2").log().headers().when().get("headTest/multiValueHeader")

				.then().log().headers().assertThat().statusCode(200).
				// using header u can assert one by one
				header("RespForMul", "Multivalue").header("SecondHeader", "MultiSecond");

		System.out.println("=======================================================================");
		// In case you need to validate multiple header use headerssss
		given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io")
				.header("multiValueHeader", "value1", "value2").log().headers().when().get("headTest/multiValueHeader")

				.then().log().headers().assertThat().statusCode(200).
//using header u can assert one by one
				headers("RespForMul", "Multivalue", "SecondHeader", "MultiSecond");

	}

	@Test
	void extract_headers() {
		// create object of headres class and use extract headrs

		Headers extractedHeader = given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io")
				.header("multiValueHeader", "value1", "value2").log().headers().when().get("headTest/multiValueHeader")

				.then().log().headers().assertThat().statusCode(200).extract().headers();

		// Print single header value
		System.out.println("Header name is --> " + extractedHeader.get("RespForMul").getName());
		System.out.println("Header Value is --> " + extractedHeader.get("RespForMul").getValue());

		System.out.println("        TO PREINT ALL THE HEADERS ");

		for (Header header : extractedHeader) {
			System.out.println("Header name is --> " + header.getName());
			System.out.println("Header Value is --> " + header.getValue());
			System.out.println("---------------------------------------------------------------");
		}

	}

	@Test
	void extract_multi_value_headers() {
		// create object of headres class and use extract headrs

		Headers extractedHeader = given().baseUri("https://8bcde19e-bb18-4b95-a5bc-57a2b05ae0ab.mock.pstmn.io")
				.header("multiValueHeader", "value1", "value2").log().headers().when().get("headTest/multiValueHeader")

				.then().log().headers().assertThat().statusCode(200).extract().headers();
		
		//Set all the values to list of string using getValues
		List<String> values = extractedHeader.getValues("MultiValue");

		int temp = 1;
		for (String value : values) {
			System.out.println("MultiValue headers value number  " + temp + " is -->" + value);
			temp = temp + 1;
		}

	}
}
