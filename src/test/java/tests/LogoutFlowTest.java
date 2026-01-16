package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class LogoutFlowTest extends BaseTest {

    private static final String EMAIL = "ten@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String OTP = "000000";

    @Test(priority = 1, description = "Complete flow: Login > Exploring Solo > Health Menu > Logout")
    public void testLogoutFlow() {
        System.out.println("\n========================================");
        System.out.println("TEST: Logout Flow");
        System.out.println("========================================\n");

        try {
            // STEP 1: Login
            System.out.println("STEP 1: Performing Login");
            performLogin();

            // STEP 2: Click on Exploring Solo option
            System.out.println("\nSTEP 2: Click on Exploring Solo");
            WelcomePage welcomePage = new WelcomePage(driver);
            Assert.assertTrue(welcomePage.isWelcomePageDisplayed(),
                    "Welcome page should be displayed");

            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // STEP 3: Verify Health Menu Page
            System.out.println("\nSTEP 3: Verify Health Menu Page");
            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            Assert.assertTrue(healthMenuPage.isHealthMenuPageDisplayed(),
                    "Health Menu page should be displayed");

            // STEP 4: Click Hamburger Menu
            System.out.println("\nSTEP 4: Click Hamburger Menu");
            // Assuming hamburger menu needs to be clicked to see Logout
            if (healthMenuPage.isHamburgerMenuButtonDisplayed()) {
                healthMenuPage.clickHamburgerMenuButton();
                waitForSeconds(2);
            } else {
                System.out.println("⚠ Warning: Hamburger menu not found/displayed");
            }

            // STEP 5: Click Logout and Confirm
            System.out.println("\nSTEP 5: Click Logout and Confirm");
            healthMenuPage.clickLogout();
            waitForSeconds(2);

            healthMenuPage.clickConfirmLogout();
            waitForSeconds(4);

            // STEP 6: Verify Redirection to Login
            System.out.println("\nSTEP 6: Verify Logout Success");
            boolean loggedOut = healthMenuPage.verifyLogoutSuccess();
            Assert.assertTrue(loggedOut,
                    "Should be redirected to login page after logout");

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Logout Flow Successful");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Helper method
    private void performLogin() {
        System.out.println("Performing login with credentials:");
        System.out.println("  Email: " + EMAIL);
        System.out.println("  Password: " + PASSWORD);
        System.out.println("  OTP: " + OTP);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        waitForSeconds(3);

        OTPVerificationPage otpPage = new OTPVerificationPage(driver);
        otpPage.verifyOTP(OTP);
        waitForSeconds(4);

        System.out.println("✓ Login completed\n");
    }
}
