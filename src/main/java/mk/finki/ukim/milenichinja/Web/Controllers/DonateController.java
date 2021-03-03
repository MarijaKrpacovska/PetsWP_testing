package mk.finki.ukim.milenichinja.Web.Controllers;


import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUsernameOrPasswordException;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.DonationCauseService;
import mk.finki.ukim.milenichinja.Service.DonationService;
import mk.finki.ukim.milenichinja.Service.PetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
public class DonateController {

    private final DonationService donationService;

    private final DonationCauseService donationCauseService;

    private final AppUserService appUserService;

    private final PetService petService;

    public DonateController(DonationService donationService, DonationCauseService donationCauseService, AppUserService appUserService, PetService petService) {
        this.donationService = donationService;
        this.donationCauseService = donationCauseService;
        this.appUserService = appUserService;
        this.petService = petService;
    }

    //DONATE
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/donate")
    public String getDonatePage(@RequestParam(required = false) String error, Model model) {

        List<DonationCause> donationCauses = this.donationCauseService.listAll();

        model.addAttribute("causesList",donationCauses);

        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "posts/donate";
    }

    @PostMapping("/donate")
    public String donate(@RequestParam double sum,
                           @RequestParam Long cardNumber,
                           @RequestParam String valute,
                           @RequestParam Integer donationCauseId,
                           HttpServletRequest req) {
        try{
            PaymentMethod paymentMethod = PaymentMethod.CREDITCARD;

            String username = req.getRemoteUser();
            AppUser user = appUserService.getByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
            DonationCause donationCause=this.donationCauseService.findById(donationCauseId).orElseThrow();
            this.donationService.save(user, sum, paymentMethod, cardNumber, valute, donationCause);
            return "redirect:/petsList";
        } catch (InvalidUsernameOrPasswordException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
    //DONATE

    //VIEW ALL DONATIONS
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allDonations")
    public String getAllDonationsPage(Model model) {
        List<Donation> donations = this.donationService.listAll();
        model.addAttribute("donationsList",donations);
        return "mainPages/allDonations";
    }
    //VIEW ALL DONATIONS

}
