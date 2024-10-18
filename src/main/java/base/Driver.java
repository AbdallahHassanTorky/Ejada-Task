package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Driver {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String browser;
    private static String url;

    static {
        try {
            loadConfig();
        } catch (IOException e) {
            System.err.println("Could not load configuration: " + e.getMessage());
        }
    }

    private static void loadConfig() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            browser = properties.getProperty("browser");
            url = properties.getProperty("url");
            System.out.println("Loaded browser: " + browser);
            System.out.println("Loaded URL: " + url);
        }
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver());
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Reset driver for the current thread
        }
    }

    public static String getUrl() {
        return url;
    }
}
