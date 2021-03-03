/*package mk.finki.ukim.milenichinja.Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public String getAllCitiesPage(Model model){
        List<City> cities = cityService.listAll();
        model.addAttribute("cityList", cities);
        return "mainPages/cities";
    }

    @GetMapping("/add-form")
    public String getAddCityPage(Model model){
        return "posts/addCity";
    }

    @PostMapping("/add")
    public String addNewCity(
            @RequestParam(required = false) Integer id,
            @RequestParam String name) {
        if(id != null){
              this.cityService.edit(id, name);
        }else{
            this.cityService.save(name);
        }
        return "redirect:/cities";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCity(@PathVariable int id){
        cityService.delete(id);
        return "redirect:/cities";
    }

    @GetMapping("/edit-form/{id}")
    public String editCityPage(@PathVariable int id, Model model) {
        if (this.cityService.findById(id).isPresent()) {
            City city = this.cityService.findById(id).get();
            model.addAttribute("city", city);
            return "posts/addCity";
        }
        return "redirect:/products?error=ProductNotFound";
    }
}*/
