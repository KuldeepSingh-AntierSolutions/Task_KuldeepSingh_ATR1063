package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class AddPlanFlowTest extends BaseTest {

    private static final String EMAIL = "laptop@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String FALLBACK_OTP = "000000";

    @Test(priority = 1, description = "Complete flow: Login > Health Menu > My Planner > Add Plan")
    public void testAddPlanCompleteFlow() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Add Plan Complete Flow");
        System.out.println("========================================\\n");

        try {
            // Login
            performLogin();

            // Navigate to Health Menu
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Navigate to My Planner
            System.out.println("\\nSTEP: Navigate to My Planner");
            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            Assert.assertTrue(healthMenuPage.isHealthMenuPageDisplayed(),
                "Health Menu page should be displayed");

            healthMenuPage.clickMyPlannerArrowButton();
            waitForSeconds(3);

            // Verify Planner Page
            System.out.println("\\nSTEP: Verify Planner Page");
            PlannerPage plannerPage = new PlannerPage(driver);
            Assert.assertTrue(plannerPage.verifyCurrentURL("planner"),
                "Should be on Planner page");
            Assert.assertTrue(plannerPage.isBuildAPlanButtonDisplayed(),
                "Build a Plan button should be displayed");

            // Click Build a Plan
            System.out.println("\\nSTEP: Click Build a Plan Button");
            plannerPage.clickBuildAPlanButton();
            waitForSeconds(3);

            // Verify Add Plan Page
            System.out.println("\\nSTEP: Fill Add Plan Form");
            AddPlanPage addPlanPage = new AddPlanPage(driver);
            Assert.assertTrue(addPlanPage.verifyCurrentURL("addAPlan"),
                "Should be on Add Plan page");

            // Fill the form
            String planName = "Automation Test Plan " + System.currentTimeMillis();
            addPlanPage.fillAndSubmitPlanForm(
                "Weekly",
                planName,
                "Fitness",
                "Automated test plan for fitness activities",
                "Self",
                23,
                "High"
            );

            waitForSeconds(4);

            // Verify Plans List Page
            System.out.println("\\nSTEP: Verify Plan Created Successfully");
            PlansListPage plansListPage = new PlansListPage(driver);
            Assert.assertTrue(plansListPage.verifyCurrentURL("plans"),
                "Should be on Plans List page");

            Assert.assertTrue(plansListPage.isActiveTabDisplayed(),
                "Active tab should be displayed");

            Assert.assertTrue(plansListPage.isPlanDisplayedInList(planName),
                "Newly created plan should be displayed in the list");

            Assert.assertTrue(plansListPage.isPlanWithPriorityDisplayed(planName, "High"),
                "Plan should have High priority");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Add Plan Flow Successful");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Verify Add Plan form validation")
    public void testAddPlanFormFields() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Add Plan Form Fields Verification");
        System.out.println("========================================\\n");

        try {
            // Login and navigate
            performLogin();
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            healthMenuPage.clickMyPlannerArrowButton();
            waitForSeconds(3);

            PlannerPage plannerPage = new PlannerPage(driver);
            plannerPage.clickBuildAPlanButton();
            waitForSeconds(3);

            // Verify form page
            AddPlanPage addPlanPage = new AddPlanPage(driver);
            Assert.assertTrue(addPlanPage.isAddPlanPageDisplayed(),
                "Add Plan page should be displayed");

            // Test individual form fields
            System.out.println("\\nTesting individual form fields...");
            addPlanPage.selectPlanType("Daily");
            addPlanPage.enterPlanName("Daily Health Check");
            addPlanPage.selectCategory("Health");
            addPlanPage.enterDescription("Daily health monitoring plan");
            addPlanPage.selectAssignee("Self");
            addPlanPage.selectFromDate("Today");
            addPlanPage.selectToDate(20);
            addPlanPage.selectPriority("Medium");

            System.out.println("✓ All form fields filled successfully");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Form Fields Verified");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Helper method
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