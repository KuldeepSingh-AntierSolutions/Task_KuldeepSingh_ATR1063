package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddFolderModal {
    private WebDriver driver;
    private WebDriverWait wait;

    // Modal Elements
    @FindBy(xpath = "//h2[contains(text(), 'Add Folder')] | //h3[contains(text(), 'Add Folder')] | //*[contains(text(), 'Create Folder')]")
    private WebElement modalHeading;

    @FindBy(xpath = "//input[@placeholder='Folder name' or @name='folderName' or @id='folderName' or contains(@placeholder, 'name')]")
    private WebElement folderNameInput;

    @FindBy(xpath = "//button[contains(text(), 'Create') or contains(text(), 'Save') or contains(text(), 'Add') or @type='submit']")
    private WebElement createButton;

    @FindBy(xpath = "//button[contains(text(), 'Cancel') or contains(@aria-label, 'cancel') or contains(@aria-label, 'close')]")
    private WebElement cancelButton;

    // Constructor
    public AddFolderModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isModalDisplayed() {
        try {
            Thread.sleep(1000);

            // Check if modal is visible by input field or heading
            try {
                wait.until(ExpectedConditions.visibilityOf(folderNameInput));
                System.out.println("✓ Add Folder modal displayed");
                return true;
            } catch (Exception e) {
                // Fallback: check if heading is visible
                if (modalHeading != null && modalHeading.isDisplayed()) {
                    System.out.println("✓ Add Folder modal displayed (verified by heading)");
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            System.err.println("Add Folder modal not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getModalHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(modalHeading));
            String heading = modalHeading.getText();
            System.out.println("✓ Modal heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Modal heading not found: " + e.getMessage());
            return "";
        }
    }

    public void enterFolderName(String folderName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(folderNameInput));
            folderNameInput.clear();
            Thread.sleep(500);
            folderNameInput.sendKeys(folderName);
            System.out.println("✓ Folder name entered: " + folderName);
        } catch (Exception e) {
            System.err.println("Failed to enter folder name: " + e.getMessage());
            throw new RuntimeException("Failed to enter folder name", e);
        }
    }

    public void clickCreateButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(createButton));
            Thread.sleep(500);
            createButton.click();
            System.out.println("✓ Create button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click create button: " + e.getMessage());
            throw new RuntimeException("Failed to click create button", e);
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

    public void createFolder(String folderName) {
        System.out.println("\nCreating folder: " + folderName);
        enterFolderName(folderName);
        clickCreateButton();
        System.out.println("✓ Folder creation completed");
    }
}
