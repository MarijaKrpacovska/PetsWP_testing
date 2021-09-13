package mk.finki.ukim.milenichinja.Selenium.Pages;

import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditCenterPage extends BasePage{

    public AddOrEditCenterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#address")
    private WebElement address;

    @FindBy(css = ".city")
    private WebElement citySelection;

    @FindBy(css = "#url")
    private WebElement url;

    @FindBy(css = "#submit")
    private WebElement saveButton;


    public static CenterPage addCenter(WebDriver driver, AddOrEditCenterPage addOrEditCenterPage, String address,
                                       City city, String url) {
        addOrEditCenterPage.address.sendKeys(address);

        String cityStr = city.toString();
        driver.findElement(By.cssSelector("[value=\""+cityStr+"\"]")).click();

        addOrEditCenterPage.url.sendKeys(address);
        addOrEditCenterPage.saveButton.click();
        return PageFactory.initElements(driver, CenterPage.class);
    }

    public static CenterPage editCenter(WebDriver driver, int CenterId, AddOrEditCenterPage addOrEditCenterPage, String address,
                                        City city, String url) {
        get(driver, "petsList/edit-form/"+CenterId);
        AddOrEditCenterPage addOrEditPetPage = PageFactory.initElements(driver, AddOrEditCenterPage.class);

        addOrEditCenterPage.address.sendKeys(address);

        String cityStr = city.toString();
        driver.findElement(By.cssSelector("[value=\""+cityStr+"\"]")).click();

        addOrEditCenterPage.url.sendKeys(address);
        addOrEditCenterPage.saveButton.click();
        return PageFactory.initElements(driver, CenterPage.class);
    }

}
