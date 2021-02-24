package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.Status;

import java.util.List;
import java.util.Optional;

public interface DonationCauseService {

    List<DonationCause> listAll();

    Optional<DonationCause> findById(int id);

    Optional<DonationCause> save(String decsription, String url, List<Pet> pets, double goal, String name, int importance);

    Optional<DonationCause> edit(String decsription, String url, Status status, List<Pet> pets, double goal, String name, int importance);

}
