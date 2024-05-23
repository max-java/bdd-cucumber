package com.tutrit.bdd.integration;

import com.tutrit.bdd.model.Coin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CoinRepoTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testCreateCoin() {
        Coin coin = new Coin(null, "Polkadot");

        given()
                .contentType(ContentType.JSON)
                .body(coin)
                .when()
                .post("/coins")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Polkadot"));
    }

    @Test
    public void testReadCoin() {
        Long id = 10L; // Use the ID of a pre-inserted coin

        given()
                .when()
                .get("/coins/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Bitcoin"));
    }

    @Test
    public void testUpdateCoin() {
        Long id = 30L; // Use the ID of a pre-inserted coin

        Coin updatedCoin = new Coin(id, "UpdatedLitecoin");

        given()
                .contentType(ContentType.JSON)
                .body(updatedCoin)
                .when()
                .put("/coins/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("UpdatedLitecoin"));
    }

    @Test
    public void testDeleteCoin() {
        Long id = 50L; // Use the ID of a pre-inserted coin

        given()
                .when()
                .delete("/coins/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .when()
                .get("/coins/" + id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testGetAllCoins() {
        given()
                .when()
                .get("/coins")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded.coins", hasSize(5));
    }

}
