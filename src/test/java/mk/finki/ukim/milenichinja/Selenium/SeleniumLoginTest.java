package mk.finki.ukim.milenichinja.Selenium;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumLoginTest {
    private HtmlUnitDriver driver;

    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
    }
}
