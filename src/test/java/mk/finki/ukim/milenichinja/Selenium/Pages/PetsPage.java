package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import mk.finki.ukim.milenichinja.Models.Pet;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PetsPage extends BasePage {

    @FindBy(css = ".pet")
    private List<WebElement> pets;

    @FindBy(css = ".media-container-row")
    private List<WebElement> petRows;


    @FindBy(css = "#filter")
    private List<WebElement> filterButton;

    @FindBy(css = ".delete_button")
    private List<WebElement> deleteButtons;

    @FindBy(xpath = "a[@href='/petsList/edit-form/1']")
    private List<WebElement> editButtons;

    @FindBy(css = ".add_pet_button")
    private List<WebElement> addPetButton;

    public PetsPage(WebDriver driver) {
        super(driver);
    }

    public static PetsPage to(WebDriver driver) {
        get(driver, "/products");
        return PageFactory.initElements(driver, PetsPage.class);
    }

//    public static PetsPage addPet(WebDriver driver, LoginPage loginPage, String username, String password) {
//        loginPage.username.sendKeys(username);
//        loginPage.password.sendKeys(password);
//        loginPage.signIn.click();
//        System.out.println(driver.getCurrentUrl());
//        return PageFactory.initElements(driver, PetsPage.class);
//    }

    public void assertElemts(int productsNumber, int deleteButtons, int editButtons, int cartButtons, int addButtons) {
        Assert.assertEquals("pets number does not match", productsNumber, this.getPets().size());
        Assert.assertEquals("rows does not match", productsNumber, this.getPetRows().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("search do not match", cartButtons, this.getFilterButton().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddPetButton().size());
    }


}
