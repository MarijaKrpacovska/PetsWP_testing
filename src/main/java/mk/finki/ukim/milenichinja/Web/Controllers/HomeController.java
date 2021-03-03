package mk.finki.ukim.milenichinja.Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        return "fragments/AccessDenied.html";
    }

}
