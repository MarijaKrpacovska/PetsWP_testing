package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class CenterPage extends BasePage {

    @FindBy(css = ".edenCentar")
    private List<WebElement> centers;

    @FindBy(css = ".delete_button")
    private List<WebElement> deleteButtons;

    @FindBy(css = ".editCenter")
    private List<WebElement> editButtons;

    @FindBy(css = ".add_center_button")
    private List<WebElement> addCenterButton;

    @FindBy(css = ".korisnik")
    private WebElement loginUsername;

    @FindBy(css = ".errorMessageText")
    private WebElement errorDelete;

    public CenterPage(WebDriver driver) {
        super(driver);
    }

    public static CenterPage to(WebDriver driver) {
        get(driver, "/centers");
        return PageFactory.initElements(driver, CenterPage.class);
    }

    public static AddOrEditCenterPage clickOnAddCenter(WebDriver driver, CenterPage centerPage) {
        centerPage.addCenterButton.get(0).click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, AddOrEditCenterPage.class);
    }

    public CenterPage clickOnDeleteCenter(WebDriver driver, CenterPage centerPage) {
        centerPage.deleteButtons.get(0).click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, CenterPage.class);
    }

    public AddOrEditCenterPage clickOnEditCenter(WebDriver driver, CenterPage centerPage) {
        centerPage.editButtons.get(0).click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, AddOrEditCenterPage.class);
    }

    public String getDeleteErrorMessage() {
        return getErrorDelete().getText();
    }

    public void assertElemts(int centerNumber, int deleteButtons, int editButtons, int addButtons) {
        Assert.assertEquals("centers number does not match", centerNumber, this.getCenters().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddCenterButton().size());
    }
}
