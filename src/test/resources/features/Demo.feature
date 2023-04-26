Feature: Testing automation

  Background:
    Given the user visits the login website

  Scenario Outline: Checkout Complete - Happy Path
    Given the login done correctly "standard_user" and "secret_sauce"
    When the user adds one product to cart through inventory page
    And the user clicks on shopping cart
    And the user accepts in Your Cart page
    And the user inserts the First Name <First Name>
    And the user inserts the Last Name <Last Name>
    And the user inserts the ZipCode <Zip/PostalCode>
    And the user clicks on Continue button in Checkout: Your Information page
    And the user clicks on Finish button in Checkout: Overview page
    Then the order is complete and success message is shown

    Examples:
      | First Name | Last Name | Zip/PostalCode |
      | Tommaso    | Perilli   | 70132          |


  Scenario Outline: Login - Happy path
    When the user inserts the username <username>
    When the user inserts the password <password>
    And the user clicks on login button
    Then The login gets done and inventory page is shown

    Examples:
      | username      | password     |
      | standard_user | secret_sauce |


  Scenario Outline: Login - Negative Path - Missing Username
    When the user inserts the password <password>
    And the user clicks on login button
    Then Missing username message is shown

    Examples:
      | password     |
      | secret_sauce |


  Scenario Outline: Login - Negative Path - Missing Password
    When the user inserts the username <username>
    And the user clicks on login button
    Then Missing password message is shown

    Examples:
      | username      |
      | standard_user |

  Scenario: Login - Negative Path - Missing Password and Username
    When the user clicks on login button
    Then Missing username message is shown

  Scenario Outline: Login - Negative Path - User locked
    When the user inserts the username <username>
    When the user inserts the password <password>
    And the user clicks on login button
    Then user locked message is shown

    Examples:
      | username        | password     |
      | locked_out_user | secret_sauce |

  Scenario: Logout - Happy Path
    Given the login done correctly "standard_user" and "secret_sauce"
    When the user clicks on Logout
    Then the login page is visible correctly