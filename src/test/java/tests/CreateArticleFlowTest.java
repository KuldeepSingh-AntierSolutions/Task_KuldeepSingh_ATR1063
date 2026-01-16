package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class CreateArticleFlowTest extends BaseTest {

    private static final String EMAIL = "ten@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String OTP = "000000";

    @Test(priority = 1, description = "Complete flow: Login > Exploring Solo > Blogs & Articles > My Library > Create Folder > Create Article")
    public void testCreateArticleCompleteFlow() {
        System.out.println("\n========================================");
        System.out.println("TEST: Create Article Complete Flow");
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

            // STEP 4: Click on Blogs and Articles
            System.out.println("\nSTEP 4: Click on Blogs and Articles");
            healthMenuPage.clickBlogsAndArticles();
            waitForSeconds(3);

            // STEP 5: Navigate to My Library Tab
            System.out.println("\nSTEP 5: Navigate to My Library Tab");
            BlogsPage blogsPage = new BlogsPage(driver);
            blogsPage.clickMyLibraryTab();
            waitForSeconds(3);

            // STEP 6: Verify My Library Page
            System.out.println("\nSTEP 6: Verify My Library Page");
            MyLibraryPage myLibraryPage = new MyLibraryPage(driver);
            Assert.assertTrue(myLibraryPage.isMyLibraryPageDisplayed(),
                    "My Library page should be displayed");

            // STEP 7: Create a New Folder
            System.out.println("\nSTEP 7: Create a New Folder");
            String folderName = "Test Folder " + System.currentTimeMillis();
            myLibraryPage.clickAddFolderButton();
            waitForSeconds(2);

            AddFolderModal addFolderModal = new AddFolderModal(driver);
            Assert.assertTrue(addFolderModal.isModalDisplayed(),
                    "Add folder modal should be displayed");

            addFolderModal.createFolder(folderName);
            waitForSeconds(3);

            // STEP 8: Verify folder is created and open it
            System.out.println("\nSTEP 8: Verify Folder Created and Open It");
            Assert.assertTrue(myLibraryPage.isFolderDisplayed(folderName),
                    "Folder should be displayed in the list");

            myLibraryPage.clickFolderByName(folderName);
            waitForSeconds(3);

            // STEP 9: Create Manual Article
            System.out.println("\nSTEP 9: Create Manual Article");
            AddArticlePage addArticlePage = new AddArticlePage(driver);

            String articleTitle = "Test Article " + System.currentTimeMillis();
            String articleDescription = "This is an automated test article created by Selenium automation framework";

            try {
                addArticlePage.createManualArticle(articleTitle, articleDescription);
                waitForSeconds(4);
            } catch (Exception e) {
                System.err.println("Error during article creation: " + e.getMessage());
                // Take screenshot or additional debug steps if needed
                throw e;
            }

            // STEP 10: Verify article is created
            System.out.println("\nSTEP 10: Verify Article Created Successfully");
            boolean articleDisplayed = addArticlePage.isArticleDisplayed(articleTitle);
            Assert.assertTrue(articleDisplayed,
                    "Article should be displayed in the folder");

            int articlesCount = addArticlePage.getArticlesCount();
            System.out.println("Total articles in folder: " + articlesCount);
            Assert.assertTrue(articlesCount > 0, "At least one article should be present");

            // STEP 11: View the created article
            System.out.println("\nSTEP 11: View the Created Article");
            addArticlePage.clickArticleByTitle(articleTitle);
            waitForSeconds(3);

            System.out.println("✓ Article viewed successfully");

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Article Creation Flow Successful");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Test folder creation only")
    public void testFolderCreation() {
        System.out.println("\n========================================");
        System.out.println("TEST: Folder Creation");
        System.out.println("========================================\n");

        try {
            // Login and navigate
            performLogin();

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            healthMenuPage.clickBlogsAndArticles();
            waitForSeconds(3);

            BlogsPage blogsPage = new BlogsPage(driver);
            blogsPage.clickMyLibraryTab();
            waitForSeconds(3);

            MyLibraryPage myLibraryPage = new MyLibraryPage(driver);
            Assert.assertTrue(myLibraryPage.isMyLibraryPageDisplayed(),
                    "My Library page should be displayed");

            // Create folder
            System.out.println("\nCreating test folder...");
            String folderName = "Automation Folder " + System.currentTimeMillis();
            myLibraryPage.clickAddFolderButton();
            waitForSeconds(2);

            AddFolderModal addFolderModal = new AddFolderModal(driver);
            addFolderModal.createFolder(folderName);
            waitForSeconds(3);

            // Verify folder creation
            Assert.assertTrue(myLibraryPage.isFolderDisplayed(folderName),
                    "Folder should be displayed after creation");

            System.out.println("✓ Folder created successfully: " + folderName);

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Folder Created Successfully");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify My Library page elements")
    public void testMyLibraryPageElements() {
        System.out.println("\n========================================");
        System.out.println("TEST: My Library Page Elements");
        System.out.println("========================================\n");

        try {
            // Login and navigate
            performLogin();

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            healthMenuPage.clickBlogsAndArticles();
            waitForSeconds(3);

            BlogsPage blogsPage = new BlogsPage(driver);
            blogsPage.clickMyLibraryTab();
            waitForSeconds(3);

            // Verify My Library page
            MyLibraryPage myLibraryPage = new MyLibraryPage(driver);
            Assert.assertTrue(myLibraryPage.isMyLibraryPageDisplayed(),
                    "My Library page should be displayed");

            System.out.println("\nVerifying My Library page elements:");

            // Get folders count
            int foldersCount = myLibraryPage.getFoldersCount();
            System.out.println("  - Total folders: " + foldersCount);

            // Get folder names if any
            if (foldersCount > 0) {
                java.util.List<String> folderNames = myLibraryPage.getAllFolderNames();
                System.out.println("  - Folder names: " + folderNames);
            } else {
                System.out.println("  - No folders currently exist");
            }

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: My Library Page Verified");
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
