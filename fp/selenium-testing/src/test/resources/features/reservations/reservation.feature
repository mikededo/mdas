Feature: User registration

  Narrative:
  In order to find available flights
  As a client or not client of https://vueling.com/es
  I want to be able to search for flights

  Scenario: Search page
    Given I'm in the main page
    Given I accept all the cookies
    When I want to find a flight
      | Origin | Destination | OutboundDate | ReturnDate | Passengers |
      | BCN    | MAD         | 01/05        |            | 1          |
    Then I get an available flight
