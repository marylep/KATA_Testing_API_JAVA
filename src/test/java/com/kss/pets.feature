Feature: Pets APIs

  @get_pets
  Scenario: Get all the PETS
    When I want to know all the pets in the clinic
    Then the correct number of pets are received

  @post_pet
  Scenario: Create a new pet - valid
    When user wants to add a "valid" new pet of type "dog"
    Then the response is correct for scenario "valid"
    And the new entry is found in the pets list

  @post_pet
  Scenario: Create a new pet - invalid
    When user wants to add a "invalid" new pet of type "dog"
    Then the response is correct for scenario "invalid"