package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class BaseClass {
    protected static WebDriver driver;
    protected static JavascriptExecutor js;
    protected static WebDriverWait wdWait;
    protected static final String URL_BASE="https://caffe-academy-liard.vercel.app/";

    @BeforeMethod

    public void initial () {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        wdWait = new WebDriverWait(driver,java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(URL_BASE);
    }

    protected void clickOnElement (WebElement w) {
        wdWait.until(ExpectedConditions.elementToBeClickable(w));
        w.click();
    }

    protected void fillTextElement (WebElement w, String text) {
        wdWait.until(ExpectedConditions.visibilityOf(w));
        w.clear();
        w.sendKeys(text);
    }

    protected boolean isElementDisplayed (WebElement w) {
        wdWait.until(ExpectedConditions.visibilityOf(w));
        return w.isDisplayed();
    }
    protected String getTextFromElement (WebElement w) {
        wdWait.until(ExpectedConditions.visibilityOf(w));
        return w.getText();
    }

    protected void clickOnDropDownItem (WebElement parent, WebElement target) {
        Actions hover = new Actions(driver);
        wdWait.until(ExpectedConditions.elementToBeClickable(parent));
        hover.moveToElement(parent).perform();
        wdWait.until(ExpectedConditions.elementToBeClickable(target));
        clickOnElement(target);
    }



    @AfterMethod
    public void tearDown() {   // To shutdown browsers after tests finished
        driver.close();
        driver.quit();
    }

}
