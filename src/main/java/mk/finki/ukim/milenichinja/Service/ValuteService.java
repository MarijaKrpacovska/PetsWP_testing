package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Valute;

import java.util.List;
import java.util.Optional;

public interface ValuteService {

    List<Valute> listAll();

    Optional<Valute> findByShortName(String valute);

    double ConvertToMKD(double value, Valute valute);

}
