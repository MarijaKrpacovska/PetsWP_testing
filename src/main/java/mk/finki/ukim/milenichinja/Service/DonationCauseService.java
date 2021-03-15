package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.*;

import java.util.List;
import java.util.Optional;

public interface DonationCauseService {

    List<DonationCause> listAll();

    List<DonationCause> listAllActiveCauses();

    Optional<DonationCause> findById(int id);

    double currentState(DonationCause donationCause);

    Optional<DonationCause> save(String decsription, String url, List<Integer> pets, double goal, String name, int importance);

    Optional<DonationCause> edit(int id, String decsription, String url, List<Integer> pets, double goal, String name, int importance);

    Optional<DonationCause> delete(int id);

    Optional<DonationCause> transferAllMoney(int transferFrom, int transferTo);

    Optional<DonationCause> transferSumMoney(int transferFrom, int transferTo, double sum);

    Optional<DonationCause> cancelCause(int id, int idTransfer);

    Optional<DonationCause> finishCause(int id);



}
