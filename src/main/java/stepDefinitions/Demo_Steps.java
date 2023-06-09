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

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class Demo_Steps {

    private WebDriver driver;
    private WebElement addToCartButtons;
    private String itemsPrice;
    private WebElement removeButtons;

    private String titleItems;

    private String descriptionItems;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
        Assert.assertTrue(driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).isDisplayed(), "Shopping cart link is not displayed");
    }

    @Then("Missing username message is shown")
    public void missing_username_message_is_shown() {
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='error-message-container error']")).isDisplayed(), "Error message container is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Epic sadface: Username is required']")).getText(), "Epic sadface: Username is required", "Error message is different to expected");
    }

    @Then("Missing password message is shown")
    public void missing_password_message_is_shown() {
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='error-message-container error']")).isDisplayed(), "Error message container is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Epic sadface: Password is required']")).getText(), "Epic sadface: Password is required", "Error message is different to expected");
    }

    @Then("user locked message is shown")
    public void user_locked_message_is_shown() {

        Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]")).isDisplayed(), "Error message container is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Sorry, this user has been locked out')]")).getText(), "Epic sadface: Sorry, this user has been locked out.", "Error message is different to expected");

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
    public void the_user_clicks_on_logout() {
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

    @When("the user adds one product to cart through inventory page")
    public void the_user_adds_one_product_to_cart_through_inventory_page() throws InterruptedException {

        addToCartButtons = driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']"));
        itemsPrice = driver.findElement(By.xpath("//div[normalize-space()='$29.99']")).getText();
        titleItems = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Backpack']")).getText();
        descriptionItems = driver.findElement(By.xpath("//div[normalize-space()='carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.']")).getText();

        addToCartButtons.click();//first element of a list

        sleep(3000);

        removeButtons = driver.findElement(By.xpath("//button[@id='remove-sauce-labs-backpack']"));
        WebElement shoppingCartBadge = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
        //Assertion
        Assert.assertTrue(removeButtons.isDisplayed(), "Remove button is not displayed");
        Assert.assertTrue(shoppingCartBadge.isDisplayed(), "Shopping cart badge is not displayed");
        Assert.assertEquals(shoppingCartBadge.getText(), "1", "Shopping cart badge has wrong value");

    }

    @And("the user clicks on shopping cart")
    public void the_user_clicks_on_shopping_cart() throws InterruptedException {
        WebElement shoppingCart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        shoppingCart.click();
        sleep(3000);
    }

    @And("^the user inserts the ZipCode ([^\"]*)$")
    public void the_user_inserts_the_zip_code(String zipCode) {
        WebElement ZipCode = driver.findElement(By.xpath("//input[@id='postal-code']"));
        ZipCode.sendKeys(zipCode);
    }

    @And("^the user inserts the First Name ([^\"]*)$")
    public void the_user_inserts_the_first_name(String firstName) {
        WebElement FirstName = driver.findElement(By.xpath("//input[@id='first-name']"));
        FirstName.sendKeys(firstName);
    }

    @And("^the user inserts the Last Name ([^\"]*)$")
    public void the_user_inserts_the_last_name(String lastName) {
        WebElement LastName = driver.findElement(By.xpath("//input[@id='last-name']"));
        LastName.sendKeys(lastName);
    }

    @When("the user clicks on Continue button in Checkout: Your Information page")
    public void the_user_clicks_on_continue_button_in_checkout_your_information_page() {
        WebElement Continue = driver.findElement(By.xpath("//input[@id='continue']"));
        Continue.click();
    }

    @When("the user clicks on Finish button in Checkout: Overview page")
    public void the_user_clicks_on_finish_button_in_checkout_overview_page() {

        //Assertion
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_item_name']")).getText(), titleItems, "Titles are not matching at the end of process");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_item_desc']")).getText(), descriptionItems, "Description are not matching");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_item_price']")).getText(), itemsPrice, "Prices are not matching");

        //Getting the element for prices assertion: Total item and tax
        WebElement itemTotalElement = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"));
        String itemTotal = itemTotalElement.getText().substring(itemTotalElement.getText().indexOf('$'));

        Assert.assertEquals(itemTotal, itemsPrice, "the prices are not matching");
        itemTotal = itemTotal.substring(itemTotal.indexOf('$')+1);

        WebElement taxElement = driver.findElement(By.xpath("//div[@class='summary_tax_label']"));
        String tax = taxElement.getText().substring(taxElement.getText().indexOf('$'));

        tax = tax.substring(tax.indexOf('$')+1);

        WebElement totalElement = driver.findElement(By.xpath("//div[@class='summary_info_label summary_total_label']"));
        String total = totalElement.getText().substring(totalElement.getText().indexOf('$')+1);

        //From string to float to perform addiction
        float add = Float.parseFloat(tax)+Float.parseFloat(itemTotal);

        Assert.assertEquals(total,String.valueOf(add));


        WebElement finish = driver.findElement(By.xpath("//button[@id='finish']"));
        finish.click();

    }

    @Then("the order is complete and success message is shown")
    public void the_order_is_complete_and_success_message_is_shown() {

        Assert.assertTrue(driver.findElement(By.xpath("//img[@alt='Pony Express']")).isDisplayed(),"The pony express logo is not visible");
        Assert.assertEquals(driver.findElement(By.xpath("//h2[normalize-space()='Thank you for your order!']")).getText(),"Thank you for your order!","Thank you message is different");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='complete-text']")).getText(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!","Complete message is different");
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='back-to-products']")).isDisplayed(),"Back to product button is not displayed");

        WebElement backToProductButton = driver.findElement(By.xpath("//button[@id='back-to-products']"));

        backToProductButton.click();

        try{
            WebElement shoppingCartBadge = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
            Assert.fail();
        }catch (Exception e){
            Assert.assertTrue(true);
        }
    }

    @And("the user accepts in Your Cart page")
    public void theUserAcceptsInYourCartPage() throws InterruptedException {
        //Assertion before going on
        sleep(2000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_item_name']")).getText(), titleItems, "the product added to cart is not the same presents in the cart");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_item_price']")).getText(), itemsPrice, "the prices are not matching in cart page");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_item_desc']")).getText(), descriptionItems, "descriptions are not matching");
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='continue-shopping']")).isDisplayed(), "Continue shopping button is not displayed");

        WebElement checkoutButton = driver.findElement(By.xpath("//button[@id='checkout']"));
        checkoutButton.click();


    }
}
