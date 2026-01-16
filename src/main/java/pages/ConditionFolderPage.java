package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConditionFolderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//button[contains(@type, 'button')][1]")
    private WebElement backButton;

    // Card Elements
    @FindBy(xpath = "//button[.//h3[contains(text(), 'Records')]]")
    private WebElement recordsCard;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Prescriptions')]]")
    private WebElement prescriptionsCard;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Planner')]]")
    private WebElement plannerCard;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Reminders')]]")
    private WebElement remindersCard;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Blogs')]]")
    private WebElement blogsCard;

    // Constructor
    public ConditionFolderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isConditionFolderPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("condition"));
            System.out.println("✓ Condition folder page displayed");
            return driver.getCurrentUrl().contains("condition");
        } catch (Exception e) {
            System.err.println("Condition folder page not displayed: " + e.getMessage());
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

    public boolean isRecordsCardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(recordsCard));
            System.out.println("✓ Records card displayed");
            return recordsCard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRecordsCard() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(recordsCard));
            Thread.sleep(500);
            recordsCard.click();
            System.out.println("✓ Records card clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Records card: " + e.getMessage());
            throw new RuntimeException("Failed to click Records card", e);
        }
    }

    public void clickPrescriptionsCard() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(prescriptionsCard));
            prescriptionsCard.click();
            System.out.println("✓ Prescriptions card clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Prescriptions card: " + e.getMessage());
        }
    }

    public boolean verifyAllCardsDisplayed() {
        boolean records = isRecordsCardDisplayed();
        boolean prescriptions = prescriptionsCard.isDisplayed();
        boolean planner = plannerCard.isDisplayed();
        boolean reminders = remindersCard.isDisplayed();
        boolean blogs = blogsCard.isDisplayed();

        System.out.println("✓ Records card: " + records);
        System.out.println("✓ Prescriptions card: " + prescriptions);
        System.out.println("✓ Planner card: " + planner);
        System.out.println("✓ Reminders card: " + reminders);
        System.out.println("✓ Blogs card: " + blogs);

        return records && prescriptions && planner && reminders && blogs;
    }
}