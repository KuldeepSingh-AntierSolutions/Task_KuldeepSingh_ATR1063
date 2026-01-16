package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlansListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Tab Elements
    @FindBy(xpath = "//button[contains(text(), 'Active')]")
    private WebElement activeTab;

    @FindBy(xpath = "//button[contains(text(), 'In-Active')]")
    private WebElement inActiveTab;

    @FindBy(xpath = "//button[contains(text(), 'Completed')]")
    private WebElement completedTab;

    // Search Elements
    @FindBy(xpath = "//input[@placeholder='Search' or contains(@placeholder, 'Search')]")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(@class, 'search')]")
    private WebElement searchButton;

    // Plan Cards
    @FindBy(xpath = "//div[contains(@class, 'plan-card')] | //div[contains(@class, 'plan-item')]")
    private List<WebElement> planCards;

    // Add Plan Button
    @FindBy(xpath = "//button[contains(@aria-label, 'add') or contains(@class, 'add')]")
    private WebElement addPlanButton;

    // Message
    @FindBy(xpath = "//*[contains(text(), 'tasks will update')]")
    private WebElement updateMessage;

    // Constructor
    public PlansListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isPlansListPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("plans"));
            System.out.println("✓ Plans List page displayed");
            return driver.getCurrentUrl().contains("plans");
        } catch (Exception e) {
            System.err.println("Plans List page not displayed: " + e.getMessage());
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

    public boolean isActiveTabDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(activeTab));
            return activeTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlanDisplayedInList(String planName) {
        try {
            Thread.sleep(2000);
            WebElement planElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(), '" + planName + "')]")));
            boolean isDisplayed = planElement.isDisplayed();
            System.out.println("✓ Plan '" + planName + "' found in list: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("Plan '" + planName + "' not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isPlanWithPriorityDisplayed(String planName, String priority) {
        try {
            Thread.sleep(2000);
            WebElement planElement = driver.findElement(
                    By.xpath("//*[contains(text(), '" + planName + "')]/following-sibling::*[contains(text(), '"
                            + priority + "')] | " +
                            "//*[contains(text(), '" + planName + "')]/parent::*//*[contains(text(), '" + priority
                            + "')]"));
            boolean isDisplayed = planElement.isDisplayed();
            System.out.println("✓ Plan '" + planName + "' with priority '" + priority + "' displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("Plan with priority not found: " + e.getMessage());
            return false;
        }
    }

    public int getPlansCount() {
        try {
            Thread.sleep(1000);
            int count = planCards.size();
            System.out.println("✓ Total plans count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Failed to get plans count: " + e.getMessage());
            return 0;
        }
    }

    public void searchPlan(String planName) {
        try {
            searchInput.clear();
            searchInput.sendKeys(planName);
            System.out.println("✓ Searched for plan: " + planName);
        } catch (Exception e) {
            System.err.println("Failed to search plan: " + e.getMessage());
        }
    }

    public void clickPlanCard(String planName) {
        try {
            Thread.sleep(2000);
            WebElement planCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(), '" + planName
                            + "')]/ancestor::div[contains(@class, 'plan') or contains(@class, 'card')]")));
            planCard.click();
            System.out.println("✓ Clicked on plan card: " + planName);
        } catch (Exception e) {
            System.err.println("Failed to click on plan card: " + e.getMessage());
            throw new RuntimeException("Failed to click on plan card", e);
        }
    }

    public void clickFirstPlanCard() {
        try {
            Thread.sleep(2000);
            WebElement firstPlanCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class, 'plan-card')] | //div[contains(@class, 'plan-item')])[1]")));
            firstPlanCard.click();
            System.out.println("✓ Clicked on first plan card");
        } catch (Exception e) {
            System.err.println("Failed to click on first plan card: " + e.getMessage());
            throw new RuntimeException("Failed to click on first plan card", e);
        }
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}