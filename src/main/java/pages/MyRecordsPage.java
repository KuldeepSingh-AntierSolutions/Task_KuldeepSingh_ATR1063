package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyRecordsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//*[contains(text(), 'My Records')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//input[@placeholder='Search' or @type='Search']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(@aria-label, 'add') or contains(@aria-label, 'upload')]")
    private WebElement addButton;

    // Empty State
    @FindBy(xpath = "//*[contains(text(), 'Looks a little empty')]")
    private WebElement emptyStateHeading;

    @FindBy(xpath = "//*[contains(text(), 'waiting for your first entry')]")
    private WebElement emptyStateMessage;

    // Constructor
    public MyRecordsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isMyRecordsPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("myRecords"));
            System.out.println("✓ My Records page displayed");
            return driver.getCurrentUrl().contains("myRecords");
        } catch (Exception e) {
            System.err.println("My Records page not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyCurrentURL(String expectedURLPart) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedURLPart));
            String currentURL = driver.getCurrentUrl();
            System.out.println("✓ Current URL: " + currentURL);
            return currentURL.contains(expectedURLPart);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmptyStateDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptyStateHeading));
            System.out.println("✓ Empty state displayed");
            return emptyStateHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddButton() {
        try {
            // Try multiple strategies to find the add button
            WebElement button = null;
            try {
                button = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@aria-label, 'Upload')]")));
            } catch (Exception e1) {
                try {
                    button = driver.findElement(By.xpath("//button[contains(@class, 'add')]"));
                } catch (Exception e2) {
                    // Look for blue circular button with +
                    button = driver.findElement(By.xpath("//button[contains(@style, 'background')]"));
                }
            }
            
            if (button != null) {
                Thread.sleep(500);
                button.click();
                System.out.println("✓ Add/Upload button clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click add button: " + e.getMessage());
            throw new RuntimeException("Failed to click add button", e);
        }
    }
}