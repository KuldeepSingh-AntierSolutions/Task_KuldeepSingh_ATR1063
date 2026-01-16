package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected YopmailService yopmailService;
    protected static final String BASE_URL = "https://qa-frontend.sierradimensions.com/onboarding?path=signin";
    protected static final int TIMEOUT = 20;

    @BeforeMethod
    public void setUp() {
        System.out.println("\\n=== Setting up WebDriver ===");
        
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        // Initialize driver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        
        // Initialize Yopmail service
        yopmailService = new YopmailService();
        
        System.out.println("✓ WebDriver initialized successfully");
        System.out.println("✓ Yopmail service initialized");
    }

    @AfterMethod
    public void tearDown() {
        if (yopmailService != null) {
            yopmailService.closeBrowser();
        }
        
        if (driver != null) {
            System.out.println("\\n=== Closing WebDriver ===");
            try {
                Thread.sleep(2000); // Brief pause before closing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("✓ WebDriver closed successfully");
        }
    }
    
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}