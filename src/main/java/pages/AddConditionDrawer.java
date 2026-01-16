package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddConditionDrawer {
    private WebDriver driver;
    private WebDriverWait wait;

    // Drawer Elements
    @FindBy(xpath = "//h2[contains(text(), 'Add Condition')]")
    private WebElement drawerTitle;

    @FindBy(xpath = "//input[@placeholder='Condition' or contains(@aria-label, 'Condition')]")
    private WebElement conditionInput;

    @FindBy(xpath = "//button[contains(text(), 'Submit')]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[contains(@aria-label, 'close') or contains(@class, 'close')]")
    private WebElement closeButton;

    // Constructor
    public AddConditionDrawer(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isDrawerDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(drawerTitle));
            System.out.println("✓ Add Condition drawer displayed");
            return drawerTitle.isDisplayed();
        } catch (Exception e) {
            System.err.println("Add Condition drawer not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getDrawerTitle() {
        try {
            return drawerTitle.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void enterConditionName(String conditionName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(conditionInput));
            conditionInput.clear();
            Thread.sleep(300);
            conditionInput.sendKeys(conditionName);
            System.out.println("✓ Condition name entered: " + conditionName);
        } catch (Exception e) {
            System.err.println("Failed to enter condition name: " + e.getMessage());
            throw new RuntimeException("Failed to enter condition name", e);
        }
    }

    public void clickSubmitButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            Thread.sleep(500);
            submitButton.click();
            System.out.println("✓ Submit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Submit button: " + e.getMessage());
            throw new RuntimeException("Failed to click Submit button", e);
        }
    }

    public void addCondition(String conditionName) {
        enterConditionName(conditionName);
        clickSubmitButton();
    }

    public void closeDrawer() {
        try {
            closeButton.click();
            System.out.println("✓ Drawer closed");
        } catch (Exception e) {
            System.err.println("Failed to close drawer: " + e.getMessage());
        }
    }
}