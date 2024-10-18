package steps;

import base.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

public class TestSteps {

    private WebDriver driver;
    private LoginPage login;

    public class Test1 {
        @BeforeClass
        public void setup() {
            driver = Driver.getDriver();
            login = new LoginPage();
        }

        @Test(dataProvider = "loginData", dataProviderClass = LoginPage.class)
        public void testLogin(String username, String password, String expectedError) {
            driver.get(Driver.getUrl());                            // GET URL
            login.login(username, password);                        // LOGIN WITH DATA
            login.verifyErrorMessage(expectedError, username);      // COMPARE B/W EXP AND ACT VALUE

        }

        @AfterClass
        public void tearDown() {
            Driver.quitDriver();
        }

    }

    public class Test2 {

        @BeforeClass
        public void setup() {
            driver = Driver.getDriver();
            login = new LoginPage();
        }

        @Test
        public void Invalid() {
            driver.get(Driver.getUrl());
            // Step 2: Login with a valid username and password
            login.login("standard_user", "secret_sauce");

            // Step 3: Verify successful login
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

            // Step 4: Add the most expensive two products to your cart
            login.addMostExpensiveProductsToCart();

            // Step 5: Click on the cart button
            WebElement cartButton = driver.findElement(By.className("shopping_cart_link"));
            cartButton.click();

            // Step 6: Verify navigation to Cart page
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html");
            Assert.assertTrue(driver.getPageSource().contains("Sauce Labs")); // Replace with actual product names
            Assert.assertTrue(driver.getPageSource().contains("Sauce Labs")); // Replace with actual product names

          // Step 7: Click on the Checkout button
            WebElement checkoutButton = driver.findElement(By.id("checkout"));
            checkoutButton.click();

            // Step 8: Verify navigation to Checkout page
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");

            // Step 9: Fill all the displayed form
            login.fillCheckoutForm("John", "Doe", "12345");

            // Step 10: Click on the Continue button
            WebElement continueButton = driver.findElement(By.id("continue"));
            continueButton.click();

            // Step 11a: Verify navigation to Overview page
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");

            // Step 11b: Verify Items total amount before taxes
            String totalAmount = driver.findElement(By.className("summary_total_label")).getText();
            Assert.assertTrue(totalAmount.contains("Total: $")); // Adjust this to match expected format

            // Step 11c: Verify URL matches
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");

            // Step 12: Click on the Finish button
            WebElement finishButton = driver.findElement(By.id("finish"));
            finishButton.click();

            // Step 13: Verify Thank You and order dispatched messages
            Assert.assertTrue(driver.getPageSource().contains("Thank you for your order"));
            Assert.assertTrue(driver.getPageSource().contains("Your order has been dispatched"));
        }
        @AfterClass
        public void tearDown() {
            Driver.quitDriver();
        }
        }


    }
