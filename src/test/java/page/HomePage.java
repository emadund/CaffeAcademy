package page;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseClass {

    @FindBy (css=".loginButton")
    WebElement login;

    @FindBy (css=".footerMain__right button")
    WebElement order;

    @FindBy (css=".footerMain .footerMain__left p")
    WebElement priceSection;

    @FindBy (css=".avatar")
    WebElement avatar;

    @FindBy (css=".coffe-item")
    List<WebElement> coffeItems;

    @FindBy (css=".coffe-item .fas.fa-plus")
    List<WebElement> plusButtons;

    @FindBy (css=".fas.fa-minus")
    List<WebElement> minusButtons;

    @FindBy (css = ".coffe-item-basket")
    List<WebElement> addToCart;

    @FindBy(id = "dropdown-left")
    List<WebElement> seedDropdown;

    @FindBy(id = "#dropdown-right")
    List<WebElement> milkDropdown;

    @FindBy (xpath="//div[@class='dropdownMenu']/ul/li[2]")
    WebElement signout;

    @FindBy(xpath = "//div[@class='dropdownMenu']/ul/li[1]")
    WebElement profile;


    List<Select> seedSelect;
    List<Select> milkSelect;

    List<WebElement> spreadButtons;
    List<WebElement> plusQuantityButtons;

    public HomePage () {
        PageFactory.initElements(driver,this);

    }

    public void clickOnLogin () {
        clickOnElement(login);
    }

    public void clickOnSpread (int i) {
        clickOnElement(spreadButtons.get(i));
    }

    public void clickOnPlusQuantity (int i) {
        clickOnElement(plusButtons.get(i));
    }
    public void clickOnMinusQuantity (int i) {
        clickOnElement(minusButtons.get(i));
    }

    public void selectSeedOption (String option, int i) {
        seedSelect.get(i).selectByVisibleText(option);
    }

    public void selectMilkOption (String option, int i) {
        milkSelect.get(i).selectByVisibleText(option);
    }

    public void addToCart (int i) {
        clickOnElement(addToCart.get(i));
    }

    public boolean isAvatarVisible () {
        return isElementDisplayed(avatar);
    }

    private List<WebElement> fillButtons (List<WebElement> buttons) {
        List<WebElement> list = new ArrayList<>();
        for (WebElement w:buttons) {
            list.add(w);
        }
        return list;
    }
    private List<Select> selectSpreadButtons () {
        List<Select> list = new ArrayList<>();
        for (int i=0;i<getPlusButtons().size();i+=2) {
            list.add(new Select(getPlusButtons().get(i)));
        }
        return list;
    }
    private List<Select> selectQuantityPlusButtons () {
        List<Select> list = new ArrayList<>();
        for (int i=1;i<getPlusButtons().size();i+=2) {
            list.add(new Select(getPlusButtons().get(i)));
        }
        return list;
    }

    public List<WebElement> getCoffeItems() {
        return coffeItems;
    }

    public List<WebElement> getPlusButtons() {
        return plusButtons;
    }

    public List<WebElement> getMinusButtons() {
        return minusButtons;
    }

    public List<WebElement> getSeedDropdown() {
        return seedDropdown;
    }

    public List<WebElement> getMilkDropdown() {
        return milkDropdown;
    }

    public List<WebElement> getSpreadButtons() {
        return spreadButtons;
    }

    public List<WebElement> getPlusQuantityButtons() {
        return plusQuantityButtons;
    }

    public List<Select> getSeedSelect() {
        return seedSelect;
    }

    public List<Select> getMilkSelect() {
        return milkSelect;
    }
    public void clickOnAvatar() {
        clickOnElement(avatar);
    }
    public void clickOnSignout () {
        clickOnElement(signout);
    }
    public void clickOnProfile() {
        clickOnElement(profile);
    }
    public String getLoginText () {
        return getTextFromElement(login);
    }
}
