package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Service.DonationService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final DonationCauseRepository donationCauseRepository;

    public DonationServiceImpl(DonationRepository donationRepository, DonationCauseRepository donationCauseRepository) {
        this.donationRepository = donationRepository;
        this.donationCauseRepository = donationCauseRepository;
    }

    @Override
    public List<Donation> listAll() {
        return donationRepository.findAll();
    }

    @Override
    public Optional<Donation> save(AppUser donator, double sum, PaymentMethod paymentMethod, Long cardNumber, Valute valute, DonationCause donationCause) {
        ZonedDateTime donationTime = ZonedDateTime.now();

        this.updateDCCurrentSum(donationCause, sum);

        Donation donation = new Donation(sum,cardNumber,donationTime,paymentMethod,donator,donationCause,donationCause,valute);
        return Optional.of( this.donationRepository.save(donation) );
    }

    @Override
    public Optional<DonationCause> updateDCCurrentSum(DonationCause donationCause, double sum) {
        double newSum = donationCause.getCurrentSum();
        newSum += sum;
        donationCause.setCurrentSum(newSum);
        donationCauseRepository.save(donationCause);
        return Optional.of(donationCause);
    }

}
