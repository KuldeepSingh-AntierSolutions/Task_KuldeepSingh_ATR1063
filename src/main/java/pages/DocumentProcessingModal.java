package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DocumentProcessingModal {
    private WebDriver driver;
    private WebDriverWait wait;

    // Modal Elements
    @FindBy(xpath = "//*[contains(text(), 'Almost Ready')]")
    private WebElement almostReadyText;

    @FindBy(xpath = "//*[contains(text(), 'Important notice') or contains(text(), 'Important Notice')]")
    private WebElement importantNoticeHeading;

    @FindBy(xpath = "//div[contains(@class, 'notice') or contains(@class, 'important')]")
    private WebElement importantNoticeSection;

    // Loading Indicator
    @FindBy(xpath = "//div[contains(@class, 'loading') or contains(@class, 'spinner')]")
    private WebElement loadingIndicator;

    // Constructor
    public DocumentProcessingModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Longer wait for processing
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isProcessingModalDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'Almost Ready')]")));
            System.out.println("✓ Processing modal displayed");
            return true;
        } catch (Exception e) {
            System.err.println("Processing modal not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isAlmostReadyTextDisplayed() {
        try {
            boolean isDisplayed = almostReadyText.isDisplayed();
            System.out.println("✓ 'Almost Ready' text displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("'Almost Ready' text not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isImportantNoticeDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'Important notice') or contains(text(), 'Important Notice')]")));
            boolean isDisplayed = importantNoticeHeading.isDisplayed();
            System.out.println("✓ Important Notice section displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("Important Notice section not found: " + e.getMessage());
            return false;
        }
    }

    public String getImportantNoticeText() {
        try {
            WebElement noticeElement = driver.findElement(
                By.xpath("//*[contains(text(), 'Important notice')]/following-sibling::* | " +
                         "//*[contains(text(), 'Important notice')]/parent::*/following-sibling::*"));
            String text = noticeElement.getText();
            System.out.println("✓ Important Notice text: " + text);
            return text;
        } catch (Exception e) {
            System.err.println("Could not get Important Notice text: " + e.getMessage());
            return "";
        }
    }

    public void waitForProcessingToComplete() {
        try {
            // Wait for the modal to disappear or URL to change
            wait.until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOf(almostReadyText),
                ExpectedConditions.urlContains("reviewDocument")
            ));
            System.out.println("✓ Document processing completed");
        } catch (Exception e) {
            System.err.println("Processing did not complete: " + e.getMessage());
        }
    }

    public boolean verifyImportantNoticeSection() {
        boolean modalDisplayed = isProcessingModalDisplayed();
        boolean almostReadyDisplayed = isAlmostReadyTextDisplayed();
        boolean noticeDisplayed = isImportantNoticeDisplayed();

        System.out.println("\\n=== Important Notice Verification ===");
        System.out.println("Processing Modal Displayed: " + modalDisplayed);
        System.out.println("'Almost Ready' Text Displayed: " + almostReadyDisplayed);
        System.out.println("Important Notice Displayed: " + noticeDisplayed);
        System.out.println("====================================\\n");

        return modalDisplayed && almostReadyDisplayed && noticeDisplayed;
    }
}