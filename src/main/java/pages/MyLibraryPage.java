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

public class MyLibraryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    @FindBy(xpath = "//button[contains(text(), 'My Library')] | //*[contains(text(), 'My Library')]")
    private WebElement myLibraryTab;

    @FindBy(xpath = "//h1[contains(text(), 'My Library')] | //h2[contains(text(), 'My Library')]")
    private WebElement pageHeading;

    // Add Folder Button (+ icon)
    @FindBy(xpath = "//button[contains(@aria-label, 'add') or contains(text(), '+') or .//img[contains(@alt, 'add')]]")
    private List<WebElement> addButtons;

    // Folders
    @FindBy(xpath = "//div[contains(@class, 'folder')] | //div[contains(@class, 'directory')]")
    private List<WebElement> folders;

    @FindBy(xpath = "//h3 | //h4 | //*[contains(@class, 'folder-name')] | //*[contains(@class, 'folder-title')]")
    private List<WebElement> folderNames;

    // No folders message
    @FindBy(xpath = "//*[contains(text(), 'No folders') or contains(text(), 'Create your first')]")
    private WebElement noFoldersMessage;

    // Constructor
    public MyLibraryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isMyLibraryPageDisplayed() {
        try {
            Thread.sleep(2000);

            // Check if My Library tab/page is visible
            String currentURL = driver.getCurrentUrl();
            if (currentURL.contains("library")) {
                System.out.println("✓ My Library page displayed (verified by URL)");
                return true;
            }

            // Try to find page heading or tab
            try {
                wait.until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOf(myLibraryTab),
                        ExpectedConditions.visibilityOf(pageHeading)));
                System.out.println("✓ My Library page displayed");
                return true;
            } catch (Exception e) {
                // Check if add button exists (indicating we're on the page)
                if (!addButtons.isEmpty()) {
                    System.out.println("✓ My Library page displayed (verified by add button)");
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            System.err.println("My Library page not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickAddFolderButton() {
        try {
            Thread.sleep(2000);

            // Find and click the + button to add folder
            WebElement addButton = null;

            try {
                // Strategy 1: Find + button
                addButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[contains(text(), '+') or contains(@aria-label, 'add folder') or contains(@aria-label, 'Add folder')]")));
            } catch (Exception e1) {
                try {
                    // Strategy 2: Find first clickable add button
                    if (!addButtons.isEmpty()) {
                        addButton = addButtons.get(0);
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
                System.out.println("✓ Add folder button clicked");
            }
        } catch (Exception e) {
            System.err.println("Failed to click add folder button: " + e.getMessage());
            throw new RuntimeException("Failed to click add folder button", e);
        }
    }

    public int getFoldersCount() {
        try {
            Thread.sleep(2000);
            int count = folders.size();
            System.out.println("✓ Total folders count: " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Failed to get folders count: " + e.getMessage());
            return 0;
        }
    }

    public List<String> getAllFolderNames() {
        try {
            Thread.sleep(2000);
            List<String> names = new java.util.ArrayList<>();
            for (WebElement name : folderNames) {
                if (name.isDisplayed()) {
                    names.add(name.getText());
                }
            }
            System.out.println("✓ Found " + names.size() + " folder names");
            return names;
        } catch (Exception e) {
            System.err.println("Failed to get folder names: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void clickFolderByName(String folderName) {
        try {
            Thread.sleep(2000);

            // Find and click folder by name
            WebElement folder = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(
                            "//*[contains(text(), '" + folderName + "')]/ancestor::div[contains(@class, 'folder')] | " +
                                    "//*[contains(text(), '" + folderName + "')]")));

            Thread.sleep(500);
            folder.click();
            System.out.println("✓ Clicked on folder: " + folderName);
        } catch (Exception e) {
            System.err.println("Failed to click folder by name: " + e.getMessage());
            throw new RuntimeException("Failed to click folder by name", e);
        }
    }

    public void clickFirstFolder() {
        try {
            Thread.sleep(2000);

            if (folders.isEmpty()) {
                throw new RuntimeException("No folders available to click");
            }

            WebElement firstFolder = folders.get(0);
            wait.until(ExpectedConditions.elementToBeClickable(firstFolder));
            Thread.sleep(500);
            firstFolder.click();
            System.out.println("✓ Clicked on first folder");
        } catch (Exception e) {
            System.err.println("Failed to click first folder: " + e.getMessage());
            throw new RuntimeException("Failed to click first folder", e);
        }
    }

    public boolean isFolderDisplayed(String folderName) {
        try {
            Thread.sleep(2000);
            WebElement folder = driver.findElement(
                    By.xpath("//*[contains(text(), '" + folderName + "')]"));
            boolean displayed = folder.isDisplayed();
            System.out.println("✓ Folder '" + folderName + "' displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            System.err.println("Folder '" + folderName + "' not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isNoFoldersMessageDisplayed() {
        try {
            return noFoldersMessage != null && noFoldersMessage.isDisplayed();
        } catch (Exception e) {
            return false;
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
}
