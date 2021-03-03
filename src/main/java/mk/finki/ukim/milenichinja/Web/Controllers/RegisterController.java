package mk.finki.ukim.milenichinja.Web.Controllers;


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
@RequestMapping("/register")
public class RegisterController {
    private final AppUserService appUserService;
    private final CenterService centerService;

    public RegisterController(AppUserService appUserService, CenterService centerService) {
        this.appUserService = appUserService;
        this.centerService = centerService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Center> worksAtList = centerService.listAll();
        List<City> cityList = Arrays.asList(City.values());
        model.addAttribute("cityList",cityList);
        model.addAttribute("worksAtList",worksAtList);
        model.addAttribute("admin",false);
       // model.addAttribute("bodyContent","register");
        return "posts/register";
    }

    @PostMapping("/registerPost")
    public String register(@RequestParam String username,
                           @RequestParam String ime,
                           @RequestParam String prezime,
                           @RequestParam City city,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String repeatPass) {
                            //added
                          // @RequestParam List<Integer> worksAtList
        try{
            //this.appUserService.register(username,ime,prezime,city,email,password,repeatPass);
            Role role = Role.ROLE_USER;
            this.appUserService.registerUser(username,ime,prezime,city,email,password,repeatPass, role);
            return "redirect:/petsList";
        } catch (InvalidUsernameOrPasswordException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getManageRolesPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Center> worksAtList = centerService.listAll();
        List<AppUser> users = appUserService.getAllUsers();
        List<AppUser> admins = appUserService.getAllAdmins();

        model.addAttribute("worksAtList",worksAtList);
        model.addAttribute("allUsers",users);
        model.addAttribute("allAdmins",admins);
        return "posts/registerA.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registerAsAdminPost")
    public String manageRoles(@RequestParam(required = false) String allUsers,
                                  @RequestParam(required = false) String allAdmins,
                           @RequestParam(required = false) List<Integer> worksAtList) {
        try{
            if(!allUsers.equals(""))
                this.appUserService.addAdmin(allUsers, worksAtList);

            if(!allAdmins.equals(""))
                this.appUserService.removeAdmin(allAdmins);

            return "redirect:/register/registerAsAdminPost";
        } catch (InvalidUsernameOrPasswordException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

}
