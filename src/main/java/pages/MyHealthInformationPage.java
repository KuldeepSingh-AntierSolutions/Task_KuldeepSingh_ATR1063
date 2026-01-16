package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyHealthInformationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Header Elements
    @FindBy(xpath = "//button[contains(@type, 'button')]//ancestor::*[contains(text(), 'My Health Information')] | //*[contains(text(), 'My Health Information')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[contains(@class, 'back') or position()=1]")
    private WebElement backButton;

    @FindBy(xpath = "//button[contains(@class, 'menu') or .//img[contains(@alt, 'menu')]]")
    private WebElement menuButton;

    // My Health Journey Section
    @FindBy(xpath = "//*[contains(text(), 'My Health Journey')]")
    private WebElement healthJourneyHeading;

    // My Health Bio Card
    @FindBy(xpath = "//h3[contains(text(), 'My Health Bio')] | //button[.//h3[contains(text(), 'My Health Bio')]]")
    private WebElement myHealthBioCard;

    @FindBy(xpath = "//h3[contains(text(), 'My Health Bio')]")
    private WebElement myHealthBioHeading;

    @FindBy(xpath = "//*[contains(text(), 'Doctor-ready snapshot')]")
    private WebElement myHealthBioDescription;

    // My Health History Card
    @FindBy(xpath = "//h3[contains(text(), 'My Health History')] | //button[.//h3[contains(text(), 'My Health History')]]")
    private WebElement myHealthHistoryCard;

    @FindBy(xpath = "//h3[contains(text(), 'My Health History')]")
    private WebElement myHealthHistoryHeading;

    @FindBy(xpath = "//*[contains(text(), 'Surgeries & past events')]")
    private WebElement myHealthHistoryDescription;

    // Search Bar
    @FindBy(xpath = "//input[@placeholder='Search condition' or @type='Search']")
    private WebElement searchConditionInput;

    @FindBy(xpath = "//button[contains(text(), 'Search') or @type='button']")
    private WebElement searchButton;

    // Condition Card
    @FindBy(xpath = "//*[contains(text(), 'condition')]")
    private WebElement conditionCard;

    // Add Button
    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    private WebElement addButton;

    // Constructor
    public MyHealthInformationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isMyHealthInformationPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("phi"));
            System.out.println("✓ My Health Information page displayed");
            return driver.getCurrentUrl().contains("phi");
        } catch (Exception e) {
            System.err.println("My Health Information page not displayed: " + e.getMessage());
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

    public boolean isHealthJourneySectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(healthJourneyHeading));
            System.out.println("✓ My Health Journey section displayed");
            return healthJourneyHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("Health Journey section not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isMyHealthBioCardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(myHealthBioHeading));
            System.out.println("✓ My Health Bio card displayed");
            return myHealthBioHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("My Health Bio card not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getMyHealthBioHeading() {
        try {
            String heading = myHealthBioHeading.getText();
            System.out.println("✓ My Health Bio heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Could not get My Health Bio heading: " + e.getMessage());
            return "";
        }
    }

    public String getMyHealthBioDescription() {
        try {
            String description = myHealthBioDescription.getText();
            System.out.println("✓ My Health Bio description: " + description);
            return description;
        } catch (Exception e) {
            System.err.println("Could not get My Health Bio description: " + e.getMessage());
            return "";
        }
    }

    public void clickMyHealthBioCard() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(myHealthBioCard));
            Thread.sleep(500);
            myHealthBioCard.click();
            System.out.println("✓ My Health Bio card clicked");
        } catch (Exception e) {
            System.err.println("Failed to click My Health Bio card: " + e.getMessage());
            throw new RuntimeException("Failed to click My Health Bio card", e);
        }
    }

    public boolean isMyHealthHistoryCardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(myHealthHistoryHeading));
            System.out.println("✓ My Health History card displayed");
            return myHealthHistoryHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("My Health History card not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getMyHealthHistoryHeading() {
        try {
            String heading = myHealthHistoryHeading.getText();
            System.out.println("✓ My Health History heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Could not get My Health History heading: " + e.getMessage());
            return "";
        }
    }

    public String getMyHealthHistoryDescription() {
        try {
            String description = myHealthHistoryDescription.getText();
            System.out.println("✓ My Health History description: " + description);
            return description;
        } catch (Exception e) {
            System.err.println("Could not get My Health History description: " + e.getMessage());
            return "";
        }
    }

    public void clickMyHealthHistoryCard() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(myHealthHistoryCard));
            Thread.sleep(500);
            myHealthHistoryCard.click();
            System.out.println("✓ My Health History card clicked");
        } catch (Exception e) {
            System.err.println("Failed to click My Health History card: " + e.getMessage());
            throw new RuntimeException("Failed to click My Health History card", e);
        }
    }

    public boolean isSearchBarDisplayed() {
        try {
            return searchConditionInput.isDisplayed();
        } catch (Exception e) {
            System.err.println("Search bar not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddButtonDisplayed() {
        try {
            return addButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("Add button not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickBackButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(backButton));
            backButton.click();
            System.out.println("✓ Back button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click back button: " + e.getMessage());
            throw new RuntimeException("Failed to click back button", e);
        }
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
 // Add these methods to existing MyHealthInformationPage.java

    public void clickAddButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Add')]")));
            WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Add')]"));
            Thread.sleep(500);
            addButton.click();
            System.out.println("✓ Add button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click Add button: " + e.getMessage());
            throw new RuntimeException("Failed to click Add button", e);
        }
    }

    public boolean isConditionDisplayedInList(String conditionName) {
        try {
            Thread.sleep(2000);
            WebElement condition = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), '" + conditionName + "')]")));
            boolean isDisplayed = condition.isDisplayed();
            System.out.println("✓ Condition '" + conditionName + "' displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("Condition not found: " + e.getMessage());
            return false;
        }
    }

    public void clickConditionFolder(String conditionName) {
        try {
            WebElement folder = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//h3[contains(text(), '" + conditionName + "')]]")));
            Thread.sleep(500);
            folder.click();
            System.out.println("✓ Condition folder '" + conditionName + "' clicked");
        } catch (Exception e) {
            System.err.println("Failed to click condition folder: " + e.getMessage());
            throw new RuntimeException("Failed to click condition folder", e);
        }
    }
}