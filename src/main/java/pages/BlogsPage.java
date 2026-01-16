package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BlogsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//h1[contains(text(), 'Blogs')] | //h2[contains(text(), 'Blogs')] | //*[contains(text(), 'Blogs and Articles')]")
    private WebElement pageHeading;

    @FindBy(xpath = "//button[contains(@aria-label, 'back') or contains(@class, 'back')]")
    private WebElement backButton;

    // Blog Cards
    @FindBy(xpath = "//div[contains(@class, 'blog-card')] | //div[contains(@class, 'article-card')] | //article")
    private List<WebElement> blogCards;

    // Blog Titles
    @FindBy(xpath = "//h3 | //h4 | //*[contains(@class, 'blog-title')] | //*[contains(@class, 'article-title')]")
    private List<WebElement> blogTitles;

    // Search Elements (if available)
    @FindBy(xpath = "//input[@placeholder='Search' or contains(@placeholder, 'Search blogs')]")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(@aria-label, 'search') or contains(@class, 'search')]")
    private WebElement searchButton;

    // Constructor
    public BlogsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isBlogsPageDisplayed() {
        try {
            Thread.sleep(2000);
            // Check if we're on blogs page by URL or visible elements
            String currentURL = driver.getCurrentUrl();
            if (currentURL.contains("blog") || currentURL.contains("article")) {
                System.out.println("✓ Blogs page displayed (verified by URL)");
                return true;
            }

            // Try to find page heading
            try {
                wait.until(ExpectedConditions.visibilityOf(pageHeading));
                System.out.println("✓ Blogs page displayed (verified by heading)");
                return true;
            } catch (Exception e) {
                // Check if blog cards are visible
                if (!blogCards.isEmpty()) {
                    System.out.println("✓ Blogs page displayed (verified by blog cards)");
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            System.err.println("Blogs page not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getPageHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeading));
            String heading = pageHeading.getText();
            System.out.println("✓ Page heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Page heading not found: " + e.getMessage());
            return "";
        }
    }

    public int getBlogsCount() {
        try {
            Thread.sleep(2000);
            int count = blogCards.size();
            System.out.println("✓ Total blogs count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Failed to get blogs count: " + e.getMessage());
            return 0;
        }
    }

    public List<String> getAllBlogTitles() {
        try {
            Thread.sleep(2000);
            List<String> titles = new java.util.ArrayList<>();
            for (WebElement title : blogTitles) {
                if (title.isDisplayed()) {
                    titles.add(title.getText());
                }
            }
            System.out.println("✓ Found " + titles.size() + " blog titles");
            return titles;
        } catch (Exception e) {
            System.err.println("Failed to get blog titles: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void clickFirstBlog() {
        try {
            Thread.sleep(2000);

            // Strategy 1: Try clicking first blog card
            WebElement firstBlog = null;
            try {
                firstBlog = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "(//div[contains(@class, 'blog-card')] | //div[contains(@class, 'article-card')] | //article)[1]")));
            } catch (Exception e1) {
                // Strategy 2: Try clicking first clickable heading/title
                try {
                    firstBlog = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("(//h3 | //h4 | //*[contains(@class, 'blog-title')])[1]")));
                } catch (Exception e2) {
                    // Strategy 3: Try first link/button in blog area
                    firstBlog = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("(//a[contains(@href, 'blog')] | //a[contains(@href, 'article')])[1]")));
                }
            }

            if (firstBlog != null) {
                Thread.sleep(500);
                firstBlog.click();
                System.out.println("✓ First blog clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click first blog: " + e.getMessage());
            throw new RuntimeException("Failed to click first blog", e);
        }
    }

    public void clickBlogByTitle(String blogTitle) {
        try {
            Thread.sleep(2000);

            // Find and click blog by title
            WebElement blogElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(), '" + blogTitle
                            + "')]/ancestor::*[contains(@class, 'card') or contains(@class, 'blog') or contains(@class, 'article')] | "
                            +
                            "//*[contains(text(), '" + blogTitle + "')]")));

            Thread.sleep(500);
            blogElement.click();
            System.out.println("✓ Clicked on blog: " + blogTitle);
        } catch (Exception e) {
            System.err.println("Failed to click blog by title: " + e.getMessage());
            throw new RuntimeException("Failed to click blog by title", e);
        }
    }

    public void clickBlogByIndex(int index) {
        try {
            Thread.sleep(2000);

            if (index < 0 || index >= blogCards.size()) {
                throw new IllegalArgumentException("Blog index out of range: " + index);
            }

            WebElement blog = blogCards.get(index);
            wait.until(ExpectedConditions.elementToBeClickable(blog));
            Thread.sleep(500);
            blog.click();
            System.out.println("✓ Clicked on blog at index: " + index);
        } catch (Exception e) {
            System.err.println("Failed to click blog by index: " + e.getMessage());
            throw new RuntimeException("Failed to click blog by index", e);
        }
    }

    public void searchBlog(String searchTerm) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(searchInput));
            searchInput.clear();
            Thread.sleep(500);
            searchInput.sendKeys(searchTerm);
            System.out.println("✓ Searched for: " + searchTerm);

            if (searchButton != null && searchButton.isDisplayed()) {
                searchButton.click();
                System.out.println("✓ Search button clicked");
            }
        } catch (Exception e) {
            System.err.println("Search functionality not available: " + e.getMessage());
        }
    }

    public boolean verifyCurrentURL(String expectedURLPart) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedURLPart));
            String currentURL = driver.getCurrentUrl();
            System.out.println("✓ Current URL: " + currentURL);
            return currentURL.contains(expectedURLPart);
        } catch (Exception e) {
            System.err.println("URL verification failed: " + e.getMessage());
            return false;
        }
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void clickBackButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backButton));
            backButton.click();
            System.out.println("✓ Back button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click back button: " + e.getMessage());
        }
    }

    public void clickMyLibraryTab() {
        try {
            Thread.sleep(2000);

            // Try multiple strategies to find and click My Library tab
            WebElement myLibraryTab = null;

            try {
                // Strategy 1: Find by text "My Library"
                myLibraryTab = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(), 'My Library')] | //*[contains(text(), 'My Library')]")));
            } catch (Exception e1) {
                try {
                    // Strategy 2: Find by aria-label or role
                    myLibraryTab = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[contains(@aria-label, 'My Library') or @role='tab' and contains(., 'Library')]")));
                } catch (Exception e2) {
                    // Strategy 3: Find tab by class
                    myLibraryTab = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class, 'tab')]//*[contains(text(), 'Library')]")));
                }
            }

            if (myLibraryTab != null) {
                Thread.sleep(500);
                myLibraryTab.click();
                System.out.println("✓ My Library tab clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click My Library tab: " + e.getMessage());
            throw new RuntimeException("Failed to click My Library tab", e);
        }
    }
}
