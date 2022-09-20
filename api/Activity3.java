package Activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Activity3 {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp(){

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType("application/json")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("status",equalTo("alive"))
                .build();
    }

    @DataProvider
    public Object[][] petInfoProvider() {
        // Setting parameters to pass to test case
        Object[][] testData = new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
        return testData;
    }

    @Test(priority = 1)
    public void postRequestTest(){
        Map<String,Object> reqBody1 = new HashMap<>();
        reqBody1.put("id","77232");
        reqBody1.put("name","Riley");
        reqBody1.put("status","alive");

        Map<String,Object> reqBody2 = new HashMap<>();
        reqBody2.put("id","77233");
        reqBody2.put("name","Hansel");
        reqBody2.put("status","alive");

        Response response1 =
                given().spec(requestSpec).body(reqBody1)
                        .when().post();
        Response response2 =
                given().spec(requestSpec).body(reqBody2)
                        .when().post();

        response1.then().spec(responseSpec);
        response2.then().spec(responseSpec);

    }


    @Test(dataProvider = "petInfoProvider", priority=2)
    public void getRequest(int petId, String name, String status){
        given().spec(requestSpec).pathParam("petId",petId)
                .when().get("/{petId}")
                .then().spec(responseSpec).body("name",equalTo(name));

    }

    @Test(dataProvider = "petInfoProvider", priority=3)
    public void deleteRequest(int petId, String name, String status){
        given().spec(requestSpec).pathParam("petId",petId)
                .when().delete("/{petId}")
                .then().body("message",equalTo(""+petId));

    }

}
