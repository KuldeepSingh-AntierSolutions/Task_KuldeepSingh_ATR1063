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

public class AddArticlePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Add Article Button (+ icon inside folder)
    @FindBy(xpath = "//button[contains(@aria-label, 'add') or contains(text(), '+') or .//img[contains(@alt, 'add')]]")
    private List<WebElement> addArticleButtons;

    // Manual Article Option
    @FindBy(xpath = "//*[contains(text(), 'Manual Article') or contains(text(), 'Manual') or contains(text(), 'Create Article')]")
    private WebElement manualArticleOption;

    // Article Form Elements
    @FindBy(xpath = "//input[@placeholder='Title' or @name='title' or @id='title' or contains(@placeholder, 'article title')]")
    private WebElement titleInput;

    @FindBy(xpath = "//textarea[@placeholder='Description' or @name='description' or @id='description'] | //input[@placeholder='Description' or @name='description']")
    private WebElement descriptionInput;

    @FindBy(xpath = "//button[contains(text(), 'Done') or contains(text(), 'Save') or contains(text(), 'Create') or @type='submit']")
    private WebElement doneButton;

    @FindBy(xpath = "//button[contains(text(), 'Cancel') or contains(@aria-label, 'cancel')]")
    private WebElement cancelButton;

    // Article heading/title in the list
    @FindBy(xpath = "//h3 | //h4 | //*[contains(@class, 'article-title')] | //*[contains(@class, 'article-name')]")
    private List<WebElement> articleTitles;

    // Articles in folder
    @FindBy(xpath = "//div[contains(@class, 'article')] | //li[contains(@class, 'article')]")
    private List<WebElement> articles;

    // Constructor
    public AddArticlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public void clickAddArticleButton() {
        try {
            Thread.sleep(2000);

            // Find and click the + button to add article
            WebElement addButton = null;

            try {
                // Strategy 1: Find + button inside folder
                addButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[contains(text(), '+') or contains(@aria-label, 'add article') or contains(@aria-label, 'Add article')]")));
            } catch (Exception e1) {
                try {
                    // Strategy 2: Find first clickable add button
                    if (!addArticleButtons.isEmpty()) {
                        addButton = addArticleButtons.get(0);
                        wait.until(ExpectedConditions.elementToBeClickable(addButton));
                    }
                } catch (Exception e2) {
                    // Strategy 3: Generic add button
                    addButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[.//img[contains(@alt, 'add')] or contains(@class, 'add')]")));
                }
            }

            if (addButton != null) {
                Thread.sleep(500);
                addButton.click();
                System.out.println("✓ Add article button clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click add article button: " + e.getMessage());
            throw new RuntimeException("Failed to click add article button", e);
        }
    }

    public void selectManualArticle() {
        try {
            Thread.sleep(2000);

            // Find and click Manual Article option
            WebElement manualOption = null;

            try {
                manualOption = wait.until(ExpectedConditions.elementToBeClickable(manualArticleOption));
            } catch (Exception e) {
                // Fallback: try to find by different text variations
                manualOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(), 'Manual')] | //*[contains(text(), 'Manual Article')]")));
            }

            if (manualOption != null) {
                Thread.sleep(500);
                manualOption.click();
                System.out.println("✓ Manual Article option selected");
            }
        } catch (Exception e) {
            System.err.println("Failed to select Manual Article: " + e.getMessage());
            throw new RuntimeException("Failed to select Manual Article", e);
        }
    }

    public boolean isArticleFormDisplayed() {
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(titleInput));
            System.out.println("✓ Article form displayed");
            return titleInput.isDisplayed();
        } catch (Exception e) {
            System.err.println("Article form not displayed: " + e.getMessage());
            return false;
        }
    }

    public void enterArticleTitle(String title) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(titleInput));
            titleInput.clear();
            Thread.sleep(500);
            titleInput.sendKeys(title);
            System.out.println("✓ Article title entered: " + title);
        } catch (Exception e) {
            System.err.println("Failed to enter article title: " + e.getMessage());
            throw new RuntimeException("Failed to enter article title", e);
        }
    }

    public void enterArticleDescription(String description) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(descriptionInput));
            descriptionInput.clear();
            Thread.sleep(500);
            descriptionInput.sendKeys(description);
            System.out.println("✓ Article description entered: " + description);
        } catch (Exception e) {
            System.err.println("Failed to enter article description: " + e.getMessage());
            throw new RuntimeException("Failed to enter article description", e);
        }
    }

    public void clickDoneButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(doneButton));
            Thread.sleep(500);
            doneButton.click();
            System.out.println("✓ Done button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click done button: " + e.getMessage());
            throw new RuntimeException("Failed to click done button", e);
        }
    }

    public void clickCancelButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            Thread.sleep(500);
            cancelButton.click();
            System.out.println("✓ Cancel button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click cancel button: " + e.getMessage());
            throw new RuntimeException("Failed to click cancel button", e);
        }
    }

    public void createManualArticle(String title, String description) {
        System.out.println("\nCreating manual article...");
        clickAddArticleButton();
        Thread.sleep(2000);
        selectManualArticle();
        Thread.sleep(2000);

        if (isArticleFormDisplayed()) {
            enterArticleTitle(title);
            enterArticleDescription(description);
            clickDoneButton();
            System.out.println("✓ Article creation completed");
        } else {
            throw new RuntimeException("Article form did not display");
        }
    }

    public boolean isArticleDisplayed(String articleTitle) {
        try {
            Thread.sleep(2000);
            WebElement article = driver.findElement(
                    By.xpath("//*[contains(text(), '" + articleTitle + "')]"));
            boolean displayed = article.isDisplayed();
            System.out.println("✓ Article '" + articleTitle + "' displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            System.err.println("Article '" + articleTitle + "' not found: " + e.getMessage());
            return false;
        }
    }

    public int getArticlesCount() {
        try {
            Thread.sleep(2000);
            int count = articles.size();
            System.out.println("✓ Total articles count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Failed to get articles count: " + e.getMessage());
            return 0;
        }
    }

    public List<String> getAllArticleTitles() {
        try {
            Thread.sleep(2000);
            List<String> titles = new java.util.ArrayList<>();
            for (WebElement title : articleTitles) {
                if (title.isDisplayed()) {
                    titles.add(title.getText());
                }
            }
            System.out.println("✓ Found " + titles.size() + " article titles");
            return titles;
        } catch (Exception e) {
            System.err.println("Failed to get article titles: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void clickArticleByTitle(String articleTitle) {
        try {
            Thread.sleep(2000);
            WebElement article = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(), '" + articleTitle
                            + "')]/ancestor::div[contains(@class, 'article')] | " +
                            "//*[contains(text(), '" + articleTitle + "')]")));

            Thread.sleep(500);
            article.click();
            System.out.println("✓ Clicked on article: " + articleTitle);
        } catch (Exception e) {
            System.err.println("Failed to click article: " + e.getMessage());
            throw new RuntimeException("Failed to click article", e);
        }
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}
