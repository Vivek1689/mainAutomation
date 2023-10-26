Feature: API validation

  @Smoke @check
  @Author.Pradeep
  Scenario: Health check of web service
    Given User hits get healthCheck api
    Then User verifies the web service is up


  @Smoke @create
  @Author.Pradeep
  Scenario: Create Booking
    Given User hits post to create booking
    Then User verifies the new booking

  @Smoke @update
  @Author.Pradeep
  Scenario: Update Booking
    Given User hits put to update booking id "10"
    Then User verifies the updated booking