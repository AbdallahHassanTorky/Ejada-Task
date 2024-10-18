import base.Driver;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageobjects.LoginPage;

public class StepDefinitions {

    private WebDriver driver;
    private LoginPage login;

    @Given("I navigate to the login page")
    public void navigateToLoginPage() {
        driver = Driver.getDriver();
        login = new LoginPage();
        driver.get(Driver.getUrl());
    }

    @When("I log in with username {string} and password {string}")
    public void logIn(String username, String password) {
        login.login(username, password);
    }

    @Then("I should see an error message {string}")
    public void verifyErrorMessage(String expectedError) {
        if (!expectedError.isEmpty()) {
            String actualError = driver.findElement(By.id("error-message-id")).getText(); // Update with actual ID
            Assert.assertTrue(actualError.contains(expectedError), "Username and password wrong");
        }
    }

    @Then("I should be on the inventory page")
    public void verifyInventoryPage() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "User is not on the inventory page.");
    }

    @When("I add the most expensive products to my cart")
    public void addMostExpensiveProductsToCart() {
        login.addMostExpensiveProductsToCart();
    }

    @When("I navigate to the cart")
    public void navigateToCart() {
        WebElement cartButton = driver.findElement(By.className("shopping_cart_link"));
        cartButton.click();
    }

    @Then("I should see my products in the cart")
    public void verifyProductsInCart() {
        Assert.assertTrue(driver.getPageSource().contains("Sauce Labs"), "Products not found in the cart.");
    }

    @Then("I should be on the cart page")
    public void verifyCartPage() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "User is not on the cart page.");
    }

    @When("I proceed to checkout")
    public void proceedToCheckout() {
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();
    }

    @Then("I should be on the checkout page")
    public void verifyCheckoutPage() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html", "User is not on the checkout page.");
    }

    @When("I fill in the checkout form with name {string}, last name {string}, and zip code {string}")
    public void fillCheckoutForm(String firstName, String lastName, String zipCode) {
        login.fillCheckoutForm(firstName, lastName, zipCode);
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();
    }

    @Then("I should see the order summary")
    public void verifyOrderSummary() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html", "User is not on the overview page.");
        String totalAmount = driver.findElement(By.className("summary_total_label")).getText();
        Assert.assertTrue(totalAmount.contains("Total: $"), "Total amount not displayed correctly.");
    }

    @When("I finish the order")
    public void finishOrder() {
        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();
    }

    @Then("I should see a thank you message and order dispatched confirmation")
    public void verifyThankYouMessage() {
        Assert.assertTrue(driver.getPageSource().contains("Thank you for your order"), "Thank you message not found.");
        Assert.assertTrue(driver.getPageSource().contains("Your order has been dispatched"), "Order dispatched message not found.");
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String arg0) {
    }
}
