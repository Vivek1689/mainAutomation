Feature: Swag lab login feature

  Validate login with different credentials

  @demo @only1
  @Author.Pradeep
  Scenario Outline: Verify Swag lab login
    Given user navigates to Swag Lab page
    And user login using "<UserName>" and "<Password>"
    Then user should login successfully
    Examples:
      | UserName      | Password     |
      | standard_user | secret_sauce |
      | invalid_user  | secret_sauce |


  @demo @log
  @Author.Pradeep
  Scenario:  Verify capturing the network logs
    Given user opens a page "https://www.thesaurus.com/browse/teameffort"

  @demo @only1
  @Author.Pradeep
  Scenario: Verify Swag lab login and logout
    Given user navigates to Swag Lab page
    And user login using "standard_user" and "secret_sauce"
    Then user should login successfully
    And user should logout