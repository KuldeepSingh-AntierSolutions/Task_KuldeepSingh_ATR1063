package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.BaseTest;

public class ViewBlogFlowTest extends BaseTest {

    private static final String EMAIL = "ten@yopmail.com";
    private static final String PASSWORD = "Admin@123";
    private static final String OTP = "000000";

    @Test(priority = 1, description = "Complete flow: Login > Exploring Solo > Blogs and Articles > View Blog")
    public void testViewBlogCompleteFlow() {
        System.out.println("\n========================================");
        System.out.println("TEST: View Blog Complete Flow");
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

            // STEP 4: Click on Blogs and Articles from circular menu
            System.out.println("\nSTEP 4: Click on Blogs and Articles");
            healthMenuPage.clickBlogsAndArticles();
            waitForSeconds(3);

            // STEP 5: Verify Blogs Page
            System.out.println("\nSTEP 5: Verify Blogs Listing Page");
            BlogsPage blogsPage = new BlogsPage(driver);
            Assert.assertTrue(blogsPage.isBlogsPageDisplayed(),
                    "Blogs listing page should be displayed");

            int blogsCount = blogsPage.getBlogsCount();
            System.out.println("Total blogs available: " + blogsCount);

            if (blogsCount > 0) {
                // Get all blog titles
                java.util.List<String> blogTitles = blogsPage.getAllBlogTitles();
                System.out.println("Blog titles found: " + blogTitles.size());

                // STEP 6: Click on first blog
                System.out.println("\nSTEP 6: Click on First Blog");
                blogsPage.clickFirstBlog();
                waitForSeconds(3);

                // STEP 7: Verify Blog Detail Page
                System.out.println("\nSTEP 7: Verify Blog Detail Page");
                BlogDetailPage blogDetailPage = new BlogDetailPage(driver);
                Assert.assertTrue(blogDetailPage.isBlogDetailPageDisplayed(),
                        "Blog detail page should be displayed");

                // Verify blog details are loaded
                boolean detailsLoaded = blogDetailPage.verifyBlogDetailsLoaded();
                Assert.assertTrue(detailsLoaded,
                        "Blog details should be loaded correctly");

                // Get blog information
                String blogTitle = blogDetailPage.getBlogTitle();
                System.out.println("✓ Viewing blog: " + blogTitle);

                boolean contentDisplayed = blogDetailPage.isBlogContentDisplayed();
                Assert.assertTrue(contentDisplayed,
                        "Blog content should be displayed");

                System.out.println("\n========================================");
                System.out.println("✅ TEST PASSED: Blog Viewing Flow Successful");
                System.out.println("========================================\n");
            } else {
                System.out.println("⚠ Warning: No blogs available to view");
                Assert.fail("No blogs available on the platform");
            }

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Verify blogs listing page elements")
    public void testBlogsListingPage() {
        System.out.println("\n========================================");
        System.out.println("TEST: Blogs Listing Page Elements");
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

            // Verify blogs page
            BlogsPage blogsPage = new BlogsPage(driver);
            Assert.assertTrue(blogsPage.isBlogsPageDisplayed(),
                    "Blogs page should be displayed");

            // Get blogs count
            int blogsCount = blogsPage.getBlogsCount();
            System.out.println("\nBlogs available: " + blogsCount);
            Assert.assertTrue(blogsCount > 0, "At least one blog should be available");

            // Get all titles
            java.util.List<String> titles = blogsPage.getAllBlogTitles();
            System.out.println("Blog titles retrieved: " + titles.size());

            for (int i = 0; i < Math.min(titles.size(), 5); i++) {
                System.out.println("  " + (i + 1) + ". " + titles.get(i));
            }

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Blogs Listing Verified");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Verify blog detail page elements")
    public void testBlogDetailPageElements() {
        System.out.println("\n========================================");
        System.out.println("TEST: Blog Detail Page Elements");
        System.out.println("========================================\n");

        try {
            // Login and navigate to blog detail
            performLogin();

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            healthMenuPage.clickBlogsAndArticles();
            waitForSeconds(3);

            BlogsPage blogsPage = new BlogsPage(driver);
            blogsPage.clickFirstBlog();
            waitForSeconds(3);

            // Verify blog detail page elements
            BlogDetailPage blogDetailPage = new BlogDetailPage(driver);
            Assert.assertTrue(blogDetailPage.isBlogDetailPageDisplayed(),
                    "Blog detail page should be displayed");

            System.out.println("\nVerifying blog detail page elements:");

            // Check title
            String title = blogDetailPage.getBlogTitle();
            Assert.assertFalse(title.isEmpty(), "Blog title should not be empty");
            System.out.println("✓ Title: " + title);

            // Check content
            boolean hasContent = blogDetailPage.isBlogContentDisplayed();
            Assert.assertTrue(hasContent, "Blog content should be displayed");
            System.out.println("✓ Content is displayed");

            // Check optional elements
            if (blogDetailPage.isAuthorDisplayed()) {
                String author = blogDetailPage.getAuthorInfo();
                System.out.println("✓ Author: " + author);
            }

            if (blogDetailPage.isPublishDateDisplayed()) {
                String date = blogDetailPage.getPublishDate();
                System.out.println("✓ Publish date: " + date);
            }

            if (blogDetailPage.isFeaturedImageDisplayed()) {
                System.out.println("✓ Featured image is displayed");
            }

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Blog Detail Elements Verified");
            System.out.println("========================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(priority = 4, description = "Navigate back from blog detail to blogs list")
    public void testNavigateBackFromBlog() {
        System.out.println("\n========================================");
        System.out.println("TEST: Navigate Back from Blog Detail");
        System.out.println("========================================\n");

        try {
            // Login and navigate to blog detail
            performLogin();

            WelcomePage welcomePage = new WelcomePage(driver);
            welcomePage.clickExploringSoloButton();
            waitForSeconds(4);

            HealthMenuPage healthMenuPage = new HealthMenuPage(driver);
            healthMenuPage.clickBlogsAndArticles();
            waitForSeconds(3);

            BlogsPage blogsPage = new BlogsPage(driver);
            blogsPage.clickFirstBlog();
            waitForSeconds(3);

            // Verify we're on blog detail page
            BlogDetailPage blogDetailPage = new BlogDetailPage(driver);
            Assert.assertTrue(blogDetailPage.isBlogDetailPageDisplayed(),
                    "Blog detail page should be displayed");

            // Click back button
            System.out.println("\nClicking back button...");
            blogDetailPage.clickBackButton();
            waitForSeconds(2);

            // Verify we're back on blogs listing page
            Assert.assertTrue(blogsPage.isBlogsPageDisplayed(),
                    "Should navigate back to blogs listing page");

            System.out.println("\n========================================");
            System.out.println("✅ TEST PASSED: Back Navigation Successful");
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
