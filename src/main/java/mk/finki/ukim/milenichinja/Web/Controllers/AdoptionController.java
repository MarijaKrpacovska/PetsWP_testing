package mk.finki.ukim.milenichinja.Web.Controllers;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Service.AdoptionService;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.PetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/adopt")
public class AdoptionController {

    private final PetService petService;
    private final AdoptionService adoptionService;
    private final AppUserService appUserService;

    public AdoptionController(PetService petService, AdoptionService adoptionService, AppUserService appUserService) {
        this.petService = petService;
        this.adoptionService = adoptionService;
        this.appUserService = appUserService;
    }

    //ADOPT
  //  @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/adoptPet/{id}")
    public String adoptPet(@PathVariable int id, Model model) {
        Pet pet = this.petService.findById(id).orElseThrow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = ZonedDateTime.now().format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String todayTime = ZonedDateTime.now().format(formatterTime);
        String today = todayDate + "T" + todayTime;

        model.addAttribute("today", today);
        model.addAttribute("pet",pet);
        return "posts/adopt.html";
    }

    @PostMapping("/adoptPet")
    public String adoptPet(@RequestParam int id,
                           @RequestParam String dateAdoption,
                           HttpServletRequest req){
        try {
            //AppUser user = (AppUser) req.getSession().getAttribute("user");
            String username = req.getRemoteUser();
            AppUser user = appUserService.getByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
            adoptionService.adopt(user,id, dateAdoption);
            return "redirect:/petsList";
        } catch (RuntimeException exception) {
            return "redirect:/petsList?error=" + exception.getMessage();
        }
    }
    //ADOPT

    @PostMapping("/confirm/{id}")
    public String confirmAdoption(@PathVariable int id, HttpServletRequest req){
        try {
            adoptionService.confirmAdoption(id);
            return "redirect:/petsList/pets-info/adopted";
        } catch (RuntimeException exception) {
            return "redirect:/adopt?error=" + exception.getMessage();
        }
    }

    @PostMapping("/cancel/{id}")
    public String cancelAdoption(@PathVariable int id, HttpServletRequest req){
        try {
            adoptionService.cancelAdoption(id);
            return "redirect:/petsList/pets-info/adopted";
        } catch (RuntimeException exception) {
            return "redirect:/adopt?error=" + exception.getMessage();
        }
    }

}
