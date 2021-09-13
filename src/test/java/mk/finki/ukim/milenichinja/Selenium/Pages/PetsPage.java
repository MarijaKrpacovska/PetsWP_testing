package mk.finki.ukim.milenichinja.Selenium.Pages;

import lombok.Getter;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
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

    @FindBy(css = ".petAnchor")
    private List<WebElement> petLinks;

    @FindBy(css = "#filter")
    private List<WebElement> filterButton;

    @FindBy(css = "#genderSearch")
    private WebElement genderSearch;

    @FindBy(css = "#femaleFilter")
    private WebElement femaleFilter;

    @FindBy(css = "#maleFilter")
    private WebElement maleFilter;

    @FindBy(css = ".delete_button")
    private List<WebElement> deleteButtons;

    @FindBy(css = ".editPet")
    private List<WebElement> editButtons;

    @FindBy(css = ".add_pet_button")
    private List<WebElement> addPetButton;

    @FindBy(css = ".korisnik")
    private WebElement loginUsername;

    public PetsPage(WebDriver driver) {
        super(driver);
    }

    public static PetsPage to(WebDriver driver) {
        get(driver, "/petsList");
        return PageFactory.initElements(driver, PetsPage.class);
    }

    public AddOrEditPetPage clickOnAddPet(WebDriver driver, PetsPage petsPage) {
        petsPage.addPetButton.get(0).click();
        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, AddOrEditPetPage.class);
    }

    public static PetsPage filterPetsByGender(WebDriver driver, PetsPage petsPage, Gender gender) {
        petsPage.genderSearch.click();

        if(gender == Gender.FEMALE)
            petsPage.femaleFilter.click();
        else if(gender == Gender.MALE)
            petsPage.maleFilter.click();

        petsPage.filterButton.get(0).click();

        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, PetsPage.class);
    }

    public PetsDetailsPage clickOnPet(WebDriver driver) {
        petLinks.get(0).click();

        System.out.println(driver.getCurrentUrl());
        return PageFactory.initElements(driver, PetsDetailsPage.class);
    }


    public void assertElemts(int petsNumber, int deleteButtons, int editButtons, int filterButtons, int addButtons) {
        Assert.assertEquals("pets number does not match", petsNumber, this.getPets().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("search do not match", filterButtons, this.getFilterButton().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddPetButton().size());
    }


}
