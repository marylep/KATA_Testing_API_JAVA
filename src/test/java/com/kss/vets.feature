Feature: Vets APIs

  @post_vets
  Scenario Outline: Create a new vet for each specialty
    When user wants to add a new vet for specialty <specialtyInt>
    Then the new vet is found in the vets list with the specialty number <specialtyInt>
    Examples:
      | specialtyInt |
      | 1            |
      | 2            |
      | 3            |