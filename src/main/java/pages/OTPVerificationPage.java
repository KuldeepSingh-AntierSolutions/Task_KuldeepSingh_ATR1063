package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OTPVerificationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//h2[contains(text(), 'OTP Verification')]")
    private WebElement otpHeading;

    @FindBy(xpath = "//a[contains(text(), 'laptop@yopmail.com')]")
    private WebElement emailVerificationText;

    @FindBy(xpath = "//input[@type='tel']")
    private List<WebElement> otpFields;

    @FindBy(xpath = "//button[contains(text(), 'Verify Code')]")
    private WebElement verifyCodeButton;

    // Constructor
    public OTPVerificationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isOTPPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(otpHeading));
            System.out.println("✓ OTP page displayed");
            return otpHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("OTP page not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isEmailVerificationTextDisplayed(String expectedEmail) {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailVerificationText));
            String actualEmail = emailVerificationText.getText();
            System.out.println("✓ Email verification text: " + actualEmail);
            return actualEmail.contains(expectedEmail);
        } catch (Exception e) {
            System.err.println("Email verification text not found: " + e.getMessage());
            return false;
        }
    }

    public String getOTPHeadingText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(otpHeading));
            return otpHeading.getText();
        } catch (Exception e) {
            System.err.println("OTP heading not found: " + e.getMessage());
            return "";
        }
    }

    public int getOTPFieldsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(otpFields));
            int count = otpFields.size();
            System.out.println("✓ OTP fields count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("OTP fields not found: " + e.getMessage());
            return 0;
        }
    }

    public void enterOTP(String otp) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(otpFields));
            
            if (otp.length() != 6) {
                throw new IllegalArgumentException("OTP must be 6 digits");
            }

            for (int i = 0; i < otpFields.size(); i++) {
                WebElement field = otpFields.get(i);
                wait.until(ExpectedConditions.elementToBeClickable(field));
                field.clear();
                Thread.sleep(300);
                field.sendKeys(String.valueOf(otp.charAt(i)));
                System.out.println("✓ OTP digit " + (i + 1) + " entered");
            }
        } catch (Exception e) {
            System.err.println("Failed to enter OTP: " + e.getMessage());
            throw new RuntimeException("Failed to enter OTP", e);
        }
    }

    public void clickVerifyCodeButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(verifyCodeButton));
            Thread.sleep(500);
            verifyCodeButton.click();
            System.out.println("✓ Verify Code button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Verify Code: " + e.getMessage());
            throw new RuntimeException("Failed to click Verify Code", e);
        }
    }

    public void verifyOTP(String otp) {
        enterOTP(otp);
        clickVerifyCodeButton();
    }
}