package builders;

import dto.Specialty;
import dto.Vet;
import java.util.ArrayList;
import java.util.List;

public class VetBuilder extends Vet {
    public Vet createNewVet(Specialty specialty) {
        Vet vet = new Vet();
        switch(specialty.getName()) {
            case "radiology":
                vet.setFirstName("Amelia");
                vet.setLastName("Bennett");
                break;
            case "surgery":
                vet.setFirstName("Lucas");
                vet.setLastName("Montgomery");
                break;
            case "dentistry":
                vet.setFirstName("Sofia");
                vet.setLastName("Harper");
                break;
        }
        List<Specialty> specialties=new ArrayList<>();
        Specialty specialty1=new Specialty();
        specialty1.setId(specialty.getId());
        specialty1.setName(specialty.getName());
        specialties.add(specialty1);
        vet.setSpecialties(specialties);
        return vet;
    }
}
