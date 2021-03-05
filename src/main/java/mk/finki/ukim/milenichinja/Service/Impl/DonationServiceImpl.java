package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Service.DonationService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public List<Donation> listAll() {
        return donationRepository.findAll();
    }

    @Override
    public Optional<Donation> save(AppUser donator, double sum, PaymentMethod paymentMethod, Long cardNumber, Valute valute, DonationCause donationCause) {
        ZonedDateTime donationTime = ZonedDateTime.now();
        Donation donation = new Donation(donator,sum,paymentMethod,cardNumber,donationTime,valute,donationCause);
        return Optional.of( this.donationRepository.save(donation) );
    }

}
