package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Logout_Steps {

    private WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("the login done correctly {string} and {string}")
    public void the_login_done_correctly_and(String string, String string2) {

        driver.get("https://www.saucedemo.com/");

        WebElement usernameField = driver.findElement(By.xpath("//input[@id='user-name']"));
        usernameField.sendKeys(string);

        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys(string2);

        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

    }

    @When("the user clicks on Logout")
    public void the_user_clicks_on_logout() throws InterruptedException {
        WebElement burgerMenu = driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
        burgerMenu.click();
        //sleep(1000);
        WebElement logout = driver.findElement(By.xpath("//a[@id='logout_sidebar_link']"));
        logout.click();
    }

    @Then("the login page is visible correctly")
    public void the_login_page_is_visible_correctly() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Login url is not as expected");
    }

}
