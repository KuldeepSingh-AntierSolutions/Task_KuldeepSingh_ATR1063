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

public class PlanDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//h1 | //h2 | //*[contains(@class, 'plan-title')]")
    private WebElement planTitle;

    @FindBy(xpath = "//button[contains(@aria-label, 'back') or contains(@class, 'back')]")
    private WebElement backButton;

    // Task Cards
    @FindBy(xpath = "//div[contains(@class, 'task-card')] | //div[contains(@class, 'task-item')]")
    private List<WebElement> taskCards;

    // Add Task Button (+ button on plan card)
    @FindBy(xpath = "//button[contains(text(), '+') or contains(@aria-label, 'add') or .//img[contains(@alt, 'add')] or .//svg[contains(@class, 'add')]]")
    private List<WebElement> addTaskButtons;

    // Constructor
    public PlanDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isPlanDetailPageDisplayed() {
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(planTitle));
            System.out.println("✓ Plan Detail page displayed");
            return planTitle.isDisplayed();
        } catch (Exception e) {
            System.err.println("Plan Detail page not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getPlanTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(planTitle));
            String title = planTitle.getText();
            System.out.println("✓ Plan title: " + title);
            return title;
        } catch (Exception e) {
            System.err.println("Plan title not found: " + e.getMessage());
            return "";
        }
    }

    public void clickAddTaskButton() {
        try {
            Thread.sleep(2000);

            // Try multiple strategies to find the add task button
            WebElement addButton = null;

            try {
                // Strategy 1: Look for + button on first task card or plan card
                addButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//button[contains(text(), '+')])[1]")));
            } catch (Exception e1) {
                try {
                    // Strategy 2: Look for add button by aria-label
                    addButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[contains(@aria-label, 'add task') or contains(@aria-label, 'Add task')]")));
                } catch (Exception e2) {
                    try {
                        // Strategy 3: Look for button with add icon
                        addButton = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath(
                                        "//button[.//svg[contains(@class, 'add')] or .//img[contains(@alt, 'add')]]")));
                    } catch (Exception e3) {
                        // Strategy 4: Generic button locator
                        addButton = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("(//button[contains(@class, 'add')])[1]")));
                    }
                }
            }

            if (addButton != null) {
                Thread.sleep(500);
                addButton.click();
                System.out.println("✓ Add Task button clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click Add Task button: " + e.getMessage());
            throw new RuntimeException("Failed to click Add Task button", e);
        }
    }

    public void clickFirstAddTaskButton() {
        try {
            Thread.sleep(2000);

            // Find the first + button
            WebElement firstAddButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[contains(text(), '+') or contains(@aria-label, 'add')])[1]")));

            Thread.sleep(500);
            firstAddButton.click();
            System.out.println("✓ First Add Task button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click first Add Task button: " + e.getMessage());
            throw new RuntimeException("Failed to click first Add Task button", e);
        }
    }

    public int getTasksCount() {
        try {
            Thread.sleep(1000);
            int count = taskCards.size();
            System.out.println("✓ Total tasks count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Failed to get tasks count: " + e.getMessage());
            return 0;
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

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}
