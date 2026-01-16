package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AssistAIPage;
import pages.LoginPage;
import pages.OTPVerificationPage;
import pages.WelcomePage;
import utils.BaseTest;

public class LoginAndTermsVerificationTest extends BaseTest {

    private static final String EMAIL = "laptop@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String FALLBACK_OTP = "000000"; // Fallback in case email fetch fails

    @Test(priority = 1, description = "Verify complete login flow with dynamic OTP and Terms & Conditions display")
    public void testLoginFlowWithDynamicOTPAndVerifyTermsAndConditions() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Login Flow with Dynamic OTP and Terms Verification");
        System.out.println("========================================\\n");

        try {
            // Step 1: Navigate to Login Page
            System.out.println("STEP 1: Navigate to Login Page");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginPage(BASE_URL);
            
            // Verify login page is displayed
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
                "Login page should be displayed");
            System.out.println("✓ Login page verified successfully\\n");

            // Step 2: Enter credentials and sign in
            System.out.println("STEP 2: Enter Credentials and Sign In");
            loginPage.login(EMAIL, PASSWORD);
            System.out.println("✓ Login credentials submitted\\n");

            // Wait for OTP page
            waitForSeconds(3);

            // Step 3: Verify OTP page is displayed
            System.out.println("STEP 3: Verify OTP Verification Page");
            OTPVerificationPage otpPage = new OTPVerificationPage(driver);
            
            Assert.assertTrue(otpPage.isOTPPageDisplayed(), 
                "OTP verification page should be displayed");
            
            Assert.assertEquals(otpPage.getOTPHeadingText(), "OTP Verification", 
                "OTP heading should match");
            
            Assert.assertTrue(otpPage.isEmailVerificationTextDisplayed(EMAIL), 
                "Email verification text should contain: " + EMAIL);
            
            Assert.assertEquals(otpPage.getOTPFieldsCount(), 6, 
                "Should have 6 OTP input fields");
            System.out.println("✓ OTP page verified\\n");

            // Step 4: Fetch OTP dynamically from Yopmail
            System.out.println("STEP 4: Fetch OTP from Yopmail");
            String otpCode = yopmailService.getOTPWithRetry(EMAIL, 3, 5);
            
            // If OTP fetch fails, use fallback
            if (otpCode == null || otpCode.length() != 6) {
                System.err.println("⚠ Failed to fetch OTP from email, using fallback OTP: " + FALLBACK_OTP);
                otpCode = FALLBACK_OTP;
            } else {
                System.out.println("✓ Dynamic OTP fetched successfully: " + otpCode);
            }
            
            // Step 5: Enter OTP and verify
            System.out.println("\\nSTEP 5: Enter OTP and Submit");
            otpPage.verifyOTP(otpCode);
            System.out.println("✓ OTP submitted\\n");

            // Wait for navigation
            waitForSeconds(4);

            // Step 6: Verify Welcome Page
            System.out.println("STEP 6: Verify Welcome Page");
            WelcomePage welcomePage = new WelcomePage(driver);
            
            Assert.assertTrue(welcomePage.isWelcomePageDisplayed(), 
                "Welcome page should be displayed");
            
            Assert.assertTrue(welcomePage.isWelcomeHeadingContainsName("Laptop"), 
                "Welcome heading should contain user name 'Laptop'");
            
            Assert.assertTrue(welcomePage.isWelcomeHeadingContainsName("Hailey"), 
                "Welcome heading should contain 'Hailey'");
            
            Assert.assertTrue(welcomePage.verifyCurrentURL("welcomeHailey"), 
                "URL should contain 'welcomeHailey'");
            System.out.println("✓ Welcome page verified\\n");

            // Step 7: Click 'Show me around, Hailey!' button
            System.out.println("STEP 7: Click 'Show me around, Hailey!' Button");
            welcomePage.clickShowMeAroundButton();
            System.out.println("✓ Button clicked\\n");

            // Wait for navigation
            waitForSeconds(4);

            // Step 8: Verify Terms & Conditions Dialog
            System.out.println("STEP 8: Verify Terms & Conditions Dialog");
            AssistAIPage assistAIPage = new AssistAIPage(driver);
            
            Assert.assertTrue(assistAIPage.verifyCurrentURL("assistAi"), 
                "URL should contain 'assistAi'");
            
            Assert.assertTrue(assistAIPage.isTermsDialogDisplayed(), 
                "Terms & Conditions dialog should be displayed");
            
            Assert.assertEquals(assistAIPage.getTermsHeadingText(), "Terms & Conditions", 
                "Terms heading should be 'Terms & Conditions'");
            
            Assert.assertTrue(assistAIPage.isTermsSubtitleDisplayed(), 
                "Terms subtitle should be displayed");
            
            Assert.assertTrue(assistAIPage.verifyAllTermsDisplayed(), 
                "All 5 terms should be displayed");
            
            Assert.assertTrue(assistAIPage.isIAgreeButtonDisplayed(), 
                "'I Agree' button should be displayed");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: All Verifications Successful");
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

    @Test(priority = 2, description = "Verify Terms & Conditions content with dynamic OTP")
    public void testTermsAndConditionsContentWithDynamicOTP() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Terms & Conditions Content Verification with Dynamic OTP");
        System.out.println("========================================\\n");

        try {
            // Perform login flow
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginPage(BASE_URL);
            loginPage.login(EMAIL, PASSWORD);

            waitForSeconds(3);

            // Fetch OTP dynamically
            String otpCode = yopmailService.getOTPWithRetry(EMAIL, 3, 5);
            if (otpCode == null || otpCode.length() != 6) {
                System.err.println("⚠ Failed to fetch OTP from email, using fallback OTP: " + FALLBACK_OTP);
                otpCode = FALLBACK_OTP;
            }

            OTPVerificationPage otpPage = new OTPVerificationPage(driver);
            otpPage.verifyOTP(otpCode);

            waitForSeconds(4);

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickShowMeAroundButton();

            waitForSeconds(4);

            // Verify Terms content
            AssistAIPage assistAIPage = new AssistAIPage(driver);
            
            Assert.assertTrue(assistAIPage.verifyAllTermsDisplayed(), 
                "All terms should be displayed with correct content");
            
            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Terms Content Verified");
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
    
    @Test(priority = 3, description = "Test dynamic OTP fetch independently")
    public void testDynamicOTPFetch() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Dynamic OTP Fetch Verification");
        System.out.println("========================================\\n");

        try {
            // Trigger OTP by logging in
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateToLoginPage(BASE_URL);
            loginPage.login(EMAIL, PASSWORD);

            waitForSeconds(3);

            // Verify OTP page is displayed
            OTPVerificationPage otpPage = new OTPVerificationPage(driver);
            Assert.assertTrue(otpPage.isOTPPageDisplayed(), 
                "OTP verification page should be displayed");

            // Fetch OTP dynamically
            String otpCode = yopmailService.getOTPWithRetry(EMAIL, 3, 5);
            
            // Verify OTP was fetched
            Assert.assertNotNull(otpCode, "OTP should be fetched from email");
            Assert.assertEquals(otpCode.length(), 6, "OTP should be 6 digits");
            Assert.assertTrue(otpCode.matches("\\\\d{6}"), "OTP should contain only digits");
            
            System.out.println("✓ Dynamic OTP validated: " + otpCode);
            
            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Dynamic OTP Fetch Successful");
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