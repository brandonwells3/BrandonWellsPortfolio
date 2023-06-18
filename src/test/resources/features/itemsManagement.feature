@regression @itemsTests
Feature: Items Management

  # any steps defined under background will be executed before the first step of each scenario in the feature file
  Background: 
    Given As an entity user, I am logged in
    And I navigate to the Items tab

  @createItem @smoketest
  Scenario: As a user, I am able to create an item or a service
    When I click on the Add Item button
    Then I should be on the item input page
    When I provide item information name "iphone", price 1800, unit "pc", and description "a good book"
    And I click the Save Item button
    Then The item is added to the item list table

  @createItemDB
  Scenario: As a user, I am able to create an item or a service on UI and check the item in DB
    When I click on the Add Item button
    Then I should be on the item input page
    When I create an item with the following information
      | Water | 200 | dz | very refreshing water |
    Then The item is added to the item list table
    And I should be able to see the item in the database
    When I delete the item created above
    Then The item is no longer in the item list table

  @createItemInDB
  Scenario: I am able to create an item or a service in the DB and check the item in the UI
    When I insert a record into the database called "Xbox"
    Then The item should be listed on the items table on the UI
    When I delete the item created above
    Then The item is no longer in the item list table

  @createItemWithDataTable @smoketest
  Scenario: As a user, I am able to create an item or a service
    When I click on the Add Item button
    Then I should be on the item input page
    When I provide item information to the fields
      | MacBook Pro | 180000 | box | a great thing |
    And I click the Save Item button
    Then The item is added to the item list table

  @updateItem
  Scenario: As a user, I am able to update an item or a service
    When I select the item "iphone"
    And I should be on the item details page
    When I update the item price to 3000 dollars
    And I click the Update Item button
    Then the Item price is updated to 30 dollars

  @updateItemDB
  Scenario: As a user, I am able to create an item or a service on UI and check the item in DB
    When I click on the Add Item button
    Then I should be on the item input page
    When I create an item with the following information
      | Water | 200 | dz | very refreshing water |
    Then The item is added to the item list table
    And I should be able to see the item in the database
    When I update the item price to 300
    Then The item price has been updated to 300 in the database
    When I delete the item created above
    Then The item is no longer in the item list table

  @deleteItem
  Scenario: As a user, I am able to delete an item
    When I click on the Add Item button
    Then I should be on the item input page
    When I create an item with the following information
      | Donut | 1400 | dz | very tasty donut |
    Then The item is added to the item list table
    When I delete the item created above
    Then The item is no longer in the item list table

  @deleteItemInDB
  Scenario: I am able to create and delete an item in the DB and check the item in the UI
    When I insert a record into the database called "Xbox"
    Then The item should be listed on the items table on the UI
    When I delete the item created above via database
    And I refresh the page
    Then The item is no longer in the item list table
