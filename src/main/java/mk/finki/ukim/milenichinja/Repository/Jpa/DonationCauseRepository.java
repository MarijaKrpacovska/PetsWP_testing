package mk.finki.ukim.milenichinja.Repository.Jpa;

import mk.finki.ukim.milenichinja.Models.DonationCause;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationCauseRepository extends JpaRepository<DonationCause,Integer> {
    List<DonationCause> findAllByStatus(Status s);
}
