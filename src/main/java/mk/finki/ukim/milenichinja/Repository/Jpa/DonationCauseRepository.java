package mk.finki.ukim.milenichinja.Repository.Jpa;

import mk.finki.ukim.milenichinja.Models.DonationCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationCauseRepository extends JpaRepository<DonationCause,Integer> {
}
