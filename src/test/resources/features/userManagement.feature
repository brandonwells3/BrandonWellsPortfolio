@login @regression
Feature: Crater app user management
  Users with permissions should be able to interact with the application on successful login

  Background: 
    Given As a user, I am on the login page

  @validlogin @smoketest
  Scenario: Successful login
    When I enter a valid username and a valid password
    And I click on the login button
    Then I should be on the user profile page

  @invalidlogin
  Scenario: Invalid username login
    When I enter an invalid username and a valid password
    And I click on the login button
    Then I should see an error message
    And I should not be logged in

  @invalidPasswordLogin
  Scenario: Invalid password login
    When I enter a valid username and an invalid password
    And I click on the login button
    Then I should see an error message
    And I should not be logged in

  @invalidLoginAttempts
  Scenario Outline: Invalid credential login attempt
    When I enter an email "<email>" and password "<password>"
    And I click on the login button
    Then I should see an error message
    And I should not be logged in

    Examples: 
      | email                           | password         |
      | entityadmin@primetechschool.com | password123      |
      | entityAd@primetechschool.com    | primetech@school |
      |																	| Testing123       |
      | nothing@primetechschool.com     |      						 |

  @validLoginAttempts
  Scenario Outline: Valid credential login attempt
    When I enter an email "<email>" and password "<password>"
    And I click on the login button
    Then I should be on the user profile page

    Examples: 
      | email                           | password         |
      | entityadmin@primetechschool.com | primetech@school |
      | entityadmin@primetechschool.com | primetech@school |
      | entityadmin@primetechschool.com | primetech@school |
      | entityadmin@primetechschool.com | primetech@school |
