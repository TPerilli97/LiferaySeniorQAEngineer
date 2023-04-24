Feature: Login actions

  Background:
    Given the user visits the login website

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

