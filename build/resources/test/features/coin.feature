Feature: Manage coins

  Scenario: Create a new coin
    Given a coin with name "Polkadot"
    When the client posts the coin
    Then the response status should be 201
    And the response body should contain "Polkadot"


  Scenario: Get all coins
    When the client requests all coins
    Then the response status should be 200
    And the response should contain 6 coins
