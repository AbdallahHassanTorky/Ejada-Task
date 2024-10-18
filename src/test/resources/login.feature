Feature: User Login and Checkout Process


  Scenario Outline: Invalid login attempt
    Given I navigate to the login page
    When I log in with username "<username>" and password "<password>"
    Then I should see an error message "<expectedError>"

    Examples:
      | username       | password          | expectedError                              |
      | invalid_user   | invalid_user      | Epic sadface: Username and password do not match any user in this service |
      | invalid_user   |  invalid_user     | Epic sadface: Username and password do not match any user in this service |
      | invalid_user   | invalid_user      | Epic sadface: Username and password do not match any user in this service |
      | invalid_user   | invalid_user      | Epic sadface: Username and password do not match any user in this service |


  Scenario: Successful login and checkout process
    Given I navigate to the login page
    When  I log in with username "standard_user" and password "secret_sauce"
    Then  I should be on the inventory page
    When  I add the most expensive products to my cart
    And   I navigate to the cart
    Then  I should see my products in the cart
    And   I should be on the cart page
    When  I proceed to checkout
    Then  I should be on the checkout page
    When  I fill in the checkout form with name "John", last name "Doe", and zip code "12345"
    Then  I should see the order summary
    When  I finish the order
    Then   I should see a thank you message and order dispatched confirmation

