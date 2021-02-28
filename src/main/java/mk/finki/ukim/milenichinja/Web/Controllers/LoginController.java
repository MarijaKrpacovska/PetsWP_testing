package mk.finki.ukim.milenichinja.Web.Controllers;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

 //   private final KlientService klientService;
    private final AppUserService appUserService;

    public LoginController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @GetMapping
    public String getLoginPage(Model model) {
        return "posts/login";
    }

  /*  @PostMapping
    public String login(HttpServletRequest request, Model model) {
        Klient klient = null;
            klient = this.klientService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("klient", klient);
            return "redirect:/petsList";

    }*/
/*
    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        AppUser user = null;
        user = this.appUserService.login(request.getParameter("username"),
                request.getParameter("password"));
        request.getSession().setAttribute("user", user);
        return "redirect:/petsList";
    }*/


}
