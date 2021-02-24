package mk.finki.ukim.milenichinja.Web.Controllers;


import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUsernameOrPasswordException;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.DonationCauseService;
import mk.finki.ukim.milenichinja.Service.DonationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/donate")
public class DonateController {
    private final DonationService donationService;
    private final DonationCauseService donationCauseService;
    private final AppUserService appUserService;

    public DonateController(DonationService donationService, DonationCauseService donationCauseService, AppUserService appUserService) {
        this.donationService = donationService;
        this.donationCauseService = donationCauseService;
        this.appUserService = appUserService;
    }

    @GetMapping("/causes")
    public String getAllCausesPage(Model model) {
        List<DonationCause> donationCauses = this.donationCauseService.listAll();
        model.addAttribute("causesList",donationCauses);
        return "mainPages/donationCauses";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public String getDonatePage(@RequestParam(required = false) String error, Model model) {
        List<DonationCause> donationCauses = this.donationCauseService.listAll();
        model.addAttribute("causesList",donationCauses);
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
       // model.addAttribute("bodyContent","register");
        return "posts/donate";
    }

    @PostMapping("/donatePost")
    public String register(@RequestParam double sum,
                           @RequestParam Long cardNumber,
                           @RequestParam String valute,
                           @RequestParam Integer donationCauseId,
                           HttpServletRequest req) {
        try{
            PaymentMethod paymentMethod = PaymentMethod.CREDITCARD;
            //AppUser user = (AppUser) req.getSession().getAttribute("user");
            String username = req.getRemoteUser();
            AppUser user = appUserService.getByUsername(username).orElseThrow(InvalidUserCredentialsException::new);
            //DonationCause donationCause = donationCauseService.findById(1).orElseThrow(DonationCreationException::new);
            DonationCause donationCause=this.donationCauseService.findById(donationCauseId).orElseThrow();
            this.donationService.save(user, sum, paymentMethod, cardNumber, valute, donationCause);
            return "redirect:/petsList";
        } catch (InvalidUsernameOrPasswordException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allDonations")
    public String getAllDonationsPage(Model model) {
        List<Donation> donations = this.donationService.listAll();
        model.addAttribute("donationsList",donations);
        return "mainPages/allDonations";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/addDonationCause")
    public String getAddCausePage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        // model.addAttribute("bodyContent","register");
        return "posts/addDonationCause.html";
    }

    @PostMapping("/causePost")
    public String addCausePost(@RequestParam String name,
                               @RequestParam String url,
                                @RequestParam String description,
                                @RequestParam double goal,
                                @RequestParam int importance,
                                HttpServletRequest req) {
            donationCauseService.save(description,url,null,goal,name,importance);
            return "redirect:/donate/causes";
    }

}
