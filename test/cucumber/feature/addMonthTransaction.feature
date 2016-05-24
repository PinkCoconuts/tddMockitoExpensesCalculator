# language: en
# Source: http://github.com/aslakhellesoy/cucumber/blob/master/examples/i18n/en/features/addition.feature
# Updated: Mon May 23 2016 - 12:40 AM
Feature: Add a new month transaction
  In order to add a new expense or income
  As a user
  I want to be able to fill in month transaction name, amount and specify category, month and type.

  Scenario Outline: Add month transaction
    Given I have entered <input_1> as month transaction name into the GUI
    And I have chosen <input_2> as a type of the month transaction in the GUI
    And I have chosen <input_3> as a month of the month transaction in the GUI
    And I have chosen <input_4 > as a category of the month transaction in the GUI
    And I have entered <input_5>  as an ammount of the month transaction in the GUI
    When I press the <button> button
    Then the result on the GUI should be <output>
    And if there is not error the month transaction should be added to the database

    Examples: 
      | input_1         | input_2   | input_3           | input_4       | input_5   | button | output                                           |
      | "Salary 'Jan"   | "income"  | "January 2016"    | "Salaries"    | 100000.00 | add    | "Month Transaction was inserted successfully!"   |
      | "Popcorn"       | "expense" | "January 2016"    | "Food"        | 25.50     | add    | "Month Transaction was inserted successfully!"   |
      | ""              | "income"  | "January 2016"    | "Salaries"    |           | add    | "Ups! An error occurred. Please try again later" |
