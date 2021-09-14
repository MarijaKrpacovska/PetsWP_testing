package mk.finki.ukim.milenichinja.ServiceTests;

import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.DonationCause;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Status;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Exceptions.DonationCauseNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.TransferSumInvalidException;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationCauseRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.DonationRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.DonationCauseService;
import mk.finki.ukim.milenichinja.Service.Impl.DonationCauseServiceImpl;
import mk.finki.ukim.milenichinja.Service.ValuteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class DonationCauseServiceTests {

    @Mock
    private DonationCauseRepository donationCauseRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private ValuteService valuteService;

    private DonationCauseServiceImpl service;

    private DonationCause donationCause1;

    private DonationCause donationCause2;

    private List<Integer> petList;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        donationCause1 = new DonationCause("dc1","descr","url",600.00,400.00,null, Status.ACTIVE,1);
        donationCause2 = new DonationCause("dc1","descr","url",600.00,200.00,null, Status.ACTIVE,1);

//        Center center = new Center("a", City.Bitola,"url");
//        Pet pet = new Pet("p", Type.DOG,"b", Gender.FEMALE,"d",center, ZonedDateTime.now(),"url",null,false,ZonedDateTime.now());
//        petList.add(pet);

        Mockito.when(this.donationCauseRepository.findById(0)).thenReturn(java.util.Optional.of(donationCause1));
        Mockito.when(this.donationCauseRepository.findById(1)).thenReturn(java.util.Optional.of(donationCause2));

        Mockito.when(this.donationCauseRepository.save(Mockito.any(DonationCause.class))).thenReturn(donationCause1);

        this.service = Mockito.spy(new DonationCauseServiceImpl(this.donationCauseRepository, this.petRepository,this.donationRepository,this.valuteService));
    }

    //transferAllMoney
    @Test
    public void transferAllMoneyTest1() { //	Т Т Т (id1 >= 0 && id2 >= 0 && id1 == id2)
        DonationCause donationCause = this.service.transferAllMoney(0,0).get();
        Mockito.verify(this.service).transferAllMoney(0,0);
        Assert.assertEquals(400,donationCause.getCurrentSum(),0);
    }
    @Test
    public void transferAllMoneyTest2() { //	F Т F (id1 < 0 && id2 >= 0 && id1 != id2)
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.transferAllMoney(-1,0));
    }
    @Test
    public void transferAllMoneyTest3() { //	Т F F (id1 >= 0 && id2 < 0 && id1 != id2)
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.transferAllMoney(-1,0));
    }
    @Test
    public void transferAllMoneyTest4() { //	Т Т F (id1 >= 0 && id2 >= 0 && id1 != id2)
        DonationCause donationCause = this.service.transferAllMoney(0,1).get();
        Mockito.verify(this.service).transferAllMoney(0,1);
        Assert.assertEquals(600,donationCause.getCurrentSum(),0);
    }


    //transferSumMoney
    @Test
    public void transferSumMoneyTest1() { //	1)	Т Т A3 F  (id1 >= 0, id2 >= 0, id1 != id2, sum > 0)
        DonationCause donationCause = this.service.transferSumMoney(0,1,100).get();
        Mockito.verify(this.service).transferSumMoney(0,1,100);
        Assert.assertEquals(300,donationCause.getCurrentSum(),0);
    }
    @Test
    public void transferSumMoneyTest2() { //	2)	F T A3 F   (id1 < 0, id2 >= 0, id1 != id2, sum > 0)
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.transferSumMoney(-1,1,100)
        );
    }
    @Test
    public void transferSumMoneyTest3() { //	3)	T F A3 F    (id1 >= 0, id2 < 0, id1 != id2, sum > 0)
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.transferSumMoney(0,-1,100)
        );
    }
    @Test
    public void transferSumMoneyTest4() { //	4)	T T A1 F    (id1 >= 0, id2 >= 0, id1 != id2, sum < 0)
        Assert.assertThrows("Transfer sum -100 is not valid",
                TransferSumInvalidException.class,
                () -> this.service.transferSumMoney(0,1,-100)
        );
    }
    @Test
    public void transferSumMoneyTest5() { //	5)	T T A2 F   (id1 >= 0, id2 >= 0, id1 != id2, sum = 0)
        DonationCause donationCause = this.service.transferSumMoney(0,1,0).get();
        Mockito.verify(this.service).transferSumMoney(0,1,0);
        Assert.assertEquals(200,donationCause.getCurrentSum(),0);
    }
    @Test
    public void transferSumMoneyTest6() { //	6)	T T A3 T   (id1 >= 0, id2 >= 0, id1 == id2, sum > 0)
        DonationCause donationCause = this.service.transferSumMoney(0,0,100).get();
        Mockito.verify(this.service).transferSumMoney(0,0,100);
        Assert.assertEquals(400,donationCause.getCurrentSum(),0);
    }

    //cancelCause
    @Test
    public void cancelCauseTest1() { //		Т Т F (id1 >= 0 && id2 >= 0 && id1 != id2)
        DonationCause donationCause = this.service.cancelCause(0,1).get();
        Assert.assertEquals(Status.CLOSED,donationCause.getStatus());
    }
    @Test
    public void cancelCauseTest2() { //		F T F  (id1 < 0 && id2 >= 0 && id1 != id2)
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.cancelCause(-1,1)
        );
    }
    @Test
    public void cancelCauseTest3() { //		3)	T F F   (id1 >= 0 && id2 < 0 && id1 != id2)
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.cancelCause(0,-1)
        );
    }
    @Test
    public void cancelCauseTest4() { //		4)	T T T   (id1 >= 0 && id2 >= 0 && id1 == id2)
        DonationCause donationCause = this.service.cancelCause(0,0).get();
        Assert.assertEquals(Status.CLOSED,donationCause.getStatus());
    }

    //finishCause
    @Test
    public void finishCause1() { //		Т
        DonationCause donationCause = this.service.finishCause(0).get();
        Mockito.verify(this.service).finishCause(0);
        Assert.assertEquals(Status.COMPLETED,donationCause.getStatus());
    }
    @Test
    public void finishCause2() { //		F
        Assert.assertThrows("Donation Cause with id: -1 is not found",
                DonationCauseNotFoundException.class,
                () -> this.service.finishCause(-1)
        );
    }

    //findById
    @Test
    public void findById1() { //		Т
        DonationCause donationCause = this.service.findById(0).get();
        Mockito.verify(this.service).findById(0);
        Assert.assertEquals(donationCause1,donationCause);
    }
    @Test
    public void findById2() { //		F
        Assert.assertThrows(
                DonationCauseNotFoundException.class,
                () -> this.service.finishCause(-1)
        );
    }

    //save
    @Test
    public void save1() {
        DonationCause donationCause = this.service.save("descr","url",petList,600.00, "dc1", 1).get();
        Mockito.verify(this.service).save("descr","url",petList,600.00, "dc1", 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save2() {
        DonationCause donationCause = this.service.save(null,"url",petList,600.00, "dc1", 1).get();
        Mockito.verify(this.service).save(null,"url",petList,600.00, "dc1", 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save3() {
        DonationCause donationCause = this.service.save("descr",null,petList,600.00, "dc1", 1).get();
        Mockito.verify(this.service).save("descr",null,petList,600.00, "dc1", 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save4() {
        DonationCause donationCause = this.service.save("descr","url",null,600.00, "dc1", 1).get();
        Mockito.verify(this.service).save("descr","url",null,600.00, "dc1", 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save5() {
        DonationCause donationCause = this.service.save("descr","url",petList,-600.00, "dc1", 1).get();
        Mockito.verify(this.service).save("descr","url",petList,-600.00, "dc1", 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save6() {
        DonationCause donationCause = this.service.save("descr","url",petList,0, "dc1", 1).get();
        Mockito.verify(this.service).save("descr","url",petList,0, "dc1", 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save7() {
        DonationCause donationCause = this.service.save("descr","url",petList,600.00, null, 1).get();
        Mockito.verify(this.service).save("descr","url",petList,600.00, null, 1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save8() {
        DonationCause donationCause = this.service.save("descr","url",petList,600.00, "dc1", -1).get();
        Mockito.verify(this.service).save("descr","url",petList,600.00, "dc1", -1);
        Assert.assertNotNull(donationCause);
    }
    @Test
    public void save9() {
        DonationCause donationCause = this.service.save("descr","url",petList,600.00, "dc1", 0).get();
        Mockito.verify(this.service).save("descr","url",petList,600.00, "dc1", 0);
        Assert.assertNotNull(donationCause);
    }

}
