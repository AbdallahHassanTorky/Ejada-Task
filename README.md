# Technical Challenge: Login Page Validation

Hello there! Congratulations on passing the second step of our process.  
Let’s move on to the following step, shall we? It’s going to be a technical challenge!

## Task Overview

You are given a login page [https://www.saucedemo.com/](https://www.saucedemo.com/) to an online service. Your task is to validate whether the form works correctly.

Your task is to provide a suite of tests using TestNG and Selenium WebDriver covering the below requirements:

## Requirements
Google_Chrome_(64bit)_v114.0.5735.199
Microsoft Edge  - 129.0.2792.89
firefox 131.0.3.223

### 1. Check if the username and password fields are on the main screen of the application:

- Username input has `id` equal to `user-name`.
- Password input has `id` equal to `password`.
- Login button has `id` equal to `login-button`.

### 2. Check if the given valid credentials work:

- Use credentials (Username: `standard_user`, Password: `secret_sauce`).
- After a successful login attempt, a div containing text: `Swag Labs` is visible.

### 3. Check if the given wrong credentials work:

- Use invalid credentials.
- After an unsuccessful login attempt, a div with class `error-message-container error` and containing a message `Epic sadface: Username and password do not match any user in this service` is visible.

### 4. Check for empty credentials:

- After an unsuccessful login attempt, a div with class `error-message-container error` and containing a message `Epic sadface: Username is required` is visible.
- After an unsuccessful login attempt, a div with class `error-message-container error` and containing a message `Epic sadface: Password is required` is visible.

## Sample Code

Here’s a basic implementation using TestNG and Selenium WebDriver:

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testFieldsPresence() {
        Assert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed(), "Username field is not displayed.");
        Assert.assertTrue(driver.findElement(By.id("password")).isDisplayed(), "Password field is not displayed.");
        Assert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(), "Login button is not displayed.");
    }

    @Test
    public void testValidLogin() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement swagLabsText = driver.findElement(By.className("title"));
        Assert.assertTrue(swagLabsText.isDisplayed(), "Swag Labs is not visible after successful login.");
    }

    @Test
    public void testInvalidLogin() {
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container.error"));
        String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(errorMessage.getText(), expectedMessage, "Error message for invalid credentials is not as expected.");
    }

    @Test
    public void testEmptyCredentials() {
        driver.findElement(By.id("login-button")).click();

        WebElement usernameError = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertEquals(usernameError.getText(), "Epic sadface: Username is required", "Username required error message is incorrect.");

        driver.findElement(By.id("user-name")).sendKeys("someuser");
        driver.findElement(By.id("password")).clear(); // Clear password field
        driver.findElement(By.id("login-button")).click();

        WebElement passwordError = driver.findElement(By.cssSelector(".error-message-container.error"));
        Assert.assertEquals(passwordError.getText(), "Epic sadface: Password is required", "Password required error message is incorrect.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
