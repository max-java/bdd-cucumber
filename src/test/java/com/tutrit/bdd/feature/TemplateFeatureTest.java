package com.tutrit.bdd.feature;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TemplateFeatureTest {
    private String result;

    @When("I run test")
    public void iRunTest() {
        result = "ok";  // Simulate the logic that sets the result to "ok"
    }

    @Then("value should be {string}")
    public void value_should_be(String expectedResult) {
        assertEquals(expectedResult, result);
    }
}
