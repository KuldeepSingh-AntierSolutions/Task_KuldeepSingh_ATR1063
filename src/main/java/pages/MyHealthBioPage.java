package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyHealthBioPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Header Elements
    @FindBy(xpath = "//*[contains(text(), 'My Health Bio')]")
    private WebElement pageTitle;

    // Empty State Elements
    @FindBy(xpath = "//h1[contains(text(), 'Looks a little empty')] | //h2[contains(text(), 'Looks a little empty')]")
    private WebElement emptyStateHeading;

    @FindBy(xpath = "//*[contains(text(), 'This spot is waiting for your first entry')]")
    private WebElement emptyStateMessage;

    @FindBy(xpath = "//img[@alt='No Records' or contains(@src, 'no-record') or contains(@class, 'empty')]")
    private WebElement emptyStateIllustration;

    // Constructor
    public MyHealthBioPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isMyHealthBioPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("healthBio"));
            System.out.println("✓ My Health Bio page displayed");
            return driver.getCurrentUrl().contains("healthBio");
        } catch (Exception e) {
            System.err.println("My Health Bio page not displayed: " + e.getMessage());
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
            System.err.println("URL verification failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isPageTitleDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            System.out.println("✓ Page title displayed: " + pageTitle.getText());
            return pageTitle.isDisplayed();
        } catch (Exception e) {
            System.err.println("Page title not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getPageTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            String title = pageTitle.getText();
            System.out.println("✓ Page title: " + title);
            return title;
        } catch (Exception e) {
            System.err.println("Could not get page title: " + e.getMessage());
            return "";
        }
    }

    public boolean isEmptyStateDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptyStateHeading));
            System.out.println("✓ Empty state displayed");
            return emptyStateHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("Empty state not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getEmptyStateHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptyStateHeading));
            String heading = emptyStateHeading.getText();
            System.out.println("✓ Empty state heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Could not get empty state heading: " + e.getMessage());
            return "";
        }
    }

    public String getEmptyStateMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(emptyStateMessage));
            String message = emptyStateMessage.getText();
            System.out.println("✓ Empty state message: " + message);
            return message;
        } catch (Exception e) {
            System.err.println("Could not get empty state message: " + e.getMessage());
            return "";
        }
    }

    public boolean isEmptyStateIllustrationDisplayed() {
        try {
            return emptyStateIllustration.isDisplayed();
        } catch (Exception e) {
            System.err.println("Empty state illustration not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        try {
            WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@type, 'button')][1]")));
            Thread.sleep(500);
            backButton.click();
            System.out.println("✓ Back button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click back button: " + e.getMessage());
            throw new RuntimeException("Failed to click back button", e);
        }
    }

    public boolean verifyAllEmptyStateElements() {
        boolean headingDisplayed = isEmptyStateDisplayed();
        boolean messageDisplayed = emptyStateMessage.isDisplayed();
        boolean illustrationDisplayed = isEmptyStateIllustrationDisplayed();

        System.out.println("✓ Empty state heading: " + headingDisplayed);
        System.out.println("✓ Empty state message: " + messageDisplayed);
        System.out.println("✓ Empty state illustration: " + illustrationDisplayed);

        return headingDisplayed && messageDisplayed && illustrationDisplayed;
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}