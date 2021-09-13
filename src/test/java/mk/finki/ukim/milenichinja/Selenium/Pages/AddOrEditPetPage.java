package mk.finki.ukim.milenichinja.Selenium.Pages;

import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Date;

public class AddOrEditPetPage extends BasePage{

    public AddOrEditPetPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#ime")
    private WebElement name;

    @FindBy(css = "#DOG")
    private WebElement typeDog;

    @FindBy(css = "#CAT")
    private WebElement typeCat;

    @FindBy(css = "#DoB")
    private WebElement dateOfBirth;

    @FindBy(css = "#rasa")
    private WebElement breed;

    @FindBy(css = "#FEMALE")
    private WebElement femaleGender;

    @FindBy(css = "#MALE")
    private WebElement maleGender;

    @FindBy(css = "#opis")
    private WebElement description;

    @FindBy(name = "id_centar")
    private WebElement center;

    @FindBy(css = "#url_slika")
    private WebElement url;

    @FindBy(css = "#submit")
    private WebElement saveButton;


    public static PetsPage addPet(WebDriver driver, AddOrEditPetPage addOrEditPetPage, String name, Type type, String date, String breed,
                                      Gender gender, String description, Integer centerId, String url) {
       // get(driver, "/petsList/add-form");
        // AddOrEditPetPage addOrEditPetPage = PageFactory.initElements(driver, AddOrEditPetPage.class);

        addOrEditPetPage.name.sendKeys(name);

        if (type == Type.DOG)
            addOrEditPetPage.typeDog.click();
        else if (type == Type.CAT)
            addOrEditPetPage.typeCat.click();

        addOrEditPetPage.dateOfBirth.sendKeys(date);

        addOrEditPetPage.breed.sendKeys(breed);

        if (gender == Gender.FEMALE)
            addOrEditPetPage.femaleGender.click();
        else if(gender == Gender.MALE)
            addOrEditPetPage.maleGender.click();

        addOrEditPetPage.description.sendKeys(description);

    //    addOrEditPetPage.center.click();
    //    addOrEditPetPage.center.findElement(By.xpath("option[@value = '" + centerId + "']")).click();

        addOrEditPetPage.url.sendKeys(url);

        addOrEditPetPage.saveButton.click();
        return PageFactory.initElements(driver, PetsPage.class);
    }

    public static PetsPage editPet(WebDriver driver, Integer petId, String name, Type type, String date, String breed,
                                       Gender gender, String description, Integer centerId, String url) {
        get(driver, "petsList/edit-form/"+petId);
        AddOrEditPetPage addOrEditPetPage = PageFactory.initElements(driver, AddOrEditPetPage.class);

        addOrEditPetPage.name.sendKeys(name);

        if (type == Type.DOG)
            addOrEditPetPage.typeDog.click();
        else if (type == Type.CAT)
            addOrEditPetPage.typeCat.click();

        addOrEditPetPage.dateOfBirth.sendKeys(date);

        addOrEditPetPage.breed.sendKeys(breed);

        if (gender == Gender.FEMALE)
            addOrEditPetPage.femaleGender.click();
        else if(gender == Gender.MALE)
            addOrEditPetPage.maleGender.click();

        addOrEditPetPage.description.sendKeys(description);

        addOrEditPetPage.center.click();
        addOrEditPetPage.center.findElement(By.xpath("//option[. = '" + centerId + "']")).click();

        addOrEditPetPage.url.sendKeys(url);

        addOrEditPetPage.saveButton.click();
        return PageFactory.initElements(driver, PetsPage.class);
    }

}
