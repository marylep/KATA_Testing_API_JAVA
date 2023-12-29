package step_definitions;

import builders.PetBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.Owner;
import dto.Pet;
import dto.Type;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import support.JSONFileParser;
import support.MyConfig;
import support.SupportFunctions;

public class PetsStepDef extends JSONFileParser {
    private static ResponseBody body;

    private static Response response;

    private static int petsNumber;

    PetBuilder builder = new PetBuilder();

    public static String createdPetName;

    @When("^I want to know all the pets in the clinic$")
    public void i_want_to_know_all_the_pets_in_the_clinic() {
        body = SupportFunctions.get(MyConfig.Endpoint + "api/pets");
        System.out.println(body.asString());
    }

    @Then("^I should receive 13 pets$")
    public void i_should_receive_pets() throws IOException {
        Pet[] petsDTO = SupportFunctions.convertResponseArray(Pet[].class);
        int amountOfPets = petsDTO.length;
        Assert.assertEquals("the amount of pets is 13 | ", 13, amountOfPets);
    }

    @Then("^the correct number of pets are received$")
    public void the_correct_number_of_pets_are_received() throws IOException {
        Pet[] petsDTO = SupportFunctions.convertResponseArray(Pet[].class);
        int amountOfPets = petsDTO.length;
        System.out.println("the amount of pets is: " + amountOfPets);
        //If we had access to the DB we could get the number of pets from the DB as well and assert that they are equal to the swagger response
    }

    @When("^user wants to add a \"([^\"]*)\" new pet of type \"([^\"]*)\"$")
    public void user_wants_to_add_a_new_pet_of_type(String scenario, String petTypeCode) throws IOException {
        //Get number of pets before creation of new pet
        body = SupportFunctions.get(MyConfig.Endpoint + "api/pets");
        Pet[] petsDTO = SupportFunctions.convertResponseArray(Pet[].class);
        petsNumber = petsDTO.length;

        //Get pet type details
        body = SupportFunctions.get(MyConfig.Endpoint + "api/pettypes/" + 2);
        Type petType = body.as(Type.class);
        Assert.assertEquals(petTypeCode, petType.getName());

        //Get owner details
        body = SupportFunctions.get(MyConfig.Endpoint + "api/owners/" + 1);
        Owner owner = body.as(Owner.class);
        Assert.assertEquals("George", owner.getFirstName());

        //Create new pet
        Pet newPet = builder.createNewPet(petType, owner, scenario);
        response = SupportFunctions.post(MyConfig.Endpoint + "api/pets", mapPOJOtoJson(newPet));
    }

    @Then("^the response is correct for scenario \"([^\"]*)\"$")
    public void theResponseIsCorrectForScenario(String scenario) throws IOException {
        //Get number of pets after creation attempt of new pet
        body = SupportFunctions.get(MyConfig.Endpoint + "api/pets");
        Pet[] petsDTO = SupportFunctions.convertResponseArray(Pet[].class);
        int petsDTOsize = petsDTO.length;

        switch(scenario) {
            case "valid":
                Assert.assertEquals(201, response.getStatusCode());
                Pet createdPet = response.body().as(Pet.class);
                createdPetName = createdPet.getName();
                Assert.assertEquals(petsNumber + 1, petsDTOsize);
                break;
            case "invalid":
                Assert.assertEquals(400, response.getStatusCode());
                Assert.assertEquals(petsNumber, petsDTOsize);
                break;
        }
    }

    @And("^the new entry is found in the pets list$")
    public void theNewEntryIsFoundInThePetsList() throws IOException {
        body = SupportFunctions.get(MyConfig.Endpoint + "api/pets");
        Pet[] petsDTO = SupportFunctions.convertResponseArray(Pet[].class);
        List<Pet> pets = Arrays.asList(petsDTO);
        Assert.assertTrue(pets.stream().anyMatch(pet->pet.getName().equals(createdPetName)));
    }
}