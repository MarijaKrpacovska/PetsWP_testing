package mk.finki.ukim.milenichinja.Selenium;

import lombok.extern.java.Log;
import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Selenium.Pages.AddOrEditPetPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.LoginPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.PetsDetailsPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.PetsPage;
import mk.finki.ukim.milenichinja.Service.AdoptionService;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.CenterService;
import mk.finki.ukim.milenichinja.Service.PetService;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumPetsTesting {

    @Autowired
    AppUserService appUserService;

    @Autowired
    PetService petService;

    @Autowired
    CenterService centerService;

    @Autowired
    AdoptionService adoptionService;


    private WebDriver driver;

    private static Center c1;
    private static Pet p1;
    private static Pet p2;
    private static Pet p3;
    private static Pet p4;
    private static AppUser regularUser;
    private static AppUser adminUser;

    private static boolean dataInitialized = false;

    @BeforeEach
    private void setup() {
        this.driver = getDriver();
        initData();
    }

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\GitHub\\PetsWP\\src\\main\\resources\\driver\\chromedriver.exe");
        return new ChromeDriver();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }

    }

    private void initData() {
        if (!dataInitialized) {
            c1 = centerService.save("ul. bla bla br 2/4-7", City.Bitola, "url").get();

            regularUser = appUserService.registerUser("u1","user1","user1",
                    City.Bitola,"u1@gmail.com","pass","pass", Role.ROLE_USER);
            adminUser = appUserService.registerUser("u2","user2","user2",
                    City.Bitola,"u2@gmail.com","pass","pass", Role.ROLE_ADMIN);

            p1 = petService.save("p1",Type.DOG,"p1",Gender.FEMALE,"descr",c1.getId(),"url",adminUser,"2020-04-04").get();
            p2 = petService.save("p2",Type.CAT,"p1",Gender.FEMALE,"descr",c1.getId(),"url",adminUser,"2020-04-04").get();
            p3 = petService.save("p3",Type.DOG,"p1",Gender.MALE,"descr",c1.getId(),"url",adminUser,"2020-04-04").get();
            p4 = petService.save("p4",Type.CAT,"p1",Gender.MALE,"descr",c1.getId(),"url",adminUser,"2020-04-04").get();


            dataInitialized = true;
        }
    }

    @Test
    public void petsNumberTest() throws Exception {
        PetsPage petsPage = PetsPage.to(driver);
        Thread.sleep(2000);
        Assert.assertEquals("Number of pets wrong",4,petsPage.getPets().size());
    }

    @Test
    public void petsFilteredNumberTest() throws Exception {
        PetsPage petsPage = PetsPage.to(driver);
        petsPage = PetsPage.filterPetsByGender(driver,petsPage,Gender.FEMALE);
        Thread.sleep(2000);
        Assert.assertEquals("Number of pets wrong",2,petsPage.getPets().size());
    }

    @Test
    public void adminDeleteButtonsTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u2","pass");
        Thread.sleep(2000);
        Assert.assertEquals("Number of delete buttons wrong",4,petsPage.getDeleteButtons().size());
        LoginPage.logout(driver);
    }

    @Test
    public void adminEditButtonsTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u2","pass");
        Thread.sleep(2000);
        Assert.assertEquals("Number of edit buttons wrong",4,petsPage.getEditButtons().size());
        LoginPage.logout(driver);
    }

    @Test
    public void adminAddButtonTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u2","pass");
        Thread.sleep(2000);
        Assert.assertEquals("Number of add buttons wrong",1,petsPage.getAddPetButton().size());
        LoginPage.logout(driver);
    }

    @Test
    public void userDeleteButtonsTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u1","pass");
        Thread.sleep(2000);
        Assert.assertEquals("Number of delete buttons wrong",0,petsPage.getDeleteButtons().size());
        LoginPage.logout(driver);
    }

    @Test
    public void userEditButtonsTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u1","pass");
        Thread.sleep(2000);
        Assert.assertEquals("Number of edit buttons wrong",0,petsPage.getEditButtons().size());
        LoginPage.logout(driver);
    }

    @Test
    public void userAddButtonTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u1","pass");
        Thread.sleep(2000);
        Assert.assertEquals("Number of add buttons wrong",0,petsPage.getAddPetButton().size());
        LoginPage.logout(driver);
    }

    @Test
    public void petDetailsPageTest() throws Exception {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u1","pass");
        Thread.sleep(2000);
        PetsDetailsPage petsDetailsPage = petsPage.clickOnPet(driver);
        Thread.sleep(2000);
        petsDetailsPage.petsInfoMatch(p1.getName(),p1.getDescription(),p1.getType().toString(),p1.getGender().toString(),p1.getBreed());
    }

}
