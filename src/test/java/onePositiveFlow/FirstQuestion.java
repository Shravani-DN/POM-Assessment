package onePositiveFlow;


import basePage.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class QuestionOne {

    protected static BaseClass baseClass;

    public static WebDriver driver;
    public static String expectedLoginUrl = "https://www.saucedemo.com/v1/";
    public static String expectedHomepageUrl = "https://www.saucedemo.com/v1/inventory.html";
    public static String expectedTitle = "Swag Labs";
    public static SoftAssert softAssert = new SoftAssert();


    @BeforeSuite(groups = {"smoke", "sanity"})
    public void initializeDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        baseClass = new BaseClass(driver);

    }

    @BeforeTest(groups = {"smoke", "sanity"})
    @Parameters("url")
    public void launchWebsite(String url) {
        driver.get(url);
    }

    @Test(priority = 0, groups = {"smoke", "sanity"})
    public void getCurrentUrlOfWebsite() {
        String actualLoginUrl = driver.getCurrentUrl();
        softAssert.assertEquals(actualLoginUrl, expectedLoginUrl);
    }

    @Test(priority = 0, groups = {"smoke", "sanity"})
    public void getTitleOfWebsite() {
        String actualTitle = driver.getTitle();
        softAssert.assertEquals(actualTitle, expectedTitle);
    }

    @Parameters({"username", "password"})
    @Test(groups = {"smoke", "sanity"}, priority = 1)
    public void positiveLoginFlow(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
//        driver.findElement(By.id("user-name")).sendKeys(username);
//        driver.findElement(By.id("password")).sendKeys(password);
//        driver.findElement(By.id("login-button")).click();

        baseClass.name.sendKeys(username);
        baseClass.key.sendKeys(password);
        baseClass.submit.click();

    }

    @Test(groups = {"smoke", "sanity"}, priority = 2, dependsOnMethods = "positiveLoginFlow")
    public void verifySuccessfulLogin() {
        String actualHomepageUrl = driver.getCurrentUrl();
        softAssert.assertEquals(actualHomepageUrl, expectedHomepageUrl);
    }

    @AfterTest
    public void quitBrowser() {
        driver.quit();
    }
}
