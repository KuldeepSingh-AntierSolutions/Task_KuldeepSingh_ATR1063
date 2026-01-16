package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddPlanPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Form Fields
    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-label, 'Plan Type')]")
    private WebElement planTypeDropdown;

    @FindBy(xpath = "//input[contains(@placeholder, 'plan name')]")
    private WebElement planNameInput;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-label, 'Category')]")
    private WebElement categoryDropdown;

    @FindBy(xpath = "//textarea[contains(@placeholder, 'Description') or @name='description']")
    private WebElement descriptionTextarea;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-label, 'Assignee')]")
    private WebElement assigneeDropdown;

    @FindBy(xpath = "//input[contains(@placeholder, 'From Date')]")
    private WebElement fromDateInput;

    @FindBy(xpath = "//input[contains(@placeholder, 'To Date')]")
    private WebElement toDateInput;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-label, 'Priority')]")
    private WebElement priorityDropdown;

    // Save Button
    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    private WebElement saveButton;

    // Back Button
    @FindBy(xpath = "//button[contains(@type, 'button')][1]")
    private WebElement backButton;

    // Constructor
    public AddPlanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isAddPlanPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("addAPlan"));
            System.out.println("✓ Add Plan page displayed");
            return driver.getCurrentUrl().contains("addAPlan");
        } catch (Exception e) {
            System.err.println("Add Plan page not displayed: " + e.getMessage());
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

    public void selectPlanType(String planType) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(planTypeDropdown));
            Thread.sleep(500);
            planTypeDropdown.click();
            Thread.sleep(1000);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and contains(text(), '" + planType + "')]")));
            option.click();
            System.out.println("✓ Plan Type selected: " + planType);
        } catch (Exception e) {
            System.err.println("Failed to select Plan Type: " + e.getMessage());
            throw new RuntimeException("Failed to select Plan Type", e);
        }
    }

    public void enterPlanName(String planName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(planNameInput));
            planNameInput.clear();
            Thread.sleep(300);
            planNameInput.sendKeys(planName);
            System.out.println("✓ Plan Name entered: " + planName);
        } catch (Exception e) {
            System.err.println("Failed to enter Plan Name: " + e.getMessage());
            throw new RuntimeException("Failed to enter Plan Name", e);
        }
    }

    public void selectCategory(String category) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));
            Thread.sleep(500);
            categoryDropdown.click();
            Thread.sleep(1000);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and contains(text(), '" + category + "')]")));
            option.click();
            System.out.println("✓ Category selected: " + category);
        } catch (Exception e) {
            System.err.println("Failed to select Category: " + e.getMessage());
            throw new RuntimeException("Failed to select Category", e);
        }
    }

    public void enterDescription(String description) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(descriptionTextarea));
            descriptionTextarea.clear();
            Thread.sleep(300);
            descriptionTextarea.sendKeys(description);
            System.out.println("✓ Description entered: " + description);
        } catch (Exception e) {
            System.err.println("Failed to enter Description: " + e.getMessage());
            throw new RuntimeException("Failed to enter Description", e);
        }
    }

    public void selectAssignee(String assignee) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(assigneeDropdown));
            Thread.sleep(500);
            assigneeDropdown.click();
            Thread.sleep(1000);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and contains(text(), '" + assignee + "')]")));
            option.click();
            System.out.println("✓ Assignee selected: " + assignee);
        } catch (Exception e) {
            System.err.println("Failed to select Assignee: " + e.getMessage());
            throw new RuntimeException("Failed to select Assignee", e);
        }
    }

    public void selectFromDate(String date) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(fromDateInput));
            Thread.sleep(500);
            fromDateInput.click();
            Thread.sleep(1500);

            // Click Today button
            WebElement todayButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Today')]")));
            todayButton.click();
            System.out.println("✓ From Date selected: Today");
        } catch (Exception e) {
            System.err.println("Failed to select From Date: " + e.getMessage());
            throw new RuntimeException("Failed to select From Date", e);
        }
    }

    public void selectToDate(int dayOfMonth) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(toDateInput));
            Thread.sleep(500);
            toDateInput.click();
            Thread.sleep(1500);

            // Select specific day
            WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@role='gridcell' and not(contains(@class, 'disabled'))]//span[text()='" + dayOfMonth + "']")));
            dayElement.click();
            System.out.println("✓ To Date selected: Day " + dayOfMonth);
        } catch (Exception e) {
            System.err.println("Failed to select To Date: " + e.getMessage());
            throw new RuntimeException("Failed to select To Date", e);
        }
    }

    public void selectPriority(String priority) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(priorityDropdown));
            Thread.sleep(500);
            priorityDropdown.click();
            Thread.sleep(1000);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and contains(text(), '" + priority + "')]")));
            option.click();
            System.out.println("✓ Priority selected: " + priority);
        } catch (Exception e) {
            System.err.println("Failed to select Priority: " + e.getMessage());
            throw new RuntimeException("Failed to select Priority", e);
        }
    }

    public void clickSaveButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(saveButton));
            Thread.sleep(500);
            saveButton.click();
            System.out.println("✓ Save button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Save button: " + e.getMessage());
            throw new RuntimeException("Failed to click Save button", e);
        }
    }

    public void fillAndSubmitPlanForm(String planType, String planName, String category,
                                       String description, String assignee, int toDay, String priority) {
        selectPlanType(planType);
        enterPlanName(planName);
        selectCategory(category);
        enterDescription(description);
        selectAssignee(assignee);
        selectFromDate("Today");
        selectToDate(toDay);
        selectPriority(priority);
        clickSaveButton();
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}