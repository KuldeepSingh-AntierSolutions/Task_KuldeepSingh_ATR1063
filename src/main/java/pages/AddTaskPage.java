package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddTaskPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Form Elements
    @FindBy(xpath = "//h1[contains(text(), 'Add Task')] | //h2[contains(text(), 'Add Task')] | //*[contains(text(), 'Create Task')]")
    private WebElement formHeading;

    @FindBy(xpath = "//input[@placeholder='Task Name' or @name='taskName' or @id='taskName']")
    private WebElement taskNameField;

    @FindBy(xpath = "//textarea[@placeholder='Description' or @name='description' or @id='description'] | //input[@placeholder='Description' or @name='description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//select[@name='category' or @id='category'] | //div[contains(@class, 'category')]//button")
    private WebElement categoryDropdown;

    @FindBy(xpath = "//input[@type='time' or @placeholder='Time' or @name='preferredTime']")
    private WebElement preferredTimeField;

    @FindBy(xpath = "//select[@name='repeat' or @id='repeat'] | //div[contains(@class, 'repeat')]//button")
    private WebElement repeatDropdown;

    @FindBy(xpath = "//input[@type='checkbox' and contains(@name, 'reminder')] | //label[contains(text(), 'Reminder')]//input")
    private WebElement reminderCheckbox;

    @FindBy(xpath = "//button[@type='submit' or contains(text(), 'Save') or contains(text(), 'Create') or contains(text(), 'Add')]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[contains(text(), 'Cancel') or contains(@aria-label, 'cancel')]")
    private WebElement cancelButton;

    // Constructor
    public AddTaskPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isAddTaskPageDisplayed() {
        try {
            Thread.sleep(2000);
            // Check if form is visible by looking for task name field
            wait.until(ExpectedConditions.visibilityOf(taskNameField));
            System.out.println("✓ Add Task form displayed");
            return taskNameField.isDisplayed();
        } catch (Exception e) {
            System.err.println("Add Task form not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getFormHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(formHeading));
            String heading = formHeading.getText();
            System.out.println("✓ Form heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Form heading not found: " + e.getMessage());
            return "";
        }
    }

    public void enterTaskName(String taskName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(taskNameField));
            taskNameField.clear();
            Thread.sleep(500);
            taskNameField.sendKeys(taskName);
            System.out.println("✓ Task name entered: " + taskName);
        } catch (Exception e) {
            System.err.println("Failed to enter task name: " + e.getMessage());
            throw new RuntimeException("Failed to enter task name", e);
        }
    }

    public void enterDescription(String description) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(descriptionField));
            descriptionField.clear();
            Thread.sleep(500);
            descriptionField.sendKeys(description);
            System.out.println("✓ Description entered: " + description);
        } catch (Exception e) {
            System.err.println("Failed to enter description: " + e.getMessage());
            throw new RuntimeException("Failed to enter description", e);
        }
    }

    public void selectCategory(String category) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));
            Thread.sleep(500);

            // Try dropdown select
            try {
                categoryDropdown.click();
                Thread.sleep(500);
                WebElement categoryOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//option[contains(text(), '" + category + "')] | //li[contains(text(), '" + category
                                + "')]")));
                categoryOption.click();
            } catch (Exception e) {
                // If dropdown doesn't work, try direct option click
                WebElement categoryOption = driver.findElement(
                        By.xpath("//*[contains(text(), '" + category + "')]"));
                categoryOption.click();
            }

            System.out.println("✓ Category selected: " + category);
        } catch (Exception e) {
            System.err.println("Failed to select category: " + e.getMessage());
            // Don't throw exception as category might be optional
        }
    }

    public void enterPreferredTime(String time) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(preferredTimeField));
            preferredTimeField.clear();
            Thread.sleep(500);
            preferredTimeField.sendKeys(time);
            System.out.println("✓ Preferred time entered: " + time);
        } catch (Exception e) {
            System.err.println("Failed to enter preferred time: " + e.getMessage());
            // Don't throw exception as time might be optional
        }
    }

    public void selectRepeatSchedule(String repeat) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(repeatDropdown));
            Thread.sleep(500);

            // Try dropdown select
            try {
                repeatDropdown.click();
                Thread.sleep(500);
                WebElement repeatOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//option[contains(text(), '" + repeat + "')] | //li[contains(text(), '" + repeat
                                + "')]")));
                repeatOption.click();
            } catch (Exception e) {
                // If dropdown doesn't work, try direct option click
                WebElement repeatOption = driver.findElement(
                        By.xpath("//*[contains(text(), '" + repeat + "')]"));
                repeatOption.click();
            }

            System.out.println("✓ Repeat schedule selected: " + repeat);
        } catch (Exception e) {
            System.err.println("Failed to select repeat schedule: " + e.getMessage());
            // Don't throw exception as repeat might be optional
        }
    }

    public void enableReminder() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(reminderCheckbox));
            if (!reminderCheckbox.isSelected()) {
                reminderCheckbox.click();
                System.out.println("✓ Reminder enabled");
            }
        } catch (Exception e) {
            System.err.println("Failed to enable reminder: " + e.getMessage());
            // Don't throw exception as reminder might be optional
        }
    }

    public void clickSubmitButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            Thread.sleep(500);
            submitButton.click();
            System.out.println("✓ Submit button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click submit button: " + e.getMessage());
            throw new RuntimeException("Failed to click submit button", e);
        }
    }

    public void clickCancelButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            Thread.sleep(500);
            cancelButton.click();
            System.out.println("✓ Cancel button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click cancel button: " + e.getMessage());
            throw new RuntimeException("Failed to click cancel button", e);
        }
    }

    public void fillAndSubmitTaskForm(String taskName, String description, String category,
            String preferredTime, String repeat, boolean enableReminder) {
        System.out.println("\nFilling task form...");

        enterTaskName(taskName);
        enterDescription(description);

        if (category != null && !category.isEmpty()) {
            selectCategory(category);
        }

        if (preferredTime != null && !preferredTime.isEmpty()) {
            enterPreferredTime(preferredTime);
        }

        if (repeat != null && !repeat.isEmpty()) {
            selectRepeatSchedule(repeat);
        }

        if (enableReminder) {
            enableReminder();
        }

        clickSubmitButton();
        System.out.println("✓ Task form submitted");
    }

    public void fillAndSubmitBasicTaskForm(String taskName, String description) {
        fillAndSubmitTaskForm(taskName, description, null, null, null, false);
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
