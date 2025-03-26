package page;

import base.BaseClass;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmRegistrationPage extends BaseClass {

    @FindBy (xpath = "//div[@class='confirmation-form']/h2")
    WebElement confirmationSuccessfull;

    @FindBy (className = "backToHomePageButton")
    WebElement homeButton;

    @FindBy (css=".back-button")
    WebElement back;

    public ConfirmRegistrationPage () {
        PageFactory.initElements(driver, this);
    }

    public void clickOnBackToHome () {
        clickOnElement(homeButton);
    }
    public boolean isRegistrationSuccessfull() {
        return isElementDisplayed(confirmationSuccessfull);
    }

    public String getSuccessfullRegistrationText() {
        return getTextFromElement(confirmationSuccessfull);
    }

    public void clickOnBack () {
        clickOnElement(back);
    }

}
