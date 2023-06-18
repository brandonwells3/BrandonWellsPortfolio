@amazon
Feature: Amazon Departments

  @amazonTest
  Scenario: As a user, I am able to select different departments and search
    Given I am on the Amazon home page
    And The departments dropdown is "All Departments"
    When I select the department "Home & Kitchen"
    And I search "coffee mug"
    Then I should be on "coffee mug" search result page
    And The departments dropdown is "Home & Kitchen"
