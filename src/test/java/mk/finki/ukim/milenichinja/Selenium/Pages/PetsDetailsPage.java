package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PetsDetailsPage extends BasePage {

    @FindBy(css = ".nameDiv")
    private WebElement name;

    @FindBy(css = ".descrDiv")
    private WebElement description;

    @FindBy(css = "#type")
    private WebElement type;

    @FindBy(css = "#gender")
    private WebElement gender;

    @FindBy(css = "#breed")
    private WebElement breed;

    @FindBy(css = ".adopt_button")
    private WebElement adoptButton;

    public PetsDetailsPage(WebDriver driver) {
        super(driver);
    }

    public AddOrEditPetPage clickOnAdoptPet(WebDriver driver, PetsDetailsPage petsDetailsPage) {
        petsDetailsPage.adoptButton.click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, AddOrEditPetPage.class);
    }

    public void petsInfoMatch(String petsName, String petsDescription, String type, String gender, String breed) {
        Assert.assertEquals("pet names does not match", petsName, getName().getText());
        Assert.assertEquals("descriptions do not match", petsDescription, getDescription().getText());
        Assert.assertEquals("types do not match", type, getType().getText());
        Assert.assertEquals("genders do not match", gender, getGender().getText());
        Assert.assertEquals("breeds is visible", breed, getBreed().getText());
    }


}
