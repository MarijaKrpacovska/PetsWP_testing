package mk.finki.ukim.milenichinja.Selenium;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Selenium.Pages.AddOrEditCenterPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.CenterPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.LoginPage;
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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumCentersTesting {

    @Autowired
    AppUserService appUserService;

    @Autowired
    PetService petService;

    @Autowired
    CenterService centerService;

    @Autowired
    AdoptionService adoptionService;


    private WebDriver driver;

    private static Pet p1;
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

            regularUser = appUserService.registerUser("u1","user1","user1",
                    City.Bitola,"u1@gmail.com","pass","pass", Role.ROLE_USER);
            adminUser = appUserService.registerUser("u2","user2","user2",
                    City.Bitola,"u2@gmail.com","pass","pass", Role.ROLE_ADMIN);

            //p1 = petService.save("p1",Type.DOG,"p1",Gender.FEMALE,"descr",c1.getId(),"url",adminUser,"2020-04-04").get();

            dataInitialized = true;
        }
    }

    @Test
    public void testCentersScenario() throws Exception {
        CenterPage centerPage = CenterPage.to(this.driver);
        centerPage.assertElemts(0, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLoginPage(this.driver);

        LoginPage.login(this.driver, loginPage, adminUser.getUsername(), "pass");
        centerPage = CenterPage.to(this.driver);
        centerPage.assertElemts(0, 0, 0,  1);

        AddOrEditCenterPage addOrEditCenterPage = CenterPage.clickOnAddCenter(driver,centerPage);
        centerPage = AddOrEditCenterPage.addCenter(driver,addOrEditCenterPage,"addr1",City.Bitola,"url1");
        centerPage.assertElemts(1, 1, 1,  1);

        addOrEditCenterPage = CenterPage.clickOnAddCenter(driver,centerPage);
        centerPage = AddOrEditCenterPage.addCenter(driver,addOrEditCenterPage,"addr2",City.Skopje,"url2");
        p1 = petService.save("p1",Type.DOG,"breed",Gender.FEMALE,"desc",2,"url",adminUser,"2020-04-04").get();
        centerPage.assertElemts(2, 2, 2,  1);

        centerPage=centerPage.clickOnDeleteCenter(driver,centerPage);
        centerPage.assertElemts(1, 1, 1,  1);

        centerPage=centerPage.clickOnDeleteCenter(driver,centerPage);
        centerPage.assertElemts(1, 1, 1,  1);
        Assert.assertEquals("error messages don't match","Center with id 2 cannot be deleted because there are pets in it",centerPage.getDeleteErrorMessage());
    }


}
