/*package mk.finki.ukim.milenichinja.Web.Controllers;

import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adoptedPets")
public class AdoptedPetsController {

    private final PetService petService;

    public AdoptedPetsController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public String getVdomeniMilenichinja(Model model){
        List<Pet> milenichinja = this.petService.vdomeniMilenichinja();
        model.addAttribute("petList",milenichinja);
        return "mainPages/adoptedPets";
    }
}*/
