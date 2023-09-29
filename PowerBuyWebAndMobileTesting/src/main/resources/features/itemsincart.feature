Feature: Order
  Order many items on ecommerce website

  Scenario: Add many items to cart
    Given I on the power buy home page
    And I change language of the page to English
    When I enter <inputValue> into search textbox
      | inputValue |
      |     TV     |
    And I filter all item to <size>
      |     size       |
      | 44 - 55 inches |
    And I choose first item to add to cart
    And I uncheck checkbox filter all item to <groupsize>
      |     groupsize  |
      | 44 - 55 inches |
    And I filter all item to <size>
      |     size       |
      | 56 - 65 inches |
    And I choose second item to add to cart
    And I navigate to cart page
    Then I should be shown a list containing two items that were just picked up