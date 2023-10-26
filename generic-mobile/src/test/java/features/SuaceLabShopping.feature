Feature: Shopping on Sauce lab app

  Background:
    Given User launches sauce lab app

  @Demo @Shopping1
  @Author.Ganesh
  Scenario: Add product to the cart-1
    And User navigates to login screen
    And User enters credentials
#    Then User logs in successfully
#    When User clicks on cart badge
#    And User lands into cart screen
#    When User clicks on go shopping button
#    Then User lands into products Lists screen
    And User selects the product
    And User add the selected product to the cart
    When User clicks on cart badge
    And User clicks on the Proceed to checkout button
#Then User Proceeds to Login screen
    And User is entered into Payment screen
    And User logs out successfully


  @Demo @Shopping
  @Author.Ganesh
  Scenario: Add product to the cart-2
    And User navigates to login screen
    And User enters credentials
#    Then User logs in successfully
#    When User clicks on cart badge
#    And User lands into cart screen
#    When User clicks on go shopping button
#    Then User lands into products Lists screen
    And User selects the product
    And User add the selected product to the cart
    When User clicks on cart badge
    And User clicks on the Proceed to checkout button
#Then User Proceeds to Login screen
    And User is entered into Payment screen
    And User logs out successfully and validate the text

