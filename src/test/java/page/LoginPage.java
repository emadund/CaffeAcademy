package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {

    @FindBy (css=".back-button")
    WebElement backButton;

    @FindBy (name="email")
    WebElement email;

    @FindBy (name="password")
    WebElement password;

    @FindBy (css=".forgot-your-pass")
    WebElement forgottenPassword;

    @FindBy (css=".loginButton")
    WebElement login;

    @FindBy (css=".register-link a")
    WebElement register;

    @FindBy (css=".login-form-alert-message2 p")
    WebElement errorMessage;

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public LoginPage fillEmail (String text) {
        fillTextElement(email, text);
        return this;
    }

    public LoginPage fillPassword (String text) {
        fillTextElement(password, text);
        return this;
    }

    public void clickOnForgottenPassword () {
        clickOnElement(forgottenPassword);
    }
    public void clickOnBack () {
        clickOnElement(backButton);
    }
    public void clickOnLogin() {
        clickOnElement(login);
    }
    public void clickOnRegister() {
        clickOnElement(register);
    }
    public void clickOnForgetPassword() {
        clickOnElement(forgottenPassword);
    }
    public String getErrorMessage() {
        return getTextFromElement(errorMessage);
    }


}
