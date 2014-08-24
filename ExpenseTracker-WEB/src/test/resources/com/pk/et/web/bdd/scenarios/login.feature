Feature: ExpenseTracker Login

  Scenario: An authorized user should be able to login to the system
    Given a User enter user name as "admin" and password as "admin"
    When the user clicks the login button
    Then user should be able to login to the system
    
 Scenario: An unauthorized user should not be able to login to the system
    Given a User enter user name as "testuser" and password as "incorrect"
    When the user clicks the login button
    Then an error should be shown
    