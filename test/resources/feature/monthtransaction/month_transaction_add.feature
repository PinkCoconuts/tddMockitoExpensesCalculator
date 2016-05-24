# language: en
# Source: http://github.com/aslakhellesoy/cucumber/blob/master/examples/i18n/en/features/addition.feature
# Updated: Mon May 23 2016 - 12:40 AM
Feature: Add a new month transaction
  In order to add a new expense or income
  As a user
  I want to be able to fill in month transaction name, amount and specify category, month and type.

  Scenario Outline: Add month transaction
    Given <input_1> as month transaction name, <input_2> as a type of the transaction, <input_3> as a month, <input_4 > as a category, <input_5>  as an amount
    When I press the <button> button
    Then the result should be <output>

    Examples: 
      | input_1         | input_2   | input_3           | input_4       | input_5     | button   | output                                           |
      | 'salaryJan'     | 'income'  | 'January 2016'    | 'Salaries'    | '100000.00' | 'add'    | 'Month Transaction was inserted successfully!'   |
      | 'Popcorn'       | 'expense' | 'January 2016'    | 'Food'        | '25.50'     | 'add'    | 'Month Transaction was inserted successfully!'  |
      | ''              | 'income'  | 'January 2016'    | 'Salaries'    | ''          | 'add'    | 'Ups! An error occurred. Please try again later' |
