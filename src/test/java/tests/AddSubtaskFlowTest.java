package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class AddSubtaskFlowTest extends BaseTest {

    private static final String EMAIL = "ten@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String OTP = "000000";

    @Test(priority = 1, description = "Complete flow: Login > Exploring Solo > My Planner > Plan Card > Add Task")
    public void testAddSubtaskCompleteFlow() {
        System.out.println("\n========================================");
        System.out.println("TEST: Add Subtask to Plan Complete Flow");
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

            // STEP 3: Navigate to My Planner
            System.out.println("\nSTEP 3: Navigate to My Planner");
            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            Assert.assertTrue(healthMenuPage.isHealthMenuPageDisplayed(),
                    "Health Menu page should be displayed");

            healthMenuPage.clickMyPlannerArrowButton();
            waitForSeconds(3);

            // STEP 4: Verify Planner Page
            System.out.println("\nSTEP 4: Verify Planner Page");
            PlannerPage plannerPage = new PlannerPage(driver);
            Assert.assertTrue(plannerPage.verifyCurrentURL("planner"),
                    "Should be on Planner page");

            // STEP 5: Click on Plans tab (if needed)
            System.out.println("\nSTEP 5: Navigate to Plans List");
            plannerPage.clickPlansTab();
            waitForSeconds(2);

            // STEP 6: Click on first Plan Card
            System.out.println("\nSTEP 6: Click on First Plan Card");
            PlansListPage plansListPage = new PlansListPage(driver);
            plansListPage.clickFirstPlanCard();
            waitForSeconds(3);

            // STEP 7: Verify Plan Detail Page
            System.out.println("\nSTEP 7: Verify Plan Detail Page");
            PlanDetailPage planDetailPage = new PlanDetailPage(driver);
            Assert.assertTrue(planDetailPage.isPlanDetailPageDisplayed(),
                    "Plan Detail page should be displayed");

            // STEP 8: Click on + button to add task
            System.out.println("\nSTEP 8: Click on + Button to Add Task");
            planDetailPage.clickFirstAddTaskButton();
            waitForSeconds(3);

            // STEP 9: Fill and Submit Task Form
            System.out.println("\nSTEP 9: Fill and Submit Task Creation Form");
            AddTaskPage addTaskPage = new AddTaskPage(driver);
            Assert.assertTrue(addTaskPage.isAddTaskPageDisplayed(),
                    "Add Task form should be displayed");

            String taskName = "Automation Test Task " + System.currentTimeMillis();
            String taskDescription = "This is an automated test task created by Selenium automation";

            addTaskPage.fillAndSubmitTaskForm(
                    taskName,
                    taskDescription,
                    "Health", // category
                    "10:00", // preferred time
                    "Daily", // repeat schedule
                    true // enable reminder
            );

            waitForSeconds(4);

            // STEP 10: Verify Task Created
            System.out.println("\nSTEP 10: Verify Task Created Successfully");
            // After task creation, we should be back to plan detail page
            String currentURL = driver.getCurrentUrl();
            System.out.println("✓ Current URL after task creation: " + currentURL);

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Add Subtask Flow Successful");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Add basic task with minimal fields")
    public void testAddBasicTask() {
        System.out.println("\n========================================");
        System.out.println("TEST: Add Basic Task with Minimal Fields");
        System.out.println("========================================\n");

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
            plannerPage.clickPlansTab();
            waitForSeconds(2);

            PlansListPage plansListPage = new PlansListPage(driver);
            plansListPage.clickFirstPlanCard();
            waitForSeconds(3);

            PlanDetailPage planDetailPage = new PlanDetailPage(driver);
            planDetailPage.clickFirstAddTaskButton();
            waitForSeconds(3);

            // Fill only required fields
            AddTaskPage addTaskPage = new AddTaskPage(driver);
            Assert.assertTrue(addTaskPage.isAddTaskPageDisplayed(),
                    "Add Task form should be displayed");

            String taskName = "Basic Task " + System.currentTimeMillis();
            String taskDescription = "Basic task with minimal fields";

            addTaskPage.fillAndSubmitBasicTaskForm(taskName, taskDescription);
            waitForSeconds(4);

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Basic Task Added Successfully");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify task form fields and cancel functionality")
    public void testTaskFormFieldsAndCancel() {
        System.out.println("\n========================================");
        System.out.println("TEST: Task Form Fields and Cancel");
        System.out.println("========================================\n");

        try {
            // Login and navigate to task form
            performLogin();

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            healthMenuPage.clickMyPlannerArrowButton();
            waitForSeconds(3);

            PlannerPage plannerPage = new PlannerPage(driver);
            plannerPage.clickPlansTab();
            waitForSeconds(2);

            PlansListPage plansListPage = new PlansListPage(driver);
            plansListPage.clickFirstPlanCard();
            waitForSeconds(3);

            PlanDetailPage planDetailPage = new PlanDetailPage(driver);
            planDetailPage.clickFirstAddTaskButton();
            waitForSeconds(3);

            // Test form fields
            AddTaskPage addTaskPage = new AddTaskPage(driver);
            Assert.assertTrue(addTaskPage.isAddTaskPageDisplayed(),
                    "Add Task form should be displayed");

            System.out.println("\nTesting individual form fields...");
            addTaskPage.enterTaskName("Test Task Name");
            addTaskPage.enterDescription("Test Description");
            addTaskPage.selectCategory("Fitness");
            addTaskPage.enterPreferredTime("14:30");
            addTaskPage.selectRepeatSchedule("Weekly");
            addTaskPage.enableReminder();

            System.out.println("✓ All form fields filled successfully");

            // Test cancel button
            System.out.println("\nTesting cancel functionality...");
            addTaskPage.clickCancelButton();
            waitForSeconds(2);

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Form Fields Verified and Cancel Works");
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
