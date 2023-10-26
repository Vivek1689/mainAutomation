Feature: Shopping cart

  As a visitor of the ecommerce website
  I want to have a shopping cart
  So that I can see the products and costs of what I want to purchase

  Background: User is logged in
    Given I am logged in

  @shopping @demo
  @Author.Ganesh
  Scenario Outline: Adding a product to cart
    Given I am on a product detail page
    When I select a product "<Product name>"
    And I click the add to cart button
    Then the product "<Product name>" is added to cart

    Examples:
      | Product name        |
      | Sauce Labs Backpack |

  @shopping @demo
  @Author.Pradeep
  Scenario: Adding multiple products to cart
    Given I am on a product detail page
    When I select multiple products
      | ProductName                       |
      | Test.allTheThings() T-Shirt (Red) |
      | Sauce Labs Fleece Jacket          |
    And I click the add to cart button
    Then validate multiple products is added to cart
    And user should logout

  @reset @demo
  @Author.Pradeep
  Scenario: Reset the application
    Given I am on a product detail page
    And I click any add to cart button
    When I reset the application
    Then I validate the application is reset

