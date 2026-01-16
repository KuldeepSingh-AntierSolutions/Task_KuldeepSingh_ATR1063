package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YopmailService {
    private WebDriver yopmailDriver;
    private WebDriverWait wait;
    private static final String YOPMAIL_URL = "https://yopmail.com/";
    
    public YopmailService() {
        // Setup separate Chrome driver for Yopmail
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        this.yopmailDriver = new ChromeDriver(options);
        this.wait = new WebDriverWait(yopmailDriver, Duration.ofSeconds(20));
    }
    
    /**
     * Fetches OTP from Yopmail email
     * @param email The email address (e.g., laptop@yopmail.com)
     * @return The OTP code as a String
     */
    public String getOTPFromEmail(String email) {
        String otp = null;
        try {
            System.out.println("\\n=== Fetching OTP from Yopmail ===");
            
            // Extract username from email
            String username = email.split("@")[0];
            System.out.println("✓ Email username: " + username);
            
            // Navigate to Yopmail
            yopmailDriver.get(YOPMAIL_URL);
            System.out.println("✓ Navigated to Yopmail");
            Thread.sleep(2000);
            
            // Enter email in the login field
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@id='login']")));
            emailInput.clear();
            emailInput.sendKeys(username);
            System.out.println("✓ Entered email: " + username);
            
            // Click the check inbox button
            WebElement checkInboxButton = yopmailDriver.findElement(
                By.xpath("//button[@title='Check Inbox' or @class='sbut']"));
            checkInboxButton.click();
            System.out.println("✓ Clicked Check Inbox button");
            Thread.sleep(3000);
            
            // Wait for inbox to load and refresh if needed
            refreshInbox();
            
            // Switch to the iframe containing the email list
            switchToInboxFrame();
            
            // Click on the latest email
            clickLatestEmail();
            
            // Switch back to main content
            yopmailDriver.switchTo().defaultContent();
            Thread.sleep(2000);
            
            // Switch to the iframe containing the email content
            switchToEmailContentFrame();
            
            // Extract OTP from email content
            otp = extractOTPFromEmailContent();
            
            System.out.println("✓ OTP extracted successfully: " + otp);
            
        } catch (Exception e) {
            System.err.println("❌ Error fetching OTP from Yopmail: " + e.getMessage());
            e.printStackTrace();
        }
        
        return otp;
    }
    
    /**
     * Refresh the inbox to get latest emails
     */
    private void refreshInbox() {
        try {
            Thread.sleep(2000);
            // Click refresh button
            try {
                WebElement refreshButton = yopmailDriver.findElement(
                    By.xpath("//button[@id='refresh' or contains(@class, 'refresh')]"));
                refreshButton.click();
                System.out.println("✓ Refreshed inbox");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Note: Refresh button not found, continuing...");
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not refresh inbox - " + e.getMessage());
        }
    }
    
    /**
     * Switch to inbox frame containing email list
     */
    private void switchToInboxFrame() {
        try {
            // Try different frame locators
            try {
                yopmailDriver.switchTo().frame("ifinbox");
                System.out.println("✓ Switched to inbox frame (ifinbox)");
            } catch (Exception e) {
                yopmailDriver.switchTo().frame(yopmailDriver.findElement(
                    By.xpath("//iframe[@id='ifinbox']")));
                System.out.println("✓ Switched to inbox frame (xpath)");
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not switch to inbox frame - " + e.getMessage());
        }
    }
    
    /**
     * Click on the latest (first) email in the inbox
     */
    private void clickLatestEmail() {
        try {
            Thread.sleep(2000);
            
            // Wait for email list to load
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='m' or contains(@class, 'mail')]")));
            
            // Click the first email (latest)
            WebElement latestEmail = yopmailDriver.findElement(
                By.xpath("(//div[@class='m' or contains(@class, 'mail')])[1]"));
            latestEmail.click();
            System.out.println("✓ Clicked on latest email");
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.err.println("Warning: Could not click latest email - " + e.getMessage());
            
            // Alternative: Try clicking first row
            try {
                WebElement firstRow = yopmailDriver.findElement(By.xpath("(//div[@id='mail'])[1]"));
                firstRow.click();
                System.out.println("✓ Clicked on first email (alternative method)");
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.err.println("Error: Could not click any email - " + ex.getMessage());
            }
        }
    }
    
    /**
     * Switch to email content frame
     */
    private void switchToEmailContentFrame() {
        try {
            // Switch back to default content first
            yopmailDriver.switchTo().defaultContent();
            Thread.sleep(1000);
            
            // Switch to mail content frame
            try {
                yopmailDriver.switchTo().frame("ifmail");
                System.out.println("✓ Switched to email content frame (ifmail)");
            } catch (Exception e) {
                yopmailDriver.switchTo().frame(yopmailDriver.findElement(
                    By.xpath("//iframe[@id='ifmail']")));
                System.out.println("✓ Switched to email content frame (xpath)");
            }
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.err.println("Warning: Could not switch to email content frame - " + e.getMessage());
        }
    }
    
    /**
     * Extract OTP from email content using multiple strategies
     */
    private String extractOTPFromEmailContent() {
        String otp = null;
        
        try {
            // Get the entire email body text
            WebElement emailBody = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//body")));
            String emailText = emailBody.getText();
            System.out.println("✓ Email content retrieved");
            System.out.println("Email text preview: " + emailText.substring(0, Math.min(200, emailText.length())));
            
            // Strategy 1: Look for 6-digit OTP pattern
            Pattern pattern1 = Pattern.compile("\\\\b(\\\\d{6})\\\\b");
            Matcher matcher1 = pattern1.matcher(emailText);
            if (matcher1.find()) {
                otp = matcher1.group(1);
                System.out.println("✓ OTP found using pattern 1: " + otp);
                return otp;
            }
            
            // Strategy 2: Look for "OTP", "code", "verification code" followed by digits
            Pattern pattern2 = Pattern.compile("(?i)(otp|code|verification code)[:\\\\s]+(\\\\d{6})");
            Matcher matcher2 = pattern2.matcher(emailText);
            if (matcher2.find()) {
                otp = matcher2.group(2);
                System.out.println("✓ OTP found using pattern 2: " + otp);
                return otp;
            }
            
            // Strategy 3: Look for bold or span elements containing 6 digits
            try {
                WebElement otpElement = yopmailDriver.findElement(
                    By.xpath("//strong[string-length(normalize-space(text()))=6 and number(text())=number(text())] | " +
                             "//b[string-length(normalize-space(text()))=6 and number(text())=number(text())] | " +
                             "//span[string-length(normalize-space(text()))=6 and number(text())=number(text())]"));
                otp = otpElement.getText().trim();
                System.out.println("✓ OTP found in formatted element: " + otp);
                return otp;
            } catch (Exception e) {
                System.out.println("Note: OTP not found in formatted elements");
            }
            
            // Strategy 4: Look for any 6-digit number in the email
            Pattern pattern3 = Pattern.compile("(\\\\d{6})");
            Matcher matcher3 = pattern3.matcher(emailText);
            if (matcher3.find()) {
                otp = matcher3.group(1);
                System.out.println("✓ OTP found using pattern 3: " + otp);
                return otp;
            }
            
            // If no OTP found, print the full email for debugging
            if (otp == null) {
                System.err.println("❌ Could not extract OTP from email");
                System.err.println("Full email content:\\n" + emailText);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error extracting OTP: " + e.getMessage());
            e.printStackTrace();
        }
        
        return otp;
    }
    
    /**
     * Close the Yopmail browser
     */
    public void closeBrowser() {
        if (yopmailDriver != null) {
            System.out.println("✓ Closing Yopmail browser");
            yopmailDriver.quit();
        }
    }
    
    /**
     * Alternative method: Wait for new email and get OTP with retry mechanism
     */
    public String getOTPWithRetry(String email, int maxRetries, int waitBetweenRetries) {
        String otp = null;
        
        for (int i = 0; i < maxRetries; i++) {
            System.out.println("\\nAttempt " + (i + 1) + " of " + maxRetries + " to fetch OTP");
            
            otp = getOTPFromEmail(email);
            
            if (otp != null && otp.length() == 6) {
                System.out.println("✓ OTP successfully retrieved: " + otp);
                return otp;
            }
            
            if (i < maxRetries - 1) {
                System.out.println("⏳ Waiting " + waitBetweenRetries + " seconds before retry...");
                try {
                    Thread.sleep(waitBetweenRetries * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        System.err.println("❌ Failed to retrieve OTP after " + maxRetries + " attempts");
        return null;
    }
}