package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login extends BaseClass {

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

    public Login () {
        PageFactory.initElements(driver, this);
    }

    public Login fillEmail (String text) {
        fillTextElement(email, text);
        return this;
    }

    public Login fillPassword (String text) {
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


}
