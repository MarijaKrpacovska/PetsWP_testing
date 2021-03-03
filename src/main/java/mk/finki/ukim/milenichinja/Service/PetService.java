package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PetService {
    List<Pet> listAll();

 //   Optional<Mileniche> save(String ime, String vid, String vozrast, String rasa, String pol, String opis, String embg_volonter, int id_centar, String url_slika);
    Optional<Pet> save(String ime, Type vid, String vozrast, String rasa, Gender pol, String opis, int id_centar, String url_slika, AppUser odgovorenVolonter);

    void adoptPet(AppUser user, int id_mileniche);

    List<Pet> nevdomeniMilenichinja();

    List<Pet> vdomeniMilenichinja();

    void delete(int id);

    Optional<Pet> findById(int id);

    Optional<Pet> edit(int id, String ime, Type vid, String vozrast, String rasa, Gender pol,
                       String opis, int id_centar, String url_slika);

    List<Pet> search(String age, String breed, Gender gender, Type type);

    List<Pet> searchAdopted(Integer id);
}
