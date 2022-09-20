package LiveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    Map<String, String> headers = new HashMap<>();

    String resourcePath = "/api/users";

    @Pact(consumer = "userConsumer", provider = "userProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        headers.put("Content-Type", "application/json");
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id", 123)
                .stringType("firstName", "Abc")
                .stringType("lastName", "xyz")
                .stringType("email", "abx.xyz@example.com");


        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                    .method("POST")
                    .path(resourcePath)
                    .headers(headers)
                    .body(requestResponseBody)
                .willRespondWith()
                    .status(201)
                    .body(requestResponseBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "userProvider",port = "8282")
    public void consumerTest(){
        String baseURI = "http://localhost:8282";
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("id",123);
        reqBody.put("firstName","abcd1");
        reqBody.put("lastName","xyz1");
        reqBody.put("email","abc@gmail.com");
        System.out.println(given().headers(headers).when().body(reqBody));
        given().headers(headers).body(reqBody).when().post(baseURI+resourcePath)
                .then().statusCode(201).log().all();

    }
}
