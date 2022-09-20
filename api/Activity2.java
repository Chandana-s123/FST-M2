package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {



    String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void postRequest() throws IOException {

        FileInputStream inputFile = new FileInputStream("src/test/java/Activities/file1.json");
        String reqBody = new String(inputFile.readAllBytes());

        Response response =
                given().contentType(ContentType.JSON)
                        .when().body(reqBody).post(baseURI);

        inputFile.close();
        System.out.println(response.then().extract().body().asPrettyString());

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("95953"));
        response.then().body("firstName", equalTo("John"));
        response.then().body("lastName", equalTo("Deo"));
        response.then().body("email", equalTo("johnDeo@gmail.com"));
        response.then().body("password", equalTo("*12345*"));
        response.then().body("password", equalTo("991234567"));

    }

    @Test(priority=2)
    public void getRequest(){
        Response response =
                given().contentType(ContentType.JSON).pathParam("username","johny")
                        .when().get(baseURI+ "/{username}");

        String resBody = response.getBody().asPrettyString();

        File outputFile = new File("src/test/java/Activities/outFile1.json");
        try {
            outputFile.createNewFile();
            FileWriter writer = new FileWriter(outputFile.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        response.then().body("id", equalTo(95953));
        response.then().body("username", equalTo("johny"));
        response.then().body("firstName", equalTo("John"));
        response.then().body("lastName", equalTo("Deo"));
        response.then().body("email", equalTo("johnDeo@gmail.com"));
        response.then().body("password", equalTo("*12345*"));
        response.then().body("phone", equalTo("991234567"));

    }

    @Test(priority=3)
    public void deleteRequest(){
        Response response =
                given().contentType(ContentType.JSON).pathParam("username","johny")
                        .when().delete(baseURI+ "/{username}");

        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("johny"));

    }
}
