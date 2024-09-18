Feature: Login Functionality

  Background:
    Given the user is on the login page

  # Scenario for incorrect username
  Scenario: Unsuccessful login with incorrect username
    When the user enters username "invalid_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should see an error message "Epic sadface: Username and password do not match any user in this service"

  # Scenario for incorrect password
  Scenario: Unsuccessful login with incorrect password
    When the user enters username "standard_user" and password "incorrect_password"
    And the user clicks the login button
    Then the user should see an error message "Epic sadface: Username and password do not match any user in this service"

  # Scenario for missing username
  Scenario: Unsuccessful login with missing username
    When the user enters username "" and password "secret_sauce"
    And the user clicks the login button
    Then the user should see an error message "Epic sadface: Username is required"

  # Scenario for missing password
  Scenario: Unsuccessful login with missing password
    When the user enters username "standard_user" and password ""
    And the user clicks the login button
    Then the user should see an error message "Epic sadface: Password is required"

  # Scenario for successful login
  Scenario: Successful login with valid credentials
    When the user enters username "standard_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should be redirected to the dashboard page