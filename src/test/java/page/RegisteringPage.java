package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisteringPage extends BaseClass {

    @FindBy (css="[placeholder=\"Unesite ime i prezime\"]")
    WebElement fullName;

    @FindBy (css="[placeholder=\"Unesite Email adresu\"]")
    WebElement email;

    @FindBy (css="[placeholder=\"Unesite lozinku\"]")
    WebElement password;

    @FindBy (css="[placeholder=\"Potvrdite lozinku\"']")
    WebElement confirmPassword;

    @FindBy (className = "registerButton")
    WebElement register;

    @FindBy (css="[alt='Back Button']")
    WebElement back;

    @FindBy (css="p a")
    WebElement login;

    public RegisteringPage () {
        PageFactory.initElements(driver, this);
    }

    public RegisteringPage fillFullName (String text) {
        fillTextElement(fullName,text);
        return this;
    }
    public RegisteringPage fillEmail (String text) {
        fillTextElement(email,text);
        return this;
    }
    public RegisteringPage fillPassword (String text) {
        fillTextElement(password,text);
        return this;
    }
    public RegisteringPage fillConfirmPassword (String text) {
        fillTextElement(confirmPassword,text);
        return this;
    }
    public void clickOnRegister () {
        clickOnElement(register);
    }
    public void clickOnLogin () {
        clickOnElement(login);
    }
    public void clickOnBack () {
        clickOnElement(back);
    }






}
