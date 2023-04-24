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

public class Login_Steps {

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

    @Given("the user visits the login website")
    public void the_user_visits_the_login_website() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("^the user inserts the username ([^\"]*)$")
    public void the_user_inserts_the_username_standard_user(String username) {
        WebElement usernameField = driver.findElement(By.xpath("//input[@id='user-name']"));
        usernameField.sendKeys(username);
    }

    @When("^the user inserts the password ([^\"]*)$")
    public void the_user_inserts_the_password_secret_sauce(String password) {
        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys(password);
    }

    @And("the user clicks on login button")
    public void the_user_clicks_on_login_button() {
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();
    }

    @Then("The login gets done and inventory page is shown")
    public void the_login_gets_done_and_inventory_page_is_shown() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Home Page url is different");
        System.out.println();
        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='title']")).getText(), "Products", "Product label is not visible in Home Page");
        Assert.assertEquals(driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).isDisplayed(), true, "Shopping cart link is not displayed");
    }

    @Then("Missing username message is shown")
    public void missing_username_message_is_shown() {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='error-message-container error']")).isDisplayed(), true, "Error message container is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Epic sadface: Username is required']")).getText(), "Epic sadface: Username is required", "Error message is different to expected");
    }

    @Then("Missing password message is shown")
    public void missing_password_message_is_shown() {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='error-message-container error']")).isDisplayed(), true, "Error message container is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Epic sadface: Password is required']")).getText(), "Epic sadface: Password is required", "Error message is different to expected");
    }

    @Then("user locked message is shown")
    public void user_locked_message_is_shown() {

        Assert.assertEquals(driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]")).isDisplayed(), true, "Error message container is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]")).getText(), "Epic sadface: Sorry, this user has been locked out.", "Error message is different to expected");

    }


}
