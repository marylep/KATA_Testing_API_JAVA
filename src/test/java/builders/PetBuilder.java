package builders;

import dto.Owner;
import dto.Pet;
import dto.PetsOwner;
import dto.Type;
import java.util.ArrayList;

public class PetBuilder extends Pet {

    public Pet fillPet() {
        Pet pet = new Pet();
        pet.setName("Bun");
        pet.setBirthDate("2023-12-28T20:31:28.970Z");
        pet.setVisits(new ArrayList<>());
        return pet;
    }

    public Pet createNewPet(Type petType, Owner owner, String scenario) {
        Pet pet = fillPet();
        switch(scenario) {
            case "valid":
                pet.setType(petType);
                PetsOwner owner1 = new PetsOwner();
                owner1.setId(owner.getId());
                owner1.setFirstName(owner.getFirstName());
                owner1.setLastName(owner.getLastName());
                owner1.setAddress(owner.getAddress());
                owner1.setCity(owner.getCity());
                owner1.setTelephone(owner.getTelephone());
                pet.setOwner(owner1);
                break;
            case "invalid":
                pet.setName(null);
                pet.setBirthDate(null);
                pet.setType(null);
                pet.setOwner(null);
        }
        return pet;
    }
}
