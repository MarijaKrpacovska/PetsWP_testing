package mk.finki.ukim.milenichinja.Web.NotUsed.Controllers;


import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Exceptions.InvalidUsernameOrPasswordException;
import mk.finki.ukim.milenichinja.Models.Exceptions.PasswordsDoNotMatchException;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.CenterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class RegisterController {

    private final AppUserService appUserService;

    private final CenterService centerService;

    public RegisterController(AppUserService appUserService, CenterService centerService) {
        this.appUserService = appUserService;
        this.centerService = centerService;
    }
}
