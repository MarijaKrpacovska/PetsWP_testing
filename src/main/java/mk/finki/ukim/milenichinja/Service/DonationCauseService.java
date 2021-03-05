package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.*;

import java.util.List;
import java.util.Optional;

public interface DonationCauseService {

    List<DonationCause> listAll();

    Optional<DonationCause> findById(int id);

    String currentState(DonationCause donationCause);

    Optional<DonationCause> save(String decsription, String url, List<Integer> pets, double goal, String name, int importance);

    Optional<DonationCause> edit(int id, String decsription, String url, List<Integer> pets, double goal, String name, int importance);

    Optional<DonationCause> delete(int id);

}
