package mk.finki.ukim.milenichinja.Selenium;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Selenium.Pages.AddOrEditPetPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.LoginPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.PetsPage;
import mk.finki.ukim.milenichinja.Service.AdoptionService;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import mk.finki.ukim.milenichinja.Service.CenterService;
import mk.finki.ukim.milenichinja.Service.PetService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

            p1 = petService.save("pet1", Type.DOG,"rasa1", Gender.FEMALE,"opis1",
                    c1.getId(),"url",adminUser, "2020-04-04").get();
            p1 = petService.save("pet2", Type.CAT,"rasa2", Gender.FEMALE,"opis2",
                    c1.getId(),"url",adminUser, "2020-04-04").get();
            p1 = petService.save("pet3", Type.DOG,"rasa3", Gender.MALE,"opis3",
                    c1.getId(),"url",adminUser, "2020-04-04").get();

            dataInitialized = true;
        }
    }


    @Test
    public void startingTest() {
        PetsPage petsPage = PetsPage.to(this.driver);
        petsPage.assertElemts(0, 0, 0, 0, 0);
    }

    @Test
    public void adminButtonsTest() {
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);
        PetsPage petsPage = LoginPage.login(driver,loginPage,"u2","pass");
        petsPage = AddOrEditPetPage.addPet(this.driver,"p1",Type.DOG, "2020-04-04","breed1",
                                                Gender.MALE,"descr",c1.getId(),"url");
        petsPage.assertElemts(1, 1, 1, 0, 1);
    }

}
