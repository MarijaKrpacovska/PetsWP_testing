package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.DonationCause;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Service.DonationCauseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationCauseServiceImpl implements DonationCauseService {

    private final DonationCauseRepository donationCauseRepository;

    public DonationCauseServiceImpl(DonationCauseRepository donationCauseRepository) {
        this.donationCauseRepository = donationCauseRepository;
    }

    @Override
    public List<DonationCause> listAll() {
        return donationCauseRepository.findAll();
    }

    @Override
    public Optional<DonationCause> findById(int id) {
        return donationCauseRepository.findById(id);
    }

    @Override
    public Optional<DonationCause> save(String decsription, String url, List<Pet> pets, double goal, String name, int importance) {
        Status status = Status.ACTIVE;
        DonationCause donationCause = new DonationCause(name,decsription,url,goal,pets,status,importance);
        return Optional.of(donationCauseRepository.save(donationCause));
    }

    @Override
    public Optional<DonationCause> edit(String decsription, String url, Status status, List<Pet> pets, double goal, String name, int importance) {
        return Optional.empty();
    }
}
