#Author: Ash, Manual 2 Automation Testing
@UI
Feature: PageTitle

  @Regression
  Scenario Outline: Validate Page Title
    Given I am on Google homepage
    When I search for a "<search item>"
    Then the page title should include the "<search item>"

    Examples: 
      | search item |
      | iphone      |
