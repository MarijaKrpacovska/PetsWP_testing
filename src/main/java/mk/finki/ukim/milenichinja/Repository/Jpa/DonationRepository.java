package mk.finki.ukim.milenichinja.Repository.Jpa;


import mk.finki.ukim.milenichinja.Models.Donation;
import mk.finki.ukim.milenichinja.Models.DonationCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation,Integer> {

    List<Donation> findAllByDonationCause(DonationCause donationCause);

}
