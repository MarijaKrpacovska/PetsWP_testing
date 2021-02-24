package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import org.springframework.security.core.userdetails.UserDetailsService;

import mk.finki.ukim.milenichinja.Models.Role;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface AppUserService extends UserDetailsService {
    List<AppUser> listAll();
    //AppUser register(String username, String ime, String prezime, int id_grad, String email, String password, String repeatPass);
    //AppUser register(String username, String ime, String prezime, City city, String email, String password, String repeatPass);
    AppUser register(String username, String ime, String prezime, City city, String email, String password, String repeatPass, List<Integer> worksAt, Role role);
    AppUser registerUser(String username, String ime, String prezime, City city, String email, String password, String repeatPass, Role role);
    AppUser login(String username, String password);
    Optional<AppUser> getByUsername(String username);
}
