Feature: Login test of Sauce lab app

Background:
  Given User launches sauce lab app

  @Demo @only
  @Author.Pradeep
  Scenario: Successful login to app
    And User navigates to login screen
    And User enters credentials
    Then User logs in successfully


  @Demo @Smoke @only2
    @Author.Pradeep
  Scenario Outline: Verify Sauce Lab app login
    And User navigates to login screen
    And User enters credentials "<UserName>" and "<Password>"
    Then User logs in successfully
    Examples:
      | UserName        | Password |
      | bob@example.com | 10203040 |
      | problem_user    | 10203040 |

