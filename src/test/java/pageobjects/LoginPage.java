package pageobjects;

import base.Driver; // Ensure this import is present
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class LoginPage {
    WebDriver driver = Driver.getDriver();
/****************LOCATORS****************/

    public void login(String username, String password) {
        WebDriver driver = Driver.getDriver();
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }


    /****************TEST DATA****************/


    @DataProvider(name = "loginData")
    public static Object[][] loginData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("testData.csv"));
        String line;
        List<Object[]> data = new ArrayList<>();

        // Skip the header line if necessary
        reader.readLine(); // Uncomment if you have a header

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            data.add(new Object[]{values[0], values[1], values.length > 2 ? values[2] : null});
        }
        reader.close();
        return data.toArray(new Object[0][0]);
    }

    /****************ERROR MESSAGE****************/


    public String getErrorMessage() {
        WebDriver driver = Driver.getDriver();
        return driver.findElement(By.cssSelector(".error-message-container")).getText(); // Adjust the selector as necessary
    }
    public void verifyErrorMessage(String expectedError, String username) {
        String actualError = getErrorMessage();

        // Verify the error message against the expected error
        Assert.assertEquals(actualError, expectedError, "Error message mismatch for user: " + username);

        System.out.println("Test executed for user: " + username + " on thread: " + Thread.currentThread().getName());
    }
    public void addMostExpensiveProductsToCart() {
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        // Assuming you have some logic to determine the most expensive items
        // Here, we are selecting the first two products as a placeholder
        // You need to replace this logic with actual product pricing retrieval

        if (products.size() < 2) return; // Ensure there are at least two products

        // Example logic to click on the first two products (update this logic to select the most expensive)
        for (int i = 0; i < 2; i++) {
            WebElement productButton = products.get(i).findElement(By.className("btn_inventory"));
            productButton.click();
        }
    }

    public void fillCheckoutForm(String firstName, String lastName, String zipCode) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(zipCode);
    }

    public String getCurrentUsername() {
        return "";
    }
}