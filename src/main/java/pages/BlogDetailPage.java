package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BlogDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//h1 | //h2[contains(@class, 'title')] | //*[contains(@class, 'blog-title')] | //*[contains(@class, 'article-title')]")
    private WebElement blogTitle;

    @FindBy(xpath = "//div[contains(@class, 'content')] | //article | //*[contains(@class, 'blog-content')] | //p")
    private List<WebElement> blogContent;

    @FindBy(xpath = "//*[contains(@class, 'author')] | //*[contains(text(), 'By')] | //*[contains(text(), 'Author')]")
    private WebElement authorInfo;

    @FindBy(xpath = "//*[contains(@class, 'date')] | //*[contains(@class, 'published')] | //time")
    private WebElement publishDate;

    @FindBy(xpath = "//img[contains(@class, 'featured') or contains(@class, 'banner') or contains(@class, 'cover')]")
    private WebElement featuredImage;

    @FindBy(xpath = "//button[contains(@aria-label, 'back') or contains(@class, 'back') or contains(text(), 'Back')]")
    private WebElement backButton;

    @FindBy(xpath = "//button[contains(@class, 'share')] | //*[contains(text(), 'Share')]")
    private WebElement shareButton;

    @FindBy(xpath = "//button[contains(@class, 'like')] | //*[contains(@aria-label, 'like')]")
    private WebElement likeButton;

    // Related blogs section
    @FindBy(xpath = "//*[contains(text(), 'Related') or contains(text(), 'More Articles')]")
    private WebElement relatedBlogsSection;

    @FindBy(xpath = "//div[contains(@class, 'related')] | //div[contains(@class, 'recommended')]")
    private List<WebElement> relatedBlogs;

    // Constructor
    public BlogDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isBlogDetailPageDisplayed() {
        try {
            Thread.sleep(2000);

            // Check if blog title is visible or content is loaded
            try {
                wait.until(ExpectedConditions.visibilityOf(blogTitle));
                System.out.println("✓ Blog detail page displayed (title visible)");
                return true;
            } catch (Exception e) {
                // Fallback: Check if we have blog content
                if (!blogContent.isEmpty() && blogContent.get(0).isDisplayed()) {
                    System.out.println("✓ Blog detail page displayed (content visible)");
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            System.err.println("Blog detail page not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getBlogTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(blogTitle));
            String title = blogTitle.getText();
            System.out.println("✓ Blog title: " + title);
            return title;
        } catch (Exception e) {
            System.err.println("Blog title not found: " + e.getMessage());
            return "";
        }
    }

    public String getBlogContent() {
        try {
            Thread.sleep(1000);
            StringBuilder content = new StringBuilder();

            for (WebElement paragraph : blogContent) {
                if (paragraph.isDisplayed() && !paragraph.getText().trim().isEmpty()) {
                    content.append(paragraph.getText()).append("\n");
                }
            }

            String fullContent = content.toString().trim();
            System.out.println("✓ Blog content length: " + fullContent.length() + " characters");
            return fullContent;
        } catch (Exception e) {
            System.err.println("Blog content not found: " + e.getMessage());
            return "";
        }
    }

    public boolean isBlogContentDisplayed() {
        try {
            Thread.sleep(1000);
            boolean hasContent = !blogContent.isEmpty() && blogContent.get(0).isDisplayed();
            System.out.println("✓ Blog content displayed: " + hasContent);
            return hasContent;
        } catch (Exception e) {
            System.err.println("Blog content check failed: " + e.getMessage());
            return false;
        }
    }

    public String getAuthorInfo() {
        try {
            wait.until(ExpectedConditions.visibilityOf(authorInfo));
            String author = authorInfo.getText();
            System.out.println("✓ Author info: " + author);
            return author;
        } catch (Exception e) {
            System.err.println("Author info not found: " + e.getMessage());
            return "";
        }
    }

    public boolean isAuthorDisplayed() {
        try {
            return authorInfo != null && authorInfo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPublishDate() {
        try {
            wait.until(ExpectedConditions.visibilityOf(publishDate));
            String date = publishDate.getText();
            System.out.println("✓ Publish date: " + date);
            return date;
        } catch (Exception e) {
            System.err.println("Publish date not found: " + e.getMessage());
            return "";
        }
    }

    public boolean isPublishDateDisplayed() {
        try {
            return publishDate != null && publishDate.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFeaturedImageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(featuredImage));
            System.out.println("✓ Featured image is displayed");
            return featuredImage.isDisplayed();
        } catch (Exception e) {
            System.err.println("Featured image not found: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backButton));
            Thread.sleep(500);
            backButton.click();
            System.out.println("✓ Back button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click back button: " + e.getMessage());
            throw new RuntimeException("Failed to click back button", e);
        }
    }

    public void clickShareButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(shareButton));
            Thread.sleep(500);
            shareButton.click();
            System.out.println("✓ Share button clicked");
        } catch (Exception e) {
            System.err.println("Share button not available: " + e.getMessage());
        }
    }

    public void clickLikeButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(likeButton));
            Thread.sleep(500);
            likeButton.click();
            System.out.println("✓ Like button clicked");
        } catch (Exception e) {
            System.err.println("Like button not available: " + e.getMessage());
        }
    }

    public boolean isRelatedBlogsSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(relatedBlogsSection));
            System.out.println("✓ Related blogs section displayed");
            return relatedBlogsSection.isDisplayed();
        } catch (Exception e) {
            System.err.println("Related blogs section not found: " + e.getMessage());
            return false;
        }
    }

    public int getRelatedBlogsCount() {
        try {
            Thread.sleep(1000);
            int count = relatedBlogs.size();
            System.out.println("✓ Related blogs count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Failed to get related blogs count: " + e.getMessage());
            return 0;
        }
    }

    public boolean verifyBlogDetailsLoaded() {
        System.out.println("\nVerifying blog details page elements...");

        boolean titleDisplayed = false;
        boolean contentDisplayed = false;

        try {
            titleDisplayed = blogTitle != null && blogTitle.isDisplayed();
            System.out.println("  - Title displayed: " + titleDisplayed);
        } catch (Exception e) {
            System.out.println("  - Title displayed: false");
        }

        try {
            contentDisplayed = !blogContent.isEmpty() && blogContent.get(0).isDisplayed();
            System.out.println("  - Content displayed: " + contentDisplayed);
        } catch (Exception e) {
            System.out.println("  - Content displayed: false");
        }

        // Author and date are optional, just log their status
        try {
            System.out.println("  - Author displayed: " + (authorInfo != null && authorInfo.isDisplayed()));
        } catch (Exception e) {
            System.out.println("  - Author displayed: false");
        }

        try {
            System.out.println("  - Date displayed: " + (publishDate != null && publishDate.isDisplayed()));
        } catch (Exception e) {
            System.out.println("  - Date displayed: false");
        }

        boolean verified = titleDisplayed || contentDisplayed;
        System.out.println("✓ Blog details verification: " + (verified ? "PASSED" : "FAILED"));
        return verified;
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
}
