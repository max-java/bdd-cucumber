Feature: Validate root endpoint

  Scenario: Check the root endpoint
    When the client requests the root endpoint
    Then the response status should be of value 200
