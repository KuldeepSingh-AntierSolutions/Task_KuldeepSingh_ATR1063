package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HealthMenuPage;
import pages.LoginPage;
import pages.OTPVerificationPage;
import pages.WelcomePage;
import utils.BaseTest;

public class ExploringSoloFlowTest extends BaseTest {

    private static final String EMAIL = "laptop@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String FALLBACK_OTP = "000000";

    @Test(priority = 1, description = "Verify Exploring Solo flow - Login and navigate to Health Menu")
    public void testExploringSoloFlow() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Exploring Solo Flow - Health Menu Navigation");
        System.out.println("========================================\\n");

        try {
            // Step 1: Navigate to Login Page
            System.out.println("STEP 1: Navigate to Login Page");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginPage(BASE_URL);
            
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
                "Login page should be displayed");
            System.out.println("✓ Login page verified\\n");

            // Step 2: Login with credentials
            System.out.println("STEP 2: Enter Credentials and Sign In");
            loginPage.login(EMAIL, PASSWORD);
            waitForSeconds(3);

            // Step 3: Handle OTP
            System.out.println("STEP 3: Handle OTP Verification");
            OTPVerificationPage otpPage = new OTPVerificationPage(driver);
            Assert.assertTrue(otpPage.isOTPPageDisplayed(), 
                "OTP page should be displayed");

            // Fetch OTP dynamically
            String otpCode = yopmailService.getOTPWithRetry(EMAIL, 3, 5);
            if (otpCode == null || otpCode.length() != 6) {
                System.err.println("⚠ Using fallback OTP: " + FALLBACK_OTP);
                otpCode = FALLBACK_OTP;
            }
            
            otpPage.verifyOTP(otpCode);
            waitForSeconds(4);

            // Step 4: Verify Welcome Page
            System.out.println("\\nSTEP 4: Verify Welcome Page");
            WelcomePage welcomePage = new WelcomePage(driver);
            Assert.assertTrue(welcomePage.isWelcomePageDisplayed(), 
                "Welcome page should be displayed");
            Assert.assertTrue(welcomePage.verifyCurrentURL("welcomeHailey"), 
                "URL should contain 'welcomeHailey'");
            System.out.println("✓ Welcome page verified\\n");

            // Step 5: Click 'I've got this, exploring solo' button
            System.out.println("STEP 5: Click 'I've got this, exploring solo' Button");
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Step 6: Verify Health Menu Page
            System.out.println("\\nSTEP 6: Verify Health Menu Page");
            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            
            Assert.assertTrue(healthMenuPage.verifyCurrentURL("healthMenu"), 
                "URL should contain 'healthMenu'");
            System.out.println("✓ URL verified: healthMenu");
            
            Assert.assertTrue(healthMenuPage.isHealthMenuPageDisplayed(), 
                "Health Menu page should be displayed");
            System.out.println("✓ Health Menu page displayed");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Exploring Solo Flow Successful");
            System.out.println("========================================\\n");

        } catch (AssertionError e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("\\n❌ TEST ERROR: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Verify Health Menu page elements")
    public void testHealthMenuPageElements() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Health Menu Page Elements Verification");
        System.out.println("========================================\\n");

        try {
            // Login flow
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

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Verify Health Menu elements
            System.out.println("STEP 1: Verify Header Elements");
            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            
            Assert.assertTrue(healthMenuPage.verifyAllHeaderElementsDisplayed(), 
                "All header elements should be displayed");
            System.out.println("✓ All header elements verified\\n");

            // Verify Progress Banner
            System.out.println("STEP 2: Verify Progress Banner");
            Assert.assertTrue(healthMenuPage.isProgressBannerDisplayed(), 
                "Progress banner should be displayed");
            
            String bannerHeading = healthMenuPage.getProgressBannerHeading();
            Assert.assertFalse(bannerHeading.isEmpty(), 
                "Progress banner heading should not be empty");
            System.out.println("✓ Progress banner verified\\n");

            // Verify My Planner Section
            System.out.println("STEP 3: Verify My Planner Section");
            Assert.assertTrue(healthMenuPage.isMyPlannerSectionDisplayed(), 
                "My Planner section should be displayed");
            
            String plannerHeading = healthMenuPage.getMyPlannerHeading();
            Assert.assertEquals(plannerHeading, "My Planner", 
                "My Planner heading should match");
            
            String plannerDescription = healthMenuPage.getMyPlannerDescription();
            Assert.assertTrue(plannerDescription.contains("Track appointments"), 
                "My Planner description should contain 'Track appointments'");
            
            Assert.assertTrue(healthMenuPage.isMyPlannerArrowButtonDisplayed(), 
                "My Planner arrow button should be displayed");
            System.out.println("✓ My Planner section verified\\n");

            // Verify Avatar
            System.out.println("STEP 4: Verify Avatar");
            Assert.assertTrue(healthMenuPage.isAvatarDisplayed(), 
                "Avatar should be displayed");
            System.out.println("✓ Avatar verified\\n");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Health Menu Elements Verified");
            System.out.println("========================================\\n");

        } catch (AssertionError e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("\\n❌ TEST ERROR: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify navigation from Welcome page to Health Menu")
    public void testWelcomeToHealthMenuNavigation() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Welcome Page to Health Menu Navigation");
        System.out.println("========================================\\n");

        try {
            // Complete login flow
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

            // Verify on Welcome page
            WelcomePage welcomePage = new WelcomePage(driver);
            String welcomeURL = driver.getCurrentUrl();
            Assert.assertTrue(welcomeURL.contains("welcomeHailey"), 
                "Should be on Welcome page");
            System.out.println("✓ On Welcome page: " + welcomeURL);

            // Click Exploring Solo
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Verify navigation to Health Menu
            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            String healthMenuURL = healthMenuPage.getCurrentURL();
            Assert.assertTrue(healthMenuURL.contains("healthMenu"), 
                "Should navigate to Health Menu page");
            System.out.println("✓ Navigated to Health Menu: " + healthMenuURL);

            Assert.assertTrue(healthMenuPage.isHealthMenuPageDisplayed(), 
                "Health Menu page should be displayed");
            System.out.println("✓ Health Menu page displayed successfully");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Navigation Verified");
            System.out.println("========================================\\n");

        } catch (AssertionError e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("\\n❌ TEST ERROR: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed with exception: " + e.getMessage());
        }
    }
}