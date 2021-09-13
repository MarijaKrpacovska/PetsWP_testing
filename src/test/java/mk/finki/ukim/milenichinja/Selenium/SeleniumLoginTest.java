package mk.finki.ukim.milenichinja.Selenium;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Role;
import mk.finki.ukim.milenichinja.Selenium.Pages.LoginPage;
import mk.finki.ukim.milenichinja.Selenium.Pages.PetsPage;
import mk.finki.ukim.milenichinja.Service.AppUserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumLoginTest {

    @Autowired
    AppUserService appUserService;

    private static AppUser regularUser;

    private static boolean dataInitialized = false;
    private WebDriver driver;

    @BeforeEach
    private void setup() {
        this.driver = getDriver();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {

            regularUser = appUserService.registerUser("u1","user1","user1",
                    City.Bitola,"u1@gmail.com","pass","pass", Role.ROLE_USER);

            dataInitialized = true;
        }
    }

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\GitHub\\PetsWP\\src\\main\\resources\\driver\\chromedriver.exe");
        return new ChromeDriver();
    }

    @Test
    public void successfulLoginTest() {
        LoginPage loginPage = LoginPage.openLoginPage(driver);
        PetsPage petsPage=LoginPage.login(driver,loginPage,regularUser.getUsername(),"pass");
        Assert.assertEquals("usernames don't match",regularUser.getUsername(),LoginPage.successUsername(driver,petsPage));
    }

    @Test
    public void wrongUsernameLoginTest() {
        LoginPage loginPage = LoginPage.openLoginPage(driver);
        LoginPage.login(driver,loginPage,"wrongUsername","pass");
        Assert.assertEquals("BadCredentials",loginPage.getErrorMessage().getText());
    }

    @Test
    public void wrongPasswordLoginTest() {
        LoginPage loginPage = LoginPage.openLoginPage(driver);
        LoginPage.login(driver,loginPage,regularUser.getUsername(),"wrongPass");
        Assert.assertEquals("BadCredentials",loginPage.getErrorMessage().getText());
    }

}
