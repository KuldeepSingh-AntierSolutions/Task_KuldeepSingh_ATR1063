package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class MyHealthInformationFlowTest extends BaseTest {

    private static final String EMAIL = "laptop@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String FALLBACK_OTP = "000000";

    @Test(priority = 1, description = "Complete flow: Login > Health Menu > My Health Information > My Health Bio")
    public void testMyHealthBioFlow() {
        System.out.println("\\n========================================");
        System.out.println("TEST: My Health Bio Flow");
        System.out.println("========================================\\n");

        try {
            // Login Flow
            performLogin();

            // Navigate to Health Menu
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Click on My Health Information in circular menu
            System.out.println("\\nSTEP: Click My Health Information in Circular Menu");
            driver.findElement(By.tagName("body")); // Ensure page is loaded
            
            // Click using coordinates (right side of circular menu)
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 400);");
            waitForSeconds(1);
            
            driver.findElement(By.tagName("body")).click(); // Click to focus
            driver.findElement(By.xpath("//body")).sendKeys(""); // Ensure interaction
            
            // Click on the My Health Information area (coordinates based on circular menu)
            org.openqa.selenium.interactions.Actions actions = 
                new org.openqa.selenium.interactions.Actions(driver);
            actions.moveByOffset(450, 540).click().perform();
            actions.moveByOffset(-450, -540).perform(); // Reset position
            
            System.out.println("✓ Clicked on My Health Information in circular menu");
            waitForSeconds(3);

            // Click arrow to navigate to My Health Information page
            System.out.println("\\nSTEP: Navigate to My Health Information Page");
            driver.findElement(By.xpath("//h2[contains(text(), 'My Health Information')]/following::button[1]")).click();
            waitForSeconds(3);

            // Verify My Health Information page
            MyHealthInformationPage myHealthInfoPage = new MyHealthInformationPage(driver);
            Assert.assertTrue(myHealthInfoPage.verifyCurrentURL("phi"),
                "Should be on My Health Information page");
            System.out.println("✓ My Health Information page verified\\n");

            // Click on My Health Bio
            System.out.println("STEP: Click My Health Bio");
            myHealthInfoPage.clickMyHealthBioCard();
            waitForSeconds(3);

            // Verify My Health Bio page
            MyHealthBioPage healthBioPage = new MyHealthBioPage(driver);
            Assert.assertTrue(healthBioPage.verifyCurrentURL("healthBio"),
                "Should be on My Health Bio page");

            Assert.assertTrue(healthBioPage.isPageTitleDisplayed(),
                "Page title should be displayed");

            Assert.assertEquals(healthBioPage.getPageTitle(), "My Health Bio",
                "Page title should be 'My Health Bio'");

            Assert.assertTrue(healthBioPage.isEmptyStateDisplayed(),
                "Empty state should be displayed");

            Assert.assertEquals(healthBioPage.getEmptyStateHeading(), "Looks a little empty!",
                "Empty state heading should match");

            Assert.assertTrue(healthBioPage.getEmptyStateMessage().contains("first entry"),
                "Empty state message should contain 'first entry'");

            Assert.assertTrue(healthBioPage.verifyAllEmptyStateElements(),
                "All empty state elements should be displayed");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: My Health Bio Flow Successful");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Complete flow: Login > Health Menu > My Health Information > My Health History")
    public void testMyHealthHistoryFlow() {
        System.out.println("\\n========================================");
        System.out.println("TEST: My Health History Flow");
        System.out.println("========================================\\n");

        try {
            // Login Flow
            performLogin();

            // Navigate to Health Menu
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Navigate to My Health Information
            System.out.println("\\nSTEP: Navigate to My Health Information");
            org.openqa.selenium.interactions.Actions actions = 
                new org.openqa.selenium.interactions.Actions(driver);
            actions.moveByOffset(450, 540).click().perform();
            actions.moveByOffset(-450, -540).perform();
            waitForSeconds(3);

            driver.findElement(By.xpath("//h2[contains(text(), 'My Health Information')]/following::button[1]")).click();
            waitForSeconds(3);

            // Click on My Health History
            System.out.println("\\nSTEP: Click My Health History");
            MyHealthInformationPage myHealthInfoPage = new MyHealthInformationPage(driver);
            myHealthInfoPage.clickMyHealthHistoryCard();
            waitForSeconds(3);

            // Verify My Health History page
            MyHealthHistoryPage healthHistoryPage = new MyHealthHistoryPage(driver);
            Assert.assertTrue(healthHistoryPage.verifyCurrentURL("healthHistory"),
                "Should be on My Health History page");

            Assert.assertTrue(healthHistoryPage.isPageTitleDisplayed(),
                "Page title should be displayed");

            Assert.assertEquals(healthHistoryPage.getPageTitle(), "My Health History",
                "Page title should be 'My Health History'");

            Assert.assertTrue(healthHistoryPage.isEmptyStateDisplayed(),
                "Empty state should be displayed");

            Assert.assertEquals(healthHistoryPage.getEmptyStateHeading(), "Looks a little empty!",
                "Empty state heading should match");

            Assert.assertTrue(healthHistoryPage.getEmptyStateMessage().contains("first entry"),
                "Empty state message should contain 'first entry'");

            Assert.assertTrue(healthHistoryPage.verifyAllEmptyStateElements(),
                "All empty state elements should be displayed");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: My Health History Flow Successful");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify both My Health Bio and My Health History pages")
    public void testBothHealthPagesVerification() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Verify Both Health Pages");
        System.out.println("========================================\\n");

        try {
            // Login and navigate
            performLogin();
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Navigate to My Health Information
            org.openqa.selenium.interactions.Actions actions = 
                new org.openqa.selenium.interactions.Actions(driver);
            actions.moveByOffset(450, 540).click().perform();
            actions.moveByOffset(-450, -540).perform();
            waitForSeconds(3);

            driver.findElement(By.xpath("//button[.//img[@alt='right arrow']]")).click();
            waitForSeconds(3);

            MyHealthInformationPage myHealthInfoPage = new MyHealthInformationPage(driver);

            // Test My Health Bio
            System.out.println("\\nVerifying My Health Bio...");
            Assert.assertTrue(myHealthInfoPage.isMyHealthBioCardDisplayed(),
                "My Health Bio card should be displayed");
            Assert.assertEquals(myHealthInfoPage.getMyHealthBioHeading(), "My Health Bio",
                "My Health Bio heading should match");
            Assert.assertTrue(myHealthInfoPage.getMyHealthBioDescription().contains("Doctor-ready"),
                "Description should contain 'Doctor-ready'");

            myHealthInfoPage.clickMyHealthBioCard();
            waitForSeconds(3);

            MyHealthBioPage healthBioPage = new MyHealthBioPage(driver);
            Assert.assertTrue(healthBioPage.isMyHealthBioPageDisplayed(),
                "My Health Bio page should be displayed");
            Assert.assertTrue(healthBioPage.isEmptyStateDisplayed(),
                "Empty state should be displayed");

            // Go back
            healthBioPage.clickBackButton();
            waitForSeconds(2);

            // Test My Health History
            System.out.println("\\nVerifying My Health History...");
            Assert.assertTrue(myHealthInfoPage.isMyHealthHistoryCardDisplayed(),
                "My Health History card should be displayed");
            Assert.assertEquals(myHealthInfoPage.getMyHealthHistoryHeading(), "My Health History",
                "My Health History heading should match");
            Assert.assertTrue(myHealthInfoPage.getMyHealthHistoryDescription().contains("Surgeries"),
                "Description should contain 'Surgeries'");

            myHealthInfoPage.clickMyHealthHistoryCard();
            waitForSeconds(3);

            MyHealthHistoryPage healthHistoryPage = new MyHealthHistoryPage(driver);
            Assert.assertTrue(healthHistoryPage.isMyHealthHistoryPageDisplayed(),
                "My Health History page should be displayed");
            Assert.assertTrue(healthHistoryPage.isEmptyStateDisplayed(),
                "Empty state should be displayed");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Both Health Pages Verified");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Helper method for login
    private void performLogin() {
        System.out.println("Performing login...");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        waitForSeconds(3);

        String otpCode = yopmailService.getOTPWithRetry(EMAIL, 3, 5);
        if (otpCode == null || otpCode.length() != 6) {
            otpCode = FALLBACK_OTP;
        }

        OTPVerificationPage otpPage = new OTPVerificationPage(driver);
        otpPage.verifyOTP(otpCode);
        waitForSeconds(4);
        System.out.println("✓ Login completed\\n");
    }
}