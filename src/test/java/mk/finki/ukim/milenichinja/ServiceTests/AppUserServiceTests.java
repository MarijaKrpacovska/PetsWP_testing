package mk.finki.ukim.milenichinja.ServiceTests;

import mk.finki.ukim.milenichinja.Models.*;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Exceptions.*;
import mk.finki.ukim.milenichinja.Repository.Jpa.AdoptionRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.AppUserRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.CenterRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.Impl.AdoptionServiceImpl;
import mk.finki.ukim.milenichinja.Service.Impl.AppUserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTests {
    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AppUserServiceImpl service;

    private AppUser admin;
    private List<Integer> centers = new ArrayList<>();


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        AppUser user = new AppUser("username", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_USER,City.Skopje);
        admin = new AppUser("admin", "name", "surname","email@gmail.com","pass", ZonedDateTime.now(), Role.ROLE_ADMIN,City.Skopje);

        List<Center> centersList = new ArrayList<>();

        Center center = new Center("adr",City.Skopje,"url");
        centers.add(0);
        centersList.add(center);

        Mockito.when(this.appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        Mockito.when(this.appUserRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        Mockito.when(this.appUserRepository.findByUsername("admin")).thenReturn(java.util.Optional.of(admin));

//        Mockito.when(this.centerRepository.findById(0)).thenReturn(java.util.Optional.of(center));
        Mockito.when(this.centerRepository.findAllById(centers)).thenReturn(centersList);


        this.service = Mockito.spy(new AppUserServiceImpl(this.appUserRepository,this.centerRepository, this.passwordEncoder));
    }

    //confirmAdoption
    @Test
    public void registerUserTestP1_8() { //	T F F F
        Assert.assertThrows(
                InvalidUsernameOrPasswordException.class,
                () -> this.service.registerUser(null, "name","surname",City.Skopje,"email@gmail.com","pass","pass",  Role.ROLE_USER)
        );
    }
    @Test
    public void registerUserTestP1_12() { //	F T F F
        Assert.assertThrows(
                InvalidUsernameOrPasswordException.class,
                () -> this.service.registerUser("", "name","surname",City.Skopje,"email@gmail.com","pass","pass",  Role.ROLE_USER)
        );
    }
    @Test
    public void registerUserTestP1_14() { //	F F T F
        Assert.assertThrows(
                InvalidUsernameOrPasswordException.class,
                () -> this.service.registerUser("username", "name","surname",City.Skopje,"email@gmail.com",null,"pass",  Role.ROLE_USER)
        );
    }
    @Test
    public void registerUserTestP1_15() { //	F F T F
        Assert.assertThrows(
                InvalidUsernameOrPasswordException.class,
                () -> this.service.registerUser("username", "name","surname",City.Skopje,"email@gmail.com","","pass",  Role.ROLE_USER)
        );
    }
    @Test
    public void registerUserTestP1_16() { //	F F F F
        AppUser appUser1 = this.service.registerUser("username1", "name","surname",City.Skopje,"email@gmail.com","pass","pass",  Role.ROLE_USER);
        Assert.assertNotNull(appUser1);
    }
    @Test
    public void registerUserTestP2_1() {
        Assert.assertThrows(
                PasswordsDoNotMatchException.class,
                () -> this.service.registerUser("username", "name","surname",City.Skopje,"email@gmail.com","pass","pass1",  Role.ROLE_USER)
        );
    }
    @Test
    public void registerUserTestP3_1() {
        Assert.assertThrows(
                ClientAlreadyExistsException.class,
                () -> {
                    this.service.registerUser("username", "name","surname",
                            City.Skopje,"email@gmail.com","pass","pass",  Role.ROLE_USER);
                }
        );
    }

    //removeAdmin
    @Test
    public void removeAdmin1() {
        Assert.assertThrows(
                InvalidUserCredentialsException.class,
                () -> this.service.removeAdmin("")
        );
    }
    @Test
    public void removeAdmin2() {
        AppUser noLongerAdmin = this.service.removeAdmin("admin").get();
        Assert.assertEquals(Role.ROLE_USER,noLongerAdmin.getRole());
    }

    //addAdmin
    @Test
    public void addAdminP1_1() {
        Assert.assertThrows(
                InvalidUserCredentialsException.class,
                () -> this.service.addAdmin("",centers)
        );
    }
    @Test
    public void addAdminP2_1() {
        AppUser newAdmin = this.service.addAdmin("username",centers).get();
        Assert.assertEquals(Role.ROLE_ADMIN,newAdmin.getRole());
    }
    @Test
    public void addAdminP2_2() {
        AppUser newAdmin = this.service.addAdmin("username",new ArrayList<>()).get();
        Assert.assertEquals(Role.ROLE_ADMIN,newAdmin.getRole());
    }


}
