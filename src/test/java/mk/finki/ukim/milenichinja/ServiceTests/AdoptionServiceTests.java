package mk.finki.ukim.milenichinja.ServiceTests;

import mk.finki.ukim.milenichinja.Repository.Jpa.*;
import mk.finki.ukim.milenichinja.Service.Impl.DonationCauseServiceImpl;
import mk.finki.ukim.milenichinja.Service.ValuteService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdoptionServiceTests {
    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private AppUserRepository appUserRepository;

    private DonationCauseServiceImpl service;
}
