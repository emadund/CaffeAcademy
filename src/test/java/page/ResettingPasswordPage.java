package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResettingPasswordPage extends BaseClass {

    @FindBy (css = "[placeholder='Unesite Email adresu']")
    WebElement email;

    @FindBy (css=".resetButton")
    WebElement resetButton;

    @FindBy (xpath = "//div[@class='input-field-box-reset']/input")
    List<WebElement> boxes;

    @FindBy (css="[alt=\'Back Button\']")
    WebElement back;

    @FindBy (css="p a")
    WebElement sendAgain;

    @FindBy (css="[placeholder='Unesite novu lozinku']")
    WebElement newPassword;

    @FindBy (css="[placeholder='Ponovite lozinku']")
    WebElement repeatPassword;

    @FindBy (xpath = "//div/h2")
    WebElement confirmationPasswordText;

    public ResettingPasswordPage () {
        PageFactory.initElements(driver,this);
    }

    public void fillEmail (String text) {
        fillTextElement(email,text);
    }
    public void clickOnResetButton () {
        clickOnElement(resetButton);
    }
    public void clickOnBack () {
        clickOnElement(back);
    }
    public void clickOnSendAgain() {
        clickOnElement(sendAgain);
    }
    public void fillBoxes (String text) throws Exception {
        for (WebElement w:boxes) {
            fillTextElement(w,text);
            Thread.sleep(500);
        }
    }
    public void fillNewPassword(String text) {
        fillTextElement(newPassword,text);
    }
    public void fillConfirmedPassword(String text) {
        fillTextElement(repeatPassword,text);
    }

    public String getConfirmationPasswordText() {
        return getTextFromElement(confirmationPasswordText);
    }


}
