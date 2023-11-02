#Author: Ash

@API
Feature: jsonplaceholder demo

  @Regression
  Scenario: json placeholder scenario
    Given the API for jsonplaceholder
    When I make a request to API
    Then the status code should be 200