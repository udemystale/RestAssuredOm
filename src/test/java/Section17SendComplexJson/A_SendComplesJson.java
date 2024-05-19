package Section17SendComplexJson;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

public class A_SendComplesJson {
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

		// OR
		// set contentype as below in both the rest api code and example as well

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
	void validate_copmplex_json_payload() {

		ArrayList<Integer> idArrayList = new ArrayList<Integer>();
		idArrayList.add(5);
		idArrayList.add(6);

		HashMap<String, Object> batter2HashMap = new HashMap<String, Object>();
		batter2HashMap.put("id", idArrayList);
		batter2HashMap.put("type", "Chocolate");
		HashMap<String, Object> batter1HashMap = new HashMap<String, Object>();
		batter1HashMap.put("id", "1001");
		batter1HashMap.put("type", "Regular");

		ArrayList<HashMap<String, Object>> batterAL = new ArrayList<HashMap<String, Object>>();
		batterAL.add(batter1HashMap);
		batterAL.add(batter2HashMap);
		
		HashMap<String, ArrayList<HashMap<String, Object>>> batterHS = new HashMap<String, ArrayList<HashMap<String, Object>>>();
		batterHS.put("batter", batterAL);

		ArrayList<String> typeArrayList = new ArrayList<String>();
		typeArrayList.add("test1");
		typeArrayList.add("test2");

		HashMap<String, Object> topping2HashMap = new HashMap<String, Object>();
		topping2HashMap.put("id", "5002");
		topping2HashMap.put("type", typeArrayList);

		HashMap<String, Object> topping1HashMap = new HashMap<String, Object>();
		topping1HashMap.put("id", "5001");
		topping1HashMap.put("type", "None");

		ArrayList<HashMap<String, Object>> toppingAL = new ArrayList<HashMap<String, Object>>();
		toppingAL.add(topping1HashMap);
		toppingAL.add(topping2HashMap);

		HashMap<String, Object> main = new HashMap<String, Object>();
		main.put("id", "0001");
		main.put("type", "donut");
		main.put("name", "Cake");
		main.put("ppu", 0.55);
		main.put("batters", batterHS);
		main.put("topping", toppingAL);

		
		given().body(main).when().post("Complexjson").then().log().all()

				.assertThat()

				.body("msg", equalTo("succeful"));

	}
}
