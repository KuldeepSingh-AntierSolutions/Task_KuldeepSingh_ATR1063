package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//input[@type='text']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInButton;

    @FindBy(xpath = "//h1[contains(text(), 'Welcome Back')]")
    private WebElement welcomeBackHeading;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public void navigateToLoginPage(String url) {
        driver.get(url);
        System.out.println("✓ Navigated to login page: " + url);
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(welcomeBackHeading));
        } catch (Exception e) {
            System.err.println("Warning: Page load delay - " + e.getMessage());
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeBackHeading));
            return welcomeBackHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("Login page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(emailField));
            emailField.clear();
            Thread.sleep(500);
            emailField.sendKeys(email);
            System.out.println("✓ Email entered: " + email);
        } catch (Exception e) {
            System.err.println("Failed to enter email: " + e.getMessage());
            throw new RuntimeException("Failed to enter email", e);
        }
    }

    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(passwordField));
            passwordField.clear();
            Thread.sleep(500);
            passwordField.sendKeys(password);
            System.out.println("✓ Password entered");
        } catch (Exception e) {
            System.err.println("Failed to enter password: " + e.getMessage());
            throw new RuntimeException("Failed to enter password", e);
        }
    }

    public void clickSignInButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            Thread.sleep(500);
            signInButton.click();
            System.out.println("✓ Sign In button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click sign in: " + e.getMessage());
            throw new RuntimeException("Failed to click sign in", e);
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}