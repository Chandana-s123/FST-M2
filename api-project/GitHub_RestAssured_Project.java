package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public class GitHub_RestAssured_Project {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    String shhKey;
    int id;


    @BeforeClass
    public void setUp(){

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType("application/json")
                .setAuth(oauth2("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"))
                .build();
    }

    @Test(priority = 1)
    public void postShhKey(){
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC4T+sRXIBBARDdFO6z7wn1EO8RcPq8n7z1rn7gFomxDISbXQ+mery07kntfMfKRmXmJ/z9jt1Pldd3GiqFK7OSVbVm/Zt2lb42cniYU+1YHF5RBrpV/rkhAAxQRWAcyVb6DXGWZZIzXmzI/2/B2vZRyBTKX5Em03L1N/thTVeHqTejgQ0iqaRLQXSjCODhnm9jq/vzovoZD6Hu0vcZdT+P+GL3F0Zbc+rKYFsjHnctSTa1D7U+3MZ13eBBSeF3+cSgbSbZFHLGFU7PYMTsQIuPN8DCzG6nmavlGraKl3oO5Y9Vcpr2UkzfA76uOUHMuXYjP/j0sDncD8NAxeZUksU7");

        Response postResponse =
        given().spec(requestSpec).when().body(reqBody).post("/user/keys");
        System.out.println(postResponse.getBody().asPrettyString());
        id = postResponse.then().extract().path("id");
        postResponse.then().statusCode(201);
    }

    @Test (priority = 2)
    public void getShhKey(){
        Response getResponse = given().spec(requestSpec).pathParam("keyId",id).when().get("/user/keys/{keyId}");
        Reporter.log(getResponse.asPrettyString());
        getResponse.then().statusCode(200);

    }

    @Test(priority=3)
    public void deleteShhKey(){
        Response deleteResponse = given().spec(requestSpec).pathParam("keyId",id).when().delete(" /user/keys/{keyId}");
        Reporter.log(deleteResponse.asPrettyString());
        deleteResponse.then().statusCode(204);
    }

}
