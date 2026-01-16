package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HealthMenuPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Header Elements
    @FindBy(xpath = "//img[@alt='Logo']")
    private WebElement healthAeraLogo;

    @FindBy(xpath = "//button[.//img[@alt='Dashboard']]")
    private WebElement dashboardButton;

    @FindBy(xpath = "//button[.//img[@alt='Notifications']]")
    private WebElement notificationsButton;

    @FindBy(xpath = "//button[.//img[@alt='Hamburger']]")
    private WebElement hamburgerMenuButton;

    // Progress Banner Elements
    @FindBy(xpath = "//h4[contains(text(), 'doing Great') or contains(text(), 'Action Needed') or contains(text(), 'Keep it up')]")
    private WebElement progressBannerHeading;

    @FindBy(xpath = "//*[contains(text(), 'Steady progress') or contains(text(), 'pending activities') or contains(text(), 'on track')]")
    private WebElement progressBannerMessage;

    // My Planner Section
    @FindBy(xpath = "//h2[contains(text(), 'My Planner')]")
    private WebElement myPlannerHeading;

    @FindBy(xpath = "//*[contains(text(), 'Track appointments, medicines, and daily tasks')]")
    private WebElement myPlannerDescription;

    @FindBy(xpath = "//h2[contains(text(), 'My Planner')]/following::button[1]")
    private WebElement myPlannerArrowButton;

    // Avatar
    @FindBy(xpath = "//img[@alt='Avatar']")
    private WebElement avatarImage;

    // Circular Menu Items (if they have specific identifiers, add them)
    @FindBy(xpath = "//button[contains(@class, 'menu-item')]")
    private List<WebElement> circularMenuItems;

    // Constructor
    public HealthMenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Page Actions
    public boolean isHealthMenuPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(healthAeraLogo));
            System.out.println("✓ Health Menu page displayed");
            return healthAeraLogo.isDisplayed();
        } catch (Exception e) {
            System.err.println("Health Menu page not displayed: " + e.getMessage());
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
            System.err.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public boolean isLogoDisplayed() {
        try {
            return healthAeraLogo.isDisplayed();
        } catch (Exception e) {
            System.err.println("Logo not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isDashboardButtonDisplayed() {
        try {
            return dashboardButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("Dashboard button not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isNotificationsButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(notificationsButton));
            return notificationsButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("Notifications button not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isHamburgerMenuButtonDisplayed() {
        try {
            return hamburgerMenuButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("Hamburger menu button not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isProgressBannerDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(progressBannerHeading));
            System.out.println("✓ Progress banner displayed: " + progressBannerHeading.getText());
            return progressBannerHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("Progress banner not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getProgressBannerHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(progressBannerHeading));
            String heading = progressBannerHeading.getText();
            System.out.println("✓ Progress banner heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Could not get progress banner heading: " + e.getMessage());
            return "";
        }
    }

    public String getProgressBannerMessage() {
        try {
            String message = progressBannerMessage.getText();
            System.out.println("✓ Progress banner message: " + message);
            return message;
        } catch (Exception e) {
            System.err.println("Could not get progress banner message: " + e.getMessage());
            return "";
        }
    }

    public boolean isMyPlannerSectionDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(myPlannerHeading));
            System.out.println("✓ My Planner section displayed");
            return myPlannerHeading.isDisplayed();
        } catch (Exception e) {
            System.err.println("My Planner section not displayed: " + e.getMessage());
            return false;
        }
    }

    public String getMyPlannerHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(myPlannerHeading));
            String heading = myPlannerHeading.getText();
            System.out.println("✓ My Planner heading: " + heading);
            return heading;
        } catch (Exception e) {
            System.err.println("Could not get My Planner heading: " + e.getMessage());
            return "";
        }
    }

    public String getMyPlannerDescription() {
        try {
            String description = myPlannerDescription.getText();
            System.out.println("✓ My Planner description: " + description);
            return description;
        } catch (Exception e) {
            System.err.println("Could not get My Planner description: " + e.getMessage());
            return "";
        }
    }

    public boolean isMyPlannerArrowButtonDisplayed() {
        try {
            return myPlannerArrowButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("My Planner arrow button not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickMyPlannerArrowButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(myPlannerArrowButton));
            Thread.sleep(500);
            myPlannerArrowButton.click();
            System.out.println("✓ My Planner arrow button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click My Planner arrow button: " + e.getMessage());
            throw new RuntimeException("Failed to click My Planner arrow button", e);
        }
    }

    public boolean isAvatarDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(avatarImage));
            System.out.println("✓ Avatar image displayed");
            return avatarImage.isDisplayed();
        } catch (Exception e) {
            System.err.println("Avatar not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickDashboardButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dashboardButton));
            dashboardButton.click();
            System.out.println("✓ Dashboard button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click dashboard button: " + e.getMessage());
            throw new RuntimeException("Failed to click dashboard button", e);
        }
    }

    public void clickNotificationsButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(notificationsButton));
            notificationsButton.click();
            System.out.println("✓ Notifications button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click notifications button: " + e.getMessage());
            throw new RuntimeException("Failed to click notifications button", e);
        }
    }

    public void clickHamburgerMenuButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenuButton));
            hamburgerMenuButton.click();
            System.out.println("✓ Hamburger menu button clicked");
        } catch (Exception e) {
            System.err.println("Failed to click hamburger menu button: " + e.getMessage());
            throw new RuntimeException("Failed to click hamburger menu button", e);
        }
    }

    public boolean verifyAllHeaderElementsDisplayed() {
        boolean logoDisplayed = isLogoDisplayed();
        boolean dashboardDisplayed = isDashboardButtonDisplayed();
        boolean notificationsDisplayed = isNotificationsButtonDisplayed();
        boolean hamburgerDisplayed = isHamburgerMenuButtonDisplayed();

        System.out.println("✓ Logo displayed: " + logoDisplayed);
        System.out.println("✓ Dashboard button displayed: " + dashboardDisplayed);
        System.out.println("✓ Notifications button displayed: " + notificationsDisplayed);
        System.out.println("✓ Hamburger menu displayed: " + hamburgerDisplayed);

        return logoDisplayed && dashboardDisplayed && notificationsDisplayed && hamburgerDisplayed;
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}