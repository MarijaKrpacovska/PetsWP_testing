package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Donation;
import mk.finki.ukim.milenichinja.Models.DonationCause;
import mk.finki.ukim.milenichinja.Models.Exceptions.CenterNotFoundException;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.DonationCauseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationCauseServiceImpl implements DonationCauseService {

    private final DonationCauseRepository donationCauseRepository;
    private final PetRepository petRepository;
    private final DonationRepository donationRepository;

    public DonationCauseServiceImpl(DonationCauseRepository donationCauseRepository, PetRepository petRepository, DonationRepository donationRepository) {
        this.donationCauseRepository = donationCauseRepository;
        this.petRepository = petRepository;
        this.donationRepository = donationRepository;
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
    public Optional<DonationCause> save(String decsription, String url, List<Integer> petsIds, double goal, String name, int importance) {
        Status status = Status.ACTIVE;
        List<Pet> pets = this.petRepository.findAllById(petsIds);
        DonationCause donationCause = new DonationCause(name,decsription,url,goal,pets,status,importance);
        return Optional.of(donationCauseRepository.save(donationCause));
    }

    @Override
    public Optional<DonationCause> edit(int id, String decsription, String url, List<Integer> petsIds, double goal, String name, int importance) {
        DonationCause cause = this.donationCauseRepository.findById(id).orElseThrow( () -> new CenterNotFoundException(id) );
        //City city = this.cityRepository.findById(idCity).orElseThrow( () -> new CityNotFoundException(idCity) );

        cause.setDescription(decsription);
        cause.setImageUrl(url);
        cause.setGoal(goal);
        cause.setName(name);
        cause.setImportance(importance);

        List<Pet> pets = this.petRepository.findAllById(petsIds);
        cause.setPets(pets);


        donationCauseRepository.save(cause);
        return Optional.of(cause);
    }

    @Override
    public Double currentState(DonationCause donationCause) {
        List<Donation> donations = donationRepository.findAllByDonationCause(donationCause);
        double sum = 0.0;
        for (Donation d : donations) {
            sum += d.getSum();
        }
        return sum;
    }

    @Override
    public Optional<DonationCause> delete(int id) {
        DonationCause cause = this.donationCauseRepository.findById(id).orElseThrow( () -> new CenterNotFoundException(id) );
        this.donationCauseRepository.delete(cause);
        return Optional.of(cause);
    }
}
