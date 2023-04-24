Feature: Logout actions

  Scenario: Logout - Happy Path
    Given the login done correctly "standard_user" and "secret_sauce"
    When the user clicks on Logout
    Then the login page is visible correctly
