Feature: Dashboard Functionality

  Background:
    Given the user is logged in and on the Dashboard page

  Scenario: Add an item to the cart
    When the user adds an item to the cart
    Then the item should be added to the cart

  Scenario: Remove an item from the cart
    When the user adds an item to the cart
    And the user removes the item from the cart
    Then the item should be removed from the cart

  Scenario: Navigate to the checkout page
    When the user navigates to the checkout page
    Then the user should be on the checkout page

  Scenario: Sort items from low to high price
    When the user sorts items from low to high price
    Then the items should be sorted from low to high price

  Scenario: Get product details
    When the user views the product details
    Then the product details should be displayed correctly

  Scenario: Logout from the application
    When the user logs out
    Then the user should be logged out and redirected to the login page