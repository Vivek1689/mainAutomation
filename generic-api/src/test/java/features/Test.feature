Feature: API validation

  @Smoke @only1
  @Author.Shadab
  Scenario: Successful booking creation
    Given User creates booking creation request with the below data
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Test      | User1    | 100        | true        | 2023-01-01 | 2023-01-02 | Breakfast       |
    When User hits the create booking api
    Then Verifies the booking got created

  @Smoke @only
  @Author.Shadab
  Scenario: Successful fetching booking data
    Given User hits get booking api for created booking
    Then User verifies the first name in the response as "Test"