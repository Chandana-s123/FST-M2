package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test(priority=1)
    public void postRequest(){

        String reqBody = "{"
                + "\"id\": 77701,"
                + "\"name\": \"pummy\","
                + " \"status\": \"alive\""
                + "}";
        Response response =
                given().contentType(ContentType.JSON)
                        .when().body(reqBody).post(baseURI);

        response.then().body("id", equalTo(77701));
        response.then().body("name", equalTo("pummy"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority=2)
    public void getRequest(){
        Response response =
                given().contentType(ContentType.JSON).pathParam("petId",77701)
                        .when().get(baseURI+ "/{petId}");

        response.then().body("id", equalTo(77701));
        response.then().body("name", equalTo("pummy"));
        response.then().body("status", equalTo("alive"));

    }

    @Test(priority=3)
    public void deleteRequest(){
        Response response =
                given().contentType(ContentType.JSON).pathParam("petId",77701)
                        .when().delete(baseURI+ "/{petId}");

        response.then().body("code", equalTo(200));
        response.then().log().all().body("message", equalTo("77701"));

    }
}
