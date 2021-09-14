package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.Donation;
import mk.finki.ukim.milenichinja.Models.DonationCause;
import mk.finki.ukim.milenichinja.Models.Exceptions.CenterNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.DonationCauseNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.DonationCreationException;
import mk.finki.ukim.milenichinja.Models.Exceptions.TransferSumInvalidException;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.DonationCauseService;
import mk.finki.ukim.milenichinja.Service.ValuteService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class DonationCauseServiceImpl implements DonationCauseService {

    private final DonationCauseRepository donationCauseRepository;
    private final PetRepository petRepository;
    private final DonationRepository donationRepository;
    private final ValuteService valuteService;

    public DonationCauseServiceImpl(DonationCauseRepository donationCauseRepository, PetRepository petRepository, DonationRepository donationRepository, ValuteService valuteService) {
        this.donationCauseRepository = donationCauseRepository;
        this.petRepository = petRepository;
        this.donationRepository = donationRepository;
        this.valuteService = valuteService;
    }
    @Override
    public Optional<DonationCause> transferAllMoney(int transferFromId, int transferToId) {
        DonationCause transferFrom = this.findById(transferFromId).orElseThrow( () -> new DonationCauseNotFoundException(transferFromId) );
        DonationCause transferTo = this.findById(transferToId).orElseThrow( () -> new DonationCauseNotFoundException(transferToId) );

        double transferFromCurrentSum = transferFrom.getCurrentSum();
        double transferToCurrentSum = transferTo.getCurrentSum();

        transferToCurrentSum += transferFromCurrentSum;
        transferFromCurrentSum = 0;

        transferFrom.setCurrentSum(transferFromCurrentSum);
        transferTo.setCurrentSum(transferToCurrentSum);

        this.donationCauseRepository.save(transferFrom);
        this.donationCauseRepository.save(transferTo);

        return Optional.of(transferTo);
    }

    @Override
    public Optional<DonationCause> transferSumMoney(int transferFromId, int transferToId, double sum) {

        if(sum < 0)
            throw new TransferSumInvalidException(sum);

        DonationCause transferFrom = this.findById(transferFromId).orElseThrow( () -> new DonationCauseNotFoundException(transferFromId) );
        DonationCause transferTo = this.findById(transferToId).orElseThrow( () -> new DonationCauseNotFoundException(transferToId) );

        double transferFromCurrentSum = transferFrom.getCurrentSum();
        double transferToCurrentSum = transferTo.getCurrentSum();

        /*
        TO DO: EXCEPTION NOT ENOUGH MONEY
         */

        transferToCurrentSum += sum;
        transferFromCurrentSum -= sum;

        transferFrom.setCurrentSum(transferFromCurrentSum);
        transferTo.setCurrentSum(transferToCurrentSum);

        this.donationCauseRepository.save(transferFrom);
        this.donationCauseRepository.save(transferTo);

        return Optional.of(transferTo);
    }

    @Override
    public Optional<DonationCause> cancelCause(int id, int idTransfer) {
        DonationCause cause = this.findById(id).orElseThrow( () -> new DonationCauseNotFoundException(id) );
        cause.setStatus(Status.CLOSED);
        this.transferAllMoney(id, idTransfer);
        this.donationCauseRepository.save(cause);
        return Optional.of(cause);
    }

    @Override
    public Optional<DonationCause> finishCause(int id) {
        DonationCause cause = this.findById(id).orElseThrow( () -> new DonationCauseNotFoundException(id) );
        cause.setStatus(Status.COMPLETED);
        this.donationCauseRepository.save(cause);
        return Optional.of(cause);
    }


    @Override
    public List<DonationCause> listAll() {
        return donationCauseRepository.findAll();
    }

    @Override
    public List<DonationCause> listAllActiveCauses() {
        return donationCauseRepository.findAllByStatus(Status.ACTIVE);
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
    public double currentState(DonationCause donationCause) {
        List<Donation> donations = donationRepository.findAllByDonationCause(donationCause);

        double sum = 0.0;
        for (Donation d : donations) {
            if(d.getValute().getShortName().equals("MKD")) {
                sum += d.getSum();
            }
            else {
                sum += valuteService.ConvertToMKD(d.getSum(), d.getValute());
            }
        }
        //DecimalFormat df = new DecimalFormat("#.##");
        return sum;
    }

    @Override
    public Optional<DonationCause> delete(int id) {
        DonationCause cause = this.donationCauseRepository.findById(id).orElseThrow( () -> new CenterNotFoundException(id) );
        this.donationCauseRepository.delete(cause);
        return Optional.of(cause);
    }


}
