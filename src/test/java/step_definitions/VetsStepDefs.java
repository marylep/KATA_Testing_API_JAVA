package step_definitions;

import builders.VetBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.Specialty;
import dto.Vet;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import support.JSONFileParser;
import support.MyConfig;
import support.SupportFunctions;

public class VetsStepDefs extends JSONFileParser {
    private static ResponseBody body;
    private static Response response;
    private static String firstName;
    VetBuilder builder = new VetBuilder();

    @When("^user wants to add a new vet for specialty (\\d+)$")
    public void userWantsToAddANewVetForSpecialty(int specialtyId) throws JsonProcessingException {
        body = SupportFunctions.get(MyConfig.Endpoint + "api/specialties/" + specialtyId);
        Specialty specialty = body.as(Specialty.class);
        Vet newVet = builder.createNewVet(specialty);
        response = SupportFunctions.post(MyConfig.Endpoint + "api/vets", mapPOJOtoJson(newVet));
        Assert.assertEquals(201, response.getStatusCode());
        firstName = response.body().as(Vet.class).getFirstName();
    }

    @Then("^the new vet is found in the vets list with the specialty number (\\d+)$")
    public void theNewVetIsFoundInTheVetsListWithTheSpecialtyNumberSpecialtyInt(int specialtyId) throws IOException {
        body = SupportFunctions.get(MyConfig.Endpoint + "api/vets");
        Vet[] vetsDTO = SupportFunctions.convertResponseArray(Vet[].class);
        List<Vet> vets = Arrays.asList(vetsDTO);
        List<Vet> filteredVets = vets.stream().filter(vet->vet.getFirstName().equals(firstName)).collect(Collectors.toList());
        Assert.assertTrue(filteredVets.stream().allMatch(vet->vet.getSpecialties().get(0).getId() == specialtyId));
    }
}
