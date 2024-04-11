package ResuestSpecificationss;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.specification.RequestSpecification;
public class RequestSepcExm {
	public static String BASE_URL = "https://api.postman.com";
	public static String WORKSPACE = "/workspaces";
	static int RESP_200 = 200;
	static String apiKey_1 = "-65e5ed1b2cbf790001869f42-";
	static String apiKey_2 = "dfea8a7457bed05a72d9d335de0797c27a";
	
	
	@Test
	void validate_status_code()
	{
		//here creating ref variable of interface in this we 
		//are creating object of class which implemented the interface method
		
		RequestSpecification  requestSpecification;
		
		//here extractracting the resuest specification from rquest
		requestSpecification =given().baseUri(BASE_URL).header("X-Api-Key","PMAK" + apiKey_1 + apiKey_2);
		
		
		given().baseUri(BASE_URL).header("X-Api-Key","PMAK" + apiKey_1 + apiKey_2) .
		when().get(WORKSPACE).
		then().assertThat().statusCode(RESP_200);
		
		//so above code can be wriutten as
		
		given(requestSpecification).when().get(WORKSPACE).
		then().assertThat().statusCode(RESP_200);
		
		//OR
		
		given().spec(requestSpecification).when().get(WORKSPACE).
		then().assertThat().statusCode(RESP_200);
	}
	
	
}
