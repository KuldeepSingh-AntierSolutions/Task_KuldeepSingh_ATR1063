package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class DocumentUploadFlowTest extends BaseTest {

    private static final String EMAIL = "laptop@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String FALLBACK_OTP = "000000";
    private static final String TEST_FILE_PATH = "C:\\Users\\user\\Downloads\\Samples\\Sample PHI data\\tentative_emr (1).pdf";

    @Test(priority = 1, description = "Complete Document Upload Flow with Important Notice Verification")
    public void testCompleteDocumentUploadFlow() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Complete Document Upload Flow");
        System.out.println("========================================\\n");

        try {
            // Login
            performLogin();

            // Navigate to Health Menu
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            // Navigate to My Health Information
            System.out.println("\\nSTEP 1: Navigate to My Health Information");
            navigateToMyHealthInformation();

            // Click Add button and create condition
            System.out.println("\\nSTEP 2: Create New Condition Folder");
            MyHealthInformationPage healthInfoPage = new MyHealthInformationPage(driver);
            healthInfoPage.clickAddButton();
            waitForSeconds(2);

            AddConditionDrawer addConditionDrawer = new AddConditionDrawer(driver);
            Assert.assertTrue(addConditionDrawer.isDrawerDisplayed(),
                "Add Condition drawer should be displayed");

            String conditionName = "Test Condition " + System.currentTimeMillis();
            addConditionDrawer.addCondition(conditionName);
            waitForSeconds(3);

            // Verify condition appears in list
            Assert.assertTrue(healthInfoPage.isConditionDisplayedInList(conditionName),
                "New condition should appear in the list");

            // Click on the condition folder
            System.out.println("\\nSTEP 3: Open Condition Folder");
            healthInfoPage.clickConditionFolder(conditionName);
            waitForSeconds(3);

            // Verify condition folder page
            ConditionFolderPage folderPage = new ConditionFolderPage(driver);
            Assert.assertTrue(folderPage.isConditionFolderPageDisplayed(),
                "Condition folder page should be displayed");
            Assert.assertTrue(folderPage.verifyAllCardsDisplayed(),
                "All cards should be displayed");

            // Click Records card
            System.out.println("\\nSTEP 4: Click Records Card");
            folderPage.clickRecordsCard();
            waitForSeconds(3);

            // Verify My Records page
            MyRecordsPage recordsPage = new MyRecordsPage(driver);
            Assert.assertTrue(recordsPage.isMyRecordsPageDisplayed(),
                "My Records page should be displayed");
            Assert.assertTrue(recordsPage.isEmptyStateDisplayed(),
                "Empty state should be displayed");

            // Click + button
            System.out.println("\\nSTEP 5: Click Add/Upload Button");
            recordsPage.clickAddButton();
            waitForSeconds(2);

            // Select Medical Documents
            System.out.println("\\nSTEP 6: Select Medical Documents");
            UploadDocumentPage uploadPage = new UploadDocumentPage(driver);
            Assert.assertTrue(uploadPage.isDocumentTypeSelectionDisplayed(),
                "Document type selection should be displayed");

            uploadPage.selectMedicalDocuments();
            uploadPage.clickContinueButton();
            waitForSeconds(2);

            // Verify upload options page
            Assert.assertTrue(uploadPage.isUploadDocumentPageDisplayed(),
                "Upload Document page should be displayed");

            // Select From Device
            System.out.println("\\nSTEP 7: Select From Device Option");
            uploadPage.selectFromDeviceOption();
            waitForSeconds(2);

            // Verify Browse button
            Assert.assertTrue(uploadPage.isBrowseButtonDisplayed(),
                "Browse button should be displayed");

            // Upload file
            System.out.println("\\nSTEP 8: Upload File");
            uploadPage.uploadFile(TEST_FILE_PATH);
            waitForSeconds(2);

            // Click Confirm & Continue
            System.out.println("\\nSTEP 9: Click Confirm & Continue");
            uploadPage.clickConfirmAndContinue();
            waitForSeconds(3);

            // Verify Important Notice on Processing Modal
            System.out.println("\\nSTEP 10: Verify Important Notice Section");
            DocumentProcessingModal processingModal = new DocumentProcessingModal(driver);
            
            Assert.assertTrue(processingModal.isProcessingModalDisplayed(),
                "Processing modal should be displayed");
            
            Assert.assertTrue(processingModal.isAlmostReadyTextDisplayed(),
                "'Almost Ready' text should be displayed");
            
            Assert.assertTrue(processingModal.isImportantNoticeDisplayed(),
                "Important Notice section should be displayed");

            boolean noticeVerified = processingModal.verifyImportantNoticeSection();
            Assert.assertTrue(noticeVerified,
                "Important Notice section verification should pass");

            // Wait for processing to complete
            System.out.println("\\nSTEP 11: Wait for Processing to Complete");
            processingModal.waitForProcessingToComplete();

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Document Upload Flow Successful");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Verify Add Condition Functionality")
    public void testAddConditionFunctionality() {
        System.out.println("\\n========================================");
        System.out.println("TEST: Add Condition Functionality");
        System.out.println("========================================\\n");

        try {
            performLogin();
            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            navigateToMyHealthInformation();

            MyHealthInformationPage healthInfoPage = new MyHealthInformationPage(driver);
            healthInfoPage.clickAddButton();
            waitForSeconds(2);

            AddConditionDrawer addConditionDrawer = new AddConditionDrawer(driver);
            Assert.assertTrue(addConditionDrawer.isDrawerDisplayed(),
                "Add Condition drawer should be displayed");
            Assert.assertEquals(addConditionDrawer.getDrawerTitle(), "Add Condition",
                "Drawer title should be 'Add Condition'");

            String conditionName = "Automated Test Condition";
            addConditionDrawer.addCondition(conditionName);
            waitForSeconds(3);

            Assert.assertTrue(healthInfoPage.isConditionDisplayedInList(conditionName),
                "Condition should be displayed in the list");

            System.out.println("\\n========================================");
            System.out.println("✅ TEST PASSED: Add Condition Verified");
            System.out.println("========================================\\n");

        } catch (Exception e) {
            System.err.println("\\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Helper methods
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

    private void navigateToMyHealthInformation() {
        org.openqa.selenium.interactions.Actions actions = 
            new org.openqa.selenium.interactions.Actions(driver);
        actions.moveByOffset(450, 540).click().perform();
        actions.moveByOffset(-450, -540).perform();
        waitForSeconds(3);

        driver.findElement(org.openqa.selenium.By.xpath(
            "//button[.//img[@alt='right arrow']]")).click();
        waitForSeconds(3);
    }
}