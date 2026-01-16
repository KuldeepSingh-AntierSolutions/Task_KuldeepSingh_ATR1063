package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AssistAIPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//h2[contains(text(), 'Terms & Conditions')]")
    private WebElement termsHeading;

    @FindBy(xpath = "//*[contains(text(), 'Before you start')]")
    private WebElement termsSubtitle;

    @FindBy(xpath = "//button[contains(text(), 'I Agree')]")
    private WebElement iAgreeButton;

    @FindBy(xpath = "//*[contains(text(), 'AI Chatbot is not a substitute')]")
    private WebElement term1;

    @FindBy(xpath = "//*[contains(text(), 'All responses are informational')]")
    private WebElement term2;

    @FindBy(xpath = "//*[contains(text(), 'Always consult a certified healthcare provider')]")
    private WebElement term3;

    @FindBy(xpath = "//*[contains(text(), 'Your interactions are secure and private')]")
    private WebElement term4;

    @FindBy(xpath = "//*[contains(text(), 'here for testing')]")
    private WebElement term5;

    // Constructor
    public AssistAIPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isTermsDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(termsHeading));
            System.out.println("✓ Terms & Conditions dialog displayed");
            return termsHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("Terms & Conditions dialog not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getTermsHeadingText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(termsHeading));
            String text = termsHeading.getText();
            System.out.println("✓ Terms heading: " + text);
            return text;
        } catch (Exception e) {
            System.err.println("Terms heading not found: " + e.getMessage());
            return "";
        }
    }

    public boolean isTermsSubtitleDisplayed() {
        try {
            return termsSubtitle.isDisplayed();
        } catch (Exception e) {
            System.err.println("Terms subtitle not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isIAgreeButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(iAgreeButton));
            System.out.println("✓ 'I Agree' button displayed");
            return iAgreeButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("'I Agree' button not displayed: " + e.getMessage());
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
            System.err.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public boolean verifyAllTermsDisplayed() {
        try {
            Thread.sleep(1000); // Allow time for all elements to load
            
            boolean term1Displayed = term1.isDisplayed();
            boolean term2Displayed = term2.isDisplayed();
            boolean term3Displayed = term3.isDisplayed();
            boolean term4Displayed = term4.isDisplayed();
            boolean term5Displayed = term5.isDisplayed();

            System.out.println("✓ Term 1 (AI Chatbot substitute): " + term1Displayed);
            System.out.println("✓ Term 2 (Informational responses): " + term2Displayed);
            System.out.println("✓ Term 3 (Consult healthcare provider): " + term3Displayed);
            System.out.println("✓ Term 4 (Secure and private): " + term4Displayed);
            System.out.println("✓ Term 5 (Testing): " + term5Displayed);

            return term1Displayed && term2Displayed && term3Displayed && 
                   term4Displayed && term5Displayed;
        } catch (Exception e) {
            System.err.println("Error verifying terms: " + e.getMessage());
            return false;
        }
    }

    public void clickIAgreeButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(iAgreeButton));
            Thread.sleep(500);
            iAgreeButton.click();
            System.out.println("✓ 'I Agree' button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click 'I Agree': " + e.getMessage());
            throw new RuntimeException("Failed to click 'I Agree'", e);
        }
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}