package com.tutrit.bdd.feature;

import com.tutrit.bdd.Application;
import com.tutrit.bdd.model.Coin;
import com.tutrit.bdd.repo.CoinRepo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@CucumberContextConfiguration
@SpringBootTest(classes = {Application.class, Coin.class, CoinRepo.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CoinSteps {
    @LocalServerPort
    private int port;

    private Coin coin;
    private Response response;

    @Given("a coin with name {string}")
    public void aCoinWithName(String name) {
        coin = new Coin(null, name);
    }

    @When("the client posts the coin")
    public void theClientPostsTheCoin() {
        response = given()
                .port(port)
                .contentType("application/json")
                .body(coin)
                .when()
                .post("/coins");
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response body should contain {string}")
    public void theResponseBodyShouldContain(String name) {
        response.then().body("name", equalTo(name));
    }

    @When("the client requests all coins")
    public void theClientRequestsAllCoins() {
        response = given()
                .port(port)
                .when()
                .get("/coins");
    }

    @Then("the response should contain {int} coins")
    public void theResponseShouldContainCoins(int size) {
        response.then()
                .statusCode(200)
                .body("_embedded.coins", hasSize(size));
    }
}
