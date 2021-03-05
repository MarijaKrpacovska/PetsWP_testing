package mk.finki.ukim.milenichinja.Web.Controllers;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.AgeGroup;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.CenterService;
import mk.finki.ukim.milenichinja.Service.PetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/petsList")
public class PetsController {

    private final PetService petService;
    private final CenterService centerService;
    private final AppUserService appUserService;

    public PetsController(PetService petService, CenterService centerService, AppUserService appUserService) {
        this.petService = petService;
        this.centerService = centerService;
        this.appUserService = appUserService;
    }

    //MAIN GET PAGES
    @GetMapping
    public String getAvaliablePets(@RequestParam(required = false) AgeGroup ageSearch,
                                   @RequestParam(required = false) String breedSearch,
                                   @RequestParam(required = false) Gender genderSearch,
                                   @RequestParam(required = false) Type typeSearch,
                                   HttpServletRequest request, Model model){
        List<Pet> pets;
        request.getSession().getAttribute("klient");
        if ( ageSearch == null && breedSearch == null && genderSearch == null && typeSearch == null) {
            pets = this.petService.nevdomeniMilenichinja();
        }
        else {
            pets = this.petService.search(ageSearch, breedSearch, genderSearch, typeSearch);
        }
        model.addAttribute("petList", pets);
        return "mainPages/pets.html";
    }

    @GetMapping("/adoptedPets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAllPets(@RequestParam(required = false) Integer petSearch,
                             @RequestParam(required = false) AgeGroup ageSearch,
                             @RequestParam(required = false) String breedSearch,
                             @RequestParam(required = false) Gender genderSearch,
                             @RequestParam(required = false) Type typeSearch,
                                         Model model){
        List<Pet> adoptedPets;
        List<Pet> notAdoptedPets;

        if ( petSearch != null ) {
            adoptedPets = this.petService.searchAdopted(petSearch);
            notAdoptedPets = this.petService.nevdomeniMilenichinja();
        }
        else if ( ageSearch != null || breedSearch != null || genderSearch != null || typeSearch != null ) {
            adoptedPets = this.petService.vdomeniMilenichinja();
            notAdoptedPets = this.petService.search(ageSearch,breedSearch,genderSearch,typeSearch);
        }
        else{
            adoptedPets = this.petService.vdomeniMilenichinja();
            notAdoptedPets = this.petService.nevdomeniMilenichinja();
        }

        model.addAttribute("petList",adoptedPets);
        model.addAttribute("notAdoptedPetList",notAdoptedPets);

        return "mainPages/allPets.html";
    }
    //GET MAIN PAGES

    //EDIT ADD DELETE ADOPT
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit-form/{id}")
    public String editPetPage(@PathVariable int id, Model model) {
        if (this.petService.findById(id).isPresent()) {
            Pet pet = this.petService.findById(id).get();
            List<Center> centri = this.centerService.listAll();
            model.addAttribute("pet", pet);
            model.addAttribute("centerList",centri);
            return "posts/addPet";
        }
        return "redirect:/products?error=ProductNotFound";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add-form")
    public String addProductPage(Model model) {
        List<Center> milenichinja = this.centerService.listAll();
        model.addAttribute("petList",milenichinja);
        int random=5;
        return "posts/addPet";
    }

    @PostMapping("/add")
    public String addPet(
            @RequestParam(required = false) Integer id,
            @RequestParam String ime,
            @RequestParam Type vid,
            @RequestParam String rasa,
            @RequestParam Gender pol,
            @RequestParam String opis,
            @RequestParam int id_centar,
            @RequestParam String DoB,
            @RequestParam String url_slika,
            HttpServletRequest req) {
        // this.milenicheService.save(ime, vid, vozrast, rasa, pol, opis, embg_volonter, id_centar, url_slika);
        if(id != null){
            this.petService.edit(id, ime, vid, rasa, pol, opis, id_centar, url_slika, DoB );
        }else{
            String username = req.getRemoteUser();
            AppUser user = appUserService.getByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
            this.petService.save(ime, vid, rasa, pol, opis, id_centar, url_slika, user, DoB);
        }
        return "redirect:/petsList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deletePet/{id}")
    public String deletePet(@PathVariable int id){
        petService.delete(id);
        return "redirect:/petsList";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/adoptPet/{id}")
    public String adoptPet(@PathVariable int id, HttpServletRequest req){
        try {
            //AppUser user = (AppUser) req.getSession().getAttribute("user");
            String username = req.getRemoteUser();
            AppUser user = appUserService.getByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
                petService.adoptPet(user, id);
                return "redirect:/petsList";
        } catch (RuntimeException exception) {
            return "redirect:/petsList?error=" + exception.getMessage();
        }
    }
    //EDIT ADD DELETE ADOPT

    //DETAILS
    @GetMapping("/details/{id}")
    public String detailsPage(@PathVariable int id, Model model) {
        if (this.petService.findById(id).isPresent()) {
            Pet pet = this.petService.findById(id).get();
            model.addAttribute("pet", pet);
            return "details/details.html";
        }
        return "redirect:/petsList?error=PetNotFound";
    }
    //DETAILS

}
