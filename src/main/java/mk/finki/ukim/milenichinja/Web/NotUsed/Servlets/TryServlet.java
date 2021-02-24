/*package mk.finki.ukim.milenichinja.Web.NotUsed.Servlets;

import mk.finki.ukim.milenichinja.Service.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="tryServlet", urlPatterns = "/try")
public class TryServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CenterService centerService;
    private final PetService petService;
    private final AppUserService appUserService;

    public TryServlet(SpringTemplateEngine springTemplateEngine, CenterService centerService, PetService petService,
                      AppUserService appUserService) {
        this.springTemplateEngine = springTemplateEngine;
        this.centerService = centerService;
        this.petService = petService;
        this.appUserService = appUserService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req,resp,req.getServletContext());
        //for (Grad grad : gradService.listAll()) {
          // int id = grad.getId_grad();
           // String ime = grad.getIme();
        //}
        context.setVariable("gradList", appUserService.listAll());
        this.springTemplateEngine.process("try.html", context, resp.getWriter());
    }
}*/
