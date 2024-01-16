package onePositiveFlowWithTwoNegativeFlow;

import basePage.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class QuestionTwo {


    public static BaseClass baseClass;
    public static WebDriver driver;
    public static String expectedLoginUrl = "https://www.saucedemo.com/v1/";
    public static String expectedHomepageUrl = "https://www.saucedemo.com/v1/inventory.html";
    public static String expectedTitle = "Swag Labs";
    public static SoftAssert softAssert = new SoftAssert();

    @Parameters("browserName")
    @BeforeTest(groups = {"smoke", "sanity"})
    public void initializeDriver(String browser) {
        switch (browser){
            case "chrome":
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;
            case "firefox":
                driver= new FirefoxDriver();
                driver.manage().window().maximize();
                break;
            default:
                System.out.println("Invalid Browser");
        }
        baseClass=new BaseClass(driver);
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

    @Parameters({"incorrectUsername", "password"})
    @Test(groups = {"smoke", "sanity"}, priority = 1)
    public void incorrectUsernameLoginFlow(String incorrectUsername, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
//        WebElement usernameTextBox = driver.findElement(By.id("user-name"));
//        usernameTextBox.clear();
//        usernameTextBox.sendKeys(incorrectUsername);
//        WebElement passwordTextBox = driver.findElement(By.id("password"));
//        passwordTextBox.clear();
//        passwordTextBox.sendKeys(password);
//        driver.findElement(By.id("login-button")).click();
//        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));

        baseClass.name.clear();
        baseClass.name.sendKeys(incorrectUsername);
        baseClass.key.sendKeys(password);
        baseClass.submit.click();
        System.out.println("Incorrect Username :" + baseClass.errorMessage.getText());

    }

    @Parameters({"username", "incorrectPassword"})
    @Test(groups = {"smoke", "sanity"}, priority = 2)
    public void incorrectPasswordLoginFlow(String username, String incorrectPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        baseClass.name.clear();
        baseClass.name.sendKeys(username);
        baseClass.key.sendKeys(incorrectPassword);
        baseClass.submit.click();
        System.out.println("Incorrect Password: " + baseClass.errorMessage.getText());
    }

    @Parameters({"username", "password"})
    @Test(groups = {"smoke", "sanity"}, priority = 3)
    public void positiveLoginFlow(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        baseClass.name.clear();
        baseClass.name.sendKeys(username);
        baseClass.key.sendKeys(password);
        baseClass.submit.click();
    }


    @Test(groups = {"smoke", "sanity"}, priority = 4, dependsOnMethods = "positiveLoginFlow")
    public void verifySuccessfulLogin() {
        String actualHomepageUrl = driver.getCurrentUrl();
        softAssert.assertEquals(actualHomepageUrl, expectedHomepageUrl);
    }

    @AfterSuite
    public void quitBrowser() {
        driver.quit();
    }

}
