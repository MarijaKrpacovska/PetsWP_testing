package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class LoginPage extends BasePage {

    @FindBy(css = "#username")
    private WebElement username;

    @FindBy(css = "#password")
    private WebElement password;

    @FindBy(css = "#submit")
    private WebElement signIn;

    @FindBy(css = ".text-danger")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static LoginPage openLoginPage(WebDriver driver) {
        get(driver, "/login");
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public static PetsPage login(WebDriver driver, LoginPage loginPage, String username, String password) {
        loginPage.username.sendKeys(username);
        loginPage.password.sendKeys(password);
        loginPage.signIn.click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, PetsPage.class);
    }

    public static LoginPage logout(WebDriver driver) {
        get(driver, "/logout");
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public static String successUsername(WebDriver driver, PetsPage petsPage) {
        return petsPage.getLoginUsername().getText();
    }

}
