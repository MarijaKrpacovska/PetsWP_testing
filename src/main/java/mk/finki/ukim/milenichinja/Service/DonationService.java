package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface DonationService {

    Optional<Donation> save(AppUser donator, double sum, PaymentMethod paymentMethod, Long cardNumber, Valute valute, DonationCause donationCause);

    Optional<DonationCause> updateDCCurrentSum(DonationCause donationCause, double sum);

    List<Donation> listAll();

}
