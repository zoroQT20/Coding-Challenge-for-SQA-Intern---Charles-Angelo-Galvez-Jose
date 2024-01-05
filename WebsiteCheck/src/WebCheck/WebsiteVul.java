package WebCheck;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebsiteVul {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","C:\\seleniumwebdriver\\chromedriver\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");

        //WebDriver
        WebDriver driver = new ChromeDriver();

        // Maximize browser window
        driver.manage().window().maximize();

        testSuccessfulLogin(driver);
        testLockedOutUser(driver);

    }

    public static void testSuccessfulLogin(WebDriver driver) {
        // Website URL
        driver.get("https://www.saucedemo.com/");

        // Scenario 1
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Verification for successful or failed
        WebElement inventoryElement = driver.findElement(By.className("inventory_item"));
        if (inventoryElement.isDisplayed()) {
            System.out.println("Login successful. User navigated to home page.");
        } else {
            System.out.println("Login failed or user not on the home page.");
        }

        // Logout
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();

        //
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutLink.click();

        // Verification for successful login and logout
        WebElement loginForm = driver.findElement(By.id("login-button"));
        if (loginForm.isDisplayed()) {
            System.out.println("Logout successful. User navigated to login page.");
        } else {
            System.out.println("Logout failed or user not on the login page.");
        }
    }

    public static void testLockedOutUser(WebDriver driver) {
       
        driver.get("https://www.saucedemo.com/");

        // Login with locked-out
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("locked_out_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Error message for locked-out user
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        String errorMessage = errorElement.getText();

        if (!errorMessage.isEmpty()) {
            System.out.println("Error message displayed: " + errorMessage);
        } else {
            System.out.println("No error message displayed or login succeeded unexpectedly.");
        }
        
        //optional
        driver.quit();
    }
}
