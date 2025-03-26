package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderStatusPage extends BaseClass {

    @FindBy (xpath = "//div[@class='status-page-info']/p")
    WebElement statusMessage;

    @FindBy (css="[alt=\'Back Button\']")
    WebElement back;

    public OrderStatusPage () {
        PageFactory.initElements(driver,this);
    }

    public String getStatusMessage() {
        return getTextFromElement(statusMessage);
    }
    public void clickOnBack() {
        clickOnElement(back);
    }
}
