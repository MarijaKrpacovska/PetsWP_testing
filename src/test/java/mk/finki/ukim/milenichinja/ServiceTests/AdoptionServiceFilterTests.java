package mk.finki.ukim.milenichinja.ServiceTests;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Exceptions.*;
import mk.finki.ukim.milenichinja.Repository.Jpa.AdoptionRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.AppUserRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.Impl.AdoptionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AdoptionServiceFilterTests {
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

    List<Adoption> allAdoptions = new ArrayList<>();
    List<Adoption> userFilteredAdoptions = new ArrayList<>();
    List<Adoption> petFilteredAdoptions = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new AppUser("username","name","lastname","email@gmail.com","pass",ZonedDateTime.now(), Role.ROLE_USER, City.Skopje);
        user2 = new AppUser("username1","name","lastname","email@gmail.com","pass",ZonedDateTime.now(), Role.ROLE_USER, City.Skopje);

        Center center = new Center("a", City.Bitola,"url");
        pet = new Pet("p", Type.DOG,"b", Gender.FEMALE,"d",center, ZonedDateTime.now(),"url",null,false,ZonedDateTime.now());
        petAlteadyAdopted = new Pet("p", Type.DOG,"b", Gender.FEMALE,"d",center, ZonedDateTime.now(),"url",null,true,ZonedDateTime.now());

        adoption1 = new Adoption(ZonedDateTime.now(),ZonedDateTime.now(),Status.ACTIVE,user,pet);
        adoption2 = new Adoption(ZonedDateTime.now(),ZonedDateTime.now(),Status.CLOSED,user,pet);
        adoption3 = new Adoption(ZonedDateTime.now(),ZonedDateTime.now(),Status.CLOSED,user2,new Pet());

        allAdoptions.add(adoption1);
        allAdoptions.add(adoption2);
        allAdoptions.add(adoption3);

        petFilteredAdoptions.add(adoption2);
        petFilteredAdoptions.add(adoption1);

        userFilteredAdoptions.add(adoption2);
        userFilteredAdoptions.add(adoption1);

        Mockito.when(this.adoptionRepository.findById(0)).thenReturn(java.util.Optional.of(adoption1));
        Mockito.when(this.adoptionRepository.findById(1)).thenReturn(java.util.Optional.of(adoption2));

        Mockito.when(this.petRepository.findById(0)).thenReturn(java.util.Optional.of(pet));
        Mockito.when(this.petRepository.findById(1)).thenReturn(java.util.Optional.of(petAlteadyAdopted));

        Mockito.when(this.appUserRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        Mockito.when(this.appUserRepository.findByUsername("username1")).thenReturn(java.util.Optional.of(user2));

        Mockito.when(this.adoptionRepository.findAll()).thenReturn(allAdoptions);
        Mockito.when(this.adoptionRepository.findAllByPet(pet)).thenReturn(petFilteredAdoptions);
        Mockito.when(this.adoptionRepository.findAllByUser(user)).thenReturn(userFilteredAdoptions);
        Mockito.when(this.adoptionRepository.findAllByUserAndPet(user,pet)).thenReturn(userFilteredAdoptions);

        Mockito.when(this.adoptionRepository.save(Mockito.any(Adoption.class))).thenReturn(adoption1);

        this.service = Mockito.spy(new AdoptionServiceImpl(this.adoptionRepository, this.petRepository,this.appUserRepository));
    }


    //search

    //good cases
    @Test
    public void searchTest1() { //	[1,2,8,13,19]
        List<Adoption> adoptionList = this.service.search("",null);
        Assert.assertEquals(allAdoptions,adoptionList);
    }
    @Test
    public void searchTest2() { //	[1,2,8,9,10,12]
        List<Adoption> adoptionList = this.service.search("",1);
        Assert.assertEquals(petFilteredAdoptions,adoptionList);
    }
    @Test
    public void searchTest3() { //	[1,2,8,13,14,16,18]
        List<Adoption> adoptionList = this.service.search("username",null);
        Assert.assertEquals(userFilteredAdoptions,adoptionList);
    }
    @Test
    public void searchTest4() { //	[1,2,3,4,5,6,7]
        List<Adoption> adoptionList = this.service.search("username",0);
        Assert.assertEquals(userFilteredAdoptions,adoptionList);
    }

    //returns empty lists because id or username doesn't exist
    @Test
    public void searchTest5() { //	[1,2,3,15]
        List<Adoption> adoptionList = this.service.search("username",3);
        Assert.assertEquals(new ArrayList<>(),adoptionList);
    }
    @Test
    public void searchTest6() { //	[1,2,8,9,15]
        List<Adoption> adoptionList = this.service.search("",3);
        Assert.assertEquals(new ArrayList<>(),adoptionList);
    }
    @Test
    public void searchTest7() { //	[1,2,3,4,15]
        List<Adoption> adoptionList = this.service.search("username4",0);
        Assert.assertEquals(new ArrayList<>(),adoptionList);
    }
    @Test
    public void searchTest8() { //	[1,2,8,13,14,15]
        List<Adoption> adoptionList = this.service.search("username4",null);
        Assert.assertEquals(new ArrayList<>(),adoptionList);
    }

}
