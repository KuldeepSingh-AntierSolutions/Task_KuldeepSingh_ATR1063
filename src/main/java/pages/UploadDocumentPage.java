package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UploadDocumentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Document Type Selection
    @FindBy(xpath = "//input[@type='radio' and contains(@value, 'medical')]")
    private WebElement medicalDocumentsRadio;

    @FindBy(xpath = "//input[@type='radio' and contains(@value, 'non-medical')]")
    private WebElement nonMedicalDocumentsRadio;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    private WebElement continueButton;

    // Upload Options
    @FindBy(xpath = "//button[.//h3[contains(text(), 'From Device')]]")
    private WebElement fromDeviceOption;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Upload Image')]]")
    private WebElement uploadImageOption;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Take Photo')]]")
    private WebElement takePhotoOption;

    @FindBy(xpath = "//button[.//h3[contains(text(), 'Get EMR')]]")
    private WebElement getEMROption;

    // Browse Button
    @FindBy(xpath = "//button[contains(text(), 'Browse')]")
    private WebElement browseButton;

    // File Input (hidden)
    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileInput;

    // Constructor
    public UploadDocumentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isUploadDocumentPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("uploadDocument"));
            System.out.println("✓ Upload Document page displayed");
            return driver.getCurrentUrl().contains("uploadDocument");
        } catch (Exception e) {
            System.err.println("Upload Document page not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isDocumentTypeSelectionDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'What are you uploading')]")));
            System.out.println("✓ Document type selection displayed");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectMedicalDocuments() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(medicalDocumentsRadio));
            Thread.sleep(500);
            medicalDocumentsRadio.click();
            System.out.println("✓ Medical Documents selected");
        } catch (Exception e) {
            System.err.println("Failed to select Medical Documents: " + e.getMessage());
            throw new RuntimeException("Failed to select Medical Documents", e);
        }
    }

    public void selectNonMedicalDocuments() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(nonMedicalDocumentsRadio));
            nonMedicalDocumentsRadio.click();
            System.out.println("✓ Non-Medical Documents selected");
        } catch (Exception e) {
            System.err.println("Failed to select Non-Medical Documents: " + e.getMessage());
        }
    }

    public void clickContinueButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            Thread.sleep(500);
            continueButton.click();
            System.out.println("✓ Continue button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Continue: " + e.getMessage());
            throw new RuntimeException("Failed to click Continue", e);
        }
    }

    public void selectFromDeviceOption() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(fromDeviceOption));
            Thread.sleep(500);
            fromDeviceOption.click();
            System.out.println("✓ From Device option selected");
        } catch (Exception e) {
            System.err.println("Failed to select From Device: " + e.getMessage());
            throw new RuntimeException("Failed to select From Device", e);
        }
    }

    public boolean isBrowseButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(browseButton));
            System.out.println("✓ Browse button displayed");
            return browseButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void uploadFile(String filePath) {
        try {
            // Find the hidden file input and send the file path
            WebElement fileInputElement = driver.findElement(By.xpath("//input[@type='file']"));
            fileInputElement.sendKeys(filePath);
            System.out.println("✓ File uploaded: " + filePath);
            Thread.sleep(2000); // Wait for upload to start
        } catch (Exception e) {
            System.err.println("Failed to upload file: " + e.getMessage());
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public void clickConfirmAndContinue() {
        try {
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Confirm') and contains(text(), 'Continue')]")));
            Thread.sleep(500);
            confirmButton.click();
            System.out.println("✓ Confirm & Continue button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Confirm & Continue: " + e.getMessage());
            throw new RuntimeException("Failed to click Confirm & Continue", e);
        }
    }
}