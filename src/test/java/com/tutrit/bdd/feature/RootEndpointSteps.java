package com.tutrit.bdd.feature;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class RootEndpointSteps {

    @LocalServerPort
    private int port;

    private Response response;

    @When("the client requests the root endpoint")
    public void theClientRequestsTheRootEndpoint() {
        response = given()
                .port(port)
                .when()
                .get("/")
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the response status should be of value {int}")
    public void theResponseStatusShouldBeOfValue(int statusCode) {
        response.then().statusCode(statusCode);
    }
}
