package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WelcomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//h1[contains(text(), 'Hey Laptop')]")
    private WebElement welcomeHeading;

    // Constructor
    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isWelcomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeHeading));
            System.out.println("✓ Welcome page displayed");
            return welcomeHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("Welcome page not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getWelcomeHeadingText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeHeading));
            String text = welcomeHeading.getText();
            System.out.println("✓ Welcome heading: " + text);
            return text;
        } catch (Exception e) {
            System.err.println("Welcome heading not found: " + e.getMessage());
            return "";
        }
    }

    public boolean isWelcomeHeadingContainsName(String name) {
        String headingText = getWelcomeHeadingText();
        boolean contains = headingText.contains(name);
        System.out.println("✓ Welcome heading contains '" + name + "': " + contains);
        return contains;
    }

    public boolean verifyCurrentURL(String expectedURLPart) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedURLPart));
            String currentURL = driver.getCurrentUrl();
            System.out.println("✓ Current URL: " + currentURL);
            return currentURL.contains(expectedURLPart);
        } catch (Exception e) {
            System.err.println("URL verification failed: " + e.getMessage());
            return false;
        }
    }

    public void clickShowMeAroundButton() {
        try {
            // Wait for the page to be fully loaded
            Thread.sleep(2000);
            
            // Try multiple locator strategies
            WebElement button = null;
            
            try {
                // Strategy 1: Find button directly
                button = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h3[contains(text(), 'Show me around')]/parent::*/following-sibling::button")));
            } catch (Exception e1) {
                try {
                    // Strategy 2: Find by button containing arrow image
                    button = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(@class, '') and position()=1]")));
                } catch (Exception e2) {
                    // Strategy 3: Find first visible button
                    button = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//button[.//img[contains(@alt, 'Arrow')]])[1]")));
                }
            }
            
            if (button != null) {
                Thread.sleep(500);
                button.click();
                System.out.println("✓ 'Show me around, Hailey!' button clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click 'Show me around' button: " + e.getMessage());
            throw new RuntimeException("Failed to click 'Show me around' button", e);
        }
    }
    public void clickExploringSoloButton() {
        try {
            // Wait for the page to be fully loaded
            Thread.sleep(2000);
            
            // Try multiple locator strategies
            WebElement button = null;
            
            try {
                // Strategy 1: Find button directly by text
                button = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h3[contains(text(), 'exploring solo')]/parent::*/following-sibling::button")));
            } catch (Exception e1) {
                try {
                    // Strategy 2: Find second button
                    button = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//button[.//img[contains(@alt, 'Arrow')]])[2]")));
                } catch (Exception e2) {
                    // Strategy 3: Find by heading and button association
                    button = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(., 'exploring solo')]")));
                }
            }
            
            if (button != null) {
                Thread.sleep(500);
                button.click();
                System.out.println("✓ 'I've got this, exploring solo' button clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click 'exploring solo' button: " + e.getMessage());
            throw new RuntimeException("Failed to click 'exploring solo' button", e);
        }
    }
}