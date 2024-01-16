package basePage;

import onePositiveFlow.QuestionOne;
import onePositiveFlowWithTwoNegativeFlow.QuestionTwo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BaseClass {
    WebDriver driver;

    public BaseClass(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    public WebElement name;

    @FindBy(id = "password")
    public WebElement key;

    @FindBy(id = "login-button")
    public WebElement submit;


    @FindBy(xpath = "//h3[@data-test='error']")
    public WebElement errorMessage;

}
