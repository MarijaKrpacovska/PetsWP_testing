package mk.finki.ukim.milenichinja.ServiceTests;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Exceptions.*;
import mk.finki.ukim.milenichinja.Repository.Jpa.*;
import mk.finki.ukim.milenichinja.Service.Impl.AdoptionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class AdoptionServiceTests {
    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private AppUserRepository appUserRepository;

    private AdoptionServiceImpl service;

    private Adoption adoption1;
    private Adoption adoption2;
    private Adoption adoption3;

    private AppUser user;
    private AppUser user2;
    private Pet pet;
    private Pet petAlteadyAdopted;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new AppUser("username","name","lastname","email@gmail.com","pass",ZonedDateTime.now(), Role.ROLE_USER, City.Skopje);

        Center center = new Center("a", City.Bitola,"url");
        pet = new Pet("p", Type.DOG,"b", Gender.FEMALE,"d",center, ZonedDateTime.now(),"url",null,false,ZonedDateTime.now());
        petAlteadyAdopted = new Pet("p", Type.DOG,"b", Gender.FEMALE,"d",center, ZonedDateTime.now(),"url",null,true,ZonedDateTime.now());

        adoption1 = new Adoption(ZonedDateTime.now(),ZonedDateTime.now(),Status.ACTIVE,user,pet);
        adoption2 = new Adoption(ZonedDateTime.now(),ZonedDateTime.now(),Status.CLOSED,user,pet);
        adoption3 = new Adoption(ZonedDateTime.now(),ZonedDateTime.now(),Status.CLOSED,user2,new Pet());

        Mockito.when(this.adoptionRepository.findById(0)).thenReturn(java.util.Optional.of(adoption1));
        Mockito.when(this.adoptionRepository.findById(1)).thenReturn(java.util.Optional.of(adoption2));

        Mockito.when(this.petRepository.findById(0)).thenReturn(java.util.Optional.of(pet));
        Mockito.when(this.petRepository.findById(1)).thenReturn(java.util.Optional.of(petAlteadyAdopted));

        Mockito.when(this.petRepository.save(pet)).thenReturn(pet);

        Mockito.when(this.adoptionRepository.save(Mockito.any(Adoption.class))).thenReturn(adoption1);

        this.service = Mockito.spy(new AdoptionServiceImpl(this.adoptionRepository, this.petRepository,this.appUserRepository));
    }

    //confirmAdoption
    @Test
    public void confirmAdoptionTest1() { //	1 2 4 6 7
        Adoption adoption = this.service.confirmAdoption(0).get();
        Mockito.verify(this.service).confirmAdoption(0);
        Assert.assertEquals(Status.COMPLETED,adoption.getStatus());
    }
    @Test
    public void confirmAdoptionTest2() { //	1 2 3
        Assert.assertThrows("Adoption with id: 3 is not found",
                AdoptionNotFoundException.class,
                () -> this.service.confirmAdoption(3).get()
        );
    }
    @Test
    public void confirmAdoptionTest3() { //	1 2 4 5
        Assert.assertThrows(
                InvalidActionException.class,
                () -> this.service.confirmAdoption(1).get()
        );
    }

    //cancelAdoption
    @Test
    public void cancelAdoptionTest1() { //	1 2 4 6 8 9
        Adoption adoption = this.service.cancelAdoption(0).get();
        Mockito.verify(this.service).cancelAdoption(0);
        Assert.assertEquals(Status.CLOSED,adoption.getStatus());
    }
    @Test
    public void cancelAdoptionTest2() { //	1 2 3
        Assert.assertThrows("Adoption with id: 3 is not found",
                AdoptionNotFoundException.class,
                () -> this.service.cancelAdoption(3).get()
        );
    }
    @Test
    public void cancelAdoptionTest3() { //	1 2 4 5
        Assert.assertThrows(
                InvalidActionException.class,
                () -> this.service.confirmAdoption(1).get()
        );
    }
    @Test
    public void cancelAdoptionTest4() { //	1 2 4 6 7
        Assert.assertThrows("Pet with id: 1 is not found",
                AdoptionNotFoundException.class,
                () -> this.service.cancelAdoption(2).get()
        );
    }

    //adopt
    @Test
    public void adoptTest1() { //	1 2 3 5 7 9 10
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = ZonedDateTime.now().plusDays(1).format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String todayTime = ZonedDateTime.now().format(formatterTime);
        String tomorrow = todayDate + "T" + todayTime;

        Adoption adoption = this.service.adopt(user,0,tomorrow).get();
        Mockito.verify(this.service).adopt(user,0,tomorrow);

        Assert.assertNotNull(adoption);
        Assert.assertTrue(adoption.getPet().isAdopted());
    }
    @Test
    public void adoptTest2() { //	1 2 3 4
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = ZonedDateTime.now().plusDays(1).format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String todayTime = ZonedDateTime.now().format(formatterTime);
        String tomorrow = todayDate + "T" + todayTime;

        Assert.assertThrows("Pet with id: 3 is not found",
                PetNotFoundException.class,
                () -> this.service.adopt(user,3,tomorrow).get()
        );
    }
    @Test
    public void adoptTest3() { //	1 2 3 5 6
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = ZonedDateTime.now().plusDays(1).format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String todayTime = ZonedDateTime.now().format(formatterTime);
        String tomorrow = todayDate + "T" + todayTime;

        Assert.assertThrows("Pet with id 1 is already adopted. Please choose a different pet.",
                PetAlreadyAdoptedException.class,
                () -> this.service.adopt(user,1,tomorrow).get()
        );
    }
    @Test
    public void adoptTest4() { //	1 2 3 5 7 8
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = ZonedDateTime.now().minusDays(2).format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String todayTime = ZonedDateTime.now().format(formatterTime);
        String twoDaysAgo = todayDate + "T" + todayTime;

        Assert.assertThrows(
                InvalidDateOrTimeException.class,
                () -> this.service.adopt(user,0,twoDaysAgo).get()
        );
    }

}
