package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlannerPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Header Elements
    @FindBy(xpath = "//button[contains(@type, 'button')]//ancestor::*[contains(text(), 'My Planner')] | //*[contains(text(), 'My Planner')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[contains(@class, 'back') or position()=1]")
    private WebElement backButton;

    @FindBy(xpath = "//button[contains(@class, 'menu') or .//img[contains(@alt, 'menu')]]")
    private WebElement menuButton;

    // Tab Elements
    @FindBy(xpath = "//button[contains(text(), 'Plans')]")
    private WebElement plansTab;

    @FindBy(xpath = "//button[contains(text(), 'Calendar')]")
    private WebElement calendarTab;

    @FindBy(xpath = "//button[contains(text(), 'Careboard')]")
    private WebElement careboardTab;

    @FindBy(xpath = "//button[contains(text(), 'Family')]")
    private WebElement familyTab;

    // Today's Tasks Section
    @FindBy(xpath = "//*[contains(text(),'Today's Tasks')]")
    private WebElement todaysTasksHeading;

    @FindBy(xpath = "//a[contains(text(), 'View All')]")
    private WebElement viewAllLink;

    @FindBy(xpath = "//*[contains(text(), 'No tasks scheduled')]")
    private WebElement noTasksMessage;

    // Active Plans Section
    @FindBy(xpath = "//*[contains(text(), 'Active Plans')]")
    private WebElement activePlansHeading;

    @FindBy(xpath = "//div[contains(@class, 'plan-card')]")
    private List<WebElement> planCards;

    // Build a Plan Button
    @FindBy(xpath = "//button[contains(text(), 'Build a Plan')]")
    private WebElement buildAPlanButton;

    // Constructor
    public PlannerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isPlannerPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("planner"));
            System.out.println("✓ Planner page displayed");
            return driver.getCurrentUrl().contains("planner");
        } catch (Exception e) {
            System.err.println("Planner page not displayed: " + e.getMessage());
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

    public boolean isPlansTabDisplayed() {
        try {
            return plansTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCalendarTabDisplayed() {
        try {
            return calendarTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCareboardTabDisplayed() {
        try {
            return careboardTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFamilyTabDisplayed() {
        try {
            return familyTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTodaysTasksSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(todaysTasksHeading));
            return todaysTasksHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isActivePlansSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(activePlansHeading));
            return activePlansHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBuildAPlanButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(buildAPlanButton));
            System.out.println("✓ Build a Plan button displayed");
            return buildAPlanButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("Build a Plan button not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickBuildAPlanButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(buildAPlanButton));
            Thread.sleep(500);
            buildAPlanButton.click();
            System.out.println("✓ Build a Plan button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Build a Plan button: " + e.getMessage());
            throw new RuntimeException("Failed to click Build a Plan button", e);
        }
    }

    public int getActivePlansCount() {
        try {
            return planCards.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickPlansTab() {
        try {
            plansTab.click();
            System.out.println("✓ Plans tab clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Plans tab: " + e.getMessage());
        }
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}